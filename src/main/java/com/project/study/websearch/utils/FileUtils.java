package com.project.study.websearch.utils;

import java.io.*;

public class FileUtils {

    public static File makePdfFile(byte[] binary) throws IOException {
        String pdfFileName = "PdfFile_" + System.currentTimeMillis();
        File pdfFile = File.createTempFile(pdfFileName, ".pdf");
        FileOutputStream fos = new FileOutputStream(pdfFile);
        fos.write(binary);
        fos.close();
        return pdfFile;
    }

    public static File writeHtml(String htmlCode) {
        try {
            String fileName = "HtmlFile_" + System.currentTimeMillis();
            File htmlFile = File.createTempFile(fileName, ".html");
            BufferedWriter writer = new BufferedWriter(new FileWriter(htmlFile));
            writer.write(htmlCode);
            writer.close();
            return htmlFile;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

    }
}
