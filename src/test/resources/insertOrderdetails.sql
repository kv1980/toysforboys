insert into orderdetails (orderId,productId,ordered,priceEach)
values ((select id from orders where ordered = "2000-01-01"),(select id from products where name = 'testProductA'),5,10.01),
	   ((select id from orders where ordered = "2000-01-01"),(select id from products where name = 'testProductB'),6,20.02),
	   ((select id from orders where ordered = "2000-01-02"),(select id from products where name = 'testProductA'),5,10.01),
	   ((select id from orders where ordered = "2000-01-02"),(select id from products where name = 'testProductB'),31,20.02);