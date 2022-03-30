package com.mycompany.agent;

import com.mulesoft.agent.exception.ArtifactValidationException;
import com.mulesoft.agent.services.ArtifactValidator;
import com.mulesoft.agent.services.EncryptionService;
import com.mycompany.agent.exceptions.DeploymentNotAllowedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.regex.Pattern;

import static java.lang.String.format;

@Named("MuleAgentNameValidator")
@Singleton
public class MuleAgentMoratoriumValidator implements ArtifactValidator {

    private static final Logger LOGGER = LogManager.getLogger(MuleAgentMoratoriumValidator.class);

    // Properties provided by Mule Agent Plugin
    public static final String APPLICATION_NAME_KEY = "_APPLICATION_NAME";

    // Properties configured in the Artifact Validator service
    public static final String START_DATE_KEY = "startDate";
    public static final String END_DATE_KEY = "endDate";

    public String getType() {
        return "moratoriumValidator";
    }

    public String getName() {
        return "defaultMoratoriumValidator";
    }

    public void validate(Map<String, Object> args) throws ArtifactValidationException {

        String applicationName = (String) args.get(APPLICATION_NAME_KEY);
        String startDateAsString = (String) args.get(START_DATE_KEY);
        String endDateAsString = (String) args.get(END_DATE_KEY);

        LocalDateTime startDate = LocalDateTime.parse(startDateAsString);
        LocalDateTime endDate = LocalDateTime.parse(endDateAsString);

        LocalDateTime now = LocalDateTime.now();

        LOGGER.info(startDate);
        LOGGER.info(endDate);
        LOGGER.info(now);

        if (now.isAfter(startDate) && now.isBefore(endDate)) {
            String message =
                format("Application '%s' cannot be deployed. Reason: Active moratorium window from '%s' to '%s'.",
                    applicationName,
                    startDateAsString,
                    endDateAsString);
            LOGGER.error(message);
            throw new DeploymentNotAllowedException(message);
        }

    }
}
