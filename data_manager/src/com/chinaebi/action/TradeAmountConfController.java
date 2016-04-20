package com.chinaebi.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinaebi.entity.TradeAmountConf;
import com.chinaebi.service.TradeAmountConfService;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Controller
public class TradeAmountConfController {
	public static final String TRADE_AMOUNT_CONF = "/sysConfig/trade_amount_conf";
	
	public static final String 	QUERYPAGETRADEAMOUNTCONF = "/queryPageTradeAmountConf.do";
	public static final String UPDATETRADEAMOUNTCONF = "/updateTradeAmountConf.do";
	public static final String DELETETRADEAMOUNTCONF = "/deleteTradeAmountConf.do";
	public static final String ADDTRADEAMOUNTCONF = "/addTradeAmountConf.do";
	public static final String QUERYTRADEAMOUNTCONF	 = "/queryTradeAmountConf.do";
	
	@Autowired
	@Qualifier(value="tradeAmountConfService")
	private TradeAmountConfService tradeAmountConfService;
	
	/**
	 * 分页查询交易金额配置信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERYPAGETRADEAMOUNTCONF)
	public String queryPageTradeAmountConf(ServletRequest request,Model model) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		String curPage = request.getParameter("pageNum");
		String pageSize = request.getParameter("numPerPage");
		String name_id = request.getParameter("name");
		String tradeMoneyStatus = request.getParameter("tradeMoneyStatus");
		if(StringUtils.isNotBlank(name_id)){
			map.put("id", name_id);
		}
		if(StringUtils.isNotBlank(tradeMoneyStatus)){
			map.put("tradeMoneyStatus", tradeMoneyStatus);
		}
		Page<TradeAmountConf> page = new Page<TradeAmountConf>();
		if(StringUtils.isNotBlank(curPage))
			page.setPageNo(Integer.parseInt(curPage.trim()));
		if(StringUtils.isNotBlank(pageSize))
			page.setPageSize(Integer.parseInt(pageSize.trim()));
		else 
			page.setPageSize(10);
		model.addAttribute("tradeAmountConfPage", tradeAmountConfService.queryPageTradeAmountConf(page, map));
		
		List<TradeAmountConf> list = tradeAmountConfService.queryTradeAmountConf();
		model.addAttribute("list", list);
		
		model.addAttribute("paramsMap", map);
		return TRADE_AMOUNT_CONF;
	}
	/**
	 * 添加系统操作员
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = ADDTRADEAMOUNTCONF,method = RequestMethod.POST)
	@ResponseBody
	public int addTradeAmountConf(TradeAmountConf tradeAmountConf,ServletRequest request) throws Exception{
		int effectNum = tradeAmountConfService.addTradeAmountConf(tradeAmountConf);	
		return effectNum;
	}
	/**
	 * 修改交易金额配置信息
	 * @param tradeAmountConf
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = UPDATETRADEAMOUNTCONF,method = RequestMethod.POST)
	public void updateTradeAmountConf(TradeAmountConf tradeAmountConf,ServletResponse response) throws Exception{
		boolean flag = false;
		String instId = tradeAmountConf.getId()+"";
		if(StringUtils.isNotBlank(instId)){
			int effectNum = tradeAmountConfService.updateTradeAmountConf(tradeAmountConf);
			if(effectNum > 0){
				flag = true;
			}
			PrintWriter out = response.getWriter();
			out.print(flag);
			out.flush();
			out.close();
		}
	}
	/**
	 * 删除交易金额配置信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = DELETETRADEAMOUNTCONF,method = RequestMethod.POST)
	public void deleteInstInfo(ServletRequest request,ServletResponse response) throws Exception{
		String id = request.getParameter("id");
		if(StringUtils.isNotBlank(id)){
			boolean flag = tradeAmountConfService.deleteInstInfo(Integer.valueOf(id));
			PrintWriter out = response.getWriter();
			out.print(flag);
			out.flush();
			out.close();
		}
	}
}
