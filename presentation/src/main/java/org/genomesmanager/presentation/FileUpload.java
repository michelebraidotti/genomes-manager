package org.genomesmanager.presentation;

import org.apache.myfaces.trinidad.model.UploadedFile;

public abstract class FileUpload {
	private UploadedFile _file;

	public UploadedFile getFile() {
		return _file;
	}

	public void setFile(UploadedFile file) {
		_file = file;
	}

	public abstract String doUpload();
}
