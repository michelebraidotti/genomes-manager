package org.genomesmanager.repositories.repeats;

import org.genomesmanager.domain.entities.DnaTeRepeat;
import org.genomesmanager.domain.entities.HelitronRepeat;
import org.genomesmanager.domain.entities.LineRepeat;
import org.genomesmanager.domain.entities.LtrRepeat;
import org.genomesmanager.domain.entities.MiteRepeat;
import org.genomesmanager.domain.entities.Repeat;
import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.SineRepeat;
import org.genomesmanager.domain.entities.UnknownRepeat;

public interface RepeatRepo {


	public abstract void insert(Repeat repeat) throws RepeatRepoException;

	public abstract void update(Repeat repeat) throws RepeatRepoException;


}