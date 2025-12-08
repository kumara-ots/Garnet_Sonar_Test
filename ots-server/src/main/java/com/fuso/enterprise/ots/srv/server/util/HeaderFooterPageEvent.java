package com.fuso.enterprise.ots.srv.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.*;

public class HeaderFooterPageEvent extends PdfPageEventHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(HeaderFooterPageEvent.class);
	
	Font font = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
    Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.NORMAL);

    //For Adding Footer in PDF
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();

        float footerXPosition = document.leftMargin();
        float footerYPosition = document.bottom() - 20;

        // Add footer text
        ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, new Paragraph("\u00A9 Ortusolis Technology Services", font),
                footerXPosition, footerYPosition, 0);

        //Add Page Numbers for each page
        int pageNumber = writer.getPageNumber();
        Paragraph pageNumberText = new Paragraph("Page " + pageNumber, font);
        float pageNumberXPosition = document.right() - document.rightMargin();
        float pageNumberYPosition = document.bottom() - 20;

        ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, pageNumberText, pageNumberXPosition, pageNumberYPosition, 0);

        // Draw a horizontal line in the footer
        cb.setLineWidth(0.5f);		// Set line width
        cb.moveTo(document.left(), document.bottomMargin() - 10);		// Adjust Y position as needed
        cb.lineTo(document.right(), document.bottomMargin() - 10);		// Adjust Y position as needed
        cb.stroke();
    }

    
    //For Adding header in PDF
    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();

        // Header
        Image img = null;
        float headerYPosition = 0;
        float headerXPosition = 36f + 28; 	// Adjusting the image position to the right by 1 cm (28 points)

        //Condition to check page orientation & decide portrait or landscape
        if (document.getPageSize().getWidth() > document.getPageSize().getHeight()) {
            //Adding image for Landscape
            try {
           	 img = Image.getInstance(HeaderFooterPageEvent.class.getResource("/logo_home.jpg"));
                img.setAbsolutePosition(headerXPosition, PageSize.A4.rotate().getHeight() - 70);
                img.scaleToFit(100, 100);
                document.add(img);
                headerYPosition = PageSize.A4.rotate().getHeight() - 50;
            } catch (Exception e) {
                logger.error("Exception while creating pdf :" + e.getMessage());
            }
        } else {
            //Adding image for Portrait
            try {
           	 img = Image.getInstance(HeaderFooterPageEvent.class.getResource("/logo_home.jpg"));
                img.setAbsolutePosition(headerXPosition, PageSize.A4.getHeight() - 50);
                img.scaleToFit(100, 100);
                document.add(img);
                headerYPosition = PageSize.A4.getHeight() - 50;
            } catch (Exception e) {
                logger.error("Exception while creating pdf :" + e.getMessage());
            }
        }

        // Draw a horizontal line below the header image
        float headerLineYPosition = headerYPosition - 28; 	// Moving the line 1 cm (28 points) below the header image
        cb.moveTo(document.left(), headerLineYPosition);	// Adjust Y position as needed
        cb.lineTo(document.right(), headerLineYPosition);	// Adjust Y position as needed
        cb.stroke();
        
        //Add & Align Header text
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, new Paragraph("Ortusolis Technology Services LLP", font1),
                (document.right() - document.left()) / 2 + document.leftMargin(), document.top() + 50, 0);		// Adjust header text position as needed by increasing/decreasing document.top() + 0
        ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, new Paragraph("Bringing IT to the Grassroots", font),
                (document.right() - document.left()) / 2 + document.leftMargin(), document.top() + 40, 0);		// Adjust header text position as needed by increasing/decreasing document.top() + 0
    }
}
