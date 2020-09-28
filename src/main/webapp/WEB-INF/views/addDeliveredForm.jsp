<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<!-- Title and other stuffs -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="author" content="">

<c:url var="deliveredList" value="/getDeliveredList" />
<c:url var="saveDeliveredList" value="/saveDeliveredList" />

<!-- <link rel="stylesheet" type="text/css"
	href="http://rawgit.com/vitmalina/w2ui/master/dist/w2ui.min.css" /> -->
	
<link href="${pageContext.request.contextPath}/resource/w2ui/w2ui.min.css" rel="stylesheet">


<style type="text/css">
td img {
	display: block;
	margin-left: auto;
	margin-right: auto;
}

.centered {
	width: 50px;
	margin: 0px, auto, 0px, auto;
}

.col-xs-1, .col-xs-2, .col-xs-3, .col-xs-4, .col-xs-5, .col-xs-6, .col-xs-7, .col-xs-8, .col-xs-9, .col-xs-10, .col-xs-11, .col-xs-12 {
    float: none;
}
</style>



<script type="text/javascript">
	function goBack() {
		window.history.back();
	}

	$(document).ready(function() {
		$('#table1').DataTable({
			dom : 'Bfrtip',
			buttons : [ 'copy', 'csv', 'excel', 'print', {
				extend : 'pdfHtml5',
				orientation : 'landscape',
				pageSize : 'LEGAL'
			} ]
		});

	});
</script>

</head>

<body>

	<input type="hidden" value="${pageContext.request.contextPath}"
		id="contextPath">

	<div class="page-head">
		<h2 class="pull-left" style="color: #428bca;">Delivered Entry Form</h2>
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
								<div class="pull-left">Delivered Entry Form</div>
								<div class="widget-icons pull-right">
									<a href="#" class="wminimize"><i class="fa fa-chevron-up"></i></a>
									<a href="#" class="wclose"><i class="fa fa-times"></i></a>
								</div>
								<div class="clearfix"></div>
							</div>

							<div class="widget-content table-responsive">

								<div id="grid" style="width: 100%; height: 500px;"></div>																
								<br> <div class="clearfix"></div>
								<button class="w2ui-btn" onclick="showChanged()">Submit</button>
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

	<script type="text/javascript">
		var deliveredListURL = '<c:out value="${deliveredList}"/>';
		var saveDeliveredListURL = '<c:out value="${saveDeliveredList}"/>';

		var resultData = new Array();
		
		$(function() {
			loadTableData();
		});

		function showChanged() {
			var tableData = w2ui['grid'].getChanges();
			
			if(tableData.length > 0){
				$.ajax({
					type : "post",
					url : saveDeliveredListURL,
					async : false,
					data : JSON.stringify({
						deliveredList : w2ui['grid'].getChanges()
					}),
					contentType : "application/json",
					success : function(response) {
						var result = JSON.parse(response);
						if (result == 'success') {
							location.reload();
							w2alert("Data Updated Successfully...Please Wait...");
						} else {
							w2alert("Please try again after sometimes! :" + result);
						}

					},
					error : function() {
						w2alert("Server Error!!!");
					}
				});
			}			
			
		}
		

		function loadTableData() {

			$.ajax({
				type : "post",
				url : deliveredListURL,
				async : false,
				data : JSON.stringify({
					id : null
				}),
				contentType : "application/json",
				success : function(response) {
					result = JSON.parse(response);					
					resultData = result;					
				},
				error : function() {
				}
			});

			$('#grid').w2grid({
				name : 'grid',
				show : {
					toolbar : true,
					footer : true
				},
				multiSearch : true,
				searches : [ {
					field : 'slNo',
					caption : 'SL. No.',
					type : 'int'
				}, {
					field : 'recid',
					caption : 'Row ID ',
					type : 'int'
				},{
					field : 'deliveryId',
					caption : 'Delivery ID ',
					type : 'text'
				}, {
					field : 'point.nameEn',
					caption : 'Point',
					type : 'text'
				}],
				columns : [ {
					field : 'slNo',
					caption : '<center>SL. No.<center>',
					size : '50px',
					sortable : true,
					attr: 'align=center',
					resizable : false
				}, {
					field : 'recid',
					caption : '<center>Row ID<center>',
					size : '50px',
					sortable : true,
					attr: 'align=center',
					resizable : false
				}, {
					field : 'deliveryId',
					caption : '<center>Delivery ID<center>',
					size : '80px',
					sortable : true,
					attr: 'align=center',
					resizable : false
				}, {
					field : 'point.nameEn',
					caption : '<center>Point<center>',
					size : '250px',
					sortable : true,
					resizable : true,
					editable : false
				}, {
					field : 'bgtEquipments',
					caption : '<center>Bgt Equipments-4112315<center>',
					size : '180px',
					sortable : true,
					resizable : true,
					attr: 'align=right',
					editable : false
				}, 
				 {
					field : 'dlvEquipments',
					caption : '<center>Dlv Equipments-4112315<center>',
					size : '180px',
					sortable : true,
					resizable : true,
					attr: 'align=right',
					editable : {
						type : 'int'
					}
				}, 
				{
					field : 'bgtFurniture',
					caption : '<center>Bgt Furniture-4112314<center>',
					size : '180px',
					sortable : true,
					resizable : true,
					attr: 'align=right',
					editable : false
				}, 
				{
					field : 'dlvFurniture',
					caption : '<center>Dlv Furniture-4112314<center>',
					size : '180px',
					sortable : true,
					resizable : true,
					attr: 'align=right',
					editable : {
						type : 'int'
					}
				},
				{
					field : 'bgtComputer',
					caption : '<center>Bgt Computer-4112202<center>',
					size : '180px',
					sortable : true,
					resizable : true,
					attr: 'align=right',
					editable : false
				}, 
				 {
					field : 'dlvComputer',
					caption : '<center>Dlv Computer-4112202<center>',
					size : '180px',
					sortable : true,
					resizable : true,
					attr: 'align=right',
					editable : {
						type : 'int'
					}
				},
				{
					field : 'bgtRepair',
					caption : '<center>Bgt Repair-3258105<center>',
					size : '180px',
					sortable : true,
					resizable : true,
					attr: 'align=right',
					editable : false
				}, 
				{
					field : 'dlvRepair',
					caption : '<center>Dlv Repair-3258105<center>',
					size : '180px',
					sortable : true,
					resizable : true,
					attr: 'align=right',
					editable : {
						type : 'int'
					}
				}, 
				{
					field : 'bgtReagent',
					caption : '<center>Bgt Reagent-3256102<center>',
					size : '180px',
					sortable : true,
					resizable : true,
					attr: 'align=right',
					editable : false
				}, 
				 {
					field : 'dlvReagent',
					caption : '<center>Dlv Reagent-3256102<center>',
					size : '180px',
					sortable : true,
					resizable : true,
					attr: 'align=right',
					editable : {
						type : 'int'
					}
				},
				{
					field : 'bgtBooks',
					caption : '<center>Bgt Books-3211127<center>',
					size : '180px',
					sortable : true,
					resizable : true,
					attr: 'align=right',
					editable : false
				}, {
					field : 'dlvBooks',
					caption : '<center>Dlv Books-3211127<center>',
					size : '180px',
					sortable : true,
					resizable : true,
					attr: 'align=right',
					editable : {
						type : 'int'
					}
				}, {
					field : 'bgtAmount',
					caption : '<center>Budget Amount BDT (Lakh)<center>',
					size : '160px',
					sortable : true,
					resizable : true,
					attr: 'align=right',
					editable : false
				}, {
					field : 'dlvAmount',
					caption : '<center>Delivered Amount BDT<center>',
					size : '150px',
					sortable : true,
					resizable : true,
					attr: 'align=right',
					editable : false
				}, {
					field : 'remarks',
					caption : '<center>Remarks<center>',
					size : '200px',
					sortable : true,
					resizable : true,
					editable : {
						type : 'text'
					}
				} ],
				records : resultData
			});

		}

		/* $(document).on("click", "#otSummaryFinalSubmitBtn",function() {
			var tableData = w2ui['grid'].getChanges();
			if (tableData.length > 0) {
				w2alert("Your Changes Are Not Saved. Please Click Submit Button First.");
			} else {
				w2confirm('After final submit you can\'t change any data forever, Do you Agree?',
					function btn(answer) {
						if (answer == 'Yes') {
							$('#otSummaryFinalSubmitForm')
									.submit();
							w2alert("Please Wait...");
						}
				});
			}
		}); */
	</script>
	<script src="${pageContext.request.contextPath}/resource/w2ui/w2ui.min.js"></script>
</body>
</html>