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


<style type="text/css">
.quick-button-small:hover {
    text-decoration: none;
    border-color: #A5A5EA;
    color: #383e4b;
    text-shadow: 0 12px 4 #fff;
    -webkit-box-shadow: 0 0 3px rgba(0,0,0,.25);
    -moz-box-shadow: 0 0 3px rgba(0,0,0,.25);
    box-shadow: inset 0 0 20px 10px #383e4b;
}
.row-fluid .span2 {
    width: 14.52991452991453%;
}
</style>

<style type="text/css">

.row-fluid .span2 {
    width: 12.029915%;
}

.rejectbutton {
  background-color: #004A7F;
  -webkit-border-radius: 10px;
  border-radius: 10px;
  border: none;
  color: #FFFFFF;
  cursor: pointer;
  display: inline-block;
  font-family: Arial;
  font-size: 13px;
  padding: 5px 10px;
  text-align: center;
  text-decoration: none;
}
@-webkit-keyframes glowing {
  0% { background-color: #B20000; -webkit-box-shadow: 0 0 3px #B20000; }
  50% { background-color: #FF0000; -webkit-box-shadow: 0 0 40px #FF0000; }
  100% { background-color: #B20000; -webkit-box-shadow: 0 0 3px #B20000; }
}

@-moz-keyframes glowing {
  0% { background-color: #B20000; -moz-box-shadow: 0 0 3px #B20000; }
  50% { background-color: #FF0000; -moz-box-shadow: 0 0 40px #FF0000; }
  100% { background-color: #B20000; -moz-box-shadow: 0 0 3px #B20000; }
}

@-o-keyframes glowing {
  0% { background-color: #B20000; box-shadow: 0 0 3px #B20000; }
  50% { background-color: #FF0000; box-shadow: 0 0 40px #FF0000; }
  100% { background-color: #B20000; box-shadow: 0 0 3px #B20000; }
}

@keyframes glowing {
  0% { background-color: #B20000; box-shadow: 0 0 3px #B20000; }
  50% { background-color: #FF0000; box-shadow: 0 0 40px #FF0000; }
  100% { background-color: #B20000; box-shadow: 0 0 3px #B20000; }
}

.rejectbutton {
  -webkit-animation: glowing 1500ms infinite;
  -moz-animation: glowing 1500ms infinite;
  -o-animation: glowing 1500ms infinite;
  animation: glowing 1500ms infinite;
}

.quick-button-small {
    border: 1px solid #ddd;
    padding: 15px 0 0 0;
    font-size: 10px;
    background-color: #efefef;
    background-image: -webkit-gradient(linear,left top,left bottom,from( #fafafa),to( #efefef));
    background-image: -webkit-linear-gradient(top, #f7ebeb, #b5b5a8);
    background-image: -moz-linear-gradient(top, #fafafa, #efefef);
    background-image: -o-linear-gradient(top, #fafafa, #efefef);
    background-image: -ms-linear-gradient(top, #fafafa, #efefef);
    background-image: linear-gradient(top, #fafafa, #efefef);
    -webkit-box-shadow: 0 1px 0 rgba(255,255,255,.8);
    -moz-box-shadow: 0 1px 0 rgba(255,255,255,.8);
    box-shadow: 0 1px 0 rgba(255,255,255,.8);
    -webkit-border-radius: 2px;
    -moz-border-radius: 2px;
    border-radius: 2px;
    display: block;
    text-shadow: 0 1px 0 rgba(255,255,255,.6);
    text-align: center;
    cursor: pointer;
    position: relative;
    -webkit-transition: all .3s ease;
    -moz-transition: all .3s ease;
    -ms-transition: all .3s ease;
    -o-transition: all .3s ease;
    transition: all .3s ease;
}
.comm_height{
height: 106px;
margin-bottom: 5px;
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

	
<link href="${pageContext.request.contextPath}/resource/w2ui/w2ui.min.css" rel="stylesheet">
<script src="${pageContext.request.contextPath}/resource/w2ui/w2ui.min.js"></script>
</head>

<body>

	<input type="hidden" value="${pageContext.request.contextPath}"
		id="contextPath">

	<div class="page-head">
		<h2 class="pull-left" style="color: #428bca;">Dashboard</h2>
		<div class="clearfix"></div>
	</div>

	
	<div style="min-height: 0px;" id="content" class="span10" >	
		<div class="row-fluid">
			<div class="span4 smallstat box mobileHalf" ontablet="span6" ondesktop="span4">
				<i class="fa fa-money blue"></i>
				<span class="title" class="title" style="color: #ef1d07;">GT Allotment Budget</span>
				<span class="value">${totalBudget} &nbsp; Lakh(BDT)</span>
			</div>
			
			<div class="span4 smallstat box mobileHalf" ontablet="span6" ondesktop="span4">
				<i class="fa fa-plane darkGreen"></i>
				<span class="title" class="title" style="color: #ef1d07;">GT Delivered</span>
				<span class="value">${totalDelivery} &nbsp;(BDT)</span>
			</div>
			
			<div class="span4 smallstat box mobileHalf" ontablet="span6" ondesktop="span4">
				<i class="fa fa-smile-o red"></i>
				<span class="title" style="color: #ef1d07;">GT Billed</span>
				<span class="value">${totalBill} &nbsp; Lakh(BDT)</span>
			</div>
		</div>
		
		<div class="row-fluid">
			<!-- Bonus window -->
			<div class="box  noMargin" ontablet="span6" ondesktop="span5">
			    <div class="box-header">
			        <h2>
			            <i class="fa fa-hand-o-up"></i>Shortcuts
			        </h2>
			        <div class="box-icon">
			            <a href="#" class="btn-minimize">
			                <i class="fa fa-chevron-up"></i>
			            </a>
			            <a href="#" class="btn-close">
			                <i class="fa fa-times"></i>
			            </a>
			        </div>
			    </div>
			    <div class="box-content">
			        <a  href="${pageContext.request.contextPath}/addBudgetForm" class="quick-button-small span2 comm_height">
			          <i class="fa fa-spinner fa-spin" aria-hidden="true"></i>
			            <p style="color:#066720;">New Budget Form</p>
			        </a>
			        
			        <a href="${pageContext.request.contextPath}/addDeliveredForm" class="quick-button-small span2 comm_height">
			            <i class="fa fa-paper-plane-o" aria-hidden="true"></i>
			            <p style="color:#066720;">Delivery Entry Form</p>
			        </a>
			        
			        
			        <a href="${pageContext.request.contextPath}/budgetList" class="quick-button-small span2 comm_height">
			            <i class="fa fa-th-large"></i>
			            <p style="color:#066720;">Budget List</p>
			        </a>
			        
			         <a href="${pageContext.request.contextPath}/addStockReportForm" class="quick-button-small span2 comm_height">
			          <i class="fa fa-bars" aria-hidden="true"
						style="color: green;"></i>
						<p style="color:#066720;">Stock Report</p>
			        </a>
			        
			         <a href="${pageContext.request.contextPath}/alAltmntReportForm" class="quick-button-small span2 comm_height">
			            <i class="fa fa-clone"></i>
			            <p style="color:#066720;">1. All Allotment</p>
			        </a>
			        
			        <a href="${pageContext.request.contextPath}/altmntColorReports" class="quick-button-small span2 comm_height">
			          <i class="fa fa fa-list-alt" aria-hidden="true"
						style="color: green;"></i>
						<p style="color:#066720;">9. Color Code</p>
			        </a>
			        
			        <a href="${pageContext.request.contextPath}/pointVSGoodDeliversForm" class="quick-button-small span2 comm_height" >
			            <i class="fa fa-list-ol"></i>
			            <p style="color:#066720;">10. Point Vs Goods</p>
			        </a>
			        			      
			       <div class="clear"></div>
			    </div>
			</div>
		</div>
		
	</div>

 <div class="clear"></div>
 
	<!--   <div class="matter"> -->
	<div class="container">

		<div class="row">

			<div class="col-md-12">

				<!-- Table -->

				<div class="row">

					<div class="col-md-12">

						<div class="widget">

							<div class="widget-head">
								<div class="pull-left">All Allotment</div>
								<div class="widget-icons pull-right">
									<span>Form-1</span>
									<a href="#" class="wminimize"><i class="fa fa-chevron-up"></i></a>
									<a href="#" class="wclose"><i class="fa fa-times"></i></a>
								</div>
								<div class="clearfix"></div>
							</div>

							<div class="widget-content">
								
								<div class="table-responsive">
									<c:set var="prevBudgetSource" value="" />
									

									<%-- <c:if test="${!empty allAllotment}">
										<table class="table table-bordered table-hover" id="table1">
											<thead>
												<tr style="background-color: #428bca; color: white">
													<th class="text-center">Sl. No.</th>
													<th class="text-center">Point Name</th>
													<th class="text-center">Budget Code</th>
													<th class="text-center">Budget Amount (BDT) Lakh</th>
													<th class="text-center">Bill Amount (BDT) Lakh</th>
													<th class="text-center">Bill Due  (BDT) Lakh </th>													
													<th class="text-center">Team Leader</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${allAllotment}" var="allotment"
													varStatus="all">
													<c:set var="currBudgetSource" value="${allotment.budgetSource}" />
													<c:if test="${empty prevBudgetSource or prevBudgetSource != currBudgetSource}">
														<tr>
															<td class="" colspan="7">${currBudgetSource}</td>																												
														</tr>
													</c:if>
													<tr>
														<td class="text-center">${allotment.slNO}</td>
														<td class="text-center">${allotment.pointName}</td>
														<td class="text-center">${allotment.budgetCode}</td>
														<td class="text-center">${allotment.budgetAmount}</td>
														<td class="text-center">${allotment.billAmount}</td>
														<td class="text-center">${allotment.billPendingAmount}</td>
														<td class="text-center">${allotment.teamLeaderName}</td>														
													</tr>
													<c:set var="prevBudgetSource" value="${allotment.budgetSource}" />
												</c:forEach>

											</tbody>
										</table>
									</c:if> --%>
									<c:if test="${!empty budgetList}">
										<table class="table table-bordered table-hover" id="table1">
											<thead>
												<tr style="background-color: #428bca; color: white">
													<th class="text-center">Sl. No.</th>
													<th class="text-center">Sharok No.</th>
													<th class="text-center">Budget Date</th>
													<th class="text-center">Fiscal Year</th>
													<th class="text-center">Point Name</th>
													<th class="text-center">Goods Type</th>
													<th class="text-center">Budget Source</th>
													<th class="text-center">Budget Code</th>
													<th class="text-center">Color Code</th>
													<th class="text-center">Budget (BDT) Lakh</th>
													<th class="text-center">Dues Bill (BDT) Lakh</th>
													<th class="text-center">Billed (BDT) Lakh</th>
													<th class="text-center">Team Leader</th>
													<th class="text-center">Remarks</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${budgetList}" var="budget"
													varStatus="bgt">
													<tr>
														<td class="text-center">${budget.budgetId}</td>
														<td class="text-center">${budget.smarokNo}</td>
														<td class="text-center">${budget.bgtDate}</td>
														<td class="text-center">${budget.fiscalYear.name}</td>
														<td class="text-left">${budget.point.name} - (${budget.point.keyword})</td>
														<td class="text-center">${budget.goodsType.name}</td>
														<td class="text-center">${budget.budgetSource.name}</td>
														<td class="text-center">${budget.budgetCode.name}</td>
														<td class="text-center" style="background-color:${budget.colorCode.hexaCode};vertical-align:middle; color:#ffff;">${budget.colorCode.name}</td>
														<td class="text-center">${budget.bgtAmount}</td>
														<td class="text-center">${budget.bgtAmount - budget.billAmount}</td>
														<td class="text-center">${budget.billAmount}</td>
														<td class="text-center">${budget.point.teamLeader.name}</td>
														<td class="text-center">${budget.remarks}</td>
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


	<!-- Mainbar ends -->
	<div class="clearfix"></div>
</body>
</html>