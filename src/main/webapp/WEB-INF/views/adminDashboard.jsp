<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<link href="resource/tabs/jquery-ui.css" rel="stylesheet">
<script src="resource/tabs/jquery-ui.js"></script>


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
	
	$( function() {
	    $( "#tabs" ).tabs();
	}); 

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
		
		var table2 = $('#table2').DataTable({
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
		
		var table3 = $('#table3').DataTable({
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
		
		var table4 = $('#table4').DataTable({
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

		$('a.toggle-vis').on('click', function(e) {
			e.preventDefault();
			var column = table.column($(this).attr('data-column'));
			column.visible(!column.visible());
		});

		$('input.toggle-vis').on('change', function(e) {
			e.preventDefault();
			var column = table.column($(this).attr('data-column'));
			column.visible(!column.visible());
		});
		
		$('a.toggle-vis2').on('click', function(e) {
			e.preventDefault();
			var column = table2.column($(this).attr('data-column'));
			column.visible(!column.visible());
		});

		$('input.toggle-vis2').on('change', function(e) {
			e.preventDefault();
			var column = table2.column($(this).attr('data-column'));
			column.visible(!column.visible());
		});
		
		$('a.toggle-vis3').on('click', function(e) {
			e.preventDefault();
			var column = table3.column($(this).attr('data-column'));
			column.visible(!column.visible());
		});

		$('input.toggle-vis3').on('change', function(e) {
			e.preventDefault();
			var column = table3.column($(this).attr('data-column'));
			column.visible(!column.visible());
		});
		
		$('a.toggle-vis4').on('click', function(e) {
			e.preventDefault();
			var column = table4.column($(this).attr('data-column'));
			column.visible(!column.visible());
		});

		$('input.toggle-vis4').on('change', function(e) {
			e.preventDefault();
			var column = table4.column($(this).attr('data-column'));
			column.visible(!column.visible());
		});

	});

	function confirmationDelete(id) {
		// href="removeDbConnect?id=${dbCon.id}"
		var contextPath = $('#contextPath').val();
		var url = contextPath + '/removeAi?id=' + id;
		w2confirm('Are you sure remove this permanently?',
				function btn(answer) {
					if (answer == 'Yes') {
						window.location = url;
					} else {
						//
					}
				});
		return false;
	}
</script>

</head>

<body>

	<input type="hidden" value="${pageContext.request.contextPath}"
		id="contextPath">

	<div class="page-head">
		<h2 class="pull-left" style="color: #428bca;">HOME | DASHBOARD</h2>
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
						
						<div id="tabs">
							<ul>
								<li><a href="#tabs-todays-pending">Todays Pending Candidate</a></li>
								
								<li><a href="#tabs-all-pending">All Pending Candidate</a></li>
								
								<li><a href="#tabs-todays-joined">Today's Joined Candidate</a></li>
								
								<li><a href="#tabs-joined-candidate">All Joined Candidate</a></li>
								
							</ul>
							
							<div id="tabs-todays-pending">
								<div>
									<label><input type="checkbox" class="toggle-vis"
									data-column="0" checked="checked"> Sl. No</label> <label><input
									type="checkbox" class="toggle-vis" data-column="1"
									checked="checked"> Candidate ID</label> <label><input
									type="checkbox" class="toggle-vis" data-column="2"
									checked="checked">Candidate Name</label>
									<!-- <label><input
									type="checkbox" class="toggle-vis" data-column="2"
									checked="checked">Department Name</label>
									<label><input
									type="checkbox" class="toggle-vis" data-column="2"
									checked="checked">Designation </label> -->
									 <label><input
									type="checkbox" class="toggle-vis" data-column="3"
									checked="checked"> Recruit By</label> <label><input
									type="checkbox" class="toggle-vis" data-column="4"
									checked="checked"> Recruit For</label> <label><input
									type="checkbox" class="toggle-vis" data-column="5"
									checked="checked"> Source</label> <label><input
									type="checkbox" class="toggle-vis" data-column="6"
									checked="checked"> Date of Join</label> <label><input
									type="checkbox" class="toggle-vis" data-column="7"
									checked="checked"> Email</label> <label><input
									type="checkbox" class="toggle-vis" data-column="8"
									checked="checked"> Contact No</label> <label><input
									type="checkbox" class="toggle-vis" data-column="9"
									checked="checked"> Status</label> <label><input
									type="checkbox" class="toggle-vis" data-column="10"
									checked="checked"> Remarks</label> 
									
									<label><input
									type="checkbox" class="toggle-vis" data-column="11"
									checked="checked"> Reject Reason</label> 
									
									<label><input
									type="checkbox" class="toggle-vis" data-column="12"
									checked="checked"> Action</label>
								</div>
								<div class="widget-content">
									<div class="table-responsive">	
										<c:if test="${!empty todaysJoiningCandidateList}">
											<table class="table table-bordered table-hover" id="table1">
												<thead>
													<tr style="background-color: #428bca; color: white">
														<th class="text-center">Sl. No.</th>
														<th class="text-center">Candidate ID</th>
														<th class="text-center">Candidate Name</th>
														<!-- <th class="text-center">Department Name</th>
														<th class="text-center">Designation</th> -->
														<th class="text-center">Recruit By</th>
														<th class="text-center">Recruit For</th>
														<th class="text-center">Source</th>
														<th class="text-center">Date Of Join</th>
														<th class="text-center">Email</th>
														<th class="text-center">Contact No</th>
														<th class="text-center">Status</th>
														<th class="text-center">Remarks</th>
														<th class="text-center">Reject Reason</th>
														<th class="text-center">Action</th>
	
	
	
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${todaysJoiningCandidateList}" var="candidate"
														varStatus="cand">
														<tr>
															<td class="text-center">${cand.count}</td>
															<td class="text-center">${candidate.candId}</td>
															<td class="text-center">${candidate.candName}</td>
															<%-- <td class="text-center">${candidate.department.name}</td>
															<td class="text-center">${candidate.designation.name}</td> --%>
															<td class="text-center">${candidate.hrEmployee.name}
																- (${candidate.hrEmployee.empId})</td>
															<td class="text-center">${candidate.company.name}</td>
															<td class="text-center">${candidate.sourceOfCand}</td>
															<td class="text-center">${candidate.dateOfJoin}</td>
															<td class="text-center">${candidate.candEmail}</td>
															<td class="text-center">${candidate.candMobileNo}</td>
															<td class="text-center"><c:if
																	test="${candidate.status eq 1}">
																	<p class="btn btn-primary btn-sm">Ready For Sending
																		To Candidate</p>
																</c:if> <c:if test="${candidate.status eq 3}">
																	<p class="btn btn-warning btn-sm">Waiting For
																		Candidate Information</p>
																</c:if> <c:if test="${candidate.status eq 4}">
																	<p class="btn btn-info btn-sm">Ready For Appointment
																		Letter Approve</p>
																</c:if> <c:if test="${candidate.status eq 5}">
																	<p class="btn btn-info btn-sm">Pending Candidate Response
																	</p>
																</c:if>
																
																<c:if test="${candidate.status eq 14}">
																	<p class="btn btn-success btn-sm">Candidate Joining
																	</p>
																</c:if>
																
																<c:if test="${candidate.status eq 15}">
																	<p class="btn btn-warning btn-sm">Rejected By Candidate
																	</p>
																</c:if>														
																
																</td>
															<td class="text-center">${candidate.remarks}</td>
															<td class="text-center">${candidate.rejectReasonByCandidate}</td>
															<td align="center">
																<c:if test="${candidate.status eq 1}"> 
																	<a class="btn btn-sm btn-primary custom-width" title="Edit" target="_blank"
																	href="${pageContext.request.contextPath}/editEmployee?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
																	
																	<a class="btn btn-sm btn-success custom-width" title="Send To" onclick="doSend(${candidate.id})"
																	href="#" style="color: white;"><i class="fa fa-inbox"></i></a>
																	
																	<a class="btn btn-sm btn-danger custom-width" title="Delete" onclick="doDelete(${candidate.id})"
																	href="#" style="color: white;"><i class="fa fa-trash" aria-hidden="true"></i></a>
																</c:if>
																
																<c:if test="${candidate.status eq 3}"> 
																	
																	<a class="btn btn-sm btn-info custom-width" title="Profile View" target="_blank"
																	href="${pageContext.request.contextPath}/candidateProfileForm?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-street-view"> </i></a>
																	
																	<a class="btn btn-sm btn-primary custom-width" title="Edit" target="_blank"
																	href="${pageContext.request.contextPath}/editEmployee?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
																	
																	<a class="btn btn-sm btn-warning custom-width" title="Resend Email to Candidate"  onclick="doReSendEmail(${candidate.id})"
																	href="#" style="color: white;"><i class="fa fa-check" aria-hidden="true"></i></a>
																	
																	<a class="btn btn-sm btn-danger custom-width" title="Decline" onclick="doDecline(${candidate.id})"
																	href="#" style="color: white;"><i class="fa fa-times"></i></a>
																</c:if>
																
																<c:if test="${candidate.status eq 4}"> 
																	<a class="btn btn-sm btn-info custom-width" title="Profile View" target="_blank"
																	href="${pageContext.request.contextPath}/candidateProfileForm?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-street-view"> </i></a>
																	
																	<a class="btn btn-sm btn-primary custom-width" title="Edit" target="_blank"
																	href="${pageContext.request.contextPath}/editEmployee?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
																	
																	<a class="btn btn-sm btn-warning custom-width" title="Generate Appointment Letter"
																	href="${pageContext.request.contextPath}/appLetter?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-address-card-o"></i></a>
																	
																	<c:if test="${candidate.uploadAppLetter eq 1}">
																		<a class="btn btn-warning btn-sm custom-width" title="Download App Letter" target="_blank"
																			href='<c:url value="/candidateAppLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-file-pdf-o" style="color:red;"></i></a>
																	
																	
																	<a class="btn btn-sm btn-success custom-width" title="Approve"  onclick="doApprove(${candidate.id})"
																	href="#" style="color: white;"><i class="fa fa-check" aria-hidden="true"></i></a>
																	
																	</c:if>
																	
																	<a class="btn btn-sm btn-danger custom-width" title="Decline" onclick="doDecline(${candidate.id})"
																	href="#" style="color: white;"><i class="fa fa-times"></i></a>
																</c:if>
																
																<c:if test="${candidate.status eq 5}"> 
																	<a class="btn btn-sm btn-info custom-width" title="Profile View" target="_blank"
																	href="${pageContext.request.contextPath}/candidateProfileForm?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-street-view"> </i></a>
																	
																	<a class="btn btn-sm btn-primary custom-width" title="Edit" target="_blank"
																	href="${pageContext.request.contextPath}/editEmployee?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
																	
																	<a class="btn btn-sm btn-warning custom-width" title="Generate Appointment Letter"
																	href="${pageContext.request.contextPath}/appLetter?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-address-card-o"></i></a>
																	
																	<c:if test="${candidate.uploadAppLetter eq 1}">
																		<a class="btn btn-success btn-sm custom-width" title="Download App Letter" target="_blank"
																			href='<c:url value="/candidateAppLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-file-pdf-o" style="color:red"></i></a>
																	</c:if>
																	
																	<a class="btn btn-sm btn-danger custom-width" title="Decline" onclick="doDecline(${candidate.id})"
																	href="#" style="color: white;"><i class="fa fa-times"></i></a>
																	
																</c:if>
																
																<c:if test="${candidate.status eq 7}"> 
																	<a class="btn btn-sm btn-info custom-width" title="Profile View" target="_blank"
																	href="${pageContext.request.contextPath}/candidateProfileForm?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-street-view"> </i></a>
																	
																	<c:if test="${candidate.uploadAppLetter eq 1}">
																		<a class="btn btn-warning btn-sm custom-width" title="Download App Letter" target="_blank"
																			href='<c:url value="/candidateAppLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-file-pdf-o" style="color:red"></i></a>
																	</c:if>
																</c:if>
																
																<c:if test="${candidate.status eq 15}"> 
																	<a class="btn btn-sm btn-info custom-width" title="Profile View" target="_blank"
																	href="${pageContext.request.contextPath}/candidateProfileForm?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-street-view"> </i></a>
																	
																	<a class="btn btn-sm btn-primary custom-width" title="Edit" target="_blank"
																	href="${pageContext.request.contextPath}/editEmployee?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
																	
																	<a class="btn btn-sm btn-warning custom-width" title="Generate Appointment Letter"
																	href="${pageContext.request.contextPath}/appLetter?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-address-card-o"></i></a>
																	
																	<c:if test="${candidate.uploadAppLetter eq 1}">
																		<a class="btn btn-warning btn-sm custom-width" title="Download App Letter" target="_blank"
																			href='<c:url value="/candidateAppLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-file-pdf-o" style="color:red"></i></a>
																	</c:if>
																	
																	<c:if test="${candidate.uploadExtendLetter eq 1}">
																		<a class="btn btn-info btn-sm custom-width" title="Download Extend Letter" target="_blank"
																			href='<c:url value="/candidateExtendLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-external-link" style="color:red"></i></a>
																	</c:if>
																	
																	<a class="btn btn-sm btn-success custom-width" title="Resend Appointment Letter"  onclick="doReSend(${candidate.id})"
																	href="#" style="color: white;"><i class="fa fa-check" aria-hidden="true"></i></a>
																	
																	<a class="btn btn-sm btn-danger custom-width" title="Decline" onclick="doDecline(${candidate.id})"
																		href="#" style="color: white;"><i class="fa fa-times"></i></a>
																</c:if>
																
																<c:if test="${candidate.status eq 14}"> 
																	<a class="btn btn-sm btn-info custom-width" title="Profile View" target="_blank"
																	href="${pageContext.request.contextPath}/candidateProfileForm?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-street-view"> </i></a>
																	
																	<a class="btn btn-sm btn-primary custom-width" title="Edit" target="_blank"
																	href="${pageContext.request.contextPath}/editEmployee?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
																	
																	<c:if test="${candidate.uploadAppLetter eq 1}">
																		<a class="btn btn-warning btn-sm custom-width" title="Download App Letter" target="_blank"
																			href='<c:url value="/candidateAppLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-file-pdf-o" style="color:red"></i></a>
																	
																		<a class="btn btn-sm btn-success custom-width" title="Joined" onclick="doJoin(${candidate.id})"
																			href="#" style="color: white;"><i class="fa fa-home"></i></a>
																	</c:if>
																	
																	<c:if test="${candidate.uploadExtendLetter eq 1}">
																		<a class="btn btn-info btn-sm custom-width" title="Download Extend Letter" target="_blank"
																			href='<c:url value="/candidateExtendLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-external-link" style="color:red"></i></a>
																	</c:if>
																	
																	<c:if test="${candidate.dateOfJoin ne candidate.oldDateOfJoin && candidate.uploadExtendLetter eq 0}">
																		<a class="btn btn-info btn-sm custom-width" title="Generate Extend Letter" target="_blank"
																			href='<c:url value="/getExtension?id=${candidate.id}"/>' 
																			style="color: white;"><i class="fa fa-download" style="color:red"></i></a>
																	</c:if>
																	
																	<a class="btn btn-sm btn-danger custom-width" title="Decline" onclick="doDecline(${candidate.id})"
																	href="#" style="color: white;"><i class="fa fa-times"></i></a>
																	
																</c:if>
																	
																</td>
														</tr>
													</c:forEach>
	
												</tbody>
											</table>
										</c:if>
									</div>
								</div>
							</div>
							
							<div id="tabs-all-pending">
								<div>
									<label><input type="checkbox" class="toggle-vis2"
									data-column="0" checked="checked"> Sl. No</label> <label><input
									type="checkbox" class="toggle-vis2" data-column="1"
									checked="checked"> Candidate ID</label> <label><input
									type="checkbox" class="toggle-vis2" data-column="2"
									checked="checked">Candidate Name</label>
									<!-- <label><input
									type="checkbox" class="toggle-vis" data-column="2"
									checked="checked">Department Name</label>
									<label><input
									type="checkbox" class="toggle-vis" data-column="2"
									checked="checked">Designation </label> -->
									 <label><input
									type="checkbox" class="toggle-vis2" data-column="3"
									checked="checked"> Recruit By</label> <label><input
									type="checkbox" class="toggle-vis2" data-column="4"
									checked="checked"> Recruit For</label> <label><input
									type="checkbox" class="toggle-vis2" data-column="5"
									checked="checked"> Source</label> <label><input
									type="checkbox" class="toggle-vis2" data-column="6"
									checked="checked"> Date of Join</label> <label><input
									type="checkbox" class="toggle-vis2" data-column="7"
									checked="checked"> Email</label> <label><input
									type="checkbox" class="toggle-vis2" data-column="8"
									checked="checked"> Contact No</label> <label><input
									type="checkbox" class="toggle-vis2" data-column="9"
									checked="checked"> Status</label> <label><input
									type="checkbox" class="toggle-vis2" data-column="10"
									checked="checked"> Remarks</label> 
									
									<label><input
									type="checkbox" class="toggle-vis2" data-column="11"
									checked="checked"> Reject Reason</label> 
									
									<label><input
									type="checkbox" class="toggle-vis2" data-column="12"
									checked="checked"> Action</label>
								</div>
								<div class="widget-content">
									<div class="table-responsive">	
										<c:if test="${!empty pendCandidateList}">
											<table class="table table-bordered table-hover" id="table2">
												<thead>
													<tr style="background-color: #428bca; color: white">
														<th class="text-center">Sl. No.</th>
														<th class="text-center">Candidate ID</th>
														<th class="text-center">Candidate Name</th>
														<!-- <th class="text-center">Department Name</th>
														<th class="text-center">Designation</th> -->
														<th class="text-center">Recruit By</th>
														<th class="text-center">Recruit For</th>
														<th class="text-center">Source</th>
														<th class="text-center">Date Of Join</th>
														<th class="text-center">Email</th>
														<th class="text-center">Contact No</th>
														<th class="text-center">Status</th>
														<th class="text-center">Remarks</th>
														<th class="text-center">Reject Reason</th>
														<th class="text-center">Action</th>
	
	
	
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${pendCandidateList}" var="candidate"
														varStatus="cand">
														<tr>
															<td class="text-center">${cand.count}</td>
															<td class="text-center">${candidate.candId}</td>
															<td class="text-center">${candidate.candName}</td>
															<%-- <td class="text-center">${candidate.department.name}</td>
															<td class="text-center">${candidate.designation.name}</td> --%>
															<td class="text-center">${candidate.hrEmployee.name}
																- (${candidate.hrEmployee.empId})</td>
															<td class="text-center">${candidate.company.name}</td>
															<td class="text-center">${candidate.sourceOfCand}</td>
															<td class="text-center">${candidate.dateOfJoin}</td>
															<td class="text-center">${candidate.candEmail}</td>
															<td class="text-center">${candidate.candMobileNo}</td>
															<td class="text-center"><c:if
																	test="${candidate.status eq 1}">
																	<p class="btn btn-primary btn-sm">Ready For Sending
																		To Candidate</p>
																</c:if> <c:if test="${candidate.status eq 3}">
																	<p class="btn btn-warning btn-sm">Waiting For
																		Candidate Information</p>
																</c:if> <c:if test="${candidate.status eq 4}">
																	<p class="btn btn-info btn-sm">Ready For Appointment
																		Letter Approve</p>
																</c:if> <c:if test="${candidate.status eq 5}">
																	<p class="btn btn-info btn-sm">Pending Candidate Response
																	</p>
																</c:if>
																
																<c:if test="${candidate.status eq 14}">
																	<p class="btn btn-success btn-sm">Candidate Joining
																	</p>
																</c:if>
																
																<c:if test="${candidate.status eq 15}">
																	<p class="btn btn-warning btn-sm">Rejected By Candidate
																	</p>
																</c:if>														
																
																</td>
															<td class="text-center">${candidate.remarks}</td>
															<td class="text-center">${candidate.rejectReasonByCandidate}</td>
															<td align="center">
																<c:if test="${candidate.status eq 1}"> 
																	<a class="btn btn-sm btn-primary custom-width" title="Edit" target="_blank"
																	href="${pageContext.request.contextPath}/editEmployee?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
																	
																	<a class="btn btn-sm btn-success custom-width" title="Send To" onclick="doSend(${candidate.id})"
																	href="#" style="color: white;"><i class="fa fa-inbox"></i></a>
																	
																	<a class="btn btn-sm btn-danger custom-width" title="Delete" onclick="doDelete(${candidate.id})"
																	href="#" style="color: white;"><i class="fa fa-trash" aria-hidden="true"></i></a>
															</c:if>
																
																<c:if test="${candidate.status eq 3}"> 
																	
																	<a class="btn btn-sm btn-info custom-width" title="Profile View" target="_blank"
																	href="${pageContext.request.contextPath}/candidateProfileForm?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-street-view"> </i></a>
																	
																	<a class="btn btn-sm btn-primary custom-width" title="Edit" target="_blank"
																	href="${pageContext.request.contextPath}/editEmployee?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
																	
																	<a class="btn btn-sm btn-warning custom-width" title="Resend Email to Candidate"  onclick="doReSendEmail(${candidate.id})"
																	href="#" style="color: white;"><i class="fa fa-check" aria-hidden="true"></i></a>
																	
																	<a class="btn btn-sm btn-danger custom-width" title="Decline" onclick="doDecline(${candidate.id})"
																	href="#" style="color: white;"><i class="fa fa-times"></i></a>
																</c:if>
																
																<c:if test="${candidate.status eq 4}"> 
																	<a class="btn btn-sm btn-info custom-width" title="Profile View" target="_blank"
																	href="${pageContext.request.contextPath}/candidateProfileForm?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-street-view"> </i></a>
																	
																	<a class="btn btn-sm btn-primary custom-width" title="Edit" target="_blank"
																	href="${pageContext.request.contextPath}/editEmployee?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
																	
																	<a class="btn btn-sm btn-warning custom-width" title="Generate Appointment Letter"
																	href="${pageContext.request.contextPath}/appLetter?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-address-card-o"></i></a>
																	
																	<c:if test="${candidate.uploadAppLetter eq 1}">
																		<a class="btn btn-warning btn-sm custom-width" title="Download App Letter" target="_blank"
																			href='<c:url value="/candidateAppLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-file-pdf-o" style="color:red;"></i></a>
																	
																	
																	<a class="btn btn-sm btn-success custom-width" title="Approve"  onclick="doApprove(${candidate.id})"
																	href="#" style="color: white;"><i class="fa fa-check" aria-hidden="true"></i></a>
																	
																	</c:if>
																	
																	<c:if test="${candidate.uploadExtendLetter eq 1}">
																		<a class="btn btn-info btn-sm custom-width" title="Download Extend Letter" target="_blank"
																			href='<c:url value="/candidateExtendLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-external-link" style="color:red"></i></a>
																	</c:if>
																	
																	<a class="btn btn-sm btn-danger custom-width" title="Decline" onclick="doDecline(${candidate.id})"
																	href="#" style="color: white;"><i class="fa fa-times"></i></a>
																</c:if>
																
																<c:if test="${candidate.status eq 5}"> 
																	<a class="btn btn-sm btn-info custom-width" title="Profile View" target="_blank"
																	href="${pageContext.request.contextPath}/candidateProfileForm?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-street-view"> </i></a>
																	
																	<a class="btn btn-sm btn-primary custom-width" title="Edit" target="_blank"
																	href="${pageContext.request.contextPath}/editEmployee?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
																	
																	<a class="btn btn-sm btn-warning custom-width" title="Generate Appointment Letter"
																	href="${pageContext.request.contextPath}/appLetter?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-address-card-o"></i></a>
																	
																	<c:if test="${candidate.uploadAppLetter eq 1}">
																		<a class="btn btn-success btn-sm custom-width" title="Download App Letter" target="_blank"
																			href='<c:url value="/candidateAppLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-file-pdf-o" style="color:red"></i></a>
																	</c:if>
																	
																	<c:if test="${candidate.uploadExtendLetter eq 1}">
																		<a class="btn btn-info btn-sm custom-width" title="Download Extend Letter" target="_blank"
																			href='<c:url value="/candidateExtendLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-external-link" style="color:red"></i></a>
																	</c:if>
																	
																	<a class="btn btn-sm btn-danger custom-width" title="Decline" onclick="doDecline(${candidate.id})"
																	href="#" style="color: white;"><i class="fa fa-times"></i></a>
																	
																</c:if>
																
																<c:if test="${candidate.status eq 7}"> 
																	<a class="btn btn-sm btn-info custom-width" title="Profile View" target="_blank"
																	href="${pageContext.request.contextPath}/candidateProfileForm?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-street-view"> </i></a>
																	
																	<c:if test="${candidate.uploadAppLetter eq 1}">
																		<a class="btn btn-warning btn-sm custom-width" title="Download App Letter" target="_blank"
																			href='<c:url value="/candidateAppLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-file-pdf-o" style="color:red"></i></a>
																	</c:if>
																	
																	<c:if test="${candidate.uploadExtendLetter eq 1}">
																		<a class="btn btn-info btn-sm custom-width" title="Download Extend Letter" target="_blank"
																			href='<c:url value="/candidateExtendLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-external-link" style="color:red"></i></a>
																	</c:if>
																	
																</c:if>
																
																<c:if test="${candidate.status eq 15}"> 
																	<a class="btn btn-sm btn-info custom-width" title="Profile View" target="_blank"
																	href="${pageContext.request.contextPath}/candidateProfileForm?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-street-view"> </i></a>
																	
																	<a class="btn btn-sm btn-primary custom-width" title="Edit" target="_blank"
																	href="${pageContext.request.contextPath}/editEmployee?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
																	
																	<a class="btn btn-sm btn-warning custom-width" title="Generate Appointment Letter"
																	href="${pageContext.request.contextPath}/appLetter?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-address-card-o"></i></a>
																	
																	<c:if test="${candidate.uploadAppLetter eq 1}">
																		<a class="btn btn-warning btn-sm custom-width" title="Download App Letter" target="_blank"
																			href='<c:url value="/candidateAppLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-file-pdf-o" style="color:red;"></i></a>
																	</c:if>
																	
																	<c:if test="${candidate.uploadExtendLetter eq 1}">
																		<a class="btn btn-info btn-sm custom-width" title="Download Extend Letter" target="_blank"
																			href='<c:url value="/candidateExtendLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-external-link" style="color:red"></i></a>
																	</c:if>
																	
																	<a class="btn btn-sm btn-success custom-width" title="Resend Appointment Letter"  onclick="doReSend(${candidate.id})"
																	href="#" style="color: white;"><i class="fa fa-check" aria-hidden="true"></i></a>
																	
																	<a class="btn btn-sm btn-danger custom-width" title="Decline" onclick="doDecline(${candidate.id})"
																		href="#" style="color: white;"><i class="fa fa-times"></i></a>
																</c:if>
																
																<c:if test="${candidate.status eq 14}"> 
																	<a class="btn btn-sm btn-info custom-width" title="Profile View" target="_blank"
																	href="${pageContext.request.contextPath}/candidateProfileForm?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-street-view"> </i></a>
																	
																	<a class="btn btn-sm btn-primary custom-width" title="Edit" target="_blank"
																	href="${pageContext.request.contextPath}/editEmployee?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>
																	
																	<c:if test="${candidate.uploadAppLetter eq 1}">
																		<a class="btn btn-warning btn-sm custom-width" title="Download App Letter" target="_blank"
																			href='<c:url value="/candidateAppLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-file-pdf-o" style="color:red"></i></a>
																	
																		<a class="btn btn-sm btn-success custom-width" title="Joined" onclick="doJoin(${candidate.id})"
																			href="#" style="color: white;"><i class="fa fa-home"></i></a>
																	</c:if>
																	
																	<c:if test="${candidate.uploadExtendLetter eq 1}">
																		<a class="btn btn-info btn-sm custom-width" title="Download Extend Letter" target="_blank"
																			href='<c:url value="/candidateExtendLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-external-link" style="color:red"></i></a>
																	</c:if>
																	
																	<c:if test="${candidate.dateOfJoin ne candidate.oldDateOfJoin && candidate.uploadExtendLetter eq 0}">
																		<a class="btn btn-info btn-sm custom-width" title="Generate Extend Letter" target="_blank"
																			href='<c:url value="/getExtension?id=${candidate.id}"/>' 
																			style="color: white;"><i class="fa fa-download" style="color:red"></i></a>
																	</c:if>
																	
																	<a class="btn btn-sm btn-danger custom-width" title="Decline" onclick="doDecline(${candidate.id})"
																	href="#" style="color: white;"><i class="fa fa-times"></i></a>
																	
																</c:if>
																	
																</td>
														</tr>
													</c:forEach>
	
												</tbody>
											</table>
										</c:if>
									</div>
								</div>
							</div>
							
							<div id="tabs-todays-joined">
								<div>
									<label><input type="checkbox" class="toggle-vis3"
									data-column="0" checked="checked"> Sl. No</label> <label><input
									type="checkbox" class="toggle-vis3" data-column="1"
									checked="checked"> Candidate ID</label> <label><input
									type="checkbox" class="toggle-vis3" data-column="2"
									checked="checked">Candidate Name</label> <label><input
									type="checkbox" class="toggle-vis3" data-column="3"
									checked="checked"> Recruit By</label> <label><input
									type="checkbox" class="toggle-vis3" data-column="4"
									checked="checked"> Recruit For</label> <label><input
									type="checkbox" class="toggle-vis3" data-column="5"
									checked="checked"> Source</label> <label><input
									type="checkbox" class="toggle-vis3" data-column="6"
									checked="checked"> Date of Join</label> <label><input
									type="checkbox" class="toggle-vis3" data-column="7"
									checked="checked"> Email</label> <label><input
									type="checkbox" class="toggle-vis3" data-column="8"
									checked="checked"> Contact No</label> <label><input
									type="checkbox" class="toggle-vis3" data-column="9"
									checked="checked"> Status</label> <label><input
									type="checkbox" class="toggle-vis3" data-column="10"
									checked="checked"> Remarks</label> <label><input
									type="checkbox" class="toggle-vis3" data-column="11"
									checked="checked"> Action</label>
								</div>
								
								<div class="widget-content">

								<div class="table-responsive">


									<c:if test="${!empty joinedCandidateList}">
										<table class="table table-bordered table-hover" id="table3">
											<thead>
												<tr style="background-color: #428bca; color: white">
													<th class="text-center">Sl. No.</th>
													<th class="text-center">Candidate ID</th>
													<th class="text-center">Candidate Name</th>
													<th class="text-center">Recruit By</th>
													<th class="text-center">Recruit For</th>
													<th class="text-center">Source</th>
													<th class="text-center">Date Of Join</th>
													<th class="text-center">Email</th>
													<th class="text-center">Contact No</th>
													<th class="text-center">Status</th>
													<th class="text-center">Remarks</th>
													<th class="text-center">Action</th>



												</tr>
											</thead>
											<tbody>
												<c:forEach items="${joinedCandidateList}" var="candidate"
													varStatus="cand">
													<tr>
														<td class="text-center">${cand.count}</td>
														<td class="text-center">${candidate.candId}</td>
														<td class="text-center">${candidate.candName}</td>
														<td class="text-center">${candidate.hrEmployee.name}
															- (${candidate.hrEmployee.empId})</td>
														<td class="text-center">${candidate.company.name}</td>
														<td class="text-center">${candidate.sourceOfCand}</td>
														<td class="text-center">${candidate.dateOfJoin}</td>
														<td class="text-center">${candidate.candEmail}</td>
														<td class="text-center">${candidate.candMobileNo}</td>
														<td class="text-center"><c:if
																test="${candidate.status eq 16}">
																<p class="btn btn-primary btn-sm">Employee Joined</p>
															</c:if> 
															<%-- <c:if test="${candidate.status eq 8}">
																<p class="btn btn-warning btn-sm">Q1 FeedBack
																	Successful</p>
															</c:if> --%>
														</td>
														<td class="text-center">${candidate.remarks}</td>
														<td align="center">
														
															<c:if test="${candidate.status eq 16}"> 
															<a class="btn btn-sm btn-info custom-width" title="Profile View" target="_blank"
																href="${pageContext.request.contextPath}/candidateProfileForm?id=${candidate.id}"
																style="color: white;"><i class="fa fa-street-view"> </i></a>	
																
																<a class="btn btn-sm btn-primary custom-width" title="Edit" target="_blank"
																	href="${pageContext.request.contextPath}/editEmployee?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>															
																																
																<%-- <a class="btn btn-sm btn-warning custom-width" title="Appointment Letter"
																href="${pageContext.request.contextPath}/appLetter?id=${candidate.id}"
																style="color: white;"><i class="fa fa-address-card-o"></i></a> --%>						
																<c:if test="${candidate.uploadAppLetter eq 1}">
																		<a class="btn btn-success btn-sm custom-width" title="Download App Letter" target="_blank"
																			href='<c:url value="/candidateAppLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-file-pdf-o" style="color:red"></i></a>
																	</c:if>
																	
																<c:if test="${candidate.uploadExtendLetter eq 1}">
																		<a class="btn btn-info btn-sm custom-width" title="Download Extend Letter" target="_blank"
																			href='<c:url value="/candidateExtendLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-external-link" style="color:red"></i></a>
																</c:if>
																
																
																<a class="btn btn-warning btn-sm custom-width" title="Generate Joining Letter" target="_blank"
																		href='<c:url value="/getJoiningLetter?id=${candidate.id}"/>' 
																		style="color: white;"><i class="fa fa-angle-double-down" style="color:red"></i></a>
																
															
															</c:if>
															
															<c:if test="${candidate.status eq 7}"> 
															<a class="btn btn-sm btn-info custom-width" title="Profile View" target="_blank"
																href="${pageContext.request.contextPath}/candidateProfileForm?id=${candidate.id}"
																style="color: white;"><i class="fa fa-street-view"> </i></a>		
																
																<a class="btn btn-sm btn-primary custom-width" title="Edit" target="_blank"
																	href="${pageContext.request.contextPath}/editEmployee?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>		
																	
																<a class="btn btn-warning btn-sm custom-width" title="Generate Joining Letter" target="_blank"
																		href='<c:url value="/getJoiningLetter?id=${candidate.id}"/>' 
																		style="color: white;"><i class="fa fa-angle-double-down" style="color:red"></i></a>												
																																
																<%-- <a class="btn btn-sm btn-warning custom-width" title="Appointment Letter"
																href="${pageContext.request.contextPath}/appLetter?id=${candidate.id}"
																style="color: white;"><i class="fa fa-address-card-o"></i></a> --%>						
																<c:if test="${candidate.uploadAppLetter eq 1}">
																		<a class="btn btn-success btn-sm custom-width" title="Download App Letter" target="_blank"
																			href='<c:url value="/candidateAppLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-file-pdf-o" style="color:red"></i></a>
																	</c:if>
															
																<c:if test="${candidate.uploadExtendLetter eq 1}">
																		<a class="btn btn-info btn-sm custom-width" title="Download Extend Letter" target="_blank"
																			href='<c:url value="/candidateExtendLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-external-link" style="color:red"></i></a>
																	</c:if>
															</c:if>
															
															<c:if test="${candidate.status eq 8}"> 
															<a class="btn btn-sm btn-info custom-width" title="Profile View" target="_blank"
																href="${pageContext.request.contextPath}/candidateProfileForm?id=${candidate.id}"
																style="color: white;"><i class="fa fa-street-view"> </i></a>		
																
																<a class="btn btn-sm btn-primary custom-width" title="Edit" target="_blank"
																	href="${pageContext.request.contextPath}/editEmployee?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>														
																																
																<%-- <a class="btn btn-sm btn-warning custom-width" title="Appointment Letter"
																href="${pageContext.request.contextPath}/appLetter?id=${candidate.id}"
																style="color: white;"><i class="fa fa-address-card-o"></i></a> --%>
																<c:if test="${candidate.uploadAppLetter eq 1}">
																		<a class="btn btn-success btn-sm custom-width" title="Download App Letter" target="_blank"
																			href='<c:url value="/candidateAppLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-file-pdf-o" style="color:red"></i></a>
																	</c:if>
																
																<c:if test="${candidate.uploadExtendLetter eq 1}">
																		<a class="btn btn-info btn-sm custom-width" title="Download Extend Letter" target="_blank"
																			href='<c:url value="/candidateExtendLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-external-link" style="color:red"></i></a>
																	</c:if>
																	
																<a class="btn btn-sm btn-success custom-width" title="Answer-1" target="_blank"
																href="${pageContext.request.contextPath}/answer1?id=${candidate.id}"
																style="color: white;"><i class="fa fa-question-circle"></i></a>									
															
															</c:if>
														</td>
													</tr>
												</c:forEach>

											</tbody>
										</table>
									</c:if>
								</div>
							</div>
							
							</div>
							
							<div id="tabs-joined-candidate">
								<div>
									<label><input type="checkbox" class="toggle-vis4"
									data-column="0" checked="checked"> Sl. No</label> <label><input
									type="checkbox" class="toggle-vis4" data-column="1"
									checked="checked"> Candidate ID</label> <label><input
									type="checkbox" class="toggle-vis4" data-column="2"
									checked="checked">Candidate Name</label> <label><input
									type="checkbox" class="toggle-vis4" data-column="3"
									checked="checked"> Recruit By</label> <label><input
									type="checkbox" class="toggle-vis4" data-column="4"
									checked="checked"> Recruit For</label> <label><input
									type="checkbox" class="toggle-vis4" data-column="5"
									checked="checked"> Source</label> <label><input
									type="checkbox" class="toggle-vis4" data-column="6"
									checked="checked"> Date of Join</label> <label><input
									type="checkbox" class="toggle-vis4" data-column="7"
									checked="checked"> Email</label> <label><input
									type="checkbox" class="toggle-vis4" data-column="8"
									checked="checked"> Contact No</label> <label><input
									type="checkbox" class="toggle-vis4" data-column="9"
									checked="checked"> Status</label> <label><input
									type="checkbox" class="toggle-vis4" data-column="10"
									checked="checked"> Remarks</label> <label><input
									type="checkbox" class="toggle-vis4" data-column="11"
									checked="checked"> Action</label>
								</div>
								
								<div class="widget-content">

								<div class="table-responsive">


									<c:if test="${!empty allJoinedCandidateList}">
										<table class="table table-bordered table-hover" id="table4">
											<thead>
												<tr style="background-color: #428bca; color: white">
													<th class="text-center">Sl. No.</th>
													<th class="text-center">Candidate ID</th>
													<th class="text-center">Candidate Name</th>
													<th class="text-center">Recruit By</th>
													<th class="text-center">Recruit For</th>
													<th class="text-center">Source</th>
													<th class="text-center">Date Of Join</th>
													<th class="text-center">Email</th>
													<th class="text-center">Contact No</th>
													<th class="text-center">Status</th>
													<th class="text-center">Remarks</th>
													<th class="text-center">Action</th>



												</tr>
											</thead>
											<tbody>
												<c:forEach items="${allJoinedCandidateList}" var="candidate"
													varStatus="cand">
													<tr>
														<td class="text-center">${cand.count}</td>
														<td class="text-center">${candidate.candId}</td>
														<td class="text-center">${candidate.candName}</td>
														<td class="text-center">${candidate.hrEmployee.name}
															- (${candidate.hrEmployee.empId})</td>
														<td class="text-center">${candidate.company.name}</td>
														<td class="text-center">${candidate.sourceOfCand}</td>
														<td class="text-center">${candidate.dateOfJoin}</td>
														<td class="text-center">${candidate.candEmail}</td>
														<td class="text-center">${candidate.candMobileNo}</td>
														<td class="text-center">
															
															<c:if
																test="${candidate.status eq 16}">
																<p class="btn btn-primary btn-sm">Employee Joined</p>
															</c:if> 
															
															<c:if
																test="${candidate.status eq 7}">
																<p class="btn btn-default btn-sm">Waiting for Q1 FeedBack</p>
															</c:if> 
															
															<c:if test="${candidate.status eq 8}">
																<p class="btn btn-warning btn-sm">Q1 FeedBack
																	Successful</p>
															</c:if>
															
															<c:if
																test="${candidate.status eq 9}">
																<p class="btn btn-info btn-sm">Waiting for Q2 FeedBack</p>
															</c:if> 
															
															<c:if test="${candidate.status eq 10}">
																<p class="btn btn-danger btn-sm">Q2 FeedBack
																	Successful</p>
															</c:if>
															
															<c:if
																test="${candidate.status eq 11}">
																<p class="btn btn-default btn-sm">Waiting for Q3 FeedBack</p>
															</c:if> 
															
															<c:if test="${candidate.status eq 12}">
																<p class="btn btn-success btn-sm">Q3 FeedBack
																	Successful &amp; Ready to Close</p>
															</c:if>
															
														</td>
														<td class="text-center">${candidate.remarks}</td>
														<td align="center">
														
															<c:if test="${candidate.status eq 16}"> 
															<a class="btn btn-sm btn-info custom-width" title="Profile View" target="_blank"
																href="${pageContext.request.contextPath}/candidateProfileForm?id=${candidate.id}"
																style="color: white;"><i class="fa fa-street-view"> </i></a>	
																	
																<a class="btn btn-sm btn-primary custom-width" title="Edit" target="_blank"
																	href="${pageContext.request.contextPath}/editEmployee?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>														
																																
															<%-- <a class="btn btn-sm btn-warning custom-width" title="Appointment Letter"
																href="${pageContext.request.contextPath}/appLetter?id=${candidate.id}"
																style="color: white;"><i class="fa fa-address-card-o"></i></a> --%>												
																
															<c:if test="${candidate.uploadAppLetter eq 1}">
																		<a class="btn btn-success btn-sm custom-width" title="Download App Letter" target="_blank"
																			href='<c:url value="/candidateAppLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-file-pdf-o" style="color:red"></i></a>
																	</c:if>
																	
																	<c:if test="${candidate.uploadExtendLetter eq 1}">
																		<a class="btn btn-info btn-sm custom-width" title="Download Extend Letter" target="_blank"
																			href='<c:url value="/candidateExtendLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-external-link" style="color:red"></i></a>
																	</c:if>
																	
																	<a class="btn btn-warning btn-sm custom-width" title="Generate Joining Letter" target="_blank"
																		href='<c:url value="/getJoiningLetter?id=${candidate.id}"/>' 
																		style="color: white;"><i class="fa fa-angle-double-down" style="color:red"></i></a>
															
															</c:if>
														
															<c:if test="${candidate.status eq 7}"> 
															<a class="btn btn-sm btn-info custom-width" title="Profile View" target="_blank"
																href="${pageContext.request.contextPath}/candidateProfileForm?id=${candidate.id}"
																style="color: white;"><i class="fa fa-street-view"> </i></a>		
																
																<a class="btn btn-sm btn-primary custom-width" title="Edit" target="_blank"
																	href="${pageContext.request.contextPath}/editEmployee?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>	
																	
																<a class="btn btn-warning btn-sm custom-width" title="Generate Joining Letter" target="_blank"
																		href='<c:url value="/getJoiningLetter?id=${candidate.id}"/>' 
																		style="color: white;"><i class="fa fa-angle-double-down" style="color:red"></i></a>													
																																
															<%-- <a class="btn btn-sm btn-warning custom-width" title="Appointment Letter"
																href="${pageContext.request.contextPath}/appLetter?id=${candidate.id}"
																style="color: white;"><i class="fa fa-address-card-o"></i></a> --%>												
																
															<c:if test="${candidate.uploadAppLetter eq 1}">
																		<a class="btn btn-success btn-sm custom-width" title="Download App Letter" target="_blank"
																			href='<c:url value="/candidateAppLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-file-pdf-o" style="color:red"></i></a>
																	</c:if>
																	
																	<c:if test="${candidate.uploadExtendLetter eq 1}">
																		<a class="btn btn-info btn-sm custom-width" title="Download Extend Letter" target="_blank"
																			href='<c:url value="/candidateExtendLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-external-link" style="color:red"></i></a>
																	</c:if>
															
															</c:if>
															
															<c:if test="${candidate.status eq 8}"> 
															<a class="btn btn-sm btn-info custom-width" title="Profile View" target="_blank"
																href="${pageContext.request.contextPath}/candidateProfileForm?id=${candidate.id}"
																style="color: white;"><i class="fa fa-street-view"> </i></a>		
																
																<a class="btn btn-sm btn-primary custom-width" title="Edit" target="_blank"
																	href="${pageContext.request.contextPath}/editEmployee?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>														
																																
															<%-- <a class="btn btn-sm btn-warning custom-width" title="Appointment Letter"
																href="${pageContext.request.contextPath}/appLetter?id=${candidate.id}"
																style="color: white;"><i class="fa fa-address-card-o"></i></a> --%>
															<c:if test="${candidate.uploadAppLetter eq 1}">
																		<a class="btn btn-success btn-sm custom-width" title="Download App Letter" target="_blank"
																			href='<c:url value="/candidateAppLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-file-pdf-o" style="color:red"></i></a>
																	</c:if>
																	
																	<c:if test="${candidate.uploadExtendLetter eq 1}">
																		<a class="btn btn-info btn-sm custom-width" title="Download Extend Letter" target="_blank"
																			href='<c:url value="/candidateExtendLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-external-link" style="color:red"></i></a>
																	</c:if>
																	
																
															<a class="btn btn-sm btn-link custom-width" title="Answer-1" target="_blank"
																href="${pageContext.request.contextPath}/answer1?id=${candidate.id}"
																style="color: white; background: #CDDC39;"><i class="fa fa-question-circle"></i></a>
															</c:if>
															
															<c:if test="${candidate.status eq 9}"> 
																<a class="btn btn-sm btn-info custom-width" title="Profile View" target="_blank"
																href="${pageContext.request.contextPath}/candidateProfileForm?id=${candidate.id}"
																style="color: white;"><i class="fa fa-street-view"> </i></a>	
																
																<a class="btn btn-sm btn-primary custom-width" title="Edit" target="_blank"
																	href="${pageContext.request.contextPath}/editEmployee?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>															
																																
															<%-- <a class="btn btn-sm btn-warning custom-width" title="Appointment Letter"
																href="${pageContext.request.contextPath}/appLetter?id=${candidate.id}"
																style="color: white;"><i class="fa fa-address-card-o"></i></a> --%>
																
															<c:if test="${candidate.uploadAppLetter eq 1}">
																		<a class="btn btn-success btn-sm custom-width" title="Download App Letter" target="_blank"
																			href='<c:url value="/candidateAppLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-file-pdf-o" style="color:red"></i></a>
																	</c:if>
																	
																	<c:if test="${candidate.uploadExtendLetter eq 1}">
																		<a class="btn btn-info btn-sm custom-width" title="Download Extend Letter" target="_blank"
																			href='<c:url value="/candidateExtendLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-external-link" style="color:red"></i></a>
																	</c:if>
																
															<a class="btn btn-sm btn-link custom-width" title="Answer-1" target="_blank"
																href="${pageContext.request.contextPath}/answer1?id=${candidate.id}"
																style="color: white; background: #CDDC39;"><i class="fa fa-question-circle"></i></a>
																
															</c:if>
															
															<c:if test="${candidate.status eq 10}"> 
															<a class="btn btn-sm btn-info custom-width" title="Profile View" target="_blank"
																href="${pageContext.request.contextPath}/candidateProfileForm?id=${candidate.id}"
																style="color: white;"><i class="fa fa-street-view"> </i></a>	
																
																<a class="btn btn-sm btn-primary custom-width" title="Edit" target="_blank"
																	href="${pageContext.request.contextPath}/editEmployee?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>															
																																
															<%-- <a class="btn btn-sm btn-warning custom-width" title="Appointment Letter"
																href="${pageContext.request.contextPath}/appLetter?id=${candidate.id}"
																style="color: white;"><i class="fa fa-address-card-o"></i></a> --%>
																
															<c:if test="${candidate.uploadAppLetter eq 1}">
																		<a class="btn btn-success btn-sm custom-width" title="Download App Letter" target="_blank"
																			href='<c:url value="/candidateAppLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-file-pdf-o" style="color:red"></i></a>
																	</c:if>
																	
																	<c:if test="${candidate.uploadExtendLetter eq 1}">
																		<a class="btn btn-info btn-sm custom-width" title="Download Extend Letter" target="_blank"
																			href='<c:url value="/candidateExtendLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-external-link" style="color:red"></i></a>
																	</c:if>
																
															<a class="btn btn-sm btn-link custom-width" title="Answer-1" target="_blank"
																href="${pageContext.request.contextPath}/answer1?id=${candidate.id}"
																style="color: white; background: #CDDC39;"><i class="fa fa-question-circle"></i></a>	
															
															<a class="btn btn-sm btn-primary custom-width" title="Answer-2" target="_blank"
																href="${pageContext.request.contextPath}/answer2?id=${candidate.id}"
																style="color: white;"><i class="fa fa-question-circle"></i></a>
															</c:if>
															
															<c:if test="${candidate.status eq 11}"> 
															<a class="btn btn-sm btn-info custom-width" title="Profile View" target="_blank"
																href="${pageContext.request.contextPath}/candidateProfileForm?id=${candidate.id}"
																style="color: white;"><i class="fa fa-street-view"> </i></a>	
																
																<a class="btn btn-sm btn-primary custom-width" title="Edit" target="_blank"
																	href="${pageContext.request.contextPath}/editEmployee?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>															
																																
															<%-- <a class="btn btn-sm btn-warning custom-width" title="Appointment Letter"
																href="${pageContext.request.contextPath}/appLetter?id=${candidate.id}"
																style="color: white;"><i class="fa fa-address-card-o"></i></a> --%>
															<c:if test="${candidate.uploadAppLetter eq 1}">
																		<a class="btn btn-success btn-sm custom-width" title="Download App Letter" target="_blank"
																			href='<c:url value="/candidateAppLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-file-pdf-o" style="color:red"></i></a>
																	</c:if>
																	
																	<c:if test="${candidate.uploadExtendLetter eq 1}">
																		<a class="btn btn-info btn-sm custom-width" title="Download Extend Letter" target="_blank"
																			href='<c:url value="/candidateExtendLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-external-link" style="color:red"></i></a>
																	</c:if>
																
															<a class="btn btn-sm btn-link custom-width" title="Answer-1" target="_blank"
																href="${pageContext.request.contextPath}/answer1?id=${candidate.id}"
																style="color: white; background: #CDDC39;"><i class="fa fa-question-circle"></i></a>	
															
															<a class="btn btn-sm btn-primary custom-width" title="Answer-2" target="_blank"
																href="${pageContext.request.contextPath}/answer2?id=${candidate.id}"
																style="color: white;"><i class="fa fa-question-circle"></i></a>
															</c:if>
															
															<c:if test="${candidate.status eq 12}"> 
															
															<a class="btn btn-sm btn-info custom-width" title="Profile View" target="_blank"
																href="${pageContext.request.contextPath}/candidateProfileForm?id=${candidate.id}"
																style="color: white;"><i class="fa fa-street-view"> </i></a>	
																
																<a class="btn btn-sm btn-primary custom-width" title="Edit" target="_blank"
																	href="${pageContext.request.contextPath}/editEmployee?id=${candidate.id}"
																	style="color: white;"><i class="fa fa-pencil-square-o" aria-hidden="true"></i></a>															
																																
															<%-- <a class="btn btn-sm btn-warning custom-width" title="Appointment Letter"
																href="${pageContext.request.contextPath}/appLetter?id=${candidate.id}"
																style="color: white;"><i class="fa fa-address-card-o"></i></a> --%>
																
															<c:if test="${candidate.uploadAppLetter eq 1}">
																		<a class="btn btn-success btn-sm custom-width" title="Download App Letter" target="_blank"
																			href='<c:url value="/candidateAppLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-file-pdf-o" style="color:red"></i></a>
																	</c:if>
																	
																	<c:if test="${candidate.uploadExtendLetter eq 1}">
																		<a class="btn btn-info btn-sm custom-width" title="Download Extend Letter" target="_blank"
																			href='<c:url value="/candidateExtendLetter/${candidate.candId}"/>.pdf' 
																			style="color: white;"><i class="fa fa-external-link" style="color:red"></i></a>
																	</c:if>
																
															<a class="btn btn-sm btn-link custom-width" title="Answer-1" target="_blank"
																href="${pageContext.request.contextPath}/answer1?id=${candidate.id}"
																style="color: white; background: #CDDC39;"><i class="fa fa-question-circle"></i></a>	
															
															<a class="btn btn-sm btn-primary custom-width" title="Answer-2" target="_blank"
																href="${pageContext.request.contextPath}/answer2?id=${candidate.id}"
																style="color: white;"><i class="fa fa-question-circle"></i></a>
																
															<a class="btn btn-sm btn-success custom-width" title="Answer-3" target="_blank"
																href="${pageContext.request.contextPath}/answer3?id=${candidate.id}"
																style="color: white;"><i class="fa fa-question-circle"></i></a>
																
															<a class="btn btn-sm btn-danger custom-width" title="Close"
																href="${pageContext.request.contextPath}/recruitmentProcessClose?id=${candidate.id}"
																style="color: white;"><i class="fa fa-rebel"></i></a>
																
															</c:if>
															
															
																
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

		</div>

	</div>
	<!-- table ends -->
	<!-- Mainbar ends -->
	<div class="clearfix"></div>


<script>
function doDelete(candId){
	
	var contextPath = $('#contextPath').val();	
	
	w2confirm('Are you sure delete this candidate?', function btn(answer) {
	    if(answer == 'Yes'){	    	  	
	    	window.location.href = contextPath+"/deleteCandidate?id="+candId;
	    } 
	});
}


function doSend(candId){
	
	var contextPath = $('#contextPath').val();	
	
	w2confirm('Do you want to send notification and login information to this candidate?', function btn(answer) {
	    if(answer == 'Yes'){
	    	window.location.href = contextPath+"/sendToCandidate?id="+candId;
	    } 
	});
}

function doDecline(candId){
	
	var contextPath = $('#contextPath').val();	
	
	w2confirm('Are you sure decline this candidate?', function btn(answer) {
	    if(answer == 'Yes'){
	    	//
	    	w2popup.open({
			    title   : 'Decline Reason',
			    body    : '<input id="candId" type="hidden" value="'+candId+'"><textarea placeholder="Please input reason here" id="declineReason" class="form-control"> </textarea> <br/> <p id="worngOtpMsg" style="color:green;">Please Input Decline Reason Here.</p>',
			    buttons : '<button id="modalDeclineSubmitButton"> Submit </button>',
			    modal           : true,
			    width           : 500,
			    height          : 200,
			    showClose       : true,
			    keyboard        : false,
			    mouse 			: false
			});
	    } 
	});
}

$(document).on("click","#modalDeclineSubmitButton",function() {
	var contextPath = $('#contextPath').val();	
	var candId = $('#candId').val();
	var declineReason = $('#declineReason').val().trim();
	if(declineReason != null && declineReason.length > 0){
		$('#worngOtpMsg').text('Please Wait...');
		$('#worngOtpMsg').css( "color", "green" )		
		window.location.href = contextPath+"/declineCandidate?id="+candId+"&declineReason="+declineReason;
	} else {
		$('#worngOtpMsg').text('Decline Reason can not be empty or null.');
		$('#worngOtpMsg').css( "color", "red" )
	}
	
});


function doApprove(candId){
	
	var contextPath = $('#contextPath').val();	
	
	w2confirm('Do you want to Approve this candidate profile and Send AL?', function btn(answer) {
	    if(answer == 'Yes'){
	    	window.location.href = contextPath+"/approveCandidateProfile?id="+candId;
	    } 
	});
}


function doReSend(candId){
	
	var contextPath = $('#contextPath').val();	
	
	w2confirm('Do you want to Resend Apponintment Letter to this candidate?', function btn(answer) {
	    if(answer == 'Yes'){
	    	window.location.href = contextPath+"/resendALToCandidate?id="+candId;
	    } 
	});
}

function doReSendEmail(candId){
	
	var contextPath = $('#contextPath').val();	
	
	w2confirm('Do you want to resend email to this candidate?', function btn(answer) {
	    if(answer == 'Yes'){
	    	window.location.href = contextPath+"/resendEmailToCandidate?id="+candId;
	    } 
	});
}

function doJoin(candId){	
	w2confirm('Are you sure join this candidate today?', function btn(answer) {
	    if(answer == 'Yes'){
	    	//window.location.href = contextPath+"/joinCandidate?id="+candId;
	    	w2popup.open({
			    title   : 'Employee ID',
			    body    : '<input id="candId" type="hidden" value="'+candId+'"><input type="text" placeholder="Please input Employee ID here" id="empId" class="form-control"> <br/>Do you want to Circular? <input type="checkbox" id="circular" checked class="form-control"> <br/> <p id="worngOtpMsg" style="color:green;">Please Input Employee ID Here.</p>',
			    buttons : '<button id="modalJoinedCandidateButton"> Submit </button>',
			    modal           : true,
			    width           : 500,
			    height          : 200,
			    showClose       : true,
			    keyboard        : false,
			    mouse 			: false
			});
	    } 
	});
}

$(document).on("click","#modalJoinedCandidateButton",function() {
	var contextPath = $('#contextPath').val();	
	var candId = $('#candId').val();
	var empId = $('#empId').val().trim();
	var circular = 0;
	if($('#circular').is(':checked')){
		circular = 1;
	}
	
	if(empId != null && empId.length > 0){
		$('#worngOtpMsg').text('Please Wait...');
		$('#worngOtpMsg').css( "color", "green" )		
		window.location.href = contextPath+"/joinCandidate?id="+candId+"&empId="+empId+"&circular="+circular;
	} else {
		$('#worngOtpMsg').text('Employee can not be empty or null.');
		$('#worngOtpMsg').css( "color", "red" )
	}
	
});

</script>


</body>
</html>