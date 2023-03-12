package com.fh.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 主要用来将整个文件夹压缩和解压zip文件
 * 
 * @author ZQJ 20100712
 * 
 */
public class ZipTools {
	/**
	 * 将某些文件压缩成zip文件并且重命名
	 * 
	 * @param filePath
	 * @param fullFileName
	 */
	public static void compressZip(List filePath, String fullFileName,
			List newFileName) throws Exception {
		// 创建一个读取这些文件的缓冲区
		int size = 1024 * 10;
		byte[] buf = new byte[size];
		FileInputStream fin = null;
		ZipOutputStream zout = null;
		try {
			zout = new ZipOutputStream(new FileOutputStream(fullFileName));
			// 对上面的几个文件进行压缩
			for (int i = 0; i < filePath.size(); i++) {
				fin = new FileInputStream(filePath.get(i).toString());
				// 添加zip到输出流
				zout.putNextEntry(new ZipEntry(newFileName.get(i).toString()));
				// 把需要压缩文件的字节流传输到ZIP文件
				int len;
				while ((len = fin.read(buf)) > 0) {
					zout.write(buf, 0, len);
				}
				// 完成创建
				zout.closeEntry();
				fin.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("3456789");
		} finally {
			try {
				if (fin != null)
					fin.close();
				if (zout != null)
					zout.close();
			} catch (IOException e) {
				throw new Exception("3456789");
			}
		}
	}

	/**
	 * 压缩文件夹和文件
	 * 
	 * @param compressedFile
	 * @param zipOutputStream
	 * @param base
	 * @return
	 * @throws IOException
	 */
	public static boolean compressFloderChangeToZip(String compressedFilePath,
			String zipFileRootPath, String zipFileName) throws Exception {
		boolean result = false;
		ZipOutputStream zos = null;
		try {
			File compressedFile = new File(compressedFilePath);
			if ("".equalsIgnoreCase(zipFileName)) {
				zipFileName = compressedFilePath;
			}
			if (!zipFileRootPath.endsWith(File.separator)) {
				zipFileRootPath = zipFileRootPath + File.separator;
			}
			zos = new ZipOutputStream(new FileOutputStream(zipFileRootPath
					+ zipFileName));
			String base = "";
			result = ZipTools.compressFloderChangeToZip(compressedFile, zos,
					base);
			zos.close();
			zos = null;
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			if(zos!=null){
				zos.close();
				zos = null;
			}
			throw new Exception("2324342");
		} finally {
			
			try {
				if(zos!=null){
					zos.close();
					zos = null;
				}
			} catch (IOException e) {
				if(zos!=null){
					zos.close();
					zos = null;
				}
				throw new Exception("2324342");
			}
		}

	}

	private static boolean compressFloderChangeToZip(File compressedFile,
			ZipOutputStream zipOutputStream, String base) throws Exception {
		FileInputStream fis = null;
		try {
			if (compressedFile.isDirectory()) {
				File[] childrenCompressedFileList = compressedFile.listFiles();
				base = base.length() == 0 ? "" : base + File.separator;
				for (int i = 0; i < childrenCompressedFileList.length; i++) {
					ZipTools.compressFloderChangeToZip(
							childrenCompressedFileList[i], zipOutputStream,
							base + childrenCompressedFileList[i].getName());
				}
			} else {
				if ("".equalsIgnoreCase(base)) {
					base = compressedFile.getName();
				}
				zipOutputStream.putNextEntry(new ZipEntry(base));
				fis = new FileInputStream(compressedFile);
				int b;
				while ((b = fis.read()) != -1) {
					zipOutputStream.write(b);
				}
				if (fis != null){
					fis.close();
					fis=null;
				}
			}
			return true;
		} catch (Exception e) {
			if (fis != null){
				fis.close();
				fis=null;
			}	
			throw new Exception("2324342");
		} finally {
			try {
				if (fis != null){
					fis.close();
					fis=null;
				}
			} catch (IOException e) {
				if (fis != null){
					fis.close();
					fis=null;
				}
				throw new Exception("2324342");
			}
		}
	}

	/**
	 * 解压文件夹和文件
	 * 
	 * @param zipFilePath
	 * @param releasePath
	 * @throws IOException
	 */
	public static void decompressFile(String zipFilePath, String releasePath)
			throws Exception {
		int size = 1024 * 10;
		byte[] buf = new byte[size];
		ZipFile zipFile = null;
		InputStream inputStream = null;
		FileOutputStream fos = null;
		ZipEntry zipEntry = null;
		String zipEntryNameStr = "";
		String[] zipEntryNameArray = null;
		try {
			zipFile = new ZipFile(zipFilePath);
			Enumeration<ZipEntry> enumeration = zipFile.getEntries();
			while (enumeration.hasMoreElements()) {
				zipEntry = enumeration.nextElement();
				zipEntryNameStr = zipEntry.getName();
				zipEntryNameArray = zipEntryNameStr.split("/");
				String path = releasePath;
				File root = new File(releasePath);
				if (!root.exists()) {
					root.mkdir();
				}
				for (int i = 0; i < zipEntryNameArray.length; i++) {
					if (i < zipEntryNameArray.length - 1) {
						path = path + File.separator + zipEntryNameArray[i];
						new File(path).mkdir();
					} else {
						if (zipEntryNameStr.endsWith(File.separator)
								|| zipEntryNameStr.endsWith("/")) {
							new File(releasePath + zipEntryNameStr).mkdir();
						} else {
							inputStream = zipFile.getInputStream(zipEntry);
							File file = new File(releasePath + zipEntryNameStr);
							fos = new FileOutputStream(file);
							int len;
							while ((len = inputStream.read(buf)) > 0) {
								fos.write(buf, 0, len);
							}
							inputStream.close();
							fos.close();
						}
					}
				}
			}
		} catch (Exception e) {
			if (zipFile != null)
				zipFile.close();
			if (fos != null)
				fos.close();
			throw new Exception("655454");
		} finally {
			try {
				if (zipFile != null)
					zipFile.close();
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				throw new Exception("655454");
			}
		}
	}

	public static void main(String[] args) {
		String path = "E:/temp/test1.zip";
		String realPath="E:\\temp\\";
		try {
			decompressFile(path,realPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		List file = new ArrayList();
//		file.add("E:\\upload\\a.txt");
//		file.add("E:\\upload\\b.txt");
//		file.add("E:\\upload\\b.txt");
//		List newFileName = new ArrayList();
//		newFileName.add("a.txt");
//		newFileName.add("b.txt");
//		newFileName.add("c.txt");
//		try {
//			compressZip(file, path, newFileName);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		// try {
		// ZipTools.compressFloderChangeToZip("E:\\理财", "g:", "test.zip");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// try {
		// ZipTools.decompressFile("g:/附件.zip","g:/cc/");
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// System.out.println(e.getMessage());
		// }

	}
}
