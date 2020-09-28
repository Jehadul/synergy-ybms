<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<!-- Title and other stuffs -->
<!-- <title>Dashboard - Lexicon Merchandise</title> -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="author" content="">

<link href="resource/w2ui/w2ui-1.5.rc1.min.css" rel="stylesheet">
<link href="resource/w2ui/w2ui-1.5.rc1.css" rel="stylesheet">
<script src="resource/w2ui/w2ui-1.5.rc1.min.js"></script>
<script src="resource/w2ui/w2ui-1.5.rc1.js"></script>

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
<script src="resource/dataTable/buttons.print.min.js"></script>




<style type="text/css">

textarea {
    resize: none;
}

td img {
	display: block;
	margin-left: auto;
	margin-right: auto;
}

.centered {
	width: 50px;
	margin: 0px, auto, 0px, auto;
}

.custom-width {
    /* display: grid; */
    display: -webkit-inline-box
    margin-bottom: 5px;
    font-weight: normal;
    text-align: center;
    vertical-align: middle;
    -ms-touch-action: manipulation;
    touch-action: manipulation;
    cursor: pointer;
    background-image: none;
    border: 1px solid transparent;
    white-space: nowrap;
    padding: 6px 12px;
    font-size: 13px;
    line-height: 1.42857143;
    border-radius: 2px;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
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

	<input type="hidden" value="${pageContext.request.contextPath}"
		id="contextPath">

	<div class="page-head">
		<h2 class="pull-left" style="color: #428bca;">Bill List</h2>
		<div class="clearfix"></div>
	</div>

	<!--   <div class="matter"> -->
	<div class="container">

		<div class="row">

			<div class="col-md-12">

				<!-- Table -->

				<div class="row">

					<div class="col-md-12">

						<div class="widget">

							<div class="widget-head">
								<div class="pull-left">Bill List Details</div>&nbsp;&nbsp;&nbsp;&nbsp;
								<span style="color: green;">${successMsg}</span>
								<div class="widget-icons pull-right">
									<a href="#" class="wminimize"><i class="fa fa-chevron-up"></i></a>
									<a href="#" class="wclose"><i class="fa fa-times"></i></a>
								</div>
								<div class="clearfix"></div>
							</div>

							<div class="widget-content">
								<div class="table-responsive">

									<c:if test="${!empty billList}">
										<table class="table table-bordered table-hover" id="table1">
											<thead>
												<tr style="background-color: #428bca; color: white">
													<th class="text-center">Sl. No.</th>
													<th class="text-center">Fiscal Year</th>
													<th class="text-center">Point Name</th>
													<th class="text-center">Goods Type</th>
													<th class="text-center">Budget Source</th>
													<th class="text-center">Budget Code</th>
													<th class="text-center">Amount  (BDT) Lakh </th>
													
													<th class="text-center">Created By</th>
													<th class="text-center">Created Date</th>
													<th class="text-center">Last Modified By</th>
													<th class="text-center">Last Modified Date</th>
													<th class="text-center">Remarks</th>
													<th class="text-center">Action</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${billList}" var="bill"
													varStatus="bgt">
													<tr>
														<td class="text-center">${bgt.count}</td>
														<td class="text-center">${bill.fiscalYear.name}</td>
														<td class="text-center">${bill.point.name} - (${bill.point.keyword})</td>
														<td class="text-center">${bill.goodsType.name}</td>
														<td class="text-center">${bill.budgetSource.name}</td>
														<td class="text-center">${bill.budgetCode.name}</td>
														<td class="text-center">${bill.billAmount}</td>
														<td class="text-center">${bill.createdBy}</td>
														<td class="text-center"><fmt:formatDate value="${bill.createdDate}" pattern="dd-MM-yyyy HH:MM:ss a"/> </td>
														<td class="text-center">${bill.modifiedBy}</td>
														<td class="text-center"><fmt:formatDate value="${bill.modifiedDate}" pattern="dd-MM-yyyy HH:MM:ss a"/></td>
														<td class="text-center">${bill.remarks}</td>
														<td class="text-center">
															<a class="btn btn-primary custom" href="editBill?id=${bill.id}">Edit</a>
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

			</div>

		</div>

	</div>
	<!-- table ends -->
	<!-- Mainbar ends -->
	<div class="clearfix"></div>


</body>
</html>