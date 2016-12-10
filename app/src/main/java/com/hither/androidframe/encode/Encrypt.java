package com.hither.androidframe.encode;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Random;

public class Encrypt {

    public static String encode(byte aa[]) {
        StringBuffer stringbuffer;
        stringbuffer = new StringBuffer();

        int i = 0;
        int len = aa.length;
        while (i < len) {
            int k = aa[i++] & 0xff;

            switch (len - i) {
                case 0:
                    stringbuffer.append(a[k >>> 2]);
                    stringbuffer.append(a[(k & 3) << 4]);
                    stringbuffer.append("..");

                    break;
                case 1: {
                    int i1 = aa[i++] & 0xff;

                    stringbuffer.append(a[k >>> 2]);
                    stringbuffer.append(a[(k & 3) << 4 | (i1 & 0xf0) >>> 4]);
                    stringbuffer.append(a[(i1 & 0xf) << 2]);
                    stringbuffer.append(".");
                    break;
                }
                default: {
                    int i1 = aa[i++] & 0xff;
                    int l = aa[i++] & 0xff;
                    stringbuffer.append(a[k >>> 2]);
                    stringbuffer.append(a[(k & 3) << 4 | (i1 & 0xf0) >>> 4]);
                    stringbuffer.append(a[(i1 & 0xf) << 2 | (l & 0xc0) >>> 6]);
                    stringbuffer.append(a[l & 0x3f]);
                }
            }

        }


        return stringbuffer.toString();

    }


    public static byte[] decode(String str) throws UnsupportedEncodingException {
        StringBuffer stringbuffer;
        int i;
        int k;
        stringbuffer = new StringBuffer();
        byte[] s = str.getBytes("US-ASCII");
        k = s.length;
        i = 0;

        while (i < k) {

            byte byte0 = b[s[i++]];
            if (byte0 == -1 || i == k)
                break;
            byte byte1 = b[s[i++]];
            if (byte1 == -1 || i == k)
                break;

            stringbuffer.append((char) (byte0 << 2 | (byte1 & 0x30) >>> 4));

            byte s2 = s[i++];
            byte byte2 = b[s2];
            if (byte2 == -1 || i == k)
                break;

            if (s2 == '.')
                break;

            stringbuffer.append((char) ((byte1 & 0xf) << 4 | (byte2 & 0x3c) >>> 2));

            byte s3 = s[i++];
            byte byte3 = b[s3];
            if (byte3 == -1)
                break;


            if (s3 == '.')
                break;
            stringbuffer.append((char) (byte3 | (byte2 & 3) << 6));
        }

        return stringbuffer.toString().getBytes("iso8859-1");
    }

    private static char a[] = {
            '5', 'r', 'c', 'd', 'R', 'x', 'j', '3', 'Y', 'w',
            'i', 'O', 'y', 'X', 'S', 'V', 'D', 'm', 'I', 'P',
            'E', '2', 'T', 'K', 'u', 'g', 'v', 'f', 'C', 'J',
            'L', 'A', 'o', 'z', '1', 'k', 'H', '6', 't', 'B',
            '8', '_', 'h', 's', '9', 'W', 'a', '4', 'N', 'F',
            'q', 'b', 'G', 'U', '0', 'p', 'n', 'e', 'Q', '7',
            'M', 'l', '-', 'Z'
    };
    private static byte b[] = {
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, 62, -1, -1, 54, 34,
            21, 7, 47, 0, 37, 59, 40, 44, -1, -1,
            -1, -1, -1, -1, -1, 31, 39, 28, 16, 20,
            49, 52, 36, 18, 29, 23, 30, 60, 48, 11,
            19, 58, 4, 14, 22, 53, 15, 45, 13, 8,
            63, -1, -1, -1, -1, 41, -1, 46, 51, 2,
            3, 57, 27, 25, 42, 10, 6, 35, 61, 17,
            56, 32, 55, 50, 1, 43, 38, 24, 26, 9,
            5, 12, 33, -1, -1, -1, -1, -1
    };

    public static String encode(long l) {
        byte[] b = new byte[8];
        int off = 56;
        for (int i = 0; i < 8; i++) {
            b[i] = (byte) ((l >> off) & 0xff);
            off -= 8;
        }
        return encode(b);
    }

    public static String encodeNow() {
        long l = System.currentTimeMillis();

        byte[] bb = new byte[16];

        for (int i = 0; i < 8; i++) {
            byte b = (byte) ((l) & 0xff);
            bb[i + i + 1] = b;
            for (int j = 0; j < 8; j++) {

                bb[j + j] |= ((b & 1) << i);
                b = (byte) (b >> 1);
            }
            l = l >> 8;
        }

        return encode(bb).substring(0, 20);
    }

    public static String encodeNow(long l) {


        byte[] bb = new byte[16];

        for (int i = 0; i < 8; i++) {
            byte b = (byte) ((l) & 0xff);
            bb[i + i + 1] = b;
            for (int j = 0; j < 8; j++) {

                bb[j + j] |= ((b & 1) << i);
                b = (byte) (b >> 1);
            }
            l = l >> 8;
        }

        return encode(bb).substring(0, 20);
    }

    public static String encode(String data, String charset) throws Exception {
        byte buffer[] = data.getBytes(charset);


        return encode(buffer);
    }

    public static String encode(String data) throws Exception {
        return encode(data, "UTF8");
    }

    public static String decode(String data, String charset) throws Exception {
        return new String(decode(data), charset);
    }


    private static void exportAB() {
        char cc[] = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_".toCharArray();

        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < 100; i++) {
            int a = r.nextInt(64);
            int b = r.nextInt(64);
            char t = cc[a];
            cc[a] = cc[b];
            cc[b] = t;
        }
        System.out.println("======================a====");
        for (int i = 0; i < 64; i++) {
            if (i % 10 == 0)
                System.out.println("");
            System.out.print("'" + cc[i] + "',");
        }
        System.out.println("");

        System.out.println("======================b====");
        int ii[] = new int[128];
        for (int i = 0; i < 128; i++)
            ii[i] = -1;
        for (int i = 0; i < 64; i++) {
            char c = cc[i];
            if (ii[c] != -1)
                System.out.println("ii[" + c + "] duped");
            ii[c] = i;
        }

        for (int i = 0; i < 128; i++) {
            if (i % 10 == 0)
                System.out.println("");
            System.out.print(ii[i] + ",");
        }
    }

    public static String encodePassword(String pwd, Date date) {
        if (pwd.isEmpty()) return pwd;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");


            if (date != null)
                return "T" + encode(md.digest((pwd + (date.getTime())).getBytes("UTF8")));
            else
                return "M" + encode(md.digest(pwd.getBytes("UTF8")));

        } catch (Exception e) {
            try {
                return "E" + encode(pwd.getBytes("UTF8"));
            } catch (UnsupportedEncodingException e1) {
            }
        }
        return "N" + pwd;
    }

    public static boolean verifyPassword(String input_pwd, Date date, String db_pwd) {
        boolean ei = input_pwd.isEmpty();
        boolean ed = db_pwd.isEmpty();
        if (ei && ed) return true;
        if (ei || ed) return false;


        char h = db_pwd.charAt(0);

        switch (h) {
            case 'M':
                String p = encodePassword(input_pwd, null);
                return db_pwd.equals(p);
            case 'T':
                if (date == null) return false;
                String p1 = encodePassword(input_pwd, date);
                return db_pwd.equals(p1);
            case 'E':
                try {
                    String p2 = encode(input_pwd.getBytes("UTF8"));
                    String pwd = db_pwd.substring(1);
                    return pwd.equals(p2);
                } catch (UnsupportedEncodingException e) {
                    return false;
                }
            case 'N':
                String pwd = db_pwd.substring(1);
                return input_pwd.equals(pwd);
        }
        return false;
    }

    public static void main(String[] argv) throws Exception {
//		String ss=encode("112233");
//		System.out.println(ss);
//		System.out.println(decode(ss,"UTF8"));
//		for(int i=0;i<100;i++)
//		{	
//		 System.out.println(encodeNow());
//		 Thread.sleep(1);
//		}
//		 System.out.println(encodeNow());

        //exportAB();

        Date d = new Date();
        String e = encodePassword("123", null);
        System.out.println(e);
        System.out.println(verifyPassword("123", d, e));
        System.out.println(verifyPassword("123", d, "N123"));

    }

}
