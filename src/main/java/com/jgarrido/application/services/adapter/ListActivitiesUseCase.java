package com.jgarrido.application.services.adapter;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.jgarrido.application.service.port.ListActivitiesInputBoundary;
import com.jgarrido.application.service.port.ListActivitiesOutputBoundary;
import com.jgarrido.domain.entities.Activity;
import com.jgarrido.domain.model.ActivityDetails;
import com.jgarrido.domain.repositories.port.ActivityRepository;
import com.jgarrido.infraestructure.callback.Callback;


public class ListActivitiesUseCase implements ListActivitiesInputBoundary  {
    private final ActivityRepository activityGateway;

    @Inject
    public ListActivitiesUseCase(ActivityRepository activityGateway) {
        this.activityGateway = activityGateway;
    }

    @Override
    public void listActivities(ListActivitiesOutputBoundary presenter) {
        activityGateway.findAll(Callback.of(
                activities -> presenter.success(toResponseModel(activities)),
                presenter::failure));
    }

    private List<ActivityDetails> toResponseModel(List<Activity> activities) {
        return activities
                .stream()
                .map(Activity::getName)
                .map(ActivityDetails::new)
                .collect(Collectors.toList());
    }
}
