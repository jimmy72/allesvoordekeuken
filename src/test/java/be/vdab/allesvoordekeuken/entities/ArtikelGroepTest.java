package be.vdab.allesvoordekeuken.entities;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class ArtikelGroepTest {

	private ArtikelGroep groep1;
	private ArtikelGroep groep2;
	private Artikel artikel1;
	@Before
	public void before() {
		groep1 = new ArtikelGroep("test1");
		groep2 = new ArtikelGroep("test2");
		artikel1 = new FoodArtikel("testfood", BigDecimal.valueOf(10), BigDecimal.valueOf(20), 100, groep1);
	}
	@Test
	public void groep1MoetDeArtikelGroepZijnVanArtikel1() {
		assertEquals(groep1, artikel1.getArtikelGroep());
		assertEquals(1, groep1.getArtikels().size());
		assertEquals(0, groep2.getArtikels().size());
		assertTrue(groep1.getArtikels().contains(artikel1));
	}
	@Test
	public void artikel1VerhuistNaarGroep2() {
		artikel1.setArtikelGroep(groep2);
		assertEquals(groep2, artikel1.getArtikelGroep());
		assertEquals(1, groep2.getArtikels().size());
		assertEquals(0, groep1.getArtikels().size());
		assertTrue(groep2.getArtikels().contains(artikel1));
	}

}
