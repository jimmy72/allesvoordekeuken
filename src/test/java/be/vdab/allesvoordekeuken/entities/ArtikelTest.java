package be.vdab.allesvoordekeuken.entities;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class ArtikelTest {

	private Artikel artikel1;
	private ArtikelGroep groep1;
	private ArtikelGroep groep2;
	@Before
	public void before() {
		groep1 = new ArtikelGroep("test");
		groep2 = new ArtikelGroep("test2");
		artikel1 = new FoodArtikel("test",BigDecimal.ONE,BigDecimal.ONE,1,groep1);
	}
	@Test
	public void groep1EnArtikel1ZijnGoedVerbonden() {
		assertEquals(groep1, artikel1.getArtikelGroep());
		assertEquals(1, groep1.getArtikels().size());
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
