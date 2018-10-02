insert into artikels(naam, aankoopprijs, verkoopprijs, houdbaarheid, soort, artikelgroepid) values
('testFood', 10, 15, 14, 'F', (select id from artikelgroepen where naam='test'));
insert into artikels(naam, aankoopprijs, verkoopprijs, garantie, soort, artikelgroepid) values
('testNonFood', 100, 125, 24, 'NF', (select id from artikelgroepen where naam='test'));
insert into kortingen(artikelid, vanafAantal, percentage) values
((select id from artikels where naam='testFood'), 5, 3.5);

