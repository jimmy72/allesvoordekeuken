package be.vdab.allesvoordekeuken.entities;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("NF")
public class NonFoodArtikel extends Artikel{

	private static final long serialVersionUID = 1L;
	private int garantie;
	
	protected NonFoodArtikel() {
		super();
	}
	
	public NonFoodArtikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs, int garantie, ArtikelGroep artikelGroep) {
		super(naam, aankoopprijs, verkoopprijs, artikelGroep);
		this.garantie = garantie;
	}

	public int getGarantie() {
		return garantie;
	}
}
