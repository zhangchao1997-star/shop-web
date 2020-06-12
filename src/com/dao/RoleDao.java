package com.dao;

import com.beans.MenuInfo;
import com.beans.RoleInfo;
import com.jdbc.DBUtil;

import java.util.List;
/**
 * 角色-dao层类
 */
public class RoleDao {
    /**
     * 查询角色表中的全部角色信息
     * @return 返回一个list列表每个元素即为一个角色信息对象
     */
    public List<RoleInfo> getRoles(){
        String sql = "select * from roleinfo";
        return DBUtil.getList(sql,RoleInfo.class);
    }

    /**
     * 根据角色id查找该角色信息
     * @param id 角色id
     * @return 返回角色信息对象
     */
    public RoleInfo getRole(int id) {
        String sql = "select * from roleInfo where id=?";
        return DBUtil.getSingleObject(sql,RoleInfo.class,id);
    }

    /**
     * 根据角色id修改角色信息
     * @param id 角色id
     * @param roleName 角色名称
     * @param des 角色描述
     * @return 如果返回1修改成功，否则失败
     */
    public int updateRole(int id ,String roleName, String des) {
        String sql = "update roleInfo set roleName=?,des=? where id=?";
        return DBUtil.update(sql,roleName,des,id);
    }

    /**
     * 根据角色id删除该角色
     * @param id 角色id
     * @return 返回1删除成功,否则失败
     */
    public int delRole(int id) {
        String sql = "delete from roleInfo where id=?";
        return DBUtil.update(sql,id);
    }

    /**
     * 查询菜单
     * @return 返回菜单列表
     */
    public List<MenuInfo> rolePowerAssignment(int parentId) {
        String sql = "select * from menuinfo where parentId=?";
        List<MenuInfo> menuInfoList = DBUtil.getList(sql,MenuInfo.class,parentId);
        for (MenuInfo m:menuInfoList){
            if(m.getParentId()==0) {
                m.setSubMenuList( rolePowerAssignment( m.getId()));
            }
        }
        return menuInfoList;
    }

    /**
     * 给角色添减权限
     * 先删除旧的，再给角色添权限
     * @param roleId 角色id
     * @param menuId 菜单id
     * @return
     */
    public void updateRoleMenu(int roleId, String[] menuId) {
        DBUtil.update("delete from rolemenu where roleId=?",roleId);
        for (String s:menuId) {
            DBUtil.update("insert into rolemenu(roleId,menuId) values(?,?)",roleId,s);
        }
    }

    /**
     * 添加角色
     * @param role 角色实体类
     * @return 成功返回1
     */
    public int addRole(RoleInfo role) {
        String sql = "insert into roleinfo (roleName,des) values (?,?)";
        return DBUtil.update(sql, role.getRoleName(),role.getDes());
    }
    /**
     * 验证角色名在数据库存在
     * @param roleName 角色账号
     * @return 角色信息实体类
     */
    public RoleInfo getSingleRole(String roleName) {
        String sql = "select * from roleinfo where roleName=?";
        return DBUtil.getSingleObject(sql, RoleInfo.class, roleName);
    }

    /**
     * 在roleMenu表中将查到的menu拼起来
     * @param roleId 角色id
     * @return 拼接后的字符串
     */
    public String getMenuStr(Integer roleId) {
        String sql = "select menuId from roleMenu where roleId=?";
        List<Integer> menuIds = DBUtil.getScalarColList(sql,roleId);
        String result = "";
        for (Integer menuId:menuIds){
            result+=menuId+",";
        }
        return result;
    }

}
