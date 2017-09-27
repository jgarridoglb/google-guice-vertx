package com.jgarrido.infraestructure.ui.port;

import java.util.List;

import com.jgarrido.application.service.port.ListActivitiesOutputBoundary;
import com.jgarrido.domain.model.ActivityDetails;

import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.templ.FreeMarkerTemplateEngine;

public class ListActivitiesView implements ListActivitiesOutputBoundary {
    private final RoutingContext ctx;

    public ListActivitiesView(RoutingContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void success(List<ActivityDetails> activityDetailsList) {
        FreeMarkerTemplateEngine engine = FreeMarkerTemplateEngine.create();

        ctx.put("activities", activityDetailsList);

        engine.render(ctx, "templates/index.ftl", res -> {
            if (res.succeeded()) {
                ctx.response().end(res.result());
            } else {
                ctx.fail(res.cause());
            }
        });
    }

    @Override
    public void failure(Throwable throwable) {
        ctx.fail(throwable);
    }
}
