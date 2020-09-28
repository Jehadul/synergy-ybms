<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<!-- Title and other stuffs -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="author" content="">

<c:url var="checkCandidateId" value="/checkCandId" />

<link
	href="${pageContext.request.contextPath}/resource/select2/select2.min.css"
	rel="stylesheet" type="text/css">
<script
	src="${pageContext.request.contextPath}/resource/select2/select2.min.js"></script>

<link
	href="${pageContext.request.contextPath}/resource/w2ui/w2ui-1.5.rc1.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resource/w2ui/w2ui-1.5.rc1.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resource/w2ui/w2ui-1.5.rc1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/w2ui/w2ui-1.5.rc1.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/js/additional-methods.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resource/js/jquery.validate.min.js"></script>
<style type="text/css">
form .error {
	color: #ff0000;
}
</style>
<style type="text/css">
.table td.fit, .table th.fit {
	white-space: nowrap;
	width: 1%;
}
</style>


</head>

<body>
	<!-- Page heading start -->
	<div class="page-head">
		<h2 class="pull-left" style="color: #428bca;">Delivery Entry Form</h2>
		<div class="clearfix"></div>
	</div>
	<!-- Page heading ends -->

	<!-- Matter -->

	<!--   <div class="matter"> -->
	<div class="container">

		<div class="row">

			<div class="col-md-12">


				<div class="widget wgreen">

					<div class="widget-head">
						<div class="pull-left"></div>
						<div class="widget-icons pull-right">
							<a href="#" class="wminimize"><i class="fa fa-chevron-up"></i></a>
							<a href="#" class="wclose"><i class="fa fa-times"></i></a>
						</div>
						<div class="clearfix"></div>
					</div>

					<div class="widget-content">
						<div class="padd">

							<br />

							<!-- Form starts.  -->
							<form:form cssClass="form-horizontal" method="POST" name="reg"
								id="deliveryForm" class="form-horizontal"
								modelAttribute="command" accept-charset="UTF-8"
								action="${pageContext.request.contextPath}/saveDelivery">

								<form:input type="hidden" path="id" value="${delivery.id}"
									readonly="true" class="form-control" />

								<div class="form-group">
									<label class="col-lg-2 control-label"></label>
									<div class="col-lg-5">
										<span style="color: green;">${successMsg}</span>
									</div>
								</div>

								<div class="form-group">
									<label class="col-lg-2 control-label">Fiscal Year: </label>
									<div class="col-lg-5">
										<c:choose>
											<c:when test="${edit}">
												<input id="fiscalYear" readonly="readonly"
													disabled="disabled" value="${delivery.fiscalYearDlv.name}"
													class="form-control">
											</c:when>
											<c:otherwise>
												<input id="fiscalYear" readonly="readonly"
													disabled="disabled" value="${fiscalYear}"
													class="form-control">
											</c:otherwise>
										</c:choose>


									</div>
								</div>

								<div class="form-group">
									<label class="col-lg-2 control-label">Delivery Date<span
										class="red">*</span>:
									</label>
									<div class="col-lg-5">
										<c:choose>
											<c:when test="${edit}">
												<input type="eu-date" class="form-control" name="dlvDate"
													readonly="readonly" disabled="disabled" id="dlvDate"
													value="${delivery.dlvDate}">
											</c:when>
											<c:otherwise>
												<input type="eu-date" class="form-control" name="dlvDate"
													id="dlvDate" value="${delivery.dlvDate}">
											</c:otherwise>
										</c:choose>
									</div>
								</div>

								<div class="form-group">
									<label class="col-lg-2 control-label">Point <span
										class="red">*</span>:
									</label>
									<div class="col-lg-5">
										<form:select path="pointId" id="pointId"
											class="form-control basic-single-select2">
											<form:option value="" selected="selected">Select One</form:option>
											<c:forEach items="${pointList}" var="point">

												<c:if test="${delivery.point.id eq point.id}">
													<form:option value="${point.id}" selected="selected">${point.name} - (${point.keyword})</form:option>
												</c:if>
												<c:if test="${delivery.point.id ne point.id}">
													<form:option value="${point.id}">${point.name} - (${point.keyword})</form:option>
												</c:if>
											</c:forEach>
										</form:select>
									</div>
								</div>

								<div class="form-group">
									<label class="col-lg-2 control-label">Delivery Type <span
										class="red">*</span>:
									</label>
									<div class="col-lg-5">
										<form:select path="againstBudget" id="againstBudget"
											class="form-control">
											<c:choose>
												<c:when test="${edit}">
													<c:if test="${delivery.againstBudget eq 1}">
														<form:option value="1" selected="selected">Against Budget</form:option>
													</c:if>
													<c:if test="${delivery.againstBudget eq 0}">
														<form:option value="0" selected="selected">Without Budget</form:option>
													</c:if>
												</c:when>
												<c:otherwise>
													<form:option value="">Select One</form:option>
													<form:option value="1">Against Budget</form:option>
													<form:option value="0">Without Budget</form:option>
												</c:otherwise>
											</c:choose>
										</form:select>
									</div>
								</div>

								<div class="form-group">
									<label class="col-lg-2 control-label">Type of Goods <span
										class="red">*</span>:
									</label>
									<div class="col-lg-5">
										<form:select path="goodsTypeId" id="goodsTypeId"
											class="form-control basic-single-select2">
											<form:option value="" selected="selected">Select One</form:option>
											<c:forEach items="${goodsTypeList}" var="goodsType">

												<c:if test="${delivery.goodsType.id eq goodsType.id}">
													<form:option value="${goodsType.id}" selected="selected">${goodsType.name}</form:option>
												</c:if>
												<c:if test="${delivery.goodsType.id ne goodsType.id}">
													<form:option value="${goodsType.id}">${goodsType.name}</form:option>
												</c:if>
											</c:forEach>
										</form:select>
									</div>
								</div>

								<div class="form-group">
									<label class="col-lg-2 control-label">Source: </label>
									<div class="col-lg-5">
										<form:select path="budgetSourceId" id="budgetSourceId"
											class="form-control basic-single-select2">
											<form:option value="" selected="selected">Select One</form:option>
											<c:forEach items="${budgetSourceList}" var="budgetSource">

												<c:if test="${delivery.budgetSource.id eq budgetSource.id}">
													<form:option value="${budgetSource.id}" selected="selected">${budgetSource.name}</form:option>
												</c:if>
												<c:if test="${delivery.budgetSource.id ne budgetSource.id}">
													<form:option value="${budgetSource.id}">${budgetSource.name}</form:option>
												</c:if>
											</c:forEach>
										</form:select>
									</div>
								</div>

								<div class="form-group">
									<label class="col-lg-2 control-label">Budget Code: </label>
									<div class="col-lg-5">
										<form:select path="budgetCodeId" id="budgetCodeId"
											class="form-control basic-single-select2">
											<form:option value="" selected="selected">Select One</form:option>
											<c:forEach items="${budgetCodeList}" var="budgetCode">

												<c:if test="${delivery.budgetCode.id eq budgetCode.id}">
													<form:option value="${budgetCode.id}" selected="selected">${budgetCode.name}</form:option>
												</c:if>
												<c:if test="${delivery.budgetCode.id ne budgetCode.id}">
													<form:option value="${budgetCode.id}">${budgetCode.name}</form:option>
												</c:if>
											</c:forEach>
										</form:select>
									</div>
								</div>

								<div class="form-group">
									<label class="col-lg-2 control-label">Amount (BDT)<span
										class="red">*</span>:
									</label>
									<div class="col-lg-5">
										<input type="number" name="dlvAmount" id="dlvAmount"
											value="${delivery.dlvAmount}" class="form-control">
									</div>
									(Lakh.)
								</div>

								<div class="form-group">
									<label class="col-lg-2 control-label">Remarks: </label>
									<div class="col-lg-5">
										<textarea id="remarks" class="form-control" name="remarks"
											rows="4" type="textarea"><c:out
												value="${delivery.remarks}" /></textarea>
									</div>
								</div>


								<div class="form-group">
									<c:choose>
										<c:when test="${edit}">
											<div class="col-lg-offset-2  col-xs-2" id="">
												<button type="submit"
													class="btn btn-sm btn-primary btn-success req-save-update-btn">
													Update</button>
												&nbsp;&nbsp;
												<button type="submit"
													class="btn btn-sm btn-primary req-save-update-btn"
													value="go-to-list" name="goToList">Update and go
													to List</button>
											</div>
											<div class="col-lg-1 col-xs-4">

												<a class="btn btn-sm btn-danger"
													href="${pageContext.request.contextPath}/">Cancel</a>
											</div>
										</c:when>
										<c:otherwise>
											<div class="col-lg-offset-2 col-xs-2" id="">
												<button type="submit"
													class="btn btn-sm btn-success req-save-update-btn">
													Submit</button>
												&nbsp;&nbsp;
												<button type="submit"
													class="btn btn-sm btn-primary req-save-update-btn"
													value="go-to-list" name="goToList">Submit and go
													to List</button>
											</div>
											<div class="col-xs-2">
												<button type="reset" class="btn btn-sm btn-danger ">Reset</button>
											</div>
										</c:otherwise>
									</c:choose>
								</div>
							</form:form>
						</div>

					</div>
					<div class="widget-foot">
						<!-- Footer goes here -->
					</div>
				</div>

				<!-- Table starts-->

				<!-- table ends -->

			</div>

		</div>

	</div>



	<!-- Matter ends -->

	<!-- Mainbar ends -->
	<div class="clearfix"></div>



	<script>
		$(document).ready(function() {
			$('.basic-single-select2').select2();
			//var selected=$( "#againstBudget option:selected" ).val();
			//alert($( "#againstBudget option:selected" ).val());
			if ($("#againstBudget option:selected").val() == "0") {
				$('#budgetSourceId').removeAttr('required');
				$('#budgetCodeId').removeAttr('required');
			}
			if ($("#againstBudget option:selected").val() == "1") {
				$('#budgetSourceId').attr('required', true);
				$('#budgetCodeId').attr('required', true);
			}
		});

		$(function() {
			// Initialize form validation on the registration form.
			// It has the name attribute "registration"
			$('#againstBudget').on("change", function() {
				if ($("#againstBudget option:selected").val() == "0") {
					$('#budgetSourceId').removeAttr('required');
					$('#budgetCodeId').removeAttr('required');
				}
				if ($("#againstBudget option:selected").val() == "1") {
					$('#budgetSourceId').attr('required', true);
					$('#budgetCodeId').attr('required', true);
				}
			});
			$("form[name='reg']").validate({
				// Specify validation rules
				rules : {
					// The key name on the left side is the name attribute
					// of an input field. Validation rules are defined
					// on the right side
					pointId : "required",
					againstBudget : "required",
					dlvAmount : "required",
					goodsTypeId : "required",
					dlvDate : "required"

				},
				// Specify validation error messages
				messages : {
					pointId : "Please Select Point",
					againstBudget : "Please Select Budget Type",
					budgetCodeId : "Please Select Budget Code",
					budgetSourceId : "Please Select Budget Source",
					dlvAmount : "Please enter Delivery Amount(BDT) in Lukh",
					goodsTypeId : "Please Select Type of Goods",
					dlvDate : "Please Select Delivery Date"
				},
				// Make sure the form is submitted to the destination defined
				// in the "action" attribute of the form when valid
				submitHandler : function(form) {
					form.submit();
				}
			});
		});

		$(function() {
			var month = (new Date()).getMonth() + 1;
			var year = (new Date()).getFullYear();

			$('input[type=eu-date]').w2field('date', {
				format : 'dd-mm-yyyy'
			});

			// US Format
			$('input[type=eu-date1]').w2field('date', {
				format : 'dd-mm-yyyy',
				end : $('input[type=eu-date2]')
			});
			$('input[type=eu-date2]').w2field('date', {
				format : 'dd-mm-yyyy',
				start : $('input[type=eu-date1]')
			});
		});
	</script>



</body>
</html>