package be.vdab.allesvoordekeuken.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import be.vdab.allesvoordekeuken.entities.Artikel;
import be.vdab.allesvoordekeuken.entities.FoodArtikel;
import be.vdab.allesvoordekeuken.entities.NonFoodArtikel;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("/insertArtikel.sql")
@Import(JpaArtikelRepository.class)
public class JpaArtikelRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
	private JpaArtikelRepository repository;
	private static final String ARTIKELS = "artikels";
	private Artikel foodArtikel;
	private Artikel nonFoodArtikel;
	
	@Before
	public void before() {
		foodArtikel = new FoodArtikel("testBanaan", BigDecimal.valueOf(1.5), BigDecimal.valueOf(1.9), 15);
		nonFoodArtikel = new NonFoodArtikel("testBlender", BigDecimal.valueOf(10.5), BigDecimal.valueOf(14.9), 24);
	}
	
	private long idVanTestFoodArtikel() {
		return super.jdbcTemplate.queryForObject("select id from artikels where naam='testFood'", Long.class);
	}
	
	private long idVanTestNonFoodArtikel() {
		return super.jdbcTemplate.queryForObject("select id from artikels where naam='testNonFood'", Long.class);
	}
	
	@Test
	public void readFoodArtikel() {
		FoodArtikel artikel = (FoodArtikel) repository.read(idVanTestFoodArtikel()).get();
		assertEquals("testFood", artikel.getNaam());
	}
	
	@Test
	public void readNonFoodArtikel() {
		NonFoodArtikel artikel = (NonFoodArtikel) repository.read(idVanTestNonFoodArtikel()).get();
		assertEquals("testNonFood", artikel.getNaam());
	}
	
	@Test
	public void readOnbestaandArtikel() {
		assertFalse(repository.read(-1).isPresent());
	}
	
	@Test
	public void createFoodArtikel() {
		int aantalFoodArtikels = super.countRowsInTableWhere(ARTIKELS, "soort='F'");
		repository.create(foodArtikel);
		assertEquals(aantalFoodArtikels + 1, super.countRowsInTableWhere(ARTIKELS, "soort='F'"));
		assertEquals(1, super.countRowsInTableWhere(ARTIKELS, "id=" + foodArtikel.getId()));
		assertTrue(repository.read(foodArtikel.getId()).get().getVerkoopprijs().compareTo(BigDecimal.valueOf(1.9)) == 0);
		assertEquals(1, super.countRowsInTableWhere(ARTIKELS, "id=" + foodArtikel.getId()));
	}
	
	@Test
	public void createNonFoodArtikel() {
		int aantalNonFoodArtikels = super.countRowsInTableWhere(ARTIKELS, "soort='NF'");
		repository.create(nonFoodArtikel);
		assertEquals(aantalNonFoodArtikels + 1, super.countRowsInTableWhere(ARTIKELS, "soort='NF'"));
		assertEquals(1, super.countRowsInTableWhere(ARTIKELS, "id=" + nonFoodArtikel.getId()));
		assertTrue(repository.read(nonFoodArtikel.getId()).get().getVerkoopprijs().compareTo(BigDecimal.valueOf(14.9)) == 0);
		assertEquals(1, super.countRowsInTableWhere(ARTIKELS, "id=" + nonFoodArtikel.getId()));
	}
	
	@Test
	public void findArtikelsMetWoord() {
		List<Artikel> artikelsMetEsInNaam = repository.findArtikelsMetWoord("es");
		long aantalArtikelsMetEs = super.countRowsInTableWhere(ARTIKELS, "naam like '%es%'");
		//long aantalArtikels = super.jdbcTemplate.queryForObject("select count(*) from artikels where naam like '%es%'", Long.class);
		assertEquals(artikelsMetEsInNaam.size(), aantalArtikelsMetEs);
		
		String vorigeNaam = "";
		for(Artikel artikel : artikelsMetEsInNaam) {
			String naam = artikel.getNaam();
			assertTrue(naam.toLowerCase().contains("es"));
			assertTrue(vorigeNaam.compareToIgnoreCase(naam) <= 0);
			vorigeNaam = naam;
		}
	}
	
	@Test
	public void algemenePrijsverhoging() {
		int aantalVerhoogd = repository.algemenePrijsverhoging(BigDecimal.valueOf(10));
		assertEquals(super.countRowsInTable(ARTIKELS), aantalVerhoogd);
		
		BigDecimal nieuwePrijs = super.jdbcTemplate.queryForObject(
				"select verkoopprijs from artikels where id=?", BigDecimal.class, idVanTestNonFoodArtikel());
		assertEquals(0, BigDecimal.valueOf(137.5).compareTo(nieuwePrijs));
	}
}
