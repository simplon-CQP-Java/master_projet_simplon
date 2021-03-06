<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<head>
	<title>Calendrier</title>
</head>

<t:genericpage>
	<jsp:attribute name="header">
		<%@ include file="/WEB-INF/jsp/include/menu.jsp"%>
	</jsp:attribute>
	<jsp:body>
			<div id="bookings-calendar" class="row">
				<div class="col-md-12">
					<h2>Calendrier des réservations de salles et ordinateurs</h2>
				</div>
				<div id="calendar" class="col-md-12"></div>
			</div>
	</jsp:body>
</t:genericpage>