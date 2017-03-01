package org.genomesmanager.repositories.snps;

import java.util.List;

import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.Snp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SnpRepository  extends JpaRepository<Snp, Integer>, SnpRepositoryCustom {
	public abstract List<Snp> findBySequence(Sequence sequence);
}