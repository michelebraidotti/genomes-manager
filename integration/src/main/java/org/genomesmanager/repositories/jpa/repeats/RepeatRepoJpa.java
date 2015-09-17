package org.genomesmanager.repositories.jpa.repeats;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.genomesmanager.domain.entities.DnaTeRepeat;
import org.genomesmanager.domain.entities.HelitronRepeat;
import org.genomesmanager.domain.entities.IntervalFeatureException;
import org.genomesmanager.domain.entities.LineRepeat;
import org.genomesmanager.domain.entities.LtrRepeat;
import org.genomesmanager.domain.entities.MiteRepeat;
import org.genomesmanager.domain.entities.OutOfBoundsException;
import org.genomesmanager.domain.entities.Repeat;
import org.genomesmanager.domain.entities.RepeatException;
import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.RepeatsOrder;
import org.genomesmanager.domain.entities.SineRepeat;
import org.genomesmanager.domain.entities.UnknownRepeat;
import org.genomesmanager.repositories.repeats.RepeatRepo;
import org.genomesmanager.repositories.repeats.RepeatRepoException;
import org.springframework.stereotype.Repository;

@Repository("RepeatRepo")
public class RepeatRepoJpa implements RepeatRepo {
	// TODO move the remaining methods to a service class (RepeatService??)
	// TODO fix tests

	@PersistenceContext
	private EntityManager em;
	private Query q;

	public RepeatRepoJpa() {
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatRepo#insert(org.
	 * genomesmanager.domain.entities.Repeat)
	 */
	@Override
	public void insert(Repeat repeat) throws RepeatRepoException {
		validate(repeat);
		try {
			em.persist(repeat);
		} catch (PersistenceException ex) {
			throw new RepeatRepoException("Error writing repeat to database."
					+ " Sequence: " + repeat.getSequence().toString()
					+ " start: " + repeat.getX() + " end: " + repeat.getY());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.genomesmanager.repositories.jpa.repeats.RepeatRepo#update(org.
	 * genomesmanager.domain.entities.Repeat)
	 */
	@Override
	public void update(Repeat repeat) throws RepeatRepoException {
		validateUpdate(repeat);
		validate(repeat);
		Repeat existingRepeat = get(repeat.getId());
		if (existingRepeat != null) {
			if (existingRepeat.getDateModified()
					.after(repeat.getDateModified())) {
				throw new RepeatRepoException("Conflict while editing repeat "
						+ repeat.getId());
			}
			if (existingRepeat.getParent() == null
					&& repeat.getParent() != null) {
				repeat.getParent().setContainedElementsCount(
						repeat.getParent().getContainedElementsCount() + 1);
			}
		}
		em.merge(repeat);
	}

	private void validate(Repeat repeat) throws RepeatRepoException {
		try {
			repeat.validate();
			if (repeat instanceof LtrRepeat) {
				((LtrRepeat) repeat).checkIsSolo();
			}
		} catch (OutOfBoundsException e) {
			throw new RepeatRepoException(e.getMessage());
		} catch (IntervalFeatureException e) {
			throw new RepeatRepoException(e.getMessage());
		} catch (RepeatException e) {
			throw new RepeatRepoException(e.getMessage());
		}
	}

}
