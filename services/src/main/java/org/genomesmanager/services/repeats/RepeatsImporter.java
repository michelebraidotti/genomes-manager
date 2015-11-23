package org.genomesmanager.services.repeats;

import org.genomesmanager.domain.entities.Repeat;

import java.util.List;

public interface RepeatsImporter {

	public abstract void parseAgiGff3(List<String> lines);

	public abstract int getRepeatsSize();

	public abstract List<Repeat> getRepeatsList();

	public abstract List<String> getWrongLines();

	public abstract List<String> getWarningLines();

	public abstract void reset();

	public abstract void saveList() throws RepeatsImporterException;

	public abstract List<String> findDuplicates();

}