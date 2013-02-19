package org.genomesmanager.presentation.sequences;

import java.io.IOException;

import org.apache.myfaces.trinidad.model.UploadedFile;
import org.genomesmanager.presentation.FileUpload;
import org.genomesmanager.services.sequences.PseudomoleculeImporter;
import org.genomesmanager.services.sequences.ScaffoldsImporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("importSequencesController")
@Scope("request")
public class ImportSequencesController extends FileUpload {

	@Autowired
	private PseudomoleculeImporter pseudomoleculeImporter;
	@Autowired
	private ScaffoldsImporter scaffoldsImporter;

	public ImportSequencesController() {
	}

	@Override
	public String doUpload() {
		UploadedFile file = getFile();
		try {
			file.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
			return "ioException";
		}
		return "success";
	}

}
