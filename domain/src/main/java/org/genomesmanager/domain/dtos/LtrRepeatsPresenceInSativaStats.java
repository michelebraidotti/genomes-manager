package org.genomesmanager.domain.dtos;

import java.io.Serializable;

public class LtrRepeatsPresenceInSativaStats implements Serializable {
	private static final long serialVersionUID = 1L;
	private String presenceType;
	private Long count;
	
	public LtrRepeatsPresenceInSativaStats(String presenceType, Long count) {
		super();
		this.presenceType = presenceType;
		this.count = count;
	}

	public String getPresenceType() {
		return presenceType;
	}

	public Long getCount() {
		return count;
	}	
	
}
