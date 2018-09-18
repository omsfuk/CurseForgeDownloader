package cn.omsfuk.curseforge.console.util;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * by omsfuk
 * ---- 创建于 9/18/18 4:18 PM
 */
public abstract class FileUtils {

    private static char[] hexs = "0123456789ABCDEF".toCharArray();

    public static String md5(File file) throws IOException {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] bytes = md.digest(getBytes(file));
        return toHex(bytes);
    }

    public static String md5(String fileName) throws IOException {
        return md5(new File(fileName));
    }

    public static byte[] getBytes(File file) throws IOException {
        FileInputStream inputStream = new FileInputStream(file);
        int size = inputStream.available();
        if (size > 20 * 1024 * 1024) throw new RuntimeException("The file is too large");
        byte[] bytes = new byte[size];
        inputStream.read(bytes, 0, bytes.length);
        inputStream.close();
        return bytes;
    }

    public static byte[] getByte(String fileName) throws IOException {
        return getBytes(new File(fileName));
    }

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(hexs[(b >> 4) & 0x0f]);
            sb.append(hexs[b & 0x0f]);
        }
        return sb.toString();
    }

    public static void writeTo(String content, String path) throws IOException {
        writeTo(content.getBytes(), path);
    }

    public static void writeTo(byte[] bytes, String path) throws IOException {
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(path));
        outputStream.write(bytes, 0, bytes.length);
        outputStream.close();
    }
}
