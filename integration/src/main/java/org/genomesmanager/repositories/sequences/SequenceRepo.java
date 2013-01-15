package org.genomesmanager.repositories.sequences;

import org.genomesmanager.domain.entities.Scaffold;
import org.genomesmanager.domain.entities.Sequence;

public interface SequenceRepo {

	public abstract void delete(Sequence seq);

	public abstract void deleteByKey(int seqId);

	public abstract Sequence get(int seqId) throws SequenceRepoException;

	public abstract Scaffold getScaffold(int seqId)
			throws SequenceRepoException;

	public abstract Scaffold getScaffold(Sequence seq)
			throws SequenceRepoException;

	public abstract Sequence get(String seqName) throws SequenceRepoException;

	public abstract Sequence getLatest(String seqName)
			throws SequenceRepoException;

	public abstract void insert(Sequence seq);

	public abstract void update(Sequence seq);

	public abstract int getLength(int seqId) throws SequenceRepoException;

}