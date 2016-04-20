package cn.com.chinaebi.dz.webservice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.MerBasic;
import cn.com.chinaebi.dz.object.MerBilling;
import cn.com.chinaebi.dz.reload.Backstage;
import cn.com.chinaebi.dz.service.MerchantService;

/**
 * 接收融易付商户信息和商户结算信息同步
 * @author fansm
 * http://192.168.20.138:8080/backstagemamage/reciveMerBilingInforService
 */
public class ReciveMerBilingInforService extends HttpServlet{

	private static final long serialVersionUID = 1L;

	private static Log log = LogFactory.getLog(ReciveMerBilingInforService.class);
	
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//bank开头的字段都是融易付的结算信息，pbk开头的字段才是我们需要存储的结算信息
		request.setCharacterEncoding("UTF-8");
		log.info("--------------------开始商户信息同步-----------------------");
		String bank_acct = request.getParameter("bank_acct"); //结算银行账号   自动结算那一套
		log.info("自动结算---结算银行账号: " + bank_acct);
		String bank_name = request.getParameter("bank_name"); //结算银行名称    自动结算那一套
		log.info("自动结算---结算银行名称: " + bank_name); 
		String bank_no = request.getParameter("bank_no");  //结算银行联行号    自动结算那一套
		log.info("自动结算---结算银行联行号: " + bank_no);
		String bank_user = request.getParameter("bank_user"); //结算账户名称  自动结算那一套
		log.info("自动结算---结算账户名称: " + bank_user);
		String bank_sub = request.getParameter("bank_sub"); //结算支行名称  自动结算那一套
		log.info("自动结算---结算支行名称: " + bank_sub);
		String category = request.getParameter("category");   //商户类别 
		log.info("商户类别: " + category);
		String end_date = request.getParameter("end_date");   //合同结束日期    
		log.info("合同结束日期 : " + end_date);
		String gate_id = request.getParameter("gate_id");    //银行网关  
		log.info("银行网关: " + gate_id);
		String liq_limit = request.getParameter("liq_limit"); //结算最少金额 
		log.info("结算最少金额 : " + liq_limit);
		String liq_obj = request.getParameter("liq_obj");     //结算账户类型、商户对象 
		log.info("结算账户类型、商户对象 : " + liq_obj);
		String liq_period = request.getParameter("liq_period"); //结算周期
		log.info("结算周期: " + liq_period);
		String liq_state = request.getParameter("liq_state");  //结算状态 
		log.info("结算状态 : " + liq_state);
		String liq_type = request.getParameter("liq_type");    //结算方式 
		log.info("结算方式: " + liq_type);
        String mer_type = request.getParameter("mer_type");    //商户类型
        log.info("商户类型: " + mer_type);
        String name = request.getParameter("name");     //商户名称 
        log.info("商户名称 : " + name);
        String org_id = request.getParameter("org_id"); //电银商户号
        log.info("电银商户号: " + org_id);
        String pbk_acc_name = request.getParameter("pbk_acc_name");//结算账户名称  自动代付那一套
        log.info("自动代付---结算账户名称: " + pbk_acc_name);
        String pbk_acc_no = request.getParameter("pbk_acc_no");//结算账户号               自动代付那一套
        log.info("自动代付---结算账户号: " + pbk_acc_no); 
        String pbk_no = request.getParameter("pbk_no");//结算银行联行号                    自动代付那一套
        log.info("自动代付---结算银行联行号: " + pbk_no);
        String pbk_name = request.getParameter("pbk_name");//结算银行名称                     自动代付那一套
        log.info("自动代付---结算银行名称: " +  pbk_name);
        String pbk_branch = request.getParameter("pbk_branch");//结算支行名称                     自动代付那一套
        log.info("自动代付---结算支行名称: " +  pbk_branch);
        String province = request.getParameter("province");//所在省
        log.info("所在省: " + province);
        String short_name = request.getParameter("short_name");//商户简称
        log.info("商户简称: " + short_name);
        String start_date = request.getParameter("start_date");//合同起始日期
        log.info("合同起始日期: " + start_date);
        String status = request.getParameter("status");//商户状态
        log.info("商户状态: " + status);
        String user_id = request.getParameter("user_id");//电银账户号
        log.info("电银账户号: " + user_id);
        String man_liq = request.getParameter("man_liq");//手工结算
        log.info("手工结算: " + man_liq);
        String is_auto_df = request.getParameter("is_auto_df"); //是否自动代付
        log.info("是否自动代付: " + is_auto_df);
        String refund_fee = request.getParameter("refund_fee");//退款是否退手续费
        log.info("退款是否退手续费 ："+refund_fee);
     try{
        //商户基本信息
        MerBasic merBasic = new MerBasic();
        merBasic.setChannel(null);    //所属交易渠道
        merBasic.setEndDate((StringUtils.isBlank(end_date) || "null".equals(end_date)) ? 0 : Integer.valueOf(end_date));  //合同结束日期
        merBasic.setMerAbbreviation(short_name);  //商户简称
        //融易付商户类别 0–RYF商户  1–VAS商户 2–POS商户
        merBasic.setMerCategory(category == null ? null : Integer.valueOf(category)); //商户类别 1：线上商户、2：线下商户
        merBasic.setMerName(name); //商户名称
        //融易付商户状态 0-正常 1-关闭  pos对账商户状态 5：正常/6：关闭
        if("0".equals(status)){
        	 merBasic.setMerState(5);
        }else if("1".equals(status)){
        	 merBasic.setMerState(6);
        }else {
        	throw new IllegalArgumentException("商户状态数据格式不对应为{0 or 1}实际为"+ status +"");
        }
       
        //融易付商户类型 0-企业1-个人2-集团    POS对账商户类型 1：企业/2：个人/3：集团
        if("0".equals(mer_type)){
        	 merBasic.setMerType(1);
        }else if("1".equals(mer_type)){
        	 merBasic.setMerType(2);
        }else if("2".equals(mer_type)){
        	merBasic.setMerType(3);
        }else{
        	throw new IllegalArgumentException("商户类别数据格式不对应为{0 or 1 or 2}实际为"+ mer_type +"");
        }
      
        merBasic.setProvinces(province);  //所在省
        merBasic.setStartDate((StringUtils.isBlank(start_date) || "null".equals(start_date)) ? 0 : Integer.valueOf(start_date)); //合同开始日期
        if(org_id != null){      
        	   merBasic.setId(org_id); //商户号
        }else{
        	throw new NullPointerException("商户号不能为空");
        }
     
        //商户结算信息
        MerBilling merBilling = new MerBilling();
        merBilling.setRefundFee(StringUtils.isNotBlank(refund_fee) ? Integer.valueOf(refund_fee) : 0);//默认是1  0 – 不退手续费、1 – 退还手续费
        merBilling.setBilAccount(user_id);          //电银结算账号
        if("1".equals(is_auto_df)){
        	//使用自动代付的数据信息pbk开头的数据
        	   merBilling.setBilAccountname(pbk_acc_name); //结算账户名称
               merBilling.setBilBank(pbk_no);             //结算银行联行号
               merBilling.setBilBankaccount(pbk_acc_no);   //结算银行账号
               merBilling.setBilBankname(pbk_name);        //结算银行名称
               merBilling.setBankBranch(pbk_branch);       //结算支行名称
        }else{
        	//使用自动结算的数据信息bank开头的数据
        	  merBilling.setBilAccountname(bank_user); //结算账户名称
              merBilling.setBilBank(bank_no);             //结算银行联行号 
              merBilling.setBilBankaccount(bank_acct);   //结算银行账号
              merBilling.setBilBankname(bank_name);        //结算银行名称
              merBilling.setBankBranch(bank_sub);       //结算支行名称
        }
        //融易付结算周期 1–每天清算一次  2–每周清算一次 3–每周清算两次  4–每月清算一次  5–其它，Pos对账 结算周期 1:每日结算一次/2:每周结算一次/3:每月结算一次
        //转换方式为：对应上的就对应处理，融易付结算周期为2、3的都对应Pos对账的2
        if("1".equals(liq_period)){
        	 merBilling.setBilCycle(1);
        }else if("2".equals(liq_period) || "3".equals(liq_period)){
        	 merBilling.setBilCycle(2);
        }else if("4".equals(liq_period)){
        	 merBilling.setBilCycle(3);
        }else {
        	throw new IllegalArgumentException("结算周期数据格式不正确应为{1 or 2 or 3 or 4}实际为"+ liq_period +"");
        }
       
        //融易付手工结算 0-关闭  1-开启 但是pos对账  手工结算 1：开通/2:关闭
        if("0".equals(man_liq)){
        	merBilling.setBilManual(2);
        }else if("1".equals(man_liq)){
        	merBilling.setBilManual(1);
        }else{
        	throw new IllegalArgumentException("手工结算方式不对应为{0 or 1}实际为"+ man_liq +"");
        }
        
        merBilling.setBilSmallamt(liq_limit);      //结算最少金额
        
        ///融易付结算结算状态 0-正常  1-冻结    pos对账 1： 正常  2：冻结
        if("0".equals(liq_state)){
        	  merBilling.setBilStatus(1);
        }else if("1".equals(liq_state)){
        	merBilling.setBilStatus(2);
        }else{
        	throw new IllegalArgumentException("结算状态数据不对应为{0 or 1}实际为"+ liq_state +"");
        }
      
        //融易付结算对象0-银行卡  1-电银账户，pos对账 结算类型 1:银行卡账号、2:电银账号   结算对象  1：银行账户/2：电银账户/3：账户系统-企业/4:账户系统-个人
        if("0".equals(liq_obj)){
        	merBilling.setBilObject(1);
        	merBilling.setBilType(1);
        }else if("1".equals(liq_obj)){
        	merBilling.setBilObject(2);
        	merBilling.setBilType(2);
        }else{
        	throw new IllegalArgumentException("结算对象数据不对应为{0 or 1}实际为"+ liq_obj +"");
        }
        
        merBilling.setBilWay((StringUtils.isBlank(liq_type) || "null".equals(liq_type)) ? 0 : Integer.valueOf(liq_type)); //结算方式 1：全额/2：净额 为空的话就填0
        merBilling.setMerCode(merBasic);           //电银商户号
//      merBilling.setMerPoundage(merPoundage); //商户签约手续费 TODO
        
        boolean fiag = MerchantService.handleRYFMerInfo(merBasic, merBilling);
        if(fiag){
        	log.info("开始更新内存商户信息和商户结算信息");
        	 //操作数据库成功则更新内存中商户基本信息和商户结算信息
        	 Backstage.getInstance().setMerBasicMap(org_id, merBasic);
   		     Backstage.getInstance().setMerBillingMap(org_id, merBilling);
   		     //返回success信息给融易付
   		     log.info("同步商户信息成功，返回融易付success"); 
   		     response.getWriter().print("success");
        }else{
        	 log.error("同步商户信息失败，返回融易付failed");
        	 response.getWriter().print("failed");
        }
     }catch(Exception e){
    	 log.error("同步商户信息出现异常 " + e.getMessage());
    	 response.getWriter().print("failed");
       }
	}
}
