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
		<h2 class="pull-left" style="color: #428bca;">Budget Entry
			Form</h2>
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
								id="budgetForm" class="form-horizontal"
								modelAttribute="command" accept-charset="UTF-8"
								action="${pageContext.request.contextPath}/saveBudget">
								
								<form:input type="hidden" path="id" value="${budget.id}"
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
											<input id="fiscalYear" readonly="readonly" disabled="disabled" value="${budget.fiscalYear.name}" class="form-control" >
										</c:when>
										<c:otherwise>
											<input id="fiscalYear" readonly="readonly" disabled="disabled" value="${fiscalYear}" class="form-control" >
										</c:otherwise>
										</c:choose>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-lg-2 control-label">Sharok No<span class="red">*</span>: </label>
									<div class="col-lg-5">
										<c:choose>
										<c:when test="${edit}">
											<input id="smarokNo" readonly="readonly" disabled="disabled" value="${budget.smarokNo}" class="form-control" >
										</c:when>
										<c:otherwise>
											<input id="smarokNo" name="smarokNo" value="${budget.smarokNo}" class="form-control" >
										</c:otherwise>
										</c:choose>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-lg-2 control-label">Budget Date<span
										class="red">*</span>:
									</label>
									<div class="col-lg-5">
									<c:choose>
										<c:when test="${edit}">
										<input type="eu-date" class="form-control" name="bgtDate"
											id="bgtDate" value="${budget.bgtDate}" readonly="readonly" disabled="disabled">
											</c:when>
										<c:otherwise>
										<input type="eu-date" class="form-control" name="bgtDate"
											id="bgtDate" value="${budget.bgtDate}">
										</c:otherwise>
										</c:choose>
									</div>
								</div>

								<div class="form-group">
									<label class="col-lg-2 control-label">Point <span class="red">*</span>: </label>
									<div class="col-lg-5">
										<form:select path="pointId" id="pointId" class="form-control basic-single-select2">			
											<form:option value="" selected="selected">Select One</form:option>
											<c:forEach items="${pointList}" var="point">

												<c:if test="${budget.point.id eq point.id}">
													<form:option value="${point.id}" selected="selected">${point.name} - (${point.keyword})</form:option>
												</c:if>
												<c:if test="${budget.point.id ne point.id}">
													<form:option value="${point.id}">${point.name} - (${point.keyword})</form:option>
												</c:if>
											</c:forEach>
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

												<c:if test="${budget.goodsType.id eq goodsType.id}">
													<form:option value="${goodsType.id}" selected="selected">${goodsType.name}</form:option>
												</c:if>
												<c:if test="${budget.goodsType.id ne goodsType.id}">
													<form:option value="${goodsType.id}">${goodsType.name}</form:option>
												</c:if>
											</c:forEach>
										</form:select>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-lg-2 control-label">Source <span class="red">*</span>: </label>
									<div class="col-lg-5">
										<form:select path="budgetSourceId" id="budgetSourceId" class="form-control basic-single-select2">			
											<form:option value="" selected="selected">Select One</form:option>
											<c:forEach items="${budgetSourceList}" var="budgetSource">

												<c:if test="${budget.budgetSource.id eq budgetSource.id}">
													<form:option value="${budgetSource.id}" selected="selected">${budgetSource.name}</form:option>
												</c:if>
												<c:if test="${budget.budgetSource.id ne budgetSource.id}">
													<form:option value="${budgetSource.id}">${budgetSource.name}</form:option>
												</c:if>
											</c:forEach>
										</form:select>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-lg-2 control-label">Budget Code <span class="red">*</span>: </label>
									<div class="col-lg-5">
										<form:select path="budgetCodeId" id="budgetCodeId" class="form-control basic-single-select2">			
											<form:option value="" selected="selected">Select One</form:option>
											<c:forEach items="${budgetCodeList}" var="budgetCode">

												<c:if test="${budget.budgetCode.id eq budgetCode.id}">
													<form:option value="${budgetCode.id}" selected="selected">${budgetCode.name}</form:option>
												</c:if>
												<c:if test="${budget.budgetCode.id ne budgetCode.id}">
													<form:option value="${budgetCode.id}">${budgetCode.name}</form:option>
												</c:if>
											</c:forEach>
										</form:select>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-lg-2 control-label">Color <span class="red">*</span>: </label>
									<div class="col-lg-5">
										<form:select path="colorCodeId" id="colorCodeId" class="form-control basic-single-select2">			
											
											<c:forEach items="${colorCodeList}" var="colorCode">

												<c:if test="${budget.colorCode.id eq colorCode.id}">
													<form:option value="${colorCode.id}" selected="selected">${colorCode.name}</form:option>
												</c:if>
												<c:if test="${budget.colorCode.id ne colorCode.id}">
													<form:option value="${colorCode.id}">${colorCode.name}</form:option>
												</c:if>
											</c:forEach>
										</form:select>
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-lg-2 control-label">Budget Amount (BDT)<span class="red">*</span>: </label>
									<div class="col-lg-5">
										<input type="number" name="bgtAmount" id="bgtAmount" value="${budget.bgtAmount}" class="form-control" >									
									</div> (Lakh.)
								</div>
								
								<div class="form-group">
									<label class="col-lg-2 control-label">Bill Amount (BDT): </label>
									<div class="col-lg-5">
										<input type="number" name="billAmount" id="billAmount" value="${budget.billAmount}" class="form-control" >									
									</div> (Lakh.)
								</div>
								
								<div class="form-group">
									<label class="col-lg-2 control-label">Remarks: </label>
									<div class="col-lg-5">
										<textarea id="remarks" class="form-control" name="remarks"
											rows="4" type="textarea"><c:out
												value="${budget.remarks}" /></textarea>
									</div>
								</div>


								<div class="form-group">
									<c:choose>
										<c:when test="${edit}">
											<div class="col-xs-offset-2  col-xs-1" id="">
												<button type="submit"
													class="btn btn-sm btn-primary btn-success req-save-update-btn">
													Update</button>													
											</div>
											<div class="col-xs-2">											
												<button type="submit"
													class="btn btn-sm btn-primary req-save-update-btn" value="go-to-list" name="goToList">
													Update and go to List</button>
											</div>
											<div class="col-xs-2">
												<a class="btn btn-sm btn-danger"
													href="${pageContext.request.contextPath}/">Cancel</a>
											</div>
										</c:when>
										<c:otherwise>
											<div class="col-xs-offset-2 col-xs-1" id="">
												<button type="submit"
													class="btn btn-sm btn-success req-save-update-btn">
													Submit</button>													
											</div>
											<div class="col-xs-2">
												<button type="submit"class="btn btn-sm btn-primary req-save-update-btn" value="go-to-list" name="goToList">
													Submit and go to List</button>
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
	});	
	
		$(function() {
			// Initialize form validation on the registration form.
			// It has the name attribute "registration"
			$("form[name='reg']").validate({
				// Specify validation rules
				rules : {
					// The key name on the left side is the name attribute
					// of an input field. Validation rules are defined
					// on the right side
					pointId : "required",
					budgetSourceId : "required",
					budgetCodeId : "required",
					colorCodeId : "required",
					bgtAmount : "required",
					goodsTypeId : "required",
					bgtDate : "required"
				},
				// Specify validation error messages
				messages : {
					pointId : "Please Select Point",
					budgetSourceId : "Please Select Budget Source",
					budgetCodeId : "Please Select Budget Code",
					colorCodeId : "Please Select Color code",
					bgtAmount : "Please enter Budget Amount(BDT) in Lukh", 
					goodsTypeId : "Please Select Type of Goods",
					bgtDate : "Please Select Budget Date"
				},
				// Make sure the form is submitted to the destination defined
				// in the "action" attribute of the form when valid
				submitHandler : function(form) {
					form.submit();
				}
			});
			
			
			$("#bgtAmount, #billAmount").blur(function(){
				if(parseFloat($("#bgtAmount").val()) < parseFloat($("#billAmount").val())){
					$("#billAmount").val(0.0);
					w2alert("Bill Amount cannot greater than Budget Amount. Please Try again!!");
				}
			    
			});
		});
		
		$(function() {
			var month = (new Date()).getMonth() + 1;
			var year = (new Date()).getFullYear();
			
			$('input[type=eu-date]').w2field('date',  { format: 'dd-mm-yyyy' });
			
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