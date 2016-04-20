package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseRiqieSzghLst;



public class RiqieSzghLst extends BaseRiqieSzghLst {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public RiqieSzghLst () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public RiqieSzghLst (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public RiqieSzghLst (
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
		java.lang.String acqInstIdCode,
		java.lang.String fwdInstIdCode,
		java.util.Date deductRollbkSysTime,
		java.lang.String agentId,
		java.lang.String whetherzero,
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
			acqInstIdCode,
			fwdInstIdCode,
			deductRollbkSysTime,
			agentId,
			whetherzero,
			whtherInnerJs,
			bankId);
	}
	
	public RiqieSzghLst(OriginalSzghLst originalSzghLst){
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
		this.setZfFee(originalSzghLst.getZfFee());
		this.setZfFileFee(originalSzghLst.getZfFileFee());
		this.setZfFeeBj(originalSzghLst.getZfFeeBj());
		this.setFeeFormula(originalSzghLst.getFeeFormula());
		this.setWhetherAccessStance(originalSzghLst.isWhetherAccessStance());
		this.setWhtherInnerDz(originalSzghLst.isWhtherInnerDz());
		this.setBankId(originalSzghLst.getBankId());
	}

/*[CONSTRUCTOR MARKER END]*/


}