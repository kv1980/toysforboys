insert into customers(name,streetAndNumber,city,state,postalCode,countryid)
values('testName','testStreetAndNumber','testCity','testPostalCode',(select id from countries where name = 'testCountry'));