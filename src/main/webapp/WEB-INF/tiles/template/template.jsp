<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><tiles:insertAttribute name="title"/></title>
  <%--   <link rel="stylesheet" type="text/css" href="<c:url value='static/css/style/style.css'/>"/> --%>
     <link href="static/css/style/style.css" rel="stylesheet">
    
<script type="text/javascript"> /* window.history.forward();function noBack(){window.history.forward();} */
</script> 
</head>
<body> <!-- onload="noBack();" onpageshow="if (event.persisted) noBack();" onunload="" -->
    <div id="header">
        <tiles:insertAttribute name="header"/>
    </div>
    
    <div id="menu">
        <tiles:insertAttribute name="menu" ignore="true"/>     
    </div>
    
    <div class="mainbar">
        <tiles:insertAttribute name="body"/>
        <div id="footer" >
            <tiles:insertAttribute name="footer"/>
        </div>
    </div>
</body>
</html>