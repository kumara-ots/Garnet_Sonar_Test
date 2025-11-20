package com.fuso.enterprise.ots.srv.server.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fuso.enterprise.ots.srv.api.model.domain.ProductManufacturerDetails;
import com.fuso.enterprise.ots.srv.api.service.request.AddProductManufacturerRequest;
import com.fuso.enterprise.ots.srv.common.exception.BusinessException;
import com.fuso.enterprise.ots.srv.server.dao.ProductManufacturerDAO;
import com.fuso.enterprise.ots.srv.server.model.entity.OtsProductManufacturer;
import com.fuso.enterprise.ots.srv.server.util.AbstractIptDao;

@Repository
public class ProductManufacturerDAOImpl extends AbstractIptDao<OtsProductManufacturer, String> implements ProductManufacturerDAO {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public ProductManufacturerDAOImpl() {
		super(OtsProductManufacturer.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String addOrUpdateProductManufacturerDetails (AddProductManufacturerRequest addProductManufacturerRequest) {
		try {
			//To Add New Manufacturer Name
			if (addProductManufacturerRequest.getRequest().getManufacturerId() == null || addProductManufacturerRequest.getRequest().getManufacturerId().isEmpty()) {	

				OtsProductManufacturer manufacturer = new OtsProductManufacturer();
				manufacturer.setOtsProductManufacturerName(addProductManufacturerRequest.getRequest().getManufacturerName());
				save(manufacturer);	
				
				return "Inserted Successfully";
			
			}else {
				// To Update Existing Manufacturer Name
				OtsProductManufacturer manufacturer = new OtsProductManufacturer();
				Map<String, Object> queryParameter = new HashMap<>();
				queryParameter.put("otsProductManufacturerId",(Integer.valueOf(addProductManufacturerRequest.getRequest().getManufacturerId())));
	
				try {
					manufacturer = super.getResultByNamedQuery("OtsProductManufacturer.findById", queryParameter);
				} catch (NoResultException e) {
					return null;
				}
				manufacturer.setOtsProductManufacturerName(addProductManufacturerRequest.getRequest().getManufacturerName());
				
				save(manufacturer); 
				return "Updated Successfully";
			}
		}catch(Exception e){
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while Inserting data to DB  :"+e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
		
	@Override
	public String deleteManufacturerDetails(String productManufacturerId) {
		try {
			OtsProductManufacturer manufacturer = new OtsProductManufacturer();
			
			Map<String, Object> queryParameter = new HashMap<>();		
			queryParameter.put("otsProductManufacturerId",Integer.valueOf(productManufacturerId));	
			try {
				manufacturer = super.getResultByNamedQuery("OtsProductManufacturer.findById", queryParameter);
			}catch(NoResultException e) {
				return null;
			}
			super.getEntityManager().remove(manufacturer);
		}catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
		return "Deleted";
	}
	
	@Override
	public List<ProductManufacturerDetails> getAllManufacturerDetails() {
		try {
			Map<String, Object> queryParameter = new HashMap<>();
			List<OtsProductManufacturer> manufacturers = super.getResultListByNamedQuery("OtsProductManufacturer.findAll", queryParameter);
			List<ProductManufacturerDetails> manufacturerList = manufacturers.stream().map(manufacturerData -> convertManufacturerDetailsFromEntityToDomain(manufacturerData)).collect(Collectors.toList());
			
			return manufacturerList;
		} catch (Exception e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		} catch (Throwable e) {
			logger.error("Exception while fetching data from DB :" + e.getMessage());
			throw new BusinessException(e.getMessage(), e);
		}
	}
	
	private ProductManufacturerDetails convertManufacturerDetailsFromEntityToDomain(OtsProductManufacturer otsProductManufacturer) {
		
		ProductManufacturerDetails manufacturerDetails = new ProductManufacturerDetails();
		manufacturerDetails.setManufacturerId((otsProductManufacturer.getOtsProductManufacturerId()== null) ? null: otsProductManufacturer.getOtsProductManufacturerId().toString());
		manufacturerDetails.setManufacturerName(((otsProductManufacturer.getOtsProductManufacturerName()== null) ? null: otsProductManufacturer.getOtsProductManufacturerName()));
	
		return manufacturerDetails;
	}
}
