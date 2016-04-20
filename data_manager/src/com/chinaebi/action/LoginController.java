package com.chinaebi.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.chinaebi.entity.FunctionRight;
import com.chinaebi.entity.Login;
//import com.chinaebi.service.BusinessService;
import com.chinaebi.service.FunctionRightService;
import com.chinaebi.service.LoginService;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Controller
public class LoginController {
	
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	public static final String QUERY_LOGIN = "/operatorManager/query_login";
	private final String LOGIN_JSP = "/login";
	private final String MAIL_JSP = "/main";
	private final String SORRY_JSP = "/sorry";
	
	public static final String QUERYLOGIN = "/queryLogin.do";
	public static final String ADDLOGIN = "/addLogin.do";
	public static final String RESETPASSWORD = "/resetLoginPassword.do";
	public static final String OPENORCLOSE = "/openOrClose.do";
	private final String LOGIN = "/login.do";
	private final String EXIT = "/exit.do";
	private final String RESETCHINESENAME = "/resetChineseName.do";
	
	@Autowired
	@Qualifier(value = "loginService")
	private LoginService loginService;
//	@Autowired
//	@Qualifier(value = "businessService")
//	private BusinessService businessService;
	@Autowired
	@Qualifier(value = "functionRightService")
	private FunctionRightService functionRightService;
	
	@RequestMapping(value = LOGIN, method = RequestMethod.POST)
	public String login(HttpSession session, ServletRequest request,
			HttpServletResponse response, ModelMap model, SessionStatus status) {
		
		session.removeAttribute("login");
		session.removeAttribute("functionRightList");
		
		String userName = request.getParameter("id");
		String password = request.getParameter("passWord");
		String verifyCode = request.getParameter("verifyCode");

		String rightVerifyCode = (String) session.getAttribute("imgCode");
		if (rightVerifyCode == null || rightVerifyCode.equals("")) {
			model.addAttribute("loginerror", "验证码生成已过期，请刷新重新输入！");
			return LOGIN_JSP;
		}
		if (rightVerifyCode != null && !rightVerifyCode.equals(verifyCode)) {
			model.addAttribute("loginerror", "验证码输入错误，请重新输入！");
			return LOGIN_JSP;
		}
			
		Login login = loginService.checkLogin(userName, password);
		if (login == null) {
			model.addAttribute("loginerror", "用户名或密码错误,请重新登录 !");
			return LOGIN_JSP;
		}
		if(login.getStatus() != 1){
			model.addAttribute("loginerror", "该用户已关闭 !");
			return LOGIN_JSP;
		}
		List<FunctionRight> flist= functionRightService.queryByLoginId(login.getId());
		if(flist == null || flist.size() == 0)
			return SORRY_JSP;
		session.setAttribute("login", login);
		session.setAttribute("functionRightList", flist);
		loginService.updateLogin(login.getId()+"", null, null, null,new Date(),null);
//		session.setAttribute("business", businessService.queryAllBusiness());
		return MAIL_JSP;
	}
	
	/**
	 * 用户退出系统
	 * 
	 * @param session
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = EXIT)
	public String exit(HttpSession session, ServletRequest request,
			ModelMap model) {
		session.removeAttribute("login");
		session.removeAttribute("functionRightList");
		return LOGIN_JSP;
	}
	
	/**
	 * 查询系统操作员
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERYLOGIN)
	public String queryLogin(ServletRequest request,Model model){
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("numPerPage");
		
		String chineseName = request.getParameter("chineseName");
		String loginName = request.getParameter("loginName");
		
		Page<Login> page = new Page<Login>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if(StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else 
			page.setPageSize(10);
				
		map.put("chineseName", chineseName);
		map.put("loginName", loginName);
		
		model.addAttribute("pageLogin",loginService.queryPageLogin(chineseName, loginName, page));
		model.addAttribute("requestMap",map);
		return QUERY_LOGIN;
	}
	/**
	 * 添加系统操作员
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = ADDLOGIN)
	public void addLogin(ServletRequest request,ServletResponse response) 
			throws IOException{
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String loginName = request.getParameter("loginName");
		String password = request.getParameter("pwd");
		String chineseName = request.getParameter("chineseName");
		if(loginService.checkAddLogin(loginName) > 0)
			out.print("<script>alert('用户名已存在！');window.history.back(-1);</script>");
		else{
			int f = loginService.addLogin(loginName, password,chineseName);						
			if(f == 1)
				out.print("<script>alert('添加成功！');location='operatorManager/add_login.jsp'</script>");
			else 
				out.print("<script>alert('添加失败！');window.history.back(-1);</script>");		
		}
	}
	
	/**
	 * 修改密码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = RESETPASSWORD)
	@ResponseBody
	public int resetLoginPassword(ServletRequest request){
		String id = request.getParameter("id");
		String loginName = request.getParameter("loginName");
		String oldPwd = request.getParameter("oldPwd");
		String newPwd = request.getParameter("newPwd");
		String chineseName = request.getParameter("chineseName");
		int f = 0;
		if(loginService.checkLogin(loginName, oldPwd) == null)
			f = 2;
		else
			f = loginService.updateLogin(id, null, newPwd, null,null,chineseName);
		
		return f;
	}
	
	/**
	 * 修改状态
	 * @param request
	 * @return
	 */
	@RequestMapping(value = OPENORCLOSE)
	@ResponseBody
	public int openOrClose(ServletRequest request){
		String id = request.getParameter("id");
		String status = request.getParameter("status");
		int f = loginService.updateLogin(id, null, null, status,null,null);
		return f;
	}
	
	/**
	 * 修改中文名称
	 * @param request
	 * @return
	 */
	@RequestMapping(value = RESETCHINESENAME,method = RequestMethod.POST)
	@ResponseBody
	public int resetChineseName(HttpSession session,ServletRequest request){
		String id  = request.getParameter("id");
		String chineseName = request.getParameter("chineseName");
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(id)){
			map.put("id", id);
		}else{
			log.debug("主键ID  参数为空，返回失败");
			return 0;
		}
		
		if(StringUtils.isNotBlank(chineseName)){
			map.put("chineseName", chineseName);
		}else{
			log.debug("真实名称 chineseName   参数为空，返回失败");
			return 0;
		}
		
		int result = loginService.updateChineseName(map);
		
		if(result > 0){//更新成功
			Login login = (Login) session.getAttribute("login");
			
			if(StringUtils.isNotBlank(id)){
				if(Integer.valueOf(id) == login.getId()){//若修改的操作员为当前操作员，则更新session中保存的操作员对象
					login.setChineseName(chineseName);
//					session.removeAttribute("login");
					session.setAttribute("login", login);
				}
			}
		}
		
		return result;
				
	}
}
