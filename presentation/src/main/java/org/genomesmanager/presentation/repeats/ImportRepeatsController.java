package org.genomesmanager.presentation.repeats;

import org.apache.myfaces.trinidad.model.UploadedFile;
import org.genomesmanager.presentation.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("importRepeatsController")
@Scope("session")
public class ImportRepeatsController extends FileUpload {

	@Autowired
	private RepeatsImporter repeatsImporter;

	public ImportRepeatsController() {
	}

	@Override
	public String doUpload() {
		UploadedFile file = getFile();
		// ... and process it in some way
		return "";
	}

}
