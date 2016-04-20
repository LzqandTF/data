package com.chinaebi.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaebi.entity.InstInfo;
import com.chinaebi.reload.DataManagerInit;
import com.chinaebi.service.BankInstService;
import com.chinaebi.service.InstInfoService;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Controller
public class InstInfoController {

	public static final String INST_INFO = "/sysConfig/inst_info";
	public static final String IGNORE_ERROR = "/sysConfig/ignore_error";
	
	public static final String QUERYINSTINFO = "/queryInstInfo.do";
	
	// 添加渠道信息
	public static final String ADDINSTINFO = "/addInstInfo.do";
	
	public static final String QUERYALL = "/queryAll.do";
	
	// 修改渠道信息
	public static final String UPDATEINSTINFO = "/updateInstInfo.do";
	
	public static final String DELETEINSTINFO = "/deleteInstInfo.do";
	public static final String LOCKORACTIVATEINSTINFO = "/lockOrActivateInstInfo.do";
	public static final String QUERYINSTINFOIGNOREERROR = "/queryInstInfoIgnoreError.do";
	public static final String UPDATEINSTWHETHERIGNOREERROR = "/updateInstWhetherIgnoreError.do";
	public static final String CHECKINSTIDREPEATORNOT = "checkInstIdRepeatOrNot.do";
	
	@Autowired
	@Qualifier(value = "instInfoService")
	private InstInfoService instInfoService;
	
	//从内存中加载各种配置列表
	@Autowired
	@Qualifier(value="dataManagerInit")
	private DataManagerInit dataManagerInit;
	
	@Autowired
	@Qualifier(value = "bankInstService")
	private BankInstService bankInstService;

	protected Log log = LogFactory.getLog(getClass());
	
	/**
	 * 查询所有机构信息
	 */
	@RequestMapping(value = QUERYALL,method = RequestMethod.POST)
	public void queryAll(ServletResponse response) throws Exception{
		List<InstInfo> list = instInfoService.queryAll();
		StringBuffer buffer = new StringBuffer();
		if(list.size() > 0){
			buffer.append("[");
			for (InstInfo instInfo : list) {
				buffer.append("{");
				buffer.append("instId : "+instInfo.getInstId()+",");
				buffer.append("name : '"+instInfo.getName()+"'");
				buffer.append("},");
			}
			buffer.append("]");
			PrintWriter out = response.getWriter();
			out.print(buffer.toString().replaceAll("},]", "}]"));
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 查询机构信息配置分页
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERYINSTINFO)
	public String queryInstInfo(ServletRequest request,Model model) throws Exception{
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String instId = request.getParameter("id");
		String name = request.getParameter("name_select");
		String active = request.getParameter("active_select");
		String instType = request.getParameter("type_select");
		String bankId = request.getParameter("bank_select");

		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(instId)){
			map.put("instId", Integer.valueOf(instId));
		}
		if(StringUtils.isNotBlank(name)){
			map.put("name", name);
		}
		if(StringUtils.isNotBlank(active)){
			map.put("active", Integer.valueOf(active));
		}
		if (StringUtils.isNotBlank(instType)) {
			map.put("instType", Integer.valueOf(instType));
		}
		if (StringUtils.isNotBlank(bankId)) {
			map.put("bankId", bankId);
		}
		
		Page<InstInfo> page = new Page<InstInfo>();
		if(StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if(StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else 
			page.setPageSize(10);
				
		model.addAttribute("pageInstInfo",instInfoService.queryPageInstInfo(page,map));
		model.addAttribute("bankInstLst", bankInstService.queryAllBankInst());
		model.addAttribute("active",active);
		model.addAttribute("instType", instType);
		model.addAttribute("bankId", bankId);
		model.addAttribute("pageSize", pageSize);
		return INST_INFO;
	}
	/**
	 * 添加系统操作员
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = ADDINSTINFO,method = RequestMethod.POST)
	@ResponseBody
	public int addInstInfo(InstInfo instInfo,ServletRequest request) throws Exception{
		int f = instInfoService.addInstInfo(instInfo);
		log.info("添加渠道成功");
		//更新后台与管理平台内存中渠道信息
		if (f > 0) {
			boolean result = instInfoService.updateRamDeductChannelInfo(instInfo.getInstId(),instInfo.getInst_type());
			if(result){
				log.info("更新后台渠道"+instInfo.getName()+"信息成功");
			}else{
				log.info("更新后台渠道"+instInfo.getName()+"信息失败");
			}
			dataManagerInit.setInstInfoMap(instInfo.getInstId(), instInfo.getInst_type(),instInfo);
		}
		return f;
	}
	
	@RequestMapping(value = UPDATEINSTINFO,method = RequestMethod.POST)
	public void updateInstInfo(ServletRequest request,ServletResponse response,InstInfo instInfo) throws Exception {
		log.info("进入修改渠道配置信息方法");
		boolean flag = false;
		try{
			if(instInfo != null){
				int effectNum = instInfoService.updateInstInfo(instInfo);
				if(effectNum > 0){
					flag = true;
					
					//更新后台与管理平台内存中渠道信息
					InstInfo info = instInfoService.queryInstInfoByInstId(instInfo.getInstId(),instInfo.getInst_type());
					
					boolean result = instInfoService.updateRamDeductChannelInfo(instInfo.getInstId(),instInfo.getInst_type());
					if(result){
						log.info("更新后台渠道"+instInfo.getName()+"信息成功");
					}else{
						log.info("更新后台渠道"+instInfo.getName()+"信息失败");
					}
					
					if (info != null) {
						dataManagerInit.setInstInfoMap(instInfo.getInstId(), instInfo.getInst_type(),info);
					}
					log.info("修改渠道配置信息成功");
				}else{
					log.info("修改渠道配置信息失败");
				}
				PrintWriter out = response.getWriter();
				out.print(flag);
				out.flush();
				out.close();
			}else{
				log.info("渠道ID为NULL");
			}
		}catch(Exception e){
			log.error(e);
		}
	}
	/**
	 * 禁用或者开通扣款渠道
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value=LOCKORACTIVATEINSTINFO,method = RequestMethod.POST)
	public void lockOrActivateInstInfo(ServletRequest request,ServletResponse response) throws Exception{
		String instId = request.getParameter("inst_id");
		String active = request.getParameter("active");
		String inst_type = request.getParameter("inst_type");
		boolean flag = false;
		if(StringUtils.isNotBlank(instId) && StringUtils.isNotBlank(inst_type)){
			if(StringUtils.isNotBlank(active)){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("instId", instId);
				map.put("inst_type", inst_type);
				map.put("active", 1-Integer.valueOf(active));
				flag = instInfoService.updateInstInfoStatus(map);
				if (flag) {
					//更新渠道对应定时任务状态
					boolean timer_flag = instInfoService.handleInstInfoTimingTask(map);
					if(timer_flag){
						log.info("渠道相应定时任务状态更新成功");
					}else{
						log.info("渠道相应定时任务状态更新失败");
					}
					
					//更新后台与管理平台内存中渠道信息
					
					InstInfo instInfo = instInfoService.queryInstInfoByInstId(Integer.valueOf(instId),Integer.valueOf(inst_type));
					
					boolean result = instInfoService.updateRamDeductChannelInfo(instInfo.getInstId(),instInfo.getInst_type());
					
					if(result){
						log.info("更新后台渠道"+instInfo.getName()+"信息成功");
					}else{
						log.info("更新后台渠道"+instInfo.getName()+"信息失败");
					}
					
					if (instInfo != null) {
						dataManagerInit.setInstInfoMap(Integer.valueOf(instId), instInfo.getInst_type(),instInfo);
					}
				}
			}
		}
		PrintWriter out = response.getWriter();
		out.print(flag);
		out.flush();
		out.close();
	}
	
	@RequestMapping(value=QUERYINSTINFOIGNOREERROR)
	public String queryInstInfoIgnoreError(ServletRequest request,Model model){
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("numPerPage");

		Map<String, Object> map = new HashMap<String, Object>();
		Page<InstInfo> page = new Page<InstInfo>();
		if(StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if(StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else 
			page.setPageSize(10);
				
		model.addAttribute("pageInstInfo",instInfoService.queryPageInstInfo(page,map));
		List<InstInfo> list = instInfoService.queryAll();
		model.addAttribute("instInfoList", list);
		return IGNORE_ERROR;
	}
//	@RequestMapping(value=UPDATEINSTWHETHERIGNOREERROR,method= RequestMethod.POST)
//	public void updateInstWhetherIgnoreError(ServletRequest request,ServletResponse response,InstInfo instInfo) throws Exception{
//		boolean flag = false;
//		String instId = instInfo.getInstId()+"";
//		if(StringUtils.isNotBlank(instId)){
//			int effectNum = instInfoService.updateInstInfo(instInfo);
//			if(effectNum > 0){
//				flag = true;
//				//更新内存
//				InstInfo info = instInfoService.queryInstInfoByInstId(Integer.valueOf(instId));
//				if (info != null) {
//					dataManagerInit.setInstInfoMap(Integer.valueOf(instId), info);
//				}
//			}
//			PrintWriter out = response.getWriter();
//			out.print(flag);
//			out.flush();
//			out.close();
//		}
//	}
	
	@RequestMapping(value=CHECKINSTIDREPEATORNOT,method=RequestMethod.POST)
	@ResponseBody
	public int checkInstIdRepeatOrNot(ServletRequest request,ServletResponse response){
		int num = 0;
		String instId = request.getParameter("inst_id");
		if(StringUtils.isNotBlank(instId)){
			num = instInfoService.queryInstInfoById(Integer.valueOf(instId));
		}
		return num;
	}
}
