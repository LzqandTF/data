package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseRiqieBeijingbankLst;



public class RiqieBeijingbankLst extends BaseRiqieBeijingbankLst {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public RiqieBeijingbankLst () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public RiqieBeijingbankLst (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public RiqieBeijingbankLst (
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
		java.util.Date deductRollbkSysTime) {

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
			deductRollbkSysTime);
	}
	
	public RiqieBeijingbankLst(OriginalBeijingbankLst originalBeijingbankLst){
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
		this.setZfFee(originalBeijingbankLst.getZfFee());
		this.setZfFileFee(originalBeijingbankLst.getZfFileFee());
		this.setZfFeeBj(originalBeijingbankLst.getZfFeeBj());
		this.setFeeFormula(originalBeijingbankLst.getFeeFormula());
		this.setWhetherAccessStance(originalBeijingbankLst.isWhetherAccessStance());
		this.setWhtherInnerDz(originalBeijingbankLst.isWhtherInnerDz());
		this.setBankId(originalBeijingbankLst.getBankId());
	}


/*[CONSTRUCTOR MARKER END]*/


}