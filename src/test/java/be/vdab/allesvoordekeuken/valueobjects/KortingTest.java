package be.vdab.allesvoordekeuken.valueobjects;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class KortingTest {

	private Korting k1;
	private Korting k1bis;
	private Korting k2;
		
	@Before
	public void before() {
		k1 = new Korting(5, BigDecimal.valueOf(2.5000));
		k1bis = new Korting(10, BigDecimal.valueOf(2.5));
		k2 = new Korting(20, BigDecimal.valueOf(3.00));
	}

	@Test (expected = NullPointerException.class)
	public void kortingMetNulAlsNummerMislukt() {
		new Korting(10, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void kortingMetNegatiefPercentageKanNiet() {
		new Korting(10, BigDecimal.valueOf(-1));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void kortingMet0AlsPercentageKanNiet() {
		new Korting(10, BigDecimal.ZERO);
	}
	
	@Test
	public void vanafAantalMagNullZijn() {
		new Korting( 0, BigDecimal.ONE);
	}
	
	@Test
	public void tweeKortingObjectenZijnGelijkAlsPercentagesGelijkZijn() {
		assertEquals(k1, k1bis);
	}
	
	@Test
	public void tweeKortingObjectenZijnVerschillendAlsPercentagesVerschillendZijn() {
		assertNotEquals(k1, k2);
	}
	
	@Test
	public void eenKortingVerschiltVanNull() {
		assertNotEquals(k1, null);
	}
	@Test
	public void eenKortingVerschiltVanEenAnderTypeObject() {
		assertNotEquals(k1, "");
	}
	@Test
	public void gelijkeKortingenGevenDezelfdeHashCode() {
		assertEquals(k1.hashCode(), k1bis.hashCode());
	}
}
