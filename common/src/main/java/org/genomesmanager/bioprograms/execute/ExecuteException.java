package org.genomesmanager.bioprograms.execute;

import java.io.IOException;

public class ExecuteException extends Exception {

    private static final long serialVersionUID = 1L;

    public ExecuteException(String arg0) {
        super(arg0);
    }

    public ExecuteException(Exception e) {
        super(e);
    }

    public ExecuteException(String message, Throwable cause) {
        super(message, cause);
    }
}
