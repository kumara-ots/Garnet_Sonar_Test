package com.fuso.enterprise.ots.srv.server.dao.impl;

import com.fuso.enterprise.ots.srv.server.dao.SellerDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsSeller;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

public class SellerDAOImpl extends AbstractIptDao<OtsSeller, String> implements SellerDAO  {

	public SellerDAOImpl() {
		super(OtsSeller.class);
	}

	
}
