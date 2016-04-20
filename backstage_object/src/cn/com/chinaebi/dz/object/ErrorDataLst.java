package cn.com.chinaebi.dz.object;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chinaebi.dz.object.base.BaseErrorDataLst;
import cn.com.chinaebi.dz.object.dao.InstInfoDAO;
import cn.com.chinaebi.dz.util.DYDataUtil;



public class ErrorDataLst extends BaseErrorDataLst {
	private static final long serialVersionUID = 1L;
	
	private static Log log = LogFactory.getLog(ErrorDataLst.class);

/*[CONSTRUCTOR MARKER BEGIN]*/
	public ErrorDataLst () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public ErrorDataLst (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public ErrorDataLst (
		java.lang.String id,
		java.util.Date tradeTime,
		java.util.Date outAccValidTime,
		java.lang.Integer tradeType,
		java.util.Date reqTime,
		java.util.Date deductStlmDate,
		java.util.Date deductSysTime,
		java.lang.Integer bkChk,
		boolean whetherJs,
		boolean whetherValid,
		java.lang.Integer whetherErroeHandle,
		boolean whetherRiqie,
		java.util.Date deductRollbkSysTime,
		boolean whtherInnerJs,
		java.lang.Integer bankId) {

		super (
			id,
			tradeTime,
			outAccValidTime,
			tradeType,
			reqTime,
			deductStlmDate,
			deductSysTime,
			bkChk,
			whetherJs,
			whetherValid,
			whetherErroeHandle,
			whetherRiqie,
			deductRollbkSysTime,
			whtherInnerJs,
			bankId);
	}
	
	/**
	 * 线下深圳中行原始日切实体
	 * @param RiqieSzghLst
	 */
	public ErrorDataLst(RiqieSzghLst riqieSzghLst){
		this.setId(riqieSzghLst.getId());
		this.setTerminalId(riqieSzghLst.getTerminalId());
		this.setTerminalInfo(riqieSzghLst.getTerminalInfo());
		this.setTerminalType(riqieSzghLst.getTerminalType());
		this.setTradeTime(riqieSzghLst.getTradeTime());
		this.setOutAccount(riqieSzghLst.getOutAccount());
		this.setOutAccountType(riqieSzghLst.getOutAccountType());
		this.setOutAccValidTime(riqieSzghLst.getOutAccValidTime());
		this.setOutAccountInfo(riqieSzghLst.getOutAccountInfo());
		this.setOutAccountInfo2(riqieSzghLst.getOutAccountInfo2());
		this.setOutAccountDesc(riqieSzghLst.getOutAccountDesc());
		this.setInAccount(riqieSzghLst.getInAccount());
		this.setInCardName(riqieSzghLst.getInCardName());
		this.setInBankId(riqieSzghLst.getInBankId());
		this.setTradeAmount(riqieSzghLst.getTradeAmount());
		this.setTradeFee(riqieSzghLst.getTradeFee());
		this.setTradeCurrency(riqieSzghLst.getTradeCurrency());
		this.setTradeResult(riqieSzghLst.getTradeResult());
		this.setReqSysId(riqieSzghLst.getReqSysId());
		this.setReqSysStance(riqieSzghLst.getReqSysStance());
		this.setReqMerCode(riqieSzghLst.getReqMerCode());
		this.setReqMerTermId(riqieSzghLst.getReqMerTermId());
		this.setReqResponse(riqieSzghLst.getReqResponse());
		this.setDeductSysId(riqieSzghLst.getDeductSysId());
		this.setDeductSysStance(riqieSzghLst.getDeductSysStance());
		this.setDeductMerCode(riqieSzghLst.getDeductMerCode());
		this.setDeductMerTermId(riqieSzghLst.getDeductMerTermId());
		this.setDeductResult(riqieSzghLst.getDeductResult());
		this.setDeductSysResponse(riqieSzghLst.getDeductSysResponse());
		this.setDeductRollBk(riqieSzghLst.isDeductRollBk());
		this.setDeductRollBkResult(riqieSzghLst.getDeductRollBkResult());
		this.setDeductRollBkReason(riqieSzghLst.getDeductRollBkReason());
		this.setDeductRollBkResponse(riqieSzghLst.getDeductRollBkResponse());
		this.setDeductRollBkStance(riqieSzghLst.getDeductRollBkStance());
		this.setTradeType(riqieSzghLst.getTradeType());
		this.setMsgNum(riqieSzghLst.getMsgNum());
		this.setPassWdMode(riqieSzghLst.getPassWdMode());
		this.setReqType(riqieSzghLst.getReqType());
		this.setReqInputType(riqieSzghLst.getReqInputType());
		this.setReqTime(riqieSzghLst.getReqTime());
		this.setTradeReqMethod(riqieSzghLst.getTradeReqMethod());
		this.setTradeDesc(riqieSzghLst.getTradeDesc());
		this.setInAccountType(riqieSzghLst.getInAccountType());
		this.setTradeOtherInfo(riqieSzghLst.getTradeOtherInfo());
		this.setDeductStlmDate(riqieSzghLst.getDeductStlmDate());
		this.setDeductSysTime(riqieSzghLst.getDeductSysTime());
		this.setGainSysId(riqieSzghLst.getGainSysId());
		this.setGainSysStance(riqieSzghLst.getGainSysStance());
		this.setGainMerCode(riqieSzghLst.getGainMerCode());
		this.setGainMerTermId(riqieSzghLst.getGainMerTermId());
		this.setGainSysResponse(riqieSzghLst.getGainSysResponse());
		this.setGainResult(riqieSzghLst.getGainResult());
		this.setGainTradeAmount(riqieSzghLst.getGainTradeAmount());
		this.setGainSysReference(riqieSzghLst.getGainSysReference());
		this.setNii(riqieSzghLst.getNii());
		this.setAuthorizationCode(riqieSzghLst.getAuthorizationCode());
		this.setAdditionalResponseData(riqieSzghLst.getAdditionalResponseData());
		this.setAdditionalData(riqieSzghLst.getAdditionalData());
		this.setBoc(riqieSzghLst.getBoc());
		this.setCustomDefineInfo(riqieSzghLst.getCustomDefineInfo());
		this.setOriginalTransInfo(riqieSzghLst.getOriginalTransInfo());
		this.setReqProcess(riqieSzghLst.getReqProcess());
		this.setDeductSysReference(riqieSzghLst.getDeductSysReference());
		this.setTrademsgType(riqieSzghLst.getTrademsgType());
		this.setDeductRollbkSysReference(riqieSzghLst.getDeductRollbkSysReference());
		this.setMerName(riqieSzghLst.getMerName());
		this.setBkChk(riqieSzghLst.getBkChk());
		this.setWhetherJs(riqieSzghLst.isWhetherJs());
		this.setWhetherValid(riqieSzghLst.isWhetherValid());
		this.setWhetherErroeHandle(riqieSzghLst.getWhetherErroeHandle());
		this.setWhetherRiqie(riqieSzghLst.isWhetherRiqie());
		this.setAcqInstIdCode(riqieSzghLst.getAcqInstIdCode());
		this.setFwdInstIdCode(riqieSzghLst.getFwdInstIdCode());
		this.setDeductRollbkSysTime(riqieSzghLst.getDeductRollbkSysTime());
		this.setAgentId(riqieSzghLst.getAgentId());
		this.setWhetherzero(riqieSzghLst.getWhetherzero());
		this.setWhtherInnerJs(riqieSzghLst.isWhtherInnerJs());
		this.setIcCardSerNo(riqieSzghLst.getIcCardSerNo());
		this.setIcReadAndCondition(riqieSzghLst.getIcReadAndCondition());
		this.setWhetherQs(riqieSzghLst.isWhetherQs());
		this.setMerFee(riqieSzghLst.getMerFee());
		this.setWhetherTk(riqieSzghLst.isWhetherTk());
		if(riqieSzghLst.getTradeAmount() < 0){
			this.setZfFee(0-riqieSzghLst.getZfFee());
			this.setZfFileFee("-"+riqieSzghLst.getZfFileFee());
		}else{
			this.setZfFee(riqieSzghLst.getZfFee());
			this.setZfFileFee(riqieSzghLst.getZfFileFee());
		}
		this.setZfFeeBj(riqieSzghLst.getZfFeeBj());
		this.setFeeFormula(riqieSzghLst.getFeeFormula());
		this.setWhetherAccessStance(riqieSzghLst.isWhetherAccessStance());
		this.setWhtherInnerDz(riqieSzghLst.isWhtherInnerDz());
		this.setBankId(riqieSzghLst.getBankId());
		this.setHandlingStatus(0);
		this.setErrorResource(0);
		this.setInstType(0);
	}
	
	/**
	 * 线下深圳中行原始实体
	 * @param OriginalSzghLst
	 */
	public ErrorDataLst(OriginalSzghLst originalSzghLst){
		this.setId(originalSzghLst.getId());
		this.setTerminalId(originalSzghLst.getTerminalId());
		this.setTerminalInfo(originalSzghLst.getTerminalInfo());
		this.setTerminalType(originalSzghLst.getTerminalType());
		this.setTradeTime(originalSzghLst.getTradeTime());
		this.setOutAccount(originalSzghLst.getOutAccount());
		this.setOutAccountType(originalSzghLst.getOutAccountType());
		this.setOutAccValidTime(originalSzghLst.getOutAccValidTime());
		this.setOutAccountInfo(originalSzghLst.getOutAccountInfo());
		this.setOutAccountInfo2(originalSzghLst.getOutAccountInfo2());
		this.setOutAccountDesc(originalSzghLst.getOutAccountDesc());
		this.setInAccount(originalSzghLst.getInAccount());
		this.setInCardName(originalSzghLst.getInCardName());
		this.setInBankId(originalSzghLst.getInBankId());
		this.setTradeAmount(originalSzghLst.getTradeAmount());
		this.setTradeFee(originalSzghLst.getTradeFee());
		this.setTradeCurrency(originalSzghLst.getTradeCurrency());
		this.setTradeResult(originalSzghLst.getTradeResult());
		this.setReqSysId(originalSzghLst.getReqSysId());
		this.setReqSysStance(originalSzghLst.getReqSysStance());
		this.setReqMerCode(originalSzghLst.getReqMerCode());
		this.setReqMerTermId(originalSzghLst.getReqMerTermId());
		this.setReqResponse(originalSzghLst.getReqResponse());
		this.setDeductSysId(originalSzghLst.getDeductSysId());
		this.setDeductSysStance(originalSzghLst.getDeductSysStance());
		this.setDeductMerCode(originalSzghLst.getDeductMerCode());
		this.setDeductMerTermId(originalSzghLst.getDeductMerTermId());
		this.setDeductResult(originalSzghLst.getDeductResult());
		this.setDeductSysResponse(originalSzghLst.getDeductSysResponse());
		this.setDeductRollBk(originalSzghLst.isDeductRollBk());
		this.setDeductRollBkResult(originalSzghLst.getDeductRollBkResult());
		this.setDeductRollBkReason(originalSzghLst.getDeductRollBkReason());
		this.setDeductRollBkResponse(originalSzghLst.getDeductRollBkResponse());
		this.setDeductRollBkStance(originalSzghLst.getDeductRollBkStance());
		this.setTradeType(originalSzghLst.getTradeType());
		this.setMsgNum(originalSzghLst.getMsgNum());
		this.setPassWdMode(originalSzghLst.getPassWdMode());
		this.setReqType(originalSzghLst.getReqType());
		this.setReqInputType(originalSzghLst.getReqInputType());
		this.setReqTime(originalSzghLst.getReqTime());
		this.setTradeReqMethod(originalSzghLst.getTradeReqMethod());
		this.setTradeDesc(originalSzghLst.getTradeDesc());
		this.setInAccountType(originalSzghLst.getInAccountType());
		this.setTradeOtherInfo(originalSzghLst.getTradeOtherInfo());
		this.setDeductStlmDate(originalSzghLst.getDeductStlmDate());
		this.setDeductSysTime(originalSzghLst.getDeductSysTime());
		this.setGainSysId(originalSzghLst.getGainSysId());
		this.setGainSysStance(originalSzghLst.getGainSysStance());
		this.setGainMerCode(originalSzghLst.getGainMerCode());
		this.setGainMerTermId(originalSzghLst.getGainMerTermId());
		this.setGainSysResponse(originalSzghLst.getGainSysResponse());
		this.setGainResult(originalSzghLst.getGainResult());
		this.setGainTradeAmount(originalSzghLst.getGainTradeAmount());
		this.setGainSysReference(originalSzghLst.getGainSysReference());
		this.setNii(originalSzghLst.getNii());
		this.setAuthorizationCode(originalSzghLst.getAuthorizationCode());
		this.setAdditionalResponseData(originalSzghLst.getAdditionalResponseData());
		this.setAdditionalData(originalSzghLst.getAdditionalData());
		this.setBoc(originalSzghLst.getBoc());
		this.setCustomDefineInfo(originalSzghLst.getCustomDefineInfo());
		this.setOriginalTransInfo(originalSzghLst.getOriginalTransInfo());
		this.setReqProcess(originalSzghLst.getReqProcess());
		this.setDeductSysReference(originalSzghLst.getDeductSysReference());
		this.setTrademsgType(originalSzghLst.getTrademsgType());
		this.setDeductRollbkSysReference(originalSzghLst.getDeductRollbkSysReference());
		this.setMerName(originalSzghLst.getMerName());
		this.setBkChk(originalSzghLst.getBkChk());
		this.setWhetherJs(originalSzghLst.isWhetherJs());
		this.setWhetherValid(originalSzghLst.isWhetherValid());
		this.setWhetherErroeHandle(originalSzghLst.getWhetherErroeHandle());
		this.setWhetherRiqie(originalSzghLst.isWhetherRiqie());
		this.setAcqInstIdCode(originalSzghLst.getAcqInstIdCode());
		this.setFwdInstIdCode(originalSzghLst.getFwdInstIdCode());
		this.setDeductRollbkSysTime(originalSzghLst.getDeductRollbkSysTime());
		this.setAgentId(originalSzghLst.getAgentId());
		this.setWhetherzero(originalSzghLst.getWhetherzero());
		this.setWhtherInnerJs(originalSzghLst.isWhtherInnerJs());
		this.setIcCardSerNo(originalSzghLst.getIcCardSerNo());
		this.setIcReadAndCondition(originalSzghLst.getIcReadAndCondition());
		this.setWhetherQs(originalSzghLst.isWhetherQs());
		this.setMerFee(originalSzghLst.getMerFee());
		this.setWhetherTk(originalSzghLst.isWhetherTk());
		if(originalSzghLst.getTradeAmount() < 0){
			this.setZfFee(0-originalSzghLst.getZfFee());
			this.setZfFileFee("-"+originalSzghLst.getZfFileFee());
		}else{
			this.setZfFee(originalSzghLst.getZfFee());
			this.setZfFileFee(originalSzghLst.getZfFileFee());
		}
		this.setZfFeeBj(originalSzghLst.getZfFeeBj());
		this.setFeeFormula(originalSzghLst.getFeeFormula());
		this.setWhetherAccessStance(originalSzghLst.isWhetherAccessStance());
		this.setWhtherInnerDz(originalSzghLst.isWhtherInnerDz());
		this.setBankId(originalSzghLst.getBankId());
		this.setHandlingStatus(0);
		this.setErrorResource(0);
		this.setInstType(0);
	}
	 
	
	/**
	 * 线下cups原始日切实体
	 * @param originalCupsLst
	 */
	public ErrorDataLst(RiqieCupsLst riqieCupsLst){
		this.setId(riqieCupsLst.getId());
		this.setTerminalId(riqieCupsLst.getTerminalId());
		this.setTerminalInfo(riqieCupsLst.getTerminalInfo());
		this.setTerminalType(riqieCupsLst.getTerminalType());
		this.setTradeTime(riqieCupsLst.getTradeTime());
		this.setOutAccount(riqieCupsLst.getOutAccount());
		this.setOutAccountType(riqieCupsLst.getOutAccountType());
		this.setOutAccValidTime(riqieCupsLst.getOutAccValidTime());
		this.setOutAccountInfo(riqieCupsLst.getOutAccountInfo());
		this.setOutAccountInfo2(riqieCupsLst.getOutAccountInfo2());
		this.setOutAccountDesc(riqieCupsLst.getOutAccountDesc());
		this.setInAccount(riqieCupsLst.getInAccount());
		this.setInCardName(riqieCupsLst.getInCardName());
		this.setInBankId(riqieCupsLst.getInBankId());
		this.setTradeAmount(riqieCupsLst.getTradeAmount());
		this.setTradeFee(riqieCupsLst.getTradeFee());
		this.setTradeCurrency(riqieCupsLst.getTradeCurrency());
		this.setTradeResult(riqieCupsLst.getTradeResult());
		this.setReqSysId(riqieCupsLst.getReqSysId());
		this.setReqSysStance(riqieCupsLst.getReqSysStance());
		this.setReqMerCode(riqieCupsLst.getReqMerCode());
		this.setReqMerTermId(riqieCupsLst.getReqMerTermId());
		this.setReqResponse(riqieCupsLst.getReqResponse());
		this.setDeductSysId(riqieCupsLst.getDeductSysId());
		this.setDeductSysStance(riqieCupsLst.getDeductSysStance());
		this.setDeductMerCode(riqieCupsLst.getDeductMerCode());
		this.setDeductMerTermId(riqieCupsLst.getDeductMerTermId());
		this.setDeductResult(riqieCupsLst.getDeductResult());
		this.setDeductSysResponse(riqieCupsLst.getDeductSysResponse());
		this.setDeductRollBk(riqieCupsLst.isDeductRollBk());
		this.setDeductRollBkResult(riqieCupsLst.getDeductRollBkResult());
		this.setDeductRollBkReason(riqieCupsLst.getDeductRollBkReason());
		this.setDeductRollBkResponse(riqieCupsLst.getDeductRollBkResponse());
		this.setDeductRollBkStance(riqieCupsLst.getDeductRollBkStance());
		this.setTradeType(riqieCupsLst.getTradeType());
		this.setMsgNum(riqieCupsLst.getMsgNum());
		this.setPassWdMode(riqieCupsLst.getPassWdMode());
		this.setReqType(riqieCupsLst.getReqType());
		this.setReqInputType(riqieCupsLst.getReqInputType());
		this.setReqTime(riqieCupsLst.getReqTime());
		this.setTradeReqMethod(riqieCupsLst.getTradeReqMethod());
		this.setTradeDesc(riqieCupsLst.getTradeDesc());
		this.setInAccountType(riqieCupsLst.getInAccountType());
		this.setTradeOtherInfo(riqieCupsLst.getTradeOtherInfo());
		this.setDeductStlmDate(riqieCupsLst.getDeductStlmDate());
		this.setDeductSysTime(riqieCupsLst.getDeductSysTime());
		this.setGainSysId(riqieCupsLst.getGainSysId());
		this.setGainSysStance(riqieCupsLst.getGainSysStance());
		this.setGainMerCode(riqieCupsLst.getGainMerCode());
		this.setGainMerTermId(riqieCupsLst.getGainMerTermId());
		this.setGainSysResponse(riqieCupsLst.getGainSysResponse());
		this.setGainResult(riqieCupsLst.getGainResult());
		this.setGainTradeAmount(riqieCupsLst.getGainTradeAmount());
		this.setGainSysReference(riqieCupsLst.getGainSysReference());
		this.setNii(riqieCupsLst.getNii());
		this.setAuthorizationCode(riqieCupsLst.getAuthorizationCode());
		this.setAdditionalResponseData(riqieCupsLst.getAdditionalResponseData());
		this.setAdditionalData(riqieCupsLst.getAdditionalData());
		this.setBoc(riqieCupsLst.getBoc());
		this.setCustomDefineInfo(riqieCupsLst.getCustomDefineInfo());
		this.setOriginalTransInfo(riqieCupsLst.getOriginalTransInfo());
		this.setReqProcess(riqieCupsLst.getReqProcess());
		this.setDeductSysReference(riqieCupsLst.getDeductSysReference());
		this.setTrademsgType(riqieCupsLst.getTrademsgType());
		this.setDeductRollbkSysReference(riqieCupsLst.getDeductRollbkSysReference());
		this.setMerName(riqieCupsLst.getMerName());
		this.setBkChk(riqieCupsLst.getBkChk());
		this.setWhetherJs(riqieCupsLst.isWhetherJs());
		this.setWhetherValid(riqieCupsLst.isWhetherValid());
		this.setWhetherErroeHandle(riqieCupsLst.getWhetherErroeHandle());
		this.setWhetherRiqie(riqieCupsLst.isWhetherRiqie());
		this.setAcqInstIdCode(riqieCupsLst.getAcqInstIdCode());
		this.setFwdInstIdCode(riqieCupsLst.getFwdInstIdCode());
		this.setDeductRollbkSysTime(riqieCupsLst.getDeductRollbkSysTime());
		this.setAgentId(riqieCupsLst.getAgentId());
		this.setWhetherzero(riqieCupsLst.getWhetherzero());
		this.setWhtherInnerJs(riqieCupsLst.isWhtherInnerJs());
		this.setIcCardSerNo(riqieCupsLst.getIcCardSerNo());
		this.setIcReadAndCondition(riqieCupsLst.getIcReadAndCondition());
		this.setWhetherQs(riqieCupsLst.isWhetherQs());
		this.setMerFee(riqieCupsLst.getMerFee());
		this.setWhetherTk(riqieCupsLst.isWhetherTk());
		if(riqieCupsLst.getTradeAmount() < 0){
			this.setZfFee(0-riqieCupsLst.getZfFee());
			this.setZfFileFee("-"+riqieCupsLst.getZfFileFee());
		}else{
			this.setZfFee(riqieCupsLst.getZfFee());
			this.setZfFileFee(riqieCupsLst.getZfFileFee());
		}
		this.setZfFeeBj(riqieCupsLst.getZfFeeBj());
		this.setFeeFormula(riqieCupsLst.getFeeFormula());
		this.setWhetherAccessStance(riqieCupsLst.isWhetherAccessStance());
		this.setWhtherInnerDz(riqieCupsLst.isWhtherInnerDz());
		this.setBankId(riqieCupsLst.getBankId());
		this.setHandlingStatus(0);
		this.setErrorResource(0);
		this.setInstType(0);
	}
	
	/**
	 * 线下cups原始交易实体
	 * @param originalCupsLst
	 */
	public ErrorDataLst(OriginalCupsLst originalCupsLst){
		this.setId(originalCupsLst.getId());
		this.setTerminalId(originalCupsLst.getTerminalId());
		this.setTerminalInfo(originalCupsLst.getTerminalInfo());
		this.setTerminalType(originalCupsLst.getTerminalType());
		this.setTradeTime(originalCupsLst.getTradeTime());
		this.setOutAccount(originalCupsLst.getOutAccount());
		this.setOutAccountType(originalCupsLst.getOutAccountType());
		this.setOutAccValidTime(originalCupsLst.getOutAccValidTime());
		this.setOutAccountInfo(originalCupsLst.getOutAccountInfo());
		this.setOutAccountInfo2(originalCupsLst.getOutAccountInfo2());
		this.setOutAccountDesc(originalCupsLst.getOutAccountDesc());
		this.setInAccount(originalCupsLst.getInAccount());
		this.setInCardName(originalCupsLst.getInCardName());
		this.setInBankId(originalCupsLst.getInBankId());
		this.setTradeAmount(originalCupsLst.getTradeAmount());
		this.setTradeFee(originalCupsLst.getTradeFee());
		this.setTradeCurrency(originalCupsLst.getTradeCurrency());
		this.setTradeResult(originalCupsLst.getTradeResult());
		this.setReqSysId(originalCupsLst.getReqSysId());
		this.setReqSysStance(originalCupsLst.getReqSysStance());
		this.setReqMerCode(originalCupsLst.getReqMerCode());
		this.setReqMerTermId(originalCupsLst.getReqMerTermId());
		this.setReqResponse(originalCupsLst.getReqResponse());
		this.setDeductSysId(originalCupsLst.getDeductSysId());
		this.setDeductSysStance(originalCupsLst.getDeductSysStance());
		this.setDeductMerCode(originalCupsLst.getDeductMerCode());
		this.setDeductMerTermId(originalCupsLst.getDeductMerTermId());
		this.setDeductResult(originalCupsLst.getDeductResult());
		this.setDeductSysResponse(originalCupsLst.getDeductSysResponse());
		this.setDeductRollBk(originalCupsLst.isDeductRollBk());
		this.setDeductRollBkResult(originalCupsLst.getDeductRollBkResult());
		this.setDeductRollBkReason(originalCupsLst.getDeductRollBkReason());
		this.setDeductRollBkResponse(originalCupsLst.getDeductRollBkResponse());
		this.setDeductRollBkStance(originalCupsLst.getDeductRollBkStance());
		this.setTradeType(originalCupsLst.getTradeType());
		this.setMsgNum(originalCupsLst.getMsgNum());
		this.setPassWdMode(originalCupsLst.getPassWdMode());
		this.setReqType(originalCupsLst.getReqType());
		this.setReqInputType(originalCupsLst.getReqInputType());
		this.setReqTime(originalCupsLst.getReqTime());
		this.setTradeReqMethod(originalCupsLst.getTradeReqMethod());
		this.setTradeDesc(originalCupsLst.getTradeDesc());
		this.setInAccountType(originalCupsLst.getInAccountType());
		this.setTradeOtherInfo(originalCupsLst.getTradeOtherInfo());
		this.setDeductStlmDate(originalCupsLst.getDeductStlmDate());
		this.setDeductSysTime(originalCupsLst.getDeductSysTime());
		this.setGainSysId(originalCupsLst.getGainSysId());
		this.setGainSysStance(originalCupsLst.getGainSysStance());
		this.setGainMerCode(originalCupsLst.getGainMerCode());
		this.setGainMerTermId(originalCupsLst.getGainMerTermId());
		this.setGainSysResponse(originalCupsLst.getGainSysResponse());
		this.setGainResult(originalCupsLst.getGainResult());
		this.setGainTradeAmount(originalCupsLst.getGainTradeAmount());
		this.setGainSysReference(originalCupsLst.getGainSysReference());
		this.setNii(originalCupsLst.getNii());
		this.setAuthorizationCode(originalCupsLst.getAuthorizationCode());
		this.setAdditionalResponseData(originalCupsLst.getAdditionalResponseData());
		this.setAdditionalData(originalCupsLst.getAdditionalData());
		this.setBoc(originalCupsLst.getBoc());
		this.setCustomDefineInfo(originalCupsLst.getCustomDefineInfo());
		this.setOriginalTransInfo(originalCupsLst.getOriginalTransInfo());
		this.setReqProcess(originalCupsLst.getReqProcess());
		this.setDeductSysReference(originalCupsLst.getDeductSysReference());
		this.setTrademsgType(originalCupsLst.getTrademsgType());
		this.setDeductRollbkSysReference(originalCupsLst.getDeductRollbkSysReference());
		this.setMerName(originalCupsLst.getMerName());
		this.setBkChk(originalCupsLst.getBkChk());
		this.setWhetherJs(originalCupsLst.isWhetherJs());
		this.setWhetherValid(originalCupsLst.isWhetherValid());
		this.setWhetherErroeHandle(originalCupsLst.getWhetherErroeHandle());
		this.setWhetherRiqie(originalCupsLst.isWhetherRiqie());
		this.setAcqInstIdCode(originalCupsLst.getAcqInstIdCode());
		this.setFwdInstIdCode(originalCupsLst.getFwdInstIdCode());
		this.setDeductRollbkSysTime(originalCupsLst.getDeductRollbkSysTime());
		this.setAgentId(originalCupsLst.getAgentId());
		this.setWhetherzero(originalCupsLst.getWhetherzero());
		this.setWhtherInnerJs(originalCupsLst.isWhtherInnerJs());
		this.setIcCardSerNo(originalCupsLst.getIcCardSerNo());
		this.setIcReadAndCondition(originalCupsLst.getIcReadAndCondition());
		this.setWhetherQs(originalCupsLst.isWhetherQs());
		this.setMerFee(originalCupsLst.getMerFee());
		this.setWhetherTk(originalCupsLst.isWhetherTk());
		if(originalCupsLst.getTradeAmount() < 0){
			this.setZfFee(0-originalCupsLst.getZfFee());
			this.setZfFileFee("-"+originalCupsLst.getZfFileFee());
		}else{
			this.setZfFee(originalCupsLst.getZfFee());
			this.setZfFileFee(originalCupsLst.getZfFileFee());
		}
		this.setZfFeeBj(originalCupsLst.getZfFeeBj());
		this.setFeeFormula(originalCupsLst.getFeeFormula());
		this.setWhetherAccessStance(originalCupsLst.isWhetherAccessStance());
		this.setWhtherInnerDz(originalCupsLst.isWhtherInnerDz());
		this.setBankId(originalCupsLst.getBankId());
		this.setHandlingStatus(0);
		this.setErrorResource(0);
		this.setInstType(0);
	}
	
	/**
	 * 线下中信原始日切实体
	 * @param riqieZhongxingbankLst
	 */
	public ErrorDataLst(RiqieZhongxingbankLst riqieZhongxingbankLst){
		this.setId(riqieZhongxingbankLst.getId());
		this.setTerminalId(riqieZhongxingbankLst.getTerminalId());
		this.setTerminalInfo(riqieZhongxingbankLst.getTerminalInfo());
		this.setTerminalType(riqieZhongxingbankLst.getTerminalType());
		this.setTradeTime(riqieZhongxingbankLst.getTradeTime());
		this.setOutAccount(riqieZhongxingbankLst.getOutAccount());
		this.setOutAccountType(riqieZhongxingbankLst.getOutAccountType());
		this.setOutAccValidTime(riqieZhongxingbankLst.getOutAccValidTime());
		this.setOutAccountInfo(riqieZhongxingbankLst.getOutAccountInfo());
		this.setOutAccountInfo2(riqieZhongxingbankLst.getOutAccountInfo2());
		this.setOutAccountDesc(riqieZhongxingbankLst.getOutAccountDesc());
		this.setInAccount(riqieZhongxingbankLst.getInAccount());
		this.setInCardName(riqieZhongxingbankLst.getInCardName());
		this.setInBankId(riqieZhongxingbankLst.getInBankId());
		this.setTradeAmount(riqieZhongxingbankLst.getTradeAmount());
		this.setTradeFee(riqieZhongxingbankLst.getTradeFee());
		this.setTradeCurrency(riqieZhongxingbankLst.getTradeCurrency());
		this.setTradeResult(riqieZhongxingbankLst.getTradeResult());
		this.setReqSysId(riqieZhongxingbankLst.getReqSysId());
		this.setReqSysStance(riqieZhongxingbankLst.getReqSysStance());
		this.setReqMerCode(riqieZhongxingbankLst.getReqMerCode());
		this.setReqMerTermId(riqieZhongxingbankLst.getReqMerTermId());
		this.setReqResponse(riqieZhongxingbankLst.getReqResponse());
		this.setDeductSysId(riqieZhongxingbankLst.getDeductSysId());
		this.setDeductSysStance(riqieZhongxingbankLst.getDeductSysStance());
		this.setDeductMerCode(riqieZhongxingbankLst.getDeductMerCode());
		this.setDeductMerTermId(riqieZhongxingbankLst.getDeductMerTermId());
		this.setDeductResult(riqieZhongxingbankLst.getDeductResult());
		this.setDeductSysResponse(riqieZhongxingbankLst.getDeductSysResponse());
		this.setDeductRollBk(riqieZhongxingbankLst.isDeductRollBk());
		this.setDeductRollBkResult(riqieZhongxingbankLst.getDeductRollBkResult());
		this.setDeductRollBkReason(riqieZhongxingbankLst.getDeductRollBkReason());
		this.setDeductRollBkResponse(riqieZhongxingbankLst.getDeductRollBkResponse());
		this.setDeductRollBkStance(riqieZhongxingbankLst.getDeductRollBkStance());
		this.setTradeType(riqieZhongxingbankLst.getTradeType());
		this.setMsgNum(riqieZhongxingbankLst.getMsgNum());
		this.setPassWdMode(riqieZhongxingbankLst.getPassWdMode());
		this.setReqType(riqieZhongxingbankLst.getReqType());
		this.setReqInputType(riqieZhongxingbankLst.getReqInputType());
		this.setReqTime(riqieZhongxingbankLst.getReqTime());
		this.setTradeReqMethod(riqieZhongxingbankLst.getTradeReqMethod());
		this.setTradeDesc(riqieZhongxingbankLst.getTradeDesc());
		this.setInAccountType(riqieZhongxingbankLst.getInAccountType());
		this.setTradeOtherInfo(riqieZhongxingbankLst.getTradeOtherInfo());
		this.setDeductStlmDate(riqieZhongxingbankLst.getDeductStlmDate());
		this.setDeductSysTime(riqieZhongxingbankLst.getDeductSysTime());
		this.setGainSysId(riqieZhongxingbankLst.getGainSysId());
		this.setGainSysStance(riqieZhongxingbankLst.getGainSysStance());
		this.setGainMerCode(riqieZhongxingbankLst.getGainMerCode());
		this.setGainMerTermId(riqieZhongxingbankLst.getGainMerTermId());
		this.setGainSysResponse(riqieZhongxingbankLst.getGainSysResponse());
		this.setGainResult(riqieZhongxingbankLst.getGainResult());
		this.setGainTradeAmount(riqieZhongxingbankLst.getGainTradeAmount());
		this.setGainSysReference(riqieZhongxingbankLst.getGainSysReference());
		this.setNii(riqieZhongxingbankLst.getNii());
		this.setAuthorizationCode(riqieZhongxingbankLst.getAuthorizationCode());
		this.setAdditionalResponseData(riqieZhongxingbankLst.getAdditionalResponseData());
		this.setAdditionalData(riqieZhongxingbankLst.getAdditionalData());
		this.setBoc(riqieZhongxingbankLst.getBoc());
		this.setCustomDefineInfo(riqieZhongxingbankLst.getCustomDefineInfo());
		this.setOriginalTransInfo(riqieZhongxingbankLst.getOriginalTransInfo());
		this.setReqProcess(riqieZhongxingbankLst.getReqProcess());
		this.setDeductSysReference(riqieZhongxingbankLst.getDeductSysReference());
		this.setTrademsgType(riqieZhongxingbankLst.getTrademsgType());
		this.setDeductRollbkSysReference(riqieZhongxingbankLst.getDeductRollbkSysReference());
		this.setMerName(riqieZhongxingbankLst.getMerName());
		this.setBkChk(riqieZhongxingbankLst.getBkChk());
		this.setWhetherJs(riqieZhongxingbankLst.isWhetherJs());
		this.setWhetherValid(riqieZhongxingbankLst.isWhetherValid());
		this.setWhetherErroeHandle(riqieZhongxingbankLst.getWhetherErroeHandle());
		this.setWhetherRiqie(riqieZhongxingbankLst.isWhetherRiqie());
		this.setAcqInstIdCode(riqieZhongxingbankLst.getAcqInstIdCode());
		this.setFwdInstIdCode(riqieZhongxingbankLst.getFwdInstIdCode());
		this.setDeductRollbkSysTime(riqieZhongxingbankLst.getDeductRollbkSysTime());
		this.setAgentId(riqieZhongxingbankLst.getAgentId());
		this.setWhetherzero(riqieZhongxingbankLst.getWhetherzero());
		this.setWhtherInnerJs(riqieZhongxingbankLst.isWhtherInnerJs());
		this.setIcCardSerNo(riqieZhongxingbankLst.getIcCardSerNo());
		this.setIcReadAndCondition(riqieZhongxingbankLst.getIcReadAndCondition());
		this.setWhetherQs(riqieZhongxingbankLst.isWhetherQs());
		this.setMerFee(riqieZhongxingbankLst.getMerFee());
		this.setWhetherTk(riqieZhongxingbankLst.isWhetherTk());
		if(riqieZhongxingbankLst.getTradeAmount() < 0){
			this.setZfFee(0-riqieZhongxingbankLst.getZfFee());
			this.setZfFileFee("-"+riqieZhongxingbankLst.getZfFileFee());
		}else{
			this.setZfFee(riqieZhongxingbankLst.getZfFee());
			this.setZfFileFee(riqieZhongxingbankLst.getZfFileFee());
		}
		this.setZfFeeBj(riqieZhongxingbankLst.getZfFeeBj());
		this.setFeeFormula(riqieZhongxingbankLst.getFeeFormula());
		this.setWhetherAccessStance(riqieZhongxingbankLst.isWhetherAccessStance());
		this.setWhtherInnerDz(riqieZhongxingbankLst.isWhtherInnerDz());
		this.setBankId(riqieZhongxingbankLst.getBankId());
		this.setHandlingStatus(0);
		this.setErrorResource(0);
		this.setInstType(0);
	}
	
	/**
	 * 线下中信原始交易实体
	 * @param originalZhongxingbankLst
	 */
	public ErrorDataLst(OriginalZhongxingbankLst originalZhongxingbankLst){
		this.setId(originalZhongxingbankLst.getId());
		this.setTerminalId(originalZhongxingbankLst.getTerminalId());
		this.setTerminalInfo(originalZhongxingbankLst.getTerminalInfo());
		this.setTerminalType(originalZhongxingbankLst.getTerminalType());
		this.setTradeTime(originalZhongxingbankLst.getTradeTime());
		this.setOutAccount(originalZhongxingbankLst.getOutAccount());
		this.setOutAccountType(originalZhongxingbankLst.getOutAccountType());
		this.setOutAccValidTime(originalZhongxingbankLst.getOutAccValidTime());
		this.setOutAccountInfo(originalZhongxingbankLst.getOutAccountInfo());
		this.setOutAccountInfo2(originalZhongxingbankLst.getOutAccountInfo2());
		this.setOutAccountDesc(originalZhongxingbankLst.getOutAccountDesc());
		this.setInAccount(originalZhongxingbankLst.getInAccount());
		this.setInCardName(originalZhongxingbankLst.getInCardName());
		this.setInBankId(originalZhongxingbankLst.getInBankId());
		this.setTradeAmount(originalZhongxingbankLst.getTradeAmount());
		this.setTradeFee(originalZhongxingbankLst.getTradeFee());
		this.setTradeCurrency(originalZhongxingbankLst.getTradeCurrency());
		this.setTradeResult(originalZhongxingbankLst.getTradeResult());
		this.setReqSysId(originalZhongxingbankLst.getReqSysId());
		this.setReqSysStance(originalZhongxingbankLst.getReqSysStance());
		this.setReqMerCode(originalZhongxingbankLst.getReqMerCode());
		this.setReqMerTermId(originalZhongxingbankLst.getReqMerTermId());
		this.setReqResponse(originalZhongxingbankLst.getReqResponse());
		this.setDeductSysId(originalZhongxingbankLst.getDeductSysId());
		this.setDeductSysStance(originalZhongxingbankLst.getDeductSysStance());
		this.setDeductMerCode(originalZhongxingbankLst.getDeductMerCode());
		this.setDeductMerTermId(originalZhongxingbankLst.getDeductMerTermId());
		this.setDeductResult(originalZhongxingbankLst.getDeductResult());
		this.setDeductSysResponse(originalZhongxingbankLst.getDeductSysResponse());
		this.setDeductRollBk(originalZhongxingbankLst.isDeductRollBk());
		this.setDeductRollBkResult(originalZhongxingbankLst.getDeductRollBkResult());
		this.setDeductRollBkReason(originalZhongxingbankLst.getDeductRollBkReason());
		this.setDeductRollBkResponse(originalZhongxingbankLst.getDeductRollBkResponse());
		this.setDeductRollBkStance(originalZhongxingbankLst.getDeductRollBkStance());
		this.setTradeType(originalZhongxingbankLst.getTradeType());
		this.setMsgNum(originalZhongxingbankLst.getMsgNum());
		this.setPassWdMode(originalZhongxingbankLst.getPassWdMode());
		this.setReqType(originalZhongxingbankLst.getReqType());
		this.setReqInputType(originalZhongxingbankLst.getReqInputType());
		this.setReqTime(originalZhongxingbankLst.getReqTime());
		this.setTradeReqMethod(originalZhongxingbankLst.getTradeReqMethod());
		this.setTradeDesc(originalZhongxingbankLst.getTradeDesc());
		this.setInAccountType(originalZhongxingbankLst.getInAccountType());
		this.setTradeOtherInfo(originalZhongxingbankLst.getTradeOtherInfo());
		this.setDeductStlmDate(originalZhongxingbankLst.getDeductStlmDate());
		this.setDeductSysTime(originalZhongxingbankLst.getDeductSysTime());
		this.setGainSysId(originalZhongxingbankLst.getGainSysId());
		this.setGainSysStance(originalZhongxingbankLst.getGainSysStance());
		this.setGainMerCode(originalZhongxingbankLst.getGainMerCode());
		this.setGainMerTermId(originalZhongxingbankLst.getGainMerTermId());
		this.setGainSysResponse(originalZhongxingbankLst.getGainSysResponse());
		this.setGainResult(originalZhongxingbankLst.getGainResult());
		this.setGainTradeAmount(originalZhongxingbankLst.getGainTradeAmount());
		this.setGainSysReference(originalZhongxingbankLst.getGainSysReference());
		this.setNii(originalZhongxingbankLst.getNii());
		this.setAuthorizationCode(originalZhongxingbankLst.getAuthorizationCode());
		this.setAdditionalResponseData(originalZhongxingbankLst.getAdditionalResponseData());
		this.setAdditionalData(originalZhongxingbankLst.getAdditionalData());
		this.setBoc(originalZhongxingbankLst.getBoc());
		this.setCustomDefineInfo(originalZhongxingbankLst.getCustomDefineInfo());
		this.setOriginalTransInfo(originalZhongxingbankLst.getOriginalTransInfo());
		this.setReqProcess(originalZhongxingbankLst.getReqProcess());
		this.setDeductSysReference(originalZhongxingbankLst.getDeductSysReference());
		this.setTrademsgType(originalZhongxingbankLst.getTrademsgType());
		this.setDeductRollbkSysReference(originalZhongxingbankLst.getDeductRollbkSysReference());
		this.setMerName(originalZhongxingbankLst.getMerName());
		this.setBkChk(originalZhongxingbankLst.getBkChk());
		this.setWhetherJs(originalZhongxingbankLst.isWhetherJs());
		this.setWhetherValid(originalZhongxingbankLst.isWhetherValid());
		this.setWhetherErroeHandle(originalZhongxingbankLst.getWhetherErroeHandle());
		this.setWhetherRiqie(originalZhongxingbankLst.isWhetherRiqie());
		this.setAcqInstIdCode(originalZhongxingbankLst.getAcqInstIdCode());
		this.setFwdInstIdCode(originalZhongxingbankLst.getFwdInstIdCode());
		this.setDeductRollbkSysTime(originalZhongxingbankLst.getDeductRollbkSysTime());
		this.setAgentId(originalZhongxingbankLst.getAgentId());
		this.setWhetherzero(originalZhongxingbankLst.getWhetherzero());
		this.setWhtherInnerJs(originalZhongxingbankLst.isWhtherInnerJs());
		this.setIcCardSerNo(originalZhongxingbankLst.getIcCardSerNo());
		this.setIcReadAndCondition(originalZhongxingbankLst.getIcReadAndCondition());
		this.setWhetherQs(originalZhongxingbankLst.isWhetherQs());
		this.setMerFee(originalZhongxingbankLst.getMerFee());
		this.setWhetherTk(originalZhongxingbankLst.isWhetherTk());
		if(originalZhongxingbankLst.getTradeAmount() < 0){
			this.setZfFee(0-originalZhongxingbankLst.getZfFee());
			this.setZfFileFee("-"+originalZhongxingbankLst.getZfFileFee());
		}else{
			this.setZfFee(originalZhongxingbankLst.getZfFee());
			this.setZfFileFee(originalZhongxingbankLst.getZfFileFee());
		}
		this.setZfFeeBj(originalZhongxingbankLst.getZfFeeBj());
		this.setFeeFormula(originalZhongxingbankLst.getFeeFormula());
		this.setWhetherAccessStance(originalZhongxingbankLst.isWhetherAccessStance());
		this.setWhtherInnerDz(originalZhongxingbankLst.isWhtherInnerDz());
		this.setBankId(originalZhongxingbankLst.getBankId());
		this.setHandlingStatus(0);
		this.setErrorResource(0);
		this.setInstType(0);
	}
	
	/**
	 * 线下北京银行日切交易实体
	 * @param riqieBeijingbankLst
	 */
	public ErrorDataLst(RiqieBeijingbankLst riqieBeijingbankLst){
		this.setId(riqieBeijingbankLst.getId());
		this.setTerminalId(riqieBeijingbankLst.getTerminalId());
		this.setTerminalInfo(riqieBeijingbankLst.getTerminalInfo());
		this.setTerminalType(riqieBeijingbankLst.getTerminalType());
		this.setTradeTime(riqieBeijingbankLst.getTradeTime());
		this.setOutAccount(riqieBeijingbankLst.getOutAccount());
		this.setOutAccountType(riqieBeijingbankLst.getOutAccountType());
		this.setOutAccValidTime(riqieBeijingbankLst.getOutAccValidTime());
		this.setOutAccountInfo(riqieBeijingbankLst.getOutAccountInfo());
		this.setOutAccountInfo2(riqieBeijingbankLst.getOutAccountInfo2());
		this.setOutAccountDesc(riqieBeijingbankLst.getOutAccountDesc());
		this.setInAccount(riqieBeijingbankLst.getInAccount());
		this.setInCardName(riqieBeijingbankLst.getInCardName());
		this.setInBankId(riqieBeijingbankLst.getInBankId());
		this.setTradeAmount(riqieBeijingbankLst.getTradeAmount());
		this.setTradeFee(riqieBeijingbankLst.getTradeFee());
		this.setTradeCurrency(riqieBeijingbankLst.getTradeCurrency());
		this.setTradeResult(riqieBeijingbankLst.getTradeResult());
		this.setReqSysId(riqieBeijingbankLst.getReqSysId());
		this.setReqSysStance(riqieBeijingbankLst.getReqSysStance());
		this.setReqMerCode(riqieBeijingbankLst.getReqMerCode());
		this.setReqMerTermId(riqieBeijingbankLst.getReqMerTermId());
		this.setReqResponse(riqieBeijingbankLst.getReqResponse());
		this.setDeductSysId(riqieBeijingbankLst.getDeductSysId());
		this.setDeductSysStance(riqieBeijingbankLst.getDeductSysStance());
		this.setDeductMerCode(riqieBeijingbankLst.getDeductMerCode());
		this.setDeductMerTermId(riqieBeijingbankLst.getDeductMerTermId());
		this.setDeductResult(riqieBeijingbankLst.getDeductResult());
		this.setDeductSysResponse(riqieBeijingbankLst.getDeductSysResponse());
		this.setDeductRollBk(riqieBeijingbankLst.isDeductRollBk());
		this.setDeductRollBkResult(riqieBeijingbankLst.getDeductRollBkResult());
		this.setDeductRollBkReason(riqieBeijingbankLst.getDeductRollBkReason());
		this.setDeductRollBkResponse(riqieBeijingbankLst.getDeductRollBkResponse());
		this.setDeductRollBkStance(riqieBeijingbankLst.getDeductRollBkStance());
		this.setTradeType(riqieBeijingbankLst.getTradeType());
		this.setMsgNum(riqieBeijingbankLst.getMsgNum());
		this.setPassWdMode(riqieBeijingbankLst.getPassWdMode());
		this.setReqType(riqieBeijingbankLst.getReqType());
		this.setReqInputType(riqieBeijingbankLst.getReqInputType());
		this.setReqTime(riqieBeijingbankLst.getReqTime());
		this.setTradeReqMethod(riqieBeijingbankLst.getTradeReqMethod());
		this.setTradeDesc(riqieBeijingbankLst.getTradeDesc());
		this.setInAccountType(riqieBeijingbankLst.getInAccountType());
		this.setTradeOtherInfo(riqieBeijingbankLst.getTradeOtherInfo());
		this.setDeductStlmDate(riqieBeijingbankLst.getDeductStlmDate());
		this.setDeductSysTime(riqieBeijingbankLst.getDeductSysTime());
		this.setGainSysId(riqieBeijingbankLst.getGainSysId());
		this.setGainSysStance(riqieBeijingbankLst.getGainSysStance());
		this.setGainMerCode(riqieBeijingbankLst.getGainMerCode());
		this.setGainMerTermId(riqieBeijingbankLst.getGainMerTermId());
		this.setGainSysResponse(riqieBeijingbankLst.getGainSysResponse());
		this.setGainResult(riqieBeijingbankLst.getGainResult());
		this.setGainTradeAmount(riqieBeijingbankLst.getGainTradeAmount());
		this.setGainSysReference(riqieBeijingbankLst.getGainSysReference());
		this.setNii(riqieBeijingbankLst.getNii());
		this.setAuthorizationCode(riqieBeijingbankLst.getAuthorizationCode());
		this.setAdditionalResponseData(riqieBeijingbankLst.getAdditionalResponseData());
		this.setAdditionalData(riqieBeijingbankLst.getAdditionalData());
		this.setBoc(riqieBeijingbankLst.getBoc());
		this.setCustomDefineInfo(riqieBeijingbankLst.getCustomDefineInfo());
		this.setOriginalTransInfo(riqieBeijingbankLst.getOriginalTransInfo());
		this.setReqProcess(riqieBeijingbankLst.getReqProcess());
		this.setDeductSysReference(riqieBeijingbankLst.getDeductSysReference());
		this.setTrademsgType(riqieBeijingbankLst.getTrademsgType());
		this.setDeductRollbkSysReference(riqieBeijingbankLst.getDeductRollbkSysReference());
		this.setMerName(riqieBeijingbankLst.getMerName());
		this.setBkChk(riqieBeijingbankLst.getBkChk());
		this.setWhetherJs(riqieBeijingbankLst.isWhetherJs());
		this.setWhetherValid(riqieBeijingbankLst.isWhetherValid());
		this.setWhetherErroeHandle(riqieBeijingbankLst.getWhetherErroeHandle());
		this.setWhetherRiqie(riqieBeijingbankLst.isWhetherRiqie());
		this.setAcqInstIdCode(riqieBeijingbankLst.getAcqInstIdCode());
		this.setFwdInstIdCode(riqieBeijingbankLst.getFwdInstIdCode());
		this.setDeductRollbkSysTime(riqieBeijingbankLst.getDeductRollbkSysTime());
		this.setAgentId(riqieBeijingbankLst.getAgentId());
		this.setWhetherzero(riqieBeijingbankLst.getWhetherzero());
		this.setWhtherInnerJs(riqieBeijingbankLst.isWhtherInnerJs());
		this.setIcCardSerNo(riqieBeijingbankLst.getIcCardSerNo());
		this.setIcReadAndCondition(riqieBeijingbankLst.getIcReadAndCondition());
		this.setWhetherQs(riqieBeijingbankLst.isWhetherQs());
		this.setMerFee(riqieBeijingbankLst.getMerFee());
		this.setWhetherTk(riqieBeijingbankLst.isWhetherTk());
		if(riqieBeijingbankLst.getTradeAmount() < 0){
			this.setZfFee(0-riqieBeijingbankLst.getZfFee());
			this.setZfFileFee("-"+riqieBeijingbankLst.getZfFileFee());
		}else{
			this.setZfFee(riqieBeijingbankLst.getZfFee());
			this.setZfFileFee(riqieBeijingbankLst.getZfFileFee());
		}
		this.setZfFeeBj(riqieBeijingbankLst.getZfFeeBj());
		this.setFeeFormula(riqieBeijingbankLst.getFeeFormula());
		this.setWhetherAccessStance(riqieBeijingbankLst.isWhetherAccessStance());
		this.setWhtherInnerDz(riqieBeijingbankLst.isWhtherInnerDz());
		this.setBankId(riqieBeijingbankLst.getBankId());
		this.setHandlingStatus(0);
		this.setErrorResource(0);
		this.setInstType(0);
	}
	
	/**
	 * 线下北京银行原始交易实体
	 * @param originalBeijingbankLst
	 */
	public ErrorDataLst(OriginalBeijingbankLst originalBeijingbankLst){
		this.setId(originalBeijingbankLst.getId());
		this.setTerminalId(originalBeijingbankLst.getTerminalId());
		this.setTerminalInfo(originalBeijingbankLst.getTerminalInfo());
		this.setTerminalType(originalBeijingbankLst.getTerminalType());
		this.setTradeTime(originalBeijingbankLst.getTradeTime());
		this.setOutAccount(originalBeijingbankLst.getOutAccount());
		this.setOutAccountType(originalBeijingbankLst.getOutAccountType());
		this.setOutAccValidTime(originalBeijingbankLst.getOutAccValidTime());
		this.setOutAccountInfo(originalBeijingbankLst.getOutAccountInfo());
		this.setOutAccountInfo2(originalBeijingbankLst.getOutAccountInfo2());
		this.setOutAccountDesc(originalBeijingbankLst.getOutAccountDesc());
		this.setInAccount(originalBeijingbankLst.getInAccount());
		this.setInCardName(originalBeijingbankLst.getInCardName());
		this.setInBankId(originalBeijingbankLst.getInBankId());
		this.setTradeAmount(originalBeijingbankLst.getTradeAmount());
		this.setTradeFee(originalBeijingbankLst.getTradeFee());
		this.setTradeCurrency(originalBeijingbankLst.getTradeCurrency());
		this.setTradeResult(originalBeijingbankLst.getTradeResult());
		this.setReqSysId(originalBeijingbankLst.getReqSysId());
		this.setReqSysStance(originalBeijingbankLst.getReqSysStance());
		this.setReqMerCode(originalBeijingbankLst.getReqMerCode());
		this.setReqMerTermId(originalBeijingbankLst.getReqMerTermId());
		this.setReqResponse(originalBeijingbankLst.getReqResponse());
		this.setDeductSysId(originalBeijingbankLst.getDeductSysId());
		this.setDeductSysStance(originalBeijingbankLst.getDeductSysStance());
		this.setDeductMerCode(originalBeijingbankLst.getDeductMerCode());
		this.setDeductMerTermId(originalBeijingbankLst.getDeductMerTermId());
		this.setDeductResult(originalBeijingbankLst.getDeductResult());
		this.setDeductSysResponse(originalBeijingbankLst.getDeductSysResponse());
		this.setDeductRollBk(originalBeijingbankLst.isDeductRollBk());
		this.setDeductRollBkResult(originalBeijingbankLst.getDeductRollBkResult());
		this.setDeductRollBkReason(originalBeijingbankLst.getDeductRollBkReason());
		this.setDeductRollBkResponse(originalBeijingbankLst.getDeductRollBkResponse());
		this.setDeductRollBkStance(originalBeijingbankLst.getDeductRollBkStance());
		this.setTradeType(originalBeijingbankLst.getTradeType());
		this.setMsgNum(originalBeijingbankLst.getMsgNum());
		this.setPassWdMode(originalBeijingbankLst.getPassWdMode());
		this.setReqType(originalBeijingbankLst.getReqType());
		this.setReqInputType(originalBeijingbankLst.getReqInputType());
		this.setReqTime(originalBeijingbankLst.getReqTime());
		this.setTradeReqMethod(originalBeijingbankLst.getTradeReqMethod());
		this.setTradeDesc(originalBeijingbankLst.getTradeDesc());
		this.setInAccountType(originalBeijingbankLst.getInAccountType());
		this.setTradeOtherInfo(originalBeijingbankLst.getTradeOtherInfo());
		this.setDeductStlmDate(originalBeijingbankLst.getDeductStlmDate());
		this.setDeductSysTime(originalBeijingbankLst.getDeductSysTime());
		this.setGainSysId(originalBeijingbankLst.getGainSysId());
		this.setGainSysStance(originalBeijingbankLst.getGainSysStance());
		this.setGainMerCode(originalBeijingbankLst.getGainMerCode());
		this.setGainMerTermId(originalBeijingbankLst.getGainMerTermId());
		this.setGainSysResponse(originalBeijingbankLst.getGainSysResponse());
		this.setGainResult(originalBeijingbankLst.getGainResult());
		this.setGainTradeAmount(originalBeijingbankLst.getGainTradeAmount());
		this.setGainSysReference(originalBeijingbankLst.getGainSysReference());
		this.setNii(originalBeijingbankLst.getNii());
		this.setAuthorizationCode(originalBeijingbankLst.getAuthorizationCode());
		this.setAdditionalResponseData(originalBeijingbankLst.getAdditionalResponseData());
		this.setAdditionalData(originalBeijingbankLst.getAdditionalData());
		this.setBoc(originalBeijingbankLst.getBoc());
		this.setCustomDefineInfo(originalBeijingbankLst.getCustomDefineInfo());
		this.setOriginalTransInfo(originalBeijingbankLst.getOriginalTransInfo());
		this.setReqProcess(originalBeijingbankLst.getReqProcess());
		this.setDeductSysReference(originalBeijingbankLst.getDeductSysReference());
		this.setTrademsgType(originalBeijingbankLst.getTrademsgType());
		this.setDeductRollbkSysReference(originalBeijingbankLst.getDeductRollbkSysReference());
		this.setMerName(originalBeijingbankLst.getMerName());
		this.setBkChk(originalBeijingbankLst.getBkChk());
		this.setWhetherJs(originalBeijingbankLst.isWhetherJs());
		this.setWhetherValid(originalBeijingbankLst.isWhetherValid());
		this.setWhetherErroeHandle(originalBeijingbankLst.getWhetherErroeHandle());
		this.setWhetherRiqie(originalBeijingbankLst.isWhetherRiqie());
		this.setAcqInstIdCode(originalBeijingbankLst.getAcqInstIdCode());
		this.setFwdInstIdCode(originalBeijingbankLst.getFwdInstIdCode());
		this.setDeductRollbkSysTime(originalBeijingbankLst.getDeductRollbkSysTime());
		this.setAgentId(originalBeijingbankLst.getAgentId());
		this.setWhetherzero(originalBeijingbankLst.getWhetherzero());
		this.setWhtherInnerJs(originalBeijingbankLst.isWhtherInnerJs());
		this.setIcCardSerNo(originalBeijingbankLst.getIcCardSerNo());
		this.setIcReadAndCondition(originalBeijingbankLst.getIcReadAndCondition());
		this.setWhetherQs(originalBeijingbankLst.isWhetherQs());
		this.setMerFee(originalBeijingbankLst.getMerFee());
		this.setWhetherTk(originalBeijingbankLst.isWhetherTk());
		if(originalBeijingbankLst.getTradeAmount() < 0){
			this.setZfFee(0-originalBeijingbankLst.getZfFee());
			this.setZfFileFee("-"+originalBeijingbankLst.getZfFileFee());
		}else{
			this.setZfFee(originalBeijingbankLst.getZfFee());
			this.setZfFileFee(originalBeijingbankLst.getZfFileFee());
		}
		this.setZfFeeBj(originalBeijingbankLst.getZfFeeBj());
		this.setFeeFormula(originalBeijingbankLst.getFeeFormula());
		this.setWhetherAccessStance(originalBeijingbankLst.isWhetherAccessStance());
		this.setWhtherInnerDz(originalBeijingbankLst.isWhtherInnerDz());
		this.setBankId(originalBeijingbankLst.getBankId());
		this.setHandlingStatus(0);
		this.setErrorResource(0);
		this.setInstType(0);
	}
	
	/**
	 * 线下大连交行日切交易实体
	 * @param riqieBeijingbankLst
	 */
	public ErrorDataLst(RiqieDljhLst riqieDljhLst){
		this.setId(riqieDljhLst.getId());
		this.setTerminalId(riqieDljhLst.getTerminalId());
		this.setTerminalInfo(riqieDljhLst.getTerminalInfo());
		this.setTerminalType(riqieDljhLst.getTerminalType());
		this.setTradeTime(riqieDljhLst.getTradeTime());
		this.setOutAccount(riqieDljhLst.getOutAccount());
		this.setOutAccountType(riqieDljhLst.getOutAccountType());
		this.setOutAccValidTime(riqieDljhLst.getOutAccValidTime());
		this.setOutAccountInfo(riqieDljhLst.getOutAccountInfo());
		this.setOutAccountInfo2(riqieDljhLst.getOutAccountInfo2());
		this.setOutAccountDesc(riqieDljhLst.getOutAccountDesc());
		this.setInAccount(riqieDljhLst.getInAccount());
		this.setInCardName(riqieDljhLst.getInCardName());
		this.setInBankId(riqieDljhLst.getInBankId());
		this.setTradeAmount(riqieDljhLst.getTradeAmount());
		this.setTradeFee(riqieDljhLst.getTradeFee());
		this.setTradeCurrency(riqieDljhLst.getTradeCurrency());
		this.setTradeResult(riqieDljhLst.getTradeResult());
		this.setReqSysId(riqieDljhLst.getReqSysId());
		this.setReqSysStance(riqieDljhLst.getReqSysStance());
		this.setReqMerCode(riqieDljhLst.getReqMerCode());
		this.setReqMerTermId(riqieDljhLst.getReqMerTermId());
		this.setReqResponse(riqieDljhLst.getReqResponse());
		this.setDeductSysId(riqieDljhLst.getDeductSysId());
		this.setDeductSysStance(riqieDljhLst.getDeductSysStance());
		this.setDeductMerCode(riqieDljhLst.getDeductMerCode());
		this.setDeductMerTermId(riqieDljhLst.getDeductMerTermId());
		this.setDeductResult(riqieDljhLst.getDeductResult());
		this.setDeductSysResponse(riqieDljhLst.getDeductSysResponse());
		this.setDeductRollBk(riqieDljhLst.isDeductRollBk());
		this.setDeductRollBkResult(riqieDljhLst.getDeductRollBkResult());
		this.setDeductRollBkReason(riqieDljhLst.getDeductRollBkReason());
		this.setDeductRollBkResponse(riqieDljhLst.getDeductRollBkResponse());
		this.setDeductRollBkStance(riqieDljhLst.getDeductRollBkStance());
		this.setTradeType(riqieDljhLst.getTradeType());
		this.setMsgNum(riqieDljhLst.getMsgNum());
		this.setPassWdMode(riqieDljhLst.getPassWdMode());
		this.setReqType(riqieDljhLst.getReqType());
		this.setReqInputType(riqieDljhLst.getReqInputType());
		this.setReqTime(riqieDljhLst.getReqTime());
		this.setTradeReqMethod(riqieDljhLst.getTradeReqMethod());
		this.setTradeDesc(riqieDljhLst.getTradeDesc());
		this.setInAccountType(riqieDljhLst.getInAccountType());
		this.setTradeOtherInfo(riqieDljhLst.getTradeOtherInfo());
		this.setDeductStlmDate(riqieDljhLst.getDeductStlmDate());
		this.setDeductSysTime(riqieDljhLst.getDeductSysTime());
		this.setGainSysId(riqieDljhLst.getGainSysId());
		this.setGainSysStance(riqieDljhLst.getGainSysStance());
		this.setGainMerCode(riqieDljhLst.getGainMerCode());
		this.setGainMerTermId(riqieDljhLst.getGainMerTermId());
		this.setGainSysResponse(riqieDljhLst.getGainSysResponse());
		this.setGainResult(riqieDljhLst.getGainResult());
		this.setGainTradeAmount(riqieDljhLst.getGainTradeAmount());
		this.setGainSysReference(riqieDljhLst.getGainSysReference());
		this.setNii(riqieDljhLst.getNii());
		this.setAuthorizationCode(riqieDljhLst.getAuthorizationCode());
		this.setAdditionalResponseData(riqieDljhLst.getAdditionalResponseData());
		this.setAdditionalData(riqieDljhLst.getAdditionalData());
		this.setBoc(riqieDljhLst.getBoc());
		this.setCustomDefineInfo(riqieDljhLst.getCustomDefineInfo());
		this.setOriginalTransInfo(riqieDljhLst.getOriginalTransInfo());
		this.setReqProcess(riqieDljhLst.getReqProcess());
		this.setDeductSysReference(riqieDljhLst.getDeductSysReference());
		this.setTrademsgType(riqieDljhLst.getTrademsgType());
		this.setDeductRollbkSysReference(riqieDljhLst.getDeductRollbkSysReference());
		this.setMerName(riqieDljhLst.getMerName());
		this.setBkChk(riqieDljhLst.getBkChk());
		this.setWhetherJs(riqieDljhLst.isWhetherJs());
		this.setWhetherValid(riqieDljhLst.isWhetherValid());
		this.setWhetherErroeHandle(riqieDljhLst.getWhetherErroeHandle());
		this.setWhetherRiqie(riqieDljhLst.isWhetherRiqie());
		this.setAcqInstIdCode(riqieDljhLst.getAcqInstIdCode());
		this.setFwdInstIdCode(riqieDljhLst.getFwdInstIdCode());
		this.setDeductRollbkSysTime(riqieDljhLst.getDeductRollbkSysTime());
		this.setAgentId(riqieDljhLst.getAgentId());
		this.setWhetherzero(riqieDljhLst.getWhetherzero());
		this.setWhtherInnerJs(riqieDljhLst.isWhtherInnerJs());
		this.setIcCardSerNo(riqieDljhLst.getIcCardSerNo());
		this.setIcReadAndCondition(riqieDljhLst.getIcReadAndCondition());
		this.setWhetherQs(riqieDljhLst.isWhetherQs());
		this.setMerFee(riqieDljhLst.getMerFee());
		this.setWhetherTk(riqieDljhLst.isWhetherTk());
		if(riqieDljhLst.getTradeAmount() < 0){
			this.setZfFee(0-riqieDljhLst.getZfFee());
			this.setZfFileFee("-"+riqieDljhLst.getZfFileFee());
		}else{
			this.setZfFee(riqieDljhLst.getZfFee());
			this.setZfFileFee(riqieDljhLst.getZfFileFee());
		}
		this.setZfFeeBj(riqieDljhLst.getZfFeeBj());
		this.setFeeFormula(riqieDljhLst.getFeeFormula());
		this.setWhetherAccessStance(riqieDljhLst.isWhetherAccessStance());
		this.setWhtherInnerDz(riqieDljhLst.isWhtherInnerDz());
		this.setBankId(riqieDljhLst.getBankId());
		this.setHandlingStatus(0);
		this.setErrorResource(0);
		this.setInstType(0);
	}
	
	/**
	 * 线下大连交行原始交易实体
	 * @param OriginalDljhLst
	 */
	public ErrorDataLst(OriginalDljhLst originalDljhLst){
		this.setId(originalDljhLst.getId());
		this.setTerminalId(originalDljhLst.getTerminalId());
		this.setTerminalInfo(originalDljhLst.getTerminalInfo());
		this.setTerminalType(originalDljhLst.getTerminalType());
		this.setTradeTime(originalDljhLst.getTradeTime());
		this.setOutAccount(originalDljhLst.getOutAccount());
		this.setOutAccountType(originalDljhLst.getOutAccountType());
		this.setOutAccValidTime(originalDljhLst.getOutAccValidTime());
		this.setOutAccountInfo(originalDljhLst.getOutAccountInfo());
		this.setOutAccountInfo2(originalDljhLst.getOutAccountInfo2());
		this.setOutAccountDesc(originalDljhLst.getOutAccountDesc());
		this.setInAccount(originalDljhLst.getInAccount());
		this.setInCardName(originalDljhLst.getInCardName());
		this.setInBankId(originalDljhLst.getInBankId());
		this.setTradeAmount(originalDljhLst.getTradeAmount());
		this.setTradeFee(originalDljhLst.getTradeFee());
		this.setTradeCurrency(originalDljhLst.getTradeCurrency());
		this.setTradeResult(originalDljhLst.getTradeResult());
		this.setReqSysId(originalDljhLst.getReqSysId());
		this.setReqSysStance(originalDljhLst.getReqSysStance());
		this.setReqMerCode(originalDljhLst.getReqMerCode());
		this.setReqMerTermId(originalDljhLst.getReqMerTermId());
		this.setReqResponse(originalDljhLst.getReqResponse());
		this.setDeductSysId(originalDljhLst.getDeductSysId());
		this.setDeductSysStance(originalDljhLst.getDeductSysStance());
		this.setDeductMerCode(originalDljhLst.getDeductMerCode());
		this.setDeductMerTermId(originalDljhLst.getDeductMerTermId());
		this.setDeductResult(originalDljhLst.getDeductResult());
		this.setDeductSysResponse(originalDljhLst.getDeductSysResponse());
		this.setDeductRollBk(originalDljhLst.isDeductRollBk());
		this.setDeductRollBkResult(originalDljhLst.getDeductRollBkResult());
		this.setDeductRollBkReason(originalDljhLst.getDeductRollBkReason());
		this.setDeductRollBkResponse(originalDljhLst.getDeductRollBkResponse());
		this.setDeductRollBkStance(originalDljhLst.getDeductRollBkStance());
		this.setTradeType(originalDljhLst.getTradeType());
		this.setMsgNum(originalDljhLst.getMsgNum());
		this.setPassWdMode(originalDljhLst.getPassWdMode());
		this.setReqType(originalDljhLst.getReqType());
		this.setReqInputType(originalDljhLst.getReqInputType());
		this.setReqTime(originalDljhLst.getReqTime());
		this.setTradeReqMethod(originalDljhLst.getTradeReqMethod());
		this.setTradeDesc(originalDljhLst.getTradeDesc());
		this.setInAccountType(originalDljhLst.getInAccountType());
		this.setTradeOtherInfo(originalDljhLst.getTradeOtherInfo());
		this.setDeductStlmDate(originalDljhLst.getDeductStlmDate());
		this.setDeductSysTime(originalDljhLst.getDeductSysTime());
		this.setGainSysId(originalDljhLst.getGainSysId());
		this.setGainSysStance(originalDljhLst.getGainSysStance());
		this.setGainMerCode(originalDljhLst.getGainMerCode());
		this.setGainMerTermId(originalDljhLst.getGainMerTermId());
		this.setGainSysResponse(originalDljhLst.getGainSysResponse());
		this.setGainResult(originalDljhLst.getGainResult());
		this.setGainTradeAmount(originalDljhLst.getGainTradeAmount());
		this.setGainSysReference(originalDljhLst.getGainSysReference());
		this.setNii(originalDljhLst.getNii());
		this.setAuthorizationCode(originalDljhLst.getAuthorizationCode());
		this.setAdditionalResponseData(originalDljhLst.getAdditionalResponseData());
		this.setAdditionalData(originalDljhLst.getAdditionalData());
		this.setBoc(originalDljhLst.getBoc());
		this.setCustomDefineInfo(originalDljhLst.getCustomDefineInfo());
		this.setOriginalTransInfo(originalDljhLst.getOriginalTransInfo());
		this.setReqProcess(originalDljhLst.getReqProcess());
		this.setDeductSysReference(originalDljhLst.getDeductSysReference());
		this.setTrademsgType(originalDljhLst.getTrademsgType());
		this.setDeductRollbkSysReference(originalDljhLst.getDeductRollbkSysReference());
		this.setMerName(originalDljhLst.getMerName());
		this.setBkChk(originalDljhLst.getBkChk());
		this.setWhetherJs(originalDljhLst.isWhetherJs());
		this.setWhetherValid(originalDljhLst.isWhetherValid());
		this.setWhetherErroeHandle(originalDljhLst.getWhetherErroeHandle());
		this.setWhetherRiqie(originalDljhLst.isWhetherRiqie());
		this.setAcqInstIdCode(originalDljhLst.getAcqInstIdCode());
		this.setFwdInstIdCode(originalDljhLst.getFwdInstIdCode());
		this.setDeductRollbkSysTime(originalDljhLst.getDeductRollbkSysTime());
		this.setAgentId(originalDljhLst.getAgentId());
		this.setWhetherzero(originalDljhLst.getWhetherzero());
		this.setWhtherInnerJs(originalDljhLst.isWhtherInnerJs());
		this.setIcCardSerNo(originalDljhLst.getIcCardSerNo());
		this.setIcReadAndCondition(originalDljhLst.getIcReadAndCondition());
		this.setWhetherQs(originalDljhLst.isWhetherQs());
		this.setMerFee(originalDljhLst.getMerFee());
		this.setWhetherTk(originalDljhLst.isWhetherTk());
		if(originalDljhLst.getTradeAmount() < 0){
			this.setZfFee(0-originalDljhLst.getZfFee());
			this.setZfFileFee("-"+originalDljhLst.getZfFileFee());
		}else{
			this.setZfFee(originalDljhLst.getZfFee());
			this.setZfFileFee(originalDljhLst.getZfFileFee());
		}
		this.setZfFeeBj(originalDljhLst.getZfFeeBj());
		this.setFeeFormula(originalDljhLst.getFeeFormula());
		this.setWhetherAccessStance(originalDljhLst.isWhetherAccessStance());
		this.setWhtherInnerDz(originalDljhLst.isWhtherInnerDz());
		this.setBankId(originalDljhLst.getBankId());
		this.setHandlingStatus(0);
		this.setErrorResource(0);
		this.setInstType(0);
	}
	
	/**
	 * 线下深圳中行原始交易实体
	 * @param originalSzzhLst
	 */
	public ErrorDataLst(OriginalSzzhLst originalSzzhLst){
		this.setId(originalSzzhLst.getId());
		this.setTerminalId(originalSzzhLst.getTerminalId());
		this.setTerminalInfo(originalSzzhLst.getTerminalInfo());
		this.setTerminalType(originalSzzhLst.getTerminalType());
		this.setTradeTime(originalSzzhLst.getTradeTime());
		this.setOutAccount(originalSzzhLst.getOutAccount());
		this.setOutAccountType(originalSzzhLst.getOutAccountType());
		this.setOutAccValidTime(originalSzzhLst.getOutAccValidTime());
		this.setOutAccountInfo(originalSzzhLst.getOutAccountInfo());
		this.setOutAccountInfo2(originalSzzhLst.getOutAccountInfo2());
		this.setOutAccountDesc(originalSzzhLst.getOutAccountDesc());
		this.setInAccount(originalSzzhLst.getInAccount());
		this.setInCardName(originalSzzhLst.getInCardName());
		this.setInBankId(originalSzzhLst.getInBankId());
		this.setTradeAmount(originalSzzhLst.getTradeAmount());
		this.setTradeFee(originalSzzhLst.getTradeFee());
		this.setTradeCurrency(originalSzzhLst.getTradeCurrency());
		this.setTradeResult(originalSzzhLst.getTradeResult());
		this.setReqSysId(originalSzzhLst.getReqSysId());
		this.setReqSysStance(originalSzzhLst.getReqSysStance());
		this.setReqMerCode(originalSzzhLst.getReqMerCode());
		this.setReqMerTermId(originalSzzhLst.getReqMerTermId());
		this.setReqResponse(originalSzzhLst.getReqResponse());
		this.setDeductSysId(originalSzzhLst.getDeductSysId());
		this.setDeductSysStance(originalSzzhLst.getDeductSysStance());
		this.setDeductMerCode(originalSzzhLst.getDeductMerCode());
		this.setDeductMerTermId(originalSzzhLst.getDeductMerTermId());
		this.setDeductResult(originalSzzhLst.getDeductResult());
		this.setDeductSysResponse(originalSzzhLst.getDeductSysResponse());
		this.setDeductRollBk(originalSzzhLst.isDeductRollBk());
		this.setDeductRollBkResult(originalSzzhLst.getDeductRollBkResult());
		this.setDeductRollBkReason(originalSzzhLst.getDeductRollBkReason());
		this.setDeductRollBkResponse(originalSzzhLst.getDeductRollBkResponse());
		this.setDeductRollBkStance(originalSzzhLst.getDeductRollBkStance());
		this.setTradeType(originalSzzhLst.getTradeType());
		this.setMsgNum(originalSzzhLst.getMsgNum());
		this.setPassWdMode(originalSzzhLst.getPassWdMode());
		this.setReqType(originalSzzhLst.getReqType());
		this.setReqInputType(originalSzzhLst.getReqInputType());
		this.setReqTime(originalSzzhLst.getReqTime());
		this.setTradeReqMethod(originalSzzhLst.getTradeReqMethod());
		this.setTradeDesc(originalSzzhLst.getTradeDesc());
		this.setInAccountType(originalSzzhLst.getInAccountType());
		this.setTradeOtherInfo(originalSzzhLst.getTradeOtherInfo());
		this.setDeductStlmDate(originalSzzhLst.getDeductStlmDate());
		this.setDeductSysTime(originalSzzhLst.getDeductSysTime());
		this.setGainSysId(originalSzzhLst.getGainSysId());
		this.setGainSysStance(originalSzzhLst.getGainSysStance());
		this.setGainMerCode(originalSzzhLst.getGainMerCode());
		this.setGainMerTermId(originalSzzhLst.getGainMerTermId());
		this.setGainSysResponse(originalSzzhLst.getGainSysResponse());
		this.setGainResult(originalSzzhLst.getGainResult());
		this.setGainTradeAmount(originalSzzhLst.getGainTradeAmount());
		this.setGainSysReference(originalSzzhLst.getGainSysReference());
		this.setNii(originalSzzhLst.getNii());
		this.setAuthorizationCode(originalSzzhLst.getAuthorizationCode());
		this.setAdditionalResponseData(originalSzzhLst.getAdditionalResponseData());
		this.setAdditionalData(originalSzzhLst.getAdditionalData());
		this.setBoc(originalSzzhLst.getBoc());
		this.setCustomDefineInfo(originalSzzhLst.getCustomDefineInfo());
		this.setOriginalTransInfo(originalSzzhLst.getOriginalTransInfo());
		this.setReqProcess(originalSzzhLst.getReqProcess());
		this.setDeductSysReference(originalSzzhLst.getDeductSysReference());
		this.setTrademsgType(originalSzzhLst.getTrademsgType());
		this.setDeductRollbkSysReference(originalSzzhLst.getDeductRollbkSysReference());
		this.setMerName(originalSzzhLst.getMerName());
		this.setBkChk(originalSzzhLst.getBkChk());
		this.setWhetherJs(originalSzzhLst.isWhetherJs());
		this.setWhetherValid(originalSzzhLst.isWhetherValid());
		this.setWhetherErroeHandle(originalSzzhLst.getWhetherErroeHandle());
		this.setWhetherRiqie(originalSzzhLst.isWhetherRiqie());
		this.setAcqInstIdCode(originalSzzhLst.getAcqInstIdCode());
		this.setFwdInstIdCode(originalSzzhLst.getFwdInstIdCode());
		this.setDeductRollbkSysTime(originalSzzhLst.getDeductRollbkSysTime());
		this.setAgentId(originalSzzhLst.getAgentId());
		this.setWhetherzero(originalSzzhLst.getWhetherzero());
		this.setWhtherInnerJs(originalSzzhLst.isWhtherInnerJs());
		this.setIcCardSerNo(originalSzzhLst.getIcCardSerNo());
		this.setIcReadAndCondition(originalSzzhLst.getIcReadAndCondition());
		this.setWhetherQs(originalSzzhLst.isWhetherQs());
		this.setMerFee(originalSzzhLst.getMerFee());
		this.setWhetherTk(originalSzzhLst.isWhetherTk());
		if(originalSzzhLst.getTradeAmount() < 0){
			this.setZfFee(0-originalSzzhLst.getZfFee());
			this.setZfFileFee("-"+originalSzzhLst.getZfFileFee());
		}else{
			this.setZfFee(originalSzzhLst.getZfFee());
			this.setZfFileFee(originalSzzhLst.getZfFileFee());
		}
		this.setZfFeeBj(originalSzzhLst.getZfFeeBj());
		this.setFeeFormula(originalSzzhLst.getFeeFormula());
		this.setWhetherAccessStance(originalSzzhLst.isWhetherAccessStance());
		this.setWhtherInnerDz(originalSzzhLst.isWhtherInnerDz());
		this.setBankId(originalSzzhLst.getBankId());
		this.setHandlingStatus(0);
		this.setErrorResource(0);
		this.setInstType(0);
	}
	
	
	/**
	 * 线下深圳中行日切交易实体
	 * @param riqieSzzhLst
	 */
	public ErrorDataLst(RiqieSzzhLst riqieSzzhLst){
		this.setId(riqieSzzhLst.getId());
		this.setTerminalId(riqieSzzhLst.getTerminalId());
		this.setTerminalInfo(riqieSzzhLst.getTerminalInfo());
		this.setTerminalType(riqieSzzhLst.getTerminalType());
		this.setTradeTime(riqieSzzhLst.getTradeTime());
		this.setOutAccount(riqieSzzhLst.getOutAccount());
		this.setOutAccountType(riqieSzzhLst.getOutAccountType());
		this.setOutAccValidTime(riqieSzzhLst.getOutAccValidTime());
		this.setOutAccountInfo(riqieSzzhLst.getOutAccountInfo());
		this.setOutAccountInfo2(riqieSzzhLst.getOutAccountInfo2());
		this.setOutAccountDesc(riqieSzzhLst.getOutAccountDesc());
		this.setInAccount(riqieSzzhLst.getInAccount());
		this.setInCardName(riqieSzzhLst.getInCardName());
		this.setInBankId(riqieSzzhLst.getInBankId());
		this.setTradeAmount(riqieSzzhLst.getTradeAmount());
		this.setTradeFee(riqieSzzhLst.getTradeFee());
		this.setTradeCurrency(riqieSzzhLst.getTradeCurrency());
		this.setTradeResult(riqieSzzhLst.getTradeResult());
		this.setReqSysId(riqieSzzhLst.getReqSysId());
		this.setReqSysStance(riqieSzzhLst.getReqSysStance());
		this.setReqMerCode(riqieSzzhLst.getReqMerCode());
		this.setReqMerTermId(riqieSzzhLst.getReqMerTermId());
		this.setReqResponse(riqieSzzhLst.getReqResponse());
		this.setDeductSysId(riqieSzzhLst.getDeductSysId());
		this.setDeductSysStance(riqieSzzhLst.getDeductSysStance());
		this.setDeductMerCode(riqieSzzhLst.getDeductMerCode());
		this.setDeductMerTermId(riqieSzzhLst.getDeductMerTermId());
		this.setDeductResult(riqieSzzhLst.getDeductResult());
		this.setDeductSysResponse(riqieSzzhLst.getDeductSysResponse());
		this.setDeductRollBk(riqieSzzhLst.isDeductRollBk());
		this.setDeductRollBkResult(riqieSzzhLst.getDeductRollBkResult());
		this.setDeductRollBkReason(riqieSzzhLst.getDeductRollBkReason());
		this.setDeductRollBkResponse(riqieSzzhLst.getDeductRollBkResponse());
		this.setDeductRollBkStance(riqieSzzhLst.getDeductRollBkStance());
		this.setTradeType(riqieSzzhLst.getTradeType());
		this.setMsgNum(riqieSzzhLst.getMsgNum());
		this.setPassWdMode(riqieSzzhLst.getPassWdMode());
		this.setReqType(riqieSzzhLst.getReqType());
		this.setReqInputType(riqieSzzhLst.getReqInputType());
		this.setReqTime(riqieSzzhLst.getReqTime());
		this.setTradeReqMethod(riqieSzzhLst.getTradeReqMethod());
		this.setTradeDesc(riqieSzzhLst.getTradeDesc());
		this.setInAccountType(riqieSzzhLst.getInAccountType());
		this.setTradeOtherInfo(riqieSzzhLst.getTradeOtherInfo());
		this.setDeductStlmDate(riqieSzzhLst.getDeductStlmDate());
		this.setDeductSysTime(riqieSzzhLst.getDeductSysTime());
		this.setGainSysId(riqieSzzhLst.getGainSysId());
		this.setGainSysStance(riqieSzzhLst.getGainSysStance());
		this.setGainMerCode(riqieSzzhLst.getGainMerCode());
		this.setGainMerTermId(riqieSzzhLst.getGainMerTermId());
		this.setGainSysResponse(riqieSzzhLst.getGainSysResponse());
		this.setGainResult(riqieSzzhLst.getGainResult());
		this.setGainTradeAmount(riqieSzzhLst.getGainTradeAmount());
		this.setGainSysReference(riqieSzzhLst.getGainSysReference());
		this.setNii(riqieSzzhLst.getNii());
		this.setAuthorizationCode(riqieSzzhLst.getAuthorizationCode());
		this.setAdditionalResponseData(riqieSzzhLst.getAdditionalResponseData());
		this.setAdditionalData(riqieSzzhLst.getAdditionalData());
		this.setBoc(riqieSzzhLst.getBoc());
		this.setCustomDefineInfo(riqieSzzhLst.getCustomDefineInfo());
		this.setOriginalTransInfo(riqieSzzhLst.getOriginalTransInfo());
		this.setReqProcess(riqieSzzhLst.getReqProcess());
		this.setDeductSysReference(riqieSzzhLst.getDeductSysReference());
		this.setTrademsgType(riqieSzzhLst.getTrademsgType());
		this.setDeductRollbkSysReference(riqieSzzhLst.getDeductRollbkSysReference());
		this.setMerName(riqieSzzhLst.getMerName());
		this.setBkChk(riqieSzzhLst.getBkChk());
		this.setWhetherJs(riqieSzzhLst.isWhetherJs());
		this.setWhetherValid(riqieSzzhLst.isWhetherValid());
		this.setWhetherErroeHandle(riqieSzzhLst.getWhetherErroeHandle());
		this.setWhetherRiqie(riqieSzzhLst.isWhetherRiqie());
		this.setAcqInstIdCode(riqieSzzhLst.getAcqInstIdCode());
		this.setFwdInstIdCode(riqieSzzhLst.getFwdInstIdCode());
		this.setDeductRollbkSysTime(riqieSzzhLst.getDeductRollbkSysTime());
		this.setAgentId(riqieSzzhLst.getAgentId());
		this.setWhetherzero(riqieSzzhLst.getWhetherzero());
		this.setWhtherInnerJs(riqieSzzhLst.isWhtherInnerJs());
		this.setIcCardSerNo(riqieSzzhLst.getIcCardSerNo());
		this.setIcReadAndCondition(riqieSzzhLst.getIcReadAndCondition());
		this.setWhetherQs(riqieSzzhLst.isWhetherQs());
		this.setMerFee(riqieSzzhLst.getMerFee());
		this.setWhetherTk(riqieSzzhLst.isWhetherTk());
		if(riqieSzzhLst.getTradeAmount() < 0){
			this.setZfFee(0-riqieSzzhLst.getZfFee());
			this.setZfFileFee("-"+riqieSzzhLst.getZfFileFee());
		}else{
			this.setZfFee(riqieSzzhLst.getZfFee());
			this.setZfFileFee(riqieSzzhLst.getZfFileFee());
		}
		this.setZfFeeBj(riqieSzzhLst.getZfFeeBj());
		this.setFeeFormula(riqieSzzhLst.getFeeFormula());
		this.setWhetherAccessStance(riqieSzzhLst.isWhetherAccessStance());
		this.setWhtherInnerDz(riqieSzzhLst.isWhtherInnerDz());
		this.setBankId(riqieSzzhLst.getBankId());
		this.setHandlingStatus(0);
		this.setErrorResource(0);
		this.setInstType(0);
	}
	
	/**
	 * 银联CUPS对账单实体
	 * @param duizhangCupsLst
	 */
	public ErrorDataLst(DuizhangCupsLst duizhangCupsLst){
		this.setId(duizhangCupsLst.getId());
		this.setAcqInstIdCode(duizhangCupsLst.getAcqInstIdCode());
		this.setFwdInstIdCode(duizhangCupsLst.getFwdInstIdCode());
		this.setReqSysStance(duizhangCupsLst.getReqSysStance());
		this.setDeductSysStance(duizhangCupsLst.getReqSysStance());
		this.setTradeTime(DYDataUtil.getformatConversionDate(duizhangCupsLst.getReqTime()));
		this.setReqTime(DYDataUtil.getformatConversionDate(duizhangCupsLst.getReqTime()));
		this.setOutAccount(duizhangCupsLst.getOutAccount());
		this.setDeductMerCode(duizhangCupsLst.getMerCode());
		this.setDeductMerTermId(duizhangCupsLst.getTermId());
		this.setReqInputType(duizhangCupsLst.getReqInputType());
		this.setOutAccValidTime(DYDataUtil.getformatConversionDate(duizhangCupsLst.getReqTime()));
		this.setDeductStlmDate(DYDataUtil.getformatConversionDate2(duizhangCupsLst.getDeductStlmDate()));
		this.setDeductRollbkSysTime(DYDataUtil.getformatConversionDate(duizhangCupsLst.getReqTime()));
		this.setDeductSysTime(DYDataUtil.getformatConversionDate(duizhangCupsLst.getReqTime()));
		if(StringUtils.isNotBlank(duizhangCupsLst.getOrigDataStance()) && !StringUtils.equals("000000",duizhangCupsLst.getOrigDataStance())){
			this.setOriginalTransInfo(duizhangCupsLst.getOrigDataStance());//原笔交易流水
		}
		this.setDeductSysId(11);
		try {
			InstInfo instInfo = InstInfoDAO.getInstance().getInstInfoByIdInSQL(11, 0);
			this.setBankId(instInfo.getBank().getId());
		} catch (Exception e) {
			log.error(e);
		}
		String acceptorPayFee = duizhangCupsLst.getAcceptorPayFee();
		String acceptorReceiveFee = duizhangCupsLst.getAcceptorReceiveFee();
		if(StringUtils.equals(acceptorPayFee, "000000000000")){
			this.setZfFileFee(String.valueOf(Float.valueOf(acceptorPayFee)/100));
		}else{
			float zf_fee = Float.valueOf(acceptorReceiveFee);
			if(zf_fee == 0){
				this.setZfFileFee("0");
			}else{
				this.setZfFileFee("-"+String.valueOf(zf_fee/100));
			}
			
		}
		Double tradeAmount = Double.valueOf(duizhangCupsLst.getTradeAmount())*100;
		if(StringUtils.equals(duizhangCupsLst.getMsgType(), "0420")){
			this.setDeductRollbkSysReference(duizhangCupsLst.getDeductSysReference());
			this.setDeductRollBkResponse("00");
			this.setDeductRollBkResult(Short.valueOf("0"));
			this.setDeductRollBkReason(duizhangCupsLst.getReqType());
			this.setDeductRollBkStance(duizhangCupsLst.getReqSysStance());
			this.setDeductRollBk(true);
			this.setReqResponse("A3");
			this.setTradeType(2);
			if(StringUtils.equals(duizhangCupsLst.getProcess(), "000000")){/*消费冲正*/
				this.setWhetherErroeHandle(2);
				this.setTrademsgType(26);
				this.setReqProcess("910000");
				this.setTradeAmount(0-tradeAmount.longValue());
			}else if(StringUtils.equals(duizhangCupsLst.getProcess(), "200000")){/*消费撤销冲正*/
				this.setWhetherErroeHandle(1);
				this.setTradeAmount(tradeAmount.longValue());
				this.setTrademsgType(28);
				this.setReqProcess("200000");
			}else{
				this.setTrademsgType(26);
				this.setReqProcess("910000");
				this.setWhetherErroeHandle(1);
				this.setTradeAmount(tradeAmount.longValue());
			}
		}else{
			this.setTradeType(3);
			this.setDeductSysReference(duizhangCupsLst.getDeductSysReference());
			this.setAuthorizationCode(duizhangCupsLst.getAuthorizationCode());
			this.setDeductSysResponse("00");
			this.setDeductResult(0);
			this.setReqResponse("00");
			if(StringUtils.equals(duizhangCupsLst.getProcess(), "000000")){
				this.setWhetherErroeHandle(1);
				this.setTradeAmount(tradeAmount.longValue());
				this.setTrademsgType(2);
				this.setReqProcess("910000");
			}else if(StringUtils.equals(duizhangCupsLst.getProcess(), "200000") && StringUtils.equals(duizhangCupsLst.getMsgType(), "0200")){
				this.setWhetherErroeHandle(2);
				this.setTradeAmount(0-tradeAmount.longValue());
				this.setTrademsgType(18);
				this.setReqProcess("200000");
			}else if(StringUtils.equals(duizhangCupsLst.getProcess(), "200000") && StringUtils.equals(duizhangCupsLst.getMsgType(), "0220")){
				this.setWhetherErroeHandle(2);
				this.setTrademsgType(20);
				this.setReqProcess("270000");
				this.setTradeAmount(0-tradeAmount.longValue());
			}else{
				this.setWhetherErroeHandle(1);
				this.setTradeAmount(tradeAmount.longValue());
				this.setTrademsgType(2);
				this.setReqProcess("910000");
			}
		}
		this.setBkChk(2);
		this.setWhetherJs(false);
		this.setErrorResource(1);
		this.setHandlingStatus(0);
		this.setWhetherValid(true);
		this.setInstType(0);
		this.setNii(duizhangCupsLst.getDzFileName());
	}
	
	/**
	 * 银联UPMP对账单实体
	 * @param duizhangUpmpLst
	 */
	public ErrorDataLst(DuizhangUpmpLst duizhangUpmpLst){
		this.setId(duizhangUpmpLst.getId());
		this.setAcqInstIdCode(duizhangUpmpLst.getAcqInstIdCode());
		this.setFwdInstIdCode(duizhangUpmpLst.getFwdInstIdCode());
		this.setReqSysStance(duizhangUpmpLst.getReqSysStance());
		this.setDeductSysStance(duizhangUpmpLst.getReqSysStance());
		this.setTradeTime(DYDataUtil.getformatConversionDate(duizhangUpmpLst.getReqTime()));
		this.setReqTime(DYDataUtil.getformatConversionDate(duizhangUpmpLst.getReqTime()));
		this.setOutAccount(duizhangUpmpLst.getOutAccount());
		this.setReqProcess(duizhangUpmpLst.getProcess());
		this.setDeductMerCode(duizhangUpmpLst.getMerCode());
		this.setDeductMerTermId(duizhangUpmpLst.getTermId());
		this.setReqInputType(duizhangUpmpLst.getReqInputType());
		this.setOutAccValidTime(DYDataUtil.getformatConversionDate(duizhangUpmpLst.getReqTime()));
		this.setDeductStlmDate(DYDataUtil.getformatConversionDate2(duizhangUpmpLst.getDeductStlmDate()));
		this.setDeductRollbkSysTime(DYDataUtil.getformatConversionDate(duizhangUpmpLst.getReqTime()));
		this.setDeductSysTime(DYDataUtil.getformatConversionDate(duizhangUpmpLst.getReqTime()));
		if(StringUtils.isNotBlank(duizhangUpmpLst.getOrigDataStance()) && !StringUtils.equals("000000",duizhangUpmpLst.getOrigDataStance())){
			this.setOriginalTransInfo(duizhangUpmpLst.getOrigDataStance());//原笔交易流水
		}
		this.setDeductSysId(55001);
		try {
			InstInfo instInfo = InstInfoDAO.getInstance().getInstInfoByIdInSQL(55001, 1);
			this.setBankId(instInfo.getBank().getId());
		} catch (Exception e) {
			log.error(e);
		}
		String acceptorPayFee = duizhangUpmpLst.getAcceptorPayFee();
		String acceptorReceiveFee = duizhangUpmpLst.getAcceptorReceiveFee();
		if(StringUtils.equals(acceptorPayFee, "000000000000")){
			this.setZfFileFee(String.valueOf(Float.valueOf(acceptorPayFee)/100));
		}else{
			float zf_fee = Float.valueOf(acceptorReceiveFee);
			if(zf_fee == 0){
				this.setZfFileFee("0");
			}else{
				this.setZfFileFee("-"+String.valueOf(zf_fee/100));
			}
			
		}
		
		Double tradeAmount = Double.valueOf(duizhangUpmpLst.getTradeAmount())*100;
		if(StringUtils.equals(duizhangUpmpLst.getMsgType(), "0420")){
			this.setDeductRollbkSysReference(duizhangUpmpLst.getDeductSysReference());
			this.setDeductRollBkResponse("00");
			this.setDeductRollBkResult(Short.valueOf("0"));
			this.setDeductRollBkReason(duizhangUpmpLst.getReqType());
			this.setDeductRollBkStance(duizhangUpmpLst.getReqSysStance());
			this.setDeductRollBk(true);
			this.setReqResponse("A3");
			this.setTradeType(2);
			if(StringUtils.equals(duizhangUpmpLst.getProcess(), "000000")){/*消费冲正*/
				this.setWhetherErroeHandle(2);
				this.setTrademsgType(26);
				this.setTradeAmount(0-tradeAmount.longValue());
			}else if(StringUtils.equals(duizhangUpmpLst.getProcess(), "200000")){/*消费撤销冲正*/
				this.setWhetherErroeHandle(1);
				this.setTradeAmount(tradeAmount.longValue());
				this.setTrademsgType(28);
			}else{
				this.setTrademsgType(26);
				this.setWhetherErroeHandle(1);
				this.setTradeAmount(tradeAmount.longValue());
			}
		}else{
			this.setTradeType(3);
			this.setDeductSysReference(duizhangUpmpLst.getDeductSysReference());
			this.setAuthorizationCode(duizhangUpmpLst.getAuthorizationCode());
			this.setDeductSysResponse("00");
			this.setDeductResult(0);
			this.setReqResponse("00");
			if(StringUtils.equals(duizhangUpmpLst.getProcess(), "000000")){
				this.setWhetherErroeHandle(1);
				this.setTradeAmount(tradeAmount.longValue());
				this.setTrademsgType(2);
			}else if(StringUtils.equals(duizhangUpmpLst.getProcess(), "200000") && StringUtils.equals(duizhangUpmpLst.getMsgType(), "0200")){
				this.setWhetherErroeHandle(2);
				this.setTradeAmount(0-tradeAmount.longValue());
				this.setTrademsgType(18);
			}else if(StringUtils.equals(duizhangUpmpLst.getProcess(), "200000") && StringUtils.equals(duizhangUpmpLst.getMsgType(), "0220")){
				this.setWhetherErroeHandle(2);
				this.setTrademsgType(20);
				this.setTradeAmount(0-tradeAmount.longValue());
			}else{
				this.setWhetherErroeHandle(1);
				this.setTradeAmount(tradeAmount.longValue());
				this.setTrademsgType(2);
			}
		}
		this.setBkChk(2);
		this.setWhetherJs(false);
		this.setErrorResource(1);
		this.setHandlingStatus(0);
		this.setWhetherValid(true);
		this.setInstType(1);
		this.setNii(duizhangUpmpLst.getDzFileName());
	}
	
	/**
	 * 中信对账单实体
	 * @param zhongxingbankLst
	 */
	public ErrorDataLst(DuizhangZhongxingbankLst zhongxingbankLst){
		this.setId(zhongxingbankLst.getId());
		this.setReqSysStance(zhongxingbankLst.getReqSysStance());
		this.setDeductSysStance(zhongxingbankLst.getReqSysStance());
		this.setTradeTime(DYDataUtil.getformatConversionDate(zhongxingbankLst.getTradeTime()));
		this.setReqTime(DYDataUtil.getformatConversionDate(zhongxingbankLst.getTradeTime()));
		this.setOutAccount(zhongxingbankLst.getOutAccount());
		this.setReqProcess(zhongxingbankLst.getReqResponse());
		this.setDeductMerCode(zhongxingbankLst.getDeductMerCode());
		this.setDeductMerTermId(zhongxingbankLst.getDeductMerTermId());
		this.setOutAccValidTime(DYDataUtil.getformatConversionDate(zhongxingbankLst.getTradeTime()));
		this.setDeductStlmDate(DYDataUtil.getformatConversionDate2(zhongxingbankLst.getDeductStlmDate_()));
		this.setDeductRollbkSysTime(DYDataUtil.getformatConversionDate(zhongxingbankLst.getTradeTime()));
		this.setDeductSysTime(DYDataUtil.getformatConversionDate(zhongxingbankLst.getTradeTime()));
		if(StringUtils.isNotBlank(zhongxingbankLst.getOriginalReference())){
			this.setOriginalTransInfo(zhongxingbankLst.getOriginalReference());//原笔交易参考号
		}
		this.setDeductSysId(10);
		try {
			InstInfo instInfo = InstInfoDAO.getInstance().getInstInfoByIdInSQL(10, 0);
			this.setBankId(instInfo.getBank().getId());
		} catch (Exception e) {
			log.error(e);
		}
		this.setTradeType(3);
		this.setTradeFee(zhongxingbankLst.getTradeFee());
		this.setDeductSysReference(zhongxingbankLst.getDeductSysReference());
		this.setAuthorizationCode(zhongxingbankLst.getAuthorizationCode());
		this.setDeductSysResponse("00");
		this.setDeductResult(0);
		this.setReqResponse("00");
		if(StringUtils.equals(zhongxingbankLst.getReqResponse(), "002000")){//消费
			this.setWhetherErroeHandle(1);
			this.setTrademsgType(2);
			this.setTradeAmount(0-Long.valueOf(zhongxingbankLst.getTradeAmount().replace(".", "")));
		}else if(StringUtils.equals(zhongxingbankLst.getReqResponse(), "202000")){//消费撤销
			this.setTrademsgType(18);
			this.setWhetherErroeHandle(2);
			this.setTradeAmount(0-Long.valueOf(zhongxingbankLst.getTradeAmount().replace(".", "")));
		}else if(StringUtils.equals(zhongxingbankLst.getReqResponse(), "204000")){ //消费退货
			this.setTrademsgType(20);
			this.setWhetherErroeHandle(2);
			this.setTradeAmount(0-Long.valueOf(zhongxingbankLst.getTradeAmount().replace(".", "")));
			this.setWhetherTk(true);
		}else{
			this.setWhetherErroeHandle(1);
			this.setTradeAmount(Long.valueOf(zhongxingbankLst.getTradeAmount().replace(".", "")));
		}
		this.setBkChk(2);
		this.setWhetherJs(false);
		this.setErrorResource(1);
		this.setHandlingStatus(0);
		this.setWhetherValid(true);
		this.setInstType(0);
		this.setNii(zhongxingbankLst.getDzFileName());
	}
	
	/**
	 * 北京银行对账单实体
	 * @param duizhangBeijingbankLst
	 */
	public ErrorDataLst(DuizhangBeijingbankLst duizhangBeijingbankLst){
		this.setId(duizhangBeijingbankLst.getId());
		this.setAcqInstIdCode(duizhangBeijingbankLst.getAcqInstIdCode());
		this.setFwdInstIdCode(duizhangBeijingbankLst.getFwdInstIdCode());
		this.setReqSysStance(duizhangBeijingbankLst.getReqSysStance());
		this.setDeductSysStance(duizhangBeijingbankLst.getReqSysStance());
		this.setTradeTime(DYDataUtil.getformatConversionDate(duizhangBeijingbankLst.getReqTime()));
		this.setReqTime(DYDataUtil.getformatConversionDate(duizhangBeijingbankLst.getReqTime()));
		this.setOutAccount(duizhangBeijingbankLst.getOutAccount());
		this.setDeductMerCode(duizhangBeijingbankLst.getMerCode());
		this.setDeductMerTermId(duizhangBeijingbankLst.getTermId());
		this.setReqInputType(duizhangBeijingbankLst.getReqInputType());
		if(StringUtils.isNotBlank(duizhangBeijingbankLst.getOrigDataStance()) && !StringUtils.equals("000000",duizhangBeijingbankLst.getOrigDataStance())){
			this.setOriginalTransInfo(duizhangBeijingbankLst.getOrigDataStance());//原笔交易流水
		}
		this.setOutAccValidTime(DYDataUtil.getformatConversionDate(duizhangBeijingbankLst.getReqTime()));
		this.setDeductStlmDate(DYDataUtil.getformatConversionDate2(duizhangBeijingbankLst.getDeductStlmDate()));
		this.setDeductRollbkSysTime(DYDataUtil.getformatConversionDate(duizhangBeijingbankLst.getReqTime()));
		this.setDeductSysTime(DYDataUtil.getformatConversionDate(duizhangBeijingbankLst.getReqTime()));
		this.setDeductSysId(70001);
		try {
			InstInfo instInfo = InstInfoDAO.getInstance().getInstInfoByIdInSQL(70001, 0);
			this.setBankId(instInfo.getBank().getId());
		} catch (Exception e) {
			log.error(e);
		}
		String acceptorPayFee = duizhangBeijingbankLst.getAcceptorPayFee();
		String acceptorReceiveFee = duizhangBeijingbankLst.getAcceptorReceiveFee();
		if(StringUtils.equals(acceptorPayFee, "000000000000")){
			this.setZfFileFee(String.valueOf(Float.valueOf(acceptorPayFee)/100));
		}else{
			float zf_fee = Float.valueOf(acceptorReceiveFee);
			if(zf_fee == 0){
				this.setZfFileFee("0");
			}else{
				this.setZfFileFee("-"+String.valueOf(zf_fee/100));
			}
			
		}
		
		Double tradeAmount = Double.valueOf(duizhangBeijingbankLst.getTradeAmount())*100;
		if(StringUtils.equals(duizhangBeijingbankLst.getMsgType(), "0420")){
			this.setDeductRollbkSysReference(duizhangBeijingbankLst.getDeductSysReference());
			this.setDeductRollBkResponse("00");
			this.setDeductRollBkResult(Short.valueOf("0"));
			this.setDeductRollBkReason(duizhangBeijingbankLst.getReqType());
			this.setDeductRollBkStance(duizhangBeijingbankLst.getReqSysStance());
			this.setDeductRollBk(true);
			this.setReqResponse("A3");
			this.setTradeType(2);
			if(StringUtils.equals(duizhangBeijingbankLst.getProcess(), "000000")){/*消费冲正*/
				this.setWhetherErroeHandle(2);
				this.setTrademsgType(26);
				this.setReqProcess("910000");
				this.setTradeAmount(0-tradeAmount.longValue());
			}else if(StringUtils.equals(duizhangBeijingbankLst.getProcess(), "200000")){/*消费撤销冲正*/
				this.setWhetherErroeHandle(1);
				this.setTrademsgType(28);
				this.setReqProcess("200000");
				this.setTradeAmount(tradeAmount.longValue());
			}else{
				this.setWhetherErroeHandle(1);
				this.setTrademsgType(26);
				this.setReqProcess("910000");
				this.setTradeAmount(tradeAmount.longValue());
			}
		}else{
			this.setTradeType(3);
			this.setDeductSysReference(duizhangBeijingbankLst.getDeductSysReference());
			this.setAuthorizationCode(duizhangBeijingbankLst.getAuthorizationCode());
			this.setDeductSysResponse("00");
			this.setDeductResult(0);
			this.setReqResponse("00");
			if(StringUtils.equals(duizhangBeijingbankLst.getProcess(), "000000")){
				this.setWhetherErroeHandle(1);
				this.setTrademsgType(2);
				this.setReqProcess("910000");
				this.setTradeAmount(tradeAmount.longValue());
			}else if(StringUtils.equals(duizhangBeijingbankLst.getProcess(), "200000") && StringUtils.equals(duizhangBeijingbankLst.getMsgType(), "0200")){
				this.setWhetherErroeHandle(2);
				this.setTrademsgType(18);
				this.setReqProcess("200000");
				this.setTradeAmount(0-tradeAmount.longValue());
			}else if(StringUtils.equals(duizhangBeijingbankLst.getProcess(), "200000") && StringUtils.equals(duizhangBeijingbankLst.getMsgType(), "0220")){
				this.setWhetherErroeHandle(2);
				this.setTrademsgType(20);
				this.setReqProcess("270000");
				this.setTradeAmount(0-tradeAmount.longValue());
				this.setWhetherTk(true);
			}else{
				this.setWhetherErroeHandle(1);
				this.setTrademsgType(2);
				this.setReqProcess("910000");
				this.setTradeAmount(tradeAmount.longValue());
			}
		}
		this.setBkChk(2);
		this.setWhetherJs(false);
		this.setErrorResource(1);
		this.setHandlingStatus(0);
		this.setWhetherValid(true);
		this.setInstType(0);
		this.setNii(duizhangBeijingbankLst.getDzFileName());
	}
	
	/**
	 * 大连交行对账单
	 * @param duizhangDljhLst
	 * @throws ParseException
	 */
	public ErrorDataLst(DuizhangDljhLst duizhangDljhLst) throws ParseException{
		this.setId(duizhangDljhLst.getId());
		this.setReqProcess(duizhangDljhLst.getProcess());
		this.setOutAccount(duizhangDljhLst.getOutAccount());
		this.setReqSysStance(duizhangDljhLst.getReqSysStance());
		this.setDeductSysId(14);
		try {
			InstInfo instInfo = InstInfoDAO.getInstance().getInstInfoByIdInSQL(14, 0);
			this.setBankId(instInfo.getBank().getId());
		} catch (Exception e) {
			log.error(e);
		}
		this.setTradeTime(DYDataUtil.getformatConversionDate(duizhangDljhLst.getReqTime()));
		this.setReqTime(DYDataUtil.getformatConversionDate(duizhangDljhLst.getReqTime()));
		this.setOutAccValidTime(DYDataUtil.getformatConversionDate(duizhangDljhLst.getReqTime()));
		this.setDeductStlmDate(DYDataUtil.getformatConversionDate2(duizhangDljhLst.getDeductStlmDate()));
		this.setDeductRollbkSysTime(DYDataUtil.getformatConversionDate(duizhangDljhLst.getReqTime()));
		this.setDeductSysTime(DYDataUtil.getformatConversionDate(duizhangDljhLst.getReqTime()));
		this.setDeductMerCode(duizhangDljhLst.getMerCode());
		this.setDeductMerTermId(duizhangDljhLst.getTermId());
		this.setAuthorizationCode(duizhangDljhLst.getAuthorizationCode());
		this.setDeductResult(0);
		
		Double tradeAmount = Double.valueOf(duizhangDljhLst.getTradeAmount())*100;
		if(StringUtils.equals(duizhangDljhLst.getMsgType(), "0400")){ //冲正交易
			this.setZfFileFee("-"+duizhangDljhLst.getTradeFee());
			this.setDeductRollBkResponse("00");
			this.setDeductRollBkResult(Short.valueOf("0"));
			this.setDeductRollBkStance(duizhangDljhLst.getReqSysStance());
			this.setDeductRollBk(true);
			this.setReqResponse("A3");
			this.setTradeType(2);
			if(StringUtils.equals(duizhangDljhLst.getProcess(), "000000")){ //消费冲正
				this.setWhetherErroeHandle(1);
				this.setTrademsgType(26);
				this.setTradeAmount(0-tradeAmount.longValue());
			}else if(StringUtils.equals(duizhangDljhLst.getProcess(), "200000")){ //消费撤销冲正
				this.setWhetherErroeHandle(1);
				this.setTrademsgType(28);
				this.setTradeAmount(tradeAmount.longValue());
			}else{
				this.setWhetherErroeHandle(1);
				this.setTrademsgType(26);
				this.setTradeAmount(0-tradeAmount.longValue());
			}
			this.setDeductRollbkSysReference(duizhangDljhLst.getDeductSysReference());
		}else{
			this.setZfFileFee(duizhangDljhLst.getTradeFee());
			this.setTradeType(3);
			this.setDeductSysReference(duizhangDljhLst.getDeductSysReference());
			this.setDeductSysResponse("00");
			this.setDeductResult(0);
			this.setDeductSysStance(duizhangDljhLst.getReqSysStance());
			this.setReqResponse("00");
			if(StringUtils.equals(duizhangDljhLst.getProcess(), "000000")){
				this.setWhetherErroeHandle(1);
				this.setTrademsgType(2);
				this.setTradeAmount(tradeAmount.longValue());
			}else if(StringUtils.equals(duizhangDljhLst.getProcess(), "200000") && StringUtils.equals(duizhangDljhLst.getMsgType(), "0200")){
				this.setWhetherErroeHandle(2);
				this.setTrademsgType(18);
				this.setTradeAmount(0-tradeAmount.longValue());
			}else if(StringUtils.equals(duizhangDljhLst.getProcess(), "200000") && StringUtils.equals(duizhangDljhLst.getMsgType(), "0220")){
				this.setWhetherErroeHandle(2);
				this.setTrademsgType(20);
				this.setTradeAmount(0-tradeAmount.longValue());
				this.setWhetherTk(true);
			}else{
				this.setWhetherErroeHandle(1);
				this.setTrademsgType(2);
				this.setTradeAmount(tradeAmount.longValue());
			}
		}
		this.setBkChk(2);
		this.setWhetherJs(false);
		this.setErrorResource(1);
		this.setHandlingStatus(0);
		this.setWhetherValid(true);
		this.setInstType(0);
		this.setNii(duizhangDljhLst.getDzFileName());
	}
	
	
	/**
	 * 深圳中行对账单
	 * @param duizhangSzzhLst
	 * @throws ParseException
	 */
	public ErrorDataLst(DuizhangSzzhLst duizhangSzzhLst) throws ParseException{
		this.setReqSysStance(duizhangSzzhLst.getDeductSysReference());
		this.setDeductSysStance(duizhangSzzhLst.getDeductSysReference());
		this.setId(duizhangSzzhLst.getId());
		this.setOutAccount(duizhangSzzhLst.getOutAccount());
		this.setDeductSysId(3);
		try {
			InstInfo instInfo = InstInfoDAO.getInstance().getInstInfoByIdInSQL(3, 0);
			this.setBankId(instInfo.getBank().getId());
		} catch (Exception e) {
			log.error(e);
		}
		this.setTradeTime(DYDataUtil.getformatConversionDate(duizhangSzzhLst.getReqTime()));
		this.setReqTime(DYDataUtil.getformatConversionDate(duizhangSzzhLst.getReqTime()));
		this.setOutAccValidTime(DYDataUtil.getformatConversionDate(duizhangSzzhLst.getReqTime()));
		this.setDeductStlmDate(DYDataUtil.getformatConversionDate2(duizhangSzzhLst.getDeductStlmDate()));
		this.setDeductRollbkSysTime(DYDataUtil.getformatConversionDate(duizhangSzzhLst.getReqTime()));
		this.setDeductSysTime(DYDataUtil.getformatConversionDate(duizhangSzzhLst.getReqTime()));
		this.setDeductMerCode(duizhangSzzhLst.getMerCode());
		this.setDeductMerTermId(duizhangSzzhLst.getTermId());
		this.setAuthorizationCode(duizhangSzzhLst.getAuthorizationCode());
		this.setDeductResult(0);
		this.setTradeType(3);
		this.setDeductSysReference(duizhangSzzhLst.getDeductSysReference());
		this.setDeductSysResponse("00");
		this.setDeductResult(0);
		this.setReqResponse("00");
		this.setWhetherErroeHandle(1);
		Double tradeAmount = Double.valueOf(duizhangSzzhLst.getTradeAmount())*100;
		if(StringUtils.isNotBlank(duizhangSzzhLst.getTradeCode()) && (StringUtils.equals(duizhangSzzhLst.getTradeCode(), "REFI") || StringUtils.equals(duizhangSzzhLst.getTradeCode(), "REFP"))){
			this.setReqProcess("270000");
			this.setTrademsgType(20);
			this.setTradeAmount(0-tradeAmount.longValue());
			this.setZfFileFee("-"+duizhangSzzhLst.getTradeFee());
			this.setWhetherTk(true);
		}else if(StringUtils.isNotBlank(duizhangSzzhLst.getTradeCode()) && StringUtils.equals(duizhangSzzhLst.getTradeCode(), "PCEI")  || StringUtils.equals(duizhangSzzhLst.getTradeCode(), "PCEP")){
			this.setReqProcess("910000");
			this.setTrademsgType(2);
			this.setTradeAmount(tradeAmount.longValue());
			this.setZfFileFee(duizhangSzzhLst.getTradeFee());
		}else{
			this.setReqProcess("510000");
			this.setTrademsgType(56);
			this.setTradeAmount(tradeAmount.longValue());
			this.setZfFileFee(duizhangSzzhLst.getTradeFee());
		}
		this.setBkChk(2);
		this.setWhetherJs(false);
		this.setErrorResource(1);
		this.setHandlingStatus(0);
		this.setWhetherValid(true);
		this.setInstType(0);
		this.setNii(duizhangSzzhLst.getDzFileName());
	}
	
	/**
	 * 深圳工行对账单
	 * @param DuizhangSzghLst
	 * @throws ParseException
	 */
	public ErrorDataLst(DuizhangSzghLst duizhangSzghLst) throws ParseException{
		this.setId(duizhangSzghLst.getId());
		this.setOutAccount(duizhangSzghLst.getOutAccount());
		this.setDeductSysId(15);
		try {
			InstInfo instInfo = InstInfoDAO.getInstance().getInstInfoByIdInSQL(15, 0);
			this.setBankId(instInfo.getBank().getId());
		} catch (Exception e) {
			log.error(e);
		}
		this.setTradeTime(DYDataUtil.getformatConversionDate(duizhangSzghLst.getReqTime()));
		this.setReqTime(DYDataUtil.getformatConversionDate(duizhangSzghLst.getReqTime()));
		this.setOutAccValidTime(DYDataUtil.getformatConversionDate(duizhangSzghLst.getReqTime()));
		this.setDeductStlmDate(DYDataUtil.getformatConversionDate2(duizhangSzghLst.getDeductStlmDate()));
		this.setDeductRollbkSysTime(DYDataUtil.getformatConversionDate(duizhangSzghLst.getReqTime()));
		this.setDeductSysTime(DYDataUtil.getformatConversionDate(duizhangSzghLst.getReqTime()));
		this.setDeductMerCode(duizhangSzghLst.getMerCode());
		this.setDeductMerTermId(duizhangSzghLst.getTermId());
		this.setAuthorizationCode(duizhangSzghLst.getAuthorizationCode());
		this.setReqSysStance(duizhangSzghLst.getReqSysStance());
		this.setDeductSysStance(duizhangSzghLst.getReqSysStance());
		this.setDeductResult(0);
		this.setZfFileFee(duizhangSzghLst.getTradeFee());
		this.setTradeType(3);
		this.setDeductSysReference(duizhangSzghLst.getDeductSysReference());
		this.setDeductSysResponse("00");
		this.setDeductResult(0);
		this.setReqResponse("00");
		Double tradeAmount = Double.valueOf(duizhangSzghLst.getTradeAmount())*100;
		if(StringUtils.isNotBlank(duizhangSzghLst.getProcess())){
			if(StringUtils.equals(duizhangSzghLst.getProcess(), "02860")){//消费
				this.setReqProcess("910000");
				this.setTrademsgType(2);
				this.setTradeAmount(tradeAmount.longValue());
				this.setZfFileFee(duizhangSzghLst.getTradeFee());
			}else if(StringUtils.equals(duizhangSzghLst.getProcess(), "02867")){//退货
				this.setReqProcess("270000");
				this.setTrademsgType(20);
				this.setTradeAmount(0-tradeAmount.longValue());
				this.setZfFileFee("-"+duizhangSzghLst.getTradeFee());
				this.setWhetherTk(true);
			}else if(StringUtils.equals(duizhangSzghLst.getProcess(), "02865")){//预授权完成
				this.setReqProcess("000000");
				this.setTrademsgType(56);
				this.setTradeAmount(tradeAmount.longValue());
				this.setZfFileFee(duizhangSzghLst.getTradeFee());
			}else{
				this.setReqProcess("910000");
				this.setTrademsgType(2);
				this.setTradeAmount(tradeAmount.longValue());
				this.setZfFileFee(duizhangSzghLst.getTradeFee());
			}
		}else{
			this.setReqProcess("910000");
			this.setTrademsgType(2);
		}
		this.setWhetherErroeHandle(1);
		this.setBkChk(2);
		this.setWhetherJs(false);
		this.setErrorResource(1);
		this.setHandlingStatus(0);
		this.setWhetherValid(true);
		this.setInstType(0);
		this.setNii(duizhangSzghLst.getDzFileName());
		this.setAdditionalResponseData(duizhangSzghLst.getReqSysStance());
	}

/*[CONSTRUCTOR MARKER END]*/
	public static void main(String[] args) throws Exception {
		System.out.println(DYDataUtil.getSimpleDateFormat("yyyyMMddHHmmss").parse("20141119032826"));
		System.out.println(new SimpleDateFormat("HHmmss").format(new Date(Long.parseLong("70106")*1000)));
		System.out.println(Long.valueOf("-0.10".replaceAll(",", "").replace(".", "")));
	}

}