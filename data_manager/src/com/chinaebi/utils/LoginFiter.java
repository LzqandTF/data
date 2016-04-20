package com.chinaebi.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

public class LoginFiter implements Filter{
	private FilterConfig config;
    private List<String> noCheckURIList = new ArrayList<String>();
     
     
    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
        this.config = null;
        this.noCheckURIList = null;
    }
 
    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest arg0, ServletResponse arg1,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)arg0;
        HttpServletResponse response = (HttpServletResponse)arg1;
        HttpSession session = request.getSession();
        Login user = (Login)session.getAttribute("login");
        String requestURI = request.getRequestURI();
         
        boolean flag =false;
        if(null == user){
            for(String str : noCheckURIList){
                if(requestURI.indexOf(str)!=-1){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                response.sendRedirect("login.jsp");
                return ;
            }
        }
        chain.doFilter(request, response);
    }
 
    /* (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig config) throws ServletException {
        this.config = config;
        String noCheckValue = config.getInitParameter("noCheckList");
        String[] arr = noCheckValue.split(";");
        noCheckURIList = Arrays.asList(arr);
    }
}
