
function stripes(tableId) {
	if(tableId) {
		$('table#'+tableId+' tr:nth-child(even)').has('td').removeClass().addClass("tableroweven");
		$('table#'+tableId+' tr:nth-child(odd)').has('td').removeClass().addClass("tablerowodd");
	}
	else {
		$('table.alternatecolors tr:nth-child(even)').has('td').removeClass().addClass("tableroweven");
		$('table.alternatecolors tr:nth-child(odd)').has('td').removeClass().addClass("tablerowodd");		
	}
}

function showSection(fld, dely) {
	var dP= new Number(500);	
	if(dely) {	if(!isNaN(dely)) {	dP = dely; } }
	$('div#'+fld).show(dP); 
}


function showDynamicContent(divArray, numColumns, targetDivId) {
	if(!divArray) { return; }
	if(!numColumns || isNaN(numColumns)) {
		numColumns = divArray.length;
	}	
	$('div#'+targetDivId).empty();
	var columnWidth=Math.round(100/numColumns);
	var colGroupStr = '<COLGROUP>';
	for(var x=0;x<numColumns;x++) {
		colGroupStr+='<COL width="'+columnWidth+'%">';
	}
	colGroupStr+= '</COLGROUP>';
	var targetTableId = targetDivId + '_PREP_DYN_TBL';
	$('div#'+targetDivId).prepend('<table class="txtBlk_bold" id="'+targetTableId+'"><tbody></tbody></table>');
	$('div#'+targetDivId+'>table#'+targetTableId).prepend(colGroupStr);
	
	var dynText = '';
	if(divArray.length > 0) {
		var colsAdded = new Number(0);
		dynText += '<tr>';
		for(var i=0;i<divArray.length;i++) {
			var colspanStr = '';
			var colspanNum = 1;
			if(divArray[i].indexOf('@')>=0) {
				colspanNum = divArray[i].substring(divArray[i].indexOf('@')+1);
				colspanStr = ' colspan="' + colspanNum + '" ';
				colsAdded = (new Number(colsAdded) + new Number(colspanNum) - 1);
			}
			if( ((colsAdded) % numColumns == 0) || (colsAdded >= numColumns)) {
				dynText += '</tr><tr>';
				colsAdded = colspanNum-1;
			}
			dynText += '<td'+colspanStr+'>'+$('div#'+divArray[i]).html()+'</td>';			
			colsAdded ++;
		}
		dynText += '</tr>';
	}
	$('div#'+targetDivId+'>table#'+targetTableId+'>tbody').empty().append(dynText);
}

function hideColumns() {        
	var colToHide = $('select[name=hideColumn]').val().substr(3);          
        colToHide--;
        $('table#mainData tr').each(function() { 
            $('td:eq(' + colToHide + ')',this).toggle();
        });
        $('table#mainData tr th:eq(' + colToHide + ')').toggle();
}

function toggleColumns(checked) {
		$('div#targetone').toggle(200);
}

function buildContextMenu(something) {
$.each(something, function(index, value) { 
});
	var xxx = 'manoj';
	
	
 var option = { width: 150, items: [
                            { text: "Print", icon: "../images/contextmenu/ac0036-16.gif", alias: "1-1", action: menuAction },
                            { text: "Toggle Columns", icon: "../images/contextmenu/ac0036-16.gif", alias: "1-2", action: menuAction },
                            { text: xxx, icon: contextPath.concat("/images/contextmenu/ac0036-16.gif"), alias: "1-2", action: menuAction },
                            
                            
                            { text: "Item Three", icon: contextPath.concat("/images/contextmenu/ei0021-16.gif"), alias: "1-3", action: menuAction },
                            { type: "splitLine" },
                            { text: "Hide Columns", icon: contextPath.concat("/images/contextmenu/wi0009-16.gif"), alias: "1-4", type: "group", width: 170, items: [	                       
                            ]
                            },
                            { type: "splitLine" },
                            { text: "Item Four", icon: contextPath.concat("/images/contextmenu/wi0124-16.gif"), alias: "1-5", action: menuAction },
                            { text: "Group Three", icon: contextPath.concat("/images/contextmenu/wi0062-16.gif"), alias: "1-6", type: "group", width: 180, items: [
	                            { text: "Item One", icon: contextPath.concat("/images/contextmenu/wi0096-16.gif"), alias: "4-1", action: menuAction },
	                            { text: "Item Two", icon: contextPath.concat("/images/contextmenu/wi0122-16.gif"), alias: "4-2", action: menuAction }
                            ]
                            }
                            ], onShow: applyrule,
                onContextMenu: BeforeContextMenu
            };
            function menuAction() {
            	alert('alias '+this.data.alias);
                if(this.data.alias == '1-1') { 
                	window.print();
                }
                else if(this.data.alias == '1-2') {
	                toggleColumns(true);
                }
                else if( (this.data.alias).indexOf('2-')>=0) {
	                $('#mainData').alertMe();
	                $('#mainData').togglePrepColumn( (this.data.alias).substr(2) );
	            }
            }
            function applyrule(menu) {               
                if (this.id == "target2") {
                    menu.applyrule({ name: "target2",
                        disable: true,
                        items: ["1-2", "2-3", "2-4", "1-6"]
                    });
                }
                else {
                    menu.applyrule({ name: "all",
                        disable: true,
                        items: []
                    });
                }
            }
            function BeforeContextMenu() {
                return this.id != "target3";
            }
			$.each(something, function(index, value) { 	
  				option.items[5].items[index] = new Object();
  				option.items[5].items[index].text = $(this).html();	
  				option.items[5].items[index].icon = contextPath.concat("/images/contextmenu/wi0124-16.gif");
  				option.items[5].items[index].alias = "2-"+index;
  				option.items[5].items[index].action = menuAction;
			});
            $("#mainData,#target2,#target3").contextmenu(option);

}




function buildContextMenuXX() {
 var option = { width: 150, items: [
                            { text: "Print", icon: contextPath.concat("/images/contextmenu/ac0036-16.gif"), alias: "1-1", action: menuAction },
                            { text: "Toggle Columns", icon: "../images/contextmenu/ac0036-16.gif", alias: "1-2", action: menuAction },
                            { text: "Item Three", icon: "../images/contextmenu/ei0021-16.gif", alias: "1-3", action: menuAction },
                            { type: "splitLine" },
                            { text: "Hide Columns", icon: contextPath.concat("/images/contextmenu/wi0009-16.gif"), alias: "1-4", type: "group", width: 170, items: [
	                            { text: "Group Three", icon: contextPath.concat("/images/contextmenu/wi0054-16.gif"), alias: "2-2", type: "group", width: 190, items: [
		                            { text: "Group3 Item One", icon: contextPath.concat("/images/contextmenu/wi0062-16.gif"), alias: "3-1", action: menuAction },
		                            { text: "Group3 Item Tow", icon: contextPath.concat("/images/contextmenu/wi0063-16.gif"), alias: "3-2", action: menuAction }
	                            ]
	                            },
	                            { text: "Group Two Item1", icon: contextPath.concat("/images/contextmenu/wi0096-16.gif"), alias: "2-1", action: menuAction },
	                            { text: "Group Two Item1", icon: contextPath.concat("/images/contextmenu/wi0111-16.gif"), alias: "2-3", action: menuAction },
	                            { text: "Group Two Item1", icon: contextPath.concat("/images/contextmenu/wi0122-16.gif"), alias: "2-4", action: menuAction }
                            ]
                            },
                            { type: "splitLine" },
                            { text: "Item Four", icon: contextPath.concat("/images/contextmenu/wi0124-16.gif"), alias: "1-5", action: menuAction },
                            { text: "Group Three", icon: contextPath.concat("/images/contextmenu/wi0062-16.gif"), alias: "1-6", type: "group", width: 180, items: [
	                            { text: "Item One", icon: contextPath.concat("/images/contextmenu/wi0096-16.gif"), alias: "4-1", action: menuAction },
	                            { text: "Item Two", icon: contextPath.concat("/images/contextmenu/wi0122-16.gif"), alias: "4-2", action: menuAction }
                            ]
                            }
                            ], onShow: applyrule,
                onContextMenu: BeforeContextMenu
            };
            function menuAction() {
                if(this.data.alias == '1-1') { 
                	window.print();
                }
                else if(this.data.alias == '1-2') {
	                toggleColumns(true);
                }
            }
            function applyrule(menu) {               
                if (this.id == "target2") {
                    menu.applyrule({ name: "target2",
                        disable: true,
                        items: ["1-2", "2-3", "2-4", "1-6"]
                    });
                }
                else {
                    menu.applyrule({ name: "all",
                        disable: true,
                        items: []
                    });
                }
            }
            function BeforeContextMenu() {
                return this.id != "target3";
            }
            $("#mainData,#target2,#target3").contextmenu(option);

}

function filter2 (clear){
	var phrase = $('#myFilter').val();
	var colIndex = $('#mySelect').val();
	if(clear == 'clear') {
		phrase = ''; $('#myFilter').val('');
		colIndex = ''; $('#mySelect').val('');
	}
	var words = phrase.toLowerCase().split(" ");
	var table = document.getElementById('mainData');
	var ele;
	for (var r = 1; r < table.rows.length; r++){
		if(colIndex == '') {
		ele = table.rows[r].innerHTML.replace(/<[^>]+>/g,"");
		ele = ele.replace(/&nbsp;/g,"");
		}
		else { 
		ele = table.rows[r].cells[colIndex].innerHTML.replace(/<[^>]+>/g,"");
		ele = ele.replace(/&nbsp;/g,"");
		}
        var displayStyle = 'none';
        for (var i = 0; i < words.length; i++) {
		    if (ele.toLowerCase().indexOf(words[i])>=0)
				displayStyle = '';
			else {
				displayStyle = 'none';
				break;
		    }
        }
		table.rows[r].style.display = displayStyle;
	}
}




