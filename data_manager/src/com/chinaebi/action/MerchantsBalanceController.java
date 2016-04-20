package com.chinaebi.action;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chinaebi.entity.MerchantsBalance;
import com.chinaebi.service.MerchantsBalanceService;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

/**
 * 商户余额查询
 *
 */
@Controller
public class MerchantsBalanceController {

	@Autowired
	private MerchantsBalanceService merchantsBalanceService ;
	
	private static Logger log = LoggerFactory.getLogger(MerchantsBalanceController.class);
	
	//分页查询商户信息
	private static final String QUERYMERCHANTBALANCELIST= "/queryMerchantsBalanceList.do";
	//返回JSP页面
	private static final String  MERCHANTBALANCE= "/merBillingManager/merchantsBalance";
	
	/**
	 * 分页查询商户余额信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERYMERCHANTBALANCELIST, method = RequestMethod.POST)
	public String queryMerchantsBalanceList(ServletRequest request, Model model) {
		log.info("进入商户余额信息查询...");
		try {
			//分页参数
			String curPage = request.getParameter("pageNum");
			//每页显示数据条数参数
			String pageSize = request.getParameter("pageSize");
			Page<MerchantsBalance> page = new Page<MerchantsBalance>();
			if (StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if (StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else
				page.setPageSize(10);
			String merCode=request.getParameter("merCode");//商户号
			String merName=request.getParameter("merName");//商户简称
			//查询参数
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("merCode", merCode);
			map.put("merName", merName);
			Page<MerchantsBalance> pageList=merchantsBalanceService.queryPageMerchantsBalanceList(page, map);
				if(pageList!=null && pageList.result.size() > 0){
					for(MerchantsBalance mb:pageList.result){
						double amt=Double.parseDouble(mb.getMer_balance());
						mb.setMer_balance(String.format("%.2f", amt));
					}
				}
			model.addAttribute("merchantsList", pageList);
			model.addAttribute("merName", merName);
			model.addAttribute("merCode", merCode);
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) {
			log.error("查询商户余额信息异常：" + e.getMessage());
		}
		return  MERCHANTBALANCE;
	}
}
