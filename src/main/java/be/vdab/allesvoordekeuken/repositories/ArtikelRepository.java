package be.vdab.allesvoordekeuken.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import be.vdab.allesvoordekeuken.entities.Artikel;

public interface ArtikelRepository {
	public abstract Optional<Artikel> read(long id);
	public abstract void create(Artikel artikel);
	public abstract List<Artikel> findArtikelsMetWoord(String woord);
	public abstract int algemenePrijsverhoging(BigDecimal percentage);
}
