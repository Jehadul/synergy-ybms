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
		<h2 class="pull-left" style="color: #428bca;">All Allotment Source Wise Report</h2>
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
								id="pointTypeForm" class="form-horizontal"
								modelAttribute="command" accept-charset="UTF-8"
								action="${pageContext.request.contextPath}/savePoint">
								
								<div class="form-group">
									<label class="col-lg-2 control-label">Fiscal Year <span class="red">*</span>: </label>
									<div class="col-lg-5">
										<form:select path="fiscalYearId" id="fiscalYearId" class="form-control basic-single-select2">			
											<form:option value="">Select One</form:option>
											<c:forEach items="${fiscalYearList}" var="fiscalYear">
												<form:option value="${fiscalYear.id}">${fiscalYear.name}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div> 
								
								<div class="form-group">
									<label class="col-lg-2 control-label">Budget Source <span class="red">*</span>: </label>
									<div class="col-lg-5">
										<form:select path="budgetSourceId" id="budgetSourceId" class="form-control basic-single-select2">			
											<form:option value="">Select One</form:option>
											<c:forEach items="${budgetSourceList}" var="budgetSource">
												<form:option value="${budgetSource.id}">${budgetSource.name}</form:option>
											</c:forEach>
										</form:select>
									</div>
								</div>
								

								<div class="form-group">
									<div class="col-lg-offset-2 col-lg-1 col-xs-4" id="">
										<button type="submit"
											class="btn btn-sm btn-primary req-save-update-btn">
											Generate</button>
									</div>
									<div class="col-lg-1 col-xs-4">
										<button type="reset" class="btn btn-sm btn-danger ">Reset</button>
									</div>
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
			$(function() {
		// Initialize form validation on the registration form.
		// It has the name attribute "registration"
		$("form[name='reg']").validate({
			// Specify validation rules
			rules : {
				// The key name on the left side is the name attribute
				// of an input field. Validation rules are defined
				// on the right side
				fiscalYearId : "required",
				budgetSourceId : "required"
			},
			// Specify validation error messages
			messages : {
				fiscalYearId : "Please Select Fiscal Year",
				budgetSourceId : "Please Select Budget Source"
			},
			// Make sure the form is submitted to the destination defined
			// in the "action" attribute of the form when valid
			submitHandler : function(form) {
				form.submit();
			}
		});
	});
	</script>



</body>
</html>