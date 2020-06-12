package com.filter;

import com.beans.AdminInfo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter("/SessionFilter")
public class SessionFilter implements Filter {
    public void destroy() {
    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest)request;
        HttpSession session =req.getSession();
        HttpServletResponse hsp = (HttpServletResponse)response;
        AdminInfo admin=(AdminInfo)session.getAttribute("adminSession");
        if(admin!=null) {
            chain.doFilter(request, response);
        }
        else {
            request.setAttribute("msg","请先登录！");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

}
