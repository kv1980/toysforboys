<%@ page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='spring' uri='http://www.springframework.org/tags'%>
<%@taglib prefix='vdab' uri='http://vdab.be/tags'%>
<!doctype html>
<html lang='nl'>
<vdab:head title="Unshipped orders" />
<body>
	<h1>Unshipped orders</h1>
	<div id='failedErrorMessage'>
		<c:if test='${not empty errors}'>
				Shipment failed for the following order(s):
				<c:forEach var='error' items='${errors}'>
				<p>
					<c:forEach var='id' items='${error.ids}' varStatus='status'>
						<c:choose>
							<c:when test='${status.first}'>- ${id}</c:when>
							<c:when test='${status.last}'>and ${id} </c:when>
							<c:otherwise>, ${id}</c:otherwise>
						</c:choose>
					</c:forEach>
					: ${error.message}
				</p>
			</c:forEach>
		</c:if>
	</div>
	<form action='/' method='post' id='form'>
		<table id="unshippedOrders">
			<tr>
				<th>ID</th>
				<th>Ordered</th>
				<th>Required</th>
				<th>Customer</th>
				<th>Comments</th>
				<th>Status</th>
				<th>Shipped</th>
			</tr>
			<c:forEach var='order' items='${unshippedOrders}'>
				<tr>
					<td><spring:url value='/order/{id}' var='url'>
							<spring:param name='id' value='${order.id}' />
						</spring:url> <a href='${url}'>${order.id}</a></td>
					<td><spring:eval expression='order.ordered' /></td>
					<td><spring:eval expression='order.required' /></td>
					<td>${order.customer.name}</td>
					<td>${order.comments}</td>
					<td><c:choose>
							<c:when test="${order.status == 'PROCESSING'}">
								<img src="/images/PROCESSING.png" alt="Processing">
							Processing
						</c:when>
							<c:when test="${order.status == 'WAITING'}">
								<img src="/images/WAITING.png" alt="Waiting">
							Waiting
						</c:when>
							<c:when test="${order.status == 'DISPUTED'}">
								<img src="/images/DISPUTED.png" alt="Disputed">
							Disputed
						</c:when>
							<c:when test="${order.status == 'RESOLVED'}">
								<img src="/images/RESOLVED.png" alt="Resolved">
							Resolved
						</c:when>
						</c:choose></td>
					<td><input type='checkbox' name='orderToShipId'
						value='${order.id}' /></td>
				</tr>
			</c:forEach>
		</table>
		<input type='submit' value='Set as shipped' id='SetAsShippedButton'>
	</form>
	<div id='pages'>
		<p>Pages:</p>
		<ul>
			<c:forEach begin='1' end='${numberOfPages}' varStatus='status'>
				<spring:url var='url' value='/{id}'>
					<spring:param name='id' value='${status.count}'/>
				</spring:url>
				<li><a href='${url}'>${status.count}</a></li>
			</c:forEach>
		</ul>
	</div>

	<script>
		document.getElementById('form').onsubmit = function() {
			document.getElementById('SetAsShippedButton').disabled = true;
		}
	</script>
</body>