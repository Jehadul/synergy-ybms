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
		<h2 class="pull-left" style="color: #428bca;">Stock Report Entry
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
								id="stockReportForm" class="form-horizontal"
								modelAttribute="command" accept-charset="UTF-8"
								action="${pageContext.request.contextPath}/updateStockReport">
								
								<form:input type="hidden" path="id" value="${stockReport.id}"
									readonly="true" class="form-control" />
									
								<div class="form-group">
									<label class="col-lg-2 control-label"></label>
									<div class="col-lg-5">
										<span style="color: green;">${successMsg}</span>
									</div>
								</div>

								<div class="form-group">
									<label class="col-lg-2 control-label">Warehouse: </label>
									<div class="col-lg-5">
										<input id="warehouse" name="warehouse" type="number" readonly="readonly"
											value="${stockReport.warehouse}" class="form-control" />
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-lg-2 control-label">Local Currency: </label>
									<div class="col-lg-5">
										<input id="localCurrency" name="localCurrency" type="number" readonly="readonly"
											value="${stockReport.localCurrency}" class="form-control" />
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-lg-2 control-label">Work Order: </label>
									<div class="col-lg-5">
										<input id="workOrder" name="workOrder" type="number" readonly="readonly"
											value="${stockReport.workOrder}" class="form-control" />
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-lg-2 control-label">Delivered At Point: </label>
									<div class="col-lg-5">
										<input id="dlvAtPoint" name="dlvAtPoint" type="number" readonly="readonly"
											value="${stockReport.dlvAtPoint}" class="form-control" />
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-lg-2 control-label"><b>Total: </b> </label>
									<div class="col-lg-5"> <b>
										<input id="grantTotal" disabled="disabled" readonly="readonly" type="number"
											class="form-control" /></b>
									</div>
								</div>


								<div class="form-group">
									<div class="col-lg-offset-2 col-lg-1 col-xs-4" id="">
										<button type="button" hidden="true"
											class="btn btn-sm btn-success req-save-update-btn">
											Update</button>
											
											<button type="button"
											class="btn btn-sm btn-info req-edit-btn">
											Edit</button>
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
		$(document).on("click", '.req-edit-btn', function(){
			$('#warehouse').prop('readOnly', false);
			$('#localCurrency').prop('readOnly', false);
			$('#workOrder').prop('readOnly', false);
			$('.req-save-update-btn').prop('hidden', false);
			$(this).prop('hidden', true);
		});
		
		$(document).on("click", '.req-save-update-btn', function(){
			$('#warehouse').prop('readOnly', true);
			$('#localCurrency').prop('readOnly', true);
			$('#workOrder').prop('readOnly', true);
			$('.req-edit-btn').prop('hidden', false);
			$(this).prop('hidden', true);
			w2confirm('Are you sure?', function btn(answer) {
			    console.log(answer); // Yes or No -- case-sensitive
			    if(answer == 'Yes'){
			    	$('#stockReportForm').submit();
			    }
			});
			
			
		});
		
		$(document).on("blur", '#warehouse, #localCurrency, #workOrder', function(){
			var warehouse = parseFloat($('#warehouse').val());
			var localCurrency = parseFloat($('#localCurrency').val());
			var workOrder = parseFloat($('#workOrder').val());
			var dlvAtPoint = parseFloat($('#dlvAtPoint').val());
			var gt = warehouse+localCurrency+workOrder+dlvAtPoint;
			$('#grantTotal').val(gt);			
		});
		
		$(function() {
			var warehouse = parseFloat($('#warehouse').val());
			var localCurrency = parseFloat($('#localCurrency').val());
			var workOrder = parseFloat($('#workOrder').val());
			var dlvAtPoint = parseFloat($('#dlvAtPoint').val());
			var gt = warehouse+localCurrency+workOrder+dlvAtPoint;
			$('#grantTotal').val(gt);
			// Initialize form validation on the registration form.
			// It has the name attribute "registration"
			$("form[name='reg']").validate({
				// Specify validation rules
				rules : {
					// The key name on the left side is the name attribute
					// of an input field. Validation rules are defined
					// on the right side
					name :  {
					      required: true,
					      maxlength: 255
					    }

				},
				// Specify validation error messages
				messages : {
					name : "Please enter Budget Source"
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