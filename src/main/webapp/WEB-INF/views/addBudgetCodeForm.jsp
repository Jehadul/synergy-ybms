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


<script type="text/javascript">
	function goBack() {
		window.history.back();
	}

	$(document).ready(function() {
		var table = $('#table1').DataTable({
			dom : 'Bfrtip',
			buttons : [ 'copy', 'csv', {
				extend : 'excel',
				exportOptions : {
					columns : ':visible'
				}
			}, 'print', {
				extend : 'pdfHtml5',
				orientation : 'landscape',
				pageSize : 'LEGAL',
				exportOptions : {
					columns : ':visible'
				}
			} ]
		});
	});
</script>

</head>

<body>
	<!-- Page heading start -->
	<div class="page-head">
		<h2 class="pull-left" style="color: #428bca;">Budget Code Entry
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
								id="budgetCodeForm" class="form-horizontal"
								modelAttribute="command" accept-charset="UTF-8"
								action="${pageContext.request.contextPath}/saveBudgetCode">
								
								<form:input type="hidden" path="id" value="${budgetCode.id}"
									readonly="true" class="form-control" />
									
								<div class="form-group">
									<label class="col-lg-2 control-label"></label>
									<div class="col-lg-5">
										<span style="color: green;">${successMsg}</span>
									</div>
								</div>

								<div class="form-group">
									<label class="col-lg-2 control-label">Budget Code <span class="red">*</span>: </label>
									<div class="col-lg-5">
										<form:input id="name" path="name"
											value="${budgetCode.name}" class="form-control" />
									</div>
								</div>
								
								<div class="form-group">
									<label class="col-lg-2 control-label">Remarks: </label>
									<div class="col-lg-5">
										<textarea id="remarks" class="form-control" name="remarks"
											rows="4" type="textarea"><c:out
												value="${budgetCode.remarks}" /></textarea>
									</div>
								</div>


								<div class="form-group">
									<c:choose>
										<c:when test="${edit}">
											<div class="col-lg-offset-2 col-lg-1 col-xs-4" id="">
												<button type="submit"
													class="btn btn-sm btn-primary btn-success req-save-update-btn">
													Update</button>
											</div>
											<div class="col-lg-1 col-xs-4">

												<a class="btn btn-sm btn-danger"
													href="${pageContext.request.contextPath}/">Cancel</a>
											</div>
										</c:when>
										<c:otherwise>
											<div class="col-lg-offset-2 col-lg-1 col-xs-4" id="">
												<button type="submit"
													class="btn btn-sm btn-primary req-save-update-btn">
													Submit</button>
											</div>
											<div class="col-lg-1 col-xs-4">
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
				<div class="row">
					<div class="col-md-12">
						<div class="widget">
							<div class="widget-head">
								<div class="pull-left">Budget Code Details</div>
								<div class="widget-icons pull-right">
									<a href="#" class="wminimize"><i class="fa fa-chevron-up"></i></a>
									<a href="#" class="wclose"><i class="fa fa-times"></i></a>
								</div>
								<div class="clearfix"></div>
							</div>
							<div class="widget-content">
								<div class="table-responsive">
									<c:if test="${!empty budgetCodeList}">
										<table class="table table-striped table-bordered table-hover"
											id="table1">
											<thead>
												<tr style="background-color: #5cb85c; color: white">
													<th class="text-center">SL No.</th>
													<th class="text-center">Budget Code</th>
													<th class="text-center">Remarks</th>
													<th class="text-center">Action</th>

												</tr>
											</thead>
											<tbody>
												<c:forEach items="${budgetCodeList}" var="budgetCode" varStatus="slNo">
													<tr>
														<td class="text-center"><c:out value="${slNo.count}" /></td>
														<td class="text-center"><c:out value="${budgetCode.name}" /></td>
														<td class="text-center"><c:out value="${budgetCode.remarks}" /></td>
														<td class="text-center">
															<a class="btn btn-primary custom" href="editBudgetCode?id=${budgetCode.id}">Edit</a> | 
															<a class="btn btn-danger custom" onclick="deleteBudgetCode(${budgetCode.id});" href="#">Delete</a>
														</td>
													</tr>
												</c:forEach>

											</tbody>
										</table>
									</c:if>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- table ends -->

			</div>

		</div>

	</div>



	<!-- Matter ends -->

	<!-- Mainbar ends -->
	<div class="clearfix"></div>

	<script>
		function deleteBudgetCode(budgetCodeId){
			var contextPath = $('#contextPath').val();	
			
			w2confirm('Do you want to Delete this Budget Code?', function btn(answer) {
			    if(answer == 'Yes'){
			    	window.location.href = contextPath+"/deleteBudgetCode?id="+budgetCodeId;
			    } 
			});
		}
		
		$(function() {
			// Initialize form validation on the registration form.
			// It has the name attribute "registration"
			$("form[name='reg']").validate({
				// Specify validation rules
				rules : {
					// The key name on the left side is the name attribute
					// of an input field. Validation rules are defined
					// on the right side
					name : "required"
				},
				// Specify validation error messages
				messages : {
					name : "Please enter Budget Code"
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