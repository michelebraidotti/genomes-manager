package org.genomesmanager.services.sequences;

public interface PseudomoleculeImporter {

	public abstract void importPseudomolecule(int chrId,
			StringBuilder sequenceBulilder, String name, String version)
			throws PseudomoleculeImporterException;

	public abstract void updatePseudomolecule(int seqId,
			StringBuilder sequenceBulilder)
			throws PseudomoleculeImporterException;

}