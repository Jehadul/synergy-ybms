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
	<link href="resource/dataTable/buttons.dataTables.min.css"
	rel="stylesheet">
<script src="resource/dataTable/jquery-1.12.3.js"></script>
<script src="resource/dataTable/jquery.dataTables.min.js"></script>
<script src="resource/dataTable/dataTables.buttons.min.js"></script>
<script src="resource/dataTable/buttons.flash.min.js"></script>
<script src="resource/dataTable/jszip.min.js"></script>
<script src="resource/dataTable/pdfmake.min.js"></script>
<script src="resource/dataTable/vfs_fonts.js"></script>
<script src="resource/dataTable/buttons.html5.min.js"></script>
	
<style type="text/css">
form .error {
	color: #ff0000;
}
</style>
<style type="text/css">


.com_row td {
	border: 0px;
}
table thead tr th{
	text-align: center;
	border: 2px solid;
}
table tbody tr td{
	text-align: center;
}
.widget .table * {
    border-color: #180909 !important;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
$("[id$=myButtonControlID]").click(function(e) {
	window.open('data:application/vnd.ms-excel,' + encodeURIComponent( $('div[id$=divTableDataHolder]').html()));
	e.preventDefault();
}); 
}); 
</script>

</head>

<body>
	<!-- Page heading start -->
	<div class="page-head">
		<h2 class="pull-left" style="color: #428bca;">Allotment Color Code Reports</h2>
			<h2><button id="myButtonControlID" class="btn btn-info pull-right">Export To Excel</button></h2>
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

					<div class="widget-content" id="divTableDataHolder">
						<div class="padd">

							<br />

							<!-- Form starts.  -->
							<div>
							<table class="table " id="tableDynamic" border="1">
								<thead>
								<tr class="com_row">
										
										<th colspan="5" style="text-align: center;font-size: 20px">FORM-9</th>
										
									</tr>
									<tr class="com_row">
										
										<th colspan="3" style="text-align: center;font-size: 20px">COLOR CODE</th>
										<th colspan="2" style="text-align: center;font-size: 20px">${curentDate}</th>
										
									</tr>	
									<tr>
										<th style="vertical-align:middle;" valign="middle">SL. NO</th>
										<th style="vertical-align:middle;" valign="middle">Zone</th>
										<th style="vertical-align:middle;" valign="middle">Incharge</th>
										<th style="vertical-align:middle;" valign="middle">Amount</th>
										<th style="vertical-align:middle;" valign="middle">In total</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${codeStatisticList}" var="tmldStatic" varStatus="slNo">
										<tr>
										<c:if test = "${tmldStatic.slNO==1}">
										<td rowspan="${size}" style="vertical-align:middle;padding-left: 35px" valign="middle">1</td>
										 </c:if>
										<c:if test = "${tmldStatic.slNO==1}">
										<td rowspan="${size}" style="background: #1ce31c;text-align: center;font-size: 20px;vertical-align:middle;" valign="middle">Green</td>
										 </c:if>
										<td>${tmldStatic.teamLeadername}</td>
										<td>${tmldStatic.totalGreenAmount}</td>
										<c:if test = "${tmldStatic.slNO==1}">
										<td rowspan="${size}" style="background: #1ce31c;text-align: center;font-size: 20px;vertical-align:middle;" valign="middle">${totalGreen}</td>
										</c:if>
									</tr>
									</c:forEach>				
									
									<tr class="com_row">
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>

									</tr>
									
									<c:forEach items="${codeStatisticList}" var="tmldStatic" varStatus="slNo">
										<tr>
										<c:if test = "${tmldStatic.slNO==1}">
										<td rowspan="${size}" style="vertical-align:middle;padding-left: 35px" valign="middle">2</td>
										 </c:if>
										<c:if test = "${tmldStatic.slNO==1}">
										<td rowspan="${size}" style="background: #0f89b0;text-align: center;font-size: 20px;vertical-align:middle;" valign="middle">Blue</td>
										 </c:if>
										<td>${tmldStatic.teamLeadername}</td>
										<td>${tmldStatic.totalBlueAmount}</td>
										<c:if test = "${tmldStatic.slNO==1}">
										<td rowspan="${size}" style="background: #0f89b0;text-align: center;font-size: 20px;vertical-align:middle;" valign="middle">${totalBlue}</td>
										</c:if>
									</tr>
									</c:forEach>				
									
									<tr class="com_row">
										<td></td>
										<td></td>
										<td></td>
										<td></td>
										<td></td>

									</tr>
									
									<c:forEach items="${codeStatisticList}" var="tmldStatic" varStatus="slNo">
										<tr>
										<c:if test = "${tmldStatic.slNO==1}">
										<td rowspan="${size}" style="vertical-align:middle;padding-left: 35px" valign="middle">3</td>
										 </c:if>
										<c:if test = "${tmldStatic.slNO==1}">
										<td rowspan="${size}" style="background: red;text-align: center;font-size: 20px;vertical-align:middle;" valign="middle">Red</td>
										 </c:if>
										<td>${tmldStatic.teamLeadername}</td>
										<td>${tmldStatic.totalRedAmount}</td>
										<c:if test = "${tmldStatic.slNO==1}">
										<td rowspan="${size}" style="background: red;text-align: center;font-size: 20px;vertical-align:middle;" valign="middle">${totalRed}</td>
										</c:if>
									</tr>
									</c:forEach>	
									<tr class="com_row">
										
										<td colspan="4" style="text-align: center;">G.T</td>
										<td style="text-align: center;">${totalAmount}</td>

									</tr>			
									
									

								</tbody>
							</table>
							</div>
							
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