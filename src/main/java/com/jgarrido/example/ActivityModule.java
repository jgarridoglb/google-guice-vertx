package com.jgarrido.example;

import com.google.inject.AbstractModule;
import com.jgarrido.application.service.port.ListActivitiesInputBoundary;
import com.jgarrido.application.services.adapter.ListActivitiesUseCase;
import com.jgarrido.domain.repositories.port.ActivityRepository;
import com.jgarrido.infraestructure.persistence.adapter.ActivityRepositoryImpl;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;

public class ActivityModule extends AbstractModule {
    private final Vertx vertx;
    private final JsonObject config;

    public ActivityModule(Vertx vertx, JsonObject config) {
        this.vertx = vertx;
        this.config = config;
    }

    @Override
    protected void configure() {
        bind(JDBCClient.class)
                .toInstance(JDBCClient.createShared(vertx, config));
        bind(ActivityRepository.class)
                .to(ActivityRepositoryImpl.class);
        bind(ListActivitiesInputBoundary.class)
                .to(ListActivitiesUseCase.class);
    }
}
