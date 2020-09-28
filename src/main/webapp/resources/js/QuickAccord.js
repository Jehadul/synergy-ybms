/*
* QuickAccord v1.0.0 Copyright (c) 2016 AJ Savino
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in
* all copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
* THE SOFTWARE.
*/
var QuickAccord = {
	DATA_ATTR_TARGET:"data-accord-target",
	DATA_ATTR_GROUP:"data-accord-group",
	
	CLASS_EXPANDED:"expanded",
	CLASS_COLLAPSED:"collapsed",
	CLASS_ANIMATING:"is-animating",
};
(function($){
	$.fn.QuickAccord = function(options){
		return this.filter("a,[" + QuickAccord.DATA_ATTR_TARGET + "]").each(function(){
			var _vars = {
				_$this:$(this)
			};
			
			var _methods = {
				init:function(){
					var $trigger = _vars._$this;
					
					//Initialize aria attributes
					var ariaControls = null;
					var accordTarget = $trigger.attr(QuickAccord.DATA_ATTR_TARGET) || $trigger.attr("href") || null;
					if (accordTarget){
						var id = /^#(\w+)/i.exec(accordTarget);
						if (id){
							$trigger.attr("aria-controls", id[1]);
						}
					}
					
					if (!_methods.isExpanded()){ //Initially collapsed
						_methods.collapse();
					} else { //Initially expanded
						_methods.expand();
					}
					$trigger.off("click", _methods._handler_trigger_click);
					$trigger.on("click", _methods._handler_trigger_click);
				},
				
				destroy:function(){
					var $trigger = _vars._$this;
					$trigger.removeClass(CLASS_EXPANDED);
					$trigger.removeClass(CLASS_COLLAPSED);
					$trigger.removeAttr("aria-expanded");
					$trigger.removeAttr("aria-controls");
					$trigger.off("click", _methods._handler_trigger_click);
					
					var $targets = _methods._resolveTargets($trigger);
					if ($targets){
						$targets.css("height", "auto");
						$targets.removeClass(QuickAccord.CLASS_EXPANDING);
						$targets.removeClass(QuickAccord.CLASS_EXPANDED);
						$targets.removeClass(QuickAccord.CLASS_COLLAPSING);
						$targets.removeClass(QuickAccord.CLASS_COLLAPSED);
						$targets.removeAttr("aria-expanded");
						$targets.each(function(){
							QuickAccord.TransitionHelper.offTransitionComplete($(this));
						});
					}
				},
				
				_handler_trigger_click:function(evt){
					evt.preventDefault();
					_methods.toggle();
					return false;
				},
				
				toggle:function($trigger){
					if (_methods.isExpanded()){ //Collapse
						_methods.collapse($trigger);
					} else { //Expand
						_methods.expand($trigger);
					}
				},
				
				collapse:function($trigger){
					$trigger = $trigger || _vars._$this;
					$trigger.removeClass(QuickAccord.CLASS_EXPANDED);
					$trigger.addClass(QuickAccord.CLASS_COLLAPSED);
					$trigger.attr("aria-expanded", "false");
					
					var $targets = _methods._resolveTargets($trigger);
					if (!$targets){
						throw new Error("Could not resolve target");
					}
					$targets.each(function(){
						var $target = $(this);
						if ($target.outerHeight() > 0){
							$target.addClass(QuickAccord.CLASS_ANIMATING);
							QuickAccord.TransitionHelper.onTransitionComplete($target, _methods._handler_collapse_complete);
							$target.css("height", "0");
						}
					});
					$targets.attr("aria-expanded", "false");
				},
				
				_handler_collapse_complete:function(evt){
					var $target = $(evt.target);
					QuickAccord.TransitionHelper.offTransitionComplete($target);
					$target.removeClass(QuickAccord.CLASS_ANIMATING);
					$target.removeClass(QuickAccord.CLASS_EXPANDED);
					$target.addClass(QuickAccord.CLASS_COLLAPSED);
				},

				expand:function($trigger){
					$trigger = $trigger || _vars._$this;
					$trigger.removeClass(QuickAccord.CLASS_COLLAPSED);
					$trigger.addClass(QuickAccord.CLASS_EXPANDED);
					$trigger.attr("aria-expanded", "true");
					
					var $accordGroupTriggers = null;
					var accordGroup = $trigger.attr(QuickAccord.DATA_ATTR_GROUP) || null;
					if (accordGroup){
						$accordGroupTriggers = $("[" + QuickAccord.DATA_ATTR_GROUP + "='" + accordGroup + "']").filter("a,[" + QuickAccord.DATA_ATTR_TARGET + "]").not($trigger);
						$accordGroupTriggers.each(function(){
							_methods.collapse($(this));
						});
					}
					
					var $targets = _methods._resolveTargets($trigger);
					if (!$targets){
						throw new Error("Could not resolve target");
					}
					$targets.each(function(){
						var $target = $(this);
						var isExpanded = $target.hasClass(QuickAccord.CLASS_EXPANDED) && !$target.hasClass(QuickAccord.CLASS_ANIMATING);
						var currentHeight = $target.outerHeight();
						$target.css("height", "auto");
						var height = $target.outerHeight();
						if (!isExpanded){
							$target.addClass(QuickAccord.CLASS_ANIMATING);
							QuickAccord.TransitionHelper.onTransitionComplete($target, _methods._handler_expand_complete);
							$target.css("height", currentHeight + "px");
						}
						$target.css("height"); //This line allows break between setters. 0 to height vs auto to height.
						$target.css("height", height + "px");
					});
					$targets.removeClass(QuickAccord.CLASS_COLLAPSED);
					$targets.addClass(QuickAccord.CLASS_EXPANDED);
					$targets.attr("aria-expanded", "true");
				},
				
				_handler_expand_complete:function(evt){
					var $target = $(evt.target);
					QuickAccord.TransitionHelper.offTransitionComplete($target);
					$target.removeClass(QuickAccord.CLASS_ANIMATING);
					$target.removeClass(QuickAccord.CLASS_COLLAPSED);
					$target.addClass(QuickAccord.CLASS_EXPANDED);
				},
				
				isExpanded:function($trigger){
					$trigger = $trigger || _vars._$this;
					if ($trigger.hasClass(QuickAccord.CLASS_COLLAPSED)){
						return false;
					} else if ($trigger.hasClass(QuickAccord.CLASS_EXPANDED)){
						return true;
					} else {
						var $targets = _methods._resolveTargets($trigger);
						if ($targets && $targets.length){
							var $firstTarget = $($targets[0]);
							if ($firstTarget.hasClass(QuickAccord.CLASS_EXPANDED)){
								return true;
							} else if ($firstTarget.hasClass(QuickAccord.CLASS_COLLAPSED)){
								return false;
							}
						}
					}
					return false;
				},
				
				_resolveTargets:function($trigger){
					var $accordTargets = null;
					var accordTarget = $trigger.attr(QuickAccord.DATA_ATTR_TARGET) || $trigger.attr("href") || null;
					if (accordTarget){
						$accordTargets = $(accordTarget);
					}
					return $accordTargets;
				}
			};
			
			this.quickAccord = {
				init:_methods.init,
				destroy:_methods.destroy,
				toggle:_methods.toggle,
				collapse:_methods.collapse,
				expand:_methods.expand,
				isExpanded:_methods.isExpanded
			};
			_methods.init();
		});
	}
})(jQuery);
QuickAccord.TransitionHelper = (function(){
	var transitionEvent = null;
	var transitionPrefix = null;
	
	var transitionEvents = [["webkitTransition","webkitTransitionEnd","-webkit-"], ["transition","transitionend",""]];
	var transitionEventsLen = transitionEvents.length;
	for (var i = 0; i < transitionEventsLen; i++){
		if (typeof document.documentElement.style[transitionEvents[i][0]] !== typeof undefined){
			break;
		}
	}
	if (i != transitionEventsLen){
		transitionEvent = transitionEvents[i][1];
		transitionPrefix = transitionEvents[i][2];
	} //else not supported
	
	var _methods = {
		hasTransition:function($element){
			var element = $element[0];
			if (typeof document.documentElement.currentStyle !== typeof undefined){ //IE
				return parseFloat(element.currentStyle[transitionPrefix + "transition-duration"]) > 0;
			} else {
				return parseFloat(window.getComputedStyle(element)[transitionPrefix + "transition-duration"]) > 0;
			}
		},
		onTransitionComplete:function($element, callback){
			if (transitionEvent && _methods.hasTransition($element)){
				$element.on(transitionEvent, callback);
			} else {
				callback({target:$element[0]});
			}
		},
		offTransitionComplete:function($element, callback){
			if (transitionEvent){
				if (typeof callback !== typeof undefined){
					$element.off(transitionEvent, callback);
				} else {
					$element.off(transitionEvent);
				}
			}
		}
	};
	return _methods;
})();