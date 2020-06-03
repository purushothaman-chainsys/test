(function ($) {

	$.extend($.ui, { yearquarterpicker: { version: "@VERSION" } });

	var PROP_NAME = 'yearquarterpicker';
	var dpuuid = new Date().getTime();
	var instActive;
	function Yearquarterpicker() {
		this.uuid = 0;
		this._curInst = null; // The current instance in use
		this._disabledInputs = []; // List of date picker inputs that have been disabled
		this._yearquarterpickerShowing = false; // True if the popup picker is showing , false if not
		this._mainDivId = 'ui-yearquarterpicker-div'; // The ID of the main yearquarterpicker division
		this._triggerClass = 'ui-yearquarterpicker-trigger'; // The name of the trigger marker class
		this._dialogClass = 'ui-yearquarterpicker-dialog'; // The name of the dialog marker class
		this.regional = []; // Available regional settings, indexed by language code
		this.regional[''] = { // Default regional settings
			prevText: 'Prev', // Display text for previous month link
			nextText: 'Next', // Display text for next month link
			monthNames: ['January','February','March','April','May','June',
				'July','August','September','October','November','December'], // Names of months for drop-down and formatting
			monthNamesShort: ['Mar', 'Jun', 'Sep', 'Dec'], // For formatting
			dateFormat: 'yymm',
			yearSuffix: '' // Additional text to append to the year in the month headers
		};
		this._defaults = { // Global defaults for all the date picker instances
			showOn: 'focus', // 'focus' for popup on focus,
				// 'button' for trigger button, or 'both' for either
			showAnim: 'fadeIn', // Name of jQuery animation for popup
			buttonText: '...', // Text for trigger button
			buttonImage: '', // URL for trigger button image
			changeYear: false, // True if year can be selected directly, false if only prev/next
			yearRange: 'c-10:c+10', // Range of years to display in drop-down,
				// either relative to today's year (-nn:+nn), relative to currently displayed year
				// (c-nn:c+nn), absolute (nnnn:nnnn), or a combination of the above (nnnn:-n)
			beforeShow: null, // Function that takes an input field and
				// returns a set of custom settings for the date picker
			onSelect: null, // Define a callback function when a date is selected
			onChangeYear: null, // Define a callback function when the year is changed
			onClose: null, // Define a callback function when the yearquarterpicker is closed
			stepYears: 1, // Number of months to step back/forward
			altField: '', // Selector for an alternate field to store selected dates into
			altFormat: '', // The date format to use for the alternate field
			disabled: false, // The initial disabled state
			dateFormat: 'yymm'
		};
		$.extend(this._defaults, this.regional['']);
		this.dpDiv = bindHover($('<div id="' + this._mainDivId + '" class="ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all"></div>'));
	}
	
	$.extend(Yearquarterpicker.prototype, {
		/* Class name added to elements to indicate already configured with a date picker. */
		markerClassName: 'hasYearquarterpicker',

		/* Debug logging (if enabled). */
		log: function () {
			if (this.debug)
				console.log.apply('', arguments);
		},
		
		_widgetYearquarterpicker: function() {
			return this.dpDiv;
		},
		_getInst: function(target) {
			try {
				return $.data(target, PROP_NAME);
			}
			catch (err) {
				throw 'Missing instance data for this yearquarterpicker';
			}
		},

		_get: function(inst, name) {
			return inst.settings[name] !== undefined ?
				inst.settings[name] : this._defaults[name];
		},
		_attachYearquarterpicker: function(target, settings) {
			var inlineSettings = null;
			for (var attrName in this._defaults) {
				var attrValue = target.getAttribute('month:' + attrName);
				if (attrValue) {
					inlineSettings = inlineSettings || {};
					try {
						inlineSettings[attrName] = eval(attrValue);
					} catch (err) {
						inlineSettings[attrName] = attrValue;
					}
				}
			}
			var nodeName = target.nodeName.toLowerCase();
			if (!target.id) {
				this.uuid += 1;
				target.id = 'dp' + this.uuid;
			}
			var inst = this._newInst($(target));
			inst.settings = $.extend({}, settings || {}, inlineSettings || {});
			if (nodeName == 'input') {
				this._connectYearquarterpicker(target, inst);
			}
		},

		/* Create a new instance object. */
		_newInst: function(target) {
			var id = target[0].id.replace(/([^A-Za-z0-9_-])/g, '\\\\$1'); // escape jQuery meta chars
			return {id: id, input: target, // associated target
				selectedDay: 0, selectedMonth: 0, selectedYear: 0, // current selection
				drawMonth: 0, drawYear: 0, // month being drawn
				dpDiv: this.dpDiv // presentation div
			};
		},

		/* Attach the date picker to an input field. */
		_connectYearquarterpicker: function(target, inst) {
			var input = $(target);
			inst.append = $([]);
			inst.trigger = $([]);
			if (input.hasClass(this.markerClassName))
				return;
			this._attachments(input, inst);
			input.addClass(this.markerClassName).keydown(this._doKeyDown).
				keypress(this._doKeyPress).keyup(this._doKeyUp).
				bind("setData.yearquarterpicker", function(event, key, value) {
					inst.settings[key] = value;
				}).bind("getData.yearquarterpicker", function(event, key) {
					return this._get(inst, key);
				});
			$.data(target, PROP_NAME, inst);
			if( inst.settings.disabled ) {
				this._disableYearquarterpicker( target );
			}
		},

		_attachments: function(input, inst) {
			input.unbind('focus', this._showYearquarterpicker);
			if (inst.trigger)
				inst.trigger.remove();
			var showOn = this._get(inst, 'showOn');
			if (showOn == 'focus' || showOn == 'both') // pop-up month picker when in the marked field
				input.focus(this._showYearquarterpicker);
			if (showOn == 'button' || showOn == 'both') { // pop-up month picker when button clicked
				var buttonText = this._get(inst, 'buttonText');
				var buttonImage = this._get(inst, 'buttonImage');
				inst.trigger = $('<img/>').addClass(this._triggerClass).
						attr({ src: buttonImage, alt: buttonText, title: buttonText });
				input['after'](inst.trigger);
				inst.trigger.click(function() {
					if ($.yearquarterpicker._yearquarterpickerShowing && $.yearquarterpicker._lastInput == input[0])
						$.yearquarterpicker._hideYearquarterpicker();
					else
						$.yearquarterpicker._showYearquarterpicker(input[0]);
					return false;
				});
			}
		},
		_checkExternalClick: function(event) {
			if (!$.yearquarterpicker._curInst)
				return;

			var $target = $(event.target),
				inst = $.yearquarterpicker._getInst($target[0]);

			if ( ( ( $target[0].id != $.yearquarterpicker._mainDivId &&
					$target.parents('#' + $.yearquarterpicker._mainDivId).length == 0 &&
					!$target.hasClass($.yearquarterpicker.markerClassName) &&
					!$target.hasClass($.yearquarterpicker._triggerClass) &&
					$.yearquarterpicker._yearquarterpickerShowing ) ) ||
				( $target.hasClass($.yearquarterpicker.markerClassName) && $.yearquarterpicker._curInst != inst ) )
				$.yearquarterpicker._hideYearquarterpicker();
		},
		_showYearquarterpicker: function(input) {
			input = input.target || input;
			if (input.nodeName.toLowerCase() != 'input') // find from button/image trigger
				input = $('input', input.parentNode)[0];
			if ($.yearquarterpicker._isDisabledYearquarterpicker(input) || $.yearquarterpicker._lastInput == input) // already here
				return;
			var inst = $.yearquarterpicker._getInst(input);
			if ($.yearquarterpicker._curInst && $.yearquarterpicker._curInst != inst) {
				$.yearquarterpicker._curInst.dpDiv.stop(true, true);
				if ( inst && $.yearquarterpicker._yearquarterpickerShowing ) {
					$.yearquarterpicker._hideYearquarterpicker( $.yearquarterpicker._curInst.input[0] );
				}
			}
			var beforeShow = $.yearquarterpicker._get(inst, 'beforeShow');
			var beforeShowSettings = beforeShow ? beforeShow.apply(input, [input, inst]) : {};
			if(beforeShowSettings === false){
				//false
				return;
			}
			extendRemove(inst.settings, beforeShowSettings);
			inst.lastVal = null;
			$.yearquarterpicker._lastInput = input;
			/*
			alert('manoj ccccccccccccurrr instance setting ' + $.yearquarterpicker._get(inst, 'dateFormat'));
			*/
			$.yearquarterpicker._setDateFromField(inst);
		
			if ($.yearquarterpicker._inDialog) // hide cursor
				input.value = '';
			if (!$.yearquarterpicker._pos) { // position below input
				$.yearquarterpicker._pos = $.yearquarterpicker._findPos(input);
				$.yearquarterpicker._pos[1] += input.offsetHeight; // add the height
			}
			var isFixed = false;
			$(input).parents().each(function() {
				isFixed |= $(this).css('position') == 'fixed';
				return !isFixed;
			});
			if (isFixed && $.browser.opera) {
				$.yearquarterpicker._pos[0] -= document.documentElement.scrollLeft;
				$.yearquarterpicker._pos[1] -= document.documentElement.scrollTop;
			}
			var offset = {left: $.yearquarterpicker._pos[0], top: $.yearquarterpicker._pos[1]};
			$.yearquarterpicker._pos = null;
			inst.dpDiv.empty();
			inst.dpDiv.css({position: 'absolute', display: 'block', top: '-1000px'});
			$.yearquarterpicker._updateYearquarterpicker(inst);
			offset = $.yearquarterpicker._checkOffset(inst, offset, isFixed);
			inst.dpDiv.css({position: ($.yearquarterpicker._inDialog && $.blockUI ?
				'static' : (isFixed ? 'fixed' : 'absolute')), display: 'none',
				left: offset.left + 'px', top: offset.top + 'px'});
			var showAnim = $.yearquarterpicker._get(inst, 'showAnim');
			var duration = $.yearquarterpicker._get(inst, 'duration');
			var postProcess = function() {
				var cover = inst.dpDiv.find('iframe.ui-yearquarterpicker-cover'); // IE6- only
				if( !! cover.length ){
					var borders = $.yearquarterpicker._getBorders(inst.dpDiv);
					cover.css({left: -borders[0], top: -borders[1],
						width: inst.dpDiv.outerWidth(), height: inst.dpDiv.outerHeight()});
				}
			};
			inst.dpDiv.zIndex($(input).zIndex()+1);
			$.yearquarterpicker._yearquarterpickerShowing = true;

			if ($.effects && $.effects[showAnim])
				inst.dpDiv.show(showAnim, $.yearquarterpicker._get(inst, 'showOptions'), duration, postProcess);
			else
				inst.dpDiv[showAnim || 'show']((showAnim ? duration : null), postProcess);
			if (!showAnim || !duration)
				postProcess();
			if (inst.input.is(':visible') && !inst.input.is(':disabled'))
				inst.input.focus();
			$.yearquarterpicker._curInst = inst;
		},

		_updateYearquarterpicker: function(inst) {
		
			var self = this;
			self.maxRows = 2;
			var borders = $.yearquarterpicker._getBorders(inst.dpDiv);
			instActive = inst; 
			inst.dpDiv.empty().append(this._generateHTML(inst));
			var cover = inst.dpDiv.find('iframe.ui-yearquarterpicker-cover'); // IE6- only
			if( !!cover.length ){
				cover.css({left: -borders[0], top: -borders[1], width: inst.dpDiv.outerWidth(), height: inst.dpDiv.outerHeight()})
			}
			inst.dpDiv.find('.' + this._dayOverClass + ' a').mouseover();

			if (inst == $.yearquarterpicker._curInst && $.yearquarterpicker._yearquarterpickerShowing && inst.input &&
					inst.input.is(':visible') && !inst.input.is(':disabled') && inst.input[0] != document.activeElement)
				inst.input.focus(); 
			if( inst.yearshtml ){
				var origyearshtml = inst.yearshtml;
				setTimeout(function(){
					if( origyearshtml === inst.yearshtml && inst.yearshtml ){
						inst.dpDiv.find('select.ui-yearquarterpicker-year:first').replaceWith(inst.yearshtml);
					}
					origyearshtml = inst.yearshtml = null;
				}, 0);
			}
		},

		_triggerOnClose: function(inst) {
			var onClose = this._get(inst, 'onClose');
			if (onClose)
				onClose.apply((inst.input ? inst.input[0] : null),
							  [(inst.input ? inst.input.val() : ''), inst]);
		},

		/* Hide the month picker from view.
		   @param  input  element - the input field attached to the month picker */
		_hideYearquarterpicker: function(input) {
			var inst = this._curInst;
			if (!inst || (input && inst != $.data(input, PROP_NAME)))
				return;
			if (this._yearquarterpickerShowing) {
				var showAnim = this._get(inst, 'showAnim');
				var duration = this._get(inst, 'duration');
				var postProcess = function() {
					$.yearquarterpicker._tidyDialog(inst);
					this._curInst = null;
				};

				if ( $.effects && $.effects[ showAnim ] )
					inst.dpDiv.hide(showAnim, $.yearquarterpicker._get(inst, 'showOptions'), duration, postProcess);
				else
					inst.dpDiv[(showAnim == 'slideDown' ? 'slideUp' :
						(showAnim == 'fadeIn' ? 'fadeOut' : 'hide'))]((showAnim ? duration : null), postProcess);
				if (!showAnim)
					postProcess();
				$.yearquarterpicker._triggerOnClose(inst);
				this._yearquarterpickerShowing = false;
				this._lastInput = null;
				if (this._inDialog) {
					this._dialogInput.css({ position: 'absolute', left: '0', top: '-100px' });
					if ($.blockUI) {
						$.unblockUI();
						$('body').append(this.dpDiv);
					}
				}
				this._inDialog = false;
			}
		},

		_isDisabledYearquarterpicker: function(target) {
			if (!target) {
				return false;
			}
			for (var i = 0; i < this._disabledInputs.length; i++) {
				if (this._disabledInputs[i] == target)
					return true;
			}
			return false;
		},
		
		_tidyDialog: function(inst) {
			inst.dpDiv.removeClass(this._dialogClass).unbind('.ui-yearquarterpicker-calendar');
		},
		_getBorders: function(elem) {
			var convert = function(value) {
				return {thin: 1, medium: 2, thick: 3}[value] || value;
			};
			return [parseFloat(convert(elem.css('border-left-width'))),
				parseFloat(convert(elem.css('border-top-width')))];
		},

		_checkOffset: function(inst, offset, isFixed) {
			var dpWidth = inst.dpDiv.outerWidth();
			var dpHeight = inst.dpDiv.outerHeight();
			var inputWidth = inst.input ? inst.input.outerWidth() : 0;
			var inputHeight = inst.input ? inst.input.outerHeight() : 0;
			var viewWidth = document.documentElement.clientWidth + $(document).scrollLeft();
			var viewHeight = document.documentElement.clientHeight + $(document).scrollTop();

			offset.left -= (isFixed && offset.left == inst.input.offset().left) ? $(document).scrollLeft() : 0;
			offset.top -= (isFixed && offset.top == (inst.input.offset().top + inputHeight)) ? $(document).scrollTop() : 0;

			// now check if yearquarterpicker is showing outside window viewport - move to a better place if so.
			offset.left -= Math.min(offset.left, (offset.left + dpWidth > viewWidth && viewWidth > dpWidth) ?
				Math.abs(offset.left + dpWidth - viewWidth) : 0);
			offset.top -= Math.min(offset.top, (offset.top + dpHeight > viewHeight && viewHeight > dpHeight) ?
				Math.abs(dpHeight + inputHeight) : 0);

			return offset;
		},

		/* Find an object's position on the screen. */
		_findPos: function(obj) {
			var inst = this._getInst(obj);
			while (obj && (obj.type == 'hidden' || obj.nodeType != 1 || $.expr.filters.hidden(obj))) {
				obj = obj['nextSibling'];
			}
			var position = $(obj).offset();
			return [position.left, position.top];
		},
		
		/* Adjust one of the month sub-fields. */
		_adjustDate: function(id, offset, period) {
			var target = $(id);
			var inst = this._getInst(target[0]);
			if (this._isDisabledYearquarterpicker(target[0])) {
				return;
			}
			this._adjustInstDate(inst, offset, period);
			this._updateYearquarterpicker(inst);
		},
		
		/* Adjust one of the date sub-fields. */
		_adjustInstDate: function(inst, offset, period) {
			var year = inst.drawYear + (period == 'Y' ? offset : 0);
			var month = inst.drawMonth + (period == 'M' ? offset : 0);
			var day = 1;
			var date = this._restrictMinMax(inst,
				this._daylightSavingAdjust(new Date(year, month, day)));
			inst.drawMonth = inst.selectedMonth = date.getMonth();
			inst.drawYear = inst.selectedYear = date.getFullYear();
			if (period == 'M' || period == 'Y')
				this._notifyChange(inst);
		},
		
		/* Notify change of month/year. */
		_notifyChange: function(inst) {
			var onChange = this._get(inst, 'onChangeYear');
			if (onChange)
				onChange.apply((inst.input ? inst.input[0] : null),
					[inst.selectedYear + 1, inst]);
		},
		
		/* Enable the date picker to a jQuery selection.
		   @param  target    element - the target input field or division or span */
		_enableYearquarterpicker: function(target) {
			var $target = $(target);
			var inst = $.data(target, PROP_NAME);
			if (!$target.hasClass(this.markerClassName)) {
				return;
			}
			var nodeName = target.nodeName.toLowerCase();
			if (nodeName == 'input') {
				target.disabled = false;
				inst.trigger.filter('button').
					each(function() { this.disabled = false; }).end().
					filter('img').css({opacity: '1.0', cursor: ''});
			}
			this._disabledInputs = $.map(this._disabledInputs,
				function(value) { return (value == target ? null : value); }); // delete entry
		},

		/* Disable the month picker to a jQuery selection.
		   @param  target    element - the target input field or division or span */
		_disableYearquarterpicker: function(target) {
			var $target = $(target);
			var inst = $.data(target, PROP_NAME);
			if (!$target.hasClass(this.markerClassName)) {
				return;
			}
			var nodeName = target.nodeName.toLowerCase();
			if (nodeName == 'input') {
				target.disabled = true;
				inst.trigger.filter('button').
					each(function() { this.disabled = true; }).end().
					filter('img').css({opacity: '0.5', cursor: 'default'});
			}
			this._disabledInputs = $.map(this._disabledInputs,
				function(value) { return (value == target ? null : value); }); // delete entry
			this._disabledInputs[this._disabledInputs.length] = target;
		},
		
		/* Generate the HTML for the current state of the date picker. */
		_generateHTML: function(inst) {
			var today = new Date();
			today = this._daylightSavingAdjust(
				new Date(today.getFullYear(), today.getMonth(), today.getDate())); // clear time
			var currentDate = this._daylightSavingAdjust((!inst.currentMonth ? new Date(9999, 9, 9) :
				new Date(inst.currentYear, inst.currentMonth, 1)));
			var html = '';
			var year = currentDate && currentDate.year ? currentDate.year : 2011;
			var prevText = this._get(inst, 'prevText');
			var nextText = this._get(inst, 'prevText');
			var stepYears = this._get(inst, 'stepYears');
			var monthNames = this._get(inst, 'monthNames');
			var monthNamesShort = this._get(inst, 'monthNamesShort');
			var drawYear = inst.drawYear;

				var prev = '<a class="ui-datepicker-prev ui-corner-all" onclick="MP_jQuery_' + dpuuid +
					'.yearquarterpicker._adjustDate(\'#' + inst.id + '\', -' + stepYears + ', \'Y\');"' +
					' title="' + prevText + '"><span class="ui-icon ui-icon-circle-triangle-w">' + prevText + '</span></a>';
				var next = '<a class="ui-datepicker-next ui-corner-all" onclick="MP_jQuery_' + dpuuid +
					'.yearquarterpicker._adjustDate(\'#' + inst.id + '\', +' + stepYears + ', \'Y\');"' +
					' title="' + nextText + '"><span class="ui-icon ui-icon-circle-triangle-e">' + nextText + '</span></a>';

				html += '<div class="ui-datepicker-header ui-widget-header ui-helper-clearfix ui-corner-all">' +
					prev + next + 
					this._generateYearHeader(inst, drawYear, monthNames, monthNamesShort) + // draw month headers
					'</div><table class="ui-datepicker-calendar"><tbody>';
			
			// mount months table
			for(var i=0; i<=3; i++){
				if (i % 2 === 0) {
					html += '<tr>';
				}
				qtrNum = 0;
				if(i==0) {qtrNum=2;}
				else if(i==1) {qtrNum=5;}
				else if(i==2) {qtrNum=8;}
				else if(i==3) {qtrNum=11;}
				html += '<td style="padding:1px;cursor:default;" data-month=' + i + '>'
					+ '<a  style="text-align: center;" class="ui-state-default'
					+ (drawYear == inst.currentYear && i == inst.currentMonth && 'yyQ' != $.yearquarterpicker._get(inst, 'dateFormat') ? ' ui-state-active' : '') // highlight selected month
					+ (drawYear == today.getFullYear() && i == today.getMonth() ? ' ui-state-highlight' : '') // highlight today (if different)
					+ '" onclick="MP_jQuery_' + dpuuid + '.yearquarterpicker._selectMonth(\'#'
					+ inst.id + '\',' + drawYear + ', ' + qtrNum + ');return false;" actionshref="#">' + (inst.settings && inst.settings.monthNamesShort ? inst.settings.monthNamesShort[i] : this._defaults.monthNamesShort[i]) + '</a>' + '</td>'; // display selectable date
				
				if (i % 2 === 2) {
					html += '</tr>';
				}
			}
			html += '</tbody></table>';

			return html;
		},
			
		/* Generate the month and year header. */
		_generateYearHeader: function(inst, drawYear, monthNames, monthNamesShort) {
			var changeYear = this._get(inst, 'changeYear');
			var html = '<div class="ui-datepicker-title">';
			// year selection
			if ( !inst.yearshtml ) {
				inst.yearshtml = '';
				// determine range of years to display
				var years = this._get(inst, 'yearRange').split(':');
				var thisYear = new Date().getFullYear();
				var determineYear = function(value) {
					var year = (value.match(/c[+-].*/) ? drawYear + parseInt(value.substring(1), 10) :
						(value.match(/[+-].*/) ? thisYear + parseInt(value, 10) :
						parseInt(value, 10)));
					return (isNaN(year) ? thisYear : year);
				};
				var year = determineYear(years[0]);
				var endYear = Math.max(year, determineYear(years[1] || ''));
				
				inst.yearshtml += '<select class="ui-datepicker-year" ' +
					'onchange="MP_jQuery_' + dpuuid + '.yearquarterpicker._selectYear(\'#' + inst.id + '\', this, \'Y\');" ' +
					'>';
				for (; year <= endYear; year++) {
					inst.yearshtml += '<option value="' + year + '"' +
						(year == drawYear ? ' selected="selected"' : '') +
						'>' + year + '</option>';
				}
				inst.yearshtml += '</select>';
				
				html += inst.yearshtml;
				inst.yearshtml = null;
			}
			html += this._get(inst, 'yearSuffix');
			html += '</div>'; // Close yearquarterpicker_header
			return html;
		},
		
		/* Provide the configuration settings for formatting/parsing. */
		_getFormatConfig: function(inst) {
			var shortYearCutoff = this._get(inst, 'shortYearCutoff');
			shortYearCutoff = (typeof shortYearCutoff != 'string' ? shortYearCutoff :
				new Date().getFullYear() % 100 + parseInt(shortYearCutoff, 10));
			return {shortYearCutoff: shortYearCutoff,
				dayNamesShort: this._get(inst, 'dayNamesShort'), dayNames: this._get(inst, 'dayNames'),
				monthNamesShort: this._get(inst, 'monthNamesShort'), monthNames: this._get(inst, 'monthNames')};
		},
		
		/* Parse existing date and initialise date picker. */
		_setDateFromField: function(inst, noDefault) {
			if (inst.input.val() == inst.lastVal) {
				return;
			}
			var dateFormat = this._get(inst, 'dateFormat');
			var dates = inst.lastVal = inst.input ? inst.input.val() : null;
			var date, defaultDate;
			date = defaultDate = this._getDefaultDate(inst);
			var settings = this._getFormatConfig(inst);
			try {
				date = this.parseDate(dateFormat, dates, settings) || defaultDate;
			} catch (event) {
				this.log(event);
				dates = (noDefault ? '' : dates);
			}
			mpMonth = 0;
			mpSelectedMonth = date.getMonth();
			if(mpSelectedMonth==0 || mpSelectedMonth==1 || mpSelectedMonth==2) {
				mpMonth = 0;
			}
			else if(mpSelectedMonth==3 || mpSelectedMonth==4 || mpSelectedMonth==5) {
				mpMonth = 1;
			}
			else if(mpSelectedMonth==6 || mpSelectedMonth==7 || mpSelectedMonth==8) {
				mpMonth = 2;
			}
			else if(mpSelectedMonth==9 || mpSelectedMonth==10 || mpSelectedMonth==11) {
				mpMonth = 3;
			}
			//if('yyQ' != $.yearquarterpicker._get(inst, 'dateFormat')) {
				inst.selectedMonth = date.getMonth();
			//}
			inst.drawYear = inst.selectedYear = date.getFullYear();
			//if('yyQ' != $.yearquarterpicker._get(inst, 'dateFormat')) {
				inst.currentMonth = (dates ? mpMonth : 0);
			//}
			inst.currentYear = (dates ? date.getFullYear() : 0);
			this._adjustInstDate(inst);
		},

		/* Retrieve the default date shown on opening. */
		_getDefaultDate: function(inst) {
			return this._restrictMinMax(inst,
				this._determineDate(inst, this._get(inst, 'defaultDate'), new Date()));
		},

		/* A date may be specified as an exact value or a relative one. */
		_determineDate: function(inst, date, defaultDate) {
			var offsetNumeric = function(offset) {
				var date = new Date();
				date.setDate(date.getDate() + offset);
				return date;
			};
			var offsetString = function(offset) {
				try {
					return $.yearquarterpicker.parseDate($.yearquarterpicker._get(inst, 'dateFormat'),
						offset, $.yearquarterpicker._getFormatConfig(inst));
				}
				catch (e) {
					// Ignore
				}
				var date = (offset.toLowerCase().match(/^c/) ?
					$.yearquarterpicker._getDate(inst) : null) || new Date();
				var year = date.getFullYear();
				var month = date.getMonth();
				var day = date.getDate();
				var pattern = /([+-]?[0-9]+)\s*(d|D|w|W|m|M|y|Y|Q)?/g;
				var matches = pattern.exec(offset);
				while (matches) {
					switch (matches[2] || 'd') {
						case 'd' : case 'D' :
							day += parseInt(matches[1],10); break;
						case 'w' : case 'W' :
							day += parseInt(matches[1],10) * 7; break;
						case 'q' : case 'Q' :
							day += 1;//parseInt(matches[1],10) * 7; break;
						case 'm' : case 'M' :
							month += parseInt(matches[1],10);
							day = Math.min(day, $.yearquarterpicker._getDaysInMonth(year, month));
							break;
						case 'y': case 'Y' :
							year += parseInt(matches[1],10);
							day = Math.min(day, $.yearquarterpicker._getDaysInMonth(year, month));
							break;
					}
					matches = pattern.exec(offset);
				}
				return new Date(year, month, day);
			};
			var newDate = (date == null || date === '' ? defaultDate : (typeof date == 'string' ? offsetString(date) :
				(typeof date == 'number' ? (isNaN(date) ? defaultDate : offsetNumeric(date)) : new Date(date.getTime()))));
			newDate = (newDate && newDate.toString() == 'Invalid Date' ? defaultDate : newDate);
			if (newDate) {
				newDate.setHours(0);
				newDate.setMinutes(0);
				newDate.setSeconds(0);
				newDate.setMilliseconds(0);
			}
			return this._daylightSavingAdjust(newDate);
		},
		
		_daylightSavingAdjust: function(date) {
			if (!date) return null;
			date.setHours(date.getHours() > 12 ? date.getHours() + 2 : 0);
			return date;
		},
		
		/* Determine the current maximum date - ensure no time components are set. */
		_getMinMaxDate: function(inst, minMax) {
			return this._determineDate(inst, this._get(inst, minMax + 'Date'), null);
		},
		
		/* Ensure a date is within any min/max bounds. */
		_restrictMinMax: function(inst, date) {
			var minDate = this._getMinMaxDate(inst, 'min');
			var maxDate = this._getMinMaxDate(inst, 'max');
			var newDate = (minDate && date < minDate ? minDate : date);
			newDate = (maxDate && newDate > maxDate ? maxDate : newDate);
			return newDate;
		},
		
		/* Action for selecting a new month/year. */
		_selectYear: function(id, select, period) {
			var target = $(id);
			var inst = this._getInst(target[0]);
			inst['selected' + (period == 'M' ? 'Month' : 'Year')] =
			inst['draw' + (period == 'M' ? 'Month' : 'Year')] =
				parseInt(select.options[select.selectedIndex].value,10);
			this._notifyChange(inst);
			this._adjustDate(target);
		},

		/* Action for selecting a month. */
		_selectMonth: function(id, year, month) {
			var target = $(id);
			var inst = this._getInst(target[0]);
			inst.selectedMonth = inst.currentMonth = month;
			inst.selectedYear = inst.currentYear = year;
			this._selectDate(id, this._formatDate(inst, inst.currentMonth, inst.currentYear));
		},
		
		
		parseDate: function (format, value, settings) {
			if (format == null || value == null)
				throw 'Invalid arguments';
			value = (typeof value == 'object' ? value.toString() : value + '');
			if (value == '')
				return null;
			var shortYearCutoff = (settings ? settings.shortYearCutoff : null) || this._defaults.shortYearCutoff;
			shortYearCutoff = (typeof shortYearCutoff != 'string' ? shortYearCutoff :
					new Date().getFullYear() % 100 + parseInt(shortYearCutoff, 10));
			var monthNamesShort = (settings ? settings.monthNamesShort : null) || this._defaults.monthNamesShort;
			var monthNames = (settings ? settings.monthNames : null) || this._defaults.monthNames;
			var year = -1;
			var month = -1;
			var literal = false;
			var lookAhead = function(match) {
				var matches = (iFormat + 1 < format.length && format.charAt(iFormat + 1) == match);
				if (matches)
					iFormat++;
				return matches;
			};
			var getNumber = function(match) {
				var isDoubled = lookAhead(match);
				var size = (match == '@' ? 14 : (match == '!' ? 20 :
					(match == 'y' && isDoubled ? 4 : (match == 'o' ? 3 : 2))));
				var digits = new RegExp('^\\d{1,' + size + '}');
				var num = value.substring(iValue).match(digits);
				if (!num)
					throw 'Missing number at position ' + iValue;
				iValue += num[0].length;
				return parseInt(num[0], 10);
			};
			// Extract a name from the string value and convert to an index
			var getName = function(match, shortNames, longNames) {
				var names = $.map(lookAhead(match) ? longNames : shortNames, function (v, k) {
					return [ [k, v] ];
				}).sort(function (a, b) {
					return -(a[1].length - b[1].length);
				});
				var index = -1;
				$.each(names, function (i, pair) {
					var name = pair[1];
					if (value.substr(iValue, name.length).toLowerCase() == name.toLowerCase()) {
						index = pair[0];
						iValue += name.length;
						return false;
					}
				});
				if (index != -1)
					return index + 1;
				else
					throw 'Unknown name at position ' + iValue;
			};
			// Confirm that a literal character matches the string value
			var checkLiteral = function() {
				if (value.charAt(iValue) != format.charAt(iFormat))
					throw 'Unexpected literal at position ' + iValue;
				iValue++;
			};
			var iValue = 0;
			for (var iFormat = 0; iFormat < format.length; iFormat++) {
				if (literal)
					if (format.charAt(iFormat) == "'" && !lookAhead("'"))
						literal = false;
					else
						checkLiteral();
				else
					switch (format.charAt(iFormat)) {
						case 'm':
							month = getNumber('m');
							break;
						case 'M':
							month = getName('M', monthNamesShort, monthNames);
							break;
						case 'Q':
							month = 'Q1';//getName('M', monthNamesShort, monthNames);
							break;
						case 'D':
							month = 'D1';//getName('M', monthNamesShort, monthNames);
							break;
						case 'y':
							year = getNumber('y');
							break;
						case '@':
							var date = new Date(getNumber('@'));
							year = date.getFullYear();
							month = date.getMonth() + 1;
							day = date.getDate();
							break;
						case '!':
							var date = new Date((getNumber('!') - this._ticksTo1970) / 10000);
							year = date.getFullYear();
							month = date.getMonth() + 1;
							break;
						case "'":
							if (lookAhead("'"))
								checkLiteral();
							else
								literal = true;
							break;
						default:
							checkLiteral();
					}
			}
			if (iValue < value.length){
				throw "Extra/unparsed characters found in date: " + value.substring(iValue);
			}
			if (year == -1)
				year = new Date().getFullYear();
			else if (year < 100)
				year += new Date().getFullYear() - new Date().getFullYear() % 100 +
					(year <= shortYearCutoff ? 0 : -100);
			var date = this._daylightSavingAdjust(new Date(year, month - 1, 1));
			if (date.getFullYear() != year || date.getMonth() + 1 != month || date.getDate() != 1)
				throw 'Invalid date'; // E.g. 31/02/00
			return date;
		},
		
		formatDate: function (format, date, settings) {
			if (!date)
				return '';
			var monthNamesShort = (settings ? settings.monthNamesShort : null) || this._defaults.monthNamesShort;
			var monthNames = (settings ? settings.monthNames : null) || this._defaults.monthNames;
			var lookAhead = function(match) {
				var matches = (iFormat + 1 < format.length && format.charAt(iFormat + 1) == match);
				if (matches)
					iFormat++;
				return matches;
			};
			var formatNumber = function(match, value, len) {
				var num = '' + value;
				if (lookAhead(match))
					while (num.length < len)
						num = '0' + num;
				return num;
			};
			var formatName = function(match, value, shortNames, longNames) {
				return (lookAhead(match) ? longNames[value] : shortNames[value]);
			};
			var output = '';
			var literal = false;
			if (date)
				for (var iFormat = 0; iFormat < format.length; iFormat++) {
					if (literal)
						if (format.charAt(iFormat) == "'" && !lookAhead("'"))
							literal = false;
						else
							output += format.charAt(iFormat);
					else
						switch (format.charAt(iFormat)) {
							case 'm':
								output += formatNumber('m', date.getMonth() + 1, 2);
								break;
							case 'M':
								output += formatName('M', date.getMonth(), monthNamesShort, monthNames);
								break;
							case 'Q':
								mNum = date.getMonth();
								qNum = '1';
								if(mNum < 3) {qNum = '1';} 
								else if(mNum < 6) {qNum = '2';}
								else if(mNum < 9) {qNum = '3';}
								else if(mNum < 12) {qNum = '4';}
								output = date.getFullYear()+'Q'+ qNum;
								break;

							case 'X':   /* X is for formatting in the format YYYYDN  */
								mNum = date.getMonth();
								qNum = '1';
								if(mNum < 3) {qNum = '1';} 
								else if(mNum < 6) {qNum = '2';}
								else if(mNum < 9) {qNum = '3';}
								else if(mNum < 12) {qNum = '4';}
								output = date.getFullYear()+'D'+ qNum;
								break;	
							case 'y':
								output += (lookAhead('y') ? date.getFullYear() :
									(date.getYear() % 100 < 10 ? '0' : '') + date.getYear() % 100);
								break;
							case '@':
								output += date.getTime();
								break;
							case '!':
								output += date.getTime() * 10000 + this._ticksTo1970;
								break;
							case "'":
								if (lookAhead("'"))
									output += "'";
								else
									literal = true;
								break;
							default:
								output += format.charAt(iFormat);
						}
				}
			return output;
		},
		
		_clearDate: function(id) {
			var target = $(id);
			var inst = this._getInst(target[0]);
			this._selectDate(target, '');
		},

		_selectDate: function(id, dateStr) {
			var target = $(id);
			var inst = this._getInst(target[0]);
			dateStr = (dateStr != null ? dateStr : this._formatDate(inst));
			if (inst.input)
				inst.input.val(dateStr);
			this._updateAlternate(inst);
			var onSelect = this._get(inst, 'onSelect');
			if (onSelect)
				onSelect.apply((inst.input ? inst.input[0] : null), [dateStr, inst]);  // trigger custom callback
			else if (inst.input)
				inst.input.trigger('change'); // fire the change event
			this._hideYearquarterpicker();
			this._lastInput = inst.input[0];
			if (typeof(inst.input[0]) != 'object')
				inst.input.focus(); // restore focus
			this._lastInput = null;
		},

		_updateAlternate: function(inst) {
			var altField = this._get(inst, 'altField');
			if (altField) { // update alternate field too
				var altFormat = this._get(inst, 'altFormat') || this._get(inst, 'dateFormat');
				var date = this._getDate(inst);
				var dateStr = this.formatDate(altFormat, date, this._getFormatConfig(inst));
				$(altField).each(function() { $(this).val(dateStr); });
			}
		},
		
		/* Format the given date for display. */
		_formatDate: function(inst, month, year) {
			if (!month) {
				inst.currentMonth = inst.selectedMonth;
				inst.currentYear = inst.selectedYear;
			}
			var date = (month ? (typeof month == 'object' ? month :
				this._daylightSavingAdjust(new Date(year, month, 1))) :
				this._daylightSavingAdjust(new Date(inst.currentYear, inst.currentMonth, 1)));
			return this.formatDate(this._get(inst, 'dateFormat'), date, this._getFormatConfig(inst));
		}
	});
	
	/* jQuery extend now ignores nulls! */
	function extendRemove(target, props) {
		$.extend(target, props);
		for (var name in props)
			if (props[name] == null || props[name] == undefined)
				target[name] = props[name];
		return target;
	};
 
	function bindHover(dpDiv) {
		var selector = '.ui-datepicker-prev, .ui-datepicker-next, .ui-datepicker-calendar td a';
		return dpDiv.delegate(selector, 'mouseout', function() {
				$(this).removeClass('ui-state-hover');
				if (this.className.indexOf('ui-datepicker-prev') != -1) $(this).removeClass('ui-datepicker-prev-hover');
				if (this.className.indexOf('ui-datepicker-next') != -1) $(this).removeClass('ui-datepicker-next-hover');
			})
			.delegate(selector, 'mouseover', function(){
				if (!$.yearquarterpicker._isDisabledYearquarterpicker( instActive.input[0])) {
					$(this).parents('.ui-datepicker-calendar').find('a').removeClass('ui-state-hover');
					$(this).addClass('ui-state-hover');
					if (this.className.indexOf('ui-datepicker-prev') != -1) $(this).addClass('ui-datepicker-prev-hover');
					if (this.className.indexOf('ui-datepicker-next') != -1) $(this).addClass('ui-datepicker-next-hover');
				}
			});
	}
	

	$.fn.yearquarterpicker = function(options){

		if ( !this.length ) {
			return this;
		}

		if (!$.yearquarterpicker.initialized) {
			$(document).mousedown($.yearquarterpicker._checkExternalClick).
				find('body').append($.yearquarterpicker.dpDiv);
			$.yearquarterpicker.initialized = true;
		}

		var otherArgs = Array.prototype.slice.call(arguments, 1);
		return this.each(function() {
			typeof options == 'string' ?
				$.yearquarterpicker['_' + options + 'Yearquarterpicker'].
					apply($.yearquarterpicker, [this].concat(otherArgs)) :
				$.yearquarterpicker._attachYearquarterpicker(this, options);
		});
	};
	
	$.yearquarterpicker = new Yearquarterpicker(); // singleton instance
	$.yearquarterpicker.initialized = false;
	
	window['MP_jQuery_' + dpuuid] = $;

})(jQuery);