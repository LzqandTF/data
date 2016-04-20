package cn.com.chinaebi.dz.object.dao.iface;

import java.io.Serializable;
import java.util.List;

import cn.com.chinaebi.dz.object.RytShyh;
import cn.com.chinaebi.dz.object.RytXyyh;

public interface RytXyyhDAO {
	public cn.com.chinaebi.dz.object.RytXyyh get(java.lang.Long key);

	public cn.com.chinaebi.dz.object.RytXyyh load(java.lang.Long key);

	public java.util.List<cn.com.chinaebi.dz.object.RytXyyh> findAll ();


	/**
	 * Persist the given transient instance, first assigning a generated identifier. (Or using the current value
	 * of the identifier property if the assigned generator is used.) 
	 * @param rytXyyh a transient instance of a persistent class 
	 * @return the class identifier
	 */
	public java.lang.Long save(cn.com.chinaebi.dz.object.RytXyyh rytXyyh);

	/**
	 * Either save() or update() the given instance, depending upon the value of its identifier property. By default
	 * the instance is always saved. This behaviour may be adjusted by specifying an unsaved-value attribute of the
	 * identifier property mapping. 
	 * @param rytXyyh a transient instance containing new or updated state 
	 */
	public void saveOrUpdate(cn.com.chinaebi.dz.object.RytXyyh rytXyyh);

	/**
	 * Update the persistent state associated with the given identifier. An exception is thrown if there is a persistent
	 * instance with the same identifier in the current session.
	 * @param rytXyyh a transient instance containing updated state
	 */
	public void update(cn.com.chinaebi.dz.object.RytXyyh rytXyyh);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param id the instance ID to be removed
	 */
	public void delete(java.lang.Long id);

	/**
	 * Remove a persistent instance from the datastore. The argument may be an instance associated with the receiving
	 * Session or a transient instance with an identifier associated with existing persistent state. 
	 * @param rytXyyh the instance to be removed
	 */
	public void delete(cn.com.chinaebi.dz.object.RytXyyh rytXyyh);

	/**
	 * 更新对账处理结果
	 * @param tseq ：流水号
	 * @param bk_chk ：是否对账成功
	 * @param whetherQs ：是否清算
	 * @param zf_fee ：银行手续费(平台计算)
	 * @param zf_file_fee ：对账文件手续费(文件获取)
	 * @param mer_fee : 商户手续费
	 * @param deductStlmDate ：清算日期,默认值0、与对账单数据勾对上时,该笔数据会赋值一个清算日期(对账日期)
	 * @return
	 */
	public boolean updateRytXyyh(String tseq,int bk_chk,boolean whetherQs,double zf_fee,String zf_file_fee,double mer_fee,Integer whetherReturnFee,Integer deductStlmDate);
	
	/**
	 * 查询交易金额、交易状态
	 * @param tseq ：流水号(参数)
	 * @param Object[0] : sys_date int
	 * @param Object[1] : sys_time int
	 * @param Object[2] : tstat    int
	 * @param Object[3] : tseq     int
	 * @param Object[4] : card_no  String
	 * @param Object[5] : amount   int
	 * @param Object[6] : mer_fee  int
	 * @param Object[7] : mid      String
	 * @param Object[8] : gid      int
	 * @param Object[9] : gate     int
	 * @return 以上为返回值
	 */
	public Object[] selectTradeAmount(String tseq);
	
	/**
	 * 根据交易日期查询数据
	 * @param sysDate
	 * @param gid
	 * @param bk_chk
	 * @return
	 */
	public List findRytXyyhList(String sysDate,int gid,int bk_chk);

	/**
	 * 根据日期查询成功交易、未对账数据
	 * @param sysDate ：日期
	 * @param tstat : 交易状态
	 * @param bk_chk ：对账结果
	 * @return List<RytKuaiqian>
	 */
	public List<RytXyyh> selectRytXyyh(String sysDate,Integer tstat,Integer bk_chk);
	
	/**
	 * 查询交易数据是否存在
	 * @param sysDate ：交易日期
	 * @return Integer
	 */
	public Integer selectRytXyyhCount(String sysDate);
	
	/**
	 * 更新待支付数据为无需对账
	 * @param sysDate ：日期yyyy-MM-dd
	 * @param bk_chk ：无需对账
	 * @param tstat ：交易状态
	 * @param whetherQs ：是否清算
	 * @return boolean
	 */
	public boolean updateNoNeedHandle(String sysDate,Integer bk_chk,Integer tstat,boolean whetherQs);
}