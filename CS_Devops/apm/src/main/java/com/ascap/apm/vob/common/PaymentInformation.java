package com.ascap.apm.vob.common;

import com.ascap.apm.vob.BaseVOB;

/**
 * @author Shyam_Narayana
 */
public class PaymentInformation extends BaseVOB {
	/**
     * 
     */
    private static final long serialVersionUID = 5994393216970622275L;

    //Key
	private String paymentInformationId = null; //PTY_CHG_SRV_PMT_DTL_ID

	//Payment information applied to the following Party Id/ Membership Application Confirmation Id
	//Membership Application Confirmation Id is Used because of current Mainframe Election/Party Incremental uploads.
	private String paymentFromPartyId = null; //PTY_ID
	private String paymentFromApplicationConfirmationId = null; //APL_CONF_ID

	//Chargebale Service Code used
	private String chargeableServiceTypeCode = null; //CHG_SRV_TYP_CDE
	private String chargeableServiceTypeDescription = null; //CHG_SRV_TYP_CDE

	private String paymentAmount = null; //PMT_AMT

	//denotes Electronic Check, Credit Card, ASCAP Waiver
	private String paymentChargeAccountTypeCode = null;//PTY_CHG_SRV_PMT_MTH_CDE
	private String paymentChargeAccountTypeDescription = null;

	//Name as appears on the Credit Card/Bank Account
	private String accountHolderName = null; //ACC_HLD_NA

	//Applicable for Credit Card Payment
	private String creditCardTypeCode = null; //CR_CARD_TYP_CDE
	private String creditCardTypeDescription = null; //CR_CARD_TYP_CDE
	private String creditCardNumber = null; //CR_CARD_NR
	private String creditCardCVVNumber = null; //CR_CARD_CVV_NR
	private String creditCardExpirationMonth = null; //CR_CARD_EXP_MN
	private String creditCardExpirationYear = null; //CR_CARD_EXP_YR

	//Applicable for Electronic Check
	private String bankRoutingNumber = null; //BANK_ROUT_NR
	private String bankAccountNumber = null; //BANK_ACC_NR
	private String bankName = null; //BANK_NA

	//Billing Address Information
	private String billingAddressLine1 = null; //ADR_LN1
	private String billingAddressLine2 = null; //ADR_LN2
	private String billingCity = null; //CTY
	private String billingPostalCode = null; //PSL_CDE
	private String billingStateCode = null; //ST_CDE
	private String billingProvince = null; //PROV
	private String billingCountryCode = null; //CNY_CDE

	//Payment Date and Payment process results
	private String paymentDate = null; //PMT_DT
	private String paymentSubmitDate = null; //PMT_SUBMIT_DT
	private String paymentReturnCode = null;
	private String paymentConfirmationCode = null;
	private String paymentTransactionNumber = null;
	private String paymentReturnErrorCode = null;
	private String paymentReturnErrorDescription = null;
	private String	billingAddressSameAs = "R";

	private String epayBIN=null;
	private String epayMerchantId=null;
	private String epayOrderId=null;

	private String epayReponseAVSRespCode=null;
	private String epayReponseResponseCode=null;
	private String epayReponseTxRefNum=null;
	private String epayReponseAuthCode=null;
	private String epayReponseErrorVal=null;
	private String epayReponseErrMsg=null;
	private String epayReponseIsApproved=null;
	private String epayReponseIsGood=null;
	private String epayReponseIsDeclined=null;
	private String epayReponseCVV2RespCode=null;
	private String epayReponseStatusVal=null;

	//Used for Display Purposes only.
	private String ascapUserId = null; //ASCAP_USR_ID
	private String ascapUserFullName = null;
	private String ascapUserEmailAddress = null;
	private String waiverCode = null; //WAIVER_CDE
	private String waiverSetupDate = null;
	
//	PAC Donation
	private String pacDonateFlag = null;
	private String pacDonateAmount = null;
	private String pacDonateAmountOther = null;
	private String pacDonateAmtToProcess = null;
	private String pacDonateOcc = null;
	private String pacDonateEmp = null;
	private String pacReadRulesFlag = null;
	private String pacSource = null;
	private String totalPacAppFeeAmount = null;

//  Paypal
	
	private String payerIPAddress=null;
	private String payerSessionId = null;
	private String paypalRequestReturnUrl = null;
	private String paypalRequestCancelUrl = null;
	
	
//    Paypal Response
	private String paypalResponseBuild = null;
	private String paypalResponseCorrID = null;
	private String paypalResponseVersion = null;
	private String paypalResponseAck = null;
	private String paypalResponsePayStatus = null;
	private String paypalResponsePendReason = null;
	private String paypalResponseTimeStamp = null;
	private String paypalResponseECToken = null;
	private String paypalResponseAmount = null;
	
	private String donationIsProcessed = null;
	
	private String donationVobFlag = null;
	
	private String payerEmailAddress = null;
	private String paymentGatewayType = null;
	
	private String accountHolderFName = null;
	private String accountHolderLName = null;
	private String accountHolderMInitial = null;
	
	private String paymentFormAlreadySubmitted = null;
	
    private String corresAddressLine1 = null;
    private String corresAddressLine2 = null;
    private String corresCity = null;
    private String corresPostalCode = null;
    private String corresStateCode = null;
    private String corresCountryCode = null;
    private String corresSameAsBillingFlag = null;
    
    private String isDonationVOB= "N";
    
    private String donorFName = null;//
    private String donorLName = null;//
    private String donorMInitial = null;//

    
	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder outStrBuff;
		outStrBuff = new StringBuilder();
		outStrBuff
			.append("com.ascap.apm.common.vob.common.PaymentInformation {")
			.append("paymentInformationId= '")
			.append(paymentInformationId)
			.append("', ")
			.append("paymentFromPartyId= '")
			.append(paymentFromPartyId)
			.append("', ")
			.append("paymentFromApplicationConfirmationId= '")
			.append(paymentFromApplicationConfirmationId)
			.append("', ")
			.append("chargeableServiceTypeCode= '")
			.append(chargeableServiceTypeCode)
			.append("', ")
			.append("chargeableServiceTypeDescription= '")
			.append(chargeableServiceTypeDescription)
			.append("', ")
			.append("paymentAmount= '")
			.append(paymentAmount)
			.append("', ")
			.append("paymentChargeAccountTypeCode= '")
			.append(paymentChargeAccountTypeCode)
			.append("', ")
			.append("paymentChargeAccountTypeDescription= '")
			.append(paymentChargeAccountTypeDescription)
			.append("', ")
// 			Commented because this information is not supposed to be logged.			
//			.append("accountHolderName= '")
//			.append(accountHolderName)
//			.append("', ")
//			.append("creditCardTypeCode= '")
//			.append(creditCardTypeCode)
//			.append("', ")
//			.append("creditCardTypeDescription= '")
//			.append(creditCardTypeDescription)
//			.append("', ")
//			.append("creditCardExpirationMonth= '")
//			.append(creditCardExpirationMonth)
//			.append("', ")
//			.append("creditCardExpirationYear= '")
//			.append(creditCardExpirationYear)
//			.append("', ")
//			.append("bankRoutingNumber= '")
//			.append(bankRoutingNumber)
//			.append("', ")
//			.append("bankAccountNumber= '")
//			.append(bankAccountNumber)
//			.append("', ")
//			.append("bankName= '")
//			.append(bankName)
//			.append("', ")
//			.append("waiverCode= '")
//			.append(waiverCode)
//			.append("', ")
//			.append("billingAddressLine1= '")
//			.append(billingAddressLine1)
//			.append("', ")
//			.append("billingAddressLine2= '")
//			.append(billingAddressLine2)
//			.append("', ")
//			.append("billingCity= '")
//			.append(billingCity)
//			.append("', ")
//			.append("billingPostalCode= '")
//			.append(billingPostalCode)
//			.append("', ")
//			.append("billingStateCode= '")
//			.append(billingStateCode)
//			.append("', ")
//			.append("billingProvince= '")
//			.append(billingProvince)
//			.append("', ")
//			.append("billingCountryCode= '")
//			.append(billingCountryCode)
//			.append("', ")
			.append("paymentDate= '")
			.append(paymentDate)
			.append("', ")
			.append("paymentReturnCode= '")
			.append(paymentReturnCode)
			.append("', ")
			.append("paymentConfirmationCode= '")
			.append(paymentConfirmationCode)
			.append("', ")
			.append("paymentReturnErrorCode= '")
			.append(paymentReturnErrorCode)
			.append("', ")
			.append("paymentReturnErrorDescription= '")
			.append(paymentReturnErrorDescription)
			.append("'}");
		return (outStrBuff.toString());
	}



	/**
	 * Returns the accountHolderName.
	 * @return String
	 */
	public String getAccountHolderName() {
		return accountHolderName;
	}

	/**
	 * Returns the paymentFromApplicationConfirmationId.
	 * @return String
	 */
	public String getPaymentFromApplicationConfirmationId() {
		return paymentFromApplicationConfirmationId;
	}

	/**
	 * Returns the bankAccountNumber.
	 * @return String
	 */
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	/**
	 * Returns the bankName.
	 * @return String
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * Returns the bankRoutingNumber.
	 * @return String
	 */
	public String getBankRoutingNumber() {
		return bankRoutingNumber;
	}

	/**
	 * Returns the billingAddressLine1.
	 * @return String
	 */
	public String getBillingAddressLine1() {
		return billingAddressLine1;
	}

	/**
	 * Returns the billingAddressLine2.
	 * @return String
	 */
	public String getBillingAddressLine2() {
		return billingAddressLine2;
	}

	/**
	 * Returns the billingCity.
	 * @return String
	 */
	public String getBillingCity() {
		return billingCity;
	}

	/**
	 * Returns the billingCountryCode.
	 * @return String
	 */
	public String getBillingCountryCode() {
		return billingCountryCode;
	}

	/**
	 * Returns the billingPostalCode.
	 * @return String
	 */
	public String getBillingPostalCode() {
		return billingPostalCode;
	}

	/**
	 * Returns the billingProvince.
	 * @return String
	 */
	public String getBillingProvince() {
		return billingProvince;
	}

	/**
	 * Returns the billingStateCode.
	 * @return String
	 */
	public String getBillingStateCode() {
		return billingStateCode;
	}

	/**
	 * Returns the chargeableServiceTypeCode.
	 * @return String
	 */
	public String getChargeableServiceTypeCode() {
		return chargeableServiceTypeCode;
	}

	/**
	 * Returns the chargeableServiceTypeDescription.
	 * @return String
	 */
	public String getChargeableServiceTypeDescription() {
		return chargeableServiceTypeDescription;
	}

	/**
	 * Returns the creditCardCVVNumber.
	 * @return String
	 */
	public String getCreditCardCVVNumber() {
		return creditCardCVVNumber;
	}

	/**
	 * Returns the creditCardExpirationMonth.
	 * @return String
	 */
	public String getCreditCardExpirationMonth() {
		return creditCardExpirationMonth;
	}

	/**
	 * Returns the creditCardExpirationYear.
	 * @return String
	 */
	public String getCreditCardExpirationYear() {
		return creditCardExpirationYear;
	}

	/**
	 * Returns the creditCardNumber.
	 * @return String
	 */
	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	/**
	 * Returns the creditCardTypeCode.
	 * @return String
	 */
	public String getCreditCardTypeCode() {
		return creditCardTypeCode;
	}

	/**
	 * Returns the creditCardTypeDescription.
	 * @return String
	 */
	public String getCreditCardTypeDescription() {
		return creditCardTypeDescription;
	}

	/**
	 * Returns the paymentFromPartyId.
	 * @return String
	 */
	public String getPaymentFromPartyId() {
		return paymentFromPartyId;
	}

	/**
	 * Returns the paymentAmount.
	 * @return String
	 */
	public String getPaymentAmount() {
		return paymentAmount;
	}

	/**
	 * Returns the paymentChargeAccountTypeCode.
	 * @return String
	 */
	public String getPaymentChargeAccountTypeCode() {
		return paymentChargeAccountTypeCode;
	}

	/**
	 * Returns the paymentChargeAccountTypeDescription.
	 * @return String
	 */
	public String getPaymentChargeAccountTypeDescription() {
		return paymentChargeAccountTypeDescription;
	}

	/**
	 * Returns the paymentConfirmationCode.
	 * @return String
	 */
	public String getPaymentConfirmationCode() {
		return paymentConfirmationCode;
	}

	/**
	 * Returns the paymentDate.
	 * @return String
	 */
	public String getPaymentDate() {
		return paymentDate;
	}

	/**
	 * Returns the paymentInformationId.
	 * @return String
	 */
	public String getPaymentInformationId() {
		return paymentInformationId;
	}

	/**
	 * Returns the paymentReturnCode.
	 * @return String
	 */
	public String getPaymentReturnCode() {
		return paymentReturnCode;
	}

	/**
	 * Returns the paymentReturnErrorCode.
	 * @return String
	 */
	public String getPaymentReturnErrorCode() {
		return paymentReturnErrorCode;
	}

	/**
	 * Returns the paymentReturnErrorDescription.
	 * @return String
	 */
	public String getPaymentReturnErrorDescription() {
		return paymentReturnErrorDescription;
	}



	/**
	 * Returns the waiverCode.
	 * @return String
	 */
	public String getWaiverCode() {
		return waiverCode;
	}

	/**
	 * Sets the accountHolderName.
	 * @param accountHolderName The accountHolderName to set
	 */
	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	/**
	 * Sets the paymentFromApplicationConfirmationId.
	 * @param paymentFromApplicationConfirmationId The paymentFromApplicationConfirmationId to set
	 */
	public void setPaymentFromApplicationConfirmationId(String paymentFromApplicationConfirmationId) {
		this.paymentFromApplicationConfirmationId = paymentFromApplicationConfirmationId;
	}

	/**
	 * Sets the bankAccountNumber.
	 * @param bankAccountNumber The bankAccountNumber to set
	 */
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	/**
	 * Sets the bankName.
	 * @param bankName The bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * Sets the bankRoutingNumber.
	 * @param bankRoutingNumber The bankRoutingNumber to set
	 */
	public void setBankRoutingNumber(String bankRoutingNumber) {
		this.bankRoutingNumber = bankRoutingNumber;
	}

	/**
	 * Sets the billingAddressLine1.
	 * @param billingAddressLine1 The billingAddressLine1 to set
	 */
	public void setBillingAddressLine1(String billingAddressLine1) {
		this.billingAddressLine1 = billingAddressLine1;
	}

	/**
	 * Sets the billingAddressLine2.
	 * @param billingAddressLine2 The billingAddressLine2 to set
	 */
	public void setBillingAddressLine2(String billingAddressLine2) {
		this.billingAddressLine2 = billingAddressLine2;
	}

	/**
	 * Sets the billingCity.
	 * @param billingCity The billingCity to set
	 */
	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}

	/**
	 * Sets the billingCountryCode.
	 * @param billingCountryCode The billingCountryCode to set
	 */
	public void setBillingCountryCode(String billingCountryCode) {
		this.billingCountryCode = billingCountryCode;
	}

	/**
	 * Sets the billingPostalCode.
	 * @param billingPostalCode The billingPostalCode to set
	 */
	public void setBillingPostalCode(String billingPostalCode) {
		this.billingPostalCode = billingPostalCode;
	}

	/**
	 * Sets the billingProvince.
	 * @param billingProvince The billingProvince to set
	 */
	public void setBillingProvince(String billingProvince) {
		this.billingProvince = billingProvince;
	}

	/**
	 * Sets the billingStateCode.
	 * @param billingStateCode The billingStateCode to set
	 */
	public void setBillingStateCode(String billingStateCode) {
		this.billingStateCode = billingStateCode;
	}

	/**
	 * Sets the chargeableServiceTypeCode.
	 * @param chargeableServiceTypeCode The chargeableServiceTypeCode to set
	 */
	public void setChargeableServiceTypeCode(String chargeableServiceTypeCode) {
		this.chargeableServiceTypeCode = chargeableServiceTypeCode;
	}

	/**
	 * Sets the chargeableServiceTypeDescription.
	 * @param chargeableServiceTypeDescription The chargeableServiceTypeDescription to set
	 */
	public void setChargeableServiceTypeDescription(String chargeableServiceTypeDescription) {
		this.chargeableServiceTypeDescription =
			chargeableServiceTypeDescription;
	}

	/**
	 * Sets the creditCardCVVNumber.
	 * @param creditCardCVVNumber The creditCardCVVNumber to set
	 */
	public void setCreditCardCVVNumber(String creditCardCVVNumber) {
		this.creditCardCVVNumber = creditCardCVVNumber;
	}

	/**
	 * Sets the creditCardExpirationMonth.
	 * @param creditCardExpirationMonth The creditCardExpirationMonth to set
	 */
	public void setCreditCardExpirationMonth(String creditCardExpirationMonth) {
		this.creditCardExpirationMonth = creditCardExpirationMonth;
	}

	/**
	 * Sets the creditCardExpirationYear.
	 * @param creditCardExpirationYear The creditCardExpirationYear to set
	 */
	public void setCreditCardExpirationYear(String creditCardExpirationYear) {
		this.creditCardExpirationYear = creditCardExpirationYear;
	}

	/**
	 * Sets the creditCardNumber.
	 * @param creditCardNumber The creditCardNumber to set
	 */
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	/**
	 * Sets the creditCardTypeCode.
	 * @param creditCardTypeCode The creditCardTypeCode to set
	 */
	public void setCreditCardTypeCode(String creditCardTypeCode) {
		this.creditCardTypeCode = creditCardTypeCode;
	}

	/**
	 * Sets the creditCardTypeDescription.
	 * @param creditCardTypeDescription The creditCardTypeDescription to set
	 */
	public void setCreditCardTypeDescription(String creditCardTypeDescription) {
		this.creditCardTypeDescription = creditCardTypeDescription;
	}

	/**
	 * Sets the paymentFromPartyId.
	 * @param paymentFromPartyId The paymentFromPartyId to set
	 */
	public void setPaymentFromPartyId(String paymentFromPartyId) {
		this.paymentFromPartyId = paymentFromPartyId;
	}

	/**
	 * Sets the paymentAmount.
	 * @param paymentAmount The paymentAmount to set
	 */
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	/**
	 * Sets the paymentChargeAccountTypeCode.
	 * @param paymentChargeAccountTypeCode The paymentChargeAccountTypeCode to set
	 */
	public void setPaymentChargeAccountTypeCode(String paymentChargeAccountTypeCode) {
		this.paymentChargeAccountTypeCode = paymentChargeAccountTypeCode;
	}

	/**
	 * Sets the paymentChargeAccountTypeDescription.
	 * @param paymentChargeAccountTypeDescription The paymentChargeAccountTypeDescription to set
	 */
	public void setPaymentChargeAccountTypeDescription(String paymentChargeAccountTypeDescription) {
		this.paymentChargeAccountTypeDescription =
			paymentChargeAccountTypeDescription;
	}

	/**
	 * Sets the paymentConfirmationCode.
	 * @param paymentConfirmationCode The paymentConfirmationCode to set
	 */
	public void setPaymentConfirmationCode(String paymentConfirmationCode) {
		this.paymentConfirmationCode = paymentConfirmationCode;
	}

	/**
	 * Sets the paymentDate.
	 * @param paymentDate The paymentDate to set
	 */
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	/**
	 * Sets the paymentInformationId.
	 * @param paymentInformationId The paymentInformationId to set
	 */
	public void setPaymentInformationId(String paymentInformationId) {
		this.paymentInformationId = paymentInformationId;
	}

	/**
	 * Sets the paymentReturnCode.
	 * @param paymentReturnCode The paymentReturnCode to set
	 */
	public void setPaymentReturnCode(String paymentReturnCode) {
		this.paymentReturnCode = paymentReturnCode;
	}

	/**
	 * Sets the paymentReturnErrorCode.
	 * @param paymentReturnErrorCode The paymentReturnErrorCode to set
	 */
	public void setPaymentReturnErrorCode(String paymentReturnErrorCode) {
		this.paymentReturnErrorCode = paymentReturnErrorCode;
	}

	/**
	 * Sets the paymentReturnErrorDescription.
	 * @param paymentReturnErrorDescription The paymentReturnErrorDescription to set
	 */
	public void setPaymentReturnErrorDescription(String paymentReturnErrorDescription) {
		this.paymentReturnErrorDescription = paymentReturnErrorDescription;
	}


	/**
	 * Sets the waiverCode.
	 * @param waiverCode The waiverCode to set
	 */
	public void setWaiverCode(String waiverCode) {
		this.waiverCode = waiverCode;
	}

	/**
	 * Returns the epayBIN.
	 * @return String
	 */
	public String getEpayBIN() {
		return epayBIN;
	}

	/**
	 * Returns the epayMerchantId.
	 * @return String
	 */
	public String getEpayMerchantId() {
		return epayMerchantId;
	}

	/**
	 * Returns the epayReponseAuthCode.
	 * @return String
	 */
	public String getEpayReponseAuthCode() {
		return epayReponseAuthCode;
	}

	/**
	 * Returns the epayReponseAVSRespCode.
	 * @return String
	 */
	public String getEpayReponseAVSRespCode() {
		return epayReponseAVSRespCode;
	}

	/**
	 * Returns the epayReponseCVV2RespCode.
	 * @return String
	 */
	public String getEpayReponseCVV2RespCode() {
		return epayReponseCVV2RespCode;
	}

	/**
	 * Returns the epayReponseErrMsg.
	 * @return String
	 */
	public String getEpayReponseErrMsg() {
		return epayReponseErrMsg;
	}

	/**
	 * Returns the epayReponseErrorVal.
	 * @return String
	 */
	public String getEpayReponseErrorVal() {
		return epayReponseErrorVal;
	}

	/**
	 * Returns the epayReponseIsApproved.
	 * @return String
	 */
	public String getEpayReponseIsApproved() {
		return epayReponseIsApproved;
	}

	/**
	 * Returns the epayReponseIsDeclined.
	 * @return String
	 */
	public String getEpayReponseIsDeclined() {
		return epayReponseIsDeclined;
	}

	/**
	 * Returns the epayReponseIsGood.
	 * @return String
	 */
	public String getEpayReponseIsGood() {
		return epayReponseIsGood;
	}

	/**
	 * Returns the epayReponseResponseCode.
	 * @return String
	 */
	public String getEpayReponseResponseCode() {
		return epayReponseResponseCode;
	}

	/**
	 * Returns the epayReponseTxRefNum.
	 * @return String
	 */
	public String getEpayReponseTxRefNum() {
		return epayReponseTxRefNum;
	}

	/**
	 * Returns the paymentTransactionNumber.
	 * @return String
	 */
	public String getPaymentTransactionNumber() {
		return paymentTransactionNumber;
	}

	/**
	 * Sets the epayBIN.
	 * @param epayBIN The epayBIN to set
	 */
	public void setEpayBIN(String epayBIN) {
		this.epayBIN = epayBIN;
	}

	/**
	 * Sets the epayMerchantId.
	 * @param epayMerchantId The epayMerchantId to set
	 */
	public void setEpayMerchantId(String epayMerchantId) {
		this.epayMerchantId = epayMerchantId;
	}

	/**
	 * Sets the epayReponseAuthCode.
	 * @param epayReponseAuthCode The epayReponseAuthCode to set
	 */
	public void setEpayReponseAuthCode(String epayReponseAuthCode) {
		this.epayReponseAuthCode = epayReponseAuthCode;
	}

	/**
	 * Sets the epayReponseAVSRespCode.
	 * @param epayReponseAVSRespCode The epayReponseAVSRespCode to set
	 */
	public void setEpayReponseAVSRespCode(String epayReponseAVSRespCode) {
		this.epayReponseAVSRespCode = epayReponseAVSRespCode;
	}

	/**
	 * Sets the epayReponseCVV2RespCode.
	 * @param epayReponseCVV2RespCode The epayReponseCVV2RespCode to set
	 */
	public void setEpayReponseCVV2RespCode(String epayReponseCVV2RespCode) {
		this.epayReponseCVV2RespCode = epayReponseCVV2RespCode;
	}

	/**
	 * Sets the epayReponseErrMsg.
	 * @param epayReponseErrMsg The epayReponseErrMsg to set
	 */
	public void setEpayReponseErrMsg(String epayReponseErrMsg) {
		this.epayReponseErrMsg = epayReponseErrMsg;
	}

	/**
	 * Sets the epayReponseErrorVal.
	 * @param epayReponseErrorVal The epayReponseErrorVal to set
	 */
	public void setEpayReponseErrorVal(String epayReponseErrorVal) {
		this.epayReponseErrorVal = epayReponseErrorVal;
	}

	/**
	 * Sets the epayReponseIsApproved.
	 * @param epayReponseIsApproved The epayReponseIsApproved to set
	 */
	public void setEpayReponseIsApproved(String epayReponseIsApproved) {
		this.epayReponseIsApproved = epayReponseIsApproved;
	}

	/**
	 * Sets the epayReponseIsDeclined.
	 * @param epayReponseIsDeclined The epayReponseIsDeclined to set
	 */
	public void setEpayReponseIsDeclined(String epayReponseIsDeclined) {
		this.epayReponseIsDeclined = epayReponseIsDeclined;
	}

	/**
	 * Sets the epayReponseIsGood.
	 * @param epayReponseIsGood The epayReponseIsGood to set
	 */
	public void setEpayReponseIsGood(String epayReponseIsGood) {
		this.epayReponseIsGood = epayReponseIsGood;
	}

	/**
	 * Sets the epayReponseResponseCode.
	 * @param epayReponseResponseCode The epayReponseResponseCode to set
	 */
	public void setEpayReponseResponseCode(String epayReponseResponseCode) {
		this.epayReponseResponseCode = epayReponseResponseCode;
	}

	/**
	 * Sets the epayReponseTxRefNum.
	 * @param epayReponseTxRefNum The epayReponseTxRefNum to set
	 */
	public void setEpayReponseTxRefNum(String epayReponseTxRefNum) {
		this.epayReponseTxRefNum = epayReponseTxRefNum;
	}

	/**
	 * Sets the paymentTransactionNumber.
	 * @param paymentTransactionNumber The paymentTransactionNumber to set
	 */
	public void setPaymentTransactionNumber(String paymentTransactionNumber) {
		this.paymentTransactionNumber = paymentTransactionNumber;
	}

	/**
	 * Returns the epayReponseStatusVal.
	 * @return String
	 */
	public String getEpayReponseStatusVal() {
		return epayReponseStatusVal;
	}

	/**
	 * Sets the epayReponseStatusVal.
	 * @param epayReponseStatusVal The epayReponseStatusVal to set
	 */
	public void setEpayReponseStatusVal(String epayReponseStatusVal) {
		this.epayReponseStatusVal = epayReponseStatusVal;
	}

	/**
	 * Returns the billingAddressSameAs.
	 * @return String
	 */
	public String getBillingAddressSameAs() {
		return billingAddressSameAs;
	}

	/**
	 * Sets the billingAddressSameAs.
	 * @param billingAddressSameAs The billingAddressSameAs to set
	 */
	public void setBillingAddressSameAs(String billingAddressSameAs) {
		this.billingAddressSameAs = billingAddressSameAs;
	}

	/**
	 * Returns the ascapUserEmailAddress.
	 * @return String
	 */
	public String getAscapUserEmailAddress() {
		return ascapUserEmailAddress;
	}

	/**
	 * Returns the ascapUserFullName.
	 * @return String
	 */
	public String getAscapUserFullName() {
		return ascapUserFullName;
	}

	/**
	 * Returns the ascapUserId.
	 * @return String
	 */
	public String getAscapUserId() {
		return ascapUserId;
	}

	/**
	 * Returns the waiverSetupDate.
	 * @return String
	 */
	public String getWaiverSetupDate() {
		return waiverSetupDate;
	}

	/**
	 * Sets the ascapUserEmailAddress.
	 * @param ascapUserEmailAddress The ascapUserEmailAddress to set
	 */
	public void setAscapUserEmailAddress(String ascapUserEmailAddress) {
		this.ascapUserEmailAddress = ascapUserEmailAddress;
	}

	/**
	 * Sets the ascapUserFullName.
	 * @param ascapUserFullName The ascapUserFullName to set
	 */
	public void setAscapUserFullName(String ascapUserFullName) {
		this.ascapUserFullName = ascapUserFullName;
	}

	/**
	 * Sets the ascapUserId.
	 * @param ascapUserId The ascapUserId to set
	 */
	public void setAscapUserId(String ascapUserId) {
		this.ascapUserId = ascapUserId;
	}

	/**
	 * Sets the waiverSetupDate.
	 * @param waiverSetupDate The waiverSetupDate to set
	 */
	public void setWaiverSetupDate(String waiverSetupDate) {
		this.waiverSetupDate = waiverSetupDate;
	}

	/**
	 * Returns the epayOrderId.
	 * @return String
	 */
	public String getEpayOrderId() {
		return epayOrderId;
	}

	/**
	 * Sets the epayOrderId.
	 * @param epayOrderId The epayOrderId to set
	 */
	public void setEpayOrderId(String epayOrderId) {
		this.epayOrderId = epayOrderId;
	}

	/**
	 * Returns the paymentSubmitDate.
	 * @return String
	 */
	public String getPaymentSubmitDate() {
		return paymentSubmitDate;
	}

	/**
	 * Sets the paymentSubmitDate.
	 * @param paymentSubmitDate The paymentSubmitDate to set
	 */
	public void setPaymentSubmitDate(String paymentSubmitDate) {
		this.paymentSubmitDate = paymentSubmitDate;
	}

	/**
	 * @return Returns the pacDonateAmount.
	 */
	public String getPacDonateAmount() {
		return pacDonateAmount;
	}
	/**
	 * @param pacDonateAmount The pacDonateAmount to set.
	 */
	public void setPacDonateAmount(String pacDonateAmount) {
		this.pacDonateAmount = pacDonateAmount;
	}
	/**
	 * @return Returns the pacDonateAmountOther.
	 */
	public String getPacDonateAmountOther() {
		return pacDonateAmountOther;
	}
	/**
	 * @param pacDonateAmountOther The pacDonateAmountOther to set.
	 */
	public void setPacDonateAmountOther(String pacDonateAmountOther) {
		this.pacDonateAmountOther = pacDonateAmountOther;
	}
	/**
	 * @return Returns the pacDonateEmp.
	 */
	public String getPacDonateEmp() {
		return pacDonateEmp;
	}
	/**
	 * @param pacDonateEmp The pacDonateEmp to set.
	 */
	public void setPacDonateEmp(String pacDonateEmp) {
		this.pacDonateEmp = pacDonateEmp;
	}
/**
 * @return Returns the pacDonateFlag.
 */
public String getPacDonateFlag() {
	return pacDonateFlag;
}
/**
 * @param pacDonateFlag The pacDonateFlag to set.
 */
public void setPacDonateFlag(String pacDonateFlag) {
	this.pacDonateFlag = pacDonateFlag;
}
	/**
	 * @return Returns the pacDonateOcc.
	 */
	public String getPacDonateOcc() {
		return pacDonateOcc;
	}
	/**
	 * @param pacDonateOcc The pacDonateOcc to set.
	 */
	public void setPacDonateOcc(String pacDonateOcc) {
		this.pacDonateOcc = pacDonateOcc;
	}
	/**
	 * @return Returns the pacReadRulesFlag.
	 */
	public String getPacReadRulesFlag() {
		return pacReadRulesFlag;
	}
	/**
	 * @param pacReadRulesFlag The pacReadRulesFlag to set.
	 */
	public void setPacReadRulesFlag(String pacReadRulesFlag) {
		this.pacReadRulesFlag = pacReadRulesFlag;
	}
	/**
	 * @return Returns the totalPacAppFeeAmount.
	 */
	public String getTotalPacAppFeeAmount() {
		return totalPacAppFeeAmount;
	}
	/**
	 * @param totalPacAppFeeAmount The totalPacAppFeeAmount to set.
	 */
	public void setTotalPacAppFeeAmount(String totalPacAppFeeAmount) {
		this.totalPacAppFeeAmount = totalPacAppFeeAmount;
	}
	/**
	 * @return Returns the pacSource.
	 */
	public String getPacSource() {
		return pacSource;
	}
	/**
	 * @param pacSource The pacSource to set.
	 */
	public void setPacSource(String pacSource) {
		this.pacSource = pacSource;
	}

    /**
     * @return Returns the paypalResponseAck.
     */
    public String getPaypalResponseAck() {
        return paypalResponseAck;
    }

    /**
     * @param paypalResponseAck
     *            The paypalResponseAck to set.
     */
    public void setPaypalResponseAck(String paypalResponseAck) {
        this.paypalResponseAck = paypalResponseAck;
    }

    /**
     * @return Returns the paypalResponseBuild.
     */
    public String getPaypalResponseBuild() {
        return paypalResponseBuild;
    }

    /**
     * @param paypalResponseBuild
     *            The paypalResponseBuild to set.
     */
    public void setPaypalResponseBuild(String paypalResponseBuild) {
        this.paypalResponseBuild = paypalResponseBuild;
    }

    /**
     * @return Returns the paypalResponseCorrID.
     */
    public String getPaypalResponseCorrID() {
        return paypalResponseCorrID;
    }

    /**
     * @param paypalResponseCorrID
     *            The paypalResponseCorrID to set.
     */
    public void setPaypalResponseCorrID(String paypalResponseCorrID) {
        this.paypalResponseCorrID = paypalResponseCorrID;
    }

    /**
     * @return Returns the paypalResponsePayStatus.
     */
    public String getPaypalResponsePayStatus() {
        return paypalResponsePayStatus;
    }

    /**
     * @param paypalResponsePayStatus
     *            The paypalResponsePayStatus to set.
     */
    public void setPaypalResponsePayStatus(String paypalResponsePayStatus) {
        this.paypalResponsePayStatus = paypalResponsePayStatus;
    }

    /**
     * @return Returns the paypalResponsePendReason.
     */
    public String getPaypalResponsePendReason() {
        return paypalResponsePendReason;
    }

    /**
     * @param paypalResponsePendReason
     *            The paypalResponsePendReason to set.
     */
    public void setPaypalResponsePendReason(String paypalResponsePendReason) {
        this.paypalResponsePendReason = paypalResponsePendReason;
    }

    /**
     * @return Returns the paypalResponseTimeStamp.
     */
    public String getPaypalResponseTimeStamp() {
        return paypalResponseTimeStamp;
    }

    /**
     * @param paypalResponseTimeStamp
     *            The paypalResponseTimeStamp to set.
     */
    public void setPaypalResponseTimeStamp(String paypalResponseTimeStamp) {
        this.paypalResponseTimeStamp = paypalResponseTimeStamp;
    }

    /**
     * @return Returns the paypalResponseVersion.
     */
    public String getPaypalResponseVersion() {
        return paypalResponseVersion;
    }

    /**
     * @param paypalResponseVersion
     *            The paypalResponseVersion to set.
     */
    public void setPaypalResponseVersion(String paypalResponseVersion) {
        this.paypalResponseVersion = paypalResponseVersion;
    }
    /**
     * @return Returns the payerIPAddress.
     */
    public String getPayerIPAddress() {
        return payerIPAddress;
    }
    /**
     * @param payerIPAddress The payerIPAddress to set.
     */
    public void setPayerIPAddress(String payerIPAddress) {
        this.payerIPAddress = payerIPAddress;
    }
    /**
     * @return Returns the payerSessionId.
     */
    public String getPayerSessionId() {
        return payerSessionId;
    }
    /**
     * @param payerSessionId The payerSessionId to set.
     */
    public void setPayerSessionId(String payerSessionId) {
        this.payerSessionId = payerSessionId;
    }
    
    /**
     * @return Returns the paypalRequestCancelUrl.
     */
    public String getPaypalRequestCancelUrl() {
        return paypalRequestCancelUrl;
    }
    /**
     * @param paypalRequestCancelUrl The paypalRequestCancelUrl to set.
     */
    public void setPaypalRequestCancelUrl(String paypalRequestCancelUrl) {
        this.paypalRequestCancelUrl = paypalRequestCancelUrl;
    }
    /**
     * @return Returns the paypalRequestReturnUrl.
     */
    public String getPaypalRequestReturnUrl() {
        return paypalRequestReturnUrl;
    }
    /**
     * @param paypalRequestReturnUrl The paypalRequestReturnUrl to set.
     */
    public void setPaypalRequestReturnUrl(String paypalRequestReturnUrl) {
        this.paypalRequestReturnUrl = paypalRequestReturnUrl;
    }
    
    /**
     * @return Returns the paypalResponseECToken.
     */
    public String getPaypalResponseECToken() {
        return paypalResponseECToken;
    }
    /**
     * @param paypalResponseECToken The paypalResponseECToken to set.
     */
    public void setPaypalResponseECToken(String paypalResponseECToken) {
        this.paypalResponseECToken = paypalResponseECToken;
    }
    
    /**
     * @return Returns the donationIsProcessed.
     */
    public String getDonationIsProcessed() {
        return donationIsProcessed;
    }
    /**
     * @param donationIsProcessed The donationIsProcessed to set.
     */
    public void setDonationIsProcessed(String donationIsProcessed) {
        this.donationIsProcessed = donationIsProcessed;
    }
    /**
     * @return Returns the donationVobFlag.
     */
    public String getDonationVobFlag() {
        return donationVobFlag;
    }
    /**
     * @param donationVobFlag The donationVobFlag to set.
     */
    public void setDonationVobFlag(String donationVobFlag) {
        this.donationVobFlag = donationVobFlag;
    }
    /**
     * @return Returns the pacDonateAmtToProcess.
     */
    public String getPacDonateAmtToProcess() {
        return pacDonateAmtToProcess;
    }
    /**
     * @param pacDonateAmtToProcess The pacDonateAmtToProcess to set.
     */
    public void setPacDonateAmtToProcess(String pacDonateAmtToProcess) {
        this.pacDonateAmtToProcess = pacDonateAmtToProcess;
    }
    /**
     * @return Returns the payerEmailAddress.
     */
    public String getPayerEmailAddress() {
        return payerEmailAddress;
    }
    /**
     * @param payerEmailAddress The payerEmailAddress to set.
     */
    public void setPayerEmailAddress(String payerEmailAddress) {
        this.payerEmailAddress = payerEmailAddress;
    }
    /**
     * @return Returns the paymentGatewayType.
     */
    public String getPaymentGatewayType() {
        return paymentGatewayType;
    }
    /**
     * @param paymentGatewayType The paymentGatewayType to set.
     */
    public void setPaymentGatewayType(String paymentGatewayType) {
        this.paymentGatewayType = paymentGatewayType;
    }
    /**
     * @return Returns the accountHolderFName.
     */
    public String getAccountHolderFName() {
        return accountHolderFName;
    }
    /**
     * @param accountHolderFName The accountHolderFName to set.
     */
    public void setAccountHolderFName(String accountHolderFName) {
        this.accountHolderFName = accountHolderFName;
    }
    /**
     * @return Returns the accountHolderLName.
     */
    public String getAccountHolderLName() {
        return accountHolderLName;
    }
    /**
     * @param accountHolderLName The accountHolderLName to set.
     */
    public void setAccountHolderLName(String accountHolderLName) {
        this.accountHolderLName = accountHolderLName;
    }
    /**
     * @return Returns the accountHolderMInitial.
     */
    public String getAccountHolderMInitial() {
        return accountHolderMInitial;
    }
    /**
     * @param accountHolderMInitial The accountHolderMInitial to set.
     */
    public void setAccountHolderMInitial(String accountHolderMInitial) {
        this.accountHolderMInitial = accountHolderMInitial;
    }
    /**
     * @return Returns the paypalResponseAmount.
     */
    public String getPaypalResponseAmount() {
        return paypalResponseAmount;
    }
    /**
     * @param paypalResponseAmount The paypalResponseAmount to set.
     */
    public void setPaypalResponseAmount(String paypalResponseAmount) {
        this.paypalResponseAmount = paypalResponseAmount;
    }
    /**
     * @return Returns the paymentFormAlreadySubmitted.
     */
    public String getPaymentFormAlreadySubmitted() {
        return paymentFormAlreadySubmitted;
    }
    /**
     * @param paymentFormAlreadySubmitted The paymentFormAlreadySubmitted to set.
     */
    public void setPaymentFormAlreadySubmitted(String paymentFormAlreadySubmitted) {
        this.paymentFormAlreadySubmitted = paymentFormAlreadySubmitted;
    }
    /**
     * @return Returns the corresAddressLine1.
     */
    public String getCorresAddressLine1() {
        return corresAddressLine1;
    }
    /**
     * @param corresAddressLine1 The corresAddressLine1 to set.
     */
    public void setCorresAddressLine1(String corresAddressLine1) {
        this.corresAddressLine1 = corresAddressLine1;
    }
    /**
     * @return Returns the corresAddressLine2.
     */
    public String getCorresAddressLine2() {
        return corresAddressLine2;
    }
    /**
     * @param corresAddressLine2 The corresAddressLine2 to set.
     */
    public void setCorresAddressLine2(String corresAddressLine2) {
        this.corresAddressLine2 = corresAddressLine2;
    }
    /**
     * @return Returns the corresCity.
     */
    public String getCorresCity() {
        return corresCity;
    }
    /**
     * @param corresCity The corresCity to set.
     */
    public void setCorresCity(String corresCity) {
        this.corresCity = corresCity;
    }
    /**
     * @return Returns the corresCountryCode.
     */
    public String getCorresCountryCode() {
        return corresCountryCode;
    }
    /**
     * @param corresCountryCode The corresCountryCode to set.
     */
    public void setCorresCountryCode(String corresCountryCode) {
        this.corresCountryCode = corresCountryCode;
    }
    /**
     * @return Returns the corresPostalCode.
     */
    public String getCorresPostalCode() {
        return corresPostalCode;
    }
    /**
     * @param corresPostalCode The corresPostalCode to set.
     */
    public void setCorresPostalCode(String corresPostalCode) {
        this.corresPostalCode = corresPostalCode;
    }
    /**
     * @return Returns the corresSameAsBillingFlag.
     */
    public String getCorresSameAsBillingFlag() {
        return corresSameAsBillingFlag;
    }
    /**
     * @param corresSameAsBillingFlag The corresSameAsBillingFlag to set.
     */
    public void setCorresSameAsBillingFlag(String corresSameAsBillingFlag) {
        this.corresSameAsBillingFlag = corresSameAsBillingFlag;
    }
    /**
     * @return Returns the corresStateCode.
     */
    public String getCorresStateCode() {
        return corresStateCode;
    }
    /**
     * @param corresStateCode The corresStateCode to set.
     */
    public void setCorresStateCode(String corresStateCode) {
        this.corresStateCode = corresStateCode;
    }
	/**
	 * @return Returns the isDonationVOB.
	 */
	public String getIsDonationVOB() {
		return isDonationVOB;
	}
	/**
	 * @param isDonationVOB The isDonationVOB to set.
	 */
	public void setIsDonationVOB(String isDonationVOB) {
		this.isDonationVOB = isDonationVOB;
	}
    /**
     * @return Returns the donorFName.
     */
    public String getDonorFName() {
        return donorFName;
    }
    /**
     * @param donorFName The donorFName to set.
     */
    public void setDonorFName(String donorFName) {
        this.donorFName = donorFName;
    }
    /**
     * @return Returns the donorLName.
     */
    public String getDonorLName() {
        return donorLName;
    }
    /**
     * @param donorLName The donorLName to set.
     */
    public void setDonorLName(String donorLName) {
        this.donorLName = donorLName;
    }
    /**
     * @return Returns the donorMInitial.
     */
    public String getDonorMInitial() {
        return donorMInitial;
    }
    /**
     * @param donorMInitial The donorMInitial to set.
     */
    public void setDonorMInitial(String donorMInitial) {
        this.donorMInitial = donorMInitial;
    }
}
