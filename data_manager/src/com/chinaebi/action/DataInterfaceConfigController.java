package com.chinaebi.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.chinaebi.entity.CustomObject;
import com.chinaebi.entity.DataInterfaceConfig;
import com.chinaebi.entity.MerInfo;
import com.chinaebi.service.CustomObjectService;
import com.chinaebi.service.DataInterfaceConfigService;
import com.chinaebi.utils.FileUtil;
import com.chinaebi.utils.ReadExcelUtil;
import com.chinaebi.utils.RequestUrlConf;
import com.chinaebi.utils.SendPostToManagerUtil;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

/**
 * 接口数据配置
 */
@Controller
public class DataInterfaceConfigController {
	private static final Logger log = LoggerFactory.getLogger(DataInterfaceConfigController.class);
	
	@Autowired
	@Qualifier(value="dataInterfaceConfigService")
	private DataInterfaceConfigService dataInterfaceConfigService;
	
	
	@Autowired
	@Qualifier(value = "customObjectService")
	private CustomObjectService customObjectService;
	
	ResourceBundle res = ResourceBundle.getBundle("application");

	String temporary_path = res.getString("temporary_path");//存放临时文件地址
	
	//页面
	private static final String DATAINTERFACECONFIGINFO = "customDzFile/data_interface_config";
	
	
	//方法
	private static final String QUERYPAGEDATAINTERFACECONFIG = "/queryPageDataInterfaceConfig.do";
	private static final String ADDDATAINTERFACECONFIGBYMERCODE = "/addDataInterfaceConfigByMerCode.do";
	private static final String ADDDATAINTERFACECONFIGBYTRADECODE = "/addDataInterfaceConfigByTradeCode.do";
	private static final String DELETEDATAINTERFACECONFIG = "/deleteDataInterfaceConfig.do";
	private static final String IMPORTDATAINTERFACECONFIGBYMERFILE = "/importDataInterfaceConfigByMerFile.do";
	private static final String IMPORTDATAINTERFACECONFIGBYTRADEFILE = "/importDataInterfaceConfigByTradeFile.do";
	private static final String CHECKMERCODEORTRADECODEREPEATORNOT = "/checkMerCodeOrTradeCodeRepeatOrNot.do";
	private static final String IMPORTMERINFOBYAGENTCODE = "/importMerInfoByAgentCode.do";
	private static final String QUERYMERINFOBYAGENTCODE = "/queryMerInfoByAgentCode.do";
	
	/**
	 * 分页查询接口数据配置信息
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value=QUERYPAGEDATAINTERFACECONFIG,method=RequestMethod.POST)
	public String queryPageDataInterfaceConfig(Model model,ServletRequest request){
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("numPerPage");
		String object_id = request.getParameter("custom_object");
		String mercode_ = request.getParameter("mercode_");
		String tradecode_ = request.getParameter("tradecode_");

		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(object_id)){
			int object_id_ = Integer.valueOf(object_id.split(";")[0]);
			map.put("object_id", object_id_);
			
			if(StringUtils.isNotBlank(tradecode_)){
				map.put("tradeCode", tradecode_);
			}
			if(StringUtils.isNotBlank(mercode_)){
				map.put("merCode", mercode_);
			}
		}
		
		Page<DataInterfaceConfig> page = new Page<DataInterfaceConfig>();
		if(StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if(StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else 
			page.setPageSize(20);
				
		model.addAttribute("pageDataInterfaceConfig",dataInterfaceConfigService.queryPageDataInterfaceConfig(page, map));
		model.addAttribute("object_id",object_id);
		return DATAINTERFACECONFIGINFO;
	}
	
	/**
	 * 删除接口配置数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value=DELETEDATAINTERFACECONFIG,method=RequestMethod.POST)
	@ResponseBody
	public int deleteDataInterfaceConfig(ServletRequest request){
		int del_num = 0;
		try{
			String id = request.getParameter("id");
			if(StringUtils.isNotBlank(id)){
				del_num = dataInterfaceConfigService.deleteDataInterfaceConfig(Integer.valueOf(id));
			}
		}catch(Exception e){
			log.error("删除接口数据抛出异常："+e.getMessage());
		}
		return del_num;
	}
	
	/**
	 * 添加商户号接口数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value=ADDDATAINTERFACECONFIGBYMERCODE,method=RequestMethod.POST)
	@ResponseBody
	public String addDataInterfaceConfigByMerCode(ServletRequest request){
		try{
			String merCode  = request.getParameter("merCode");
			String merName  = request.getParameter("merName");
			String object_id = request.getParameter("object_id");
			String object_name = request.getParameter("object_name");
//			String status_mer = request.getParameter("status_mer");
			if(StringUtils.isNotBlank(object_id) && StringUtils.isNotBlank(object_name)){
				if(StringUtils.isNotBlank(merCode) && StringUtils.isNotBlank(merName)){
					String[] merCodes = merCode.split(",");
					String[] merNames = merName.split(",");
					int effectNum = 0;
					if(merCodes != null && merNames != null){
						if(merCodes.length != merNames.length){
							return "0";
						}else{
							CustomObject customObject = customObjectService.queryCustomObjectById(Integer.valueOf(object_id));
							for(int i=0;i<merCodes.length;i++){
								if(dataInterfaceConfigService.queryDataInterfaceConfigNumberByValueOrName(Integer.valueOf(object_id),merCodes[i])){//检查值或者商户号是否重复 
									DataInterfaceConfig dataInterfaceConfig = new DataInterfaceConfig();
									dataInterfaceConfig.setObject_id(Integer.valueOf(object_id));
									dataInterfaceConfig.setObject_name(object_name);
									dataInterfaceConfig.setValue(merCodes[i]);
									dataInterfaceConfig.setName(merNames[i]);
									dataInterfaceConfig.setStatus(customObject.getWhether_create_file_by_range());
									dataInterfaceConfig.setOperation_time(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
									effectNum += dataInterfaceConfigService.insertDataInterfaceConfig(dataInterfaceConfig);
								}
							}
							return effectNum+";"+(merCodes.length-effectNum);
						}
						
					}
				}
			}
		}catch(Exception e){
			log.error("按商户号添加操作控制层抛出异常："+e.getMessage());
			e.printStackTrace();
			return "0";
		}
		return "0";
	}
	
	/**
	 * 添加交易码接口数据
	 * @param request
	 * @return
	 */
	@RequestMapping(value=ADDDATAINTERFACECONFIGBYTRADECODE,method=RequestMethod.POST)
	@ResponseBody
	public String addDataInterfaceConfigByTradeCode(ServletRequest request){
		try{
			String tradeCode  = request.getParameter("tradeCode");
			String tradeName  = request.getParameter("tradeName");
			String object_id = request.getParameter("object_id");
			String object_name = request.getParameter("object_name");
//			String status_trade = request.getParameter("status_trade");
			if(StringUtils.isNotBlank(object_id) && StringUtils.isNotBlank(object_name)){
				if(StringUtils.isNotBlank(tradeCode) && StringUtils.isNotBlank(tradeName)){
					String[] tradeCodes = tradeCode.split(",");
					String[] tradeNames = tradeName.split(",");
					int effectNum = 0;
					if(tradeCodes != null && tradeNames != null){
						if(tradeCodes.length != tradeNames.length){
							return "0";
						}else{
							CustomObject customObject = customObjectService.queryCustomObjectById(Integer.valueOf(object_id));
							for(int i=0;i<tradeCodes.length;i++){
								if(dataInterfaceConfigService.queryDataInterfaceConfigNumberByValueOrName(Integer.valueOf(object_id),tradeCodes[i])){//检查值或者商户号是否重复 
									DataInterfaceConfig dataInterfaceConfig = new DataInterfaceConfig();
									dataInterfaceConfig.setObject_id(Integer.valueOf(object_id));//系统ID
									dataInterfaceConfig.setObject_name(object_name);//系统名称
									dataInterfaceConfig.setValue(tradeCodes[i]);//交易码
									dataInterfaceConfig.setName(tradeNames[i]);//交易码名称
									dataInterfaceConfig.setStatus(customObject.getWhether_create_file_by_range());//数据状态
									dataInterfaceConfig.setOperation_time(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));//操作时间
									effectNum += dataInterfaceConfigService.insertDataInterfaceConfig(dataInterfaceConfig);
								}
							}
							return effectNum+";"+(tradeCodes.length-effectNum);
						}
						
					}
				}
			}
		}catch(Exception e){
			log.error("按交易码添加操作控制层抛出异常："+e.getMessage());
			e.printStackTrace();
			return "0";
		}
		return "0";
	}
	
	
	/**
	 * 通过商户号文件导入接口数据
	 * @param file_mer
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value=IMPORTDATAINTERFACECONFIGBYMERFILE,method=RequestMethod.POST)
	public String importDataInterfaceConfigByMerFile(@RequestParam MultipartFile file_mer,HttpServletRequest request,Model model){
		int sucessNum = 0;
		int repeatNum = 0;
		int errorNum = 0;
		int wrongNum = 0;
		try{
			String object_id = request.getParameter("object_id");
			String object_name = new String(request.getParameter("object_name").getBytes("iso-8859-1"),"utf-8").trim();
			if(StringUtils.isNotBlank(object_id) && StringUtils.isNotBlank(object_name)){//判断时候从前端获取系统ID和系统名称
				if(file_mer.getOriginalFilename().endsWith(".xls") || file_mer.getOriginalFilename().endsWith(".xlsx")){//判断文件名称是否为xls格式
					FileUtil.inputstreamToFile(file_mer.getInputStream(), new File(temporary_path, file_mer.getOriginalFilename()));
					String result = ReadExcelUtil.readExcel(temporary_path+file_mer.getOriginalFilename(), file_mer.getOriginalFilename().endsWith(".xls")?"xls":"xlsx");
					if(StringUtils.isNotBlank(result)){//判断是否从文件中解析出内容
						String[] mer_arr = result.split(";");
						CustomObject customObject = customObjectService.queryCustomObjectById(Integer.valueOf(object_id));
						for(int i=0;i<mer_arr.length;i++){
							try{
								String mer_string = mer_arr[i];
								String[] merCode_merName = mer_string.split(":");
								String pattern = "^[0-9]{3,15}$"; 
								Pattern p = Pattern.compile(pattern); 
								Matcher m = p.matcher(StringUtils.isNotBlank(merCode_merName[0])?merCode_merName[0].trim():""); 
								if(m.matches()){
									if(merCode_merName[0].length() >=3 && merCode_merName[0].length() <= 15){
										if(dataInterfaceConfigService.queryDataInterfaceConfigNumberByValueOrName(Integer.valueOf(object_id),merCode_merName[0])){//检查值或者商户号是否重复 
											DataInterfaceConfig dataInterfaceConfig = new DataInterfaceConfig();
											dataInterfaceConfig.setObject_id(Integer.valueOf(object_id));
											dataInterfaceConfig.setObject_name(object_name);
											dataInterfaceConfig.setValue(merCode_merName[0]);
											dataInterfaceConfig.setName(merCode_merName[1]);
											dataInterfaceConfig.setStatus(customObject.getWhether_create_file_by_range());
											dataInterfaceConfig.setOperation_time(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
											sucessNum += dataInterfaceConfigService.insertDataInterfaceConfig(dataInterfaceConfig);
										}else{
											repeatNum ++;
										}
									}else{
										errorNum ++;
									}
								}else{
									errorNum ++;
								}
							}catch(Exception e){
								log.error(e.getMessage());
								errorNum ++;
								continue;
							}
						}
						if(errorNum == 0){
							errorNum += mer_arr.length - sucessNum - repeatNum;
						}
					}else{
						wrongNum = 1;
					}
				}else{
					wrongNum = 2;
				}
			}else{
				wrongNum = 1;
			}
		}catch(Exception e){
			log.error(e.getMessage());
			wrongNum = 1;
		}
		model.addAttribute("sucessNum", sucessNum);
		model.addAttribute("repeatNum", repeatNum);
		model.addAttribute("errorNum", errorNum);
		model.addAttribute("wrongNum", wrongNum);
		return DATAINTERFACECONFIGINFO;
	}
	/**
	 * 通过交易码文件导入接口数据
	 * @param file_mer
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value=IMPORTDATAINTERFACECONFIGBYTRADEFILE,method=RequestMethod.POST)
	public String importDataInterfaceConfigByTradeFile(@RequestParam MultipartFile file_trade,HttpServletRequest request,Model model){
		int sucessNum = 0;
		int repeatNum = 0;
		int errorNum = 0;
		int wrongNum = 0;
		try{
			String object_id = request.getParameter("object_id");
			String object_name = new String(request.getParameter("object_name").getBytes("iso-8859-1"),"utf-8").trim();
			if(StringUtils.isNotBlank(object_id) && StringUtils.isNotBlank(object_name)){//判断时候从前端获取系统ID和系统名称
				if(file_trade.getOriginalFilename().endsWith(".xls") || file_trade.getOriginalFilename().endsWith(".xlsx")){//判断文件名称是否为xls格式
					FileUtil.inputstreamToFile(file_trade.getInputStream(), new File(temporary_path, file_trade.getOriginalFilename()));//保存到临时文件夹中
					String result = ReadExcelUtil.readExcel(temporary_path+file_trade.getOriginalFilename(), file_trade.getOriginalFilename().endsWith(".xls")?"xls":"xlsx");
					if(StringUtils.isNotBlank(result)){//判断是否从文件中解析出内容
						String[] mer_arr = result.split(";");
						CustomObject customObject = customObjectService.queryCustomObjectById(Integer.valueOf(object_id));
						for(int i=0;i<mer_arr.length;i++){
							try{
								String mer_string = mer_arr[i];
								String[] merCode_merName = mer_string.split(":");
								String pattern = "^[a-zA-Z0-9]{3}$"; 
								Pattern p = Pattern.compile(pattern); 
								Matcher m = p.matcher(merCode_merName[0]); 
								if(m.matches()){
									if(merCode_merName[0].length() == 3){
										if(dataInterfaceConfigService.queryDataInterfaceConfigNumberByValueOrName(Integer.valueOf(object_id),merCode_merName[0])){//检查值或者商户号是否重复 
											DataInterfaceConfig dataInterfaceConfig = new DataInterfaceConfig();
											dataInterfaceConfig.setObject_id(Integer.valueOf(object_id));
											dataInterfaceConfig.setObject_name(object_name);
											dataInterfaceConfig.setValue(merCode_merName[0]);
											dataInterfaceConfig.setName(merCode_merName[1]);
											dataInterfaceConfig.setStatus(customObject.getWhether_create_file_by_range());
											dataInterfaceConfig.setOperation_time(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
											sucessNum += dataInterfaceConfigService.insertDataInterfaceConfig(dataInterfaceConfig);
										}else{
											repeatNum ++;
										}
									}else{
										errorNum ++;
									}
								}else{
									errorNum ++;
								}
							}catch(Exception e){
								log.error(e.getMessage());
								errorNum ++;
								continue;
							}
						}
						errorNum += mer_arr.length - sucessNum - repeatNum;
					}else{
						wrongNum = 1;
					}
				}else{
					wrongNum = 2;
				}
			}else{
				wrongNum = 1;
			}
		}catch(Exception e){
			log.error(e.getMessage());
			wrongNum = 1;
		}
		model.addAttribute("sucessNum", sucessNum);
		model.addAttribute("repeatNum", repeatNum);
		model.addAttribute("errorNum", errorNum);
		model.addAttribute("wrongNum", wrongNum);
		return DATAINTERFACECONFIGINFO;
	}
	
	
	/**
	 * 解析商户号/交易码文件
	 * @param file
	 * @return
	 */
	public static String parsingXmlFile(File file){
		Workbook workbook = null;   
		String result = "";
		StringBuffer sb = new StringBuffer("");
		try{
			try {   
			    workbook = Workbook.getWorkbook(file);   
			} catch (FileNotFoundException e) {   
				log.info("file to import not found!"); 
				return result;
			}   
			  
			Sheet sheet = workbook.getSheet(0);   
			Cell cell_code = null;   
			Cell cell_name = null;   
			  
			int rowCount=sheet.getRows();  
			for (int i = 1; i <rowCount; i++) {   
		        //这里的两个参数，第一个是表示列的，第二才表示行   
				cell_code=sheet.getCell(0, i);   
				cell_name=sheet.getCell(1, i);   
		        if(StringUtils.isNotBlank(cell_code.getContents()) && StringUtils.isNotBlank(cell_name.getContents())){
		        	sb.append(cell_code.getContents()+":"+cell_name.getContents());
					sb.append(";");
		        }   
			} 
		}catch(Exception e){
			log.error("文件解析报错："+e.getMessage());
		}
		if(StringUtils.isNotBlank(sb.toString())){
			result = sb.toString().substring(0, sb.toString().length()-1);
		}
		try{
			file.delete();//读取完毕，将文件删除
		}catch(Exception e){
			log.error("文件删除报错："+e);
		}
		return result;
	}
	
	/**
	 * 查询同一个系统配置的商户号或者交易码是否重复，如果重复，返回0,；未重复，返回1
	 * @param request
	 * @return
	 */
	@RequestMapping(value=CHECKMERCODEORTRADECODEREPEATORNOT,method=RequestMethod.POST)
	@ResponseBody
	public int checkMerCodeOrTradeCodeRepeatOrNot(ServletRequest request){
		try{
			String object_id = request.getParameter("object_id");
			String code = request.getParameter("code");
			if(StringUtils.isNotBlank(object_id) && StringUtils.isNotBlank(code)){
				if(dataInterfaceConfigService.queryDataInterfaceConfigNumberByValueOrName(Integer.valueOf(object_id),code)){
					return 1;
				}
			}
		}catch(Exception e){
			log.error("检查商户号或者交易码是否重复时抛出异常："+e.getMessage());
		}
		return 0;
	}
	
	/**
	 * 通过代理商号码导入商户号配置信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = IMPORTMERINFOBYAGENTCODE,method=RequestMethod.POST)
	@ResponseBody
	public String importMerInfoByAgentCode(HttpSession session,ServletRequest request){
		int sucessNum = 0;
		int repeatNum = 0;
		int errorNum = 0;
		try{
			String object_id = request.getParameter("object_id");
			String object_name = request.getParameter("object_name");
			String status_mer = request.getParameter("status_mer");
			if(StringUtils.isNotBlank(object_id) && StringUtils.isNotBlank(object_name)){
				List<MerInfo> mer_list = (List<MerInfo>) session.getAttribute("mer_list");
				if(mer_list != null && mer_list.size()>0){
					for (MerInfo merInfo : mer_list) {
						if(dataInterfaceConfigService.queryDataInterfaceConfigNumberByValueOrName(Integer.valueOf(object_id),merInfo.getInnerMercode())){
							DataInterfaceConfig dataInterfaceConfig = new DataInterfaceConfig();
							dataInterfaceConfig.setObject_id(Integer.valueOf(object_id));
							dataInterfaceConfig.setObject_name(object_name);
							dataInterfaceConfig.setValue(merInfo.getInnerMercode());
							dataInterfaceConfig.setName(merInfo.getMerName());
							dataInterfaceConfig.setStatus(StringUtils.isBlank(status_mer)?0:Integer.valueOf(status_mer));
							dataInterfaceConfig.setOperation_time(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
							dataInterfaceConfigService.insertDataInterfaceConfig(dataInterfaceConfig);
							sucessNum ++;
						}else{
							repeatNum ++;
						}
					}
					errorNum = mer_list.size() - sucessNum - repeatNum;
				}
			}
		}catch(Exception e){
			log.error("通过代理商导入商户号抛出异常"+e.getMessage());
		}
		return sucessNum+";"+repeatNum+";"+errorNum;
	}
	/**
	 * 通过代理商号码查询商户号配置信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = QUERYMERINFOBYAGENTCODE,method=RequestMethod.POST)
	public String queryMerInfoByAgentCode(HttpSession session,ServletRequest request,ServletResponse response){
		String result = "";
		response.setContentType("text/html;charset=utf-8");
		try{
			session.removeAttribute("mer_list");
			String object_id = request.getParameter("object_id");
			String agentId = request.getParameter("agent_code");
			if(StringUtils.isNotBlank(object_id)){
				result = SendPostToManagerUtil.getMerInfoList(session,RequestUrlConf.pos_manager_url,agentId);
			}
			PrintWriter out = response.getWriter();
			out.print(result);
		}catch(Exception e){
			log.error("通过代理商导入商户号抛出异常"+e.getMessage());
		}
		return null;
	}
	public static void main(String[] args) {
		String pattern = "^[0-9]{3,15}$"; 
		Pattern p = Pattern.compile(pattern); 
		Matcher m = p.matcher("872100059330008");
		System.out.println(m.matches());
	}
}