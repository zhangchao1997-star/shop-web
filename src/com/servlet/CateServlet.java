package com.servlet;

import com.beans.CateInfo;
import com.dao.CateDao;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/CateServlet.do")
public class CateServlet extends HttpServlet {
    CateDao cateDao = new CateDao();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String flag = request.getParameter("flag");
         if("validateFirForAdd".equals(flag)){
            validateFirForAdd(request,response);
        }
        else if("validateSecForAdd".equals(flag)){
            validateSecForAdd(request,response);
        }
        else if("addFir".equals(flag)){
            addFir(request,response);
        }
        else if("addSecond".equals(flag)){
            addSecond(request,response);
        }
        else if("manage".equals(flag)){
            manage(request,response);
        }
        else if("updateFir".equals(flag)){
            updateFir(request,response);
        }
        else if("updateSecond".equals(flag)){
            updateSecond(request,response);
        }
        else if("delSec".equals(flag)){
            delSec(request,response);
        }
        else if("delFir".equals(flag)){
            delFir(request,response);
        }
        else if("selSon".equals(flag)){
            selSon(request,response);
        }
    }

    private void validateSecForAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String className = request.getParameter("className");
        CateInfo cate=cateDao.getSecCateInfo(className);
        if(cate!=null){
            response.getWriter().print("-1");
        }
    }

    //添加一级分类时判断分类名是否重复
    private void validateFirForAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String className = request.getParameter("className");
        CateInfo cate=cateDao.getFirCateInfo(className);
        if(cate!=null){
            response.getWriter().print("-1");
        }
    }

    //关联查询
    private void selSon(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int parentId = Integer.parseInt(request.getParameter("parentId"));
        List<CateInfo> cateList = cateDao.getSecCate(parentId);
        JSONArray jsonObj= JSONArray.fromObject(cateList);
        response.getWriter().print(jsonObj);
    }

    //删除一级分类
    private void delFir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int result = 0;
        result = cateDao.delFir(id);
        if(result != 0){
            request.setAttribute("msg","删除成功");
            manage(request, response);
        }
    }

    //删除二级分类
    private void delSec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int result = cateDao.delSec(id);
        if(result == 1){
            request.setAttribute("msg","删除成功");
            manage(request, response);
        }
    }

    //二级分类修改
    private void updateSecond(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String secondClassName = request.getParameter("secondClassName");
        String des = request.getParameter("des");
        int id = Integer.parseInt(request.getParameter("id"));
        int result = cateDao.updateCate(secondClassName,des,id);
        if(result == 1){
            request.setAttribute("msg","二级分类修改成功");
            request.getRequestDispatcher("/goods/smallcate_update.jsp").forward(request,response);
        }
    }

    //修改一级分类
    private void updateFir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       String firstClassName =  request.getParameter("firstClassName");
       String des =  request.getParameter("des");
       int id = Integer.parseInt( request.getParameter("id"));
       int result = cateDao.updateCate(firstClassName,des,id);
       if(result == 1){
           request.setAttribute("msg","修改成功");
           request.getRequestDispatcher("/goods/bigcate_update.jsp").forward(request,response);
       }
    }

    //商品分类维护
    private void manage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CateInfo> cateList = cateDao.getAllCate(0);
        request.setAttribute("cateList",cateList);
        request.getRequestDispatcher("/goods/cate_manage.jsp").forward(request,response);
    }

    //添加对应一级分类下的二级分类
    private void addSecond(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String secondClassName = request.getParameter("secondClassName");
        int parentId = Integer.parseInt(request.getParameter("firName"));
        String note = request.getParameter("note");
        int result = cateDao.addSecond(secondClassName,note,parentId);
        if(result==1){
            request.setAttribute("msg","添加成功");
            request.getRequestDispatcher("/goods/smallcate_add.jsp").forward(request,response);
        }
    }

    //添加一级分类
    private void addFir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstClassName = request.getParameter("firstClassName");
        String note = request.getParameter("note");
        int result = cateDao.addFir(firstClassName,note);
        if(result==1){
            request.setAttribute("msg","添加成功");
            request.getRequestDispatcher("/goods/bigcate_add.jsp").forward(request,response);
        }
    }

}
