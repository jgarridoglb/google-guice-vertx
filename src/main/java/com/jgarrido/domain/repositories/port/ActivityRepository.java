package com.jgarrido.domain.repositories.port;

import java.util.List;

import com.jgarrido.domain.entities.Activity;
import com.jgarrido.infraestructure.callback.Callback;


public interface ActivityRepository {

	void findAll(Callback<List<Activity>> callback);
}
