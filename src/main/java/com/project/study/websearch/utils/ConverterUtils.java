package com.project.study.websearch.utils;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class ConverterUtils {

    public static File convertHtmlToPdf(String fileName, String htmlCode) {
        try {
            File file = File.createTempFile(fileName, ".pdf");
            ConverterProperties props = new ConverterProperties();
            props.setTagWorkerFactory(new CustomTagWorkerFactory());
            HtmlConverter.convertToPdf(htmlCode, new FileOutputStream(file), props);
            return file;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static byte[] convertFileToByteArray(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
