package com.chinaebi.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.chinaebi.utils.Common;
import com.chinaebi.utils.HttpUtil;

import cn.com.chinaebi.dz.webservice.DataHandler;
import cn.com.chinaebi.dz.webservice.MerInfoHandlerReqDocument;
import cn.com.chinaebi.dz.webservice.MerInfoHandlerReqType;

public class Test {
	
	/**
	 * 读取txt文件中内容
	 * 
	 * @param filePath
	 */
	public static void readTxtFile(String filePath) {
		try {
			String encoding = "UTF-8"; // 设置读取时的编码格式
			File file = new File(filePath);
			if (file.isFile() && file.exists()) {// 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				
				//BufferedWriter writer = new BufferedWriter(new FileWriter(new File("C:/Documents and Settings/Administrator/桌面/test.txt")));
				StringBuilder sb = new StringBuilder();
				int bytesRead = 0;
				int i=0;
				while ((bytesRead = bufferedReader.read()) != -1) {
					if(i > 32){
						sb.append((char)bytesRead);
						//writer.write(bytesRead);
					}
					i++;
				}
				String str = Common.toMd5String(sb.toString() + "5683F572F29C0B45").toUpperCase();
				System.out.println(str);
				//writer.write(str);
				//writer.write("\n");
				//writer.write(sb.toString());
				bufferedReader.close();
				//writer.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		
		String filePath = "C:/Documents and Settings/Administrator/桌面/划款结果-电银账户报表.txt";
        readTxtFile(filePath);
        
//		try {
//			MerInfoHandlerReqDocument document = MerInfoHandlerReqDocument.Factory.newInstance();
//			MerInfoHandlerReqType type = document.addNewMerInfoHandlerReq();
//			type.setTrace("00");
//			type.setMerCategory(1);//商户类别
//			type.setMerName("上海");//商户名称
//			type.setMerAbbreviation("11");//商户简称 
//			type.setInnerMerCode("12312321");//电银商户号
//			type.setIndustry(2);//商户状态 
//			type.setProvince("1000|1027");//所属省份
//			type.setMerType(3);//商户类型
//			type.setStartDateFrom("2012-1-1");//合同起始日期
//			type.setEndDateFrom("2010-1-1");//合同结束日期
//			type.setTradeChannel("12321");//所属交易渠道
//			type.setMerExpand("1231");//商户拓展方(代理商名称)
//			type.setMerExpandNo("0000");//商户拓展方(代理商编号)
//			type.setClearingObject(1);//结算对象
//			type.setClearingBankNo("1042");//结算银行联行号
//			type.setClearingBankName("12");//结算银行名称
//			type.setClearingAccountName("1231");//结算账户名称
//			type.setClearingCardNo("000000");//结算银行账号
//			type.setClearingWay(1);//结算方式
//			type.setClearingMinMoney("0.01");//结算最少金额(元)
//			type.setClearingPeriod(1);//结算周期 
//			type.setHandworkClearing(1);//手工结算
//			type.setDyClearingNo("111111");//电银结算账号
//			type.setClearingStatus(2);//结算状态
//			type.setMerSignedFee("0.05");//商户签约手续费
//			type.setDataHandler(DataHandler.Enum.forString("add"));//数据处理  add : 增、delete : 删、update : 改
//			
//			System.out.println(document.toString());
//			String result = HttpUtil.sendPostRequest("http://192.168.18.203:8080/data_manager/merInfoServlet", "xmlString="+document.toString(),"utf-8");
//			System.out.println(result);		
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
