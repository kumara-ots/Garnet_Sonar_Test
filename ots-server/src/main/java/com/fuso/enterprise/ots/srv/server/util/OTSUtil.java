package com.fuso.enterprise.ots.srv.server.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;

public class OTSUtil {

	public static String generateReportPDFFromHTMLPortrait(String html ,String value) {
		String pdfPath = "C:\\template\\"+value;
		try {
			OutputStream file = new FileOutputStream(new File(pdfPath));
			Document document = new Document(PageSize.A4, 36, 36,85, 36);		//Page margins(right, left, top, bottom)
			PdfWriter writer = PdfWriter.getInstance(document, file);
		    
		    // add header and footer
		    HeaderFooterPageEvent event = new HeaderFooterPageEvent();
	        writer.setPageEvent(event);

		    document.open();
		    HTMLWorker htmlWorker = new HTMLWorker(document);
	  	    htmlWorker.parse(new StringReader(html));
		    document.close();
		    file.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return pdfPath;
	}
	
	public static String generateReportPDFFromHTMLLandscape(String html ,String value) {
		String pdfPath = "C:\\template\\"+value;
		try {
			OutputStream file = new FileOutputStream(new File(pdfPath));
			Document document = new Document(PageSize.A4.rotate(), 36, 36,85, 36); 		//Page margins(right, left, top, bottom)
			PdfWriter writer = PdfWriter.getInstance(document, file);
		    
			// add header and footer
			HeaderFooterPageEvent event = new HeaderFooterPageEvent();
            writer.setPageEvent(event);

            document.open();
            HTMLWorker htmlWorker = new HTMLWorker(document);
  	       	htmlWorker.parse(new StringReader(html));
  	       	document.close();
  	       	file.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		return pdfPath;
	}
	
}
