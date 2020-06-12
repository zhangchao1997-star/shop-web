package com.dao;

import com.beans.AdminInfo;
import com.beans.PageInfo;
import com.commons.Constant;
import com.jdbc.DBUtil;
import com.utils.DesUtil;

import java.util.List;

/**
 * 管理员-dao层类
 */
public class AdminDao {
    /**
     * 管理员登陆
     * @param adminName 账号
     * @param password 密码
     * @return 如果数据库里有该管理员返回管理员信息，否则返回null
     */
    public AdminInfo login(String adminName, String password) {
        String sql = "select * from adminInfo where adminName=? and password=?";
        return DBUtil.getSingleObject(sql,AdminInfo.class,adminName,password);
    }

    /**
     * 添加管理员用户
     * @param adminInfo 管理员实体类
     * @return 如果返回1添加成功，否则添加失败
     */
    public int addAdmin(AdminInfo adminInfo) {
        String sql = "insert into adminInfo(adminName,password,note,state) values(?,?,?,?)";
        return DBUtil.update(sql,adminInfo.getAdminName(),adminInfo.getPassword(),adminInfo.getNote(),adminInfo.getState());
    }

    /**
     * 服务端验证该管理员账号是否已经存在
     * @param adminName 账号
     * @return 如果返回AdminInfo对象则表明该管理员在数据库已存在，否则不存在
     */
    public AdminInfo validateAdminName(String adminName) {
        String sql = "select * from adminInfo where adminName=? and state!=?";
        return DBUtil.getSingleObject(sql,AdminInfo.class,adminName,Constant.EnumAdminState.删除.getValue());
    }

    /**
     * 服务端查看当前登陆用户的状态
     * @param adminName 用户名
     * @return 返回用户的状态
     */
    public String getAdminState(String adminName) {
        String sql="select state from admininfo where adminName=?";
        return DBUtil.getScalar(sql,adminName);
    }

    /**
     * 根据用户的账号和密码,查询用户 (用于修改密码前，检验用户旧密码是否输入正确)
     * @param adminName 用户名
     * @param oldPwd  密码
     * @return 返回null则证明数据不存在这个用户，否则存在
     */
    public AdminInfo checkAdmin(String adminName, String oldPwd) {
        String sql="select * from admininfo where adminName=? and password=?";
        return DBUtil.getSingleObject(sql,AdminInfo.class,adminName,oldPwd);
    }

    /**
     * 根据用户名修改用户的密码
     * @param adminName 用户名
     * @param newPwd  新密码
     * @return  返回1修改成功
     */
    public int updatePwd(String adminName, String newPwd) {
        String sql = "update admininfo set password=? where adminName=?";
        return DBUtil.update(sql, newPwd,adminName);
    }

    /**
     * 查询所有的管理员用户--不带分页的
     * @return 返回全部的用户
     */
    public List<AdminInfo> getAllAdmin() {
        String sql = "select * from admininfo";
        return DBUtil.getList(sql,AdminInfo.class);
    }

    /**
     * 根据用户名修改用户状态--加锁
     * @param adminName 用户名
     * @return 返回1修改成功
     */
    public int updateStateLock(String adminName) {
        String sql = "update admininfo set state=? where adminName=?";
        return DBUtil.update(sql,Constant.EnumAdminState.锁定.getValue(),adminName);
    }

    /**
     * 根据用户名修改状态--解锁
     * @param adminName 用户名
     * @return 返回1修改成功
     */
    public int updateStateUnLock(String adminName) {
        String sql = "update admininfo set state=? where adminName=?";
        return DBUtil.update(sql,Constant.EnumAdminState.正常.getValue(),adminName);
    }

    /**
     * 根据用户id查询用户
     * @param id 用户id
     * @return 返回Admininfo对象
     */
    public AdminInfo getAdmin(int id) {
        String sql = "select * from admininfo where id=?";
        return DBUtil.getSingleObject(sql,AdminInfo.class,id);
    }

    /**
     * 根据用户id修改信息
     * @param adminInfo 用户信息类
     * @param id 用户id
     * @return 返回1修改成功
     */
    public int updateAdmin(AdminInfo adminInfo, int id) {
        String sql = "update admininfo set adminName=?,password=?,note=? where id=?";
        return DBUtil.update(sql,adminInfo.getAdminName(),adminInfo.getPassword(),adminInfo.getNote(),id);
    }

    /**
     * 根据用户id删除用户
     * @param id 用户id
     * @return 返回1删除成功
     */
    public int delAdmin(int id) {
        String sql = "update admininfo set state=? where id=?";
        return DBUtil.update(sql,Constant.EnumAdminState.删除.getValue(),id);
    }
    /**
     * 删除多个用户
     */
    public void delAdmins(String[] id){
        for (String s:id){
            String sql="update admininfo set state=? where id=?";
            DBUtil.update(sql, Constant.EnumAdminState.删除.getValue(),s);  //这里只是修改用户的状态state为2表示禁用状态,没有真正的删除
        }
    }

    /**
     * 获取所有的管理员用户(带有分页)
     * @param page PageInfo类
     * @return 返回所有的管理员用户
     */
    public List<AdminInfo> getAdminList(PageInfo page){
        return DBUtil.getList("select * from adminInfo order by editDate desc limit ?, ? ",
                AdminInfo.class,page.getBeginRow(),page.getPageSize());
    }

    /**
     **     获取所有的管理员用户(带有分页和角色)
     *      * @param page PageInfo类
     *      * @return 返回所有的管理员用户
     */
    public List<AdminInfo> getAdminListRole(PageInfo page){
        return DBUtil.getList("select adminInfo.*,roleInfo.roleName from adminInfo left join roleInfo  on admininfo.roleid=roleinfo.id order by editDate desc limit ?, ? ",
                AdminInfo.class,page.getBeginRow(),page.getPageSize());
    }


    /**
     * 查询用户的总数
     * @return 用户总数
     */
    public int getAdminCount(){
        String sql = "select count(*) from admininfo";
        long count= DBUtil.getScalar(sql);  //这里数据库从得到的是long型，所以需要再转下
        return Integer.parseInt(count+"");
    }

    /**
     * 根据角色id查询角色名称
     * @param roleId 角色id
     * @return 角色名称
     */
    public String getRoleName(int roleId){
        String sql = "select roleName from roleinfo where id=?";
        return DBUtil.getScalar(sql,roleId);
    }


    /**
     * 修改用户角色
     * @param adminId 用户id
     * @param roleId 角色id
     */
    public int updateAdminRole(Integer adminId, Integer roleId) {
            String sql="update adminInfo set roleId=? where id=?";
           return DBUtil.update(sql,roleId,adminId);
    }
}
