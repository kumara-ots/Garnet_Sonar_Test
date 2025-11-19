package com.fuso.enterprise.ots.srv.server.dao;

import java.util.List;

import com.fuso.enterprise.ots.srv.api.model.domain.CustomerChangeAddress;
import com.fuso.enterprise.ots.srv.api.service.request.AddCustomerChangeAddressRequest;

public interface CustomerChangeAddressDAO {

	String addCustomerChangeAddress(AddCustomerChangeAddressRequest addCustomerChangeAddressRequest);

	List<CustomerChangeAddress> getCustomerChangeAddressByCustomerId(String customerId);

	List<CustomerChangeAddress> getCustomerChangeAddressById(String customerChangeAddressId);

	String updateCustomerChangeAddress(AddCustomerChangeAddressRequest addCustomerChangeAddressRequest);

//	String deleteCustomerChangeAddress(String customerChangeAddressId);

}
