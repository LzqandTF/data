package com.chinaebi.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinaebi.entity.MerInfo;


public class SendPostToManagerUtil {
	private static final String SUCESS_FLAG = "0";//查询成功
	private static final String NO_AGENT_DATA = "E0001";//没有此代理商数据
	private static final String NO_MER_DATA = "E0002";//查询不到商户数据
	private static final String SYS_ERROR = "E0003";//系统错误
	
	private static final Logger log = LoggerFactory.getLogger(SendPostToManagerUtil.class);
	/**
	 * 通过代理商渠道号，向管理平台发送接口，返回商户集合
	 * @param agentId
	 * @return
	 */
	public static String getMerInfoList(HttpSession session,String url,String agentId){
		String num_message = "0";
		String agentName = "";
		try{
			String result = HttpUtil.sendPostRequest(url+"?agentId="+agentId, "", "utf-8");
			if(StringUtils.isNotBlank(result)){
				JaxbMapper jaxbMapper = new JaxbMapper(ResDTO.class);
				ResDTO dto = jaxbMapper.fromXml(result);
				System.out.println(result);
				if(dto != null){
					StatusDTO sto = dto.getStatus();
					OrgDTO oto = dto.getOrganization();
					if(sto != null && oto != null){
						agentName = oto.getOrganizationname();
						if(SUCESS_FLAG.equals(sto.getValue())){
							if(dto.getList() != null && dto.getList().getMerInfo() != null && dto.getList().getMerInfo().size() > 0){
								List<MerInfo> list_ = new ArrayList<MerInfo>();
								for (MerInfoDTO merInfo : dto.getList().getMerInfo()) {
									MerInfo mer = new MerInfo();
									mer.setInnerMercode(merInfo.getInnerMercode());
									mer.setMerName(merInfo.getMerName());
									list_.add(mer);
								}
								num_message = list_.size()+"";
								session.setAttribute("mer_list", list_);
							}
						}else if(NO_AGENT_DATA.equals(sto.getValue())){
							num_message = NO_AGENT_DATA;
							log.info("无此代理商数据");
						}else if(NO_MER_DATA.equals(sto.getValue())){
							num_message = NO_MER_DATA;
							log.info("查询不到商户数据");
						}else if(SYS_ERROR.equals(sto.getValue())){
							num_message = SYS_ERROR;
							log.info("通过代理商渠道号向管理平台发接口，管理平台返回信息为'系统错误'");
						}
					}
				}
			}
		}catch(Exception e){
			num_message = "-1";
			log.error("通过代理商渠道号向管理平台发接口过程抛出异常："+e);
		}
		return num_message + ";" + agentName;
	}
	public static void main(String[] args) {
		try {
			
			List<MerInfo> list_ = new ArrayList<MerInfo>();
			
//			String result = "<res><status><value>0</value><msg>成功返回</msg></status><list><merInfo><innerMercode>999301040110001</innerMercode><merName>段洁</merName></merInfo><merInfo><innerMercode>999301040110002</innerMercode><merName>谢洁111</merName></merInfo><merInfo><innerMercode>001584058121148</innerMercode><merName>谢洁测试1</merName></merInfo></list></res>";
			String result = HttpUtil.sendPostRequest("http://192.168.18.66:8080/mms-web/MerInfoServlet?agentId="+100001, "", "utf-8");
//			String xmlString = "<res>"+
//					"<tranCode>POS-0000</tranCode>"+
//					"<status>"+
//					"<value>0</value>"+
//					"<msg>成功返回</msg>"+
//					"</status>"+
//					"<list>"+
//					"<merInfo>"+
//					"<innerMercode>999301040110001</innerMercode>"+
//					"<merName>段洁</merName>"+
//					"</merInfo>"+
//					"<merInfo>"+
//					"<innerMercode>999301040110002</innerMercode>"+
//					"<merName>谢洁111</merName>"+
//					"</merInfo>"+
//					"<merInfo>"+
//					"<innerMercode>001584058121148</innerMercode>"+
//					"<merName>谢洁测试1</merName>"+
//					"</merInfo>"+
//					"</list>"+
//					"</res>";
			
			JaxbMapper jaxbMapper = new JaxbMapper(ResDTO.class);
			ResDTO dto = jaxbMapper.fromXml(result);
			StatusDTO sto = dto.getStatus();
			OrgDTO oto = dto.getOrganization();
			System.out.println(oto.getOrganizationname());
			if(SUCESS_FLAG.equals(sto.getValue())){
				for (MerInfoDTO merInfo : dto.getList().getMerInfo()) {
					MerInfo mer = new MerInfo();
					mer.setInnerMercode(merInfo.getInnerMercode());
					mer.setMerName(merInfo.getMerName());
					list_.add(mer);
				}
			}else{
				System.out.println(sto.getValue());
			}
			System.out.println(list_.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
