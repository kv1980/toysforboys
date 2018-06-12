insert into orders(ordered,required,comments,customerId,status)
values("2000-01-01","2001-01-01","testCommentsA",(select id from customers where name = "testCustomerA"),"PROCESSING"),
	  ("2000-01-02","2001-01-02","testCommentsB",(select id from customers where name = "testCustomerB"),"RESOLVED"),
	  ("2000-01-03","2001-01-03","testCommentsC",(select id from customers where name = "testCustomerC"),"SHIPPED"),
	  ("2000-01-04","2001-01-04","testCommentsD",(select id from customers where name = "testCustomerD"),"CANCELLED");