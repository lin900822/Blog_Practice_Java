package com.lin.blog.portal.utils;

public class ByteConverter {
    private static final long KILOBYTE = 1024;
    private static final long MEGABYTE = KILOBYTE * 1024;
    private static final long GIGABYTE = MEGABYTE * 1024;
    private static final long TERABYTE = GIGABYTE * 1024;

    public static String convertBytes(long bytes) {
        String result = "";
        if (bytes >= TERABYTE) {
            result = String.format("%.2f TB", (double) bytes / TERABYTE);
        } else if (bytes >= GIGABYTE) {
            result = String.format("%.2f GB", (double) bytes / GIGABYTE);
        } else if (bytes >= MEGABYTE) {
            result = String.format("%.2f MB", (double) bytes / MEGABYTE);
        } else if (bytes >= KILOBYTE) {
            result = String.format("%.2f KB", (double) bytes / KILOBYTE);
        } else {
            result = String.format("%d bytes", bytes);
        }
        return result;
    }
}
