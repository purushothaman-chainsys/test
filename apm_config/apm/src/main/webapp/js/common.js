// Generic function
// Given a source text field, a destination text field and the value of the
// source text field, this function will copy the value to the destination
// field when the focus is lost from the source field
function populateDependent(sourceField, destinationField, textValue) {
	destinationField.value = textValue;
}

// Generic function
// Given a div Id and a flag, the defined div will be shown or hidden
// Works along with a proper css definition
function toggleBox(szDivID, iState) // 1 visible, 0 hidden
{
    if(document.layers)	   //NN4+
    {
       document.layers[szDivID].visibility = iState ? "show" : "hide";
    }
    else if(document.getElementById)	  //gecko(NN6) + IE 5+
    {
        var obj = document.getElementById(szDivID);
        obj.style.visibility = iState ? "visible" : "hidden";
    }
    else if(document.all)	// IE 4
    {
        document.all[szDivID].style.visibility = iState ? "visible" : "hidden";
    }
}

// Generic function
// Given a form object and a text field, this function will format the date
// to mm/dd/yyyy
// This function needs two helper methods isDatemsdsyy and isDatemmddyyyy

function formatDateTommsddsyyyy(formObject, textBoxObject) {

	if(formObject.textBoxObject.value.length == 6) {
		// if type m/d/yy
		newDate = isDatemsdsyy(formObject.textBoxObject.value);

		// if type mmddyy
		//isDatemmddyy(formObject.textBoxObject.value);

		formObject.textBoxObject.value = newDate;

	} else if (formObject.textBoxObject.value.length == 8) {
		// if type mm/dd/yy
		//isDatemmsddsyy(formObject.textBoxObject.value);

		// if type mmddyyyy
		newDate = isDatemmddyyyy(formObject.textBoxObject.value);
		formObject.textBoxObject.value = newDate;
	}
}

var dtCh= "/";
var minYear=1900;
var maxYear=2100;
// Will check if the given date is of format m/d/yy and if so format it to mm/dd/yyyy
function isDatemsdsyy(dtStr){

	var pos1=dtStr.indexOf(dtCh)
	var pos2=dtStr.indexOf(dtCh,pos1+1)

	if (pos1==1 || pos2==3){
		var strMonth=dtStr.substring(0,1);
		var strDay=dtStr.substring(2,3);
		var strYear=dtStr.substring(4,6);
		if(strDay.length == 1) strDay = "0"+strDay;
		if(strMonth.length == 1) strMonth = "0"+strMonth;
		if(strYear.length == 2) strYear = "20"+strYear;
		newDate = strMonth + "/" + strDay + "/" + strYear;
	}

	return newDate;

}

// Will check if the given date is of format mmmddyyyy and if so format it to mm/dd/yyyy
function isDatemmddyyyy(dtStr){

	var strMonth=dtStr.substring(0,2);
	var strDay=dtStr.substring(2,4);
	var strYear=dtStr.substring(4,8);
	newDate = strMonth + "/" + strDay + "/" + strYear;
	return newDate;
}

// Submits form
//Shailaja 09/25/2003
 function submitForm(formObject,actionName)
 {
  formObject.action=actionName;
  formObject.submit();
  
 }
 
 function submitForm2(formObject,actionName,value)
 {
  formObject.action=actionName+value;
  formObject.submit();
  
 }

function getIndexOfCheckBoxByValue(checkBoxes,valueToFind){
	var chkBxsLen = 0;
	if(checkBoxes == undefined){
		return -1;
	}
	if(checkBoxes.length == undefined){
		return -2;
	}else{
		chkBxsLen = checkBoxes.length;
		for(var i = 0; i<chkBxsLen; i++){
			if(checkBoxes[i].value == valueToFind){
				return i;
			} 
		}	
	}
}
 
// Generic function
// author Manoj_Puli
// Given a link name, the function highlites the link 
// All <a href> Links are to be enclosed in <DIV ID='navigation'></DIV>

function highlightLink(linkName) {
  
}




function confirmDeleteWithSelectionCheck(formObject, fieldName, message1, message2) {
	count = $('[name='+fieldName+']:checked').length;
	if (count == 0) {
		alert('Atleast one '+message1+' needs be checked in order to delete.');
		return 0;
	}else{
		return confirmDelete(formObject, fieldName, message1, message2);
	}
}


function confirmDelete(formObject, fieldName, message1, message2) {
	if(message2 == undefined){
		return	confirm('Are you sure you want to delete the selected ' + message1 + '?');
	}else{
		return	confirm('Are you sure you want to delete the selected ' + message1 + '?' + '\n' + message2);
	}	
}




/* ================================================================
				**** TRIM Function *****
===================================================================*/
/*
==================================================================
LTrim(string) : Returns a copy of a string without leading spaces.
==================================================================
*/
function LTrim(str)
/*
   PURPOSE: Remove leading blanks from our string.
   IN: str - the string we want to LTrim
*/
{
   var whitespace = new String(" \t\n\r");

   var s = new String(str);

   if (whitespace.indexOf(s.charAt(0)) != -1) {
      // We have a string with leading blank(s)...

      var j=0, i = s.length;

      // Iterate from the far left of string until we
      // don't have any more whitespace...
      while (j < i && whitespace.indexOf(s.charAt(j)) != -1)
         j++;

      // Get the substring from the first non-whitespace
      // character to the end of the string...
      s = s.substring(j, i);
   }
   return s;
}

/*
==================================================================
RTrim(string) : Returns a copy of a string without trailing spaces.
==================================================================
*/
function RTrim(str)
/*
   PURPOSE: Remove trailing blanks from our string.
   IN: str - the string we want to RTrim

*/
{
   // We don't want to trip JUST spaces, but also tabs,
   // line feeds, etc.  Add anything else you want to
   // "trim" here in Whitespace
   var whitespace = new String(" \t\n\r");

   var s = new String(str);

   if (whitespace.indexOf(s.charAt(s.length-1)) != -1) {
      // We have a string with trailing blank(s)...

      var i = s.length - 1;       // Get length of string

      // Iterate from the far right of string until we
      // don't have any more whitespace...
      while (i >= 0 && whitespace.indexOf(s.charAt(i)) != -1)
         i--;


      // Get the substring from the front of the string to
      // where the last non-whitespace character is...
      s = s.substring(0, i+1);
   }

   return s;
}

/*
=============================================================
Trim(string) : Returns a copy of a string without leading or trailing spaces
=============================================================
*/
function Trim(str)
/*
   PURPOSE: Remove trailing and leading blanks from our string.
   IN: str - the string we want to Trim

   RETVAL: A Trimmed string!
*/
{
   return RTrim(LTrim(str));
}


/*
=============================================================
trimAll(): removes the leading or trailing spaces for all the fields of the form
=============================================================
*/

function pageTrimAll(formObj){
//var formObj = document.workSearchForm

	for(var i= 0; i< formObj.elements.length; i++)
	{
		//alert("before trim" + formObj.elements[i].value +"end")
		var strTemp = formObj.elements[i].value;
		formObj.elements[i].value = Trim(strTemp);
		//alert("after trim" + formObj.elements[i].value+"end")
	}
}

/*
*  Generic function
*  Common function to check existance of a field in the form
*/
 function getFormElements(oForm, sName) {
	if (oForm && oForm.elements)	{
		var o = oForm.elements[sName];
		if (o)
		return o;
	}
	return null;
}

/*
* For Distribution Module
* This function is for checking percentage change in weight fields
* for distribution module 
*/
function checkWeightChange(myForm, weightField){
	var x = getFormElements(myForm, weightField); 
	var changeMoreThan10pct = false;
	if(x) {
		if(x.length) {
 			for (i=0;i<x.length;i++) {
 				var changePercentage = ((x[i].value - myForm.oldWeight[i].value)/myForm.oldWeight[i].value) * 100;
 				if(changePercentage >= 9.999 || changePercentage <= -9.999) {			
 					changeMoreThan10pct = true;
 				}
 			}			
		}
		else {
			var changePercentage = ((x.value - myForm.oldWeight.value)/myForm.oldWeight.value) * 100; 
			if(changePercentage >= 9.999 || changePercentage <= -9.999) {
				changeMoreThan10pct = true;
			}
		}
	}
	if(changeMoreThan10pct) {
		myForm.warningRequired.value = "true";
		myForm.userConfirmed.value = "false";		
	}
	else {
		myForm.warningRequired.value = "false";
		myForm.userConfirmed.value = "true";		
	}
}


function refreshLeftnav(moduleId, contextRoot) {
	leftNavUrl = contextRoot;
	switch (moduleId) {
		case 'MEMBERSHIP_LEFTNAV':
			leftNavUrl += '/membership/me_left.jsp';
			break;
		case 'ROYALTY_LEFTNAV':
			leftNavUrl += '/royalty/ry_left.jsp';
			break;
		case 'DISTRIBUTION_LEFTNAV':
			leftNavUrl += '/distribution/ds_left.jsp';
			break;
		case 'USAGE_LEFTNAV':
			leftNavUrl += '/usage/us_left.jsp';
			break;
		case 'WORKS_LEFTNAV':
			leftNavUrl += '/works/wk_left.jsp';
			break;
		case 'AGREEMENTS_LEFTNAV':
			leftNavUrl += '/agreements/ag_left.jsp';
			break;
		case 'MANDATES_LEFTNAV':
			leftNavUrl += '/mandates/mn_left.jsp';
			break;
		case 'INQUIRY_LEFTNAV':
			leftNavUrl += '/inquiry/in_left.jsp';
			break;
		case 'MODELING_LEFTNAV':
			leftNavUrl += '/distribution/ds_left_mod.jsp';
			break;
		case 'ADJUSTMENTS_LEFTNAV':
			leftNavUrl += '/adjustments/aj_left.jsp';
			break;
		case 'LRS_LEFTNAV':
			leftNavUrl += '/lrs/lrs_left.jsp';
			break;
		case 'ADMIN_LEFTNAV':
			leftNavUrl += '/admin/ad_left.jsp';
			break;
		case 'PREFERENCES_LEFTNAV':
			leftNavUrl += '/preferences/pf_left.jsp';
			break;
		case 'DOCUMENTS_LEFTNAV':
			leftNavUrl += '/documents/documents_left.jsp';
			break;
		default:
			leftNavUrl += '/leftnav.jsp';
			break;
			
	}

	parent.leftNavFrame.location.href = leftNavUrl;
		
}

function reHighlightLinks() {
	if(parent.topNavFrame.document.forms[0].contentSource.value == 'TOP_NAV') {
		highlightLink('Search');
	}
	else {
		if(parent.topNavFrame.document.forms[0].contentLink.value != '') {
			highlightLink(parent.topNavFrame.document.forms[0].contentLink.value);
		}
	}
}

/*
function pleaseWait() {
	
	var winW = 800, winH = 600;

	if (parseInt(navigator.appVersion)>3) {
	 if (navigator.appName=="Netscape") {
	  winW = window.innerWidth;
	  winH = window.innerHeight;
	 }
	 if (navigator.appName.indexOf("Microsoft")!=-1) {
	  winW = document.body.offsetWidth;
	  winH = document.body.offsetHeight;
	 }
	}

	if(typeof(document.all) != "undefined") { 
		
		if(document.body.scrollTop > 0) {
		   document.all.popmsg.style.pixelTop = (document.body.scrollTop + 200);
		}			
		document.all.popmsg.style.pixelLeft = ((winW/2)-150);			
		document.all.popmsg.style.visibility = "visible";
		
	} else {
		
		popMsgObj = document.getElementById("popmsg");
		popMsgObj.style.top = (document.body.scrollTop + 250) + "px";
		popMsgObj.style.left = ((winW/2)-150) + "px";			
		popMsgObj.style.visibility = "visible";
	}
	
	hideSelectOrAppletElements('SELECT', document.getElementById('popmsg'));
	hideSelectOrAppletElements('APPLET', document.getElementById('popmsg'));
}

function closeScreen()
{
popMsgObj = document.getElementById("popmsg");
popMsgObj.style.display="none";
window.location=window.location;
}


function hideSelectOrAppletElements( elmID, overDiv ) {
	if(document.all) {
		for(i = 0; i < document.all.tags( elmID ).length; i++) {
			obj = document.all.tags( elmID )[i];
			if(!obj || !obj.offsetParent) continue;

			// Find the element's offsetTop and offsetLeft relative to the BODY tag.
			objLeft   = obj.offsetLeft;
			objTop    = obj.offsetTop;
			objParent = obj.offsetParent;

			while(objParent.tagName.toUpperCase() != 'BODY') {
				objLeft  += objParent.offsetLeft;
				objTop   += objParent.offsetTop;
				objParent = objParent.offsetParent;
			}

			objHeight = obj.offsetHeight;
			objWidth  = obj.offsetWidth;

			if((overDiv.offsetLeft + overDiv.offsetWidth) <= objLeft);
			else if((overDiv.offsetTop + overDiv.offsetHeight) <= objTop);
			
			else if(overDiv.offsetTop >= (objTop + objHeight + obj.height));
			
			else if(overDiv.offsetLeft >= (objLeft + objWidth));
			else {
				obj.style.visibility = 'hidden';
			}
		}
	}
}
*/

