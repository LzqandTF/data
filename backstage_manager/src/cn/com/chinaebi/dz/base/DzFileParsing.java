package cn.com.chinaebi.dz.base;

import cn.com.chinaebi.dz.object.BankInst;
import cn.com.chinaebi.dz.object.InstInfo;

/**
 * 对账文件解析类
 * @author Administrator
 *
 */
public interface DzFileParsing {
	/**
	 * @param filePath	文件路径
	 * @param date		清算日期
	 * @param bankInst	银行网关
	 * @throws Exception	系统异常
	 */
    public void parseBankDzFile(String filePath,String date,BankInst bankInst) throws Exception;
}
