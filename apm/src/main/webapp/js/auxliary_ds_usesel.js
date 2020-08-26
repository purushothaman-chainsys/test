// auxliary_ds_usesel.js

var IE = (document.all) ? true : false;
function getRowIndex( obj ){
    if (!obj.tagName == 'TR')
         return;
    while (obj != null && obj.tagName != 'TR')
          obj = (IE) ? obj.parentElement : obj.parentNode;
     //alert(obj.rowIndex);
     return obj.rowIndex;
}



// ***** Popup window
var aWindow = null;
function openWin(URL, w, h){
aWindow = open(URL,'aWindow','width='+w+',height='+h+',scrollbars=yes, status=yes');
aWindow.opener = self;
}


//#### Series Codes
function makeSeries(id,cbox,code,title,cbox1,cbox2) {
	this.id = id;
	this.cbox = cbox;
	this.code = code;
	this.title = title;	
	this.cbox1 = cbox1;
	this.cbox2 = cbox2;
    return this;
}

theSeries = new Array();

theSeries[0] = new  makeSeries('','','','','','');
theSeries[1] = new  makeSeries('','','','','','');
theSeries[2] = new  makeSeries('','','','','','');
theSeries[3] = new  makeSeries('','','','','','');
theSeries[4] = new  makeSeries('','','','','','');
theSeries[5] = new  makeSeries('','','','','','');
theSeries[6] = new  makeSeries('','','','','','');
theSeries[7] = new  makeSeries('','','','','','');
theSeries[8] = new  makeSeries('','','','','','');
theSeries[9] = new  makeSeries('','','','','','');


// **** Method to Create Series Code row
function populateSeries() {
	rowFilled=0;
	for (var i = 0; i <= theSeries.length; i++) {
		if (theSeries[i].id=='') {
         	rowFilled=1;
         	        			
         	theSeries[i].id = '<input  type="hidden" name="id"   value="'+i+'"  />';
         	theSeries[i].cbox='<input  type="checkbox" name="seriesId"   value="'+i+'"  />' ;
            theSeries[i].code= '<input  type="text" name="seriesCode"   value=""  />';
			theSeries[i].title='<input  type="text" name="seriesTitle"   value=""  />' ;
			theSeries[i].cbox1='<input  type="checkbox" name="seriesInclInd"   value="'+i+'"  />' ;
			theSeries[i].cbox2='<input  type="checkbox" name="seriesExclInd"   value="'+i+'"  />' ;
			    	
    		createDynamicContent2();
		}
    	if( rowFilled==1)
    		break;
    }
}

// **** Method to remove the selected Series Codes from UI
function removeSeries() 
{
	for (var i = 0; i<document.forms[0].elements.length; i++) 
	{ 
		if (((document.forms[0].elements[i].name.indexOf('seriesId') > -1)) &&(document.forms[0].elements[i].checked==true))
        {
           for (var j = 0; j < theSeries.length; j++) 
   		   {
        		if((theSeries[j].id !='' )&&(theSeries[j].id.indexOf(document.forms[0].elements[i].value) > -1))
        		{   
        			theSeries[j].id = ' ';
         			theSeries[j].cbox=' ';
		            theSeries[j].code= ' ';
					theSeries[j].title=' ';
					theSeries[j].cbox1=' ';
					theSeries[j].cbox2=' ';
        		}
           }
	    }
	}
	createDynamicContent2();
}


//**** Function to Check all selected Series Codes
//**** Before submitting the form
function checkAllSeries()
{
	for (var i = 0; i<document.forms[0].elements.length; i++) 
	{
		if (((document.forms[0].elements[i].name.indexOf('seriesId') > -1)) )
		{
     		document.forms[0].elements[i].checked=true;
     	}
	}
}


//#### Program Codes
function makeProgram(id,cbox,code,title,cbox1,cbox2) {
	this.id = id;
	this.cbox = cbox;
	this.code = code;
	this.title = title;	
	this.cbox1 = cbox1;
	this.cbox2 = cbox2;
    return this;
}


theProgram = new Array();
theProgram[0] = new makeProgram('','','','','','');
theProgram[1] = new makeProgram('','','','','','');
theProgram[2] = new makeProgram('','','','','','');
theProgram[3] = new makeProgram('','','','','','');
theProgram[4] = new makeProgram('','','','','','');
theProgram[5] = new makeProgram('','','','','','');
theProgram[6] = new makeProgram('','','','','','');
theProgram[7] = new makeProgram('','','','','','');
theProgram[8] = new makeProgram('','','','','','');
theProgram[9] = new makeProgram('','','','','','');

// **** Method to Create Program Code row
function populateProgram() {
	rowFilled=0;
	for (var i = 0; i <= theProgram.length; i++) {
	   	if (theProgram[i].id=='') {
         	rowFilled=1;
         	        			
         	theProgram[i].id = '<input  type="hidden" name="id"   value="'+i+'"  />';
         	theProgram[i].cbox='<input  type="checkbox" name="programId"   value="'+i+'"  />' ;
            theProgram[i].code= '<input  type="text" name="programCode"   value=""  />';
			theProgram[i].title='<input  type="text" name="programTitle"   value=""  />' ;
			theProgram[i].cbox1='<input  type="checkbox" name="programInclInd"   value="'+i+'"  />' ;
			theProgram[i].cbox2='<input  type="checkbox" name="programExclInd"   value="'+i+'"  />' ;
			    	
    		createDynamicContent3();
		}
    	if( rowFilled==1)
    		break;
    }
}

// **** Method to remove the selected Program Codes from UI
function removeProgram() 
{
	for (var i = 0; i<document.forms[0].elements.length; i++) 
	{ 
		if (((document.forms[0].elements[i].name.indexOf('programId') > -1)) &&(document.forms[0].elements[i].checked==true))
        {
           for (var j = 0; j < theProgram.length; j++) 
   		   {
        		if((theProgram[j].id !='' )&&(theProgram[j].id.indexOf(document.forms[0].elements[i].value) > -1))
        		{   
        			theProgram[j].id = ' ';
         			theProgram[j].cbox=' ';
		            theProgram[j].code= ' ';
					theProgram[j].title=' ';
					theProgram[j].cbox1=' ';
					theProgram[j].cbox2=' ';
        		}
           }
	    }
	}
	createDynamicContent3();
}

//**** Function to Check all selected Program Codes
//**** Before submitting the form
function checkAllProgram()
{
	for (var i = 0; i<document.forms[0].elements.length; i++) 
	{
		if (((document.forms[0].elements[i].name.indexOf('programId') > -1)) )
		{
     		document.forms[0].elements[i].checked=true;
     	}
	}
}



//#### Intrested Parties
function makeParty(cbox,id,name,cbox) {
	this.cbox = cbox;
    this.id = id;
    this.name = name;   
    this.cbox1 = cbox;
    return this;
}

theParty = new Array();
theParty[0] = new makeParty('','','','');
theParty[1] = new makeParty('','','','');
theParty[2] = new makeParty('','','','');
theParty[3] = new makeParty('','','','');
theParty[4] = new makeParty('','','','');
theParty[5] = new makeParty('','','','');
theParty[6] = new makeParty('','','','');
theParty[7] = new makeParty('','','','');
theParty[8] = new makeParty('','','','');
theParty[9] = new makeParty('','','','');

// ############################################
// ######### Music User #######################
// ############################################



// ***** Music User Definition **********
function makeMusicUser(cbox,id,name,cbox) {
	this.cbox = cbox;
    this.id = id;
    this.name = name;   
    this.cbox1 = cbox;
    return this;
}

// ***** Array of Music Users *********
theMusicUser = new Array();
theMusicUser[0] = new makeMusicUser('','','','');
theMusicUser[1] = new makeMusicUser('','','','');
theMusicUser[2] = new makeMusicUser('','','','');
theMusicUser[3] = new makeMusicUser('','','','');
theMusicUser[4] = new makeMusicUser('','','','');
theMusicUser[5] = new makeMusicUser('','','','');
theMusicUser[6] = new makeMusicUser('','','','');
theMusicUser[7] = new makeMusicUser('','','','');
theMusicUser[8] = new makeMusicUser('','','','');
theMusicUser[9] = new makeMusicUser('','','','');

// **** Method to remove the selected music user from UI
function removeMusicUser() 
{
	for (var i = 0; i<document.forms[0].elements.length; i++) 
	{ 
		if (((document.forms[0].elements[i].name.indexOf('musicUserId') > -1)) &&(document.forms[0].elements[i].checked==true))
        {
           for (var j = 0; j < theMusicUser.length; j++) 
   		   {
        		if((theMusicUser[j].id !='' )&&(theMusicUser[j].id.indexOf(document.forms[0].elements[i].value) > -1))
        		{   
        			theMusicUser[j].cbox = '';
        			theMusicUser[j].id = '';
           			theMusicUser[j].name = '';
          			theMusicUser[j].cbox1 = '';
        		}
           }
	    }
	}
	createDynamicContent();
}

//**** Function to Check all selected music users 
//**** Before submitting the form
function checkAllMusicUsers()
{
	for (var i = 0; i<document.forms[0].elements.length; i++) 
	{
		if (((document.forms[0].elements[i].name.indexOf('musicUserId') > -1)) )
		{
     		document.forms[0].elements[i].checked=true;
     	}
	}
}


// **** Method to remove the selected Intrested Parties from UI
function removeIPs() 
{
	for (var i = 0; i<document.forms[0].elements.length; i++) 
	{ 
		if (((document.forms[0].elements[i].name.indexOf('ipId') > -1)) &&(document.forms[0].elements[i].checked==true))
        {
           for (var j = 0; j < theParty.length; j++) 
   		   {
        		if((theParty[j].id !='' )&&(theParty[j].id.indexOf(document.forms[0].elements[i].value) > -1))
        		{   
        			theParty[j].cbox = '';
        			theParty[j].id = '';
           			theParty[j].name = '';
          			theParty[j].cbox1 = '';
        		}
           }
	    }
	}
	createDynamicContent4();
}

//check all ips
function checkAllIPs()
{
	for (var i = 0; i<document.forms[0].elements.length; i++) 
	{
		if (((document.forms[0].elements[i].name.indexOf('ipId') > -1)) )
		{
     		document.forms[0].elements[i].checked=true;
     	}
	}
}


function createDynamicContent(){

// Music User

	header = '<br>[OPTIONAL]<br>Music Users <br><table width="660" border="0" cellspacing="2" cellpadding="2" class="txtBlk">' +
			 '<tr bgcolor="#5985AD" align="center" class="txtBlk_bold"> ' +
				'<td width="10%">Select</td> ' +
				'<td width="40%">ID</td> ' +
				'<td width="40%">Music User Call Letters/Name</td> ' +
				'<td width="10%">Exclude</td> ' +
			'</tr>';
			
	content = '';			
	
	//*** Build the Content with Music User data
	for (var i = 0; i <= theMusicUser.length; i++) {
        if (theMusicUser[i])
          content += '<tr>'+
          '<td>' + theMusicUser[i].cbox + '<\/td>'+
          '<td>' + theMusicUser[i].id + '<\/td>'+
          '<td>' + theMusicUser[i].name + '<\/td>'+
          '<td>' + theMusicUser[i].cbox1 + '<\/td>'+
          '<\/tr>';         
    }
  
    
    footer = '<tr>' +
		      '<td align="left"> '+
			   '<input type="button" name="actionType1" value=" + "  class="formButtonBlue" onclick="javascript:openWin(\'/PrepWeb/distribution/searchPartyEmpty.do?mode=mu\',700,410);" /> ' + 
			   '<input type="button" name="actionType1" value=" - "  class="formButtonBlue" onClick="javascript:removeMusicUser()" /> ' + 
			  '</td> '+
			'</tr>' +
			'</table>';
	

	dynamicContent1 = header + content + footer;

	if (document.all)
        document.all('dynamicContent').innerHTML = dynamicContent1;
    else if(document.layers) {
    	document.layers['dynamicContent'].document.open();
        document.layers['dynamicContent'].document.writeln(dynamicContent1);
        document.layers['dynamicContent'].document.close();
    }
}    
    
function createDynamicContent2(){
/*
//Series codes
			
	header2 = '<br>[Optional Fields For Non-stratum] <br> Series <br><table width="660" border="0" cellspacing="2" cellpadding="2" class="txtBlk">' +
			'<tr bgcolor="#5985AD" align="center" class="txtBlk_bold">' +
				'<td width="15%">Select</td>' +
				'<td width="10%">Code</td>' +
				'<td width="40%">Series Title</td>' +
				'<td width="20%">Include</td>' +
				'<td width="20%">Exclude</td>' +
			'</tr>';
	content2 = '';			
	
	//*** Build the Content of the table
	for (var i = 0; i <= theSeries.length; i++) {
        if (theSeries[i])
          content2 += '<tr>'+ theSeries[i].id +          
          '<td>' + theSeries[i].cbox + '<\/td>'+
          '<td>' + theSeries[i].code + '<\/td>'+
          '<td>' + theSeries[i].title + '<\/td>'+
          '<td>' + theSeries[i].cbox1 + '<\/td>'+
          '<td>' + theSeries[i].cbox2 + '<\/td>'+                    
          '<\/tr>';         
    }
      
    footer2 = '<tr>' +
		      '<td align="left"> '+
		      '<input type="button" name="actionType1" value=" + "  class="formButtonBlue" onclick="javascript:populateSeries()" /> ' + 
			  '<input type="button" name="actionType1" value=" - "  class="formButtonBlue" onClick="javascript:removeSeries()" /> ' + 
			  '</td> '+
			  '</tr>' +
			  '</table>';
	dynamicContentX = header2 + content2 + footer2;
	
	if (document.all)
        document.all('dynamicContent2').innerHTML = dynamicContentX;
    else if(document.layers) {
    	document.layers['dynamicContent2'].document.open();
        document.layers['dynamicContent2'].document.writeln(dynamicContentX);
        document.layers['dynamicContent2'].document.close();
    }			*/
    
      dynamicContentX = '<br>[Optional Fields For Non-stratum] ' +
		      '<br> Series <br> ' +

		      '<table id="series_table" width="660" border="0" cellspacing="2" cellpadding="2" class="txtBlk">'+
		      '<tr bgcolor="#5985AD" align="center" class="txtBlk_bold">'+
		      '<td width="10%">Select</td>'+
		      '<td width="40%">Code</td>'+
		      '<td width="40%">Series Title</td>'+
		      '<td width="10%">Exclude</td>'+
		      '</table>'+
		      
		      '<table>'+
		      '<tr>'+
		      '<td align="left">'+
		      '<input type="button" name="actionType1" value=" + "  class="formButtonBlue" onclick="javascript:insert_series_row()" />'+
		      '<input type="button" name="actionType1" value=" - "  class="formButtonBlue" onClick="javascript:deleteSelectedRows(\'series_table\', document.forms[0].seriesId )" />'+
		      '</td>'+
		      '</tr>'+
		      '</table>';
if (document.all)
        document.all('dynamicContent2').innerHTML = dynamicContentX;
    else if(document.layers) {
    	document.layers['dynamicContent2'].document.open();
        document.layers['dynamicContent2'].document.writeln(dynamicContentX);
        document.layers['dynamicContent2'].document.close();
    }
}

function createDynamicContent3(){
//Program codes
/*			
	header3 = '<br>[Optional]<br> Program <br><table width="660" border="0" cellspacing="2" cellpadding="2" class="txtBlk">' +
			'<tr bgcolor="#5985AD" align="center" class="txtBlk_bold">' +
				'<td width="15%">Select</td>' +
				'<td width="10%">Code</td>' +
				'<td width="40%">Program Title</td>' +
				'<td width="20%">Include</td>' +
				'<td width="20%">Exclude</td>' +
			'</tr>';
	content3 = '';			
	
	//*** Build the Content of the table
	for (var i = 0; i <= theProgram.length; i++) {
        if (theProgram[i])
          content3 += '<tr>'+ theProgram[i].id +          
          '<td>' + theProgram[i].cbox + '<\/td>'+
          '<td>' + theProgram[i].code + '<\/td>'+
          '<td>' + theProgram[i].title + '<\/td>'+
          '<td>' + theProgram[i].cbox1 + '<\/td>'+
          '<td>' + theProgram[i].cbox2 + '<\/td>'+                    
          '<\/tr>';         
    }
      
    footer3 = '<tr>' +
		      '<td align="left"> '+
		       '<input type="button" name="actionType1" value=" + "  class="formButtonBlue" onclick="javascript:populateProgram()" /> ' + 
			   '<input type="button" name="actionType1" value=" - "  class="formButtonBlue" onClick="javascript:removeProgram()" /> ' + 
 			   '</td> '+
			   '</tr>' +
			   '</table>';
	dynamicContentX = header3 + content3 + footer3;
	
	if (document.all)
        document.all('dynamicContent3').innerHTML = dynamicContentX;
    else if(document.layers) {
    	document.layers['dynamicContent3'].document.open();
        document.layers['dynamicContent3'].document.writeln(dynamicContentX);
        document.layers['dynamicContent3'].document.close();
    } */
    dynamicContentX = '<br>[Optional] ' +
		      '<br> Program <br> ' +

		      '<table id="program_table" width="660" border="0" cellspacing="2" cellpadding="2" class="txtBlk">'+
		      '<tr bgcolor="#5985AD" align="center" class="txtBlk_bold">'+
		      '<td width="10%">Select</td>'+
		      '<td width="40%">Code</td>'+
		      '<td width="40%">Program Title</td>'+
		      '<td width="10%">Exclude</td>'+
		      '</table>'+
		      
		      '<table>'+
		      '<tr>'+
		      '<td align="left">'+
		      '<input type="button" name="actionType1" value=" + "  class="formButtonBlue" onclick="javascript:insert_program_row()" />'+
		      '<input type="button" name="actionType1" value=" - "  class="formButtonBlue" onClick="javascript:deleteSelectedRows(\'program_table\', document.forms[0].programId )" />'+
		      '</td>'+
		      '</tr>'+
		      '</table>';
if (document.all)
        document.all('dynamicContent3').innerHTML = dynamicContentX;
    else if(document.layers) {
    	document.layers['dynamicContent3'].document.open();
        document.layers['dynamicContent3'].document.writeln(dynamicContentX);
        document.layers['dynamicContent3'].document.close();
    }			   		
}

function createDynamicContent4(){
//Interested parties

	header4 = '<br>[Optional]<br>Interested Parties<br><table width="660" border="0" cellspacing="2" cellpadding="2" class="txtBlk">' +
			 '<tr bgcolor="#5985AD" align="center" class="txtBlk_bold"> ' +
				'<td width="10%">Select</td> ' +
				'<td width="40%">PartyID</td> ' +
				'<td width="40%">Name</td> ' +
				'<td width="10%">Exclude</td> ' +
			'</tr>';
			
	content4 = '';			
	
	//*** Build the Content with IP data
	for (var i = 0; i <= theParty.length; i++) {
        if (theParty[i])
          content4 += '<tr>'+
          '<td>' + theParty[i].cbox + '<\/td>'+
          '<td>' + theParty[i].id + '<\/td>'+
          '<td>' + theParty[i].name + '<\/td>'+
          '<td>' + theParty[i].cbox1 + '<\/td>'+
          '<\/tr>';         
    }
  
    
    footer4 = '<tr>' +
		      '<td align="left"> '+
			  '<input type="button" name="actionType1" value=" + "  class="formButtonBlue" onclick="javascript:openWin(\'/PrepWeb/distribution/searchPartyEmpty.do?mode=ip\',700,410);" /> ' + 
			  '<input type="button" name="actionType1" value=" - "  class="formButtonBlue" onClick="javascript:removeIPs()" /> ' + 
  	     	  '</td> '+
			  '</tr>' +
			  '</table>';
			
	pageFooter = '<table class="sr">' +
	          		'<tr><td align="right">' +
	          		'<input type="button" name="actionType1" value=" Submit "  class="formButtonBlue" onclick="javascript:submitAddSelForm(document.forms[0])" /> ' + 
		            '<input type="button" name="actionType1" value=" Cancel"  class="formButtonBlue" onclick="javascript:document.location.href=\'/PrepWeb/distribution/getDisUseSelList.do\'" /> ' + 
   				    '</td></tr>' +
				'</table>';
	dynamicContentX = header4 + content4 + footer4 + pageFooter;
	
	if (document.all)
        document.all('dynamicContent4').innerHTML = dynamicContentX;
    else if(document.layers) {
    	document.layers['dynamicContent4'].document.open();
        document.layers['dynamicContent4'].document.writeln(dynamicContentX);
        document.layers['dynamicContent4'].document.close();
    }	
}



function insert_program_row() {
	var the_table = document.getElementById('program_table');
	// add to end of table, can be modified to insert anywhere in table
	var new_row_index = the_table.rows.length;
	the_table.insertRow(new_row_index);
	the_table.rows[new_row_index].insertCell(0);
	the_table.rows[new_row_index].insertCell(1);
	the_table.rows[new_row_index].insertCell(2);
	the_table.rows[new_row_index].insertCell(3);

	the_table.rows[new_row_index].cells[0].innerHTML = '<tr><td><input  type="checkbox" name="programId"   value="'+(new_row_index - 1) +'"  /></td>';
	the_table.rows[new_row_index].cells[1].innerHTML = '<td><input  type="text" name="programCode"   value=""  /></td>';
	the_table.rows[new_row_index].cells[2].innerHTML = '<td><input  type="text" name="programTitle"   value=""  /></td>';
	the_table.rows[new_row_index].cells[3].innerHTML = '<td><input  type="checkbox" name="programExclInd"   value="'+ (new_row_index - 1)  +'"  /></td></tr>';
}


function insert_series_row() {
	var the_table = document.getElementById('series_table');
	// add to end of table, can be modified to insert anywhere in table
	var new_row_index = the_table.rows.length;
	the_table.insertRow(new_row_index);
	the_table.rows[new_row_index].insertCell(0);
	the_table.rows[new_row_index].insertCell(1);
	the_table.rows[new_row_index].insertCell(2);
	the_table.rows[new_row_index].insertCell(3);

	the_table.rows[new_row_index].cells[0].innerHTML = '<tr><td><input  type="checkbox" name="seriesId"   value="'+ (new_row_index - 1) +'"  /></td>';
	the_table.rows[new_row_index].cells[1].innerHTML = '<td><input  type="text" name="seriesCode"   value=""  /></td>';
	the_table.rows[new_row_index].cells[2].innerHTML = '<td><input  type="text" name="seriesTitle"   value=""  /></td>';
	the_table.rows[new_row_index].cells[3].innerHTML = '<td><input  type="checkbox" name="seriesExclInd"   value="'+ (new_row_index -1)  +'"  /></td></tr>';
}

function delete_row(table_name, row_index) {
	//alert("Intrested table name " + table_name);
	var the_table = document.getElementById(table_name);
	the_table.deleteRow(row_index);
}

function deleteSelectedRows(table_name, buttonGroup){
   if (buttonGroup[0]) { // if the button group is an array (one check box is not an array)
      for (var i=0; i<buttonGroup.length; i++) {
         if (buttonGroup[i].checked) {
			delete_row(table_name, getRowIndex(buttonGroup[i]));
	      }
	  }
	} else {  // There is only one check box (its not an array)
	   if (buttonGroup.checked) { // if the one check box is checked
		delete_row(table_name, getRowIndex(buttonGroup[i]));
	   }
	}
}
