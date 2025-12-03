package com.fuso.enterprise.ots.srv.server.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.cxf.helpers.IOUtils;

import com.fuso.enterprise.ots.srv.api.model.domain.Base64ByteImage;
import com.fuso.enterprise.ots.srv.api.model.domain.ZipBase64Images;

import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class Base64Util {
	
	
	public Base64ByteImage convertPasswordProtectedzip(ZipBase64Images zipBase64Images) {
		Base64ByteImage base64ByteImage = new Base64ByteImage();
		zipBase64Images.getBase64Image().forEach(
				base64Item->{
					try {
						Base64 base64 = new Base64();
						base64ByteImage.setBase64String(base64.encodeBase64String(zipFileProtected(base64Item.getBase64String(), zipBase64Images.getFilename(), zipBase64Images.getPassword()))); 
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		);
		return base64ByteImage;
		
	}
	
	private static byte[] zipFileProtected(String fileBytes, String fileName, String pass) throws IOException {

        ByteArrayInputStream inputByteStream = null;
        ByteArrayOutputStream outputByteStream = null;
        net.lingala.zip4j.io.ZipOutputStream outputZipStream = null;

        try {
            //write the zip bytes to a byte array
            outputByteStream = new ByteArrayOutputStream();
            outputZipStream = new net.lingala.zip4j.io.ZipOutputStream(outputByteStream);
            
            Base64 base64 = new Base64();
            //input byte stream to read the input bytes
            inputByteStream = new ByteArrayInputStream(base64.decodeBase64(fileBytes));

            //init the zip parameters
            ZipParameters zipParams = new ZipParameters();
            zipParams.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            zipParams.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            zipParams.setEncryptFiles(true);
            zipParams.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            zipParams.setPassword(pass);
            zipParams.setSourceExternalStream(true);
            zipParams.setFileNameInZip(fileName);

            //create zip entry
            outputZipStream.putNextEntry(new File(fileName), zipParams);
            IOUtils.copy(inputByteStream, outputZipStream);
            outputZipStream.closeEntry();

            //finish up
            outputZipStream.finish();

            /*IOUtils.closeQuietly(inputByteStream);
            IOUtils.closeQuietly(outputByteStream);
            IOUtils.closeQuietly(outputZipStream);*/

            return outputByteStream.toByteArray();

        } catch (ZipException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
           /* IOUtils.closeQuietly(inputByteStream);
            IOUtils.closeQuietly(outputByteStream);
            IOUtils.closeQuietly(outputZipStream);*/
        }
        return null;
    }
	
	

}

	