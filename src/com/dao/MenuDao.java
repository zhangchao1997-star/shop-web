package com.dao;

import com.beans.MenuInfo;
import com.jdbc.DBUtil;

import java.util.ArrayList;
import java.util.List;
/**
 * 菜单-dao层类
 */
public class MenuDao {
    /**
     * 根据角色,提取菜单数据
     * @return 菜单列表
     */
    public List<MenuInfo> getMenuListByRole(Integer roleId){
        //在rolemenu表中根据用户角色id得到菜单id列表
        String sql = "select menuId from rolemenu where roleId=?";
        List<Integer> menuIdList = DBUtil.getScalarColList(sql,roleId);
        List<MenuInfo> list = new ArrayList<>();
        for (int menuId:menuIdList){
            //在menuinfo表中获取其大菜单
            String sql1 = "select * from menuInfo where id=? and parentId=?";
            MenuInfo menuInfo = DBUtil.getSingleObject(sql1,MenuInfo.class,menuId,0);
            if(menuInfo!=null){
                list.add(menuInfo);
            }
        }
        for (MenuInfo m:list){
            if(m.getParentId()==0) {
                //根据大菜单查找对应的小菜单
                String sql2 = "select * from menuinfo where parentId=?";
                List<MenuInfo> menuInfoList = DBUtil.getList(sql2,MenuInfo.class,m.getId());
                m.setSubMenuList(menuInfoList);
            }
        }
        return list;
    }
}
