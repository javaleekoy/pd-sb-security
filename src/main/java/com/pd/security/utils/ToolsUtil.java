package com.pd.security.utils;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author peramdy on 2018/6/6.
 */
public class ToolsUtil {

    /**
     * md5 加密
     *
     * @param str
     * @return
     */
    public static String md5(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = messageDigest.digest(str.getBytes("UTF-8"));
            StringBuilder strBuilder = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                int temp = 0xff & bytes[i];//TODO:此处为什么添加 0xff & ？
                String hexString = Integer.toHexString(temp);
                if (hexString.length() == 1) {//如果是十六进制的0f，默认只显示f，此时要补上0
                    strBuilder.append("0").append(hexString);
                } else {
                    strBuilder.append(hexString);
                }
            }
            System.out.println(strBuilder.toString());
            return strBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 序列化对象
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            if (object != null) {
                baos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
                return baos.toByteArray();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反序列化对象
     *
     * @param bytes
     * @return
     */
    public static Object deserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            if (bytes != null && bytes.length > 0) {
                bais = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(bais);
                return ois.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 序列化对象
     *
     * @param object
     * @return
     */
    public static byte[] getBytes(Object object) {
        if (object != null) {
            if (object instanceof String) {
                try {
                    return object.toString().getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    return null;
                }
            } else {
                return serialize(object);
            }
        }
        return null;
    }

}
