package com.servlet;

import com.beans.AdminInfo;
import com.beans.CateInfo;
import com.beans.GoodsInfo;
import com.beans.PageInfo;
import com.dao.CateDao;
import com.dao.GoodsDao;
import com.utils.PageUtil;
import com.utils.StrUtil;
import net.sf.json.JSONArray;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@WebServlet("/GoodsServlet.do")
@MultipartConfig //文件上传需要加上
public class GoodsServlet extends HttpServlet {
    GoodsDao goodsDao = new GoodsDao();
    CateDao cateDao = new CateDao();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String flag = request.getParameter("flag");
        if("checkGoodsName".equals(flag)){
            checkGoodsName(request,response);
        }
        else if("add".equals(flag)){
            add(request,response);
        }
        else if("manage".equals(flag)){
            manage(request,response);
        }
        else if("del".equals(flag)){
            del(request,response);
        }
        else if("forUpdate".equals(flag)){
            forUpdate(request,response);
        }else if("checkUpdate".equals(flag)){
            checkUpdate(request,response);
        }
        else if("update".equals(flag)){
            update(request,response);
        }
        else if("selSon".equals(flag)){
            selSon(request,response);
        }
        else if("delMore".equals(flag)){
            delMore(request, response);
        }
    }
    //根据一级菜单查找对应的二级菜单
    private void selSon(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer parentId=Integer.parseInt(request.getParameter("parentId"));
        //获取当前一级菜单的所有二级菜单
        List<CateInfo> cateList = cateDao.getSecCate(parentId);
        JSONArray jsonObj=JSONArray.fromObject(cateList);
        response.setContentType("text/html;charset=utf-8" );
        response.getWriter().print(jsonObj);
    }

    //根据一定条件查询商品
    private void manage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            //获取一级分类名称
            String select1 = request.getParameter("select1");
            //获取二级分类名称
            String select2 = request.getParameter("select2");
            //得到商品名
            String goodsName = request.getParameter("goodsName");
            goodsName = (goodsName==null)?null:goodsName.trim();//消掉可能存在的空格
            int pageSize = 4;
            int rowCount = goodsDao.getGoodsCountPage(select1, select2, goodsName);
            int pageIndex = 1;
            String pageIndexStr = request.getParameter("pageIndex");
            if (!StrUtil.isNullOrEmpty(pageIndexStr)) {
                pageIndex = Integer.parseInt(pageIndexStr);
            }
            PageInfo page = PageUtil.getPageInfo(pageSize, rowCount, pageIndex);
            List<GoodsInfo> goodsList = goodsDao.getGoods(select1, select2, goodsName, page);
            request.setAttribute("goodsList", goodsList);
            request.setAttribute("page", page);
            request.getRequestDispatcher("/goods/goods_manage.jsp").forward(request, response);
     }

    //删除多个商品
    private void delMore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String []ck_id = request.getParameterValues("ck_id");
        goodsDao.delMoreGoods(ck_id);
        request.setAttribute("msg","删除成功");
        manage(request, response);  //数据再进行回显在前台页面
    }

    //更新商品信息
    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));
        String goodsName = request.getParameter("goodsName");
        String unit = request.getParameter("unit");
        double price =Double.parseDouble( request.getParameter("price"));
        String producter = request.getParameter("producter");
        String des = request.getParameter("des");

        Integer bigCateId = Integer.parseInt(request.getParameter("select1"));
        Integer smallCateId = Integer.parseInt(request.getParameter("select2"));

        GoodsInfo goods = goodsDao.getGoodsById(id);

        goods.setGoodsName(goodsName);
        goods.setUnit(unit);
        goods.setPrice(price);
        goods.setProducter(producter);
        goods.setDes(des);
        goods.setBigCateId(bigCateId);
        goods.setSmallCateId(smallCateId);

        //获取图片数据
        Part part = request.getPart("picture");
        if(part.getSize()!=0){
            InputStream in = part.getInputStream();
            byte[] buff = new byte[in.available()];
            in.read(buff);
            goods.setPictureData(buff);
        }
        int result = goodsDao.updateGoods(goods);
        if(result==1){
            request.setAttribute("goods",goods);
            request.setAttribute("msg","修改成功");
            request.getRequestDispatcher("/goods/goods_update.jsp").forward(request,response);
        }

    }

    //修改商品时判断商品名是否重复
    private void checkUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String goodsName = request.getParameter("goodsName");
        String oldGoodsName = request.getParameter("oldGoodsName");
        if(oldGoodsName.equals(goodsName)){
            response.getWriter().print("0");
        }
        else{
            GoodsInfo goods=goodsDao.checkGoodsName(goodsName);
            if(goods!=null){
                response.getWriter().print("-1");
            }
        }

    }

    //更新商品信息前的数据回显
    private void forUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        GoodsInfo goods = goodsDao.getGoodsById(id);
        request.setAttribute("goods",goods);
        request.getRequestDispatcher("/goods/goods_update.jsp").forward(request,response);
    }

    //删除商品
    private void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int result = goodsDao.delGoods(id);
        if(result == 1){
            request.setAttribute("msg","删除成功");
            manage(request, response);
        }
    }

    //添加商品
    private void add(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String goodsName = request.getParameter("goodsName");
        String unit = request.getParameter("unit");
        double price =Double.parseDouble( request.getParameter("price"));
        String producter = request.getParameter("producter");
        String des = request.getParameter("des");

        Integer bigCateId = Integer.parseInt(request.getParameter("select1"));
        Integer smallCateId = Integer.parseInt(request.getParameter("select2"));
        //获取图片数据
        Part part = request.getPart("picture");
        InputStream in = part.getInputStream();
        byte[] buff = new byte[in.available()];
        in.read(buff);

        GoodsInfo goods = new GoodsInfo();
        goods.setGoodsName(goodsName);
        goods.setUnit(unit);
        goods.setPrice(price);
        goods.setProducter(producter);
        goods.setDes(des);
        goods.setPictureData(buff);

        goods.setBigCateId(bigCateId);
        goods.setSmallCateId(smallCateId);
        //用于前台回显图片
        int goodsId = goodsDao.addGoods(goods);
        goods.setId(goodsId);
        request.setAttribute("goods",goods);
        request.setAttribute("msg","添加成功");
        request.getRequestDispatcher("/goods/goods_add.jsp").forward(request,response);
    }

    //添加商品时检查商品名是否重复
    private void checkGoodsName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String goodsName = request.getParameter("goodsName");
        GoodsInfo goods=goodsDao.checkGoodsName(goodsName);
        if(goods!=null){
            response.getWriter().print("-1");
        }
    }
}
