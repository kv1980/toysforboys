insert into orderdetails (orderId,productId,ordered,priceEach)
values ((select id from orders where customerId = (select id from customers where name = 'testName')),(select id from products where name = 'testName'),5,10);