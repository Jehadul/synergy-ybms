<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page session="true"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<style>
.image {
	width: 32px;
	height: 32px;
	border-radius: 50%; 
	background-image: url("path/to/image");
	background-position: center center;
	/* as mentioned by Vad: */
	background-size: cover;
}
</style>


<div class="row">
	<div class="col-xs-12 text-center">
		<h1>
			<strong>Yearly Budget Management System</strong>
		</h1>
		<h3 class="blue">
			<strong>Fiscal Year : ${fiscalYear}</strong>
		</h3>

	</div>
	
	<div class="col-xs-11 text-center">
		${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
		<c:set var="userName" value="${sessionScope.userr}" scope="page" />
		<c:set var="userId" value="${sessionScope.userrId}" scope="page" />
		<c:set var="fiscalYear" value="${sessionScope.fiscalYear}" scope="page" />
		<div class="col-xs-2">
			<span></span>
		</div>
		<div class="" style="float: right">
		
			<span class="btn btn-primary"><strong><c:out
							value="${userName}" /></strong>
			</span> 
			
			<!-- added by taleb -->
			<span class=""><a class="btn btn-info"
				style="background-color: #E5EBFF; color: orange;"
				href="${pageContext.request.contextPath}/<c:url value='j_spring_security_logout' />"><i
				class="fa fa-power-off"></i><span> Logout</span></a></span>
		</div>
	</div>


</div>
