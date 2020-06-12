package com.servlet;

import com.beans.AdminInfo;
import com.beans.MenuInfo;
import com.beans.RoleInfo;
import com.dao.RoleDao;
import com.beans.PageInfo;
import com.dao.AdminDao;
import com.utils.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/RoleServlet.do")
public class RoleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    RoleDao dao = new RoleDao();
    AdminDao adminDao = new AdminDao();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String flag = request.getParameter("flag");
        if("manage".equals(flag)){
            manage(request,response);
        }
        else if("forUpdate".equals(flag)){
            forUpdate(request,response);
        }
        else if("update".equals(flag)){
            update(request,response);
        }
        else if("del".equals(flag)){
            del(request,response);
        }
        else if("rolePowerAssignment".equals(flag)){
            rolePowerAssignment(request,response);
        }
        else if("updateRoleMenu".equals(flag)){
            updateRoleMenu(request,response);
        }
        else if("listAdmin".equals(flag)){
            listAdmin(request,response);
        }
        else if("adminRole".equals(flag)){
            adminRole(request,response);
        }
        else if("updateAdminRole".equals(flag)){
            updateAdminRole(request, response);
        }
        else if("add".equals(flag)){
            add(request,response);
        }
        else if("validate".equals(flag)) {
            validate(request,response);
        }
        else if("validateForAdd".equals(flag)){
            validateForAdd(request, response);
        }
    }
    //验证角色名是否重复(角色添加)
    private void validateForAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String roleName = request.getParameter("roleName");
        RoleInfo role = dao.getSingleRole(roleName);
        if(role != null) {
            response.getWriter().print("-1");
        }
    }

    //验证角色名是否重复(角色修改)
    private void validate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String roleName = request.getParameter("roleName"); //新的角色名
        RoleInfo role = dao.getSingleRole(roleName);
        int roleId = Integer.parseInt(request.getParameter("id"));
        String name = dao.getRole(roleId).getRoleName();
        if(roleName.equals(name)){ //如果新的用户名和当前待修改的用户名相同,返回正确信息
            response.getWriter().print("0");
        }
        else {   //如果新的角色名与其他角色名冲突，返回错误信息
            if(role!=null){
                response.getWriter().print("-1");
            }
        }
    }

    //创建用户
    private void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roleName = request.getParameter("roleName");
        String des = request.getParameter("des");

        RoleInfo role = new RoleInfo();
        role.setRoleName(roleName);
        role.setDes(des);

        int result = dao.addRole(role);
        if(result==1) {
            request.setAttribute("msg", "角色创建成功");
            request.getRequestDispatcher("/role/role_add.jsp").forward(request, response);
        }
    }

    //更新用户角色
    private void updateAdminRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer adminId=Integer.parseInt(request.getParameter("id"));
        Integer roleId=Integer.parseInt(request.getParameter("roleId"));
        int result = adminDao.updateAdminRole(adminId,roleId);
        if(result == 1){
            request.setAttribute("msg","角色更新成功");
            adminRole(request, response);
        }
    }

    //转向用户角色表
    private void adminRole(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id=Integer.parseInt(request.getParameter("id"));
        AdminInfo admin=adminDao.getAdmin(id);
        List<RoleInfo> roleList=dao.getRoles();
        if(roleList!=null){
            request.setAttribute("admin",admin);
            request.setAttribute("roleList",roleList);
            request.getRequestDispatcher("/role/admin_role.jsp").forward(request,response);
        }

    }
    //查询出所有的用户角色
    private void listAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageIndex = 1;
        int pageSize = 6;
        String pageIndexStr = request.getParameter("pageIndex");
        int rowCount=adminDao.getAdminCount();
        if (!StrUtil.isNullOrEmpty(pageIndexStr)) {
            pageIndex = Integer.parseInt(pageIndexStr);
        }
        PageInfo page = PageUtil.getPageInfo(pageSize, rowCount, pageIndex);
        List<AdminInfo> adminList= adminDao.getAdminListRole(page);
        if(adminList!=null){
            request.setAttribute("adminList",adminList);
            request.setAttribute("pageInfo",page);
            request.getRequestDispatcher("/role/admin_list.jsp").forward(request, response);
        }

    }

    //更新角色权限
    private void updateRoleMenu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer roleId = Integer.parseInt(request.getParameter("roleId"));
        String [] menu = request.getParameterValues("menu");
        dao.updateRoleMenu(roleId,menu);
        request.setAttribute("msg","保存成功");
        rolePowerAssignment(request,response);
    }

    //角色权限分配
    private void rolePowerAssignment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer roleId = Integer.parseInt(request.getParameter("roleId"));
        List<MenuInfo> menuList = dao.rolePowerAssignment(0); //查询父级菜单
        request.setAttribute("roleId",roleId);
        String Str = dao.getMenuStr(roleId);
        request.setAttribute("menuStr",Str);
        request.setAttribute("menuList",menuList);
        RoleInfo role = dao.getRole(roleId);
        request.setAttribute("roleInfo",role);
        request.getRequestDispatcher("/role/role_rolePowerAssignment.jsp").forward(request,response);
    }

    //根据角色id删除该角色
    private void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int result = dao.delRole(id);
        if(result == 1){
            request.setAttribute("msg","删除成功");
            manage(request,response);
        }
    }

    //角色信息更新
    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String roleName = request.getParameter("roleName");
        String des = request.getParameter("des");
        int id = Integer.parseInt(request.getParameter("id"));
        int result = dao.updateRole(id,roleName,des);
        RoleInfo role = dao.getRole(id);
        if(result == 1){
            request.setAttribute("msg","角色更新成功");
            request.setAttribute("role",role);
            request.getRequestDispatcher("/role/role_update.jsp").forward(request,response);
        }
    }

    //角色更新前的数据回显
    private void forUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        RoleInfo roleInfo = dao.getRole(id);
        request.setAttribute("role",roleInfo);
        request.getRequestDispatcher("/role/role_update.jsp").forward(request,response);
    }

    //权限管理下的角色管理
    private void manage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<RoleInfo> roleList = dao.getRoles();
        request.setAttribute("roleList",roleList);
        request.getRequestDispatcher("/role/role_manage.jsp").forward(request,response);
    }
}
