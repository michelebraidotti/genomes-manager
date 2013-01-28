package org.genomesmanager.services.impl.repeats;

import java.util.List;

import org.genomesmanager.domain.entities.DnaTeRepeat;
import org.genomesmanager.domain.entities.HelitronRepeat;
import org.genomesmanager.domain.entities.LineRepeat;
import org.genomesmanager.domain.entities.LtrRepeat;
import org.genomesmanager.domain.entities.MiteRepeat;
import org.genomesmanager.domain.entities.Repeat;
import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.RepeatsOrder;
import org.genomesmanager.domain.entities.SineRepeat;
import org.genomesmanager.domain.entities.SpeciesPK;
import org.genomesmanager.domain.entities.UnknownRepeat;
import org.genomesmanager.repositories.repeats.RepeatRepo;
import org.genomesmanager.repositories.repeats.RepeatRepoException;
import org.genomesmanager.repositories.repeats.RepeatsList;
import org.genomesmanager.services.repeats.RepeatsBrowser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("RepeatsBrowser")
public class RepeatsBrowserImpl implements RepeatsBrowser {
	@Autowired
	private RepeatRepo repeatRepo;
	@Autowired
	private RepeatsList repeatsList;
	
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#delete(org.genomesmanager.domain.entities.Repeat)
	 */
	@Override
	public void delete(Repeat repeat) {
		repeatRepo.delete(repeat);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#deleteByKey(int)
	 */
	@Override
	public void deleteByKey(int repId) {
		repeatRepo.deleteByKey(repId);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#get(int)
	 */
	@Override
	public Repeat get(int repId) throws RepeatRepoException {
		return repeatRepo.get(repId);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#getDnaTe(int)
	 */
	@Override
	public DnaTeRepeat getDnaTe(int dnaTeId) throws RepeatRepoException {
		return repeatRepo.getDnaTe(dnaTeId);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#getHelitron(int)
	 */
	@Override
	public HelitronRepeat getHelitron(int helitronId)
			throws RepeatRepoException {
		return repeatRepo.getHelitron(helitronId);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#getLine(int)
	 */
	@Override
	public LineRepeat getLine(int lineId) throws RepeatRepoException {
		return repeatRepo.getLine(lineId);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#getLtr(int)
	 */
	@Override
	public LtrRepeat getLtr(int lrtId) throws RepeatRepoException {
		return repeatRepo.getLtr(lrtId);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#getMite(int)
	 */
	@Override
	public MiteRepeat getMite(int miteId) throws RepeatRepoException {
		return repeatRepo.getMite(miteId);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#getSine(int)
	 */
	@Override
	public SineRepeat getSine(int sineId) throws RepeatRepoException {
		return repeatRepo.getSine(sineId);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#getUnkn(int)
	 */
	@Override
	public UnknownRepeat getUnkn(int unknId) throws RepeatRepoException {
		return repeatRepo.getUnkn(unknId);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#getNew(org.genomesmanager.domain.entities.RepeatsClassification)
	 */
	@Override
	public Repeat getNew(RepeatsClassification repClass)
			throws RepeatRepoException {
		return repeatRepo.getNew(repClass);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#insert(org.genomesmanager.domain.entities.Repeat)
	 */
	@Override
	public void insert(Repeat repeat) throws RepeatRepoException {
		repeatRepo.insert(repeat);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#update(org.genomesmanager.domain.entities.Repeat)
	 */
	@Override
	public void update(Repeat repeat) throws RepeatRepoException {
		repeatRepo.update(repeat);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#validatePosition(org.genomesmanager.domain.entities.Repeat)
	 */
	@Override
	public void validatePosition(Repeat repeat) throws RepeatRepoException {
		repeatRepo.validatePosition(repeat);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#validateUpdate(org.genomesmanager.domain.entities.Repeat)
	 */
	@Override
	public void validateUpdate(Repeat rep) throws RepeatRepoException {
		repeatRepo.validateUpdate(rep);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#countChildren(int)
	 */
	@Override
	public Long countChildren(int repId) throws RepeatRepoException {
		return repeatRepo.countChildren(repId);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#getParent(int)
	 */
	@Override
	public Repeat getParent(int repId) {
		return repeatRepo.getParent(repId);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#getAllBySequence(int)
	 */
	@Override
	public List<Repeat> getAllBySequence(int seqId) {
		return repeatsList.getAllBySequence(seqId);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#getAllBySequence(int, org.genomesmanager.domain.entities.RepeatsOrder)
	 */
	@Override
	public List<Repeat> getAllBySequence(int seqId, RepeatsOrder repOrder) {
		return repeatsList.getAllBySequence(seqId, repOrder);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#getAllByChromosome(int, org.genomesmanager.domain.entities.RepeatsOrder)
	 */
	@Override
	public List<Repeat> getAllByChromosome(int chrId, RepeatsOrder repOrder) {
		return repeatsList.getAllByChromosome(chrId, repOrder);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#getAllBySequence(int, org.genomesmanager.domain.entities.RepeatsClassification)
	 */
	@Override
	public List<Repeat> getAllBySequence(int seqId,
			RepeatsClassification repClass) {
		return repeatsList.getAllBySequence(seqId, repClass);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#getAllInRange(int, int, int)
	 */
	@Override
	public List<Repeat> getAllInRange(int seqId, int start, int end) {
		return repeatsList.getAllInRange(seqId, start, end);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#getAllLtr(int)
	 */
	@Override
	public List<LtrRepeat> getAllLtr(int seqId) {
		return repeatsList.getAllLtr(seqId);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#getAllLtrInRange(int, int, int)
	 */
	@Override
	public List<LtrRepeat> getAllLtrInRange(int seqId, int start, int end) {
		return repeatsList.getAllLtrInRange(seqId, start, end);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#getAllBySequence(int, org.genomesmanager.domain.entities.RepeatsOrder, java.lang.String)
	 */
	@Override
	public List<Repeat> getAllBySequence(int seqId, RepeatsOrder repType,
			String superFamily) {
		return repeatsList.getAllBySequence(seqId, repType, superFamily);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#getAllBySpecies(org.genomesmanager.domain.entities.SpeciesPK)
	 */
	@Override
	public List<Repeat> getAllBySpecies(SpeciesPK id) {
		return repeatsList.getAllBySpecies(id);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#getAllByChromosome(int)
	 */
	@Override
	public List<Repeat> getAllByChromosome(int chrId) {
		return repeatsList.getAllByChromosome(chrId);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#getAllByChromosome(int, org.genomesmanager.domain.entities.RepeatsClassification)
	 */
	@Override
	public List<Repeat> getAllByChromosome(int chrId,
			RepeatsClassification repClass) {
		return repeatsList.getAllByChromosome(chrId, repClass);
	}
	/* (non-Javadoc)
	 * @see org.genomesmanager.services.impl.repeats.RepeatsBrowser#getAllByChromosome(int, org.genomesmanager.domain.entities.RepeatsOrder, java.lang.String)
	 */
	@Override
	public List<Repeat> getAllByChromosome(int chrId, RepeatsOrder repOrd,
			String superFamily) {
		return repeatsList.getAllByChromosome(chrId, repOrd, superFamily);
	}
	
	
}
