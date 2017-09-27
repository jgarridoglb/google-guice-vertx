package com.jgarrido.application.service.port;

import java.util.List;

import com.jgarrido.domain.model.ActivityDetails;
import com.jgarrido.infraestructure.callback.Callback;


public interface ListActivitiesOutputBoundary extends Callback<List<ActivityDetails>>{

}
