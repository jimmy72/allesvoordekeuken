package be.vdab.allesvoordekeuken.valueobjects;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class Korting implements Serializable{

	private static final long serialVersionUID = 1L;
	private int vanafAantal;
	private BigDecimal percentage;
	
	protected Korting() {
	}
	
	public Korting(int vanafAantal, BigDecimal percentage) {
		if(percentage.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException();
		}
		this.vanafAantal = vanafAantal;
		this.percentage = percentage;
	}

	public int getVanafAantal() {
		return vanafAantal;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((percentage == null) ? 0 : percentage.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Korting)) {
			return false;
		}
		Korting eenKorting = (Korting) obj;
		return (this.getPercentage().compareTo(eenKorting.getPercentage()) == 0);
	}


	
	
}
