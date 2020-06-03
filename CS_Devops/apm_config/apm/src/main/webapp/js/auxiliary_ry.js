// Based on the Instruction Type this function will manipulate the Accrual option
//Shailaja 10/09/2003

function checkInstructionType(object) {
	instructionType = object.rpiType.options[object.rpiType.selectedIndex].value;
	if((instructionType == "LGLFD")||(instructionType == "ASCAP")) {
		object.accrual[1].checked = true;
		object.accrual[0].disabled = true;
		object.accrual[1].disabled = false;
	} 
	else if ((instructionType == "TXLVY")||(instructionType == "CHSUP")) {
		object.accrual[0].checked = true;
		object.accrual[1].disabled = true;
		object.accrual[0].disabled = false;
//		if(instructionType == "TXLVY") {
//			object.maxLifeAmt.readOnly = true;
//		}
	} 
	else if ((instructionType == "BWHTX")||(instructionType == "NRATX")) {
		object.accrual[0].checked = true;
		object.accrual[1].disabled = true;
		object.accrual[0].disabled = false;	
		if(	instructionType == "BWHTX" ) {
		  	object.percentDistAmt.value = '28';
		}
		
//		setPaymentMethod(instructionType);
	} 
	else {
		object.accrual[0].disabled = false;
		object.accrual[1].disabled = false;
		object.accrual[0].checked = false;
		object.accrual[1].checked = false;
	} //end if
	
	
	if ((instructionType == "TXLVY")||
	(instructionType == "NRATX")||
	(instructionType == "BWHTX") ||(instructionType == "CHSUP")){
		object.minThreshAmt.value = "";
		//object.minThreshAmt.disabled = true;
		object.minThreshAmt.readOnly = true;
	} else {
		object.minThreshAmt.disabled = false;
		object.minThreshAmt.readOnly = false;
	} //end if
	

	if(instructionType == "AWPMT") {
		object.seqId.value = "50";
	  	object.percentDistAmt.value = '';
		
		//document.rpiForm.seqId.readonly = true;
	} else if (instructionType == "AWRMT") {
		document.rpiForm.seqId.value = "60";
	  	object.percentDistAmt.value = '';
		//document.rpiForm.seqId.readonly = true;
		//document.rpiForm.seqId.disabled = true;
	} else if (instructionType == "HLDPT") {
		document.rpiForm.seqId.value = "80";
	  	object.percentDistAmt.value = '';
		//document.rpiForm.seqId.readonly = true;
		//document.rpiForm.seqId.disabled = true;
	} else if (instructionType == "NRATX") {
		document.rpiForm.seqId.value = "100";
		object.distType[0].checked = true;	
	  	object.percentDistAmt.value = object.entitledEatnerTaxPercentage.value;
		//document.rpiForm.seqId.readonly = true;
		//document.rpiForm.seqId.disabled = true;
	} else if (instructionType == "BWHTX") {
		document.rpiForm.seqId.value = "101";
	  	object.percentDistAmt.value = '28';
		//document.rpiForm.seqId.readonly = true;
		//document.rpiForm.seqId.disabled = true;
	} else if (instructionType == "PRADJ") {
		document.rpiForm.seqId.value = "150";
	  	object.percentDistAmt.value = '';
		//document.rpiForm.seqId.readonly = true;
		//document.rpiForm.seqId.disabled = true;
	} 
	else if (instructionType == "TPPMT") {
		document.rpiForm.seqId.value = "300";
	  	object.percentDistAmt.value = '';
		//document.rpiForm.seqId.readonly = false;
	}
	else if (instructionType == "TPPMB") {
		document.rpiForm.seqId.value = "400";
	  	object.percentDistAmt.value = '';
		//document.rpiForm.seqId.readonly = false;
	} 
	else if (instructionType == "ADRMT") {
		document.rpiForm.seqId.value = "340";
	  	object.percentDistAmt.value = '';
		//document.rpiForm.seqId.disabled = false;
		//document.rpiForm.seqId.readonly = false;
	} 
		
	else if (instructionType == "ADCRS") {
		document.rpiForm.seqId.value = "360";
	  	object.percentDistAmt.value = '';
		//document.rpiForm.seqId.disabled = false;
			//document.rpiForm.seqId.readonly = false;
	} 
	else if (instructionType == "TXLVY") {
		document.rpiForm.seqId.value = "371";
	  	object.percentDistAmt.value = '';
		//document.rpiForm.seqId.disabled = false;
		//document.rpiForm.seqId.readonly = false;
	}  
	else if (instructionType == "LGLFD") {
		document.rpiForm.seqId.value = "410";
	  	object.percentDistAmt.value = '';
			//document.rpiForm.seqId.readonly = false;
		//document.rpiForm.seqId.disabled = true;
	} 
	else if (instructionType == "ASCAP") {
		//document.rpiForm.seqId.readonly = false;
		document.rpiForm.seqId.value = "411";
	  	object.percentDistAmt.value = '';
		//alert(document.rpiForm.seqId.readonly);
		
			
		//document.rpiForm.seqId.disabled = false;
	} 
	else if (instructionType == "ADPMT") {
		document.rpiForm.seqId.value = "503";
	  	object.percentDistAmt.value = '';
			//document.rpiForm.seqId.readonly = false;
		//document.rpiForm.seqId.disabled = false;
	} 
	else if (instructionType == "MISCL") {
		document.rpiForm.seqId.value = "650";
	  	object.percentDistAmt.value = '';
		//document.rpiForm.seqId.disabled = false;
			//document.rpiForm.seqId.readonly = false;
	} 
	else if (instructionType == "CRDUN") {
		document.rpiForm.seqId.value = "660";
	  	object.percentDistAmt.value = '';
		//document.rpiForm.seqId.disabled = false;
			//document.rpiForm.seqId.readonly = false;
	} 
	else if (instructionType == "RYLTY") {
		document.rpiForm.seqId.value = "661";
	  	object.percentDistAmt.value = '';
		//document.rpiForm.seqId.disabled = false;
			//document.rpiForm.seqId.readonly = false;
	} 
	else if (instructionType == "CHSUP") {
		document.rpiForm.seqId.value = "691";
	  	object.percentDistAmt.value = '';
		//document.rpiForm.seqId.disabled = false;
			//document.rpiForm.seqId.readonly = false;
	} 
	else if (instructionType == "RLPMT") {
		document.rpiForm.seqId.value = "501";
	  	object.percentDistAmt.value = '';
		//document.rpiForm.seqId.disabled = false;
			//document.rpiForm.seqId.readonly = false;
	} 	
	//else {
		//document.rpiForm.seqId.value = "";
		//document.rpiForm.seqId.disabled = false;
	//} //end if

}

var somethingCheck = 0;
var somethingEFT = 0;
isCheck = true;
isEFT = true;


// If the Instruction Type is 'NRA Tax' or 'Backup Witholding Tax' then
// Payment Method 'Check' has to be deleted
// If the Instruction Type is 'ASCAP Foundation' or 'Legislative Fund' then
// Payment Method 'Check' and 'EFT' has to be deleted
function deleteCheckInPaymentMethod(object) {

	instructionType = object.rpiType.options[object.rpiType.selectedIndex].value;
	var defaultSelected = false;
	var selected = false;

	for (var i=object.pmtMeth.options.length;i > 0 ;i--) {
		object.pmtMeth.options[i] = null;
	}
	if(instructionType == "1001" || instructionType == "1000") {
		option0 = new Option("", "", defaultSelected, selected)
		option1 = new Option("EFT", "1002", defaultSelected, selected)
		option2 = new Option("DirectDeposite", "1001", defaultSelected, selected)
		for (var i=0;i < 3 ;i++) {
			optionEval = 'option'+i;
			object.pmtMeth.options[i] = eval(optionEval);
		}
	} else if(instructionType == "1003" || instructionType == "1002") {
		option0 = new Option("", "", defaultSelected, selected)
		option1 = new Option("DirectDeposite", "1001", defaultSelected, selected)
		for (var i=0;i < 2 ;i++) {
			optionEval = 'option'+i;
			object.pmtMeth.options[i] = eval(optionEval);
		}
	} else {
		option0 = new Option("", "", defaultSelected, selected)
		option1 = new Option("EFT", "1002", defaultSelected, selected)
		option2 = new Option("DirectDeposite", "1001", defaultSelected, selected)
		option3 = new Option("Check", "1000", defaultSelected, selected)
		for (var i=0;i < 4 ;i++) {
			optionEval = 'option'+i;
			object.pmtMeth.options[i] = eval(optionEval);
		}
	}

/*
I tried the following code first, but it did not work very well. Basically I try to
based on the instruction type delete the unwanted ones and keep the existing ones
based on some flags.

	if(instructionType == "1001" || instructionType == "1000") {


		for (var i=0;i < object.pmtMeth.options.length;i++) {
			if(object.pmtMeth.options[i].value == "1000") {
				object.pmtMeth.options[i] = null;
				isCheck = false;
				alert("isCheck1 is: " + isCheck);
				somethingCheck = i;
			}
		}
	} else {


		for (var i=0;i < object.pmtMeth.options.length;i++) {
			if(object.pmtMeth.options[i].value == "1000") {
				isCheck = true;
				break;
			}
		}

		if (!isCheck) {
			var defaultSelected = false;
			var selected = false;
			var optionName = new Option("Check", "1000", defaultSelected, selected)
			var length = object.pmtMeth.length;
			object.pmtMeth.options[somethingCheck] = optionName;
		}
	} // end if

	if(instructionType == "1003" || instructionType == "1002") {

		for (var i=0;i < object.pmtMeth.options.length;i++) {
			if(object.pmtMeth.options[i].value == "1000") {
				object.pmtMeth.options[i] = null;
				isCheck = false;
				somethingCheck = i;
			}
		}
		for (var i=0;i < object.pmtMeth.options.length;i++) {
			if(object.pmtMeth.options[i].value == "1002") {
				object.pmtMeth.options[i] = null;
				isEFT = false;
				somethingEFT = i;
			}
		}
	} else {

		for (var i=0;i < object.pmtMeth.options.length;i++) {
			if(object.pmtMeth.options[i].value == "1000") {
				isCheck = true;
				break;
			}
		}
		for (var i=0;i < object.pmtMeth.options.length;i++) {
			if(object.pmtMeth.options[i].value == "1002") {
				isEFT = true;
				break;
			}
		}

		if (isCheck) {
				alert("here????: " + isCheck);
			var defaultSelected = false;
			var selected = false;
			var optionName = new Option("Check1", "1000", defaultSelected, selected)
			var length = object.pmtMeth.length;
			object.pmtMeth.options[somethingCheck] = optionName;
		}
		if (!isEFT) {
			var defaultSelected = false;
			var selected = false;
			var optionName = new Option("EFT", "1002", defaultSelected, selected)
			var length = object.pmtMeth.length;
			object.pmtMeth.options[somethingEFT] = optionName;
		}
	} // end if

*/

} // end of function deleteCheckInPaymentMethod

// If the Distribution Type is 'All' automatically check and disable other types
function checkDistType(object) {

	distAll = object.distAll;


	if(distAll.checked == true) {
		document.rpiForm.distDome.checked = true;
		document.rpiForm.distIntIn.checked = true;
		document.rpiForm.distSpl.checked = true;
		document.rpiForm.distDome.disabled = true;
		document.rpiForm.distIntIn.disabled = true;
		document.rpiForm.distSpl.disabled = true;
	}
	else {
		document.rpiForm.distDome.disabled = false;
		document.rpiForm.distIntIn.disabled = false;
		document.rpiForm.distSpl.disabled = false;
		document.rpiForm.distDome.checked = false;
		document.rpiForm.distIntIn.checked = false;
		document.rpiForm.distSpl.checked = false;
	}

}
//Shailaja 10/24/2003
function checkDistributionType(object) {
if(object.checked == true) 
{
	for (var i = 0; i<document.rpiForm.elements.length; i++) 
	{	
        if (((document.rpiForm.elements[i].name.indexOf('distType') > -1)) )
	     {
	   		document.rpiForm.elements[i].checked=true
	     	document.rpiForm.elements[i].readonly;
	      	
	     }
	     
	}
	
}else 
{
	for (var i = 0; i<document.rpiForm.elements.length; i++) 
	{
        if (((document.rpiForm.elements[i].name.indexOf('distType') > -1)) )
     	{
     		document.forms[0].elements[i].checked=false;
     	
     	}
    
	}

}

}



function checkDistTypeAll(object) {

	//dist = object.distType;
	distAll=object[0];


	if(distAll.checked == true) {
		document.rpiForm.distDome.checked = true;
		document.rpiForm.distIntIn.checked = true;
		document.rpiForm.distSpl.checked = true;
		document.rpiForm.distDome.disabled = true;
		document.rpiForm.distIntIn.disabled = true;
		document.rpiForm.distSpl.disabled = true;
	}
	else {
		document.rpiForm.distDome.disabled = false;
		document.rpiForm.distIntIn.disabled = false;
		document.rpiForm.distSpl.disabled = false;
		document.rpiForm.distDome.checked = false;
		document.rpiForm.distIntIn.checked = false;
		document.rpiForm.distSpl.checked = false;
	}

}



// Based on Payment Method, show or hide their respective information
function checkPaymentMethod(object) {

	paymentMethod = object.pmtMeth.options[object.pmtMeth.selectedIndex].value;
	if(paymentMethod == "CH")//Check 
	{
		toggleBox('paymentName',1);
		toggleBox('checkDetails',1);
		toggleBox('bankDetails',0);
		}
		if(paymentMethod == "") 
		{
		toggleBox('paymentName',0);
		toggleBox('checkDetails',0);
		toggleBox('bankDetails',0);
		} 
	if(paymentMethod == "WT") //EFT
	{
		toggleBox('paymentName',1);
		toggleBox('bankDetails',1);
		toggleBox('checkDetails',0);
	}else if (paymentMethod == "DD")//DirectDeposit
	 {
		toggleBox('paymentName',1);
		toggleBox('bankDetails',1);
		toggleBox('checkDetails',0);
	}

}


// Based on the Hold Type selected this function will dynamically change
// the values of Hold Level

function checkHoldType(object) {

	holdType = object.holdType.options[object.holdType.selectedIndex].value;
	holdLevelLength = object.holdLevel.options.length;

	for (var i=holdLevelLength; i > 1; i--) {
		object.holdLevel.options[i] = null;
	}


	var defaultSelected = false;
	var selected = false;
	var optionNameMembership = new Option("Membership", "C", defaultSelected, selected);
	var optionNameEntitledEarner = new Option("EntitledEarner", "D", defaultSelected, selected);
	var optionNameRPI = new Option("RPI", "E", defaultSelected, selected);

	if(holdType == "10003" ||
		holdType == "10006" ||
		holdType == "10014" ) {

		object.holdLevel.options[1] = optionNameMembership;

	} else if (holdType == "10004" ||
		holdType == "10007" ||
		holdType == "10008" ||
		holdType == "10009" ) {

		object.holdLevel.options[1] = optionNameMembership;
		object.holdLevel.options[2] = optionNameEntitledEarner;

	} else if (holdType == "10005" ||
		holdType == "10011" ||
		holdType == "10012" ) {

		object.holdLevel.options[1] = optionNameRPI;

	} else if (holdType == "10010" ) {

		object.holdLevel.options[1] = optionNameEntitledEarner;

	} else if (holdType == "10001" ||
		holdType == "10002" ||
		holdType == "10013" ) {

		object.holdLevel.options[1] = optionNameMembership;
		object.holdLevel.options[2] = optionNameEntitledEarner;
		object.holdLevel.options[3] = optionNameRPI;

	}
} // end function checkHoldType

// Display the quarter based on the date
// Current logic: If the dates are of mm/dd/yyyy format only then will we display the quarter
function setStartQuarter(object) {

	startDate = object.startDate.value;
	if (isValidDate(startDate)==true){
		startDateMMDDYYYY = stripCharsInBag(startDate, dtCh);
		var strMonth=startDate.substring(0,2);
		var strYear =startDate.substring(6); 
		if (strMonth == "01" || strMonth == "02" || strMonth == "03" ) {
			object.startQuarter.value = "Q1 "+strYear;
		} else if (strMonth == "04" || strMonth == "05" || strMonth == "06" ) {
			object.startQuarter.value = "Q2 "+strYear;
		} else if (strMonth == "07" || strMonth == "08" || strMonth == "09" ) {
			object.startQuarter.value = "Q3 "+strYear;
		} else if (strMonth == "10" || strMonth == "11" || strMonth == "12" ) {
			object.startQuarter.value = "Q4 "+strYear;
		}
	}
}

// Display the quarter based on the date
// Current logic: If the dates are of mm/dd/yyyy format only then will we display the quarter
function setEndQuarter(object) {

	endDate = object.endDate.value;
	if (isValidDate(endDate)==true){
		endDateMMDDYYYY = stripCharsInBag(endDate, dtCh);
		var strMonth=endDate.substring(0,2);
		var strYear =endDate.substring(6); 
		if (strMonth == "01" || strMonth == "02" || strMonth == "03" ) {
			object.endQuarter.value = "Q1 "+strYear;
		} else if (strMonth == "04" || strMonth == "05" || strMonth == "06" ) {
			object.endQuarter.value = "Q2 "+strYear;
		} else if (strMonth == "07" || strMonth == "08" || strMonth == "09" ) {
			object.endQuarter.value = "Q3 "+strYear;
		} else if (strMonth == "10" || strMonth == "11" || strMonth == "12" ) {
			object.endQuarter.value = "Q4 "+strYear;
		}
	}
}

// If today's date falls between Start Date and End Date then enable the active and
// inactive radio buttons. Current logic: If the dates are of mm/dd/yyyy format only
// then will we perform the enable
function activateHold(object) {
	startDate = object.startDate.value;
	endDate = object.endDate.value;
	if (isValidDate(startDate)==true && isValidDate(endDate)==true){
		startDateMMDDYYYY = stripCharsInBag(startDate, dtCh);
		endDateMMDDYYYY = stripCharsInBag(endDate, dtCh);

		var aceDate=new Date();
		var aceYear=aceDate.getYear();
		if (aceYear < 1000) {
			aceYear+=1900;
		}
		var aceDay=aceDate.getDay();
		var aceMonth=aceDate.getMonth()+1;
		if (aceMonth<10) {
			aceMonth="0"+aceMonth;
		}
		var aceDayMonth=aceDate.getDate();
		if (aceDayMonth<10) {
			aceDayMonth="0"+aceDayMonth
		}
		currentDate = aceMonth+aceDayMonth+aceYear;
		
		if ( (startDateMMDDYYYY < currentDate) && (endDateMMDDYYYY > currentDate)) {
			object.status[0].disabled = false;
			object.status[1].disabled = false;
			object.status[1].checked = true;
		}
	}
}

// Based on the Hold level  this function will dynamically change
// the values of Hold type

function setHoldType(object, holdLevelValue) {
	holdType = object.holdType.options[object.holdType.selectedIndex].value;
	holdLevelLength = object.holdLevel.options.length;
	
	for (var i=holdLevelLength; i > 1; i--) {
		object.holdLevel.options[i] = null;
	}
	
	var _selected = object.holdLevel.selectedIndex;
	var defaultSelected = false;
	var selected = false;
	var optionNameMembership = new Option("Membership", "C", defaultSelected, selected);
	var optionNameEntitledEarner = new Option("EntitledEarner", "D", defaultSelected, selected);
	var optionNameRPI = new Option("RPI", "E", defaultSelected, selected);

	if(holdType == "10003" ||
		holdType == "10006" ||
		holdType == "10014" ) {

		object.holdLevel.options[1] = optionNameMembership;
		if(object.holdLevel.options[1].value == holdLevelValue) {
			object.holdLevel.options[1].selected = 'true';
		}

	} else if (holdType == "10004" ||
		holdType == "10007" ||
		holdType == "10008" ||
		holdType == "10009" ) {

		object.holdLevel.options[1] = optionNameMembership;
		if(object.holdLevel.options[1].value == holdLevelValue) {
			object.holdLevel.options[1].selected = 'true';
		}

		object.holdLevel.options[2] = optionNameEntitledEarner;
		if(object.holdLevel.options[2].value == holdLevelValue) {
			object.holdLevel.options[2].selected = 'true';
		}


	} else if (holdType == "10005" ||
		holdType == "10011" ||
		holdType == "10012" ) {

		object.holdLevel.options[1] = optionNameRPI;
		if(object.holdLevel.options[1].value == holdLevelValue) {
			object.holdLevel.options[1].selected = 'true';
		}


	} else if (holdType == "10010" ) {

		object.holdLevel.options[1] = optionNameEntitledEarner;
		if(object.holdLevel.options[1].value == holdLevelValue) {
			object.holdLevel.options[1].selected = 'true';
		}

	} else if (holdType == "10001" ||
		holdType == "10002" ||
		holdType == "10013" ) {

		object.holdLevel.options[1] = optionNameMembership;
		if(object.holdLevel.options[1].value == holdLevelValue) {
			object.holdLevel.options[1].selected = 'true';
		}

		object.holdLevel.options[2] = optionNameEntitledEarner;
		if(object.holdLevel.options[2].value == holdLevelValue) {
			object.holdLevel.options[2].selected = 'true';
		}

		object.holdLevel.options[3] = optionNameRPI;
		if(object.holdLevel.options[3].value == holdLevelValue) {
			object.holdLevel.options[3].selected = 'true';
		}


	}	
} // end function setHoldType

// Begin: Internet Script - Saravanan
/**
 * DHTML date validation script. Courtesy of SmartWebby.com (http://www.smartwebby.com/dhtml/)
 */
// Declaring valid date character, minimum year and maximum year
var dtCh= "/";
var minYear=1900;
var maxYear=2100;


function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}


function stripCharsInBag(s, bag){
	var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++){
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}


function daysInFebruary (year){
	// February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}

function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31
		if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
		if (i==2) {this[i] = 29}
   }
   return this
}

function isValidDate(dtStr){
	var daysInMonth = DaysArray(12)
	var pos1=dtStr.indexOf(dtCh)
	var pos2=dtStr.indexOf(dtCh,pos1+1)
	var strMonth=dtStr.substring(0,pos1)
	var strDay=dtStr.substring(pos1+1,pos2)
	var strYear=dtStr.substring(pos2+1)
	strYr=strYear
	if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
	if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
	}
	month=parseInt(strMonth)
	day=parseInt(strDay)
	year=parseInt(strYr)
	if (pos1==-1 || pos2==-1){
		//alert("The date format should be : mm/dd/yyyy")
		return false
	}
	if (strMonth.length<1 || month<1 || month>12){
		//alert("Please enter a valid month")
		return false
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		//alert("Please enter a valid day")
		return false
	}
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		//alert("Please enter a valid 4 digit year between "+minYear+" and "+maxYear)
		return false
	}
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		//alert("Please enter a valid date")
		return false
	}
	return true
}

//Shailaja 09/25/03
//moves the contents of table cells up and down

function moveRow(direction)
    {
      rowIndex = parseInt(window.event.srcElement.parentElement.parentElement.index);
      if((rowIndex == 0 && direction > 0) || (rowIndex == id_listTable.rows.length - 1 && direction < 0) || (rowIndex > 0 && rowIndex < id_listTable.rows.length - 1))
      {
        if(id_listTable.rows[rowIndex].cells[2].swapNode)
        {
          id_listTable.rows[rowIndex + direction].cells[2].swapNode(id_listTable.rows[rowIndex].cells[2]);
        }
        else
        {
          swapHTML = id_listTable.rows[rowIndex + direction].cells[2].innerHTML;
          id_listTable.rows[rowIndex + direction].cells[2].innerHTML = id_listTable.rows[rowIndex].cells[2].innerHTML;
          id_listTable.rows[rowIndex].cells[2].innerHTML = swapHTML;
        }
      }
    }
 



//Shailaja 09/30/03
//checks if any check box is selected .Used in ryRpi.jsp


function checkedAnyRpi()
{
 checkedFalg=false;
	for (var i = 0; i<document.forms[0].elements.length; i++) 
{
        if (((document.forms[0].elements[i].name.indexOf('rpiId') > -1)) )
     {
     	if(document.forms[0].elements[i].checked==true)
     	checkedFalg=true;
     	
     }
     if(checkedFalg)
     break;
}
return checkedFalg;



}

//writes to the opener window




function populateParent2(payeeName, payeeAddress, ssnOrTaxID) {

 rowFilled=0;
for (var i = 0; i <= opener.theWork.length; i++) {
        if (  opener.theWork[i].id=='')
        {
         	rowFilled=1;
         	
         	opener.theWork[i].cbox='<input type="checkbox" name="works"   value="'+payeeName+'"  checked />' ;
            opener.theWork[i].id = payeeName;
    		opener.theWork[i].title= payeeAddress;
    		opener.theWork[i].type = ssnOrTaxID;
    	
    		 
    		opener.createWorkTable();
    		window.close();
    
    	}
    if( rowFilled==1)
    break;
    }
  
}


function populateChildParty(partyId, sequence, relationship) {



 rowFilled=0;
for (var i = 0; i <= opener.theParty.length; i++) {
        if (  opener.theParty[i].id=='')
        {
         	rowFilled=1;
         	
         	opener.theParty[i].cbox='<input  type="checkbox" name="works"   value="'+partyId+'" />' ;
            opener.theParty[i].id = partyId;
    		opener.theParty[i].title= sequence;
    		opener.theParty[i].type = relationship;
    	
    		 
    		opener.createPartyTable();
    		window.close();
    
    	}
    if( rowFilled==1)
    break;
    }


//    window.close();

}












