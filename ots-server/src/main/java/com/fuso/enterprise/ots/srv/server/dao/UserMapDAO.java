package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.service.request.MapUsersDataBORequest;
import com.fuso.enterprise.ots.srv.api.service.response.MapUsersDataBOResponse;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsUserMapping;

public interface UserMapDAO {

	String mappUser(MapUsersDataBORequest mapUsersDataBORequest);

	String getMappedDistributor(String userId);

	List<OtsUserMapping> getUserForDistributor(String userId);

}
