package org.genomesmanager.domain.entities.objectmothers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.genomesmanager.domain.entities.DnaTeRepeat;
import org.genomesmanager.domain.entities.HelitronRepeat;
import org.genomesmanager.domain.entities.IntervalFeatureException;
import org.genomesmanager.domain.entities.LineRepeat;
import org.genomesmanager.domain.entities.LtrRepeat;
import org.genomesmanager.domain.entities.MiteRepeat;
import org.genomesmanager.domain.entities.Repeat;
import org.genomesmanager.domain.entities.RepeatsClassification;
import org.genomesmanager.domain.entities.Sequence;
import org.genomesmanager.domain.entities.SineRepeat;
import org.genomesmanager.domain.entities.UnknownRepeat;

public class RepeatsOM {
	public static List<Repeat> Generate(int amount, RepeatsClassification repClass, Sequence sequence) {
		List<Repeat> repeats = new ArrayList<Repeat>();
		for (int i = 0; i < amount ; i++) {
			repeats.add(GenerateRepeat(new Repeat(), i, repClass, sequence));
		}
		return repeats;
	}
	
	private static <T extends Repeat> T GenerateRepeat(T rep, int i, RepeatsClassification repClass, Sequence sequence) {
		Random generator = new Random();
		rep.setSequence(sequence);
		rep.setRepeatsClassification(repClass);
		rep.setContainedElementsCount(i);
		rep.setGcContent(new BigDecimal(10 * i));
		String strandness = ( generator.nextInt()%2 ==0 ? "+" : "-");
		try {
			rep.setStrandness(strandness);
			rep.setX(generator.nextInt(sequence.getLength()));
			int offset = sequence.getLength() - rep.getX();
			rep.setY(rep.getX() + generator.nextInt(offset) - 1);
		} catch (IntervalFeatureException e) {
			e.printStackTrace();
		}
		rep.setNotes("genreatedRepeat" + i);
		return rep;
	}
	
	public static List<DnaTeRepeat> GenerateDnaTes(int amount, RepeatsClassification repClass, Sequence sequence) {
		List<DnaTeRepeat> repeats = new ArrayList<DnaTeRepeat>();
		for (int i = 0; i < amount ; i++) {
			DnaTeRepeat dnaTe = GenerateRepeat(new DnaTeRepeat(), i, repClass, sequence);
			dnaTe.setTirX(dnaTe.getX() + 1);
			dnaTe.setTirY(dnaTe.getY() - 1);
			dnaTe.setTransPresence(true);
			dnaTe.setTransSequence("AA");
			dnaTe.setTsdSequence("CC");
			repeats.add(dnaTe);
		}
		return repeats;
	}
	
	public static List<HelitronRepeat> GenerateHelitrons(int amount, RepeatsClassification repClass, Sequence sequence) {
		List<HelitronRepeat> repeats = new ArrayList<HelitronRepeat>();
		for (int i = 0; i < amount ; i++) {
			HelitronRepeat helitron = GenerateRepeat(new HelitronRepeat(), i, repClass, sequence);
			helitron.setIsAutonomus(true);
			helitron.setPotentialCdsCount(i);
			helitron.setOrfGreaterThan50aa(i + 1);
			helitron.setEnd3("TT");
			helitron.setEnd5("GG");
			helitron.setIsPresentInSativa(false);
			repeats.add(helitron);
		}
		return repeats;
	}
	
	public static List<LineRepeat> GenerateLines(int amount, RepeatsClassification repClass, Sequence sequence) {
		List<LineRepeat> repeats = new ArrayList<LineRepeat>();
		for (int i = 0; i < amount ; i++) {
			LineRepeat line = GenerateRepeat(new LineRepeat(), i, repClass, sequence);
			line.setOverallStructureDesc("OverallStructureDesc" + i);
			line.setPolyA(true);
			line.setRtPresence(true);
			line.setRtSequence("AACCTTGG");
			repeats.add(line);
		}
		return repeats;
	}
	
	public static List<LtrRepeat> GenerateLtrs(int amount, RepeatsClassification repClass, Sequence sequence) {
		List<LtrRepeat> repeats = new ArrayList<LtrRepeat>();
		for (int i = 0; i < amount ; i++) {
			LtrRepeat ltr = GenerateRepeat(new LtrRepeat(), i, repClass, sequence);
			ltr.setGcContent3(new BigDecimal(i));
			ltr.setGcContent5(new BigDecimal(i));
			ltr.setIntPresence(true);
			ltr.setIntSequence("AACCTTGG");
			ltr.setIntStopCodonsCount(i + 1);
			ltr.setLtr3Length(i + 2);
			ltr.setLtr5Length(i + 3);
			ltr.setIsComplete(true);
			ltr.setIsSolo(false);
			ltr.setLtrComparisonInsertionTime(new BigDecimal(i + 4));
			ltr.setLtrComparisonMutationAt(i + 1);
			ltr.setLtrComparisonMutationCa(i + 2);
			ltr.setLtrComparisonMutationCg(i + 3);
			ltr.setLtrComparisonMutationCt(i + 4);
			ltr.setLtrComparisonNucDistance(new BigDecimal(i + 6));
			ltr.setLtrComparisonSimilarity(new BigDecimal(i + 7));
			ltr.setPbsX(ltr.getX() + 1);
			ltr.setPbsY(ltr.getY() - 1);
			ltr.setPptX(ltr.getX() + 1);
			ltr.setPptY(ltr.getY() - 1);
			ltr.setRtPresence(true);
			ltr.setRtSequence("CCAATTGG");
			ltr.setRtStopCodonsCount(i + 8);
			ltr.setTsdSequence("GGAATTCC");
			ltr.setPresenceInSativa("PresenceInSativa" + i);
			repeats.add(ltr);
		}
		return repeats;
	}	
	
	public static List<MiteRepeat> GenerateMites(int amount, RepeatsClassification repClass, Sequence sequence) {
		List<MiteRepeat> repeats = new ArrayList<MiteRepeat>();
		for (int i = 0; i < amount ; i++) {
			MiteRepeat mite = GenerateRepeat(new MiteRepeat(), i, repClass, sequence);
			mite.setTirX(mite.getX() + 1);
			mite.setTirY(mite.getY() - 1);
			mite.setTsdSeq("TAGAAAA");
			repeats.add(mite);
		}
		return repeats;
	}
	
	public static List<SineRepeat> GenerateSines(int amount, RepeatsClassification repClass, Sequence sequence) {
		List<SineRepeat> repeats = new ArrayList<SineRepeat>();
		for (int i = 0; i < amount ; i++) {
			SineRepeat sine = GenerateRepeat(new SineRepeat(), i, repClass, sequence);
			sine.setOverallStructureDesc("AACCCTAGGAGAATTT");
			repeats.add(sine);
		}
		return repeats;
	}

	public static List<UnknownRepeat> GenerateUnknowns(int amount, RepeatsClassification repClass, Sequence sequence) {
		List<UnknownRepeat> repeats = new ArrayList<UnknownRepeat>();
		for (int i = 0; i < amount ; i++) {
			UnknownRepeat unkn = GenerateRepeat(new UnknownRepeat(), i, repClass, sequence);
			unkn.setDescription("description" + i);
			repeats.add(unkn);
		}
		return repeats;
	}
}
