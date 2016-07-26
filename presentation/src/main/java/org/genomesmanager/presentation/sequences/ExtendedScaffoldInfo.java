package org.genomesmanager.presentation.sequences;

import org.genomesmanager.bioprograms.formats.ScaffoldInfo;
import org.genomesmanager.bioprograms.formats.ScaffoldInfoException;

public class ExtendedScaffoldInfo extends ScaffoldInfo {
	private String chrDescr;

	public ExtendedScaffoldInfo(String desc) throws ScaffoldInfoException {
		super(desc);
	}

	public String getChrDescr() {
		return chrDescr;
	}

	public void setChrDescr(String chrDescr) {
		this.chrDescr = chrDescr;
	}
	
}
