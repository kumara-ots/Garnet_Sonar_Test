package com.fuso.enterprise.ots.srv.server.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fuso.enterprise.ots.srv.api.model.domain.DistributorCompanyDetails;
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

public class DistributorRegisterInvoicePdf {
	
	private static final Logger logger = LoggerFactory.getLogger(DistributorRegisterInvoicePdf.class);
	
	//To Generate PDf & file will not be save in local system.
	public static byte[] generateRegistrationInvoiceCopy(List<DistributorCompanyDetails> distDetails,String registrationPaymentId){
    	String distributorId = distDetails.get(0).getDistributorId();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream); // In-memory writer

            document.open();

            invoiceTitle(document);
			invoiceDetails(document,distributorId);
			getInvoiceBillingDeatils(document,distDetails,registrationPaymentId);
			addThankYouNoteAtTheEndOfPage(document,writer);
			//Close document and outputStream.
			document.close();
			
			System.out.println("Pdf created successfully.");

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

 	    return "INV-" + datePart + "-" + randomNumber; // Combine all parts
 	}

	private static void invoiceDetails(Document document,String distributorId) throws DocumentException {
		Paragraph lineAdjust = new Paragraph("\n\n");
		lineAdjust.setLeading(11f);
		document.add(lineAdjust);
		
		String invoiceNumber = generateInvoiceNumber();
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String now = LocalDateTime.now().format(dtf);
		Font font = FontFactory.getFont(FontFactory.TIMES,12,BaseColor.BLACK);

		PdfPCell invoiceCell = new PdfPCell();
		invoiceCell.setFixedHeight(20);
		invoiceCell.setBorder(Rectangle.NO_BORDER);
		invoiceCell.setBackgroundColor(new BaseColor(211,211,211));
		invoiceCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		Paragraph invoiceDetails= new Paragraph("Invoice Number: "+invoiceNumber+getSpace(28)+"Distributor Id: "+distributorId+getSpace(28)+"Invoice Date: "+now,font);
		invoiceDetails.setAlignment(Element.ALIGN_LEFT);
		PdfPTable invoiceTable = new PdfPTable(1);
		invoiceCell.setPhrase(invoiceDetails);
		invoiceTable.setWidthPercentage(100);
		invoiceTable.addCell(invoiceCell);
	
		try {
			document.add(invoiceTable);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	private static void getInvoiceBillingDeatils(Document document,List<DistributorCompanyDetails> distDetails,String registrationPaymentId) throws DocumentException {
		document.add(Chunk.NEWLINE);
		Font fontBold = FontFactory.getFont(FontFactory.TIMES_BOLD,11,BaseColor.BLACK);
		Font fontBold1 = FontFactory.getFont(FontFactory.TIMES_BOLD,12,BaseColor.BLACK);
		Font fontLight = FontFactory.getFont(FontFactory.TIMES,11,BaseColor.BLACK);
		
		Paragraph billTo = new Paragraph("BILL TO \n",fontBold);
		
		for(int i=0; i<distDetails.size(); i++) {
			//Adding Bill To details
			billTo.add(new Chunk("Name: "+distDetails.get(i).getDistributorFirstName()+" "+distDetails.get(i).getDistributorLastName()+"\n",fontBold));
			billTo.add(new Chunk("Address: "+distDetails.get(i).getCompanyAddress()+"\n",fontLight));
			billTo.add(new Chunk("Pincode: "+distDetails.get(i).getCompanyPincode()+"\n",fontLight));
			billTo.add(new Chunk("Mobile: "+distDetails.get(i).getCompanyContactNo()+"\n",fontLight));
			billTo.add(new Chunk("Email Id: "+distDetails.get(i).getCompanyEmailId()+"\n",fontLight));
			if(distDetails.get(i).getCompanyTaxNumber() != null) {
				billTo.add(new Chunk("TAX Number: "+distDetails.get(i).getCompanyTaxNumber()+"\n",fontLight));
			}
		}
		document.add(billTo);
	
		document.add(Chunk.NEWLINE);
		Paragraph transaction = new Paragraph("Registration Payment ID: "+registrationPaymentId,fontBold1);
		document.add(transaction);
		document.add(Chunk.NEWLINE);
		addOrderDetails(document);
		getCalc(document);
		document.add(Chunk.NEWLINE);

	}
	
	private static void addOrderDetails(Document document) throws DocumentException {
		Font fontBold = FontFactory.getFont(FontFactory.TIMES_BOLD,12,BaseColor.BLACK);
		Font fontLight = FontFactory.getFont(FontFactory.TIMES,11,BaseColor.BLACK);
		
		PdfPTable table = new PdfPTable(7);
		//to set the table width
		table.setWidthPercentage(100);
        //to set the cell width
		table.setWidths(new int[]{100,400,150,150,100,100,150});

		String[] header = {"Sl.no","Item & Description","HSN/SAC","Rate","CGST","SGST","Amount"};
		for(int i=0;i<header.length;i++) {
			PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(header[i]),fontBold));
			cell.setPaddingBottom(5);
			cell.setBorderWidth(2f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
		}
		
		String[] body = {"1","Registration Charges for eMarket platform (one time charge)","9983","636.00","09.00","09.00","750.00"};
		for(int i=0;i<body.length;i++) {
			PdfPCell cell1 = new PdfPCell(new Phrase(String.valueOf(body[i]),fontLight));
			cell1.setPaddingBottom(5);
			cell1.setBorderWidth(2f);
            cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell1);
		}
		
		//add fetched billing data here....
		try {
			document.add(table);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	private static void addDistributorTable(Document document,List<List<String>> distDetails) throws DocumentException {
		Font fontBold = FontFactory.getFont(FontFactory.TIMES_BOLD,12,BaseColor.BLACK);
		Font fontLight = FontFactory.getFont(FontFactory.TIMES,11,BaseColor.BLACK);
		
		PdfPTable table = new PdfPTable(3);
		//to set the table width
		table.setWidthPercentage(100);
        //to set the cell width
		table.setWidths(new int[]{100,400,400});

		String[] header = {"Sl.no","Item & Description","Sold By"};
		for(int i=0;i<header.length;i++) {
			PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(header[i]),fontBold));
			cell.setPaddingBottom(5);
			cell.setBorderWidth(2f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
		}

		for(int i=0;i<distDetails.size();i++) {
			for(int j=0;j<distDetails.get(i).size();j++) {
				PdfPCell cell1 = new PdfPCell(new Phrase(String.valueOf(distDetails.get(i).get(j))));
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
			e.printStackTrace();
		}
	}
	
	private static void addThankYouNoteAtTheEndOfPage(Document document, PdfWriter writer) {  
		  Font fontLight = FontFactory.getFont(FontFactory.TIMES,11,BaseColor.BLACK);
		  
		  Paragraph thanks = new Paragraph("Thank you for your business \n\n",fontLight);
		  thanks.add(new Chunk("ORTUSOLIS TECHNOLOGIES PVT LTD \n",fontLight));
		  
		  PdfPTable tabella = new PdfPTable(2);
		  PdfPCell cell1 = new PdfPCell(thanks);
		  PdfPCell cell2 = new PdfPCell();
		  cell1.setBorder(Rectangle.NO_BORDER);
		  cell2.setBorder(Rectangle.NO_BORDER);
		  tabella.addCell(cell1);
		  tabella.addCell(cell2);
		  tabella.setWidthPercentage(100);
		
		  //To set the table to bottom of the PDF Pgae
		  tabella.setTotalWidth(document.right() - document.left());
		  tabella.writeSelectedRows(0, -1, document.left(),tabella.getTotalHeight() + document.bottom(), writer.getDirectContent());
	 }

	private static void getTandC(Document document) throws DocumentException {
		
		Font fontBold = FontFactory.getFont(FontFactory.TIMES_BOLD,11,BaseColor.BLACK);
		Font fontLight = FontFactory.getFont(FontFactory.TIMES,11,BaseColor.BLACK);
		Paragraph tnc = new Paragraph("TERMS AND CONDITIONS\n\n",fontBold);
		tnc.add(new Chunk("1. Goods once sold will not be taken back or exchanged\n",fontLight));
		tnc.add(new Chunk("2. All  disputes are subject to Bengaluru, KA-INDIA jurisdiction only \n",fontLight));
		tnc.add(new Chunk("3. Transportation Damages/Scratches will not be responsible\n",fontLight));
		
		Paragraph sig = new Paragraph(" Authorised Signature For\n Polymercrafts Retail Displays",fontLight);
		
		
		PdfPTable header = new PdfPTable(2);
		PdfPCell bc = new PdfPCell(tnc);
		PdfPCell tc = new PdfPCell(sig);
		bc.setBorder(Rectangle.NO_BORDER);
		tc.setBorder(Rectangle.NO_BORDER);
		header.setWidthPercentage(100);
		header.setWidths(new int[] {100,50});
		header.addCell(bc);	
		header.addCell(tc);	
		document.add(header);
	}

	private static void getBankDetails(Document document) throws DocumentException {
		Font fontBold = FontFactory.getFont(FontFactory.TIMES_BOLD,11,BaseColor.BLACK);
		Font fontLight = FontFactory.getFont(FontFactory.TIMES,11,BaseColor.BLACK);
		Paragraph bankDetails = new Paragraph("Bank Details\n",fontBold);
		bankDetails.add(new Chunk("Account Number:\n",fontLight));
		bankDetails.add(new Chunk("IFSC Code:\n",fontLight));
		bankDetails.add(new Chunk("Bank:\n",fontLight));
		
		Paragraph taxDetails = new Paragraph("Taxable Amount:\n",fontLight);
		taxDetails.add(new Chunk("CGST@15:Rs\n",fontLight));
		taxDetails.add(new Chunk("SGST@15:Rs\n \n",fontLight));
		taxDetails.add(new Chunk("Grand Total :Rs\n",fontBold));
		taxDetails.add(new Chunk("\nRECEIVED AMOUNT : Rs\n\n",fontLight));
		taxDetails.add(new Chunk("Balance : Rs\n\n\n",fontLight));
		taxDetails.add(new Chunk("Total Amount (in Words) :\n",fontLight));
	
		PdfPTable header = new PdfPTable(2);
		PdfPCell bc = new PdfPCell(bankDetails);
		PdfPCell tc = new PdfPCell(taxDetails);
		bc.setBorder(Rectangle.NO_BORDER);
		tc.setBorder(Rectangle.NO_BORDER);
		header.setWidthPercentage(100);
		header.setWidths(new int[] {100,50});
		header.addCell(bc);	
		header.addCell(tc);	
		
		//add fetched billing data here....
		try {
			document.add(header);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}

	private static void getCalc(Document document) throws DocumentException {
		Font fontBold = FontFactory.getFont(FontFactory.TIMES_BOLD,11,BaseColor.BLACK);
		
		//To calculate Total order cost from order details
		double totalAmount = 750.00;
		
		//To print Rupee symbol in PDF for itextPdf framework
		BaseFont bf = null;
		try {
			bf = BaseFont.createFont("c:/windows/fonts/arial.ttf",
			                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		} catch (IOException e) {
			// TODO Auto-generated catch block
        	logger.error("Exception while fetching data from DB :"+e.getMessage());
		}
		Font font = new Font(bf, 12);
		Chunk chunkRupee = new Chunk(" \u20B9"+totalAmount, font);
		System.out.println("rupee "+chunkRupee);
		
		PdfPCell empty = new PdfPCell();
		PdfPCell text = new PdfPCell(new Phrase("Sub-Total",fontBold));
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
		
		//To convert total cost from Number into Words
		Paragraph sig = new Paragraph("Total Amount (in Words):  "+EnglishNumberToWords.convert((long)totalAmount)+ " Rupees Only",fontBold);
		document.add(sig);
		
	}

	private static void getInvoiceHeader(Document document) throws DocumentException {
		Font fontBold = FontFactory.getFont(FontFactory.TIMES_BOLD,11,BaseColor.BLACK);
		PdfPTable header = new PdfPTable(8);
		Stream.of("Sl.no","Item & Description","HSN/SAC","QTY","Price","CGST","SGST","Amount").forEach(headerText-> headerCellprops(header,headerText,fontBold));
		//add fetched billing data here....
		document.add(header);
	}

	private static void headerCellprops(PdfPTable header, String headerText, Font fontBold) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(BaseColor.WHITE);
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setPhrase(new Phrase(headerText,fontBold));
		cell.setBorderColor(new BaseColor(255,255,255));
		header.setWidthPercentage(100);
		header.addCell(cell);
	}

	private static void getDividerLine(Document document) throws DocumentException {
		LineSeparator eS = new LineSeparator();
		Paragraph pr = new  Paragraph();
		eS.setLineColor(BaseColor.BLACK);
		eS.setLineWidth(3);
		pr.add(eS);
		document.add(pr);	
	}
	
	private static void invoiceTitle(Document document) throws MalformedURLException, IOException, DocumentException{
        PdfPTable header = new PdfPTable(2);
        try {
        	StringBuilder exSpace = getSpace(22);

    		Font font = FontFactory.getFont(FontFactory.TIMES_BOLD,18,BaseColor.BLACK);
    		Font font3 = FontFactory.getFont(FontFactory.TIMES_BOLD,12,BaseColor.BLACK);
    		Font font2 = FontFactory.getFont(FontFactory.TIMES,12,BaseColor.BLACK);
    		Font font4 = FontFactory.getFont(FontFactory.TIMES,12,BaseColor.DARK_GRAY);
    		
        	Paragraph headerTax = new Paragraph("Tax Invoice ",font3);
    		headerTax.setLeading(12f);
    		headerTax.add(new Chunk("Original For Recipient \n \n",font4));
    		headerTax.setAlignment(Element.ALIGN_RIGHT);
    		
    		// set defaults
    		header.setWidthPercentage(100);
    		header.setSpacingBefore(0f);
    		header.setSpacingAfter(0f);
    		float[] columnWidths = { 1f, 2f};
    		header.setWidths(columnWidths);
            header.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
            
            // add image
            Image logo = Image.getInstance(DistributorRegisterInvoicePdf.class.getResource("/OTSPL-Logo.jpg"));
            logo.setScaleToFitHeight(false);
            logo.scaleAbsolute(1500, 1000);
            System.out.println(logo.getHeight());
            System.out.println(logo.getWidth());
            
            header.addCell(logo);

            // add text
            PdfPCell text = new PdfPCell();
//            text.setPaddingBottom(15);
//            text.setPaddingLeft(10);
            text.setBorder(Rectangle.NO_BORDER);
            text.setBorderColor(BaseColor.LIGHT_GRAY);
            Paragraph compName = new Paragraph("ORTUSOLIS TECHNOLOGIES PVT LTD\n",font);
            compName.setAlignment(Element.ALIGN_RIGHT);
            Paragraph title = new Paragraph("63/2,Akshaya,2nd floor,18th cross Malleswaram,Bengaluru,Karnataka \n\n"+" Mobile: +91-9482199513"+exSpace+"GSTIN: 29AAFFO4147D1ZB \n",font2);
            title.setAlignment(Element.ALIGN_RIGHT);
            text.addElement(compName);
            text.addElement(title);
            header.addCell(text);

            LineSeparator eS = new LineSeparator();
    		Paragraph pr = new  Paragraph();
    		eS.setLineColor(BaseColor.BLACK);
    		eS.setLineWidth(10);
    		pr.add(eS);
    		
            try {
    			document.add(headerTax);
    			document.add(header);
    			document.add(pr);
    		} catch (DocumentException e) {
    			e.printStackTrace();
    		}

        } catch(DocumentException de) {
            throw new ExceptionConverter(de);
        } catch (MalformedURLException e) {
            throw new ExceptionConverter(e);
        } catch (IOException e) {
            throw new ExceptionConverter(e);
        }
    }

	private static StringBuilder getSpace(int space) {
		StringBuilder spaceBuilder = new StringBuilder();
		for(int i=0;i<space;i++) {
			spaceBuilder.append(" ");
		}
		return spaceBuilder;
	}
}

