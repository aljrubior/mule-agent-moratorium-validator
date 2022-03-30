## Install the Validator in the Mule Runtime

The corresponding JAR should be added under the lib folder within the mule-agent plugin, which is contained in the plugins folder of the Mule instance.

For example, $MULE_HOME/server-plugins/mule-agent-plugin/lib/mule-agent-properties-validator.jar.

## Mule Agent Moratorium Validator Configuration

In the following configuration, we are going to implement rules for naming applications.

File: $MULE_HOME/conf/mule-agent.yml

```
services:
  mule.agent.artifact.validator.service:
    enabled: true
    validators:
    - type: moratoriumValidator
      name: defaultMoratoriumValidator
      enabled: true
      args:
        startDate: '2022-03-28T00:00:00'
        endDate: '2022-04-04T00:00:00'
```

### Log4j Configuration

File: $MULE_HOME/conf/log4j2.xml

```
 <AsyncLogger name="com.mycompany.agent" level="INFO"/>
```

### Test the Name Validator

Deploy an application that has an invalid name.

Command

```
curl -X PUT 'http://localhost:9999/mule/applications/finance-prod-sapAccountsV1' \
-H 'Content-Type: application/octet-stream' \
-F 'file=@"/repositories/apps/app-01.jar"'
```

Response

```
{
  "type": "class com.mycompany.agent.exceptions.DeploymentNotAllowedException",
  "message": "Application 'finance-prod-sapAccountsV1' cannot be deployed. Reason: Active moratorium window from '2022-03-30T00:00:00' to '2022-03-31T00:00:00'."
}
```

Logs

```
INFO  2022-03-30 20:11:26,893 [qtp1881766154-60] [processor: ; event: ] com.mulesoft.agent.services.application.AgentApplicationService: Deploying the finance-prod-sapAccountsV1 application from jar file.
ERROR 2022-03-30 20:11:26,966 [qtp1881766154-60] [processor: ; event: ] com.mycompany.agent.MuleAgentMoratoriumValidator: Application 'finance-prod-sapAccountsV1' cannot be deployed. Reason: Active moratorium window from '2022-03-28T00:00:00' to '2022-04-04T00:00:00'.
ERROR 2022-03-30 20:11:26,976 [qtp1881766154-60] [processor: ; event: ] com.mulesoft.agent.external.handlers.deployment.ApplicationsRequestHandler: Error performing the deployment of finance-prod-sapAccountsV1. Cause: DeploymentNotAllowedException: Application 'finance-prod-sapAccountsV1' cannot be deployed. Reason: Active moratorium window from '2022-03-28T00:00:00' to '2022-04-04T00:00:00'.
```