package org.genomesmanager.domain.dtos;

import java.io.Serializable;
import java.math.BigInteger;

public class RepeatsStatsByClassification implements Serializable {
	private static final long serialVersionUID = 1L;
	private String rclass;
	private String subclass;
	private String rorder;
	private String superfamily;
	private BigInteger count;
	private BigInteger countNucl;
	
	public RepeatsStatsByClassification(String rclass, String subclass,
			String rorder, String superfamily, BigInteger count, BigInteger countNucl) {
		super();
		this.rclass = rclass;
		this.subclass = subclass;
		this.rorder = rorder;
		this.superfamily = superfamily;
		this.count = count;
		this.countNucl = countNucl;
	}

	public String getRclass() {
		return rclass;
	}

	public String getSubclass() {
		return subclass;
	}

	public String getRorder() {
		return rorder;
	}

	public String getSuperfamily() {
		return superfamily;
	}

	public BigInteger getCount() {
		return count;
	}

	public BigInteger getCountNucl() {
		return countNucl;
	}
	
}
