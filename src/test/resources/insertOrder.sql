insert into orders(ordered,required,comments,customerId,status)
values("2000-01-01","2001-01-01","testComments",(select id from customers where name = "testName"),"PROCESSING");
