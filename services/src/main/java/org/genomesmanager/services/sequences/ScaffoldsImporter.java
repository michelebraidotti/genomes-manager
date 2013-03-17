package org.genomesmanager.services.sequences;

import java.util.List;

import org.genomesmanager.common.formats.ScaffoldInfo;
import org.genomesmanager.common.formats.SimpleFasta;
import org.genomesmanager.domain.entities.Scaffold;
import org.genomesmanager.domain.entities.Species;

public interface ScaffoldsImporter {
	public abstract void importScaffoldsWithInfo(List<ScaffoldInfo> scaffolds,
			List<SimpleFasta> fastas, Species sp)
			throws ScaffoldsImporterException;
	
	public abstract void importScaffolds(List<String> manifest,
			List<String> fastaLines, Species sp)
			throws ScaffoldsImporterException;

	public abstract void importScaffolds(int chrId, String version,
			List<String> fastaLines) throws ScaffoldsImporterException;

	public abstract void reset();

	public abstract int countScaffolds();

	public abstract List<Scaffold> getScaffolds();

	public abstract List<String> getWrongLines();

	public abstract List<String> getWarningLines();

	public abstract void save() throws ScaffoldsImporterException;

}