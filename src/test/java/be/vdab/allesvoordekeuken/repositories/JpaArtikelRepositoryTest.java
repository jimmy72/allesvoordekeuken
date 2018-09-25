package be.vdab.allesvoordekeuken.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

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

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("/insertArtikel.sql")
@Import(JpaArtikelRepository.class)
public class JpaArtikelRepositoryTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
	private JpaArtikelRepository repository;
	private static final String ARTIKELS = "artikels";
	private Artikel artikel;
	
	@Before
	public void before() {
		artikel = new Artikel("test", BigDecimal.valueOf(100), BigDecimal.valueOf(147.5));
	}
	
	private long getIdVanTestArtikel() {
		return super.jdbcTemplate.queryForObject("select id from artikels where naam='test'", Long.class);
	}
	
	@Test
	public void read() {
		artikel = repository.read(getIdVanTestArtikel()).get();
		assertEquals("test", artikel.getNaam());
	}
	
	@Test
	public void readOnbestaandArtikel() {
		assertFalse(repository.read(-1).isPresent());
	}
	
	@Test
	public void createArtikel() {
		int aantalArtikels = super.countRowsInTable(ARTIKELS);
		repository.create(artikel);
		assertEquals(aantalArtikels + 1, super.countRowsInTable(ARTIKELS));
		assertNotEquals(0, artikel.getId());
		assertEquals(1, super.countRowsInTableWhere(ARTIKELS, "id=" + artikel.getId()));
		assertTrue(repository.read(artikel.getId()).get().getVerkoopprijs().compareTo(BigDecimal.valueOf(147.5)) == 0);
	}
}
