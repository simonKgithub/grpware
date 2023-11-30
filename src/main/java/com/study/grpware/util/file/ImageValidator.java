package com.study.grpware.util.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class ImageValidator {
    public static boolean isValidImage(MultipartFile file) {
        try (final InputStream inputStream = file.getInputStream()) {
            // 파일 시그니처 읽기
            byte[] signature = new byte[8];
            final int bytesRead = inputStream.read(signature);

            // 이미지 형식에 따라 다른 시그니처 값들을 여기에 추가
            if (bytesRead == 8 && ( isJpeg(signature) || isPng(signature) || isGif(signature) )) {
                System.out.println("Valid image");
                return true;
            } else {
                System.out.println("Invalid image");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    private static boolean isJpeg(byte[] signature) {
        byte[] jpegSignature = {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF};
        return Arrays.equals(Arrays.copyOfRange(signature, 0, 3), jpegSignature);
    }

    private static boolean isPng(byte[] signature) {
        byte[] pngSignature = {(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A};
        return Arrays.equals(Arrays.copyOfRange(signature, 0, 8), pngSignature);
    }

    private static boolean isGif(byte[] signature) {
        byte[] gif87aSignature = {'G', 'I', 'F', '8', '7', 'a'};
        byte[] gif89aSignature = {'G', 'I', 'F', '8', '9', 'a'};
        return Arrays.equals(Arrays.copyOfRange(signature, 0, 6), gif87aSignature) ||
                Arrays.equals(Arrays.copyOfRange(signature, 0, 6), gif89aSignature);
    }
}
