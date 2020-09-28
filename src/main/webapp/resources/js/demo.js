if(!!(window.addEventListener)) window.addEventListener('DOMContentLoaded', main);
else window.attachEvent('onload', main);

function main() {
    lineChart();
    barChart();
}

function lineChart() {
	
	var testQuantity= document.getElementById("testQuantity").value;
	var totalTest=document.getElementById("totalTest").value;	
	var value2=document.getElementById("value2").value;	
	var totalPrice=document.getElementById("totalPrice").value;	

	var data = {
			labels : [(prevMonthName+"(No Of totalTest = "+testQuantity+")"),(currMonthName+"(No Of totalTest = "+totalTest+")")],
			datasets : [
				{
					label: "",
					point: "hhhh",
					fillColor : "white",
					strokeColor : "white",
					pointColor : "white",
					pointStrokeColor : "white",
					pointHighlightFill : "white",
					pointHighlightStroke : "white",
					data : [testQuantity, totalTest]
				},
				{
					label: "Total Price",
					fillColor : "rgba(151,187,205,0.2)",
					strokeColor : "rgba(77,186,192,1.0)",
					pointColor : "rgba(151,187,205,1)",
					pointStrokeColor : "77,186,192,1.0",
					pointHighlightFill : "#fff",
					pointHighlightStroke : "rgba(151,187,205,1)",
					data : [value2,totalPrice]
				}
			]

		};

    var ctx = document.getElementById("lineChart").getContext("2d");
    new Chart(ctx).Line(data);

    legend(document.getElementById("lineLegend"), data);

    // testing adding twice (should get same result)
    //legend(document.getElementById("lineLegend"), data);
}

function barChart() {

var prev0=document.getElementById("prev[0]").value;
var prev1=document.getElementById("prev[1]").value;	
var prev2=document.getElementById("prev[2]").value;	
var prev3=document.getElementById("prev[3]").value;	
var prev4=document.getElementById("prev[4]").value;	
var prev5=document.getElementById("prev[5]").value;	
var prev6=document.getElementById("prev[6]").value;	
var prev7=document.getElementById("prev[7]").value;	
var prev8=document.getElementById("prev[8]").value;	
var prev9=document.getElementById("prev[9]").value;	
var prev10=document.getElementById("prev[10]").value;	
var prev11=document.getElementById("prev[11]").value;

var curr0=document.getElementById("curr[0]").value;
var curr1=document.getElementById("curr[1]").value;	
var curr2=document.getElementById("curr[2]").value;	
var curr3=document.getElementById("curr[3]").value;	
var curr4=document.getElementById("curr[4]").value;	
var curr5=document.getElementById("curr[5]").value;	
var curr6=document.getElementById("curr[6]").value;	
var curr7=document.getElementById("curr[7]").value;	
var curr8=document.getElementById("curr[8]").value;	
var curr9=document.getElementById("curr[9]").value;	
var curr10=document.getElementById("curr[10]").value;	
var curr11=document.getElementById("curr[11]").value;

var data = {
labels :  ["Fully Automated Clinical Chemistry Analyzer","Semi Auto Bio Chemistry Analyzer","Electrolyte Analyzer","Hematology Analyzer","Hematology Lab Automation","Electrophoresis Lab Automation","Urine Lab Automation","Serology Test","Digital X-Ray Machine","CT Scan","MRI","Ultrasonogram"],
datasets : [
{
label: prevMonthName,
fillColor : "rgba(49,159,44,1.0)",
strokeColor : "rgba(220,220,220,0.8)",
highlightFill : "rgba(49,159,44,0.8)",
highlightStroke: "rgba(220,220,220,1)",
data : [prev0,prev1,prev2,prev3,prev4,prev5,prev6,prev7,prev8,prev9,prev10,prev11]
 			
 },
{
label: currMonthName,
fillColor : "rgba(0,191,255,1.0)",
strokeColor : "rgba(151,187,205,0.8)",
highlightFill : "rgba(0,128,255,0.9)",
highlightStroke : "rgba(151,187,205,1)",
data : [curr0,curr1,curr2,curr3,curr4,curr5,curr6,curr7,curr8,curr9,curr10,curr11]
}
]

}; 
var ctx = document.getElementById("barChart").getContext("2d");
new Chart(ctx).Bar(data);

legend(document.getElementById("barLegend"), data);

// testing adding twice (should get same result)
legend(document.getElementById("barLegend"), data);
}

/*function pieChart() {
    var data = [
        {
            value: 30,
            color:"#F38630",
            label: 'Bears'
        },
        {
            value : 50,
            color : "#E0E4CC",
            label: 'Lynxes'
        },
        {
            value : 100,
            color : "#69D2E7",
            label: 'Reindeer'
        }
    ];

    var ctx = document.getElementById("pieChart").getContext("2d");
    var pieChart = new Chart(ctx).Pie(data);

    legend(document.getElementById("pieLegend"), data, pieChart);
}

function doughnutChart() {
    var data = [
        {
            value: 40,
            color:"#F38630",
            label: 'Animals'
        },
        {
            value : 20,
            color : "#E0E4CC",
            label: 'People'
        },
        {
            value : 30,
            color : "#69D2E7",
            label: 'Aliens'
        }
    ];

    var ctx = document.getElementById("doughnutChart").getContext("2d");
    var doughnutChart = new Chart(ctx).Doughnut(data);

    legend(document.getElementById("doughnutLegend"), data, doughnutChart);
}



function doughnutChartWithCustomLegend() {
    var data = [
        {
            value: 40,
            color:"#F38630",
            label: 'Chocolate'
        },
        {
            value : 20,
            color : "#E0E4CC",
            label: 'Floor'
        },
        {
            value : 30,
            color : "#69D2E7",
            label: 'Butter'
        }
    ];

    var ctx = document.getElementById("doughnutChartWithCustomLegend").getContext("2d");
    var doughnutChartWithCustomLegend = new Chart(ctx).Doughnut(data,{tooltipTemplate:"<%=label%>: <%=value%>g"});

    legend(document.getElementById("doughnutChartCustomLegend"), data, doughnutChartWithCustomLegend, "<%=label%>: <%=value%>g");*/
//}