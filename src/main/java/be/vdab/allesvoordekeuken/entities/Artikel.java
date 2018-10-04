package be.vdab.allesvoordekeuken.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import be.vdab.allesvoordekeuken.valueobjects.Korting;

@Entity
@Table(name = "artikels")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "soort")
@NamedEntityGraph(name = Artikel.MET_ARTIKELGROEP, attributeNodes = @NamedAttributeNode("artikelGroep")) //leest bijbehorende artikelgroep via join
public abstract class Artikel implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String MET_ARTIKELGROEP = "Artikel.metArtikelGroep";
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String naam;
	private BigDecimal aankoopprijs;
	private BigDecimal verkoopprijs;
	@ElementCollection 
	@CollectionTable(name = "kortingen", joinColumns = @JoinColumn(name = "artikelid")) 
	@OrderBy("percentage")
	private Set<Korting> kortingen;
	@ManyToOne(fetch = FetchType.LAZY, optional = false) 
	@JoinColumn(name = "artikelgroepid") 
	private ArtikelGroep artikelGroep;
	
	protected Artikel() {
	}
	
	public Artikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs, ArtikelGroep artikelGroep) {
		this.naam = naam;
		this.aankoopprijs = aankoopprijs;
		this.verkoopprijs = verkoopprijs;
		this.kortingen = new LinkedHashSet<>();
		this.setArtikelGroep(artikelGroep);
	}
		
	public Set<Korting> getKortingen() {
		return Collections.unmodifiableSet(this.kortingen);
	}
	
	public boolean addKorting(Korting korting) {
		if(korting == null) {
			throw new NullPointerException();
		}
		return this.kortingen.add(korting);
	}
	
	public boolean removeKorting(Korting korting) {
		return this.kortingen.remove(korting);
	}

	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getNaam() {
		return naam;
	}
	public void setNaam(String naam) {
		this.naam = naam;
	}
	public BigDecimal getAankoopprijs() {
		return aankoopprijs;
	}
	public void setAankoopprijs(BigDecimal aankoopprijs) {
		this.aankoopprijs = aankoopprijs;
	}
	public BigDecimal getVerkoopprijs() {
		return verkoopprijs;
	}
	public void setVerkoopprijs(BigDecimal verkoopprijs) {
		this.verkoopprijs = verkoopprijs;
	}

	public ArtikelGroep getArtikelGroep() {
		return artikelGroep;
	}

	public void setArtikelGroep(ArtikelGroep artikelGroep) {
		if(artikelGroep == null) {
			throw new NullPointerException();
		}
		
		if(!artikelGroep.getArtikels().contains(this)) {
			artikelGroep.addArtikel(this);
		}
		this.artikelGroep = artikelGroep;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((naam == null) ? 0 : naam.toUpperCase().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Artikel))
			return false;
		Artikel other = (Artikel) obj;
		if (naam == null) {
			if (other.naam != null)
				return false;
		} else if (!naam.equalsIgnoreCase(other.naam))
			return false;
		return true;
	}
		
	
}
