package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.ServiceState;

public interface ServiceStateDAO {

	List<ServiceState> getAllStates();

}
