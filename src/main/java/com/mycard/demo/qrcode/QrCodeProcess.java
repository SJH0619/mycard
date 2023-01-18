package com.mycard.demo.qrcode;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

@Component
public class QrCodeProcess {
    public void createQrCode(String userId) throws WriterException, IOException {
        String defaultQrCodeContent = "http://43.201.111.6:18080/addbusinesscard/";
        String qrCodeContent = defaultQrCodeContent + userId;

        BitMatrix matrix = new MultiFormatWriter().encode(qrCodeContent, BarcodeFormat.QR_CODE, 296, 296);
        Path path = Paths.get("./upload/" + userId + "_QRCODE.png");

        try {
            MatrixToImageWriter.writeToPath(matrix, "png", path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
