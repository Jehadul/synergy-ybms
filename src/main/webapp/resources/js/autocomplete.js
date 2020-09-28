
$(document)
		.ready(function() {
					$(function() {
						$("#name")
								.autocomplete(
										{
											source : function(request, response) {
												/*alert(request.term);*/
												$.ajax({
														url : '/hrm/products/getMachedNames',
														type : "POST",
														data : {
															name : request.term
														},
														dataType : "json",
														success : function(																	data) {
															response($
																	.map(data,
																			function(v,i) {
																				return {
																					label : v.name,
																					value : v.name
																				};
																			}));
														}

												});

											},
											response : function(event, ui) {
												if (!ui.content.length) {
													var noResult = {
														value : "",
														label : "No matching your request"
													};
													ui.content.push(noResult);
												}
											},
											select : function(event, ui) {
												// alert(ui.item.label);
												window.location.href = '#?name='
														+ ui.item.label;
											},
											minLength : 1
										});
					});
				});

// /
