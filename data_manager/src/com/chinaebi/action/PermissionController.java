package com.chinaebi.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinaebi.dao.FunctionRightDao;
import com.chinaebi.dao.FunctionRightLoginDao;
import com.chinaebi.dao.LoginDao;
import com.chinaebi.entity.FunctionRight;
import com.chinaebi.entity.FunctionRightLogin;
import com.chinaebi.utils.StringUtils;

@Controller
public class PermissionController {
	
	@Autowired
	@Qualifier(value = "loginDao")
	private LoginDao loginDao;
	@Autowired
	@Qualifier(value = "functionRightDao")
	private FunctionRightDao functionRightDao;
	@Autowired
	@Qualifier(value = "functionRightLoginDao")
	private FunctionRightLoginDao functionRightLoginDao;
	
	private static final String PERMISSION_UPDATE = "/operatorManager/update_permission";
	
	private static final String INITUPDATE = "/initUpdatePermission.do";
	private static final String INITPERMISSION = "/initPermission.do";
	private static final String UPDATEPERMISSION = "/updatePermission.do";
	/**
	 * 初始化修改
	 * @param model
	 * @return
	 */
	@RequestMapping(value = INITUPDATE)
	public String initUpdate(HttpSession session){
		session.setAttribute("logins", loginDao.queryAllLogins());
		return PERMISSION_UPDATE;
	}
	
	/**
	 * 初始化该系统操作员权限及所以权限
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = INITPERMISSION)
	public void initPermission(ServletRequest request,ServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		String id = request.getParameter("id");
		int loginId = Integer.parseInt(id.trim());
		List<FunctionRight> fList = functionRightDao.queryByLoginId(loginId);
		List<FunctionRight> fList2 = functionRightDao.queryAll();
		JSONArray json = new JSONArray();
		for(FunctionRight f2:fList2){			
			if(f2.getLevel() == 1){
								
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", f2.getId());
				map.put("text", f2.getFuncName());
				//判断操作员是否有该一级权限
				if(!f2.getUrl().equals("#")){
					out:for(FunctionRight f:fList){
						if(f2.getId() == f.getId()){
							map.put("checked", true);
							break out;
						}
					}
				}
				List<FunctionRight> fList3 = functionRightDao.queryByParentId(f2.getId());
				JSONArray json1 = new JSONArray();
				if(fList3 != null && fList3.size() > 0){
					for(FunctionRight f3:fList3){
						if(fList != null && fList.size() > 0){
							//判断操作员是否有该二级权限
							in:for(FunctionRight f:fList){
								if(f != null){
									if(f3.getId() == f.getId()){
										f3.setFlag(1);
										break in;
									}
								}
							}
						}
						Map<String, Object> map1 = new HashMap<String, Object>();
						map1.put("id", f3.getId());
						map1.put("text", f3.getFuncName());
						if(f3.getFlag() == 1)
							map1.put("checked", true);
						json1.add(map1);
					}
				}
				map.put("children", json1);								
				json.add(map);
			}
		}			
		
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
	}
	
	/**
	 * 修改系统操作员权限
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = UPDATEPERMISSION)
	public void updatePermission(ServletRequest request,ServletResponse response) throws IOException{
		String id = request.getParameter("id");
		int loginId = Integer.parseInt(id.trim());
	
		String funcId = request.getParameter("funcId");
		String[] funcIds = new String[funcId.length()];
		if(StringUtils.isNotBlank(funcId)){
			StringTokenizer st = new StringTokenizer(funcId,",");
			int i = 0;
			while (st.hasMoreElements()) {				
				funcIds[i] = st.nextToken();
				i ++;
			}
		}
		int flag = functionRightLoginDao.deleteByLoginId(loginId);//删除所有权限
		if(funcIds != null){			
			for(int i = 0;i < funcIds.length ;i ++){
				if(StringUtils.isNotBlank(funcIds[i])){
					FunctionRightLogin f = new FunctionRightLogin();
					f.setLoginId(loginId);
					f.setFuncId(Integer.parseInt(funcIds[i].trim()));
					flag = functionRightLoginDao.addFunctionRightLogin(f);
				}
			}
		}		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(flag);
		out.flush();
		out.close();
	}
}
