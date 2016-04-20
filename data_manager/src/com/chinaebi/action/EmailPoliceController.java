package com.chinaebi.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaebi.entity.EmailPolice;
import com.chinaebi.entity.PoliceType;
import com.chinaebi.exception.SelectException;
import com.chinaebi.service.EmailPoliceService;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

/**
 * 邮件、短信报警
 * @author wufei
 *
 */
@Controller
public class EmailPoliceController {
	private static final Logger logger = LoggerFactory.getLogger(EmailPoliceController.class);
	
	private static final String QUERY_ALL = "queryPageEmailPolice.do";
	
	private static final String JSP_PAGE = "sysConfig/email_police";
	
	private static final String QUERY_ALL_BY_POLICE_ID = "queryAllByPoliceId.do";
	
	private static final String ADD_EMAIL_POLICE = "addEmailPolice.do";
	
	private static final String UPDATE_BY_ID = "updatePoliceId.do";
	
	private static final String DELETE_BY_ID = "deleteByPoliceId.do";
	
	private static final String QUERY_POLICE_TYPE = "getPoliceTypeList.do";
	
	@Autowired
	@Qualifier(value = "emailPoliceService")
	private EmailPoliceService emailPoliceService;
	
	/**
	 * 分页查询报警信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERY_ALL, method = RequestMethod.POST)
	public String queryPageEmailPolice(ServletRequest request, Model model) {
		logger.info("进入报警配置模块，查询报警配置信息...");
		try {
			String curPage = request.getParameter("pageNum");
			String pageSize = request.getParameter("pageSize");
			Page<EmailPolice> page = new Page<EmailPolice>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(10);
			String policeType = request.getParameter("policeType");
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(policeType)) {
				map.put("police_id", policeType);
			}
			model.addAttribute("pageEmailPolice", emailPoliceService.queryPageEmailPolice(page, map));
			model.addAttribute("policeType", policeType);
		} catch (Exception e) {
			logger.error("查询报警信息出错了：" + e.getMessage());
		}
		return JSP_PAGE;
	}
	
	/**
	 * 根据ID查询报警配置详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = QUERY_ALL_BY_POLICE_ID, method = RequestMethod.POST)
	@ResponseBody
	public List<EmailPolice> queryAllById(ServletRequest request) {
		List<EmailPolice> list = null;
		try {
			String policeId = request.getParameter("policeId");
			if (StringUtils.isNotBlank(policeId)) {
				list = emailPoliceService.queryAllByPoliceId(Integer.valueOf(policeId));
				if (list != null) {
					return list;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}
	
	/**
	 * 添加报警配置信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = ADD_EMAIL_POLICE, method = RequestMethod.POST)
	public void addEmailPolice(ServletRequest request, ServletResponse response) throws Exception {
		String police_id = request.getParameter("police_id");
		String data_type = request.getParameter("data_type");
		String email = request.getParameter("email");
		String email_remark = request.getParameter("email_remark");
		String email_theme = request.getParameter("email_theme");
		String phone = request.getParameter("phone");
		String email_content = request.getParameter("email_content");
		String phone_content = request.getParameter("phone_content");
		String phone_remark = request.getParameter("phone_remark");
		String[] str = email.split(",");
		String[] str1 = email_remark.split(",");
		String[] str2 = phone.split(",");
		String[] str3 = phone_remark.split(",");
		String[] str4 = data_type.split(",");
		int effectNum = 0;
		int flag = 0;
		if (str4.length == 1) {
			Map<String, Object> map = new HashMap<String, Object>();
			if (Integer.valueOf(str4[0]) == 1) {
				EmailPolice emailPolice = new EmailPolice();
				for(int i = 0; i < str.length; i++) {
					if (StringUtils.isNotBlank(police_id)) {
						emailPolice.setPolice_id(Integer.valueOf(police_id));
						map.put("police_id", police_id);
					}
					if (StringUtils.isNotBlank(str4[0])) {
						emailPolice.setData_type(Integer.valueOf(str4[0]));
					}
					if (StringUtils.isNotBlank(str[i])) {
						emailPolice.setEmail(str[i]);
						map.put("email", str[i]);
					}
					if (StringUtils.isNotBlank(str1[i])) {
						emailPolice.setEmail_remark(str1[i]);
					}
					if (StringUtils.isNotBlank(email_theme)) {
						emailPolice.setEmail_theme(email_theme);
					}
					if (StringUtils.isNotBlank(email_content)) {
						emailPolice.setEmail_content(email_content);
					}
					flag = emailPoliceService.queryEmailOrPhone(map);
					if (flag == 0) {
						effectNum = emailPoliceService.addEmailPolice(emailPolice);
					}
				}
			} else {
				EmailPolice emailPolice = new EmailPolice();
				for(int i = 0; i < str2.length; i++) {
					if (StringUtils.isNotBlank(police_id)) {
						emailPolice.setPolice_id(Integer.valueOf(police_id));
						map.put("police_id", police_id);
					}
					if (StringUtils.isNotBlank(str4[0])) {
						emailPolice.setData_type(Integer.valueOf(str4[0]));
					}
					if (StringUtils.isNotBlank(str2[i])) {
						emailPolice.setPhone(str2[i]);
						map.put("phone", str2[i]);
					}
					if (StringUtils.isNotBlank(phone_content)) {
						emailPolice.setPhone_content(phone_content);
					}
					if (StringUtils.isNotBlank(str3[i])) {
						emailPolice.setPhone_remark(str3[i]);
					}
					flag = emailPoliceService.queryEmailOrPhone(map);
					if (flag == 0) {
						effectNum = emailPoliceService.addEmailPolice(emailPolice);
					}
				}
			}
		} else {
			Map<String, Object> map1 = new HashMap<String, Object>();
			if (Integer.valueOf(str4[0]) == 1) {
				EmailPolice emailPolice = new EmailPolice();
				for(int i = 0; i < str.length; i++) {
					if (StringUtils.isNotBlank(police_id)) {
						emailPolice.setPolice_id(Integer.valueOf(police_id));
						map1.put("police_id", police_id);
					}
					if (StringUtils.isNotBlank(str4[0])) {
						emailPolice.setData_type(Integer.valueOf(str4[0]));
					}
					if (StringUtils.isNotBlank(str[i])) {
						emailPolice.setEmail(str[i]);
						map1.put("email", str[i]);
					}
					if (StringUtils.isNotBlank(str1[i])) {
						emailPolice.setEmail_remark(str1[i]);
					}
					if (StringUtils.isNotBlank(email_theme)) {
						emailPolice.setEmail_theme(email_theme);
					}
					if (StringUtils.isNotBlank(email_content)) {
						emailPolice.setEmail_content(email_content);
					}
					flag = emailPoliceService.queryEmailOrPhone(map1);
					if (flag == 0) {
						effectNum = emailPoliceService.addEmailPolice(emailPolice);
					}
				}
			} else {
				EmailPolice emailPolice = new EmailPolice();
				for(int i = 0; i < str2.length; i++) {
					if (StringUtils.isNotBlank(police_id)) {
						emailPolice.setPolice_id(Integer.valueOf(police_id));
						map1.put("police_id", police_id);
					}
					if (StringUtils.isNotBlank(str4[0])) {
						emailPolice.setData_type(Integer.valueOf(str4[0]));
					}
					if (StringUtils.isNotBlank(str2[i])) {
						emailPolice.setPhone(str2[i]);
						map1.put("phone", str2[i]);
					}
					if (StringUtils.isNotBlank(phone_content)) {
						emailPolice.setPhone_content(phone_content);
					}
					if (StringUtils.isNotBlank(str3[i])) {
						emailPolice.setPhone_remark(str3[i]);
					}
					flag = emailPoliceService.queryEmailOrPhone(map1);
					if (flag == 0) {
						effectNum = emailPoliceService.addEmailPolice(emailPolice);
					}
				}
			}
			Map<String, Object> map2 = new HashMap<String, Object>();
			if (Integer.valueOf(str4[1]) == 1) {
				EmailPolice emailPolice = new EmailPolice();
				for(int i = 0; i < str.length; i++) {
					if (StringUtils.isNotBlank(police_id)) {
						emailPolice.setPolice_id(Integer.valueOf(police_id));
						map2.put("police_id", police_id);
					}
					if (StringUtils.isNotBlank(str4[1])) {
						emailPolice.setData_type(Integer.valueOf(str4[1]));
					}
					if (StringUtils.isNotBlank(str[i])) {
						emailPolice.setEmail(str[i]);
						map2.put("email", str[i]);
					}
					if (StringUtils.isNotBlank(str1[i])) {
						emailPolice.setEmail_remark(str1[i]);
					}
					if (StringUtils.isNotBlank(email_theme)) {
						emailPolice.setEmail_theme(email_theme);
					}
					if (StringUtils.isNotBlank(email_content)) {
						emailPolice.setEmail_content(email_content);
					}
					flag = emailPoliceService.queryEmailOrPhone(map2);
					if (flag == 0) {
						effectNum = emailPoliceService.addEmailPolice(emailPolice);
					}
				}
			} else {
				EmailPolice emailPolice = new EmailPolice();
				for(int i = 0; i < str2.length; i++) {
					if (StringUtils.isNotBlank(police_id)) {
						emailPolice.setPolice_id(Integer.valueOf(police_id));
						map2.put("police_id", police_id);
					}
					if (StringUtils.isNotBlank(str4[1])) {
						emailPolice.setData_type(Integer.valueOf(str4[1]));
					}
					if (StringUtils.isNotBlank(str2[i])) {
						emailPolice.setPhone(str2[i]);
						map2.put("phone", str2[i]);
					}
					if (StringUtils.isNotBlank(phone_content)) {
						emailPolice.setPhone_content(phone_content);
					}
					if (StringUtils.isNotBlank(str3[i])) {
						emailPolice.setPhone_remark(str3[i]);
					}
					flag = emailPoliceService.queryEmailOrPhone(map2);
					if (flag == 0) {
						effectNum = emailPoliceService.addEmailPolice(emailPolice);
					}
				}
			}
		}
		PrintWriter out = response.getWriter();
		out.print(effectNum);
		out.flush();
		out.close();
	}
	
	/**
	 * 根据id修改
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = UPDATE_BY_ID, method = RequestMethod.POST)
	public void updateById(ServletRequest request, ServletResponse response) throws Exception {
		String email_id = request.getParameter("email_id");
		String police_id = request.getParameter("police_id");
		String data_type = request.getParameter("data_type");
		String email = request.getParameter("email");
		String email_remark = request.getParameter("email_remark");
		String email_theme = request.getParameter("email_theme");
		String email_content = request.getParameter("email_content");
		String phone = request.getParameter("phone");
		String phone_remark = request.getParameter("phone_remark");
		String phone_content = request.getParameter("phone_content");
		String[] str = email_id.split(",");
		String[] str1 = email.split(",");
		String[] str2 = email_remark.split(",");
		String[] str3 = phone.split(",");
		String[] str4 = phone_remark.split(",");
		String[] str5 = data_type.split(",");
		int flag = 0;
		int effectNum = 0;
		for(int i = 0; i < str.length; i++) {
			emailPoliceService.deleteEmailPolice(Integer.valueOf(str[i]));
		}
		if(str5.length == 1) {
			Map<String, Object> map = new HashMap<String, Object>();
			if (Integer.valueOf(str5[0]) == 1) {
				EmailPolice emailPolice = new EmailPolice();
				for(int i = 0; i < str1.length; i++) {
					if (StringUtils.isNotBlank(police_id)) {
						emailPolice.setPolice_id(Integer.valueOf(police_id));
						map.put("police_id", police_id);
					}
					if (StringUtils.isNotBlank(str5[0])) {
						emailPolice.setData_type(Integer.valueOf(str5[0]));
					}
					if (StringUtils.isNotBlank(str1[i])) {
						emailPolice.setEmail(str1[i]);
						map.put("email", str1[i]);
					}
					if (StringUtils.isNotBlank(str2[i])) {
						emailPolice.setEmail_remark(str2[i]);
					}
					if (StringUtils.isNotBlank(email_theme)) {
						emailPolice.setEmail_theme(email_theme);
					}
					if (StringUtils.isNotBlank(email_content)) {
						emailPolice.setEmail_content(email_content);
					}
					flag = emailPoliceService.queryEmailOrPhone(map);
					if (flag == 0) {
						effectNum = emailPoliceService.addEmailPolice(emailPolice);
					}
				}
			} else {
				EmailPolice emailPolice = new EmailPolice();
				for(int i = 0; i < str5.length; i++) {
					if (StringUtils.isNotBlank(police_id)) {
						emailPolice.setPolice_id(Integer.valueOf(police_id));
						map.put("police_id", police_id);
					}
					if (StringUtils.isNotBlank(str5[0])) {
						emailPolice.setData_type(Integer.valueOf(str5[0]));
					}
					if (StringUtils.isNotBlank(str3[i])) {
						emailPolice.setPhone(str3[i]);
						map.put("phone", str3[i]);
					}
					if (StringUtils.isNotBlank(phone_content)) {
						emailPolice.setPhone_content(phone_content);
					}
					if (StringUtils.isNotBlank(str4[i])) {
						emailPolice.setPhone_remark(str4[i]);
					}
					flag = emailPoliceService.queryEmailOrPhone(map);
					if (flag == 0) {
						effectNum = emailPoliceService.addEmailPolice(emailPolice);
					}
				}
			}
		} else {
			Map<String, Object> map1 = new HashMap<String, Object>();
			if (Integer.valueOf(str5[0]) == 1) {
				EmailPolice emailPolice = new EmailPolice();
				for(int i = 0; i < str1.length; i++) {
					if (StringUtils.isNotBlank(police_id)) {
						emailPolice.setPolice_id(Integer.valueOf(police_id));
						map1.put("police_id", police_id);
					}
					if (StringUtils.isNotBlank(str5[0])) {
						emailPolice.setData_type(Integer.valueOf(str5[0]));
					}
					if (StringUtils.isNotBlank(str1[i])) {
						emailPolice.setEmail(str1[i]);
						map1.put("email", str1[i]);
					}
					if (StringUtils.isNotBlank(str2[i])) {
						emailPolice.setEmail_remark(str2[i]);
					}
					if (StringUtils.isNotBlank(email_theme)) {
						emailPolice.setEmail_theme(email_theme);
					}
					if (StringUtils.isNotBlank(email_content)) {
						emailPolice.setEmail_content(email_content);
					}
					flag = emailPoliceService.queryEmailOrPhone(map1);
					if (flag == 0) {
						effectNum = emailPoliceService.addEmailPolice(emailPolice);
					}
				}
			} else {
				EmailPolice emailPolice = new EmailPolice();
				for(int i = 0; i < str5.length; i++) {
					if (StringUtils.isNotBlank(police_id)) {
						emailPolice.setPolice_id(Integer.valueOf(police_id));
						map1.put("police_id", police_id);
					}
					if (StringUtils.isNotBlank(str5[0])) {
						emailPolice.setData_type(Integer.valueOf(str5[0]));
					}
					if (StringUtils.isNotBlank(str3[i])) {
						emailPolice.setPhone(str3[i]);
						map1.put("phone", str3[i]);
					}
					if (StringUtils.isNotBlank(phone_content)) {
						emailPolice.setPhone_content(phone_content);
					}
					if (StringUtils.isNotBlank(str5[i])) {
						emailPolice.setPhone_remark(str5[i]);
					}
					flag = emailPoliceService.queryEmailOrPhone(map1);
					if (flag == 0) {
						effectNum = emailPoliceService.addEmailPolice(emailPolice);
					}
				}
			}
			Map<String, Object> map2 = new HashMap<String, Object>();
			if (Integer.valueOf(str5[1]) == 1) {
				EmailPolice emailPolice = new EmailPolice();
				for(int i = 0; i < str.length; i++) {
					if (StringUtils.isNotBlank(police_id)) {
						emailPolice.setPolice_id(Integer.valueOf(police_id));
						map2.put("police_id", police_id);
					}
					if (StringUtils.isNotBlank(str5[1])) {
						emailPolice.setData_type(Integer.valueOf(str5[1]));
					}
					if (StringUtils.isNotBlank(str1[i])) {
						emailPolice.setEmail(str1[i]);
						map2.put("email", str1[i]);
					}
					if (StringUtils.isNotBlank(str2[i])) {
						emailPolice.setEmail_remark(str2[i]);
					}
					if (StringUtils.isNotBlank(email_theme)) {
						emailPolice.setEmail_theme(email_theme);
					}
					if (StringUtils.isNotBlank(email_content)) {
						emailPolice.setEmail_content(email_content);
					}
					flag = emailPoliceService.queryEmailOrPhone(map2);
					if (flag == 0) {
						effectNum = emailPoliceService.addEmailPolice(emailPolice);
					}
				}
			} else {
				EmailPolice emailPolice = new EmailPolice();
				for(int i = 0; i < str3.length; i++) {
					if (StringUtils.isNotBlank(police_id)) {
						emailPolice.setPolice_id(Integer.valueOf(police_id));
						map2.put("police_id", police_id);
					}
					if (StringUtils.isNotBlank(str5[1])) {
						emailPolice.setData_type(Integer.valueOf(str5[1]));
					}
					if (StringUtils.isNotBlank(str3[i])) {
						emailPolice.setPhone(str3[i]);
						map2.put("phone", str3[i]);
					}
					if (StringUtils.isNotBlank(phone_content)) {
						emailPolice.setPhone_content(phone_content);
					}
					if (StringUtils.isNotBlank(str4[i])) {
						emailPolice.setPhone_remark(str4[i]);
					}
					flag = emailPoliceService.queryEmailOrPhone(map2);
					if (flag == 0) {
						effectNum = emailPoliceService.addEmailPolice(emailPolice);
					}
				}
			}
		}
		PrintWriter out = response.getWriter();
		out.print(effectNum);
		out.flush();
		out.close();
	}
	
	/**
	 * 根据ID删除报警信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = DELETE_BY_ID, method = RequestMethod.POST)
	public void deleteById(ServletRequest request, ServletResponse response) throws Exception {
		String policeId = request.getParameter("policeId");
		List<EmailPolice> list = null;
		int effectNum = 0;
		if (StringUtils.isNotBlank(policeId)) {
			list = emailPoliceService.queryAllByPoliceId(Integer.valueOf(policeId));
			if (list != null) {
				for (EmailPolice emailPolice : list) {
					effectNum = emailPoliceService.deleteEmailPolice(emailPolice.getEmail_id());
				}
				PrintWriter out = response.getWriter();
				out.print(effectNum);
				out.flush();
				out.close();
			}
		}
	}
	
	/**
	 * 查询报警类型列表
	 * @return
	 */
	@RequestMapping(value = QUERY_POLICE_TYPE)
	@ResponseBody
	public List<PoliceType> getPoliceTypeList() {
		List<PoliceType> list = null;
		try {
			list = emailPoliceService.queryAll();
			if (list == null) {
				throw new SelectException("emailPoliceService.queryAll()  查询结果为NULL");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}
}
