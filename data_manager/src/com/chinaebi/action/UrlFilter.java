/*********************************************************************
 * 
 * Copyright (C) 2012, Shanghai Chinaebi
 * All rights reserved.
 * http://www.chinaebi.com.cn/
 * 
 *********************************************************************/
package com.chinaebi.action;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chinaebi.entity.Login;

/**
 * URL 过滤器
 * 
 * @author tangbiao
 * @version $Id: UrlFilter.java, v 0.1 2012-12-19 下午5:40:09 Administrator Exp $
 */
public class UrlFilter implements Filter {

    /**
     * Default constructor. 
     */
    public UrlFilter() {
    	
    }

    /**
     * @see Filter#destroy()
     */
    public void destroy() {
    	
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        String url = httpRequest.getRequestURI();
        String path = httpRequest.getContextPath();
        Login empRole = (Login) session.getAttribute("login");
        String method = httpRequest.getMethod();
        boolean flag = true;
        if (empRole == null && url.indexOf("login.do") == -1) {//empRole=null，url存在login.do 登录时不拦截
        	session.removeAttribute("login");
            httpResponse.sendRedirect(path);
            flag = false;
        } else if (method.equals("GET") && url.indexOf("login.do") != -1) {
        	session.removeAttribute("login");
            httpResponse.sendRedirect(path);
            flag = false;
        } else if (method.equals("GET")) {
            if (url.indexOf(".do") != -1) {
                //防止删除功能url攻击
            }
        } else {
            if (url.indexOf(".do") != -1 && url.indexOf("system") != -1) {
                httpResponse.sendRedirect(path);
                flag = false;
            }
        }
        if (flag) {
            chain.doFilter(httpRequest, httpResponse);
        }
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
        // TODO Auto-generated method stub
    }

}
