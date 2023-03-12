package utils;

import sun.misc.BASE64Decoder;

import java.io.*;

/**
 * @Author huang
 * @DATE 2020/1/31
 */
public class FileStrUtil {
    /**
     * summary:将字符串存储为文件 采用Base64解码
     */
    public static void streamSaveAsFile(InputStream is, String outFileStr) {
        FileOutputStream fos = null;
        try {
            File file = new File(outFileStr);
            BASE64Decoder decoder = new BASE64Decoder();
            fos = new FileOutputStream(file);
            byte[] buffer = decoder.decodeBuffer(is);
            fos.write(buffer, 0, buffer.length);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
                fos.close();
            } catch (Exception e2) {
                e2.printStackTrace();
                throw new RuntimeException(e2);
            }
        }
    }

    /**
     *
     *
     * summary:将字符串存储为文件
     *
     */
    public static void stringSaveAsFile(String fileStr, String outFilePath) {
        InputStream out = new ByteArrayInputStream(fileStr.getBytes());
        FileStrUtil.streamSaveAsFile(out, outFilePath);
    }

    /**
     * 将流转换成字符串 使用Base64加密
     *
     */
    public static String streamToString(InputStream inputStream) throws IOException {
        byte[] bt = toByteArray(inputStream);
        inputStream.close();
        String out = new sun.misc.BASE64Encoder().encodeBuffer(bt);
        return out;
    }

    /**
     * 将流转换成字符串
     *
     */
    public static String fileToString(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream is = new FileInputStream(file);
        String fileStr = FileStrUtil.streamToString(is);
        return fileStr;
    }

    /**
     *
     * summary:将流转化为字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     *
     */
    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        byte[] result = null;
        try {
            int n = 0;
            while ((n = inputStream.read(buffer)) != -1) {
                out.write(buffer, 0, n);
            }
            result = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            out.close();
        }
        return result;
    }
    
    
    /**
     * 获取文件扩展名
     * @return
     */
    public static String ext(String filename) {
        int index = filename.lastIndexOf(".");
 
        if (index == -1) {
            return null;
        }
        String result = filename.substring(index + 1);
        return result;
    }
    
    /**
      * 获取文件大小
      *
      * @param size
      * @return
      */
    public static String getPrintSize(long size) {
    	// 如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
    	if (size < 1024) {
    		return String.valueOf(size) + "B";
    	} else {
    		size = size / 1024;
    	}
    	// 如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
    	// 因为还没有到达要使用另一个单位的时候
    	// 接下去以此类推
    	if (size < 1024) {
    		return String.valueOf(size) + "KB";
    	} else {
    		size = size / 1024;
    	}
    	if (size < 1024) {
    		// 因为如果以MB为单位的话，要保留最后1位小数，
    		// 因此，把此数乘以100之后再取余
    		size = size * 100;
    		return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "MB";
    	} else {
    		// 否则如果要以GB为单位的，先除于1024再作同样的处理
    		size = size * 100 / 1024;
    		return String.valueOf((size / 100)) + "." + String.valueOf((size % 100)) + "GB";
    	}
    }
    
    
    /**
     * 判断是否为允许的上传文件类型,true表示允许
     */
    public boolean checkFile(String fileName) {
        //设置允许上传文件类型
        String suffixList = "jpg,gif,jpeg,png,rar,txt,zip,doc,ppt,xls,pdf,docx,xlsx";
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".")
                + 1, fileName.length());
        if (suffixList.contains(suffix.trim().toLowerCase())) {
            return true;
        }
        return false;
    }
    

    public static void main(String[] args) throws Exception {
        String fromPath = "E://测试附件1.docx";
        String toPath = "E://aaaaa.docx";
        String fileStr = FileStrUtil.fileToString(fromPath);
        System.out.println(fileStr);
        FileStrUtil.stringSaveAsFile(fileStr, toPath);
    }
}
