package com.fuso.enterprise.ots.srv.server.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fuso.enterprise.ots.srv.api.model.domain.OrderDetails;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class ProformaPdf {
	
	private static final Logger logger = LoggerFactory.getLogger(InvoicePdf.class);
 
	//To Generate PDf & file will not be save in local system.
    public static byte[] generateProformaOrderInvoiceCopy(OrderDetails orderDetails, List<List<String>> distDetails,List<List<String>> orderList){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream); // In-memory writer
            document.open();
            
            // To Add invoice title
            proformaInvoiceTitle(document);             
            //To Add basic invoice details like invoice number, order number etc
			proformaInvoiceDetails(document,orderDetails.getOrderNumber());
			// To Add billing details which includes main table values and calculations	
			getProformaInvoiceBillingDeatils(document,orderDetails,orderList,distDetails);
			// To Add final thank-you note at bottom of page
			addProformaThankYouNoteAtTheEndOfPage(document,writer);
			//Close document and outputStream.
			document.close();
			
        } catch (Exception e) {
        	logger.error("Exception while fetching data from DB :"+e.getMessage());
        }
        return byteArrayOutputStream.toByteArray(); // Return byte[]
    }
    
    // ✅ Helper method to generate invoice number in the required format
 	private static String generateInvoiceNumber() {
 	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd"); // Date in YYYYMMDD format
 	    String datePart = LocalDate.now().format(dtf); // Get the current date

 	    SecureRandom secureRandom = new SecureRandom();
 	    int randomNumber = 1000 + secureRandom.nextInt(9000); // Generate a random 4-digit number

 	    return "PROINV-" + datePart + "-" + randomNumber; // Combine all parts
 	}
    
    private static void proformaInvoiceTitle(Document document)
	        throws  IOException, DocumentException {

	    try {
	        // Better minimal margins
	        document.setMargins(36f, 36f, 24f, 36f);

	        StringBuilder exSpace = getSpace(22);

	        // Fonts
	        Font titleFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 18, BaseColor.BLACK);
	        Font subtitleFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 12, BaseColor.BLACK);
	        Font normalFont = FontFactory.getFont(FontFactory.TIMES, 12, BaseColor.BLACK);
	        Font grayFont = FontFactory.getFont(FontFactory.TIMES, 11, BaseColor.DARK_GRAY);

	        // --- Proforma Invoice Title ---
	        Paragraph headerTax = new Paragraph("Proforma Invoice ", subtitleFont);
	        headerTax.add(new Chunk("Original For Recipient", grayFont));
	        headerTax.setAlignment(Element.ALIGN_RIGHT);
	        headerTax.setSpacingBefore(0f);
	        headerTax.setSpacingAfter(8f);
	        document.add(headerTax);

	        // --- Header Table (Logo + Company Info) ---
	        PdfPTable header = new PdfPTable(2);
	        header.setWidthPercentage(100);
	        header.setWidths(new float[]{1.2f, 4f});  // better proportion
	        header.getDefaultCell().setBorder(Rectangle.NO_BORDER);

	        // --- Logo ---
	        Image logo = Image.getInstance(InvoicePdf.class.getResource("/Garnet.png"));

	        // Perfect scaling without distortion
	        logo.scaleToFit(110, 110);     // 🔥 Increase size (adjust if needed)
	        logo.setSpacingBefore(0f);
	        logo.setSpacingAfter(0f);

	        PdfPCell logoCell = new PdfPCell(logo, false); // "false" = no resize distortion
	        logoCell.setBorder(Rectangle.NO_BORDER);
	        logoCell.setPadding(0);         // 🔥 remove white space completely
	        logoCell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

	        header.addCell(logoCell);

	        // --- Company Info ---
	        PdfPCell infoCell = new PdfPCell();
	        infoCell.setBorder(Rectangle.NO_BORDER);
	        infoCell.setPadding(0);
	        infoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

	        Paragraph compName = new Paragraph("GARNET TECHNOLOGIES\n", titleFont);
	        compName.setSpacingAfter(2f);

	        Paragraph address = new Paragraph(
	                "P.O. Box: 40249, Doha, Qatar, The Commercial Avenue Street No. 964,\n" +
	                "Industrial Area Road, Area 56, Umm Al Seneem\n\n" +
	                "Mobile: +91-9482199513" + exSpace + "TAX Number: 29AAFFO4147D1ZB",
	                normalFont);

	        infoCell.addElement(compName);
	        infoCell.addElement(address);

	        header.addCell(infoCell);

	        document.add(header);

	        // --- Separator Line ---
	        LineSeparator line = new LineSeparator();
	        line.setLineColor(BaseColor.GRAY);
	        line.setLineWidth(0.6f);
	        document.add(new Chunk(line));

	        document.add(Chunk.NEWLINE);

	    } catch (DocumentException | IOException e) {
	        throw new ExceptionConverter(e);
	    }
	}

	private static void proformaInvoiceDetails(Document document, String orderNumber) throws DocumentException {
	
	    // Add spacing before the invoice details section
	    Paragraph lineAdjust = new Paragraph("\n\n");
	    lineAdjust.setLeading(11f);
	    document.add(lineAdjust);
	
	    // Generate invoice number and current date
	    String invoiceNumber = generateInvoiceNumber();
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	    String now = LocalDateTime.now().format(dtf);
	
	    Font font = FontFactory.getFont(FontFactory.TIMES, 12, BaseColor.BLACK);
	
	    // Table with 2 columns for Invoice Number and Date
	    PdfPTable invoiceTable = new PdfPTable(2);
	    invoiceTable.setWidthPercentage(100);
	
	    // Left cell → Proforma invoice number
	    PdfPCell invoiceNumberCell = new PdfPCell();
	    invoiceNumberCell.setFixedHeight(20);
	    invoiceNumberCell.setBorder(Rectangle.NO_BORDER);
	    invoiceNumberCell.setBackgroundColor(new BaseColor(211, 211, 211));
	    invoiceNumberCell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    invoiceNumberCell.setPhrase(new Phrase("PI Number: " + invoiceNumber, font));
	
	    // Right cell → Proforma invoice date
	    PdfPCell invoiceDateCell = new PdfPCell();
	    invoiceDateCell.setFixedHeight(20);
	    invoiceDateCell.setBorder(Rectangle.NO_BORDER);
	    invoiceDateCell.setBackgroundColor(new BaseColor(211, 211, 211));
	    invoiceDateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    invoiceDateCell.setPhrase(new Phrase("PI Date: " + now, font));
	
	    invoiceTable.addCell(invoiceNumberCell);
	    invoiceTable.addCell(invoiceDateCell);
	
	    // Row 2 → Order Number on left, empty cell on right for alignment
	    PdfPCell orderNumberCell = new PdfPCell();
	    orderNumberCell.setFixedHeight(20);
	    orderNumberCell.setBorder(Rectangle.NO_BORDER);
	    orderNumberCell.setBackgroundColor(new BaseColor(211, 211, 211));
	    orderNumberCell.setHorizontalAlignment(Element.ALIGN_LEFT);
	    orderNumberCell.setPhrase(new Phrase("Order Number: " + orderNumber, font));
	
	    PdfPCell emptyCell = new PdfPCell();
	    emptyCell.setFixedHeight(20);
	    emptyCell.setBorder(Rectangle.NO_BORDER);
	    emptyCell.setBackgroundColor(new BaseColor(211, 211, 211));
	
	    invoiceTable.addCell(orderNumberCell);
	    invoiceTable.addCell(emptyCell);  // Used only for layout purposes
	
	    // Add constructed table to the document
	    try {
	        document.add(invoiceTable);
	    } catch (DocumentException e) {
	        e.printStackTrace();
	    }
	}

	private static void getProformaInvoiceBillingDeatils(
	        Document document,
	        OrderDetails orderDetails,
	        List<List<String>> orderList,
	        List<List<String>> distDetails) throws DocumentException {
	
	    // Spacing before the billing section
	    document.add(Chunk.NEWLINE);
	
	    Font fontBold = FontFactory.getFont(FontFactory.TIMES_BOLD, 11, BaseColor.BLACK);
	    Font fontLight = FontFactory.getFont(FontFactory.TIMES, 11, BaseColor.BLACK);
	
	    // Creating the "Bill To" and "Ship To" sections
	    Paragraph billTo = new Paragraph("BILL TO \n\n", fontBold);
	    Paragraph shipTo = new Paragraph("SHIP TO \n\n", fontBold);
	
	    // Populate customer billing & shipping information
        // BILL TO information
        billTo.add(new Chunk("Name: " + orderDetails.getCustomerName() + "\n\n", fontBold));
        billTo.add(new Chunk("Address: " + orderDetails.getDeliveryAddress() + "\n\n", fontLight));
        billTo.add(new Chunk("Mobile: " + orderDetails.getCustomerContactNo() + "\n\n", fontLight));

        // SHIP TO information (same as BILL TO in this layout)
        shipTo.add(new Chunk("Name: " + orderDetails.getCustomerName() + "\n\n", fontBold));
        shipTo.add(new Chunk("Address: " + orderDetails.getDeliveryAddress() + "\n\n", fontLight));
        shipTo.add(new Chunk("Mobile: " + orderDetails.getCustomerContactNo() + "\n\n", fontLight));

	    // Create a table layout for Bill To and Ship To sections
	    PdfPTable header = new PdfPTable(2);
	    PdfPCell bc = new PdfPCell(billTo);
	    PdfPCell tc = new PdfPCell(shipTo);
	
	    bc.setBorder(Rectangle.NO_BORDER);
	    tc.setBorder(Rectangle.NO_BORDER);
	
	    header.setWidthPercentage(100);
	    header.setWidths(new int[] {50, 50}); // Split equally into 2 columns
	    header.addCell(bc);
	    header.addCell(tc);
	    document.add(header);
	
	    document.add(Chunk.NEWLINE);
	
	    // To Add main Table values
	    addOrderDetails(document, orderList);
	
	    // To calculate total price 
	    getCalc(document, orderList);
	    document.add(Chunk.NEWLINE);
	
	    //To Add distributor payment details
	    addDistributorProformaPaymentDetails(document, distDetails);
	
	    document.add(Chunk.NEWLINE);
	}
	
	private static void addProformaThankYouNoteAtTheEndOfPage(Document document, PdfWriter writer) {

	    Font fontLight = FontFactory.getFont(FontFactory.TIMES, 11, BaseColor.BLACK);
	
	    // Thank-you text shown at bottom of invoice
	    Paragraph thanks = new Paragraph("Thank you for your business \n\n", fontLight);
	    thanks.add(new Chunk("Garnet TECHNOLOGIES \n", fontLight));
	
	    // Table layout to position the thank-you message (left aligned)
	    PdfPTable tabella = new PdfPTable(2);
	    PdfPCell cell1 = new PdfPCell(thanks);
	    PdfPCell cell2 = new PdfPCell();
	
	    // Remove borders for clean appearance
	    cell1.setBorder(Rectangle.NO_BORDER);
	    cell2.setBorder(Rectangle.NO_BORDER);
	
	    tabella.addCell(cell1);
	    tabella.addCell(cell2);
	    tabella.setWidthPercentage(100);
	
	    // Position the table at the bottom of the PDF page
	    tabella.setTotalWidth(document.right() - document.left());
	
	    // Write table at exact X,Y coordinates (bottom alignment)
	    tabella.writeSelectedRows(
	            0, -1,
	            document.left(),
	            tabella.getTotalHeight() + document.bottom(),
	            writer.getDirectContent()
	    );
	}
	
	private static void addOrderDetails(Document document,List<List<String>> orderDetails) throws DocumentException {
		Font fontBold = FontFactory.getFont(FontFactory.TIMES_BOLD,12,BaseColor.BLACK);
		
		PdfPTable table = new PdfPTable(8);
		//to set the table width
		table.setWidthPercentage(100);
        //to set the cell width
		table.setWidths(new int[]{100,400,200,100,150,150,150,150});

		String[] header = {"Sl.no","Item & Description","Product Code","Qty","Currency","Price","Tax Price","Amount"};
		for(int i=0;i<header.length;i++) {
			PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(header[i]),fontBold));
			cell.setPaddingBottom(5);
			cell.setBorderWidth(2f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
		}

		for(int i=0;i<orderDetails.size();i++) {
			for(int j=0;j<orderDetails.get(i).size();j++) {
				PdfPCell cell1 = new PdfPCell(new Phrase(String.valueOf(orderDetails.get(i).get(j))));
				cell1.setPaddingBottom(5);
				cell1.setBorderWidth(2f);
	            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
	            table.addCell(cell1);

			}
		}	
		//add fetched billing data here....
		try {
			document.add(table);
		} catch (DocumentException e) {
        	logger.error("Exception while fetching data from DB :"+e.getMessage());
		}
	}
	
	private static void getCalc(Document document,List<List<String>> orderDetails) throws DocumentException {
		Font fontBold = FontFactory.getFont(FontFactory.TIMES_BOLD,11,BaseColor.BLACK);
		
		//To calculate Total order cost from order details
		double sum = 0;
		DecimalFormat decfor = new DecimalFormat("0.00");  //To format decimal number to 2 digits "decfor.format(sum)"
		for(int i=0;i<orderDetails.size();i++)
		{
			sum = sum + Double.parseDouble(String.valueOf((orderDetails.get(i).get(orderDetails.get(i).size()-1))));
		}
		 
		//To print Rupee symbol in PDF for itextPdf framework
		BaseFont bf = null;
		try {
			bf = BaseFont.createFont("c:/windows/fonts/arial.ttf",
			                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		} catch (IOException e) {
        	logger.error("Exception while fetching data from DB :"+e.getMessage());
		}
		Font font = new Font(bf, 12);
		Chunk chunkRupee = new Chunk(" \u20B9"+decfor.format(sum), font);
		System.out.println("rupee "+chunkRupee);
		
		PdfPCell empty = new PdfPCell();
		PdfPCell text = new PdfPCell(new Phrase("Final-Total",fontBold));
    	PdfPCell subTotal = new PdfPCell(new Phrase(chunkRupee));
		
		PdfPTable header = new PdfPTable(3);
		text.setBorder(Rectangle.NO_BORDER);
		text.setBorderColor(BaseColor.BLACK);
		text.setHorizontalAlignment(Element.ALIGN_RIGHT);
		empty.setBorder(Rectangle.NO_BORDER);
		empty.setBorderColor(BaseColor.BLACK);
		subTotal.setBorder(Rectangle.NO_BORDER);
		subTotal.setHorizontalAlignment(Element.ALIGN_RIGHT);
		header.setWidthPercentage(100);
		header.setWidths(new int[]{800,400,250});
		header.addCell(empty);
		header.addCell(text);	
		header.addCell(subTotal);	
		document.add(header);
		document.add(Chunk.NEWLINE);	
	}
	
	private static void addDistributorProformaPaymentDetails(Document document, List<List<String>> distDetails)
	        throws DocumentException {

	    Font fontBold = FontFactory.getFont(FontFactory.TIMES_BOLD, 12, BaseColor.BLACK);
	    Font fontLight = FontFactory.getFont(FontFactory.TIMES, 11, BaseColor.BLACK);

	    Paragraph title = new Paragraph("Seller Payment Details\n\n", fontBold);
	    document.add(title);

	    PdfPTable table = new PdfPTable(2);
	    table.setWidthPercentage(100);
	    table.setWidths(new int[]{200, 500});

	    for (int i = 0; i < distDetails.size(); i++) {
	        List<String> row = distDetails.get(i);

	        String paymentName = (row.size() > 1) ? row.get(1) : "";
	        String accountNo = (row.size() > 2) ? row.get(2) : "";
	        String ifscCode = (row.size() > 3) ? row.get(3) : "";
	        String bankName = (row.size() > 4) ? row.get(4) : "";
	        String address = (row.size() > 5) ? row.get(5) : "";

	        PdfPCell leftCell = new PdfPCell();
	        PdfPCell rightCell = new PdfPCell();

	        leftCell.setBorder(Rectangle.NO_BORDER);
	        rightCell.setBorder(Rectangle.NO_BORDER);

	        Paragraph left = new Paragraph("Bank Name:", fontBold);
	        left.add(new Chunk("\nAccount Holder Name:", fontBold));
	        left.add(new Chunk("\nAccount Number:", fontBold));
	        left.add(new Chunk("\nBank Code:", fontBold));
	        left.add(new Chunk("\nBranch Address:", fontBold));

	        Paragraph right = new Paragraph(bankName, fontLight);
	        right.add(new Chunk("\n" + paymentName, fontLight));
	        right.add(new Chunk("\n" + accountNo, fontLight));
	        right.add(new Chunk("\n" + ifscCode, fontLight));
	        right.add(new Chunk("\n" + address, fontLight));

	        leftCell.addElement(left);
	        rightCell.addElement(right);

	        table.addCell(leftCell);
	        table.addCell(rightCell);
	    }

	    document.add(table);
	}

	private static StringBuilder getSpace(int space) {
		StringBuilder spaceBuilder = new StringBuilder();
		for(int i=0;i<space;i++) {
			spaceBuilder.append(" ");
		}
		return spaceBuilder;
	}

}

