package com.jgarrido.infraestructure.persistence.adapter;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.jgarrido.domain.entities.Activity;
import com.jgarrido.domain.repositories.port.ActivityRepository;
import com.jgarrido.infraestructure.callback.Callback;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;

public class ActivityRepositoryImpl implements ActivityRepository{
    private JDBCClient jdbcClient;

    @Inject
    public ActivityRepositoryImpl(JDBCClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public void findAll(Callback<List<Activity>> callback) {
        getConnection(connection -> connection.query("SELECT * FROM Activities;", asyncRs -> {
            if (asyncRs.succeeded()) {
                List<Activity> activities = asyncRs.result()
                        .getRows()
                        .stream()
                        .map(this::toActivity)
                        .collect(Collectors.toList());

                callback.success(activities);
            } else {
                callback.failure(asyncRs.cause());
            }
        }), callback::failure);
    }

    private void getConnection(Consumer<SQLConnection> sqlConnectionConsumer, Consumer<Throwable> onFailure) {
        jdbcClient.getConnection(asyncConn -> {
            if (asyncConn.succeeded()) {
                SQLConnection connection = asyncConn.result();
                sqlConnectionConsumer.accept(connection);
                connection.close();
            } else {
                onFailure.accept(asyncConn.cause());
            }
        });
    }

    private Activity toActivity(JsonObject row) {
        Activity activity = new Activity();
        activity.setId(row.getString("ID"));
        activity.setName(row.getString("NAME"));
        return activity;
    }
}
