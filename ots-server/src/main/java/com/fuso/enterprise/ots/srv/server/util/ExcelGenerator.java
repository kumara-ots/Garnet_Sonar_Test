package com.fuso.enterprise.ots.srv.server.util;

import java.io.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fuso.enterprise.ots.srv.api.model.domain.DistributorDetailsForSettlement;
import com.fuso.enterprise.ots.srv.api.service.request.GetDetailsForExcelRequest;

public class ExcelGenerator {
	
	private static final Logger logger = LoggerFactory.getLogger(ExcelGenerator.class);
	
	public static byte[] generateExcel(GetDetailsForExcelRequest getDetailsForExcelRequest, DistributorDetailsForSettlement distributorDetailsForSettlement) {
        byte[] bytePath = null;
        try {
        	// Declare file name to be create   
            String pdfPath = "WKST_" + distributorDetailsForSettlement.getDistributorPaymentDetails().get(0).getPaymentDetailsDistributorId() + "_" + distributorDetailsForSettlement.getFromToDate() + ".xlsx";
            System.out.println("File name = " + pdfPath);

            // Creating an instance of HSSFWorkbook class  
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("January");

            // Create a CellStyle and set the font to the bold font
            CellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setFontHeightInPoints((short) 10);
            font.setFontName("Calibri");
            font.setBold(true);
            style.setFont(font);

            // Creating the 0th row using the createRow() method  
            Row rowTitle = sheet.createRow(0);
            Cell cell = rowTitle.createCell(4);
            cell.setCellValue(distributorDetailsForSettlement.getExcelTitle());
            cell.setCellStyle(style);
            sheet.addMergedRegion(CellRangeAddress.valueOf("E1:M1"));	 //To merge cell from range
            CellUtil.setAlignment(cell, HorizontalAlignment.CENTER);	// Sets the alignment to the created cell

            // Inserting data from List to Array
            String[] distValue1 = new String[4];
            distValue1[0] = distributorDetailsForSettlement.getDistributorPaymentDetails().get(0).getPaymentDetailsDistributorId();
            distValue1[1] = distributorDetailsForSettlement.getDistributorPaymentDetails().get(0).getPaymentDetailsName();
            distValue1[2] = distributorDetailsForSettlement.getDistributorPaymentDetails().get(0).getPaymentDetailsAccountNumber();
            distValue1[3] = distributorDetailsForSettlement.getDistributorPaymentDetails().get(0).getPaymentDetailsIfscCode();

            String[] distValue2 = new String[4];
            distValue2[0] = distributorDetailsForSettlement.getDistributorPaymentDetails().get(0).getPaymentDetailsBankName();
            distValue2[1] = distributorDetailsForSettlement.getDistributorPaymentDetails().get(0).getPaymentDetailsBranchName();
            distValue2[2] = distributorDetailsForSettlement.getDistributorPaymentDetails().get(0).getPaymentDetailsPanNumber();
            distValue2[3] = distributorDetailsForSettlement.getDistributorPaymentDetails().get(0).getPaymentDetailsAadhaarNumber();

            // Creating headers in form of Array
            String[] distHeaders = {"Distributor ID", "Distributor Name", "Distributor Account No", "Distributor IFSC No"};
            String[] distHeaders2 = {"Distributor Bank Name", "Bank Branch Name", "Distributor PAN No", "Distributor Aadhaar No"};

            // To insert data's row by row for distributor details
            for (int rn = 0; rn < distHeaders.length; rn++) {
            	// To create multiple Rows 
                Row rowHeader = sheet.createRow(rn + 2);
                rowHeader.createCell(0).setCellValue(distHeaders[rn]);
                Cell cell2 = rowHeader.createCell(2);
                cell2.setCellValue(distValue1[rn]);
                cell2.setCellStyle(style);

                rowHeader.createCell(6).setCellValue(distHeaders2[rn]);
                Cell cell4 = rowHeader.createCell(8);
                cell4.setCellValue(distValue2[rn]);
                cell4.setCellStyle(style);

                // Merging required cells at required range
                int rowNum = rn + 3;
                sheet.addMergedRegion(CellRangeAddress.valueOf("A" + rowNum + ":B" + rowNum));
                sheet.addMergedRegion(CellRangeAddress.valueOf("C" + rowNum + ":D" + rowNum));
                sheet.addMergedRegion(CellRangeAddress.valueOf("G" + rowNum + ":H" + rowNum));
                sheet.addMergedRegion(CellRangeAddress.valueOf("I" + rowNum + ":J" + rowNum));
            }

            // To Insert Data for UnRegistered & Registered Distributor based on condition
            if (getDetailsForExcelRequest.getRegisteredDist() == null) {
                getUnregisteredDistSettlement(getDetailsForExcelRequest, workbook, sheet);
            } else {
                getRegisteredDistSettlement(getDetailsForExcelRequest, workbook, sheet);
            }

            // To convert Excel file into Byte Array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                workbook.write(bos);
            } finally {
                bos.close();
            }
            bytePath = bos.toByteArray();

            System.out.println("excel byte = " + bytePath);
            System.out.println("Excel file has been generated successfully.");
        } catch (Exception e) {
        	logger.error("Exception from ExcelGenerator class :" + e.getMessage());
        }
        return bytePath;
    }

	//Method to Add Settlement Data for UnRegistered Distributor
    private static void getUnregisteredDistSettlement(GetDetailsForExcelRequest getDetailsForExcelRequest, XSSFWorkbook workbook, XSSFSheet sheet) {
    	// Create a font and set it to be bold
    	XSSFFont boldFont = workbook.createFont();
        boldFont.setBold(true);

        // Create a CellStyle and set the font to the bold font
        CellStyle boldStyle = workbook.createCellStyle();
        boldStyle.setFont(boldFont);
        
        // Creating single row for Header
        XSSFRow rowhead = sheet.createRow(8);
        String[] headers = {"Sl.No.", "Order Number", "SubOrder Number", "Distributor Name", "Product Name", "Ordered Qty",
                            "Order Delivered Date", "Order Cost", "Seller Price", "Product Price", "18% on PP",
                            "LLP payable", "3% Profit Pvt Ltd", "1% TDS", "Pvt Ltd Payable", "Distributor Payable"};
        
        // Creating Multiple Cells & Inserting Header Values
        for (int i = 0; i < headers.length; i++) {
            // Create the cell first
            XSSFCell cell = rowhead.createCell(i);
            // Set the cell value
            cell.setCellValue(headers[i]);
            // Apply the bold style to the cell
            cell.setCellStyle(boldStyle);
        }

        // Creating multiple rows & column with data
        for (int i = 0; i < getDetailsForExcelRequest.getUnregisteredDist().size(); i++) {
            XSSFRow row = sheet.createRow(i + 9);
            row.createCell(0).setCellValue(getDetailsForExcelRequest.getUnregisteredDist().get(i).getSlno());
            row.createCell(1).setCellValue(getDetailsForExcelRequest.getUnregisteredDist().get(i).getOrderNumber());
            row.createCell(2).setCellValue(getDetailsForExcelRequest.getUnregisteredDist().get(i).getSubOrderNumber());
            row.createCell(3).setCellValue(getDetailsForExcelRequest.getUnregisteredDist().get(i).getDistributorName());
            row.createCell(4).setCellValue(getDetailsForExcelRequest.getUnregisteredDist().get(i).getProductName());
            row.createCell(5).setCellValue(getDetailsForExcelRequest.getUnregisteredDist().get(i).getOrderedQty());
            row.createCell(6).setCellValue(getDetailsForExcelRequest.getUnregisteredDist().get(i).getOrderDeliveredDate());
            row.createCell(7).setCellValue(getDetailsForExcelRequest.getUnregisteredDist().get(i).getOrderProductPrice());
            row.createCell(8).setCellValue(getDetailsForExcelRequest.getUnregisteredDist().get(i).getSellerPrice());
            row.createCell(9).setCellValue(getDetailsForExcelRequest.getUnregisteredDist().get(i).getProductPrice());
            row.createCell(10).setCellValue(getDetailsForExcelRequest.getUnregisteredDist().get(i).getEighteenPercGst());
            row.createCell(11).setCellValue(getDetailsForExcelRequest.getUnregisteredDist().get(i).getLlpPayablePrice());
            row.createCell(12).setCellValue(getDetailsForExcelRequest.getUnregisteredDist().get(i).getThreePercProfit());
            row.createCell(13).setCellValue(getDetailsForExcelRequest.getUnregisteredDist().get(i).getOnePercTds());
            row.createCell(14).setCellValue(getDetailsForExcelRequest.getUnregisteredDist().get(i).getPvtPayablePrice());
            row.createCell(15).setCellValue(getDetailsForExcelRequest.getUnregisteredDist().get(i).getDistributorPayablePrice());
        }
        
        // Creating single row for Second Header
        XSSFRow rowHeadTotal = sheet.createRow(getDetailsForExcelRequest.getUnregisteredDist().size() + 11);
        String[] totalHeaders = {"Total 18% on PP", "Toatal LLP payable", "Total 3% Profit Pvt Ltd", "Total 1% TDS",
                                 "Total Pvt Ltd Payable", "Total Distributor Payable"};
        
        // Creating Multiple Cells & Inserting Header Values
        for (int i = 0; i < totalHeaders.length; i++) {
        	XSSFCell cell = rowHeadTotal.createCell(10 + i);
            cell.setCellValue(totalHeaders[i]);
            // Apply the bold style to the cell
            cell.setCellStyle(boldStyle);
        }

        // Creating multiple rows & column with total values
        XSSFRow rowTotal = sheet.createRow(getDetailsForExcelRequest.getUnregisteredDist().size()+12);
        rowTotal.createCell(10).setCellValue(getDetailsForExcelRequest.getTotalEighteenPercGstOnProductPrice());  
		rowTotal.createCell(11).setCellValue(getDetailsForExcelRequest.getTotalLLPPayablePrice());  
		rowTotal.createCell(12).setCellValue(getDetailsForExcelRequest.getTotalThreePercProfitPvt());  
		rowTotal.createCell(13).setCellValue(getDetailsForExcelRequest.getTotalOnePercTds());
		rowTotal.createCell(14).setCellValue(getDetailsForExcelRequest.getTotalPvtPayablePrice());  
		rowTotal.createCell(15).setCellValue(getDetailsForExcelRequest.getTotalPendingAmount());

    }

    //Method to Add Settlement Data for Registered Distributor
    private static void getRegisteredDistSettlement(GetDetailsForExcelRequest getDetailsForExcelRequest, XSSFWorkbook workbook, XSSFSheet sheet) {
    	// Create a font and set it to be bold
    	XSSFFont boldFont = workbook.createFont();
        boldFont.setBold(true);

        // Create a CellStyle and set the font to the bold font
        CellStyle boldStyle = workbook.createCellStyle();
        boldStyle.setFont(boldFont);
        
        // Creating single row for Header
        XSSFRow rowhead = sheet.createRow(8);
        String[] headers = {"Sl.No.", "Order Number", "SubOrder Number", "Distributor Name", "Product Name", "Ordered Qty",
                            "Order Delivered Date", "Order Cost", "Seller Price", "Product Price", "10% Transaction",
                            "18% GST on 10% trans", "1% TDS on Product Price", "Pvt Ltd Payable", "Distributor Payable"};
        
        // Creating Multiple Cells & Inserting Header Values
        for (int i = 0; i < headers.length; i++) {
            // Create the cell first
            XSSFCell cell = rowhead.createCell(i);
            // Set the cell value
            cell.setCellValue(headers[i]);
            // Apply the bold style to the cell
            cell.setCellStyle(boldStyle);
        }
        
        // Creating multiple rows & column with data
  		for(int i=0; i<getDetailsForExcelRequest.getRegisteredDist().size(); i++) {
  			XSSFRow row = sheet.createRow(i+9);  
  			//inserting data in the row  
  			row.createCell(0).setCellValue(getDetailsForExcelRequest.getRegisteredDist().get(i).getSlno());  
  			row.createCell(1).setCellValue(getDetailsForExcelRequest.getRegisteredDist().get(i).getOrderNumber());  
  			row.createCell(2).setCellValue(getDetailsForExcelRequest.getRegisteredDist().get(i).getSubOrderNumber());  
  			row.createCell(3).setCellValue(getDetailsForExcelRequest.getRegisteredDist().get(i).getDistributorName());  
  			row.createCell(4).setCellValue(getDetailsForExcelRequest.getRegisteredDist().get(i).getProductName());  
  			row.createCell(5).setCellValue(getDetailsForExcelRequest.getRegisteredDist().get(i).getOrderedQty());  
  			row.createCell(6).setCellValue(getDetailsForExcelRequest.getRegisteredDist().get(i).getOrderDeliveredDate());  
  			row.createCell(7).setCellValue(getDetailsForExcelRequest.getRegisteredDist().get(i).getOrderProductPrice());  
  			row.createCell(8).setCellValue(getDetailsForExcelRequest.getRegisteredDist().get(i).getSellerPrice());  
  			row.createCell(9).setCellValue(getDetailsForExcelRequest.getRegisteredDist().get(i).getProductPrice()); 
  			row.createCell(10).setCellValue(getDetailsForExcelRequest.getRegisteredDist().get(i).getTenPercTranscation());  
  			row.createCell(11).setCellValue(getDetailsForExcelRequest.getRegisteredDist().get(i).getEighteenPercGstOnTenPercTranscation());  
  			row.createCell(12).setCellValue(getDetailsForExcelRequest.getRegisteredDist().get(i).getOnePercTdsOnProductPrice());  
  			row.createCell(13).setCellValue(getDetailsForExcelRequest.getRegisteredDist().get(i).getPvtPayablePrice());  
  			row.createCell(14).setCellValue(getDetailsForExcelRequest.getRegisteredDist().get(i).getDistributorPayablePrice());
  		}

  		// Creating single row for Second Header
        XSSFRow rowHeadTotal = sheet.createRow(getDetailsForExcelRequest.getRegisteredDist().size() + 11);
        String[] totalHeaders = {"Total 10% Transcation on PP", "Toatal 18% GST on 10% Trans", "Total 1% TDS on PP", 
        		"Total Pvt Ltd Payable","Total Distributor Payable"};
        
        // Creating Multiple Cells & Inserting Header Values
        for (int i = 0; i < totalHeaders.length; i++) {
        	// Create the cell first
        	XSSFCell cell = rowHeadTotal.createCell(10 + i);
        	// Set the cell value
            cell.setCellValue(totalHeaders[i]);
            // Apply the bold style to the cell
            cell.setCellStyle(boldStyle);
        }

        // Creating multiple rows & column with total values
        XSSFRow rowTotal = sheet.createRow(getDetailsForExcelRequest.getRegisteredDist().size()+12);
        rowTotal.createCell(10).setCellValue(getDetailsForExcelRequest.getTotalTenPercTrans());  
		rowTotal.createCell(11).setCellValue(getDetailsForExcelRequest.getTotalEighteenPercGstOnTenPercTrans());  
		rowTotal.createCell(12).setCellValue(getDetailsForExcelRequest.getTotalOnePercTdsOnProductPrice());  
		rowTotal.createCell(13).setCellValue(getDetailsForExcelRequest.getTotalPvtPayablePrice());  
		rowTotal.createCell(14).setCellValue(getDetailsForExcelRequest.getTotalPendingAmount());

    }

}
