insert into customers(name,streetAndNumber,city,state,postalCode,countryid)
values('testCustomerA','testStreetAndNumber','testCity','testState','testPostalCode',(select id from countries where name = 'testCountry')),
	  ('testCustomerB','testStreetAndNumber','testCity','testState','testPostalCode',(select id from countries where name = 'testCountry')),
	  ('testCustomerC','testStreetAndNumber','testCity','testState','testPostalCode',(select id from countries where name = 'testCountry')),
	  ('testCustomerD','testStreetAndNumber','testCity','testState','testPostalCode',(select id from countries where name = 'testCountry'));