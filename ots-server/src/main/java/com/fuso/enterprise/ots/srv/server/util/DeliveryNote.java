package com.fuso.enterprise.ots.srv.server.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fuso.enterprise.ots.srv.api.model.domain.DistributorCompanyDetails;
import com.fuso.enterprise.ots.srv.api.model.domain.OrderProductDetails;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator; 

public class DeliveryNote {
	
	private static final Logger logger = LoggerFactory.getLogger(DeliveryNote.class);
	
	//To Generate PDf & file will not be save in local system.
	public static byte[] getDeliveryNotePdf(List<OrderProductDetails> orderProduct,List<DistributorCompanyDetails> companyDetails) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream); // In-memory writer

            document.open();

            createPageBorder(document);

			getFromAddressAndLogo(document, writer,companyDetails);
			getDividerLine(document);

			gettoAddress(document,orderProduct);
			getDividerLine(document);
			
			getProductDetails(document,orderProduct);
			getDividerLine(document);
	
			getTermsandCondition(document);

			getHandleWithCareImage(document);
			getDividerLine(document);

			document.close();
            System.out.println("Bill of Supply PDF generated successfully");

        } catch (Exception e) {
        	logger.error("Exception from DeliveryNote class :" + e.getMessage());
        }
        return byteArrayOutputStream.toByteArray(); // Return byte[]
    }

	// to get FromAddress And Logo
	public static void getFromAddressAndLogo(Document document,PdfWriter writer,List<DistributorCompanyDetails> companyDetails)
			throws DocumentException, MalformedURLException, IOException {
		Font fontBold = FontFactory.getFont(FontFactory.TIMES_BOLD, 12, BaseColor.BLACK);
		Font fontLight = FontFactory.getFont(FontFactory.TIMES, 11, BaseColor.BLACK);

		PdfPTable table1 = new PdfPTable(3);
		table1.setWidthPercentage(100);
		table1.setWidths(new int[] { 20, 30, 50 });
		table1.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		table1.setHorizontalAlignment(Image.LEFT);

		// To Add Logo
		Image logo = Image.getInstance(DeliveryNote.class.getResource("/OTSPL-Logo.jpg"));
		System.out.println("logo printing" +logo);
		logo.setAlignment(Image.LEFT);
		logo.setBorder(Image.NO_BORDER);
		table1.addCell(logo);

		// To add empty cell for the middle empty space
		PdfPCell cell1 = new PdfPCell();
		cell1.setBorder(Rectangle.NO_BORDER);
		table1.addCell(cell1);

		// To add from address
		Paragraph from = new Paragraph("From \n\n", fontBold);
		from.add(new Chunk(companyDetails.get(0).getCompanyName() + "\n\n", fontBold));
		from.add(new Chunk(companyDetails.get(0).getCompanyAddress() +"\n\n", fontLight));
		from.add(new Chunk(companyDetails.get(0).getCompanyPincode() +"\n\n", fontLight));
		
		PdfPCell cell2 = new PdfPCell(from);
		cell2.setBorder(Rectangle.NO_BORDER);

		table1.addCell(cell2);

		document.add(Chunk.NEWLINE);
		document.add(Chunk.NEWLINE);

		document.add(table1);

	}
	
	// To add horizontal line with black color
	private static void getDividerLine(Document document) throws DocumentException {
		LineSeparator eS = new LineSeparator();
		Paragraph pr = new Paragraph();
		eS.setLineColor(BaseColor.BLACK);
		eS.setLineWidth(3);
		pr.add(eS);
		document.add(pr);

	}

	// To get To Address
	private static void gettoAddress(Document document,List<OrderProductDetails> orderProduct) throws DocumentException, MalformedURLException, IOException {
		document.add(Chunk.NEWLINE);

		Font fontBold = FontFactory.getFont(FontFactory.TIMES_BOLD, 14, BaseColor.BLACK);
		Font fontLight = FontFactory.getFont(FontFactory.TIMES, 13, BaseColor.BLACK);

		PdfPTable tablefortoadd = new PdfPTable(2);
		tablefortoadd .setWidthPercentage(100);
		tablefortoadd .setWidths(new int[] {60,40 });
		// To add SHIP To details
		Paragraph Toadd = new Paragraph(" TO \n\n", fontBold);
		Toadd.add(new Chunk(orderProduct.get(0).getCustomerName() + "\n\n", fontBold));
		Toadd.add(new Chunk(orderProduct.get(0).getDeliveryAddress()+ "\n\n", fontLight));
		Toadd.add(new Chunk(orderProduct.get(0).getCustomerContactNo()+ "\n\n", fontLight));

		PdfPCell toadd = new PdfPCell(Toadd);
		toadd.setBorder(Rectangle.NO_BORDER);
		
		PdfPCell orderNo = new PdfPCell();
     	orderNo.setBorder(Rectangle.NO_BORDER);

		tablefortoadd.addCell(toadd);
		tablefortoadd.addCell(orderNo);
		document.add(tablefortoadd);

	}
	
	private static void getProductDetails(Document document,List<OrderProductDetails> orderProduct) throws DocumentException, MalformedURLException, IOException {
		document.add(Chunk.NEWLINE);

		Font fontBold = FontFactory.getFont(FontFactory.TIMES_BOLD, 14, BaseColor.BLACK);
		Font fontLight = FontFactory.getFont(FontFactory.TIMES, 13, BaseColor.BLACK);

		PdfPTable deliveryTable = new PdfPTable(4);
		deliveryTable.setWidthPercentage(100);
		deliveryTable.setWidths(new int[] {25,40,15,20});
		
		String[] header = {"SubOrder Id","Item & Discription","Quantity","Amount"};
		for(int i=0;i<header.length;i++) {
			PdfPCell cell = new PdfPCell(new Phrase(String.valueOf(header[i]),fontBold));
			cell.setPaddingBottom(5);
			cell.setBorderWidth(2f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            deliveryTable.addCell(cell);
		}
		
		for(int i=0; i<orderProduct.size(); i++) {
			// To add SHIP To details
			Paragraph headerData = new Paragraph(orderProduct.get(i).getOtsSubOrderId() + "\n\n", fontBold);
	
			PdfPCell suborder = new PdfPCell(headerData);
			suborder.setPaddingBottom(5);
			suborder.setBorderWidth(2f);
			suborder.setHorizontalAlignment(Element.ALIGN_CENTER);
			suborder.setBorderColor(BaseColor.BLACK);
			
			Paragraph headerData1 = new Paragraph(orderProduct.get(i).getProductName().toUpperCase() + "\n\n", fontBold);
	
			PdfPCell productName = new PdfPCell(headerData1);
			productName.setPaddingBottom(5);
			productName.setBorderWidth(2f);
			productName.setHorizontalAlignment(Element.ALIGN_CENTER);
			productName.setBorderColor(BaseColor.BLACK);
			
			Paragraph headerData2 = new Paragraph(orderProduct.get(i).getOtsOrderedQty() + "\n\n", fontBold);
	
			PdfPCell orderQty = new PdfPCell(headerData2);
			orderQty.setPaddingBottom(5);
			orderQty.setBorderWidth(2f);
			orderQty.setHorizontalAlignment(Element.ALIGN_CENTER);
			orderQty.setBorderColor(BaseColor.BLACK);
			orderQty.setBorderColor(BaseColor.BLACK);
			
			Paragraph headerData3 = new Paragraph(orderProduct.get(i).getOtsOrderProductCost() + "\n\n", fontBold);
	
			PdfPCell orderCost = new PdfPCell(headerData3);
			orderCost.setPaddingBottom(5);
			orderCost.setBorderWidth(2f);
			orderCost.setHorizontalAlignment(Element.ALIGN_CENTER);
			orderCost.setBorderColor(BaseColor.BLACK);
			orderCost.setBorderColor(BaseColor.BLACK);
			orderCost.setBorderColor(BaseColor.BLACK);
	
			deliveryTable.addCell(suborder);
			deliveryTable.addCell(productName);
			deliveryTable.addCell(orderQty);
			deliveryTable.addCell(orderCost);
		}			
		document.add(deliveryTable);
	}

	private static void getTermsandCondition(Document document) throws DocumentException {

		Font fontBold = FontFactory.getFont(FontFactory.TIMES_BOLD, 11, BaseColor.BLACK);
		Font fontLight = FontFactory.getFont(FontFactory.TIMES, 11, BaseColor.BLACK);

		PdfPTable tctable = new PdfPTable(1);
		tctable.setWidthPercentage(100);

		Paragraph tnc = new Paragraph("\n\n\nTERMS AND CONDITIONS\n\n", fontBold);
		tnc.add(new Chunk("1. If undelivered please return to From address. \n\n", fontLight));
		tnc.add(new Chunk(
				"2. If your order has multiple itimes, order may came to seperate shipments and item will be charged one shipment.\n\n",
				fontLight));
		tnc.add(new Chunk("3. Transportation damages/scratches will not be responsible.\n\n\n", fontLight));

		PdfPCell TandC = new PdfPCell(tnc);
		TandC.setBorder(Rectangle.NO_BORDER);

		tctable.addCell(TandC);

		document.add(tctable);
	}

	// To Add The handleWithcare image
	private static void getHandleWithCareImage(Document document)
			throws DocumentException, MalformedURLException, IOException {

//		Font fontBold = FontFactory.getFont(FontFactory.TIMES_BOLD, 11, BaseColor.BLACK);
//		Font fontLight = FontFactory.getFont(FontFactory.TIMES, 11, BaseColor.BLACK);

		PdfPTable tableforimage = new PdfPTable(1);
		tableforimage.setWidthPercentage(65);
		tableforimage.getRowHeight(24);
		tableforimage.calculateHeights();
		tableforimage.setHorizontalAlignment(Image.MIDDLE);
		tableforimage.getDefaultCell().setBorder(Rectangle.NO_BORDER);

		// To Add The Image
		Image logo = Image.getInstance(DeliveryNote.class.getResource("/Handlewithcare.jpg"));

		logo.setScaleToFitHeight(false);
		logo.scaleAbsolute(logo);
		logo.setAbsolutePosition(100, 0);
		logo.setBorder(Image.NO_BORDER);

		tableforimage.addCell(logo);
		document.add(tableforimage);

	}

	// To Add the Page Border For The PDf Page
	private static void createPageBorder(Document document) throws IOException, DocumentException {
		Rectangle layout = new Rectangle(577, 825, 18, 15);

		layout.setBorderColor(BaseColor.BLACK); // Border color
		layout.setBorderWidth(3); // Border width
		layout.setBorder(Rectangle.BOX); // Border
		document.add(layout);

		System.out.println("To set page Border");

	}
}
