package com.servlet;

import com.beans.MemberInfo;
import com.beans.PageInfo;
import com.dao.MemberDao;
import com.utils.PageUtil;
import com.utils.StrUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/MemberServlet.do")
public class MemberServlet extends HttpServlet {
    MemberDao memberDao = new MemberDao();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
          String flag = request.getParameter("flag");
            if("delMore".equals(flag)){
                delMore(request,response);
            }
              else if("del".equals(flag)){
                  del(request,response);
              }
              else if("LookUp".equals(flag)){
                  LookUp(request,response);
              }
            else if("manage".equals(flag)){
                manage(request,response);
            }
    }
    //删除多个会员
    private void delMore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] memberIds = request.getParameterValues("ck_id");
        int result = 0;
        result = memberDao.delMembers(memberIds);
        if(result!=0){
            request.setAttribute("msg","删除成功");
            manage(request, response);
        }
    }

    //查看某个会员的详细信息
    private void LookUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        MemberInfo member = memberDao.getMemberById(id);
        request.setAttribute("member",member);
        request.getRequestDispatcher("/member/member_show.jsp").forward(request,response);
    }

    //删除一个会员
    private void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int result = memberDao.delMember(id);
        if(result == 1){
            request.setAttribute("msg","删除成功");
            manage(request, response);
        }
    }

    //会员管理
    private void manage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String memberNo =  request.getParameter("memberNo");
         memberNo =memberNo==null?null:memberNo.trim();
        String beginTime = request.getParameter("beginTime");
        String endTime = request.getParameter("endTime");
        int pageSize = 5;
        int rowCount = memberDao.getMemberCount(memberNo,beginTime,endTime);
        int pageIndex = 1;
        String pageIndexStr = request.getParameter("pageIndex");
        if(!StrUtil.isNullOrEmpty(pageIndexStr)){
            pageIndex = Integer.parseInt(pageIndexStr);
        }
        PageInfo page = PageUtil.getPageInfo(pageSize,rowCount,pageIndex);
        List<MemberInfo> memberList = memberDao.getMembers(memberNo,beginTime,endTime,page);
        request.setAttribute("memberList",memberList);
        request.setAttribute("page",page);
        request.getRequestDispatcher("/member/member_manage.jsp").forward(request,response);
    }
}
