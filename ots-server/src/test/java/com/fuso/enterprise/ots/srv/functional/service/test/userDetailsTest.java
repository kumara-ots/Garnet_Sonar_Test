package com.fuso.enterprise.ots.srv.functional.service.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.fuso.enterprise.ots.srv.api.model.domain.UserDetails;
import com.fuso.enterprise.ots.srv.api.service.response.UserDataBOResponse;
import com.fuso.enterprise.ots.srv.functional.service.OTSUserServiceImpl;
import com.fuso.enterprise.ots.srv.server.dao.impl.UserServiceDAOImpl;

@RunWith(MockitoJUnitRunner.class)
public class userDetailsTest {
	
	@InjectMocks
	OTSUserServiceImpl otsUserServiceImpl;
	
	@Mock
	UserServiceDAOImpl userServiceDaoImpl;
	
	@Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void getUserIdUsersTest()
	{
    	UserDetails userDetail = new UserDetails();
    	userDetail.setFirstName("Vidhya");
    	userDetail.setUserId("1");
    	userDetail.setEmailId("vidhya@gmail.com");
    	
    	UserDataBOResponse userDataBOResponse = new UserDataBOResponse();
    	 // Mocking getUserIdUsers with specifix data
    	 
    	when(userServiceDaoImpl.getUserIdUsers("1")).thenReturn(userDetail);
    	
    	// calling the service class 
    	userDataBOResponse = otsUserServiceImpl.getUserIDUsers("1");
    	assertEquals("Vidhya", userDataBOResponse.getUserDetails().get(0).getFirstName());
    	assertNotEquals("Sri", userDataBOResponse.getUserDetails().get(0).getFirstName());
	}

}
