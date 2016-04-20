package com.chinaebi.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinaebi.entity.MerFundStance;
import com.chinaebi.service.MerchantAccountingService;
import com.chinaebi.utils.DateUtil;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Controller
public class MerchantAccountingController {
	
	private static Logger log = LoggerFactory.getLogger(MerchantAccountingController.class);
	
	@Autowired
	private MerchantAccountingService merchantAccountingService;
	
	public static final String SELECTACCOUNTING = "/merBillingManager/selectAccounting";//商户账务查询页面
	
	public static final String QUERYMERACCOUNTING = "/queryMerAccounting.do";//商户账务查询方法
	
	/**
	 * 商户账务查询方法
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value=QUERYMERACCOUNTING)
	public String queryMerchantAccounting(ServletRequest request,Model model){
		try{
			String curPage = request.getParameter("pageNo");
			String pageSize = request.getParameter("pageSize");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String merCode = request.getParameter("merCode");
			String mer_category = request.getParameter("mer_category");
			
			Map<String, Object> map = new HashMap<String, Object>();
			if(StringUtils.isNotBlank(mer_category)){
				map.put("mer_category", Integer.valueOf(mer_category));
			}
			if(StringUtils.isNotBlank(merCode)){
				map.put("mer_code", merCode);
			}
			if(StringUtils.isNotBlank(startTime)){
				map.put("startTime", DateUtil.getformatConversionStart(startTime));
			}
			if(StringUtils.isNotBlank(endTime)){
				map.put("endTime", DateUtil.getformatConversionEnd(endTime));
			}
			
			
			Page<MerFundStance> page = new Page<MerFundStance>();
			if(StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if(StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else 
				page.setPageSize(10);
			
			Page<MerFundStance> resultPage = merchantAccountingService.queryPageMerchantAccounting(page, map);
			List<MerFundStance> list = null;
			long delNum = 0;
			if(resultPage.getResult() != null && resultPage.getResult().size() > 0){
//				list = resultPage.getResult();
				list = new ArrayList<MerFundStance>();
				for (MerFundStance merFundStance : resultPage.getResult()) {
					if(!( 
							(StringUtils.isBlank(merFundStance.getOnAccountAmt()) || "0".equals(merFundStance.getOnAccountAmt()))
								&& 
							(StringUtils.isBlank(merFundStance.getTheAmount()) || "0".equals(merFundStance.getTheAmount()))
								&& 
							(StringUtils.isBlank(merFundStance.getRefundAmount()) || "0".equals(merFundStance.getRefundAmount()))
						)
					){
						list.add(merFundStance);
//						resultPage.setTotalItems(Integer.valueOf(String.valueOf(resultPage.getTotalItems())));
					}else{
						delNum = delNum + 1;
					}
//					else if("0".equals(merFundStance.getOnAccountAmt()) && "0".equals(merFundStance.getTheAmount()) && "0".equals(merFundStance.getRefundAmount())){
//						list.remove(merFundStance);
//						resultPage.setTotalItems(Integer.valueOf(String.valueOf(resultPage.getTotalItems())));
//					}
				}
				
			}
			resultPage.setResult(list);
			resultPage.setTotalItems(Integer.valueOf(String.valueOf(resultPage.getTotalItems() - delNum)));
			resultPage.setTotalPage(Integer.valueOf(String.valueOf(resultPage.getTotalItems()/resultPage.getPageSize())));
			model.addAttribute("pageMerFundStance",resultPage);
			model.addAttribute("pageSize", pageSize);
			model.addAttribute("mer_category", mer_category);
			
		}catch(Exception e){
			log.error("商户账务流水查询抛出异常:"+e);
		}
		return SELECTACCOUNTING;
	}
}
