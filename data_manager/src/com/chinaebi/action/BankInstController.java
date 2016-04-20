package com.chinaebi.action;

import java.io.PrintWriter;
import java.util.HashMap;
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

import com.chinaebi.entity.BankInst;
import com.chinaebi.reload.DataManagerInit;
import com.chinaebi.service.BankInstService;
import com.chinaebi.service.InstInfoService;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

/**
 * 银行机构维护
 * @author wufei
 *
 */
@Controller
public class BankInstController {
	private static final Logger logger = LoggerFactory.getLogger(BankInstController.class);
	
	// 查询银行机构信息
	private static final String QUERY_PAGE_BANK_INST = "queryBankInstLst.do";
	private static final String JSP_PAGE = "sysConfig/bank_inst";
	
	//添加银行机构信息
	private static final String ADD_BANK_INST = "addBankInst.do";
	
	// 修改银行机构信息
	private static final String UPDATE_BANK_INST_BY_BANK_ID = "updateBankInstByBankId.do";
	
	// 删除银行机构
	private static final String DELETE_BANK_INST_BY_BANK_ID = "deleteBankInstByBankId.do";
	
	@Autowired
	@Qualifier(value = "bankInstService")
	private BankInstService bankInstService;
	
	@Autowired
	@Qualifier(value = "instInfoService")
	private InstInfoService instInfoService;
	
	//从内存中加载各种配置列表
	@Autowired
	@Qualifier(value="dataManagerInit")
	private DataManagerInit dataManagerInit;
	
	@RequestMapping(value = QUERY_PAGE_BANK_INST, method = RequestMethod.POST)
	public String queryPageBankInst(ServletRequest request, Model model) {
		logger.info("系统配置  进入银行机构配置数据查询...");
		try {
			//分页参数
			String curPage = request.getParameter("pageNum");
			//分页显示条数
			String pageSize = request.getParameter("pageSize");
			Page<BankInst> page = new Page<BankInst>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(10);
			
			String bankName = request.getParameter("bankName");
			String bank_type = request.getParameter("bank_type_select");
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(bankName)) {
				map.put("bank_name", bankName);
			}
			if (StringUtils.isNotBlank(bank_type)) {
				map.put("bank_type", bank_type);
			}
			model.addAttribute("pageLst", bankInstService.queryPageBankInst(page, map));
			model.addAttribute("bank_type", bank_type);
			model.addAttribute("pageSize", pageSize);
			return JSP_PAGE;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return JSP_PAGE;
		}
	}
	
	/**
	 * 添加银行机构信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = ADD_BANK_INST, method = RequestMethod.POST)
	public void addBankInst(ServletRequest request, ServletResponse response) throws Exception {
		String bank_name = request.getParameter("bank_name");
		String parse_dz_file_class = request.getParameter("parse_dz_file_class");
		String dz_data_tableName = request.getParameter("dz_data_tableName");
		String ftp_dz_file_path = request.getParameter("ftp_dz_file_path");
		String dz_file_path = request.getParameter("dz_file_path");
		String dz_file_name_pattern = request.getParameter("dz_file_name_pattern");
		String start_row = request.getParameter("start_row");
		String isTk = request.getParameter("isTk");
		String tk_type = request.getParameter("tk_type");
		String tk_context = request.getParameter("tk_context");
		String tk_column = request.getParameter("tk_column");
		String original_data_tableName = request.getParameter("original_data_tableName");
		String riqie_original_tableName = request.getParameter("riqie_original_tableName");
		String inst_entity_name = request.getParameter("inst_entity_name");
		String bank_type = request.getParameter("bank_type");
		String whether_outer_dz = request.getParameter("whether_outer_dz");
		String bank_entity_name = request.getParameter("bank_entity_name");
		String trade_dz_impl_class = request.getParameter("trade_dz_impl_class");
		String tk_tableName = request.getParameter("tk_tableName");
		
		int effectNum = 0;
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(bank_name)) {
			map.put("bank_name", bank_name);
		}
		if (StringUtils.isNotBlank(parse_dz_file_class)) {
			map.put("parse_dz_file_class", parse_dz_file_class);
		}
		if (StringUtils.isNotBlank(dz_data_tableName)){
			map.put("dz_data_tableName", dz_data_tableName);
		}
		if (StringUtils.isNotBlank(ftp_dz_file_path)) {
			map.put("ftp_dz_file_path", ftp_dz_file_path);
		}
		if (StringUtils.isNotBlank(dz_file_path)) {
			map.put("dz_file_path", dz_file_path);
		}
		if (StringUtils.isNotBlank(dz_file_name_pattern)) {
			map.put("dz_file_name_pattern", dz_file_name_pattern);
		}
		if (StringUtils.isNotBlank(start_row)) {
			map.put("start_row", start_row);
		}
		if (StringUtils.isNotBlank(isTk)) {
			map.put("isTk", isTk);
		}
		if (StringUtils.isNotBlank(tk_type)) {
			map.put("tk_type", tk_type);
		}
		if (StringUtils.isNotBlank(tk_context)) {
			map.put("tk_context", tk_context);
		}
		if (StringUtils.isNotBlank(tk_column)) {
			map.put("tk_column", tk_column);
		}
		if (StringUtils.isNotBlank(original_data_tableName)) {
			map.put("original_data_tableName", original_data_tableName);
		}
		if (StringUtils.isNotBlank(riqie_original_tableName)) {
			map.put("riqie_original_tableName", riqie_original_tableName);
		}
		if (StringUtils.isNotBlank(inst_entity_name)) {
			map.put("inst_entity_name", inst_entity_name);
		}
		if (StringUtils.isNotBlank(bank_entity_name)) {
			map.put("bank_entity_name", bank_entity_name);
		}
		if (StringUtils.isNotBlank(bank_type)) {
			map.put("bank_type", bank_type);
		}
		if (StringUtils.isNotBlank(whether_outer_dz)) {
			map.put("whether_outer_dz", whether_outer_dz);
		}
		if (StringUtils.isNotBlank(trade_dz_impl_class)) {
			map.put("trade_dz_impl_class", trade_dz_impl_class);
		}
		if (StringUtils.isNotBlank(tk_tableName)) {
			map.put("tk_tableName", tk_tableName);
		}
		
		BankInst bankInst = null;
		bankInst = bankInstService.queryBankInstByBankName(bank_name);
		if (bankInst != null) {
			effectNum = 2;
		} else {
			effectNum = bankInstService.addBankInst(map);
			if (effectNum > 0) {
				bankInst = bankInstService.queryBankInstByBankName(bank_name);
				dataManagerInit.setBankInstMap(bankInst.getBank_id(), bankInst);
				boolean result = bankInstService.updateRamBankInstInfo(bankInst.getBank_id());
				if(result){
					logger.info("更新后台银行机构"+bankInst.getBank_name()+"信息成功");
				}else{
					logger.info("更新后台银行机构"+bankInst.getBank_name()+"信息失败");
				}
			}
		}
		PrintWriter out = response.getWriter();
		out.print(effectNum);
		out.flush();
		out.close();
	}
	
	/**
	 * 根据银行机构ID修改信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = UPDATE_BANK_INST_BY_BANK_ID, method = RequestMethod.POST)
	public void updateBankInstByBankId(ServletRequest request, ServletResponse response) throws Exception {
		String bank_id = request.getParameter("bank_id");
		String bank_name = request.getParameter("bank_name");
		String parse_dz_file_class = request.getParameter("parse_dz_file_class");
		String dz_data_tableName = request.getParameter("dz_data_tableName");
		String ftp_dz_file_path = request.getParameter("ftp_dz_file_path");
		String dz_file_path = request.getParameter("dz_file_path");
		String dz_file_name_pattern = request.getParameter("dz_file_name_pattern");
		String start_row = request.getParameter("start_row");
		String isTk = request.getParameter("isTk");
		String tk_type = request.getParameter("tk_type");
		String tk_context = request.getParameter("tk_context");
		String tk_column = request.getParameter("tk_column");
		String original_data_tableName = request.getParameter("original_data_tableName");
		String riqie_original_tableName = request.getParameter("riqie_original_tableName");
		String inst_entity_name = request.getParameter("inst_entity_name");
		String bank_type = request.getParameter("bank_type");
		String whether_outer_dz = request.getParameter("whether_outer_dz");
		String bank_entity_name = request.getParameter("bank_entity_name");
		String trade_dz_impl_class = request.getParameter("trade_dz_impl_class");
		String tk_tableName = request.getParameter("tk_tableName");
		
		int effectNum = 0;
		
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(bank_id)) {
			map.put("bank_id", bank_id);
		}
		if (StringUtils.isNotBlank(bank_name)) {
			map.put("bank_name", bank_name);
		}
		if (StringUtils.isNotBlank(parse_dz_file_class)) {
			map.put("parse_dz_file_class", parse_dz_file_class);
		}
		if (StringUtils.isNotBlank(dz_data_tableName)){
			map.put("dz_data_tableName", dz_data_tableName);
		}
		if (StringUtils.isNotBlank(ftp_dz_file_path)) {
			map.put("ftp_dz_file_path", ftp_dz_file_path);
		}
		if (StringUtils.isNotBlank(dz_file_path)) {
			map.put("dz_file_path", dz_file_path);
		}
		if (StringUtils.isNotBlank(dz_file_name_pattern)) {
			map.put("dz_file_name_pattern", dz_file_name_pattern);
		}
		if (StringUtils.isNotBlank(start_row)) {
			map.put("start_row", start_row);
		}
		if (StringUtils.isNotBlank(isTk)) {
			map.put("isTk", isTk);
		}
		if (StringUtils.isNotBlank(tk_type)) {
			map.put("tk_type", tk_type);
		}
		if (StringUtils.isNotBlank(tk_context)) {
			map.put("tk_context", tk_context);
		}
		if (StringUtils.isNotBlank(tk_column)) {
			map.put("tk_column", tk_column);
		}
		if (StringUtils.isNotBlank(original_data_tableName)) {
			map.put("original_data_tableName", original_data_tableName);
		}
		if (StringUtils.isNotBlank(riqie_original_tableName)) {
			map.put("riqie_original_tableName", riqie_original_tableName);
		}
		if (StringUtils.isNotBlank(inst_entity_name)) {
			map.put("inst_entity_name", inst_entity_name);
		}
		if (StringUtils.isNotBlank(bank_entity_name)) {
			map.put("bank_entity_name", bank_entity_name);
		}
		if (StringUtils.isNotBlank(bank_type)) {
			map.put("bank_type", bank_type);
		}
		if (StringUtils.isNotBlank(whether_outer_dz)) {
			map.put("whether_outer_dz", whether_outer_dz);
		}
		if (StringUtils.isNotBlank(trade_dz_impl_class)) {
			map.put("trade_dz_impl_class", trade_dz_impl_class);
		}
		if (StringUtils.isNotBlank(tk_tableName)) {
			map.put("tk_tableName", tk_tableName);
		}
		
		effectNum = bankInstService.updateBankInstByBankId(map);
		if (effectNum > 0) {
			dataManagerInit.setBankInstMap(Integer.valueOf(bank_id), map);
			boolean result = bankInstService.updateRamBankInstInfo(Integer.valueOf(bank_id));
			if(result){
				logger.info("更新后台银行机构"+bank_name+"信息成功");
			}else{
				logger.info("更新后台银行机构"+bank_name+"信息失败");
			}
		}
		PrintWriter out = response.getWriter();
		out.print(effectNum);
		out.flush();
		out.close();
	}
	
	/**
	 * 根据银行机构ID先删除该机构下的所有扣款渠道，再删除该银行机构
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = DELETE_BANK_INST_BY_BANK_ID, method=RequestMethod.POST)
	public void deleteBankInstByBankId(ServletRequest request, ServletResponse response) throws Exception {
		String bankId = request.getParameter("bank_id");
		int effectNum = 0;
		if (StringUtils.isNotBlank(bankId)) {
			int count = instInfoService.queryInstInfoCountByBankId(bankId);
			if (count > 0) {
				int delInstInfoNum = instInfoService.deleteInstInfoByBankId(bankId);
				if (delInstInfoNum > 0) {
					effectNum = bankInstService.deleteBankInstByBankId(bankId);
					// 更新管理平台渠道内存信息
					dataManagerInit.removeInstInfoByBankId(Integer.valueOf(bankId));
					if (effectNum > 0) {
						// 更新管理平台银行机构内存信息
						dataManagerInit.removeBankInst(Integer.valueOf(bankId));
						boolean result = bankInstService.updateRamBankInstInfo(Integer.valueOf(bankId));
						if(result){
							logger.info("更新后台银行机构信息成功");
						}else{
							logger.info("更新后台银行机构信息失败");
						}
					} 
				}
			} else {
				effectNum = bankInstService.deleteBankInstByBankId(bankId);
				if (effectNum > 0) {
					// 更新管理平台银行机构内存信息
					dataManagerInit.removeBankInst(Integer.valueOf(bankId));
					boolean result = bankInstService.updateRamBankInstInfo(Integer.valueOf(bankId));
					if(result){
						logger.info("更新后台银行机构信息成功");
					}else{
						logger.info("更新后台银行机构信息失败");
					}
				} 
			}
		} else {
			logger.warn("主键ID为空");
		}
		PrintWriter out = response.getWriter();
		out.print(effectNum);
		out.flush();
		out.close();
	}
}
