package com.fuso.enterprise.ots.srv.server.dao;
import com.fuso.enterprise.ots.srv.api.model.domain.SubadminValidity;
import com.fuso.enterprise.ots.srv.api.service.request.AddUpdateSubadminValidity;

public interface SubadminValidityDAO {

	String addSubAdminValidity(AddUpdateSubadminValidity addUpdateSubadminValidity);

	String updateSubAdminValidity(AddUpdateSubadminValidity addUpdateSubadminValidity);

	SubadminValidity getSubAdminValidity(String subAdminId);

}