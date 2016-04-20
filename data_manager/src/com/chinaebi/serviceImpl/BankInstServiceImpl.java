package com.chinaebi.serviceImpl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.com.chinaebi.dz.webservice.BankInstUpdateRamDocument;
import cn.com.chinaebi.dz.webservice.BankInstUpdateRamType;

import com.chinaebi.dao.BankInstDao;
import com.chinaebi.entity.BankInst;
import com.chinaebi.exception.DeleteException;
import com.chinaebi.exception.InsertException;
import com.chinaebi.exception.PageException;
import com.chinaebi.exception.UpdateException;
import com.chinaebi.service.BankInstService;
import com.chinaebi.utils.HttpUtil;
import com.chinaebi.utils.RequestUrlConf;
import com.chinaebi.utils.ReturnXmlHandler;
import com.chinaebi.utils.mybaits.Page;

@Service(value = "bankInstService")
public class BankInstServiceImpl implements BankInstService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Qualifier(value = "bankInstDao")
	private BankInstDao bankInstDao;
	
	@Override
	public Page<BankInst> queryPageBankInst(Page<BankInst> page,Map<String, Object> map) {
		Page<BankInst> pageList = null;
		try {
			pageList = bankInstDao.queryPageBankInst(page, map);
			if (pageList.getResult() == null) {
				throw new PageException("bankInstDao.queryPageBankInst(page, map) 查询结果为NULL");
			}
		} catch (Exception e) {
			logger.error("获取银行机构数据出现异常：" + e.getMessage());
		}
		return pageList;
	}

	@Override
	public int addBankInst(Map<String, Object> map) {
		int effectNum = 0;
		try {
			effectNum = bankInstDao.addBankInst(map);
			if ( !(effectNum > 0 ) ) {
				throw new InsertException("bankInstDao.addBankInst(map)  添加银行机构失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public int updateBankInstByBankId(Map<String, Object> map) {
		int effectNum = 0;
		try {
			effectNum = bankInstDao.updateBankInstByBankId(map);
			if (!(effectNum > 0)) {
				throw new UpdateException("bankInstDao.updateBankInstByBankId(map)  修改银行机构失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public int deleteBankInstByBankId(String bankId) {
		int effectNum = 0;
		try {
			effectNum = bankInstDao.deleteBankInstByBankId(bankId);
			if (!(effectNum > 0)) {
				throw new DeleteException("bankInstDao.deleteBankInstByBankId(bankId)  删除银行机构失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return effectNum;
	}

	@Override
	public List<BankInst> queryAllBankInst() {
		List<BankInst> list = null;
		try {
			list = bankInstDao.queryAllBankInst();
			if (list != null && list.size() > 0) {
				return list;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return list;
	}

	@Override
	public boolean updateRamBankInstInfo(int bankId) {
		boolean flag = false;
		try {
			BankInstUpdateRamDocument document = BankInstUpdateRamDocument.Factory.newInstance();
			BankInstUpdateRamType type = document.addNewBankInstUpdateRam();
			type.setTrace("011");
			type.setBankId(bankId);
			logger.info("向后台发送更新内存中银行机构信息请求");
			String xmlString = HttpUtil.sendPostRequest(RequestUrlConf.url, "xmlString="+document.toString(), "utf-8");
			logger.info("后台更新内存中银行机构信息请求信息："+xmlString);
			flag = ReturnXmlHandler.xmlStringHandling(xmlString);
			if(flag){
				logger.info("更新内存中银行机构信息成功");
				flag = true;
			}else{
				logger.info("更新内存中银行机构信息失败");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return flag;
	}

	@Override
	public BankInst queryBankInstByBankName(String bankName) {
		BankInst bankInst = null;
		try {
			bankInst = bankInstDao.queryBankInstByBankName(bankName);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return bankInst;
	}

}
