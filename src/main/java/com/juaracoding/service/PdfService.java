package com.juaracoding.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Map;

@Service
public class PdfService {

    @Autowired
    private TemplateEngine templateEngine;

    /**
     * Men-generate PDF dari template Thymeleaf.
     *
     * @param templateName Nama file template (misal: "invoice.html")
     * @param data         Data yang akan dimasukkan ke template (berupa Map)
     * @return PDF dalam bentuk byte array
     */
    public byte[] generatePdf(String templateName, Map<String, Object> data) throws Exception {
        // 1. Buat Thymeleaf Context
        Context context = new Context();
        context.setVariables(data);

        // 2. Proses template HTML dengan data
        String htmlContent = templateEngine.process(templateName, context);

        // 3. Setup output stream untuk menampung PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // 4. Inisialisasi ITextRenderer (dari Flying Saucer)
        ITextRenderer renderer = new ITextRenderer();

        // Mengatur base URL agar bisa load resource relatif (CSS, gambar)
        // Kita set base URL ke folder 'resources'
        String baseUrl = "classpath:/"; // Menggunakan classpath sebagai root
        renderer.setDocumentFromString(htmlContent, baseUrl);
        renderer.layout();

        // 5. Render HTML ke PDF
        renderer.createPDF(outputStream);

        // Tutup stream
        outputStream.close();

        // 6. Kembalikan PDF sebagai byte array
        return outputStream.toByteArray();
    }
}