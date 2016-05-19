package com.hzf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by ZhiFeng Hu on 2015/8/21.
 */
public class FileOperate {
    public static void main(String[] args) {
        copy("D:/gather_net_patent.js", "D:/1.js");
        copy(new File("D:/gather_net_patent.js"),"D:/1.js");
    }

    public static void copy(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    fs.write(buffer, 0, byteread);
                }
                System.out.println("copy "+oldPath+" to " + newPath);
                fs.close();
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("error  ");
            e.printStackTrace();
        }
    }

    public static void copy(File oldfile, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            //File     oldfile     =     new     File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldfile);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("error  ");
            e.printStackTrace();
        }
    }
    public static boolean renameFile(File from,String to){
        return from.renameTo(new File(to));
    }
}
