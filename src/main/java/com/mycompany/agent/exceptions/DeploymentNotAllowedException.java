package com.mycompany.agent.exceptions;

import com.mulesoft.agent.exception.ArtifactValidationException;

public class DeploymentNotAllowedException extends ArtifactValidationException {

    public DeploymentNotAllowedException() {
        super();
    }

    /**
     * Creates an instance of the exception with a message explaining the cause.
     *
     * @param message The message explaining the cause of the exception.
     */
    public DeploymentNotAllowedException(String message) {
        super(message);
    }

    /**
     * Creates an instance of the exception with a message explaining the cause and the exception that triggered it.
     *
     * @param message       The message explaining the cause of the exception.
     * @param originalCause The exception that triggered this exception.
     */
    public DeploymentNotAllowedException(String message, Exception originalCause) {
        super(message, originalCause);
    }
}
