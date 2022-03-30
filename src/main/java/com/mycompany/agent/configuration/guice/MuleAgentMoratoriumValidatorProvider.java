package com.mycompany.agent.configuration.guice;

import com.google.inject.Binder;
import com.mulesoft.agent.configuration.guice.BaseModuleProvider;
import com.mulesoft.agent.services.ArtifactValidator;
import com.mycompany.agent.MuleAgentMoratoriumValidator;

public class MuleAgentMoratoriumValidatorProvider extends BaseModuleProvider {
    @Override
    protected void configureModule(Binder binder) {
        bindNamedSingleton(binder, ArtifactValidator.class, MuleAgentMoratoriumValidator.class);
    }
}