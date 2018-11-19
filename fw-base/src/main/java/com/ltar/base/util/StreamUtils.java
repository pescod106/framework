package com.ltar.base.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/11/19
 * @version: 1.0.0
 */
public class StreamUtils {
    /**
     * 将文件读为byte数组
     *
     * @param file
     * @return
     */
    public static byte[] fileToByte(String file) {

        byte[] result = null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            result = new byte[inputStream.available()];
            inputStream.read(result);
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
