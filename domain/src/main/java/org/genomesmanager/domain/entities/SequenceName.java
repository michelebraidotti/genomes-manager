package org.genomesmanager.domain.entities;

import java.io.Serializable;


public class SequenceName implements Serializable, Comparable<SequenceName> {
	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	
	public SequenceName(Sequence seq) {
		id = seq.getId();
		name = seq.toString();
	}
	

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}

	public int compareTo(SequenceName otherName) {
		return name.compareTo(otherName.getName());
	}
	
}
