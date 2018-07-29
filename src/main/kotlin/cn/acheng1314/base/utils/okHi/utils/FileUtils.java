package cn.acheng1314.base.utils.okHi.utils;

import java.io.*;

/**
 * Created by pc on 2017/8/8.
 */
public class FileUtils {


    /**
     * 输入流转byte[]
     */
    public static byte[] input2byte(InputStream inStream) {
        if (inStream == null) {
            return null;
        }
        byte[] in2b = null;
        BufferedInputStream in = new BufferedInputStream(inStream);
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        int rc = 0;
        try {
            while ((rc = in.read()) != -1) {
                swapStream.write(rc);
            }
            in2b = swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeIO(inStream, in, swapStream);
        }
        return in2b;
    }

    /**
     * 关闭流
     */
    public static void closeIO(Closeable... closeables) {
        if (null == closeables || closeables.length <= 0) {
            return;
        }
        for (Closeable cb : closeables) {
            try {
                if (null == cb) {
                    continue;
                }
                cb.close();
            } catch (IOException e) {
                throw new RuntimeException(
                        FileUtils.class.getClass().getName(), e);
            }
        }
    }
}
