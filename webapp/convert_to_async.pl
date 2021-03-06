#!/usr/bin/perl
use strict;
use warnings;

my $lines =
'public abstract LineRepeat getLine(int lineId) throws RepeatRepoException;
	
		public abstract LtrRepeat getLtr(int lrtId) throws RepeatRepoException;
	
		public abstract MiteRepeat getMite(int miteId) throws RepeatRepoException;
	
		public abstract SineRepeat getSine(int sineId) throws RepeatRepoException;
	
		public abstract UnknownRepeat getUnkn(int unknId) throws RepeatRepoException;
	
		public abstract Repeat getNew(RepeatsClassification repClass) throws RepeatRepoException;
	
		public abstract void insert(Repeat repeat) throws RepeatRepoException;
	
		public abstract void update(Repeat repeat) throws RepeatRepoException;
	
		public abstract void validatePosition(Repeat repeat) throws RepeatRepoException;
	
		public abstract void validateUpdate(Repeat rep) throws RepeatRepoException;
	
		public abstract Long countChildren(int repId) throws RepeatRepoException;
	
		public abstract Repeat getParent(int repId);
	
		public abstract List<Repeat> getAllBySequence(int seqId);
	
		public abstract List<Repeat> getAllBySequence(int seqId, RepeatsOrder repOrder);
	
		public abstract List<Repeat> getAllByChromosome(int chrId, RepeatsOrder repOrder);
	
		public abstract List<Repeat> getAllBySequence(int seqId, RepeatsClassification repClass);
	
		public abstract List<Repeat> getAllInRange(int seqId, int start, int end);
	
		public abstract List<LtrRepeat> getAllLtr(int seqId);
	
		public abstract List<LtrRepeat> getAllLtrInRange(int seqId, int start, int end);
	
		public abstract List<Repeat> getAllBySequence(int seqId, RepeatsOrder repType, String superFamily);
	
		public abstract List<Repeat> getAllBySpecies(SpeciesPK id);
	
		public abstract List<Repeat> getAllByChromosome(int chrId);
	
		public abstract List<Repeat> getAllByChromosome(int chrId, RepeatsClassification repClass);
	
		public abstract List<Repeat> getAllByChromosome(int chrId, RepeatsOrder repOrd, String superFamily);'
;
my @lines_arr =  split(/\n/, $lines);
foreach my $line (@lines_arr) {
	$line =~ s/public abstract (.+?) (.+?)\)/public abstract void $2, AsyncCallback<$1> callback)/;
	print $line."\n";
}
