package com.jdbc;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBUtil {
    // 私有化构造函数,防止其他人new本类的实例
    private DBUtil() {}

    private static DataSource dataSource;

    static {
        dataSource = new ComboPooledDataSource("mysql");
    }

    // 得到连接,从数据源得到
    public static Connection getConn() {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return conn;
    }

    // 清理资源
    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    // 清理资源
    public static void close(ResultSet rs, Statement stm, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (stm != null) {
            try {
                stm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close(); // 不会真的销毁连接,会把连接放回连接池中,因为经过DataSource得到的连接,其实经过了代理, close() 已经在内部重写了
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    //万能更新(增,删,改)
    public static int update(String sql,Object ... params) {
        int result=0;

        Connection conn=null;
        QueryRunner qr=null;

        try {
            conn=DBUtil.getConn();
            qr=new QueryRunner();
            result=qr.update(conn, sql, params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            close(conn);
        }

        return result;
    }

    //添加数据,返回生成的自增ID
    public static int addAndReturnId(String sql,Object ... params) {
        int autoId =0 ;
        Connection conn=null;
        PreparedStatement stm=null;
        try {
            conn=getConn();
            stm=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            for(int i=0;i<params.length;i++) {
                stm.setObject(i+1, params[i]);
            }

            //执行添加操作
            stm.execute();

            //取出生成的自增主键
            ResultSet rskey=stm.getGeneratedKeys();
            rskey.next();
            autoId=rskey.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            close(null,stm,conn);
        }

        return autoId;
    }


    //查询单个对象
    public static <T> T getSingleObject(String sql,Class<T> clazz,  Object ...  params) {
        T result=null;
        QueryRunner qr=null;   //它是线程不安全的,不要定义成全局变量
        Connection conn=null;

        try {
            conn=getConn();
            qr=new QueryRunner();
            result=qr.query(conn,sql, new BeanHandler<T>(clazz),params);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            close(conn);
        }

        return result;
    }

    //查询一组对象(如果查到的数据为空,返回的是一个空列表,不是null)
    public static <T>  List<T> getList(String sql,Class<T> clazz,  Object ...  params){
        List<T> list=new ArrayList<T>();
        Connection conn=null;
        try {
            conn=getConn();
            list=new QueryRunner().query(conn,sql, new BeanListHandler<T>(clazz),params);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            close(conn);
        }

        return list;
    }


    //返回Map集合(将查到的一条数据,以Map集合的方式返回, key是字段名,value 是字段值)
    public static Map<String,Object> getMap(String sql,Object ...params){
        Map<String,Object> m=new HashMap<String,Object>();
        Connection conn=null;

        try {
            conn=getConn();
            m=new QueryRunner().query(conn,sql, new MapHandler(), params);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            close(conn);
        }

        return m;
    }

    //返回一个List集合,里面的每条数据都是一个Map集合
    public static List<Map<String,Object>> getMapList(String sql,Object ... params){
        List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>> ();
        Connection conn=null;
        try {
            conn=getConn();
            listMap=new QueryRunner().query(conn,sql, new MapListHandler(), params);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            close(conn);
        }

        return listMap;
    }


    //返回单个数据
    public static <T> T getScalar(String sql, Object ... params) {
        T result=null;

        Connection conn=null;
        try {
            conn=DBUtil.getConn();
            result=new QueryRunner().query(conn,sql,new ScalarHandler<T>(1), params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            close(conn);
        }
        return result;
    }

    //返回单个数据的List (例如：Name字段)
    public static <T> List<T> getScalarColList(String sql,Object ... params) {
        List<T>  result=new ArrayList<>();
        Connection conn=null;
        try {
            conn=getConn();
            result=new QueryRunner().query(conn,sql,new ColumnListHandler<T>(1),params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            close(conn);
        }
        return result;
    }
    //返回单个数据的全部内容（不重复）
    public static <T> Set<T> getScalarColSet(String sql,Object ... params) {
        Set<T>  result=new HashSet<>();
        Connection conn=null;
        try {
            conn=getConn();
            //将得到的List集合转成Set集合
            result= new QueryRunner().query(conn,sql,new ColumnListHandler<T>(1),params)
                           .stream().distinct().collect(Collectors.toSet());;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            close(conn);
        }
        return result;
    }
}








