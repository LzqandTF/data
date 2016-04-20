package com.chinaebi.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.chinaebi.entity.InstInfo;
import com.chinaebi.entity.BankDealWith;
import com.chinaebi.reload.DataManagerInit;
import com.chinaebi.service.BankDealWithService;
import com.chinaebi.service.PpfwDataService;
import com.chinaebi.utils.DateUtil;
import com.chinaebi.utils.Ryt_trade_type;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;


/**
 * 应收银行交易款
 * @author wanghui
 *
 */
@Controller
public class BankDealWithController {
	
	@Autowired
	private BankDealWithService bankDealWithService;
	
	private static Logger log = LoggerFactory.getLogger(BankDealWithController.class);
	
	@Autowired
	@Qualifier(value = "dataManagerInit")
	private DataManagerInit dataManagerInit;
	
	@Autowired
	@Qualifier(value = "commonClass")
	private CommonClass commonClass;
	
	@Autowired
	@Qualifier(value="ppfwDataService")
	private PpfwDataService ppfwDataService;
	
	
	//分页查询商户信息
	private static final String QUERYLISTCOUNT="/querySumListCountInfoCount.do";
	
	//返回JSP页面
	private static final String  BANKDEALWITH= "/merBillingManager/bankDealWith";
	
	//初始化页面值
	private static final String QUERYINSTINFOCOUNT="/queryInstInfoCount.do";
	
	//查询交易数据
	private static final String QUERYTRADINGLIST="/queryTradingList.do";
	
	//返回JSP页面
	private static final String  TRADINGSHOW= "/merBillingManager/TradingShow";
	
	
	/**
	 * 查询线上线下交易
	 * @return
	 */
	@RequestMapping(value = QUERYINSTINFOCOUNT)
	@ResponseBody
	public List<BankDealWith> queryInstInfo() {
		List<BankDealWith> list_inst=new ArrayList<BankDealWith>();
		try {
			list_inst=bankDealWithService.queryInstInfo();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return list_inst;
	}
	
	/**
	 * 查询T+1数据统计表数据
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERYLISTCOUNT, method = RequestMethod.POST)
	public String querySumListCountInfoCount(ServletRequest request, Model model){
		try {
			String startDate=null;
			String endDate=null;
			int number=0;//每页显示条数
			int pageNo=0;//当前页数
			int pagesNumber=0;//总页数
			int numbers=0;//本页条数
			String pageNos=request.getParameter("pageSize");//每页显示条数
			if(StringUtils.isBlank(pageNos)){
				pageNo=10;
			}else{
				pageNo = Integer.valueOf(pageNos);
			}
			String pageNum = request.getParameter("pageNo");//跳转页数
			if(StringUtils.isNotBlank(pageNum)){
				number=Integer.parseInt(pageNum);
			}else{
				number=1;
			}
			String startTime=request.getParameter("startTime");//交易起始时间
			if(StringUtils.isNotBlank(startTime)){
				startDate=startTime.replace("-","");
			}
			String endTime=request.getParameter("endTime");//交易截止时间
			if(StringUtils.isNotBlank(endTime)){
				endDate=endTime.replace("-","");
			}
			StringBuffer inst_idBuffer=new StringBuffer();
			String shangxia=request.getParameter("arr");//线上、下渠道
			if(StringUtils.isNotBlank(shangxia)){
				String[] offline=shangxia.split(",");
				if(offline != null && offline.length > 0){
					inst_idBuffer.append(" ( ");
					for (int i = 0; i < offline.length; i++) {
						
						inst_idBuffer.append(" (inst_id = ");
						String inst_id=offline[i].split("-")[0];
						inst_idBuffer.append(inst_id);
						inst_idBuffer.append(" and ");
						
						inst_idBuffer.append("inst_type = ");
						String inst_type=offline[i].split("-")[1];
						inst_idBuffer.append(inst_type);
						inst_idBuffer.append(") ");
						if(i+1 != offline.length){
							inst_idBuffer.append(" or ");
						}
					}
					inst_idBuffer.append(" ) ");
				}
				
			}
			String instId = inst_idBuffer.toString();
//			String instId=sqlStr.substring(0, sqlStr.length()-4);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("instId", instId);
			map.put("startDate", startDate);
			map.put("endDate", endDate);
			double sunmpayamt=0;//支付金额
			double sumarefundamt=0;//退款金额
			double sumpayfee=0;//银行手续费
			double sumarefundfee=0;//银行退回手续费
			double bankPaidInFee=0;//银行实收手续费
			double bankamt=0;//银行划款额
			List<BankDealWith> pageList=bankDealWithService.queryListCountInfo(map);
			List<BankDealWith> list=new ArrayList<BankDealWith>();
			Map<String,Object> map_ = new HashMap<String,Object>();
			if(pageList !=null && pageList.size() > 0){
				for(BankDealWith bkw:pageList){
					map_.put(bkw.getName()+"-"+bkw.getInstType(), bkw.getName()+"-"+bkw.getInstType());
						BankDealWith bank=new BankDealWith();
						if(StringUtils.isNotBlank(bkw.getSunmPayAmt())){//支付金额
							sunmpayamt+=Double.parseDouble(bkw.getSunmPayAmt());
							double payamt=Double.parseDouble(bkw.getSunmPayAmt());
							bank.setSunmPayAmt(String.format("%.2f", payamt));//支付金额
						}
						if(StringUtils.isNotBlank(bkw.getSumArefundAmt())){//退款金额
							sumarefundamt+=Double.parseDouble(bkw.getSumArefundAmt());
							double arefundamt=Double.parseDouble(bkw.getSumArefundAmt());
							bank.setSumArefundAmt(String.format("%.2f",arefundamt));//退款金额
						}
						if(StringUtils.isNotBlank(bkw.getSumPayFee())){//支付手续费
							sumpayfee+=Double.parseDouble(bkw.getSumPayFee());
							double payfee=Double.parseDouble(bkw.getSumPayFee());
							bank.setSumPayFee(String.format("%.2f",payfee));//支付手续费
						}
						if(StringUtils.isNotBlank(bkw.getSumArefundFee())){//退款手续费
							sumarefundfee+=Double.parseDouble(bkw.getSumArefundFee());
							double arefunfee=Double.parseDouble(bkw.getSumArefundFee());
							bank.setSumArefundFee(String.format("%.2f",arefunfee));//退款手续费
						}
//						bank.setOriginalDataTableName(bkw.getOriginalDataTableName());
						
						bankPaidInFee+=Double.parseDouble(bkw.getSumPayFee())+Double.parseDouble(bkw.getSumArefundFee());
						double bankPaidInFees=Double.parseDouble(bkw.getSumPayFee())+Double.parseDouble(bkw.getSumArefundFee());
						bank.setBankPaidInFee(String.format("%.2f",bankPaidInFees));//银行实收手续费
						
						InstInfo instinfo=dataManagerInit.getInstInfoById(Integer.parseInt(bkw.getName()),Integer.parseInt(bkw.getInstType()));
						bank.setName(instinfo.getName());//获取渠道名
						bank.setInstType(bkw.getInstType());//获取渠道类型
						String original_data_tableName = dataManagerInit.getBankInstByBankId(instinfo.getBank_id()).getOriginal_data_tableName();
						bank.setOriginalDataTableName(original_data_tableName);//线上线下渠道对应交易表
						
						Double ppfw_fee = 0.00d;//品牌服务费
						if(instinfo.getWhether_parse_brank() == 1){//针对银联cups渠道，到数据库表中查询数据，其他渠道则默认为0.00
							ppfw_fee = ppfwDataService.getPpfwFeeTotalCount(map);
						}
						bank.setPpfw_fee(ppfw_fee);
						
						bankamt+=(Double.parseDouble(bkw.getSunmPayAmt()))+(Double.parseDouble(bkw.getSumArefundAmt()))-((Double.parseDouble(bkw.getSumPayFee()))+(Double.parseDouble(bkw.getSumArefundFee())) + ppfw_fee);//银行划款额
						double bankamts=(Double.parseDouble(bkw.getSunmPayAmt()))+(Double.parseDouble(bkw.getSumArefundAmt()))-((Double.parseDouble(bkw.getSumPayFee()))+(Double.parseDouble(bkw.getSumArefundFee())) + ppfw_fee);
						bank.setBankAmt(String.format("%.2f", bankamts));//银行划款额
						
						list.add(bank);
				  }
				String[] id_arr = shangxia.split(",");
				for(int i=0;i<id_arr.length;i++){
					String instid=id_arr[i].split("-")[0];
					String instType=id_arr[i].split("-")[1];//渠道类型
					if(!map_.containsKey(instid+"-"+instType)){
						BankDealWith with=new BankDealWith();
						InstInfo instinfo=dataManagerInit.getInstInfoById(Integer.parseInt(instid),Integer.parseInt(instType));
						String original_data_tableName = dataManagerInit.getBankInstByBankId(instinfo.getBank_id()).getOriginal_data_tableName();
						with.setOriginalDataTableName(original_data_tableName);//线上线下渠道对应交易表
						with.setName(instinfo.getName());//获取渠道名
						with.setInstType(instType);//获取渠道类型
						with.setSunmPayAmt("0.00");//支付金额
						with.setSumArefundAmt("0.00");//退款金额
						with.setSumPayFee("0.00");//支付手续费
						with.setSumArefundFee("0.00");//退款手续费
						with.setBankPaidInFee("0.00");//实收手续费
						with.setBankAmt("0.00");//银行划款额
						with.setPpfw_fee(0.00d);//品牌服务费
						list.add(with);
					}
				}
			}else{
				if(StringUtils.isNotBlank(shangxia)){
					String[] offline=shangxia.split(",");
					for (int i = 0; i < offline.length; i++) {
						String instid=offline[i].split("-")[0];
						String instType=offline[i].split("-")[1];//渠道类型
							BankDealWith with=new BankDealWith();
							InstInfo instinfo=dataManagerInit.getInstInfoById(Integer.parseInt(instid),Integer.parseInt(instType));
							String original_data_tableName = dataManagerInit.getBankInstByBankId(instinfo.getBank_id()).getOriginal_data_tableName();
							with.setOriginalDataTableName(original_data_tableName);//线上线下渠道对应交易表
							with.setName(instinfo.getName());//获取渠道名
							with.setInstType(instType);//获取渠道类型
							with.setSunmPayAmt("0.00");//支付金额
							with.setSumArefundAmt("0.00");//退款金额
							with.setSumPayFee("0.00");//支付手续费
							with.setSumArefundFee("0.00");//退款手续费
							with.setBankPaidInFee("0.00");//实收手续费
							with.setBankAmt("0.00");//银行划款额
							with.setPpfw_fee(0.00d);//品牌服务费
							list.add(with);
					}
				}
			}
			int size=list.size();
			int start=pageNo*(number-1);//开始条数
			int end=pageNo*number;//结束条数
			int currentPageNo = 1;
			if(end==0){
				end=10;
			}
			if(end > size){
				end=size;
			}
			List<BankDealWith> bankList=new ArrayList<BankDealWith>();
			bankList=list.subList(start,end);
			numbers=end-start;//本页的条数
			if(size%pageNo == 0){
				pagesNumber = size/pageNo;
			}else{
				pagesNumber = size/pageNo + 1;
			}
			model.addAttribute("querySumListCountInfo",bankList);
			model.addAttribute("shangxia", shangxia);
			model.addAttribute("sunmpayamt", sunmpayamt);
			model.addAttribute("sumarefundamt", sumarefundamt);
			model.addAttribute("sumpayfee",sumpayfee);
			model.addAttribute("sumarefundfee",sumarefundfee);
			model.addAttribute("bankPaidInFee",bankPaidInFee);
			model.addAttribute("bankamt", bankamt);
			model.addAttribute("size", size);
			model.addAttribute("pageNo", pageNo);
			model.addAttribute("numbers", numbers);
			model.addAttribute("number", number);
			model.addAttribute("pagesNumber", pagesNumber);
			model.addAttribute("currentPageNo", StringUtils.isBlank(pageNum)?currentPageNo:Integer.valueOf(pageNum));
		} catch (Exception e) {
			log.error("查询T+1数据统计表数据：" + e.getMessage());
		}
		return BANKDEALWITH;
	}
	
	/**
	 * 查询手续费异常交易数据
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = QUERYTRADINGLIST, method = RequestMethod.POST)
	public String queryTradingList(ServletRequest request, Model model){
		try {
			String tableName=null;
			String sqlList=null;
			String sumSql=null;
			double sumTradingAmt=0;// 交易总金额
			double sumBankEchoFee=0;//银行应收手续费总计
			double sumBankRealFee=0;//银行实收手续费
			String curPage = request.getParameter("pageNum");
			String pageSize = request.getParameter("numPerPage");
			String instType = request.getParameter("instType");//线上线下标识、1：线上、0：线下
			String originalDataTableName = request.getParameter("originalDataTableName");//渠道对应交易表
			String name = request.getParameter("name");//扣款渠道
			String startTime= request.getParameter("startTime");
			String endTime= request.getParameter("endTime");
			StringBuffer sdf=new StringBuffer();
			StringBuffer sff=new StringBuffer();
			if(instType.equals("1")){
				startTime = startTime.replace("-", "");
				endTime = endTime.replace("-", "");
				String[] str=originalDataTableName.split(",");
				tableName=str[0];
				sdf.append("(select tseq, oid, mid, type, sys_date,sys_time, amount, zf_file_fee, zf_fee from ");
				sdf.append(tableName);
				sdf.append(" where sys_date BETWEEN ");
				sdf.append(startTime);
				sdf.append(" and ");
				sdf.append(endTime);
				sdf.append(" and bk_chk = 1 and zf_fee_bj = 2) m");
				sqlList=sdf.toString();
				
				sff.append("(select sum(amount) as amount, IFNULL(sum(CONVERT(zf_file_fee,DECIMAL(20,2))),0) as zf_file_fee, sum(zf_fee) as zf_fee from ");
				sff.append(tableName);
				sff.append(" where sys_date BETWEEN ");
				sff.append(startTime);
				sff.append(" and ");
				sff.append(endTime);
				sff.append(" and bk_chk = 1 and zf_fee_bj = 2) m");
				sumSql=sff.toString();
			}else{
				startTime = DateUtil.getformatConversionStart(startTime);
				endTime = DateUtil.getformatConversionEnd(endTime);
				tableName=originalDataTableName;
				sdf.append("(select req_sys_stance as tseq, substring(additional_data,1,20) as oid, req_process as process, trademsg_type as tradeMsgTypeprivate, req_mer_code as mid, trade_type as type, trade_time as mdate,");
				sdf.append("trade_amount as amount, zf_file_fee, zf_fee from ");
				sdf.append(tableName);
				sdf.append(" where trade_time BETWEEN '");
				sdf.append(startTime);
				sdf.append("' and '");
				sdf.append(endTime);
				sdf.append("' and bk_chk = 1 and zf_fee_bj = 2) m");
				sqlList=sdf.toString();
				
				sff.append("(select sum(trade_amount) as amount, IFNULL(sum(CONVERT(zf_file_fee,DECIMAL(20,2))),0) as zf_file_fee, sum(zf_fee) as zf_fee from ");
				sff.append(tableName);
				sff.append(" where trade_time BETWEEN '");
				sff.append(startTime);
				sff.append("' and '");
				sff.append(endTime);
				sff.append("' and bk_chk = 1 and zf_fee_bj = 2) m");
				sumSql=sff.toString();
			}
			Page<BankDealWith> page = new Page<BankDealWith>();
			if(StringUtils.isNotBlank(curPage))
				page.setPageNo(Integer.parseInt(curPage.trim()));
			if(StringUtils.isNotBlank(pageSize))
				page.setPageSize(Integer.parseInt(pageSize.trim()));
			else 
				page.setPageSize(10);
			Page<BankDealWith> list=new Page<BankDealWith>();
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("sqlList", sqlList);
			map.put("sumSql", sumSql);
			BankDealWith bWith=new BankDealWith();
			bWith=bankDealWithService.querySumTradingList(map);
			if(bWith != null){
				if(StringUtils.isNotBlank(bWith.getAmount())){
					sumTradingAmt=Double.parseDouble(bWith.getAmount());
				}
				if(StringUtils.isNotBlank(bWith.getZf_file_fee())){
					sumBankEchoFee=Double.parseDouble(bWith.getZf_file_fee());
				}
				if(StringUtils.isNotBlank(bWith.getZf_fee())){
					sumBankRealFee=Double.parseDouble(bWith.getZf_fee());
				}
			}
			list=bankDealWithService.queryTradingList(page,map);
			if(list != null && list.result.size() > 0){
				for(BankDealWith bkw : list.result){
					String merAbbreviation="";
					String mercode=bkw.getMid();
					if(StringUtils.isNotBlank(bkw.getAmount())){
						bkw.setAmount(String.valueOf(Double.parseDouble(bkw.getAmount())/100));
					}
					if(StringUtils.isNotBlank(mercode)){
						String mabbreviation=bankDealWithService.queryMerName(mercode);
						if(StringUtils.isNotBlank(mabbreviation)){
							merAbbreviation=mabbreviation;
						}else{
							String mabbreviationOne=bankDealWithService.queryMernameOne(mercode);
							merAbbreviation=mabbreviationOne;
						}
					}
					bkw.setMabbreviation(merAbbreviation);
					if(instType.equals("0")){
						bkw.setType(commonClass.getTradeTypeByProcessAndTradeMsgType(bkw.getProcess(),bkw.getTradeMsgTypeprivate()));
					}else{
						if(StringUtils.isNotBlank(bkw.getType()))
							bkw.setType(Ryt_trade_type.getRytTradeName(Integer.valueOf(bkw.getType())));
					}
					if(instType.equals("1")){
						bkw.setMdate(DateUtil.getRyfDateHandler(bkw.getSys_date(),bkw.getSys_time()));
					}
					bkw.setName(name);
				}
			}
			model.addAttribute("tradingList",list);
			model.addAttribute("sumTradingAmt",sumTradingAmt/100);
			model.addAttribute("sumBankEchoFee",sumBankEchoFee);
			model.addAttribute("sumBankRealFee",sumBankRealFee);
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) {
			log.error("查询交易数据：" + e.getMessage());
		}
		return TRADINGSHOW;
	}
	
	
	public static void main(String[] args) {
		String str = " inst_id = 4 and  inst_id = 10 and  inst_id = 11 and  inst_id = 11 and ";
		
		System.out.println(str.substring(0, str.length()-5));
		
	}
}
