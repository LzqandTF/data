package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseRiqieDljhLst;



public class RiqieDljhLst extends BaseRiqieDljhLst {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public RiqieDljhLst () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public RiqieDljhLst (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public RiqieDljhLst (
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

	public RiqieDljhLst(OriginalDljhLst originalDljhLst){
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
		this.setZfFee(originalDljhLst.getZfFee());
		this.setZfFileFee(originalDljhLst.getZfFileFee());
		this.setZfFeeBj(originalDljhLst.getZfFeeBj());
		this.setFeeFormula(originalDljhLst.getFeeFormula());
		this.setWhetherAccessStance(originalDljhLst.isWhetherAccessStance());
		this.setWhtherInnerDz(originalDljhLst.isWhtherInnerDz());
		this.setBankId(originalDljhLst.getBankId());
	}
/*[CONSTRUCTOR MARKER END]*/


}