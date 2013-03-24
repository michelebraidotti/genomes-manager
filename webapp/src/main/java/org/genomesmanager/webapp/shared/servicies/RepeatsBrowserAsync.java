package org.genomesmanager.webapp.shared.servicies;

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
import org.genomesmanager.repositories.repeats.RepeatRepoException;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RepeatsBrowserAsync {

	public abstract void delete(Repeat repeat, AsyncCallback<Void> callback);
	
		public abstract void deleteByKey(int repId);
	
		public abstract void get(int repId, AsyncCallback<Repeat> callback) throws RepeatRepoException;
	
		public abstract void getDnaTe(int dnaTeId, AsyncCallback<DnaTeRepeat> callback)
				throws RepeatRepoException;
	
		public abstract void getHelitron(int helitronId, AsyncCallback<HelitronRepeat> callback)
				throws RepeatRepoException;
	
		public abstract void getLine(int lineId, AsyncCallback<LineRepeat> callback) throws RepeatRepoException;
		
		public abstract void getLtr(int lrtId, AsyncCallback<LtrRepeat> callback) throws RepeatRepoException;
	
		public abstract void getMite(int miteId, AsyncCallback<MiteRepeat> callback) throws RepeatRepoException;
	
		public abstract void getSine(int sineId, AsyncCallback<SineRepeat> callback) throws RepeatRepoException;
	
		public abstract void getUnkn(int unknId, AsyncCallback<UnknownRepeat> callback) throws RepeatRepoException;
	
		public abstract void getNew(RepeatsClassification repClass, AsyncCallback<Repeat> callback) throws RepeatRepoException;
	
		public abstract void insert(Repeat repeat, AsyncCallback<Void> callback) throws RepeatRepoException;
	
		public abstract void update(Repeat repeat, AsyncCallback<Void> callback) throws RepeatRepoException;
	
		public abstract void validatePosition(Repeat repeat, AsyncCallback<Void> callback) throws RepeatRepoException;
	
		public abstract void validateUpdate(Repeat rep, AsyncCallback<Void> callback) throws RepeatRepoException;
	
		public abstract void countChildren(int repId, AsyncCallback<Long> callback) throws RepeatRepoException;
	
		public abstract void getParent(int repId, AsyncCallback<Repeat> callback);
	
		public abstract void getAllBySequence(int seqId, AsyncCallback<List<Repeat>> callback);
	
		public abstract void getAllBySequence(int seqId, RepeatsOrder repOrder, AsyncCallback<List<Repeat>> callback);
	
		public abstract void getAllByChromosome(int chrId, RepeatsOrder repOrder, AsyncCallback<List<Repeat>> callback);
	
		public abstract void getAllBySequence(int seqId, RepeatsClassification repClass, AsyncCallback<List<Repeat>> callback);
	
		public abstract void getAllInRange(int seqId, int start, int end, AsyncCallback<List<Repeat>> callback);
	
		public abstract void getAllLtr(int seqId, AsyncCallback<List<LtrRepeat>> callback);
	
		public abstract void getAllLtrInRange(int seqId, int start, int end, AsyncCallback<List<LtrRepeat>> callback);
	
		public abstract void getAllBySequence(int seqId, RepeatsOrder repType, String superFamily, AsyncCallback<List<Repeat>> callback);
	
		public abstract void getAllBySpecies(SpeciesPK id, AsyncCallback<List<Repeat>> callback);
	
		public abstract void getAllByChromosome(int chrId, AsyncCallback<List<Repeat>> callback);
	
		public abstract void getAllByChromosome(int chrId, RepeatsClassification repClass, AsyncCallback<List<Repeat>> callback);
	
		public abstract void getAllByChromosome(int chrId, RepeatsOrder repOrd, String superFamily, AsyncCallback<List<Repeat>> callback);

}
