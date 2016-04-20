package com.chinaebi.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chinaebi.dao.InstMerRateConfigDao;
import com.chinaebi.dao.InstRateDao;
import com.chinaebi.dao.InstRateMccConfigDao;
import com.chinaebi.entity.InstMerRateConfig;
import com.chinaebi.entity.InstRate;
import com.chinaebi.entity.InstRateMccConfig;
import com.chinaebi.service.InstRateService;
import com.chinaebi.utils.StringUtils;
import com.chinaebi.utils.mybaits.Page;

@Service(value="instRateService")
public class InstRateServiceImpl implements InstRateService{
	
	private static final Logger log = LoggerFactory.getLogger(InstRateServiceImpl.class);
	
	@Autowired
	@Qualifier(value="instRateDao")
	private InstRateDao instRateDao;
	
	@Autowired
	@Qualifier(value="instRateMccConfigDao")
	private InstRateMccConfigDao instRateMccConfigDao;
	
	@Autowired
	@Qualifier(value="instMerRateConfigDao")
	private InstMerRateConfigDao instMerRateConfigDao;

	@Override
	public Page<InstRate> queryPageInstRate(Page<InstRate> page,
			Map<String, Object> map) {
		Page<InstRate> page_ = null;
		try{
			page_ = instRateDao.queryPageInstRate(page, map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return page_;
	}

	@Override
	public int addInstRate(InstRate instRate) {
		int result = 0;
		try{
			String mccDalieArr = instRate.getMccDaLeiArr();
			String mccXilieArr = instRate.getMccXiLeiArr();
			String deductMerCodeArr = instRate.getDeductMerCodeArr();
			String cardTypeArr = instRate.getCardTypeArr();
			String bankTypeArr = instRate.getBankTypeArr();
			String channelFeeArr = instRate.getChannelFeeArr();
			
			result += instRateDao.addInstRate(instRate);
			
			if(StringUtils.isNotBlank(mccDalieArr) && StringUtils.isNotBlank(mccXilieArr)){//按mcc费率计算
				String[] mccDaLei = mccDalieArr.split(",");
				String[] mccXiLei = mccXilieArr.split(",");
				InstRateMccConfig instRateMccConfig = null;
				result += mccDaLei.length;
				for (int i = 0; i < mccXiLei.length; i++) {
					instRateMccConfig = new InstRateMccConfig();
					instRateMccConfig.setInst_id(instRate.getInst_id());
					instRateMccConfig.setInst_type(instRate.getInst_type());
					instRateMccConfig.setMcc_b_type(Integer.valueOf(mccDaLei[i]));
					instRateMccConfig.setMcc_s_type(Integer.valueOf(mccXiLei[i]));
					instRateMccConfig.setWhether_return_fee(instRate.getWhetherReturnFee());
					if(instRateMccConfigDao.checkInstRateMccConfigExistOrNot(instRateMccConfig) > 0){//查询数据库，是否已有相同配置数据,若存在相同配置，则跳过本条数据，执行下一条数据,反之，保存该条数据
						result--;
						continue;
					}else{
						instRateMccConfigDao.addInstRateMccConfig(instRateMccConfig);
						result--;
					}
				}
			}
			
			/*按固定费率计算*/
			if(StringUtils.isNotBlank(deductMerCodeArr) && StringUtils.isNotBlank(cardTypeArr) && StringUtils.isNotBlank(bankTypeArr) && StringUtils.isNotBlank(channelFeeArr)){
				String[] deductMerCode = deductMerCodeArr.split(",");
				String[] cardType = cardTypeArr.split(",");
				String[] bankType = bankTypeArr.split(",");
				String[] channelFee = channelFeeArr.split(";");
				InstMerRateConfig instMerRateConfig = null;
				result += deductMerCode.length;
				for (int i = 0; i < channelFee.length; i++) {
					instMerRateConfig = new InstMerRateConfig();
					instMerRateConfig.setMer_code(deductMerCode[i]);
					instMerRateConfig.setInst_id(instRate.getInst_id());
					instMerRateConfig.setInst_type(instRate.getInst_type());
					instMerRateConfig.setCard_type(cardType[i]);
					instMerRateConfig.setLineOrinter(Integer.valueOf(bankType[i]));
					instMerRateConfig.setFee_Poundage(channelFee[i]);
					instMerRateConfig.setG_type(instRate.getInst_type());
					instMerRateConfig.setGid(instRate.getInst_id());
					instMerRateConfig.setUser_name(instRate.getUser_name());
					if(instMerRateConfigDao.checkInstMerRateConfigExistOrNot(instMerRateConfig)){
						result--;
						continue;
					}else{
						instMerRateConfigDao.addInstMerRateConfig(instMerRateConfig);
						result--;
					}
				}
			}
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public int deleteInstRate(Map<String, Object> map) {
		int result = 0;
		try{
			result = instRateDao.deleteInstRate(map);
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public int updateInstRate(InstRate instRate) {
		int result = 0;
		try{
			
			String mccDalieArr = instRate.getMccDaLeiArr();
			String mccXilieArr = instRate.getMccXiLeiArr();
			String deductMerCodeArr = instRate.getDeductMerCodeArr();
			String cardTypeArr = instRate.getCardTypeArr();
			String bankTypeArr = instRate.getBankTypeArr();
			String channelFeeArr = instRate.getChannelFeeArr();
			String operTypeArr = instRate.getOperTypeArr();
			
			result += instRateDao.updateInstRate(instRate);
			
			if(StringUtils.isNotBlank(mccDalieArr) && StringUtils.isNotBlank(mccXilieArr)){
				
				InstRateMccConfig parameter = new InstRateMccConfig();
				parameter.setInst_id(instRate.getInst_id());
				parameter.setInst_type(instRate.getInst_type());
				int delete_result = instRateMccConfigDao.deleteInstRateMccConfig(parameter);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("inst_id", instRate.getInst_id());
				map.put("inst_type", instRate.getInst_type());
				int delete_result_ = instMerRateConfigDao.deleteInstMerRateConfigByInstIdAndInstType(map);
				
				if(delete_result >= 0 && delete_result_ >= 0){
					String[] mccDaLei = mccDalieArr.split(",");
					String[] mccXiLei = mccXilieArr.split(",");
					InstRateMccConfig instRateMccConfig = null;
					result += mccDaLei.length;
					for (int i = 0; i < mccXiLei.length; i++) {
						instRateMccConfig = new InstRateMccConfig();
						instRateMccConfig.setInst_id(instRate.getInst_id());
						instRateMccConfig.setInst_type(instRate.getInst_type());
						instRateMccConfig.setMcc_b_type(Integer.valueOf(mccDaLei[i]));
						instRateMccConfig.setMcc_s_type(Integer.valueOf(mccXiLei[i]));
						instRateMccConfig.setWhether_return_fee(instRate.getWhetherReturnFee());
						if(instRateMccConfigDao.checkInstRateMccConfigExistOrNot(instRateMccConfig) > 0){//查询数据库，是否已有相同配置数据,若存在相同配置，则跳过本条数据，执行下一条数据,反之，保存该条数据
							result--;
							continue;
						}else{
							instRateMccConfigDao.addInstRateMccConfig(instRateMccConfig);
							result--;
						}
					}
				}
			}else if(StringUtils.isNotBlank(deductMerCodeArr) && StringUtils.isNotBlank(cardTypeArr) && StringUtils.isNotBlank(bankTypeArr) && StringUtils.isNotBlank(channelFeeArr) && StringUtils.isNotBlank(operTypeArr)){
				
				InstRateMccConfig parameter = new InstRateMccConfig();
				parameter.setInst_id(instRate.getInst_id());
				parameter.setInst_type(instRate.getInst_type());
				int delete_result_ = instRateMccConfigDao.deleteInstRateMccConfig(parameter);
				if(delete_result_ >= 0){
					String[] deductMerCode = deductMerCodeArr.split(",");
					String[] cardType = cardTypeArr.split(",");
					String[] bankType = bankTypeArr.split(",");
					String[] channelFee = channelFeeArr.split(";");
					String[] operType = operTypeArr.split(",");
					InstMerRateConfig instMerRateConfig = null;
					result += result + deductMerCode.length;
					for (int i = 0; i < channelFee.length; i++) {
						if(!"0".equals(operType[i])){
							instMerRateConfig = new InstMerRateConfig();
							instMerRateConfig.setMer_code(deductMerCode[i]);
							instMerRateConfig.setInst_id(instRate.getInst_id());
							instMerRateConfig.setInst_type(instRate.getInst_type());
							instMerRateConfig.setCard_type(cardType[i]);
							instMerRateConfig.setLineOrinter(Integer.valueOf(bankType[i]));
							instMerRateConfig.setFee_Poundage(channelFee[i]);
							if("1".equals(operType[i])){//新增数据
								if(instMerRateConfigDao.checkInstMerRateConfigExistOrNot(instMerRateConfig)){
									result--;
									continue;
								}else{
									instMerRateConfig.setG_type(instRate.getInst_type());
									instMerRateConfig.setGid(instRate.getInst_id());
									instMerRateConfig.setUser_name(instRate.getUser_name());
									instMerRateConfigDao.addInstMerRateConfig(instMerRateConfig);
									result--;
								}
							}else if("2".equals(operType[i])){//删除数据
								result = result - instMerRateConfigDao.deleteInstMerRateConfig(instMerRateConfig);
							}
						}
					}
				}
			}else{
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("inst_id", instRate.getInst_id());
				map.put("inst_type", instRate.getInst_type());
				instMerRateConfigDao.deleteInstMerRateConfigByInstIdAndInstType(map);
				InstRateMccConfig parameter = new InstRateMccConfig();
				parameter.setInst_id(instRate.getInst_id());
				parameter.setInst_type(instRate.getInst_type());
				instRateMccConfigDao.deleteInstRateMccConfig(parameter);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return result;
	}

	@Override
	public List<InstRate> queryInstRateInstId() {
		List<InstRate> list = null;
		try{
			list = instRateDao.queryInstRateInstId();
		}catch(Exception e){
			log.error(e.getMessage());
		}
		return list;
	}

}
