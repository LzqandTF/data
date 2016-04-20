package com.chinaebi.action;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.chinaebi.entity.InstInfo;
import com.chinaebi.entity.PpfwData;
import com.chinaebi.reload.DataManagerInit;
import com.chinaebi.service.DuizhangDataService;
import com.chinaebi.service.InstInfoService;
import com.chinaebi.service.PpfwDataService;
import com.chinaebi.utils.DataStatus;
import com.chinaebi.utils.FileUtil;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Controller
public class PpfwDataController {
	private static final Logger log = LoggerFactory.getLogger(PpfwDataController.class);
	
	@Autowired
	@Qualifier(value="ppfwDataService")
	private PpfwDataService ppfwDataService;
	
	@Autowired
	@Qualifier(value = "instInfoService")
	private InstInfoService instInfoService;
	
	@Autowired
	@Qualifier(value = "duizhangDataService")
	private DuizhangDataService duizhangDataService;
	

	@Autowired
	@Qualifier(value="dataManagerInit")
	private DataManagerInit dataManagerInit;
	
	/*
	 * 手动上传并解析品牌服务费请求
	 */
	private static final String MANUALUPLOADPPFWDATA = "/manualUploadPpfwData.do";
	/*
	 * 查村品牌服务费数据请求
	 */
	private static final String QUERYPPFWDATA = "/queryPpfwData.do";
	
	/*
	 * 品牌服务费页面
	 */
	private static final String PPFWDATASELECT	= "/duizhangData/ppfw_data_select";
	/*
	 * 手动上传并解析品牌服务费页面
	 */
	private static final String MANUALUPLOADPPFWFILE = "/duizhangData/manual_upload_ppfw_file";
	
	
	/**
	 * 查询品牌服务费
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value=QUERYPPFWDATA,method=RequestMethod.POST)
	public String queryPagePpfwData(HttpServletRequest request,Model model){
		try{
			String curPage = request.getParameter("pageNum");
			String pageSize = request.getParameter("pageSize");
			String reqSysStance = request.getParameter("reqSysStance");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String deductStlmDate = request.getParameter("deductStlmDate");
			String inst_id = request.getParameter("inst_id");
			
			Page<PpfwData> page = new Page<PpfwData>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(10);
			Map<String, Object> map = new HashMap<String, Object>();
			
			if(StringUtils.isNotBlank(reqSysStance)){
				map.put("reqSysStance", reqSysStance.trim());
			}
			if(StringUtils.isNotBlank(startTime)){
				map.put("startTime", startTime.replace("-", ""));
			}
			if(StringUtils.isNotBlank(endTime)){
				map.put("endTime", endTime.replace("-", ""));
			}
			if(StringUtils.isNotBlank(deductStlmDate)){
				map.put("deduct_stlm_date", deductStlmDate.replace("-", ""));
			}
			Page<PpfwData> page_ = ppfwDataService.queryPpfwData(page, map);
			model.addAttribute("totalFee",ppfwDataService.queryPagePpfwDataTotalCount(map));
			model.addAttribute("pageData",page_);
			model.addAttribute("inst_id", inst_id);
			model.addAttribute("totalItems", page_.getTotalItems());
			model.addAttribute("totalPage", page_.getTotalPage());
			model.addAttribute("size", page_.getResult().size());
			model.addAttribute("pageSize", pageSize);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return PPFWDATASELECT;
	}
	
	/**
	 * 手动上传并解析品牌服务费
	 * @param file
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value=MANUALUPLOADPPFWDATA,method=RequestMethod.POST)
	public String manualUploadPpfwData(@RequestParam MultipartFile file,HttpServletRequest request,Model model){
		try{
			String inst_id = request.getParameter("inst_id");
			String summaryDate = request.getParameter("summaryDate");
			
			if(StringUtils.isNotBlank(inst_id) && StringUtils.isNotBlank(summaryDate)){
				ResourceBundle resource = ResourceBundle.getBundle("application");
				
				summaryDate = summaryDate.replaceAll("-", "");
				
				InstInfo inst = dataManagerInit.getInstInfoById(Integer.valueOf(inst_id), 0);
				
				String file_name_pattern = resource.getString("ppfw_file_pattern");//对账文件名称格式
				String file_path = resource.getString("ppfw_file_path");//对账文件拉取路径
				
				if(StringUtils.isNotBlank(file_name_pattern) && StringUtils.isNotBlank(file_path)){
					try {
						String file_name = file_name_pattern;
						
						String upload_file_name = file.getOriginalFilename();
						
						log.info("当前渠道ID"+inst_id+",对账文件格式为"+file_name+",当前上传文件名称为"+upload_file_name);
						
						if(file_name_pattern.contains(DataStatus.date_format_1)){
							file_name = file_name.replaceAll(DataStatus.date_format_1,"\\\\d{4}-\\\\d{2}-\\\\d{2}");
						}else if(file_name_pattern.contains(DataStatus.date_format_2) && !file_name_pattern.contains(DataStatus.date_format_4)){
							file_name = file_name.replaceAll(DataStatus.date_format_2,"\\\\d{8}");
						}else if(file_name_pattern.contains(DataStatus.date_format_3) && !file_name_pattern.contains(DataStatus.date_format_2) && !file_name_pattern.contains(DataStatus.date_format_5) && !file_name_pattern.contains(DataStatus.date_format_4)){
							file_name = file_name.replaceAll(DataStatus.date_format_3,"\\\\d{2}");
						}else if(file_name_pattern.contains(DataStatus.date_format_4)){
							file_name = file_name.replaceAll(DataStatus.date_format_4,"\\\\d{14}");
						}else if(file_name_pattern.contains(DataStatus.date_format_5) && !file_name_pattern.contains(DataStatus.date_format_2) && !file_name_pattern.contains(DataStatus.date_format_4)){
							file_name = file_name.replaceAll(DataStatus.date_format_5,"\\\\d{6}");
						}else if(file_name_pattern.contains(DataStatus.date_format_6)){
							file_name = file_name.replaceAll(DataStatus.date_format_6,"\\\\d{1,2}.\\\\d{1,2}");
	        			}
						
						if(file_name_pattern.contains("*")){
							if(file_name.split("\\*").length > 0){
								file_name = file_name.split("\\*")[0];
							}else{
								file_name = "";
							}
	        				if(upload_file_name.length() > file_name.indexOf("*")){
	        					upload_file_name = upload_file_name.substring(0, file_name_pattern.indexOf("*"));
	        				}
	        			}
						
						if(upload_file_name.matches(file_name)){
							File file_ = new File(file_path);
							if(!file_.exists() && !file_.isDirectory()){
								file_.mkdir();
							}
							
							FileUtil.inputstreamToFile(file.getInputStream(), new File(file_path, file.getOriginalFilename()));
							
							log.info("品牌服务费上传成功,进行文件解析操作");
							
							boolean result_flag = duizhangDataService.manualParsingDzData(inst.getBank_id(),Integer.valueOf(inst_id),summaryDate,file_path,DataStatus.PPFW_FILE,0);
							
							if(result_flag){
								model.addAttribute("return_msg", "true");
								log.info("手动上传文件成功");
							}else{
								model.addAttribute("return_msg","false");
								log.info("后台返回信息：品牌服务费解析失败");
							}
						}else{
							model.addAttribute("return_msg", "false");
							model.addAttribute("file_msg", "false");
							log.info("上传文件格式不匹配，手动上传文件失败");
						}
					} catch (Exception e) {
						log.error(e.getMessage());
						model.addAttribute("return_msg", "false");
						log.info("手动上传文件失败");
					}
				}else{
					model.addAttribute("return_msg", "false");
				}
			}
			
			List<InstInfo> list = instInfoService.queryAll();
			model.addAttribute("instInfoList", list);
			model.addAttribute("inst_id", inst_id);
			model.addAttribute("summaryDate", summaryDate);
		}catch(Exception e){
			model.addAttribute("return_msg", "false");
			log.error("手动上传文件抛出异常"+e.getMessage());
		}
		return MANUALUPLOADPPFWFILE;
	
	}
}
