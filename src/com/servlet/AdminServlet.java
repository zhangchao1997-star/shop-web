package com.servlet;

import com.beans.*;
import com.commons.Constant;
import com.dao.AdminDao;
import com.utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/AdminServlet.do")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    AdminDao dao = new AdminDao();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String flag = request.getParameter("flag");
        if("add".equals(flag)){
            addAdmin(request,response);
        }
        else if("validateAdmin".equals(flag)){
            validateAdmin(request,response);
        }
        else if("updatePwd".equals(flag)){
            updatePwd(request,response);
        }
        else if("logOut".equals(flag)){
            logOut(request,response);
        }
        else if("manage".equals(flag)){
            manage(request,response);
        }
        else if("lock".equals(flag)){
            lock(request,response);
        }
        else if("unlock".equals(flag)){
            unlock(request,response);
        }
        else if("beforeUpdate".equals(flag)){
            beforeUpdate(request,response);
        }
        else if("update".equals(flag)){
            update(request,response);
        }
        else if("del".equals(flag)){
            del(request,response);
        }
        else if("delMore".equals(flag)){
            delMore(request,response);
        }else if("refresh".equals(flag)){
            refresh(request, response);
        }
    }
    //返回当前系统时间
    private void refresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String date= new SimpleDateFormat("yyyy-MM-dd E a hh:mm").format(new java.util.Date());
        response.getWriter().print(date);
    }

    //删除多个用户
    private void delMore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         //拿到从前台传过来的所有管理员用户id
          String[] ids =  request.getParameterValues("ck_id");
          dao.delAdmins(ids);
          request.setAttribute("msg","删除成功");
          manage(request,response);
    }

    //删除用户
    private void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id")); //其实可以不用转的这里
        int result = dao.delAdmin(id);
        if(result == 1){
            request.setAttribute("msg","删除成功");
            manage(request, response);
        }
    }

    //修改用户信息
    private void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String adminName = request.getParameter("adminName");
        String password = request.getParameter("password");
        String note = request.getParameter("note");
        int id = Integer.parseInt(request.getParameter("id"));
        //根据用户id得到用户信息
        AdminInfo adminInfo = dao.getAdmin(id);
        //在原有的用户信息基础上进行修改
        adminInfo.setAdminName(adminName);
        adminInfo.setPassword(DesUtil.encStr(password));
        adminInfo.setNote(note);
        int result = dao.updateAdmin(adminInfo,id);
        AdminInfo admin = dao.getAdmin(id);
        admin.setPassword(DesUtil.desStr(admin.getPassword()));
        if(result == 1){
            request.setAttribute("msg","修改成功");
            request.setAttribute("admin",admin);
            request.getRequestDispatcher("/admin/admin_update.jsp").forward(request,response);
        }
    }

    //管理员信息修改前的操作
    private void beforeUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        //根据用户id得到用户原有信息，并返回前台
        AdminInfo adminInfo = dao.getAdmin(id);
        adminInfo.setPassword(DesUtil.desStr(adminInfo.getPassword())); //解密
        request.setAttribute("admin",adminInfo);
        request.getRequestDispatcher("/admin/admin_update.jsp").forward(request,response);
    }

    //给用户加锁
    private void lock(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String adminName = request.getParameter("adminName");
        //根据其用户名修改用户状态为0，表示暂停状态
        int result = dao.updateStateLock(adminName);
        if(result == 1){
            request.setAttribute("msg","加锁成功");
           manage(request, response);
        }
    }

    //给用户解锁
    private void unlock(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String adminName = request.getParameter("adminName");
        //根据其用户名修改用户状态为1，表示正常状态
        int result = dao.updateStateUnLock(adminName);
        if(result == 1){
            request.setAttribute("msg","解锁成功");
           manage(request,response);
        }
    }

    //用户管理
    private void manage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageSize = 6;
        //获取管理员总数
        int rowCount = dao.getAdminCount();
        int pageIndex = 1;
        //拿到前台传过来的当前页
        String pageIndexStr = request.getParameter("pageIndex");
        if(pageIndexStr!=null){
            pageIndex = Integer.parseInt(pageIndexStr);
        }
        PageInfo page = PageUtil.getPageInfo(pageSize,rowCount,pageIndex);

        List<AdminInfo> adminInfoList = dao.getAdminList(page);
        request.setAttribute("adminList",adminInfoList);
        request.setAttribute("page",page);
        request.getRequestDispatcher("/admin/admin_manage.jsp").forward(request,response);
    }
    //退出系统
    private void logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //清理session，并转向login.jsp登录页
        request.getSession().invalidate();
        request.getRequestDispatcher("/login.jsp").forward(request,response);
    }

    //修改密码
    private void updatePwd(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String adminName = request.getParameter("adminName");
        String oldPwd = request.getParameter("password");  //旧密码
        String newPwd = request.getParameter("newPwd");  //新密码
        String pwdconfirm = request.getParameter("pwdconfirm");  //确认密码
        //根据当前用户名与旧密码获取当前用户对象
        AdminInfo adminInfo = dao.checkAdmin(adminName,DesUtil.encStr(oldPwd));
        String reg = "[0-9a-zA-Z]{6,20}"; //新密码是6到20位大小写字母或数字
        //如果从数据库没查到这个用户则证明用户输入的旧密码有错误
        if(adminInfo==null){
            response.getWriter().print("旧密码输入有误,请检查后重试");  //f:用户的旧密码输入有误
        }
        else if(!newPwd.matches(reg)){
            response.getWriter().print("新密码格式错误");  //1：新密码格式错误
        }
        else if(!pwdconfirm.matches(reg)){
            response.getWriter().print("确认密码格式错误");  //2：确认密码格式错误
        }
        else if(!pwdconfirm.equals(newPwd)){
            response.getWriter().print("确认密码与新密码不一致");
        }
        else{
           int result= dao.updatePwd(adminName,DesUtil.encStr(newPwd));
           if(result == 1){
              response.getWriter().print("修改成功");
           }
        }
    }

    // 验证用户账号不能重复
    private void validateAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //从前台拿到注册用户用户名
        String adminName = request.getParameter("adminName");
        AdminInfo resultValidate = dao.validateAdminName(adminName);
        //如果数据库里已存在该用户则返回错误信息给前台
        if (resultValidate != null) {
            response.getWriter().print("该管理员用户已存在");
        }
    }

    //添加管理员用户
    private void addAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            String adminName = request.getParameter("adminName");
            String password = request.getParameter("password");
            String note = request.getParameter("note");

            AdminInfo adminInfo = new AdminInfo();
            adminInfo.setAdminName(adminName);
            adminInfo.setPassword(DesUtil.encStr(password)); //添加用户密码时加密
            adminInfo.setNote(note);
            adminInfo.setState(Constant.EnumAdminState.正常.getValue());  //1 代表是正常状态 用户默认是可用状态，没用枚举
            int result = dao.addAdmin(adminInfo);
            if (result == 1) {
                request.setAttribute("msg", "添加成功");
                request.getRequestDispatcher("/admin/admin_add.jsp").forward(request, response);
            }
        }
}
