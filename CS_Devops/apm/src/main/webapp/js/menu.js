    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // http://interakt.ro/products/demo.php?prodId=7                                                      //
    // Copyright www.interakt.ro 2000 -2001                                                               //
    //                                                                                                    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////

   parentWindow = this;
   lastElement = this;

   fii = new Array();
   layerN = 0;

   function show(l){
     l.style.display = "block";
   }

   function hide(l){
     l.style.display = "none";
   }

   function scrie(l, text){
     l.insertAdjacentHTML("beforeEnd", text);
   }

   function Leiar(x, tata){
    var temp;
     src = "<div id=leiar"+parentWindow.layerN+" class=ieLeiar></div>";
     if(tata == parentWindow){
      document.body.insertAdjacentHTML("beforeEnd", src);
      parentWindow.layerN++;
      return(eval("document.all.leiar"+(parentWindow.layerN-1)));
     }
     else{
      tata.insertAdjacentHTML("beforeEnd", src);
      parentWindow.layerN++;
      return(eval("tata.all.leiar"+(parentWindow.layerN-1)));
     }
   }

   function arrayPush(a, l){
     a[a.length]=l;
   }

   function addMenu(eSusParam, text, exe, w, mw, x, y){
    if(!w){
     w = 60;
    }
    if(!mw){
     mw = 40;
    }
    if(this.fiu){
     return this.fiu.addMenu(0, text, exe, w, mw, x, y);
    }
    var aux;
    if(this == parentWindow){
     if(!x){
      if(this.fii.length != 0){
       aux = this.fii[this.fii.length-1];
       if(eSusParam){
       	x = aux.style.pixelLeft + aux.style.pixelWidth;
       	y = aux.style.pixelTop;
       }
       else{
       	x = aux.style.pixelLeft;
       	y = aux.style.pixelTop + 16;
       }
      }
      else{
       x=0;
       y=0;
      }
     }
     aux = new MenuButton(text, x, y, mw, this);
     aux.eSus = eSusParam;
     show(aux);
     auxx = new Menu(w, this)
     aux.fiu = auxx;
     aux.addMenuItem = addMenuItem;
     aux.addMenu = addMenu;
     aux.addMenuSeparator = addMenuSeparator;
     auxx.tata = aux;
     arrayPush(this.fii, aux);
     show(aux);
    }
    else{
     aux = this.addMenuItem("<table border=0 cellspacing=0 cellpadding=0><tr><td width=100% class=item>"+text+"</td><td valign=center class=item> "+menuNext+" </td></table>", exe);
     auxx = new Menu(w, parentWindow);
     aux.fiu = auxx;
     auxx.tata = aux;
     aux.addMenuItem = addMenuItem;
     aux.addMenu = addMenu;
     aux.addMenuSeparator = addMenuSeparator;
    }
    return aux;
   }

   function addMenuItem(text, exe){
    if(this.fiu){
     return this.fiu.addMenuItem(text, exe);
    }
    var aux;
    aux = new MenuItem(text, this, exe);
    arrayPush(this.fii, aux);
    return aux;
   }

   function addMenuSeparator(ceFel){
     var aux = new MenuSeparator(this.fiu, this.fiu.latsime-2, ceFel);
   }

   function showMenu(l){
    show(l);
    for(i=0;i<l.fii.length;i++){
     show(l.fii[i]);
    }
   }

   function hideFii(fii){
   }

   function Menu(w, tata){
    var aux;
    aux = new Leiar(w, tata);
    aux.fii = new Array();
    aux.style.pixelWidth=w;
    txt = "<table border=0 cellspacing=0 cellpadding=0 bgcolor='"+parentWindow.menuFgColor+"' id=tablou width="+w+"><tr><td colspan=3 height=1><img src='<%=request.getContextPath()%>/nimic.gif' height=1></td></tr><tr><td width=1 height=1><img src='<%=request.getContextPath()%>/nimic.gif' height=1></td><td colspan=2 bgcolor="+parentWindow.menuBgColor+" height=1><img src='<%=request.getContextPath()%>/nimic.gif' height=1></td></tr></table>";
    scrie(aux, txt);
    aux.addMenuItem = parentWindow.addMenuItem;
    aux.addMenu = parentWindow.addMenu;
    aux.addMenuSeparator = addMenuSeparator;
    aux.onmouseover = menuMouseOver;
    aux.onmouseout = menuMouseOut;
    aux.tata = tata;
    aux.tip = "Menu";
    aux.latsime=w;
    return aux;
   }

   function MenuItem(text, tata, exe){
    var aux;
    var w;
    text = "<a class=item>"+text+"</a>";
    var nr = tata.all.tablou.rows.length;
    var tr = tata.all.tablou.insertRow(nr-1);
    var cel = tr.insertCell(0);
    cel.innerHTML = "<img src='<%=request.getContextPath()%>/nimic.gif' width=1 height=1>";
    cel.style.width="1";
    cel.style.height="1";
    var cel1 = tr.insertCell(1);
    cel1.innerHTML = text;
    cel1.style.backgroundColor = parentWindow.itemOffColor;
    cel1.style.width="100%";
    cel = tr.insertCell(2);
    cel.innerHTML = "<img src='<%=request.getContextPath()%>/nimic.gif' width=1 height=1>";
    cel.style.backgroundColor = parentWindow.menuBgColor;
    cel.style.width="1";
    cel.style.height="1";
    if(!(tata.ien)){
     tata.ien = 0;
    }
    cel1.ien = tata.ien;
    tata.ien++;
    aux = cel1;
    aux.onmouseover=menuItemMouseOver;
    aux.onmouseout=menuItemMouseOut;
    aux.onmousedown = click;
    if(exe){
     aux.exe = exe;
    }
    aux.tata = tata;
    aux.tip = "MenuItem";
    return aux;
   }

   function MenuSeparator(tata, x, ceFel){
    var nr = tata.all.tablou.rows.length;
    var tr = tata.all.tablou.insertRow(nr-1);
    var cel = tr.insertCell(0);
    cel.innerHTML = "<img src='<%=request.getContextPath()%>/images/nimic.gif' height=1 width=1>";
    cel.style.width = "1";
    cel.style.height = "1";
    cel = tr.insertCell(1);
    cel.innerHTML = "<img src='<%=request.getContextPath()%>/images/nimic.gif' height=1 width=1>";
    if(ceFel!=0){
    	cel.style.backgroundColor = parentWindow.menuBgColor;
    }else{
    	cel.style.backgroundColor = parentWindow.itemOffColor;
    }
    cel.style.width="100%";
    cel = tr.insertCell(2);
    cel.innerHTML = "<img src='<%=request.getContextPath()%>/images/nimic.gif' height=1 width=1>";
    cel.style.backgroundColor = parentWindow.menuBgColor;
    cel.style.width = "1";
    cel.style.height = "1";
     tr = tata.all.tablou.insertRow(nr);
    cel = tr.insertCell(0);
    cel.innerHTML = "<img src='<%=request.getContextPath()%>/images/nimic.gif' height=1 width=1>";
    cel.style.width = "1";
    cel.style.height = "1"; 
    cel = tr.insertCell(1);
    cel.innerHTML = "<img src='<%=request.getContextPath()%>/images/nimic2.gif' height=1 width=180>";
    if(ceFel==0){
    	cel.style.backgroundColor = parentWindow.itemOffColor;
    }
    cel.style.width="100%";
    cel = tr.insertCell(2);
    cel.innerHTML = "<img src='<%=request.getContextPath()%>/images/nimic.gif' height=1 width=1>";
    cel.style.backgroundColor = parentWindow.menuBgColor;
    cel.style.width = "1";
    cel.style.height = "1";
  }

   function MenuButton(text, x, y, w, tata){
    var aux;
    aux = new Leiar(w, tata);
    aux.style.backgroundColor=parentWindow.itemOffColor;
    aux.style.pixelTop = y;
    aux.style.pixelLeft = x;
    if(w){
      aux.style.pixelWidth = w;
    }
    scrie(aux, "<table border=0 cellspacing=0 cellpadding=0 width="+w+"><tr><td colspan=3 bgcolor="+parentWindow.menuFgColor+" height=1><img src='<%=request.getContextPath()%>/images/nimic.gif' height=1></td></tr><tr><td bgcolor="+parentWindow.menuFgColor+" height=100%  width=1><img src='<%=request.getContextPath()%>/images/nimic.gif' width=1></td><td width=100% align=center class=item>"+text+"</td><td bgcolor="+parentWindow.menuBgColor+" width=1 height=1><img src='<%=request.getContextPath()%>/images/nimic.gif' width=1 height=1></td></tr><tr><td colspan=3 bgcolor="+parentWindow.menuBgColor+" height=1><img src='<%=request.getContextPath()%>/images/nimic.gif' height=1></td></tr></table>");
    aux.onmouseover=menuButtonMouseOver;
    aux.onmouseout=menuButtonMouseOut;
    aux.menuItemMouseOut = menuItemMouseOut;
    aux.menuItemMouseOver = menuItemMouseOver;
    aux.menuMouseOut = menuMouseOut;
    aux.menuMouseOver = menuMouseOver;
    aux.tip = "MenuButton";
    return aux;
   }

   function hideRec(ce, panaUnde){
    if(ce.tip != "MenuButton"){
     if(ce.tata != panaUnde){
      if(ce.fii){
       hideFii(ce.fii);
      }
      hide(ce);
      hideRec(ce.tata, panaUnde);
     }
    }
   }

   function menuItemMouseOver(e){

    if(this.fiu && this.tip=="MenuItem"){
      this.fiu.style.pixelLeft = this.tata.style.pixelLeft + this.tata.clientWidth + parentWindow.newMenuXDepl;
      var a = this.offsetTop;
      if(a==0){
       a=this.tata.clientHeight/(this.tata.ien)*this.ien + 1;
      }
      this.fiu.style.pixelTop = this.tata.style.pixelTop + a - 1 + parentWindow.newMenuYDepl;
    }

    this.style.backgroundColor=parentWindow.itemOnColor;
    if(parentWindow.lastElement != parentWindow){
     if(this.tip == "MenuButton"){
      if(parentWindow.lastElement != this){
       if(parentWindow.lastElement.fiu){
        hideFii(parentWindow.lastElement.fiu.fii);
        hide(parentWindow.lastElement.fiu);
       }
       hideRec(parentWindow.lastElement, this);
      }
     }
     else{
      if(this.tata.tata != parentWindow.lastElement){
       if(parentWindow.lastElement.fiu){
        hideFii(parentWindow.lastElement.fiu.fii);
        hide(parentWindow.lastElement.fiu);
       }
       if(parentWindow.lastElement.tata != this.tata){
        hideRec(parentWindow.lastElement, this.tata);
       }
      }
     }
     parentWindow.lastElement = this;
    }
    if(this.fiu){
     showMenu(this.fiu);
    }
   }

   function menuItemMouseOut(e){
    this.style.backgroundColor=parentWindow.itemOffColor;
    parentWindow.lastElement = this;
   }

   function menuMouseOver(){
    if(parentWindow.toID != -1){
     clearTimeout(parentWindow.toID);
     parentWindow.toID = -1;
    }
   }

   function menuMouseOut(){
    parentWindow.toID = setTimeout("timeout();", 1);
   }

   function timeout(){
    if(parentWindow.lastElement != parentWindow){
     if(parentWindow.lastElement.fiu){
      hideRec(parentWindow.lastElement.fiu, parentWindow);
     }
     else{
      hideRec(parentWindow.lastElement, parentWindow);
     }
    }
    parentWindow.toID = -1;
   }

   function menuButtonMouseOut(){
    if(myStatus == 1){
     this.menuItemMouseOut();
     this.menuMouseOut();
    }
   }

   function menuButtonMouseOver(){
    if(myStatus == 1){
      if(this.eSus){
      	this.fiu.style.pixelLeft = this.style.pixelLeft;
      	this.fiu.style.pixelTop = this.style.pixelTop + this.clientHeight;
      }else{
      	this.fiu.style.pixelLeft = this.style.pixelLeft + this.clientWidth;
      	this.fiu.style.pixelTop = this.style.pixelTop;
      }
     this.menuMouseOver();
     this.menuItemMouseOver();
    }
   }

   function click(){
    timeout();
    if(this.exe){
     eval(this.exe);
    }
    return false;
   }

   function loaded(){
   
   itemOnColor = "#0000ff";
   itemOffColor = "#000000";
   menuBgColor="#000000";
   menuFgColor="#000000";

    status = "Loading menus...";
    myStatus = 0;

    l1=addMenu(1, "Membership", "", 180, 73, 46, 20);
    l1.addMenuSeparator();
    l1.addMenuItem("&nbsp;In Context","window.open('toModule.do?prefix=/membership&page=/viewSummary.do&actionType=context&t='+((new Date()).getTime()),'contentFrame');window.open('toModule.do?prefix=/membership&page=/me_left.jsp','leftNavFrame')");
    l1.addMenuItem("&nbsp;New MEMBERSHIP Search","window.open('toModule.do?prefix=/membership&page=/mePrtySrch.jsp','contentFrame');window.open('toModule.do?prefix=/membership&page=/me_left.jsp','leftNavFrame')");
    l1.addMenuSeparator();

    l2=addMenu(1, "Royalty", "", 180, 49);
    l2.addMenuSeparator();
    l2.addMenuItem("&nbsp;In Context","window.open('toModule.do?prefix=/royalty&page=/royaltySummary.do&actionType=context&t='+((new Date()).getTime()),'contentFrame');window.open('toModule.do?prefix=/royalty&page=/ry_left.jsp','leftNavFrame')");
    l2.addMenuItem("&nbsp;New ROYALTY Search","window.open('toModule.do?prefix=/royalty&page=/ryPartySearch.jsp','contentFrame');window.open('toModule.do?prefix=/royalty&page=/ry_left.jsp','leftNavFrame')");
    l2.addMenuSeparator();
        
    l3=addMenu(1, "Distribution", "", 180, 72);
    l3.addMenuSeparator();
    l3.addMenuItem("&nbsp;In Context","window.open('toModule.do?prefix=/distribution&page=/getDisRunList.do','contentFrame');window.open('toModule.do?prefix=/distribution&page=/ds_left.jsp','leftNavFrame')");
//    l3.addMenuItem("&nbsp;New DISTRIBUTION Search","window.open('distribution/dsHm.jsp','contentFrame');window.open('distribution/ds_left.jsp','leftNavFrame')");
    l3.addMenuSeparator();

//Commented by Jaya Shyam Narayana Lingamchetty on 28 Sep 2003 to just preserve the old one before changing it    
//    l4=addMenu(1, "Usage", "", 180, 48);
//    l4.addMenuSeparator();
//    l4.addMenuItem("&nbsp;In Context","window.open('usage/usHm.jsp&t='+((new Date()).getTime()),'contentFrame');window.open('toModule.do?prefix=/usage&page=/us_left.jsp&t='+((new Date()).getTime()),'leftNavFrame')");
//    l4.addMenuItem("&nbsp;New USAGE Search","window.open('usage/usHm.jsp','contentFrame');window.open('usage/us_left.jsp&t='+((new Date()).getTime()),'leftNavFrame')");
//    l4.addMenuSeparator();
 
    l4=addMenu(1, "Usage", "", 180, 43);
//    l4.addMenuSeparator();
//    l4.addMenuItem("&nbsp;In Context","window.open('toModule.do?prefix=/usage&page=/UsageHome.jsp&t='+((new Date()).getTime()),'contentFrame');window.open('toModule.do?prefix=/usage&page=/us_left.jsp&t='+((new Date()).getTime()),'leftNavFrame')");
//    l4.addMenuItem("&nbsp;New USAGE Search","window.open('toModule.do?prefix=/usage&page=/UsageHome.jsp','contentFrame');window.open('toModule.do?prefix=/usage&page=/us_left.jsp&t='+((new Date()).getTime()),'leftNavFrame')");
//    l4.addMenuSeparator();


//    l5=addMenu(1, "Copyright", "", 180, 68);
//    l5.addMenuSeparator();
//    l5.addMenuItem("&nbsp;WORKS In Context","window.open('wk/wkDtlsSmmryCllsped.jsp','contentFrame');");
//    l5.addMenuItem("&nbsp;New WORKS Search","window.open('wk/wkSrch.jsp','contentFrame');");
//    l5.addMenuSeparator();
//    l5.addMenuItem("&nbsp;AGREEMENTS In Context","window.open('../ag/ag_scr_ag_msr_collapsed.jsp','_top');");
//    l5.addMenuItem("&nbsp;New AGREEMENTS Search","window.open('../ag/ag_scr_hm.jsp','_top');");
//    l5.addMenuSeparator();
//    l5.addMenuItem("&nbsp;MANDATES In Context","window.open('../mn/mnSrchRslts.jsp','_top');");
//    l5.addMenuItem("&nbsp;New MANDATES Search","window.open('../mn/mnSrch.jsp','_top');");
//    l5.addMenuSeparator();


    l5=addMenu(1, "Works", "", 180, 43);
    l5.addMenuSeparator();
    l5.addMenuItem("&nbsp;In Context","window.open('toModule.do?prefix=/works&page=/searchWork.do&actionType=context&t='+((new Date()).getTime()),'contentFrame');window.open('toModule.do?prefix=/works&page=/wk_left.jsp','leftNavFrame')");
    l5.addMenuItem("&nbsp;New WORKS Search","window.open('toModule.do?prefix=/works&page=/wkWorkSearch.jsp','contentFrame');window.open('toModule.do?prefix=/works&page=/wk_left.jsp','leftNavFrame')");
    l5.addMenuSeparator();
    
    l6=addMenu(1, "Agreements", "", 180, 72);
    l6.addMenuSeparator();
    l6.addMenuItem("&nbsp;In Context","window.open('toModule.do?prefix=/agreements&page=/searchAgreements.do&actionType=context&t='+((new Date()).getTime()),'contentFrame');window.open('toModule.do?prefix=/agreements&page=/ag_left.jsp','leftNavFrame')");
    l6.addMenuItem("&nbsp;New AGREEMENTS Search","window.open('toModule.do?prefix=/agreements&page=/start.do&actionType=get','contentFrame');window.open('toModule.do?prefix=/agreements&page=/ag_left.jsp','leftNavFrame')");
    l6.addMenuSeparator();
    
    l7=addMenu(1, "Mandates", "", 180, 60);
    l7.addMenuSeparator();
    l7.addMenuItem("&nbsp;In Context","window.open('toModule.do?prefix=/mandates&page=/searchMandates.do&actionType=context&t='+((new Date()).getTime()),'contentFrame');window.open('toModule.do?prefix=/mandates&page=/mn_left.jsp','leftNavFrame')");
    l7.addMenuItem("&nbsp;New MANDATES Search","window.open('toModule.do?prefix=/mandates&page=/mandatesInit.do&actionType=get','contentFrame');window.open('toModule.do?prefix=/mandates&page=/mn_left.jsp','leftNavFrame')");
    l7.addMenuSeparator();

    l8=addMenu(1, "Inquiry", "", 180, 45);
    l8.addMenuSeparator();
    l8.addMenuItem("&nbsp;In Context","window.open('toModule.do?prefix=/inquiry&page=/SearchInquiry.jsp&inContext=true','contentFrame');window.open('toModule.do?prefix=/inquiry&page=/in_left.jsp','leftNavFrame')");
    l8.addMenuItem("&nbsp;New INQUIRY Search","window.open('toModule.do?prefix=/inquiry&page=/SearchInquiry.jsp','contentFrame');window.open('toModule.do?prefix=/inquiry&page=/in_left.jsp','leftNavFrame')");
    l8.addMenuSeparator();

//    l9=addMenu(1, "Modeling", "", 180, 60);
//    l9.addMenuSeparator();
//    l9.addMenuItem("&nbsp;In Context","window.open('index.jsp','_top');");
//    l9.addMenuItem("&nbsp;New MODELING Search","window.open('index.jsp','_top');");
//    l9.addMenuSeparator();

    l10=addMenu(1, "Adjustments", "", 180, 73);
//    l10.addMenuSeparator();
//    l10.addMenuItem("&nbsp;In Context","window.open('index.jsp','_top');");
//    l10.addMenuItem("&nbsp;New MODELING Search","window.open('index.jsp','_top');");
//    l10.addMenuSeparator();
    
    l11=addMenu(1, "Preferences", "", 180, 73);
    l11.addMenuSeparator();
    l11.addMenuItem("&nbsp;Set Personal Preferences","window.open('toModule.do?prefix=/distribution&page=/getPreferences.do','contentFrame');window.open('toModule.do?prefix=&page=/leftnav.jsp','leftNavFrame')");
    l11.addMenuSeparator();
    
    l12=addMenu(1, "Reports", "", 180, 50);
    //l12.addMenuSeparator();
    //l12.addMenuItem("&nbsp;Set Personal Preferences","window.open('toModule.do?prefix=/distribution&page=/getPreferences.do','contentFrame');window.open('toModule.do?prefix=&page=/leftnav.jsp','leftNavFrame')");
    //l12.addMenuSeparator();
    
    l13=addMenu(1, "Admin", "", 180, 40);
    //l13.addMenuSeparator();
    //l13.addMenuItem("&nbsp;In Context","window.open('welcome.jsp','contentFrame');window.open('toModule.do?prefix=/admin&page=/ad_left.jsp','leftNavFrame')");        
    //l13.addMenuSeparator();
    
    myStatus = 1;
    status = "Loaded menus";
    
    //openPage('intro.html', '1.gif');
	
   }