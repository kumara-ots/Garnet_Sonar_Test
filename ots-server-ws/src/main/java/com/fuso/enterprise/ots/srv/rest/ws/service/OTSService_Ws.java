package com.fuso.enterprise.ots.srv.rest.ws.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fuso.enterprise.ots.srv.api.service.request.AddOrUpdateServiceRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddServiceLocationMappingRequest;
import com.fuso.enterprise.ots.srv.api.service.request.AddServiceReviewAndRatingRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServiceCategorySubCategoryRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServiceReviewsAndRatingRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServiceSlotRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServicesByProviderAndStatusRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServicesByProviderPaginationRequest;
import com.fuso.enterprise.ots.srv.api.service.request.GetServicesBySubCategoryAndProviderRequest;
import com.fuso.enterprise.ots.srv.api.service.request.ProviderAndServiceRequest;
import com.fuso.enterprise.ots.srv.api.service.request.UpdateServiceStatusRequestModel;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

//Product related API's
@Validated
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "OTSService_Ws", description = "This service provides the operations for OTS Service")
@Path("service")
@CrossOrigin
public interface OTSService_Ws {

    @POST
	@Path("/addorUpdateService")
	@ApiOperation(value = "addorUpdateService", notes = "adding or updating ServiceDetails", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addorUpdateService(@ApiParam(value = "request", required = true) @NotNull  @Valid AddOrUpdateServiceRequest addUpdateServiceRequest);

	@POST
	@Path("/getServiceDetailsByServiceId")
	@ApiOperation(value = "getServiceDetailsByServiceId", notes = "To get Service Deatils By ServiceID  ", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getServiceDetailsByServiceId(@ApiParam(value = "serviceId", required = true) @NotNull @Valid @QueryParam("serviceId")String serviceId);

	@POST
	@Path("/getServiceCategoryAndSubCategory")
	@ApiOperation(value = "getServiceCategoryAndSubCategory", notes = "Getcategory And Subcategory And  ServiceDetails", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getServiceCategoryAndSubCategory(@ApiParam(value = "request", required = true) @NotNull  @Valid  GetServiceCategorySubCategoryRequest getServiceCategorySubCategoryRequest);
	
	@POST
	@Path("/getSlotsDetailsByServiceId")
	@ApiOperation(value = "getSlotsDetailsByServiceId", notes = "To get Slots Deatils By ServiceID  ", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getSlotsDetailsByServiceId(@ApiParam(value = "serviceId", required = true) @NotNull @Valid @QueryParam("serviceId")String serviceId);

	@POST
	@Path("/getAvailableServiceSlots")
	@ApiOperation(value = "getAvailableServiceSlots", notes = "To Get Available Slots for Service", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getAvailableServiceSlots(@ApiParam(value = "request", required = true) @NotNull  @Valid GetServiceSlotRequest getServiceSlotRequest);

	@POST
    @Path("/getAllServiceDetails")
	@ApiOperation(value = "getAllServiceDetails", notes = "To Get All Service Details", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getAllServiceDetails();
	
	@POST
    @Path("/getAllServicesByProviderId")
	@ApiOperation(value = "getAllServicesByProviderId", notes = "To Get All Services By ProviderId ", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getAllServicesByProviderId(@ApiParam(value = "providerId", required = true) @NotNull @Valid @QueryParam("providerId")String providerId);

	@POST
	@Path("/getAllServiceCategoryAndSubCategory")
	@ApiOperation(value = "getAllServiceCategoryAndSubCategory", notes = "To Get Category, SubCategory & Services By Hierarchy Method", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getAllServiceCategoryAndSubCategory(@ApiParam(value = "serviceLevelId", required = true) @NotNull @Valid @QueryParam("serviceLevelId")String serviceLevelId);
	
	@POST
	@Path("/updateServiceStatus")
	@ApiOperation(value = "updateServiceStatus", notes = "To Update Service Status", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response updateServiceStatus(@ApiParam(value = "serviceId", required = true) @NotNull @Valid UpdateServiceStatusRequestModel updateServiceStatusRequestModel);
	
	@POST
	@Path("/getServicesByProviderAndStatus")
	@ApiOperation(value = "getServicesByProviderAndStatus", notes = "To Get Service By Provider & Status", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getServicesByProviderAndStatus(@ApiParam(value = "request", required = true) @NotNull @Valid GetServicesByProviderAndStatusRequest getServicesByProviderAndStatusRequest );
	
	@POST
	@Path("/addServiceRatingAndReview")
	@ApiOperation(value = "addServiceRatingAndReview", notes = "To Add Service Review And Rating", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addServiceRatingAndReview(@ApiParam(value = "request", required = true) @NotNull @Valid AddServiceReviewAndRatingRequest reviewAndRatingRequest );
	
	@POST
	@Path("/getServiceRatingAndReviewDetailsByOrderId")
	@ApiOperation(value = "getServiceRatingAndReviewDetailsByOrderId", notes = "To Get Service Review And Rating By Order ID", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") }) 
	Response getServiceRatingAndReviewDetailsByOrderId(@ApiParam(value = "request", required = true) @NotNull @Valid GetServiceReviewsAndRatingRequest getServiceReviewsAndRatingRequest );
	
	@POST
	@Path("/getAverageRatingOfService")
	@ApiOperation(value = "getAverageRatingOfService", notes = "To Get Average Rating Of Service", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getAverageRatingOfService(@ApiParam(value = "serviceId", required = true) @NotNull @Valid @QueryParam("serviceId")String serviceId);

	@POST
	@Path("/getServicesBySubCategoryAndProvider")
	@ApiOperation(value = "getServicesBySubCategoryAndProvider", notes = "To Get Service By Subcategory And Provider", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getServicesBySubCategoryAndProvider(@ApiParam(value = "request", required = true) @NotNull @Valid GetServicesBySubCategoryAndProviderRequest getServicesBySubCategoryAndProviderRequest );
	
	@POST
	@Path("/getRecentlyAddedServiceList")
	@ApiOperation(value = "getRecentlyAddedServiceList", notes = "To Get Recently Added Service List", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getRecentlyAddedServiceList(@ApiParam(value = "levelId", required = true) @NotNull @Valid @QueryParam("levelId")String levelId);
	
	@POST
	@Path("/getServicesByProviderPagination")
	@ApiOperation(value = "getServicesByProviderPagination", notes = "To Get Service By Provider Pagination", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getServicesByProviderPagination(@ApiParam(value = "request", required = true) @NotNull @Valid GetServicesByProviderPaginationRequest getServicesByProviderPaginationRequest );
	
	@POST
    @Path("/addServiceLocationMapping")
	@ApiOperation(value = "addServiceLocationMapping", notes = "To map servcies service availability with location", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response addServiceLocationMapping(@ApiParam(value = "request", required = true) @NotNull  @Valid AddServiceLocationMappingRequest addServiceLocationMappingRequest);
	
	@POST
    @Path("/clearAndAddServiceLocationMapping")
	@ApiOperation(value = "clearAndAddServiceLocationMapping", notes = "To delete all the locations mapped to service & later add services service availability with location", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response clearAndAddServiceLocationMapping(@ApiParam(value = "request", required = true) @NotNull  @Valid AddServiceLocationMappingRequest addServiceLocationMappingRequest);

	@POST
    @Path("/clearAndAddProviderLocationMapping")
	@ApiOperation(value = "clearAndAddProviderLocationMapping", notes = "To delete all the locations mapped to provider & later add services service availability with location", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response clearAndAddProviderLocationMapping(@ApiParam(value = "request", required = true) @NotNull  @Valid AddServiceLocationMappingRequest addServiceLocationMappingRequest);
	
	@POST
	@Path("/getServiceableLocationByProviderOnly")
	@ApiOperation(value = "getServiceableLocationByProviderOnly", notes = "To Get Locations By Provider Only", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = 0, message = "SUCCESS") })
	Response getServiceableLocationByProviderOnly(@ApiParam(value = "request", required = true) @NotNull @Valid ProviderAndServiceRequest providerAndServiceRequest);

}
