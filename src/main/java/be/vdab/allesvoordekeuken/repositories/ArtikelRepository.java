package be.vdab.allesvoordekeuken.repositories;

import java.util.Optional;

import be.vdab.allesvoordekeuken.entities.Artikel;

public interface ArtikelRepository {
	public abstract Optional<Artikel> read(long id);
	public abstract void create(Artikel artikel);
}
