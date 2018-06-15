<%@ page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='spring' uri='http://www.springframework.org/tags'%>
<%@taglib prefix='vdab' uri='http://vdab.be/tags'%>
<!doctype html>
<html lang='nl'>
<vdab:head title="Order info" />
<body>
	<h1>Order ${order.id}</h1>
	<dl id="orderInfo">
		<dt>Ordered:</dt>
		<dd>
			<spring:eval expression='order.ordered' />
		</dd>
		<dt>Required:</dt>
		<dd>
			<spring:eval expression='order.required' />
		</dd>
		<dt>Customer:</dt>
		<dd>${order.customer.name}<br>
			${order.customer.adress.streetAndNumber}<br>
			${order.customer.adress.postalCode} ${order.customer.adress.city}
			${order.customer.adress.state}<br>
			${order.customer.adress.country.name}
		</dd>
		<dt>Comments:</dt>
		<dd>${order.comments}</dd>
		<dt>Details:</dt>
		<dd>
			<table id="orderdetails">
				<tr>
					<th>Product</th>
					<th>Price each</th>
					<th>Quantity</th>
					<th>Value</th>
					<th>Deliverable</th>
				</tr>
				<c:forEach var='orderdetail' items='${order.orderdetails}'>
					<tr>
						<td>${orderdetail.product.name}</td>
						<td><spring:eval expression='orderdetail.priceEach'/></td>
						<td>${orderdetail.ordered}</td>
						<td><spring:eval expression='orderdetail.value'/></td>
						<td>
							<c:choose>
								<c:when test='${orderdetail.deliverable}'>
									&check;
								</c:when>
								<c:otherwise>
									&cross;
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</table>
		</dd>
		<dt>Value:</dt>
		<dd><spring:eval expression='order.value'/></dd>
	</dl>
</body>
</html>

