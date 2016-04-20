package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseRiqieSzzhLst;



public class RiqieSzzhLst extends BaseRiqieSzzhLst {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public RiqieSzzhLst () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public RiqieSzzhLst (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public RiqieSzzhLst (
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
		java.lang.String agentId,
		java.lang.String whetherzero,
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
			agentId,
			whetherzero,
			bankId);
	}
	
	public RiqieSzzhLst(OriginalSzzhLst originalSzzhLst){
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
		this.setZfFee(originalSzzhLst.getZfFee());
		this.setZfFileFee(originalSzzhLst.getZfFileFee());
		this.setZfFeeBj(originalSzzhLst.getZfFeeBj());
		this.setFeeFormula(originalSzzhLst.getFeeFormula());
		this.setWhetherAccessStance(originalSzzhLst.isWhetherAccessStance());
		this.setWhtherInnerDz(originalSzzhLst.isWhtherInnerDz());
		this.setBankId(originalSzzhLst.getBankId());
	}

/*[CONSTRUCTOR MARKER END]*/


}