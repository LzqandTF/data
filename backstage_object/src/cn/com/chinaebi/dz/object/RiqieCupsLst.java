package cn.com.chinaebi.dz.object;

import cn.com.chinaebi.dz.object.base.BaseRiqieCupsLst;



public class RiqieCupsLst extends BaseRiqieCupsLst {
	private static final long serialVersionUID = 1L;

/*[CONSTRUCTOR MARKER BEGIN]*/
	public RiqieCupsLst () {
		super();
	}

	/**
	 * Constructor for primary key
	 */
	public RiqieCupsLst (java.lang.String id) {
		super(id);
	}

	/**
	 * Constructor for required fields
	 */
	public RiqieCupsLst (
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

	public RiqieCupsLst(OriginalCupsLst originalCupsLst){
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
		this.setZfFee(originalCupsLst.getZfFee());
		this.setZfFileFee(originalCupsLst.getZfFileFee());
		this.setZfFeeBj(originalCupsLst.getZfFeeBj());
		this.setFeeFormula(originalCupsLst.getFeeFormula());
		this.setWhetherAccessStance(originalCupsLst.isWhetherAccessStance());
		this.setWhtherInnerDz(originalCupsLst.isWhtherInnerDz());
		this.setBankId(originalCupsLst.getBankId());
	}
/*[CONSTRUCTOR MARKER END]*/


}