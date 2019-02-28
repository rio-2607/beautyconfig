package com.beautyboss.slogen.config.client.util;

import java.io.*;

/**
 * Author : Slogen
 * Date   : 2019/2/27
 */
public class FileUtils {


    /**
     * Test whether a directory is writable
     *
     * @param dir
     * @return
     */
    public static boolean ensureAccess(String dir) {
        if(dir == null) {
            throw new NullPointerException("directory is null");
        }
        try {
            if(ensureDir(dir)) {
                String file = dir + "/test";
                write(file, "test");
                delete(file);
                return true;
            } else {
                return false;
            }
        } catch(Exception se) {
            return false;
        }
    }

    public static boolean ensureDir(String dir) {
        File df = new File(dir);
        if (df.exists()) {
            return df.isDirectory();
        } else {
            return df.mkdirs();
        }
    }

    public static boolean delete(String file) {
        File f = new File(file);
        if (f.exists() && f.isFile()) {
            return f.delete();
        }
        return false;
    }

    public static void write(String file, String content) throws IOException {
        OutputStream os = new FileOutputStream(file);
        writeTo(os, "UTF-8", content);
    }

    public static String read(String file) throws IOException {
        InputStream is = new FileInputStream(file);
        String content = readFrom(is, "UTF-8");
        return content;
    }

    public static void writeTo(OutputStream os, String charset, String content) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(content.getBytes(charset));

        copy(bais, os, AutoClose.OUTPUT);
    }

    public static String readFrom(InputStream is, String charsetName) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(16 * 1024);

        copy(is, baos, AutoClose.INPUT);
        return baos.toString(charsetName);
    }

    public static void copy(InputStream is, OutputStream os, AutoClose stream) throws IOException {
        byte[] content = new byte[4096];

        try {
            while (true) {
                int size = is.read(content);

                if (size == -1) {
                    break;
                } else {
                    os.write(content, 0, size);
                }
            }
        } finally {
            stream.close(is);
            stream.close(os);
        }
    }

    public enum AutoClose {
        NONE,

        INPUT,

        OUTPUT,

        INPUT_OUTPUT;

        public void close(InputStream is) {
            if (this == INPUT || this == INPUT_OUTPUT) {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        // ignore it
                    }
                }
            }
        }

        public void close(OutputStream os) {
            if (this == OUTPUT || this == INPUT_OUTPUT) {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        // ignore it
                    }
                }
            }
        }
    }

}
