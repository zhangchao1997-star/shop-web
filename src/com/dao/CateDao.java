package com.dao;

import com.beans.CateInfo;
import com.jdbc.DBUtil;

import java.util.List;

/**
 * 一二级分类-dao层
 */
public class CateDao {
    /**
     * 根据一级分类名获取一级分类实体对象--主要是用来判断一级分类是否重复
     * @param className 分类名称
     * @return 分类实体类
     */
    public CateInfo getFirCateInfo(String className) {
        String sql = "select * from cateInfo where cateName=? and parentId=?";
        return DBUtil.getSingleObject(sql,CateInfo.class,className,0);
    }

    /**
     * 在cateInfo表中添加一级分类
     * @param firstClassName 一级分类名称
     * @param note 一级分类描述
     * @return 如果为1，则成功
     */
    public int addFir(String firstClassName, String note) {
        String sql = "insert into cateInfo(cateName,des,parentId) values(?,?,?)";
        return DBUtil.update(sql,firstClassName,note,0); //一级分类的parentId为0
    }

    /**
     * 获取所有的一级分类
     * @return 返回一个装有所有一级分类的List
     */
    public List<CateInfo> getFirCate() {
        String sql = "select * from cateInfo where parentId=?";
        return DBUtil.getList(sql,CateInfo.class,0);
    }

    /**
     * 添加某个一级分类下的二级分类
     * @param secondClassName 二级分类名
     * @param note 二级分类描述
     * @param id 一级分类的id
     * @return 如果为1，则成功
     */
    public int addSecond(String secondClassName, String note, Integer id) {
        String sql = "insert into cateInfo (cateName,des,parentId) values(?,?,?)";
        return DBUtil.update(sql,secondClassName,note,id);
    }

    /**
     * 查询所有的分类（一级分类，二级分类）
     * @return 所有分类信息
     */
    public List<CateInfo> getAllCate(int parentId) {
        String sql = "select * from cateInfo where parentId=?";
        List<CateInfo> cateInfoList = DBUtil.getList(sql,CateInfo.class,parentId);
        for (CateInfo m:cateInfoList){
            m.setSubCateInfo( getAllCate( m.getId()));
        }
        return cateInfoList;
    }

    /**
     * 根据分类id查询分类信息
     * @param id 分类id
     * @return 分类实体类
     */
    public CateInfo getCateById(int id) {
        return DBUtil.getSingleObject("select * from cateInfo where id=?",CateInfo.class,id);
    }

    /**
     * 更新一，二级分类
     * @param className 分类名
     * @param des 分类描述
     * @param id 分类id
     * @return 返回1成功
     */
    public int updateCate(String className, String des, int id) {
        String sql = "update cateInfo set cateName=?,des=? where id=?";
        return DBUtil.update(sql,className,des,id);
    }

    /**
     * 根据二级分类id删除该二级分类
     * @param id 分类id
     * @return 如果是1成功
     */
    public int delSec(int id) {
        String sql = "delete from cateInfo where id=?";
        return DBUtil.update(sql,id);
    }

    /**
     * 根据一级分类id删除该一级分类(对应所属的二级分类应该都要删除掉)
     * @param id 分类id
     * @return 如果是1成功
     */
    public int delFir(int id) {
        int result = 0;
        String sql = "delete from cateInfo where id=?";
        result+=DBUtil.update(sql,id);
        //找出该一级分类下的二级分类有几个
        int sub_length = getSecCate(id).size();
        String sql_sub = "delete from cateInfo where parentId=?";
        for (int i = 0; i < sub_length; i++) {
            result+=DBUtil.update(sql_sub,id);
        }
        return result;
    }

    /**
     * 根据父级id查询所有二级分类信息
     * @param parentId 父级id
     * @return cate对象
     */
    public List<CateInfo> getSecCate(int parentId) {
        String sql = "select * from cateInfo where parentId=?";
        return DBUtil.getList(sql,CateInfo.class,parentId);
    }

    /**
     * 根据二级分类名获取二级分类实体对象
     * @param className 分类名称
     * @return 分类实体类
     */
    public CateInfo getSecCateInfo(String className) {
        String sql = "select * from cateInfo where cateName=? and parentId!=?";
        return DBUtil.getSingleObject(sql,CateInfo.class,className,0);
    }
}
