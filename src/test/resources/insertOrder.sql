insert into orders(ordered,required,customerId,status)
values("2000-01-01","2001-01-01",(select id from customers where name = "testName"),"PROCESSING");
