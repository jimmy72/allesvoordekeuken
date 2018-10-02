package be.vdab.allesvoordekeuken.entities;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="artikelgroepen")
public class ArtikelGroep implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String naam;
	@OneToMany(mappedBy = "artikelGroep") 
	@OrderBy("naam") 
	private Set<Artikel> artikels;
	
	protected ArtikelGroep() {
	}

	public ArtikelGroep(String naam) {
		this.naam = naam;
		this.artikels = new LinkedHashSet<>();
	}

	public long getId() {
		return id;
	}

	public String getNaam() {
		return naam;
	}

	public Set<Artikel> getArtikels() {
		return Collections.unmodifiableSet(this.artikels);
	}
	
	public boolean addArtikel(Artikel artikel) {
		if(artikel == null) {
			throw new NullPointerException();
		}
		boolean toegevoegd = this.artikels.add(artikel);
		ArtikelGroep oudeArtikelGroep = artikel.getArtikelGroep();
		//als artikelgroep van het artikel dat we willen toevoegen een andere is dan de huidige artikelgroep
		//dan verwijderen we dit artikel uit die oudeArtikelGroep
		if(oudeArtikelGroep != null && oudeArtikelGroep != this) {
			oudeArtikelGroep.removeArtikel(artikel);
		}
		//als deze artikelgroep een andere is dan die van het artikel dat we willen toevoegen,
		//voeg dan deze artikelgroep toe aan dit artikel
		if (this != oudeArtikelGroep) {
			artikel.setArtikelGroep(this);
		}
		return toegevoegd;
	}
	
	public boolean removeArtikel(Artikel artikel) {
		return artikels.remove(artikel);
	}
	
	
}
