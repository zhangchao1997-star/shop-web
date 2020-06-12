package com.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/CodeFilter")
public class CodeFilter implements Filter {
    //字符编码
    String encoding=null;

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
            //设置request字符编码
            request.setCharacterEncoding(encoding);
            //设置response字符编码
            response.setContentType("text/html;charset="+encoding);
            chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        //获取初始化参数
        encoding=filterConfig.getInitParameter("encoding");
    }

    public void destroy() {
        encoding=null;
    }

}