package cn.com.chinaebi.dz.object.dao.iface;

import java.util.List;
import java.util.Map;

import cn.com.chinaebi.dz.object.ChannelDzCollect;
import cn.com.chinaebi.dz.object.InstInfo;
import cn.com.chinaebi.dz.object.InstInfoPK;

public interface ChannelDzCollectDAO {
	public cn.com.chinaebi.dz.object.ChannelDzCollect get(java.lang.String key);

	public cn.com.chinaebi.dz.object.ChannelDzCollect load(java.lang.String key);

	public java.util.List<cn.com.chinaebi.dz.object.ChannelDzCollect> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param channelDzCollect a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.String save(cn.com.chinaebi.dz.object.ChannelDzCollect channelDzCollect);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param channelDzCollect a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.ChannelDzCollect channelDzCollect);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param channelDzCollect a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.ChannelDzCollect channelDzCollect);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.String id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param channelDzCollect the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.ChannelDzCollect channelDzCollect);
	
	/**
	 * 保存线下渠道对账数据汇总
	 * @param instInfo : 渠道实体
	 * @param deductstlmdate ：清算日期
	 * @param gate ：网关号
	 * @param settleMerchantMatchMap ：结算商户对应电银商户号
	 * @param instName ：渠道名称
	 * @param mapMerBasic ：商户基本信息
	 * @return int
	 * @throws Exception
	 */
	public int savePosChannelDzCollect(InstInfo instInfo,String deductstlmdate,
			Map<String, String> settleMerchantMatchMap,Map<String, Object> mapMerBasic) throws Exception;
	
	/**
	 * 保存线上渠道对账数据汇总
	 * @param channelTableName ：渠道表名称
	 * @param sysDate ：交易日期
	 * @param instName ：渠道名称
	 * @param mapMerBasic ：商户基本信息
	 * @param instInfoMap : 渠道基本信息
	 * @return int
	 * @throws Exception
	 */
	public int saveRytChannelDzCollect(String channelTableName,Integer sysDate,Map<String, Object> mapMerBasic,Map<InstInfoPK, Object> instInfoMap) throws Exception;
	
	/**
	 * 保存融易付线上退款数据至对账总表中
	 * @param channelDzCollect
	 * @return
	 * @throws Exception
	 */
	public int saveRytTKChannelDzCollect(ChannelDzCollect channelDzCollect) throws Exception;
	
	/**
	 * 结算单明细查询接口，返回总笔数与总金额
	 * @param merCode 商户号
	 * @param startDate 起始日期
	 * @param endDate 结束日子
	 * @return
	 */
	public Object queryCountAndMoney(String merCode, String startDate, String endDate);
	
	/**
	 * 结算明细查询接口，返回分页查询数据
	 * @param merCode 商户号
	 * @param startDate 起始日期
	 * @param endDate 结束日期
	 * @param startRow 起始行
	 * @param endRow 结束行
	 * @return
	 */
	public List<ChannelDzCollect> queryChannelDzCollectLst(String merCode, String startDate, String endDate, int startRow, int endRow);
	
	/**
	 * 结算单明细下载接口，返回数据列表
	 * @param merCode 商户号
	 * @param startDate 起始日期
	 * @param endDate 结束日期
	 * @return
	 */
	public List<ChannelDzCollect> queryChannelDzCollectDataLst(String merCode, String startDate, String endDate);
	
}