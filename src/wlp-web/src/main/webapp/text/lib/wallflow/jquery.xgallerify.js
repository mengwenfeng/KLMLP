/* globals jQuery */
/* Version v0.1.5 */
'use strict';
(function($){
	var windowHasLoaded = false;
	$(window).on('load', function() {
		windowHasLoaded = true;
	});

	///////// PUBLIC FUNCTIONS /////////
	/**
	* gallerify a gallery and render rows
	*
	* @param params can contain margin, width, mode, jsSetup and imagesPerRow
	*/
	$.fn.gallerify = $.fn.xgallerify =  $.fn.xGallerify = function(params){
		var _this = this;
		//Initial Parameters
		params = params || {};
		params.margin = params.margin !== undefined && params.margin !== null ? params.margin : 10;
		params.galleryMargin = params.galleryMargin !== undefined && params.galleryMargin !== null ? params.galleryMargin : 17; // 17px is the largest native scrollbar width state of 2017
		params.width = params.width || undefined; //width of the whole gallery
		params.mode = params.mode || 'default'; //default, bootstrap, flickr, small
		params.jsSetup = params.jsSetup !== undefined && params.jsSetup !== null ? params.jsSetup : true; //if you are going to set the css variables for the elements in CSS
		params.imagesPerRow = params.imagesPerRow || undefined; //How many images should show up at a MINIMUM
		params.debounceLoad = params.debounceTime !== undefined && params.debounceTime !== null && (params.debounceTime % 1 === 0); // Check if debounce time is set and is a integer
		params.debounceTime = params.debounceTime !== undefined && params.debounceTime !== null ? params.debounceTime : 50; //How many images should show up at a MINIMUM
		params.lastRow = params.lastRow || 'adjust';
		init(_this, params);

		this.gallerify.render = function(){
			setupChilds(_this, params.margin);
			renderGallery(_this, params);
		};

		var asyncImagesLoadedFinished = debounce(function() {
			renderGallery(_this, params);
		}, params.debounceTime);

		this.gallerify.renderAsyncImages = function(){
			setupChilds(_this, params.margin);
			if(params.debounceLoad){
				_this.find('img').on('load', function(){
					asyncImagesLoadedFinished();
				});
			}else{
				_this.find('img').on('load', function(){
					renderGallery(_this, params);
				});	
			}
		};

		return _this;
	};

	///////// PRIVATE FUNCTIONS /////////
	function init(jGallery, params){
		//Allow
		if(params.jsSetup){
			setupChilds(jGallery, params.margin);
		}
		jGallery.addClass('xgallerify');
		// Render Gallery, if window has not been loaded yet
		if(windowHasLoaded){
			renderGallery(jGallery, params);
		}else{ // Eventlistener for window load, to load gallery after window has been loaded
			$(window).on('load', function(){
				renderGallery(jGallery, params);
			});
		}
		// Render Gallery when window got resized
		$(window).resize(function(){
			renderGallery(jGallery, params);
		});
	}

	function setupChilds(jGallery, margin){
		var jChildren = $(jGallery.children());
		jChildren
		.css('display', 'inline-block')
		.css('margin', margin)

		.find('img')
		.css('width', '100%')
		.addClass('ximage-loaded');
	}
	function renderGallery(jGallery, _params){
		var jChildren = []; //jquery childs
		var jChildRows = []; //jquery childs
		var dChildren = jGallery.children(); //dom childs
		var width = _params.width || jGallery[0].clientWidth;
		var screenSettings = getScreenSettings(width, _params.mode);
		var imagesPerRow = _params.imagesPerRow || screenSettings.itemsPerRow;

		var lastRowHeight;
		//TODO Might need some rework
		if(_params.width){
			jGallery.width(width);
		}
		//TODO This code looks a little too complex - seperate in multiple functions?!
		for (var i = 0; i < dChildren.length; i++){
			var _jChild = $(dChildren[i]);
			if(_jChild.width()){

				
				jChildren.push(_jChild);
				if(jChildren.length >= imagesPerRow || i == dChildren.length -1){
					jChildRows.push(jChildren);
					if(
						!(
							i == dChildren.length -1 && //Check if last row
							jChildren.length < screenSettings.itemsPerRow // Check if the miminum items per row are reched
						) || //Checking if current row is a complete row
						_params.lastRow == 'fullwidth' //check if a non-complete row should be displayed with the full width
					){
						lastRowHeight = renderRow(jChildRows[jChildRows.length - 1], width, _params.margin, _params.galleryMargin, screenSettings.maxHeight);
					}else{
						if(_params.lastRow === 'hidden' && imagesPerRow !== 1){
							hideRow(jChildren); // Don't render last row
						}else{ // default / adjust
							renderLastRow(jChildRows[jChildRows.length - 1], width, _params.margin, _params.galleryMargin, lastRowHeight);		
						}
					}

					if(lastRowHeight < screenSettings.maxHeight){ //If the row height is smaller than the maxHeight property beginn a new row. Otherwise add another image to decrese the height
						jChildren = [];
					}
				}
			}
		}
	}

	function renderRow(jChildren, galleryWidth, margin, galleryMargin, maxHeight){
		resizeToSameHeight(jChildren, maxHeight);
		return resizeToWidth(jChildren, galleryWidth, margin, galleryMargin); //Returning height of the current row
	}

	function renderLastRow(jChildren,  galleryWidth, margin, galleryMargin, rowHeight){
		rowHeight = resizeToSameHeight(jChildren, rowHeight);
		var currentWidth = 0;
		$(jChildren).each( function(){ currentWidth += $(this).width(); });
		if(currentWidth > galleryWidth){
			rowHeight = resizeToWidth(jChildren, galleryWidth, margin, galleryMargin);
		}
		return rowHeight;
	}
	function hideRow(jChildren){
		$(jChildren).each( function(){ $(this).css('display', 'none'); });
		return 0; // Return height of 0px, to be consistent with other render functions
	}

	function resizeToSameHeight(jChildren, childHeight){
		for (var i = 0; i < jChildren.length; i++){
			var factor =  childHeight / jChildren[i].height();
			jChildren[i].width(jChildren[i].width() * factor);
		}
		return jChildren[0].height(); //Returning height of the current row
	}

	function resizeToWidth(jChildren, rowWidth, margin, galleryMargin){
		var currentWidth = 0;
		$(jChildren).each( function(){ currentWidth += $(this).width(); });
		var marginTotal = (jChildren.length * (margin) * 2);
		// Adding 17 pixel of margin to the whole gallery because of some scrollbar issue
		// TODO find workaround here
		marginTotal += galleryMargin;
		var factor = (rowWidth - marginTotal) / currentWidth;
		for (var i = 0; i < jChildren.length; i++){
			jChildren[i].css('width',  jChildren[i].width() * factor);
		}
		return jChildren[0].height();
	}

	function getMode(_mode){
		if(typeof _mode === 'object'){
			return _mode;
		}else{
			if(_mode == 'bootstrap'){ // ------- bootstrap mode -------
				return{
					maxHeight: screen.height * 0.5,
					breakPoints:[
						{
							minWidth: 1170,
							columns: 4,
						},{
							minWidth: 970,
							columns: 3,
						},{
							minWidth: 750,
							columns: 2,
						},{
							maxWidth: 750,
							columns: 0.4,
						}
					]
				};
			}else if(_mode == 'bootstrapv4'){ // ------- bootstrap mode -------
				return{
					maxHeight: screen.height * 0.5,
					breakPoints:[
						{
							minWidth: 1200,
							columns: 4,
						},{
							minWidth: 992,
							columns: 3,
						},{
							minWidth: 768,
							columns: 2,
						},{
							maxWidth: 768,
							columns: 0.4,
						}
					]
				};
			}else if(_mode == 'flickr'){ // ------- flickr mode -------
				return{
					maxHeight: screen.height * 0.4,
					breakPoints:[
						{
							minWidth: 1800,
							columns: 4,
						},{
							minWidth: 1300,
							columns: 3,
						},{
							minWidth: 610,
							columns: 2,
						},{
							maxWidth: 610,
							columns: 1,
						}
					]
				};
			}else if(_mode == 'small'){ // ------- small mode -------
				return{
					maxHeight: screen.height * 0.4,
					breakPoints:[
						{
							minWidth: 1800,
							columns: 14,
						},{
							minWidth: 1300,
							columns: 10,
						},{
							minWidth: 610,
							columns: 6,
						},{
							maxWidth: 610,
							columns: 4,
						}
					]
				};
			}else { // ------- default mode -------
				return{
					maxHeight: screen.height * 0.5,
					breakPoints:[
						{
							minWidth: 1800,
							columns: 4,
						},{
							minWidth: 1200,
							columns: 3,
						},{
							minWidth: 768,
							columns: 2,
						},{
							maxWidth: 768,
							columns: 1,
						}
					]
				};
			}
		}
	}

	function getScreenSettings(galleryWidth, mode){
		var ret = {
			itemsPerRow : undefined,
			maxHeight   : undefined
		};

		// Default MAX HEIGHT for mobile
		if(galleryWidth <= 768){
			ret.maxHeight = screen.height;
		}

		var _mode = getMode(mode);
		for (var i = _mode.breakPoints.length - 1; i >= 0; i--) {
			if(_mode.breakPoints[i].minWidth && _mode.breakPoints[i].minWidth < galleryWidth){
				ret.itemsPerRow = _mode.breakPoints[i].columns;
			}else if(_mode.breakPoints[i].maxWidth && _mode.breakPoints[i].maxWidth > galleryWidth){
				ret.itemsPerRow = _mode.breakPoints[i].columns;
			}
		}
		if(_mode.maxHeight){
			ret.maxHeight = _mode.maxHeight;
		}
		return ret;
	}

	function debounce(func, wait, immediate) {
		var timeout;
		return function() {
			var context = this, args = arguments;
			var later = function() {
				timeout = null;
				if (!immediate) func.apply(context, args);
			};
			var callNow = immediate && !timeout;
			clearTimeout(timeout);
			timeout = setTimeout(later, wait);
			if (callNow) func.apply(context, args);
		};
	}

}( jQuery ));