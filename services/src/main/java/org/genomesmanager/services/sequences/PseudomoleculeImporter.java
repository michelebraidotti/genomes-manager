package org.genomesmanager.services.sequences;

import org.genomesmanager.common.formats.SimpleFasta;

import java.util.List;

public interface PseudomoleculeImporter {

	public abstract void importPseudomolecule(int chrId,
			List<SimpleFasta> fastas, String version)
			throws PseudomoleculeImporterException;
	
	public abstract void importPseudomolecule(int chrId,
			StringBuilder sequenceBulilder, String name, String version)
			throws PseudomoleculeImporterException;

	public abstract void updatePseudomolecule(int seqId,
			StringBuilder sequenceBulilder)
			throws PseudomoleculeImporterException;

}