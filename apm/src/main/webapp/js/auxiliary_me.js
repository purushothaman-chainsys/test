
// Based on the resignationType radio button, other radio button groups and
// text fields will be disabled/enabled
function checkResignationType(object) {

	if(object.eventCode[0].checked == true) {
		for(i=0; i<object.rightsPreferencePendingResignation.length; i++) {
			object.rightsPreferencePendingResignation[i].disabled = false;
			
			if(i==0)
			{
				object.rightsPreferencePendingResignation[i].checked = true;
			}
			else
			{
				object.rightsPreferencePendingResignation[i].checked = false;
			}
		}
		for(i=0; i<object.rightsPreferenceResign.length; i++) {
			object.rightsPreferenceResign[i].disabled = true;
			object.rightsPreferenceResign[i].checked = false;
				
		}
		for(i=0; i<object.rightsPreferenceTerminate.length; i++) {
			object.rightsPreferenceTerminate[i].disabled = true;
			object.rightsPreferenceTerminate[i].checked = false;			
		}

		object.publishAgreementEndToIPIResign.checked = false;
		object.publishAgreementEndToIPIResign.disabled = true;

		//object.effectiveStartResignDate.value = "";
		object.effectiveStartResignDate.disabled = true;

		object.publishAgreementEndToIPITerminate.checked = false;
		object.publishAgreementEndToIPITerminate.disabled = true;

		//object.effectiveStartTerminateDate.value = "";
		object.effectiveStartTerminateDate.disabled = true;

		//Defect ID: 3507 Added code to disable Effective Resignation Quarter 
		//and Year fields - Ganesh.B - 02/18/2005
		for(i=0; i<object.effectiveResignationQtr.length; i++) {
			object.effectiveResignationQtr[i].disabled = false;
			if(i==0) {
				object.effectiveResignationQtr[i].checked = true;
				
			} else {
				object.effectiveResignationQtr[i].checked = false;
			}
		}
		object.effectiveResignationYr.disabled = false;

		object.terminationTypeCode.disabled = true;

	} else if(object.eventCode[1].checked == true) {
		for(i=0; i<object.rightsPreferencePendingResignation.length; i++) {
			object.rightsPreferencePendingResignation[i].disabled = true;
			object.rightsPreferencePendingResignation[i].checked = false;
		}
		for(i=0; i<object.rightsPreferenceResign.length; i++) {
			object.rightsPreferenceResign[i].disabled = false;
			
			if(i==0)
			{
				object.rightsPreferenceResign[i].checked = true;
			}
			else
			{
				object.rightsPreferenceResign[i].checked = false;
			}
		}
		for(i=0; i<object.rightsPreferenceTerminate.length; i++) {
			object.rightsPreferenceTerminate[i].disabled = true;
			object.rightsPreferenceTerminate[i].checked = false;
		}

		object.publishAgreementEndToIPIResign.checked = true;
		object.publishAgreementEndToIPIResign.disabled = false;

		//object.effectiveStartResignDate.value = "";
		object.effectiveStartResignDate.disabled = false;

		object.publishAgreementEndToIPITerminate.checked = false;
		object.publishAgreementEndToIPITerminate.disabled = true;

		//object.effectiveStartTerminateDate.value = "";
		object.effectiveStartTerminateDate.disabled = true;
		
		//Defect ID: 3507 Added code to disable Effective Resignation Quarter 
		//and Year fields - Ganesh.B - 02/18/2005
		for(i=0; i<object.effectiveResignationQtr.length; i++) {
			object.effectiveResignationQtr[i].disabled = true;
			object.effectiveResignationQtr[i].checked = false;
				
		}
		object.effectiveResignationYr.disabled = true;

		object.terminationTypeCode.disabled = true;

	} else if(object.eventCode[2].checked == true) {
		for(i=0; i<object.rightsPreferencePendingResignation.length; i++) {
			object.rightsPreferencePendingResignation[i].disabled = true;
			object.rightsPreferencePendingResignation[i].checked = false;
		}
		for(i=0; i<object.rightsPreferenceResign.length; i++) {
			object.rightsPreferenceResign[i].disabled = true;
			object.rightsPreferenceResign[i].checked = false;
		}
		for(i=0; i<object.rightsPreferenceTerminate.length; i++) {
			object.rightsPreferenceTerminate[i].disabled = false;
			
			if(i==0)
			{
				object.rightsPreferenceTerminate[i].checked = true;
			}
			else
			{
				object.rightsPreferenceTerminate[i].checked = false;
			}
		}

		object.publishAgreementEndToIPIResign.checked = false;
		object.publishAgreementEndToIPIResign.disabled = true;

		//object.effectiveStartResignDate.value = "";
		object.effectiveStartResignDate.disabled = true;

		object.publishAgreementEndToIPITerminate.checked = true;
		object.publishAgreementEndToIPITerminate.disabled = false;

		//object.effectiveStartTerminateDate.value = "";
		object.effectiveStartTerminateDate.disabled = false;

		//Defect ID: 3507 Added code to disable Effective Resignation Quarter 
		//and Year fields - Ganesh.B - 02/18/2005
		for(i=0; i<object.effectiveResignationQtr.length; i++) {
			object.effectiveResignationQtr[i].disabled = true;
			object.effectiveResignationQtr[i].checked = false;
				
		}
		object.effectiveResignationYr.disabled = true;

		object.terminationTypeCode.disabled = false;
	}
}

// Disable form fields that are not necessary during page loading
function disableFormFields(object) {
		for(i=0; i<object.rightsPreferencePendingResignation.length; i++) {
			object.rightsPreferencePendingResignation[i].disabled = true;
			object.rightsPreferencePendingResignation[i].checked = false;
		}
		for(i=0; i<object.rightsPreferenceResign.length; i++) {
			object.rightsPreferenceResign[i].disabled = true;
			object.rightsPreferenceResign[i].checked = false;	
		}
		for(i=0; i<object.rightsPreferenceTerminate.length; i++) {
			object.rightsPreferenceTerminate[i].disabled = true;
			object.rightsPreferenceTerminate[i].checked = false;
		}

		object.publishAgreementEndToIPIResign.checked = false;
		object.publishAgreementEndToIPIResign.disabled = true;

		//object.effectiveStartResignDate.value = "";
		//object.effectiveStartResignDate.disabled = true;

		object.publishAgreementEndToIPITerminate.checked = false;
		object.publishAgreementEndToIPITerminate.disabled = true;

		//object.effectiveStartTerminateDate.value = "";
		//object.effectiveStartTerminateDate.disabled = true;

		object.terminationTypeCode.disabled = true;
}

function displayMandatesMessage(val1)
{
	alert("Please donot forget to create mandates");
}
// JSP: mePrtySccsn.jsp
// Purpose: To set the flag: "Deceased" or "Remove Flag"
//val1 = hidden variable for which button is pressed
// val2 = <bean:write name="deceasedNotificationForm" property="partyID" />
// val3 = <bean:write name="deceasedNotificationForm" property="partyStatus" />
// val4 = <bean:write name="deceasedNotificationForm" property="partyType" />
function  InitiateSetFlag(val1,formObject,actionName,val2,val3,val4)
{
	formObject.statusFlag.value=val1;
	formObject.partyID.value=val2;
	formObject.partyStatus.value=val3;
	formObject.partyType.value=val4;
	formObject.action=actionName;
	formObject.submit();
}

