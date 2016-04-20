package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseRiqieZhongxingbankLst;



public class RiqieZhongxingbankLst extends BaseRiqieZhongxingbankLst {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public RiqieZhongxingbankLst () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public RiqieZhongxingbankLst (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public RiqieZhongxingbankLst (
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
	
	public RiqieZhongxingbankLst(OriginalZhongxingbankLst originalZhongxingbankLst){
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
		this.setZfFee(originalZhongxingbankLst.getZfFee());
		this.setZfFileFee(originalZhongxingbankLst.getZfFileFee());
		this.setZfFeeBj(originalZhongxingbankLst.getZfFeeBj());
		this.setFeeFormula(originalZhongxingbankLst.getFeeFormula());
		this.setWhetherAccessStance(originalZhongxingbankLst.isWhetherAccessStance());
		this.setWhtherInnerDz(originalZhongxingbankLst.isWhtherInnerDz());
		this.setBankId(originalZhongxingbankLst.getBankId());
	}

/*[CONSTRUCTOR MARKER END]*/


}