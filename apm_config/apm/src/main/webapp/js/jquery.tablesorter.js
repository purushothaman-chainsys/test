(function ($) {
    $.extend({
        tablesorter: new function () {
            function benchmark(a, b) {
                log(a + "," + ((new Date).getTime() - b.getTime()) + "ms")
            }
            function log(a) {
                if (typeof console != "undefined" && typeof console.debug != "undefined") {
                    console.log(a)
                } else {}
            }
            function buildParserCache(a, b) { 
                if (a.config.debug) {
                    var c = ""
                }
                if (a.tBodies.length == 0) return;
                var d = a.tBodies[0].rows;
                if (d[0]) {
                    var e = [],
                        f = d[0].cells,
                        g = f.length;
                    for (var h = 0; h < g; h++) {
                        var i = false;
                        if ($.metadata && $(b[h]).metadata() && $(b[h]).metadata().sorter) {
                            i = getParserById($(b[h]).metadata().sorter)
                        } else if (a.config.headers[h] && a.config.headers[h].sorter) {
                            i = getParserById(a.config.headers[h].sorter)
                        }
                        if (!i) {
                            i = detectParserForColumn(a, d, -1, h)
                        }
                        if (a.config.debug) {
                            c += "column:" + h + " parser:" + i.id + "\n"
                        }
                        e.push(i)
                    }
                }
                if (a.config.debug) {
                    log(c)
                }
                return e
            }
            function detectParserForColumn(a, b, c, d) {
                var e = parsers.length,
                    f = false,
                    g = false,
                    h = true;
                while (g == "" && h) {
                    c++;
                    if (b[c]) {
                        f = getNodeFromRowAndCellIndex(b, c, d);
                        g = trimAndGetNodeText(a.config, f);
                        if (a.config.debug) {
                            log("Checking if value was empty on row:" + c)
                        }
                    } else {
                        h = false
                    }
                }
                for (var i = 1; i < e; i++) {
                    if (parsers[i].is(g, a, f)) {
                        return parsers[i]
                    }
                }
                return parsers[0]
            }
            function getNodeFromRowAndCellIndex(a, b, c) {
                return a[b].cells[c]
            }
            function trimAndGetNodeText(a, b) {
                return $.trim(getElementText(a, b))
            }
            function getParserById(a) {
                var b = parsers.length;
                for (var c = 0; c < b; c++) {
                    if (parsers[c].id.toLowerCase() == a.toLowerCase()) {
                        return parsers[c]
                    }
                }
                return false
            }
            function buildCache(a) {
                if (a.config.debug) {
                    var b = new Date
                }
                var c = a.tBodies[0] && a.tBodies[0].rows.length || 0,
                    d = a.tBodies[0].rows[0] && a.tBodies[0].rows[0].cells.length || 0,
                    e = a.config.parsers,
                    f = {
                        row: [],
                        normalized: []
                    };
                for (var g = 0; g < c; ++g) {
                    var h = $(a.tBodies[0].rows[g]),
                        i = [];
                    if (h.hasClass(a.config.cssChildRow)) {
                        f.row[f.row.length - 1] = f.row[f.row.length - 1].add(h);
                        continue
                    }
                    f.row.push(h);
                    for (var j = 0; j < d; ++j) {
                        i.push(e[j].format(getElementText(a.config, h[0].cells[j]), a, h[0].cells[j]))
                    }
                    i.push(f.normalized.length);
                    f.normalized.push(i);
                    i = null
                }
                if (a.config.debug) {
                    benchmark("Building cache for " + c + " rows:", b)
                }
                return f
            }
            function getElementText(a, b) {
                var c = "";
                if (!b) return "";
                if (!a.supportsTextContent) a.supportsTextContent = b.textContent || false;
                if (a.textExtraction == "simple") {
                    if (a.supportsTextContent) {
                        c = b.textContent
                    } else {
                        if (b.childNodes[0] && b.childNodes[0].hasChildNodes()) {
                            c = b.childNodes[0].innerHTML
                        } else {
                            c = b.innerHTML
                        }
                    }
                } else {
                    if (typeof a.textExtraction == "function") {
                        c = a.textExtraction(b)
                    } else {
                        c = $(b).text()
                    }
                }
                return c
            }
            function appendToTable(a, b) {
                if (a.config.debug) {
                    var c = new Date
                }
                var d = b,
                    e = d.row,
                    f = d.normalized,
                    g = f.length,
                    h = f[0].length - 1,
                    i = $(a.tBodies[0]),
                    j = [];
                for (var k = 0; k < g; k++) {
                    var l = f[k][h];
                    j.push(e[l]);
                    if (!a.config.appender) {
                        var m = e[l].length;
                        for (var n = 0; n < m; n++) {
                            i[0].appendChild(e[l][n])
                        }
                    }
                }
                if (a.config.appender) {
                    a.config.appender(a, j)
                }
                j = null;
                if (a.config.debug) {
                    benchmark("Rebuilt table:", c)
                }
                applyWidget(a);
                setTimeout(function () {
                    $(a).trigger("sortEnd")
                }, 0)
            }
            function buildHeaders(a) {
                if (a.config.debug) {
                    var b = new Date
                }
                var c = $.metadata ? true : false;
                var d = computeTableHeaderCellIndexes(a);
                $tableHeaders = $(a.config.selectorHeaders, a).each(function (b) {
                    this.column = d[this.parentNode.rowIndex + "-" + this.cellIndex];
                    this.order = formatSortingOrder(a.config.sortInitialOrder);
                    this.count = this.order;
                    if (checkHeaderMetadata(this) || checkHeaderOptions(a, b)) this.sortDisabled = true;
                    if (checkHeaderOptionsSortingLocked(a, b)) this.order = this.lockedOrder = checkHeaderOptionsSortingLocked(a, b);
                    if (!this.sortDisabled) {
                        var c = $(this).addClass(a.config.cssHeader);
                        if (a.config.onRenderHeader) a.config.onRenderHeader.apply(c)
                    }
                    a.config.headerList[b] = this
                });
                if (a.config.debug) {
                    benchmark("Built headers:", b);
                    log($tableHeaders)
                }
                return $tableHeaders
            }
            function computeTableHeaderCellIndexes(a) {
                var b = [];
                var c = {};
                var d = a.getElementsByTagName("THEAD")[0];
                var e = d.getElementsByTagName("TR");
                for (var f = 0; f < e.length; f++) {
                    var g = e[f].cells;
                    for (var h = 0; h < g.length; h++) {
                        var i = g[h];
                        var j = i.parentNode.rowIndex;
                        var k = j + "-" + i.cellIndex;
                        var l = i.rowSpan || 1;
                        var m = i.colSpan || 1;
                        var n;
                        if (typeof b[j] == "undefined") {
                            b[j] = []
                        }
                        for (var o = 0; o < b[j].length + 1; o++) {
                            if (typeof b[j][o] == "undefined") {
                                n = o;
                                break
                            }
                        }
                        c[k] = n;
                        for (var o = j; o < j + l; o++) {
                            if (typeof b[o] == "undefined") {
                                b[o] = []
                            }
                            var p = b[o];
                            for (var q = n; q < n + m; q++) {
                                p[q] = "x"
                            }
                        }
                    }
                }
                return c
            }
            function checkCellColSpan(a, b, c) {
                var d = [],
                    e = a.tHead.rows,
                    f = e[c].cells;
                for (var g = 0; g < f.length; g++) {
                    var h = f[g];
                    if (h.colSpan > 1) {
                        d = d.concat(checkCellColSpan(a, headerArr, c++))
                    } else {
                        if (a.tHead.length == 1 || h.rowSpan > 1 || !e[c + 1]) {
                            d.push(h)
                        }
                    }
                }
                return d
            }
            function checkHeaderMetadata(a) {
                if ($.metadata && $(a).metadata().sorter === false) {
                    return true
                }
                return false
            }
            function checkHeaderOptions(a, b) {
                if (a.config.headers[b] && a.config.headers[b].sorter === false) {
                    return true
                }
                return false
            }
            function checkHeaderOptionsSortingLocked(a, b) {
                if (a.config.headers[b] && a.config.headers[b].lockedOrder) return a.config.headers[b].lockedOrder;
                return false
            }
            function applyWidget(a) {
                var b = a.config.widgets;
                var c = b.length;
                for (var d = 0; d < c; d++) {
                    getWidgetById(b[d]).format(a)
                }
            }
            function getWidgetById(a) {
                var b = widgets.length;
                for (var c = 0; c < b; c++) {
                    if (widgets[c].id.toLowerCase() == a.toLowerCase()) {
                        return widgets[c]
                    }
                }
            }
            function formatSortingOrder(a) {
                if (typeof a != "Number") {
                    return a.toLowerCase() == "desc" ? 1 : 0
                } else {
                    return a == 1 ? 1 : 0
                }
            }
            function isValueInArray(a, b) {
                var c = b.length;
                for (var d = 0; d < c; d++) {
                    if (b[d][0] == a) {
                        return true
                    }
                }
                return false
            }
            function setHeadersCss(a, b, c, d) {
                b.removeClass(d[0]).removeClass(d[1]);
                var e = [];
                b.each(function (a) {
                    if (!this.sortDisabled) {
                        e[this.column] = $(this)
                    }
                });
                var f = c.length;
                for (var g = 0; g < f; g++) {
                    e[c[g][0]].addClass(d[c[g][1]])
                }
            }
            function fixColumnWidth(a, b) {
                var c = a.config;
                if (c.widthFixed) {
                    var d = $("<colgroup>");
                    $("tr:first td", a.tBodies[0]).each(function () {
                        d.append($("<col>").css("width", $(this).width()))
                    });
                    $(a).prepend(d)
                }
            }
            function updateHeaderSortCount(a, b) {
                var c = a.config,
                    d = b.length;
                for (var e = 0; e < d; e++) {
                    var f = b[e],
                        g = c.headerList[f[0]];
                    g.count = f[1];
                    g.count++
                }
            }
            function multisort(table, sortList, cache) {
                if (table.config.debug) {
                    var sortTime = new Date
                }
                var dynamicExp = "var sortWrapper = function(a,b) {",
                    l = sortList.length;
                for (var i = 0; i < l; i++) {
                    var c = sortList[i][0];
                    var order = sortList[i][1];
                    var s = table.config.parsers[c].type == "text" ? order == 0 ? makeSortFunction("text", "asc", c) : makeSortFunction("text", "desc", c) : order == 0 ? makeSortFunction("numeric", "asc", c) : makeSortFunction("numeric", "desc", c);
                    var e = "e" + i;
                    dynamicExp += "var " + e + " = " + s;
                    dynamicExp += "if(" + e + ") { return " + e + "; } ";
                    dynamicExp += "else { "
                }
                var orgOrderCol = cache.normalized[0].length - 1;
                dynamicExp += "return a[" + orgOrderCol + "]-b[" + orgOrderCol + "];";
                for (var i = 0; i < l; i++) {
                    dynamicExp += "}; "
                }
                dynamicExp += "return 0; ";
                dynamicExp += "}; ";
                if (table.config.debug) {
                    benchmark("Evaling expression:" + dynamicExp, new Date)
                }
                eval(dynamicExp);
                cache.normalized.sort(sortWrapper);
                if (table.config.debug) {
                    benchmark("Sorting on " + sortList.toString() + " and dir " + order + " time:", sortTime)
                }
                return cache
            }
            function makeSortFunction(a, b, c) {
                var d = "a[" + c + "]",
                    e = "b[" + c + "]";
                if (a == "text" && b == "asc") {
                    return "(" + d + " == " + e + " ? 0 : (" + d + " === null ? Number.POSITIVE_INFINITY : (" + e + " === null ? Number.NEGATIVE_INFINITY : (" + d + " < " + e + ") ? -1 : 1 )));"
                } else if (a == "text" && b == "desc") {
                    return "(" + d + " == " + e + " ? 0 : (" + d + " === null ? Number.POSITIVE_INFINITY : (" + e + " === null ? Number.NEGATIVE_INFINITY : (" + e + " < " + d + ") ? -1 : 1 )));"
                } else if (a == "numeric" && b == "asc") {
                    return "(" + d + " === null && " + e + " === null) ? 0 :(" + d + " === null ? Number.POSITIVE_INFINITY : (" + e + " === null ? Number.NEGATIVE_INFINITY : " + d + " - " + e + "));"
                } else if (a == "numeric" && b == "desc") {
                    return "(" + d + " === null && " + e + " === null) ? 0 :(" + d + " === null ? Number.POSITIVE_INFINITY : (" + e + " === null ? Number.NEGATIVE_INFINITY : " + e + " - " + d + "));"
                }
            }
            function makeSortText(a) {
                return "((a[" + a + "] < b[" + a + "]) ? -1 : ((a[" + a + "] > b[" + a + "]) ? 1 : 0));"
            }
            function makeSortTextDesc(a) {
                return "((b[" + a + "] < a[" + a + "]) ? -1 : ((b[" + a + "] > a[" + a + "]) ? 1 : 0));"
            }
            function makeSortNumeric(a) {
                return "a[" + a + "]-b[" + a + "];"
            }
            function makeSortNumericDesc(a) {
                return "b[" + a + "]-a[" + a + "];"
            }
            function sortText(a, b) {
                if (table.config.sortLocaleCompare) return a.localeCompare(b);
                return a < b ? -1 : a > b ? 1 : 0
            }
            function sortTextDesc(a, b) {
                if (table.config.sortLocaleCompare) return b.localeCompare(a);
                return b < a ? -1 : b > a ? 1 : 0
            }
            function sortNumeric(a, b) {
                return a - b
            }
            function sortNumericDesc(a, b) {
                return b - a
            }
            function getCachedSortType(a, b) {
                return a[b].type
            }
            var parsers = [],
                widgets = [];
            this.defaults = {
                cssHeader: "header",
                cssAsc: "headerSortUp",
                cssDesc: "headerSortDown",
                cssChildRow: "expand-child",
                sortInitialOrder: "asc",
                sortMultiSortKey: "shiftKey",
                sortForce: null,
                sortAppend: null,
                sortLocaleCompare: true,
                textExtraction: "simple",
                parsers: {},
                widgets: [],
                widgetZebra: {
                    css: ["tableroweven", "o20"]
                },
                headers: {},
                widthFixed: false,
                cancelSelection: true,
                sortList: [],
                headerList: [],
                dateFormat: "us",
                decimal: "/.|,/g",
                onRenderHeader: null,
                selectorHeaders: "thead th",
                debug: false
            };
            this.benchmark = benchmark;
            this.construct = function (a) {
                return this.each(function () {
                    if (!this.tHead || !this.tBodies) return;
                    var b, c, d, e, f, g = 0,
                        h;
                    this.config = {};
                    f = $.extend(this.config, $.tablesorter.defaults, a);
                    b = $(this);
                    $.data(this, "tablesorter", f);
                    d = buildHeaders(this);
                    this.config.parsers = buildParserCache(this, d);
                    e = buildCache(this);
                    var i = [f.cssDesc, f.cssAsc];
                    fixColumnWidth(this);
                    d.click(function (a) {
                        var c = b[0].tBodies[0] && b[0].tBodies[0].rows.length || 0;
                        if (!this.sortDisabled && c > 0) {
                            b.trigger("sortStart");
                            var g = $(this);
                            var h = this.column;
                            this.order = this.count++ % 2;
                            if (this.lockedOrder) this.order = this.lockedOrder;
                            if (!a[f.sortMultiSortKey]) {
                                f.sortList = [];
                                if (f.sortForce != null) {
                                    var j = f.sortForce;
                                    for (var k = 0; k < j.length; k++) {
                                        if (j[k][0] != h) {
                                            f.sortList.push(j[k])
                                        }
                                    }
                                }
                                f.sortList.push([h, this.order])
                            } else {
                                if (isValueInArray(h, f.sortList)) {
                                    for (var k = 0; k < f.sortList.length; k++) {
                                        var l = f.sortList[k],
                                            m = f.headerList[l[0]];
                                        if (l[0] == h) {
                                            m.count = l[1];
                                            m.count++;
                                            l[1] = m.count % 2
                                        }
                                    }
                                } else {
                                    f.sortList.push([h, this.order])
                                }
                            }
                            setTimeout(function () {
                                setHeadersCss(b[0], d, f.sortList, i);
                                appendToTable(b[0], multisort(b[0], f.sortList, e))
                            }, 1);
                            return false
                        }
                    }).mousedown(function () {
                        if (f.cancelSelection) {
                            this.onselectstart = function () {
                                return false
                            };
                            return false
                        }
                    });
                    b.bind("update", function () {
                        var a = this;
                        setTimeout(function () {
                            a.config.parsers = buildParserCache(a, d);
                            e = buildCache(a)
                        }, 1)
                    }).bind("updateCell", function (a, b) {
                        var c = this.config;
                        var d = [b.parentNode.rowIndex - 1, b.cellIndex];
                        e.normalized[d[0]][d[1]] = c.parsers[d[1]].format(getElementText(c, b), b)
                    }).bind("sorton", function (a, b) {
                        $(this).trigger("sortStart");
                        f.sortList = b;
                        var c = f.sortList;
                        updateHeaderSortCount(this, c);
                        setHeadersCss(this, d, c, i);
                        appendToTable(this, multisort(this, c, e))
                    }).bind("appendCache", function () {
                        appendToTable(this, e)
                    }).bind("applyWidgetId", function (a, b) {
                        getWidgetById(b).format(this)
                    }).bind("applyWidgets", function () {
                        applyWidget(this)
                    });
                    if ($.metadata && $(this).metadata() && $(this).metadata().sortlist) {
                        f.sortList = $(this).metadata().sortlist
                    }
                    if (f.sortList.length > 0) {
                        b.trigger("sorton", [f.sortList])
                    }
                    applyWidget(this)
                })
            };
            this.addParser = function (a) {
                var b = parsers.length,
                    c = true;
                for (var d = 0; d < b; d++) {
                    if (parsers[d].id.toLowerCase() == a.id.toLowerCase()) {
                        c = false
                    }
                }
                if (c) {
                    parsers.push(a)
                }
            };
            this.addWidget = function (a) {
                widgets.push(a)
            };
            this.formatFloat = function (a) {
                var b = parseFloat(a);
                return isNaN(b) ? 0 : b
            };
            this.formatInt = function (a) {
                var b = parseInt(a);
                return isNaN(b) ? 0 : b
            };
            this.isDigit = function (a, b) {
                return /^[-+]?\d*$/.test($.trim(a.replace(/[,.']/g, "")))
            };
            this.clearTableBody = function (a) {
                if ($.browser.msie) {
                    function b() {
                        while (this.firstChild) this.removeChild(this.firstChild)
                    }
                    b.apply(a.tBodies[0])
                } else {
                    a.tBodies[0].innerHTML = ""
                }
            }
        }
    });
    $.fn.extend({
        tablesorter: $.tablesorter.construct
    });
    var ts = $.tablesorter;
    ts.addParser({
        id: "text",
        is: function (a) {
            return true
        },
        format: function (a) {
            return $.trim(a.toLocaleLowerCase())
        },
        type: "text"
    });
    ts.addParser({
        id: "digit",
        is: function (a, b) {
            var c = b.config;
            return $.tablesorter.isDigit(a, c)
        },
        format: function (a) {
        	if (a == null || $.trim(a) == "") {
                a = Number.NEGATIVE_INFINITY
            }
        	else {
        		a = a.replace(/&nbsp;/g,"").replace(/,/g,"");
        	}
            return $.tablesorter.formatFloat(a)
        },
        type: "numeric"
    });
    ts.addParser({
        id: "currency",
        is: function (a) {
            return /^[-$,Â£$â‚¬?.]/.test(a)
        },
        format: function (a) {
            if (a == null || $.trim(a) == "") {
                a = Number.NEGATIVE_INFINITY
            } else {
                a = a.replace(/,/g, "")
            }
            if (a == Number.NEGATIVE_INFINITY) {
                return $.tablesorter.formatFloat(a)
            } else {
                return $.tablesorter.formatFloat(a.replace(new RegExp(/[Â£$â‚¬]/g), ""))
            }
        },
        type: "numeric"
    });
    ts.addParser({
        id: "ipAddress",
        is: function (a) {
            return /^\d{2,3}[\.]\d{2,3}[\.]\d{2,3}[\.]\d{2,3}$/.test(a)
        },
        format: function (a) {
            var b = a.split("."),
                c = "",
                d = b.length;
            for (var e = 0; e < d; e++) {
                var f = b[e];
                if (f.length == 2) {
                    c += "0" + f
                } else {
                    c += f
                }
            }
            return $.tablesorter.formatFloat(c)
        },
        type: "numeric"
    });
    ts.addParser({
        id: "url",
        is: function (a) {
            return /^(https?|ftp|file):\/\/$/.test(a)
        },
        format: function (a) {
            return jQuery.trim(a.replace(new RegExp(/(https?|ftp|file):\/\//), ""))
        },
        type: "text"
    });
    ts.addParser({
        id: "isoDate",
        is: function (a) {
            return /^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/.test(a)
        },
        format: function (a) {
            return $.tablesorter.formatFloat(a != "" ? (new Date(a.replace(new RegExp(/-/g), "/"))).getTime() : "0")
        },
        type: "numeric"
    });
    ts.addParser({
        id: "percent",
        is: function (a) {
            return /\%$/.test($.trim(a))
        },
        format: function (a) {
            return $.tablesorter.formatFloat(a.replace(new RegExp(/%/g), ""))
        },
        type: "numeric"
    });
    ts.addParser({
        id: "usLongDate",
        is: function (a) {
            return a.match(new RegExp(/\d{1,2}[\/\-]\d{1,2}[\/\-]\d{2,4} ([0-2]?[0-9]:[0-5][0-9]:[0-5][0-9])/)) || a.match(new RegExp(/\d{1,2}[\/\-]\d{1,2}[\/\-]\d{2,4} ([0-2]?[0-9]:[0-5][0-9])/))
        },
        format: function (a) {
    		a = a.replace(/&nbsp;/g,"");
            return $.tablesorter.formatFloat((new Date(a)).getTime())
        },
        type: "numeric"
    });
    ts.addParser({
        id: "shortDate",
        is: function (a) {
            return /\d{1,2}[\/]\d{1,2}[\/]\d{2,4}/.test(a)
        },
        format: function (a, b) {
            var c = b.config;
            a = a.replace(/\-/g, "/");
            if (c.dateFormat == "us") {
                a = a.replace(/(\d{1,2})[\/\-](\d{1,2})[\/\-](\d{4})/, "$3$1$2")
            } else if (c.dateFormat == "uk") {
                a = a.replace(/(\d{1,2})[\/\-](\d{1,2})[\/\-](\d{4})/, "$3/$2/$1")
            } else if (c.dateFormat == "dd/mm/yy" || c.dateFormat == "dd-mm-yy") {
                a = a.replace(/(\d{1,2})[\/\-](\d{1,2})[\/\-](\d{2})/, "$1/$2/$3")
            }
            if (c.dateFormat == "us") {
                return $.tablesorter.formatFloat(a)
            } else {
                return $.tablesorter.formatFloat((new Date(a)).getTime())
            }
        },
        type: "numeric"
    });
    ts.addParser({
        id: "time",
        is: function (a) {
            return /^(([0-2]?[0-9]:[0-5][0-9])|([0-1]?[0-9]:[0-5][0-9]\s(am|pm)))$/.test(a)
        },
        format: function (a) {
            return $.tablesorter.formatFloat((new Date("2000/01/01 " + a)).getTime())
        },
        type: "numeric"
    });
    ts.addParser({
        id: "metadata",
        is: function (a) {
            return false
        },
        format: function (a, b, c) {
            var d = b.config,
                e = !d.parserMetadataName ? "sortValue" : d.parserMetadataName;
            return $(c).metadata()[e]
        },
        type: "numeric"
    });
    ts.addWidget({
        id: "zebra",
        format: function (a) {
            if (a.config.debug) {
                var b = new Date
            }
            var c, d = -1,
                e;
            $("tr:visible", a.tBodies[0]).each(function (b) {
                c = $(this);
                if (!c.hasClass(a.config.cssChildRow)) d++;
                e = d % 2 == 0;
                c.removeClass(a.config.widgetZebra.css[e ? 0 : 1]).addClass(a.config.widgetZebra.css[e ? 1 : 0])
            });
            if (a.config.debug) {
                $.tablesorter.benchmark("Applying Zebra widget", b)
            }
        }
    })
})(jQuery)