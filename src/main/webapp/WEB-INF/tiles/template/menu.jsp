<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<!-- Title and other stuffs -->
<title></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="author" content="">


<!-- Stylesheets -->
<link href="resource/css/demo.css" rel="stylesheet">
<link href="resource/css/bootstrap.min.css" rel="stylesheet">
<!-- Font awesome icon -->
<link rel="stylesheet" href="resource/css/font-awesome.min.css">
<!-- bdjobs css -->
<link rel="stylesheet" href="resource/css/bdjobs-style.css">
<!-- jQuery UI -->
<link rel="stylesheet" href="resource/css/jquery-ui.css">
<!-- Calendar -->
<link rel="stylesheet" href="resource/css/fullcalendar.css">
<!-- prettyPhoto -->
<link rel="stylesheet" href="resource/css/prettyPhoto.css">
<!-- Star rating -->
<link rel="stylesheet" href="resource/css/rateit.css">
<!-- Date picker -->
<link rel="stylesheet"
	href="resource/css/bootstrap-datetimepicker.min.css">
<!-- CLEditor -->
<link rel="stylesheet" href="resource/css/jquery.cleditor.css">
<!-- Data tables -->
<link rel="stylesheet" href="resource/css/jquery.dataTables.css">
<!-- Bootstrap toggle -->
<link rel="stylesheet" href="resource/css/jquery.onoff.css">
<!-- Main stylesheet -->
<link href="resource/css/style.css" rel="stylesheet">

<link href="resource/css/custom.css" rel="stylesheet">
<!-- Widgets stylesheet -->
<link href="resource/css/widgets.css" rel="stylesheet">
<!-- Widgets stylesheet -->
<link href="resource/css/bootstrap-table.css" rel="stylesheet">
<!-- Widgets stylesheet -->
<link href="resource/css/jquery.dataTables.css" rel="stylesheet">
<!-- Widgets stylesheet -->
<link href="resource/css/bootstrap.min.css" rel="stylesheet">
<link href="resource/css/jquery.steps.css" rel="stylesheet">
<link href="resource/css/main.css" rel="stylesheet">
<link href="resource/css/normalize.css" rel="stylesheet">
<script src="resource/js/respond.min.js"></script>
<script src="resource/js/jquery-1.7.2.js"></script>
<!--[if lt IE 9]>
  <script src="resource/js/html5shiv.js"></script>
  <![endif]-->
<link
	href="https://cdn.jsdelivr.net/bootstrap.timepicker/0.2.6/css/bootstrap-timepicker.min.css"
	rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/bootstrap.timepicker/0.2.6/css/bootstrap-timepicker.css"
	rel="stylesheet">

<!-- Favicon -->
<link rel="shortcut icon" href="resource/img/favicon.ico">

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<!--for new Dashboard-->
<link href="resource/css/style.min.css" rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<link href='http://fonts.googleapis.com/css?family=Open+Sans'
	rel='stylesheet' type='text/css'>
<style>

/* .sidebar #nav > li > a:hover, .sidebar #nav > li.open > a {
    color: #428bca;
    border-bottom: 1px solid #5bc0de;
    border-top: 1px solid #428bca;
    background-color: #aaa;
    background: -webkit-gradient(linear, left top, left bottom, from(#068a36), to(#73bd1e));
    background: -webkit-linear-gradient(top, #ffffff, #ffffff);
    background: -moz-linear-gradient(center top , #5bc0de, #428bca) repeat scroll 0 0 rgba(0, 0, 0, 0);
    background: -ms-linear-gradient(top, #068a36, #73bd1e);
    background: -o-linear-gradient(top, #068a36, #73bd1e);
    background: linear-gradient(top, #068a36, #73bd1e);
    box-shadow: none;
    color: #000;
    font-weight: bold;
}

.sidebar #nav > li > a {
    display: block;
    padding: 3px 20px;
    font-size: 13px;
    color: #fffefe;
    text-decoration: none;
    border-bottom: 1px solid #ccc;
    border-top: 1px solid #fff;
    background-color: #f8f8f8;
    background: -webkit-gradient(linear, left top, left bottom, from(#f9f9f9), to(#f2f2f2));
    background: -webkit-linear-gradient(top, #000000, #000000);
    background: -moz-linear-gradient(top, #f9f9f9, #f2f2f2);
    background: -ms-linear-gradient(top, #f9f9f9, #f2f2f2);
    background: -o-linear-gradient(top, #f9f9f9, #f2f2f2);
    background: linear-gradient(top, #f9f9f9, #f2f2f2);
    box-shadow: inset 0px 1px 1px #fff;
} */

#clock {
	font-family: 'Open Sans', sans-serif;
	font-size: 1em;
	font-weight: bold;
	background-color: #0CF;
	color: #fff;
	padding: 7px 50px;
	position: relative;
	top: 100px;
	left: 100px;
}
</style>

<script>
	$(document).ready(function() {
		DisplayCurrentTime();
	});

	function DisplayCurrentTime() {
		var dt = new Date();
		var refresh = 1000; //Refresh rate 1000 milli sec means 1 sec
		var cDate = (dt.getMonth() + 1) + "/" + dt.getDate() + "/"
				+ dt.getFullYear();
		document.getElementById('cTime').innerHTML = cDate + "  "
				+ dt.toLocaleTimeString();
		window.setTimeout('DisplayCurrentTime()', refresh);
	}
</script>
</head>



<body>

<input type="hidden" id="cTime">
	<div class="content">

		<%
			// Set refresh, autoload time as 5 seconds
			response.setIntHeader("Refresh", 5);
		%>

		<!-- Sidebar -->
		<div class="sidebar">
			<div class="sidebar-dropdown">
				<a href="#">Navigation</a>
			</div>

			<!--- Sidebar navigation -->
			<!-- If the main navigation has sub navigation, then add the class "has_sub" to "li" of main navigation. -->

			<ul id="nav">
				<!-- Main menu with font awesome icon -->

				<li class="open"><a href="${pageContext.request.contextPath}/"><i
						class="fa fa-home"></i>Dashboard</a></li>
				
				<sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')">
					<li class="has_sub"><a href="#"><i class="fa fa-cog"></i>Settings<span class="pull-right"><i class="fa fa-chevron-right"></i></span></a>
						<ul>
							<li><a href="addTeamLeaderForm">Team Leader Entry Form</a></li>
							<li><a href="addPointTypeForm">Point Type Entry Form</a></li>
							<li><a href="addPointForm">Point Entry Form</a></li>
							<li><a href="addBudgetSourceForm">Budget Source Entry Form</a></li>
							<li><a href="addBudgetCodeForm">Budget Code Entry Form</a></li>
							<li><a href="addColorCodeForm">Color Code Entry Form</a></li>
							<li><a href="addGoodsTypeForm">Goods Type Entry Form</a></li>
							
							<sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
							<li><a href="addFiscalYearForm">Fiscal Year Entry Form</a></li>
							<li><a href="addReportColumnTitleForm">Report Column Title Form</a></li>
							</sec:authorize>
						</ul></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')">
					<li class="has_sub"><a href="#"><i class="fa fa-btc"></i>Budget<span class="pull-right"><i class="fa fa-chevron-right"></i></span></a>
						<ul>
							<li><a href="addBudgetForm">Budget Entry Form</a></li>
							<li><a href="budgetList">Budget List</a></li>
						</ul></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')">
					<li class="has_sub"><a href="#"><i class="fa fa-ship"></i>Delivery<span class="pull-right"><i class="fa fa-chevron-right"></i></span></a>
						<ul>
							<li><a href="addDeliveredForm">Delivery Entry Form</a></li>
						</ul></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')">
					<li class="has_sub"><a href="#"><i class="fa fa-certificate"></i>Stock Report<span class="pull-right"><i class="fa fa-chevron-right"></i></span></a>
						<ul>
							<li><a href="addStockReportForm">Stock Report Entry Form</a></li>
						</ul></li>
				</sec:authorize>
				
				<%-- <sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')">
					<li class="has_sub"><a href="#"><i class="fa fa-ship"></i>Delivery<span class="pull-right"><i class="fa fa-chevron-right"></i></span></a>
						<ul>
							<li><a href="addDeliveryForm">Delivery Entry Form</a></li>
							<li><a href="abDeliveryList">Against Budget Delivery List</a></li>
							<li><a href="wbDeliveryList">Without Budget Delivery List</a></li>
							<li><a href="allDeliveryList">All Delivery List</a></li>
							<li><a href="adjustDeliveryList">Adjust Delivery List</a></li>
						</ul></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')">
					<li class="has_sub"><a href="#"><i class="fa fa-certificate"></i>Bill<span class="pull-right"><i class="fa fa-chevron-right"></i></span></a>
						<ul>
							<li><a href="addBillForm">Bill Entry Form</a></li>
							<li><a href="billList">Bill List</a></li>
						</ul></li>
				</sec:authorize> --%>
				
				<sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_SUPER_ADMIN')">
					<li class="has_sub"><a href="#"><i class="fa fa-file-pdf-o"></i>Download Reports<span class="pull-right"><i class="fa fa-chevron-right"></i></span></a>
						<ul>
							<li><a href="alAltmntReportForm">1. All Allotment</a></li>
							<li><a href="rstAltmntPointReportForm">2. All Allotment Point Wise</a></li>
							<li><a href="altmntTLReportForm">3. (A) All Allotment Team Leader Wise</a></li>
							<li><a href="altmntSrcReportForm">3. (B) All Allotment Source Wise</a></li>
							<li><a href="alAltmntReportFormRest">4. Rest Of The Allotment</a></li>
							<li><a href="altmntTLReportFormRest">5. Rest Of The Allotment Team Leader Wise</a></li>
							<li><a href="altmntSrcReportFormRest">6. Rest Of The Allotment Source Wise</a></li>
							
							<li><a href="alAltmntVSGoodDeliversForm">7. (A) Allotment Vs Goods Delivered</a></li>
							<li><a href="alAltmntPntReportForm">7. (B) Point And Team Leader Wise</a></li>
							<li><a href="rstAltmntPointReportFormRest">8. Rest Of The Allotment Point Wise</a></li>
							<li><a href="altmntColorReports">9.(A) Color Code All</a></li>
							<li><a href="altmntColorTeamLeaderWise">9. (B)Color Code Color And TeamLeader Wise</a></li>
							<li><a href="pointVSGoodDeliversForm">10. Point Vs Goods</a></li>
							<li><a href="stockReportsForm">11. Stock Report</a></li>
							<li><a href="dlvReports">12. Delivery Reports</a></li>
														
							<!-- <li><a href="alAltmntReportForm">1. All Allotment</a></li>
							<li><a href="alAltmntTLReportForm">2. All Allotment Team Leader Wise</a></li>
							<li><a href="altmntSrcReportForm">3. Allotment Source Wise</a></li>
							<li><a href="alAltmntPntReportForm">4. All Allotment Point &amp; Team Leader Wise</a></li>
							<li><a href="alAltmntSrcReportForm">5. All Allotment Source Wise</a></li>
							<li><a href="teamLeaderPointList">6.Team Leader Point List</a></li>
							<li><a href="rstAltmntReportForm">7. Rest of The Allotment</a></li>
							
							<li><a href="rstAltmntTLReportForm">8. Rest of The Allotment Team Leader Wise</a></li>
							<li><a href="rstAltmntSrcReportForm">9. Rest of The Allotment Source Wise</a></li>
							<li><a href="altmntTLReportForm">10. Allotment Team Leader Wise</a></li>
							<li><a href="rstAltmntSrcSmryReportForm">11. Rest of The Allotment Source Wise Summary</a></li>
							<li><a href="altmntTLSmryReportForm">12. Allotment Team Leader Wise Summary</a></li>
							<li><a href="altmntColorReportForm">13. Allotment Color Code Wise</a></li>
							<li><a href="altmntClrAndTLReportForm">14. All Allotment Color Code &amp; Team Leader Wise</a></li>
							<li><a href="rstAltmntPointReportForm">15. Rest of The Allotment Point Wise</a></li>
							<li><a href="altmntColorReports">16.  Allotments Color Code  Wise</a></li> -->
						</ul></li>
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_SUPER_ADMIN')">
					<li class="has_sub"><a href="#"><i class="fa fa-file-pdf-o"></i>Log History<span class="pull-right"><i class="fa fa-chevron-right"></i></span></a>
						<ul>
							<li><a href="deliveryHistory">Delivery History</a></li>
							
						</ul></li>
				</sec:authorize>
			</ul>



		</div>

		<!-- Sidebar ends -->
		<!-- Main bar -->



		<!-- Mainbar ends -->
		<div class="clearfix"></div>

	</div>
	<!-- Content ends -->
	
	<!-- Scroll to top -->
	<span class="totop"><a href="#"><i class="fa fa-chevron-up"></i></a></span>
	<!--chart js   -->
	<!-- <script src="js/jquery-1.11.1.min.js"></script> -->
	<!-- <script src="js/highcharts.js"></script> -->
	<script src="resource/js/Chart.js"></script>
	<script src="resource/js/Chart1.js"></script>
	<script src="resource/js/legend.js"></script>
	<script src="resource/js/demo.js"></script>
	<script src="resource/js/zingchart.min.js"></script>

	<!-- for add delete row -->
	<script src="resource/js/itemSelect.js"></script>
	<!-- JS -->
	<script src="resource/js/jquery.js"></script>
	<!-- jQuery -->
	<!-- <script src="resource/js/jquery.min.js"></script> jQuery -->
	<script src="resource/js/jquery-3.1.0.min.js"></script>
	<!-- jQuery -->
	<script src="resource/js/bootstrap.min.js"></script>
	<!-- Bootstrap -->
	<script src="resource/js/jquery-ui.min.js"></script>
	<!-- jQuery UI -->
	<script src="resource/js/fullcalendar.min.js"></script>
	<!-- Full Google Calendar - Calendar -->
	<script src="resource/js/jquery.rateit.min.js"></script>
	<!-- RateIt - Star rating -->
	<script src="resource/js/jquery.prettyPhoto.js"></script>
	<!-- prettyPhoto -->
	<script src="resource/js/jquery.slimscroll.min.js"></script>
	<!-- jQuery Slim Scroll -->
	<script src="resource/js/jquery.dataTables.min.js"></script>
	<!-- Data tables -->

	<!-- jQuery Flot -->
	<script src="resource/js/excanvas.min.js"></script>
	<script src="resource/js/jquery.flot.js"></script>
	<script src="resource/js/jquery.flot.resize.js"></script>
	<script src="resource/js/jquery.flot.pie.js"></script>
	<script src="resource/js/jquery.flot.stack.js"></script>

	<!-- jQuery Notification - Noty -->
	<script src="resource/js/jquery.noty.js"></script>
	<!-- jQuery Notify -->
	<script src="resource/js/themes/default.js"></script>
	<!-- jQuery Notify -->
	<script src="resource/js/layouts/bottom.js"></script>
	<!-- jQuery Notify -->
	<script src="resource/js/layouts/topRight.js"></script>
	<!-- jQuery Notify -->
	<script src="resource/js/layouts/top.js"></script>
	<!-- jQuery Notify -->
	<!-- jQuery Notification ends -->

	<script src="resource/js/sparklines.js"></script>
	<!-- Sparklines -->
	<script src="resource/js/jquery.cleditor.min.js"></script>
	<!-- CLEditor -->
	<script src="resource/js/bootstrap-datetimepicker.min.js"></script>
	<!-- Date picker -->
	<script src="resource/js/jquery.onoff.min.js"></script>
	<!-- Bootstrap Toggle -->
	<script src="resource/js/filter.js"></script>
	<!-- Filter for support page -->
	<script src="resource/js/custom.js"></script>
	<!-- Custom codes -->
	<script src="resource/js/charts.js"></script>
	<!-- Charts & Graphs -->



	<!-- jQuery Unknown -->
	<script src="resource/js/jquery.calculation.js"></script>
	<script src="resource/js/legend.js"></script>
	<script src="resource/js/respond.min.js"></script>
	<script src="resource/js/topRight.js"></script>
	<script src="resource/js/navAccordion.js"></script>
	<script src="resource/js/navAccordion.min.js"></script>

	<script
		src="https://cdn.jsdelivr.net/bootstrap.timepicker/0.2.6/js/bootstrap-timepicker.js"></script>
	<script
		src="https://cdn.jsdelivr.net/bootstrap.timepicker/0.2.6/js/bootstrap-timepicker.min.js"></script>


	<input type="hidden" value="${pageContext.request.contextPath}" id="contextPath">


</body>
</html>

