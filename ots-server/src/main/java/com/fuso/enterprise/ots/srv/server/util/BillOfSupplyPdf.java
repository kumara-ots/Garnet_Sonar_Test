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
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fuso.enterprise.ots.srv.api.model.domain.DistributorCompanyDetails;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class BillOfSupplyPdf {
	
	private static final Logger logger = LoggerFactory.getLogger(BillOfSupplyPdf.class);

	//To Generate PDf & file will not be save in local system.
    public static byte[] generateBillOfSupply(List<DistributorCompanyDetails> distcompanyDetailsList, List<List<String>> orderDetails, String orderNumber) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream); // In-memory writer

            document.open();

            invoiceTitle(document);
            invoiceDetails(document, orderNumber);
            getInvoiceBillingDeatils(document, distcompanyDetailsList, orderDetails);
            addThankYouNoteAtTheEndOfPage(document, writer, distcompanyDetailsList);

            document.close();
            System.out.println("Bill of Supply PDF generated successfully");

        } catch (Exception e) {
        	logger.error("Exception from BillOfSupplyPdf class :" + e.getMessage());
        }
        return byteArrayOutputStream.toByteArray(); // Return byte[]
    }
    
    // ✅ Helper method to generate invoice number in the required format
  	private static String generateBOSNumber() {
  	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd"); // Date in YYYYMMDD format
  	    String datePart = LocalDate.now().format(dtf); // Get the current date

  	    SecureRandom secureRandom = new SecureRandom();
  	    int randomNumber = 1000 + secureRandom.nextInt(9000); // Generate a random 4-digit number

  	    return "BOS-" + datePart + "-" + randomNumber; // Combine all parts
  	}

	private static void invoiceDetails(Document document,String orderNumber) throws DocumentException {
		Paragraph lineAdjust = new Paragraph("\n\n");
		lineAdjust.setLeading(11f);
		document.add(lineAdjust);
		
		String BOSNumber = generateBOSNumber();
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String now = LocalDateTime.now().format(dtf);
		Font font = FontFactory.getFont(FontFactory.TIMES,12,BaseColor.BLACK);

		PdfPCell invoiceCell = new PdfPCell();
		invoiceCell.setFixedHeight(20);
		invoiceCell.setBorder(Rectangle.NO_BORDER);
		invoiceCell.setBackgroundColor(new BaseColor(211,211,211));
		invoiceCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		Paragraph invoiceDetails= new Paragraph("BOS Number: "+BOSNumber+getSpace(28)+"OrderNumber: "+orderNumber+getSpace(28)+"BOS Date: "+now,font);
		invoiceDetails.setAlignment(Element.ALIGN_LEFT);
		PdfPTable invoiceTable = new PdfPTable(1);
		invoiceCell.setPhrase(invoiceDetails);
		invoiceTable.setWidthPercentage(100);
		invoiceTable.addCell(invoiceCell);
	
		try {
			document.add(invoiceTable);
		} catch (DocumentException e) {
			logger.error("Exception from BillOfSupplyPdf class :" + e.getMessage());
		}
	}

	private static void getInvoiceBillingDeatils(Document document,List<DistributorCompanyDetails> distcompanyDetailsList,List<List<String>> orderDetails) throws DocumentException {
		document.add(Chunk.NEWLINE);
		Font fontBold = FontFactory.getFont(FontFactory.TIMES_BOLD,11,BaseColor.BLACK);
		Font fontLight = FontFactory.getFont(FontFactory.TIMES,11,BaseColor.BLACK);
		
		Paragraph billFrom = new Paragraph("BILL FROM \n\n",fontBold);
		//To fetch distributor details 
		for(int i=0; i<distcompanyDetailsList.size(); i++) {
			//Adding Bill To details
			billFrom.add(new Chunk("Name: "+distcompanyDetailsList.get(i).getDistributorFirstName()+" "+distcompanyDetailsList.get(i).getDistributorLastName()+"\n\n",fontBold));
			billFrom.add(new Chunk("Company Name: "+distcompanyDetailsList.get(i).getCompanyName()+"\n\n",fontLight));
			billFrom.add(new Chunk("Address: "+distcompanyDetailsList.get(i).getCompanyAddress()+"\n\n",fontLight));
			billFrom.add(new Chunk("Pincode: "+distcompanyDetailsList.get(i).getCompanyPincode()+"\n\n",fontLight));
			billFrom.add(new Chunk("Mobile: "+distcompanyDetailsList.get(i).getCompanyContactNo()+"\n\n",fontLight));
		}
		
		Paragraph billTo = new Paragraph("BILL TO\n\n",fontBold);
		billTo.add(new Chunk("Ortusolis Technology Solutions LLP\n\n",fontBold));
		billTo.add(new Chunk("93/2, Akshaya, 2nd floor,18th cross Malleswaram,\n\n",fontLight));
		billTo.add(new Chunk("Bengaluru, Karnataka,\n\n",fontLight));	
		billTo.add(new Chunk("GSTIN: 29AAFFO4147D1ZB \n\n",fontLight));	
		billTo.add(new Chunk("Mobile: +91-9482199513  \n\n",fontLight));
		
		PdfPTable header = new PdfPTable(2);
		PdfPCell bc = new PdfPCell(billFrom);
		PdfPCell tc = new PdfPCell(billTo);
		bc.setBorder(Rectangle.NO_BORDER);
//		bc.setBorderColor(new BaseColor(0,0,0));
		tc.setBorder(Rectangle.NO_BORDER);
//		tc.setBorderColor(new BaseColor(0,0,0));
		header.setWidthPercentage(100);
		header.setWidths(new int[] {50,50});
		header.addCell(bc);	
		header.addCell(tc);	
		document.add(header);
	
		document.add(Chunk.NEWLINE);
		addOrderDetails(document, orderDetails);
		getCalc(document,orderDetails);
		document.add(Chunk.NEWLINE);

	}
	
	private static void addOrderDetails(Document document,List<List<String>> orderDetails) throws DocumentException {
		Font fontBold = FontFactory.getFont(FontFactory.TIMES_BOLD,12,BaseColor.BLACK);
		Font fontLight = FontFactory.getFont(FontFactory.TIMES,11,BaseColor.BLACK);
		
		PdfPTable table = new PdfPTable(5);
		//to set the table width
		table.setWidthPercentage(100);
        //to set the cell width
		table.setWidths(new int[]{100,400,100,100,150});

		//to add headings to the table
		String[] header = {"Sl.no","Item & Description","QTY","Price","Amount"};
		for(int i=0;i<header.length;i++) {
			PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(header[i]),fontBold));
			cell.setPaddingBottom(5);
			cell.setBorderWidth(2f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
		}

		//to add data into table
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
			logger.error("Exception from BillOfSupplyPdf class :" + e.getMessage());
		}
		
	}
	
	private static void addThankYouNoteAtTheEndOfPage(Document document, PdfWriter writer,List<DistributorCompanyDetails> distcompanyDetailsList) {  
		  Font fontLight = FontFactory.getFont(FontFactory.TIMES,11,BaseColor.BLACK);
		  
		  Paragraph thanks = new Paragraph("Thank you for your business \n\n",fontLight);
		  thanks.add(new Chunk(distcompanyDetailsList.get(0).getDistributorFirstName()+" "+distcompanyDetailsList.get(0).getDistributorLastName(),fontLight));
		  
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
			logger.error("Exception from BillOfSupplyPdf class :" + e.getMessage());
		}
		
	}

	private static void getCalc(Document document,List<List<String>> orderDetails) throws DocumentException {
		Font fontBold = FontFactory.getFont(FontFactory.TIMES_BOLD,11,BaseColor.BLACK);
		
		//To calculate Total order cost from order details
		double sum = 0;
		DecimalFormat decfor = new DecimalFormat("0.00");  //To format decimal number to 2 digits "decfor.format(sum)"
		for(int i=0;i<orderDetails.size();i++)
		{
			System.out.println("price = "+String.valueOf(orderDetails.get(i).get(orderDetails.get(i).size()-1)));
			sum = sum + Double.parseDouble(String.valueOf((orderDetails.get(i).get(orderDetails.get(i).size()-1))));
		}
		System.out.println("sum = "+sum);
		//To print Rupee symbol in PDF for itextPdf framework
		BaseFont bf = null;
		try {
			bf = BaseFont.createFont("c:/windows/fonts/arial.ttf",
			                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("Exception from BillOfSupplyPdf class :" + e.getMessage());
		}
		Font font = new Font(bf, 12);
		Chunk chunkRupee = new Chunk(" \u20B9"+decfor.format(sum), font);
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
		Paragraph sig = new Paragraph("Total Amount (in Words):  "+EnglishNumberToWords.convert((long)sum)+ " Rupees Only",fontBold);
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
        StringBuilder exSpace = getSpace(25);

		Font font = FontFactory.getFont(FontFactory.TIMES_BOLD,18,BaseColor.BLACK);
		Font font3 = FontFactory.getFont(FontFactory.TIMES_BOLD,12,BaseColor.BLACK);
		Font font2 = FontFactory.getFont(FontFactory.TIMES,12,BaseColor.BLACK);
		Font font4 = FontFactory.getFont(FontFactory.TIMES,12,BaseColor.DARK_GRAY);
		
		Paragraph headerTax = new Paragraph("Bill Of Supply ",font);
		headerTax.setLeading(12f);
		headerTax.setAlignment(Element.ALIGN_CENTER);

		LineSeparator eS = new LineSeparator();
		Paragraph pr = new  Paragraph();
		eS.setLineColor(BaseColor.BLACK);
		eS.setLineWidth(10);
		pr.add(eS);
		
		try {
			document.add(headerTax);
			document.add(pr);
		} catch (DocumentException e) {
			logger.error("Exception from BillOfSupplyPdf class :" + e.getMessage());
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


