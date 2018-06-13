<%@ page contentType="text/html" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='spring' uri='http://www.springframework.org/tags'%>
<%@taglib prefix='vdab' uri='http://vdab.be/tags'%>
<!doctype html>
<html lang='nl'>
<vdab:head title="Order info" />
<body>
	<h1>Order ${order.id}</h1>
	<dl>
		<dt>Ordered: </dt>
		<dd><spring:eval expression='order.ordered'/></dd>
		<dt>Required: </dt>
		<dd><spring:eval expression='order.required'/></dd>
		<dt>Customer: </dt>
		<dd>${order.customer.name}</dd>
		<dd>${order.customer.adress.streetAndNumber}</dd>
		<dd>${order.customer.adress.streetAndNumber}</dd>
		<dd>${order.customer.adress.streetAndNumber}</dd>
	</dl>
</body>
</html>

