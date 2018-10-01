insert into artikels(naam, aankoopprijs, verkoopprijs, houdbaarheid, soort) values
('testFood', 10, 15, 14, 'F');
insert into artikels(naam, aankoopprijs, verkoopprijs, garantie, soort) values
('testNonFood', 100, 125, 24, 'NF');
insert into kortingen(artikelid, vanafAantal, percentage) values
((select id from artikels where naam='testFood'), 5, 3.5);
