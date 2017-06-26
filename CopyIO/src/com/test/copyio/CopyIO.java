package com.test.copyio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 复制文件夹 熟悉 isFile isDirectiory getName getPath getParent exists
 * 
 * @author Administrator
 *
 */
public class CopyIO {
	private static String inputFile = "G:/1";
	private static String outputFile = "G:/2";

	public static void main(String[] args) {
		copyDir(inputFile,outputFile);
	}
	
	/**
	 * 复制文件
	 * @param src
	 * @param desc
	 */
	public static void copyFile(File src,File desc){
		InputStream is =null;
		OutputStream os = null;
		try {
			is = new BufferedInputStream(new FileInputStream(src));
			os  = new BufferedOutputStream(new FileOutputStream(desc));

			byte[] info = new byte[1024];
			int len = 0;
			while(-1!=(len=is.read(info))){
				os.write(info,0,len);
			}
			os.flush();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				os.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	//复制文件夹细节
	public static void copyDirDex(File srcPath,File descPath){
		if(srcPath.isFile()){
			copyFile(srcPath,descPath);
		}else if(srcPath.isDirectory()){
			descPath.mkdirs();
			for(File sub:srcPath.listFiles()){
				copyDirDex(sub,new File(descPath,sub.getName()));
			}
		}
	}

	//复制文件夹
	public static void copyDir(File src,File desc){
		//如果源路径是文件夹，则原路径的文件名放在目标文件的下一级（文件夹中）
		if(src.isDirectory()){
			desc = new File(desc,src.getName());
			
			if(desc.getAbsolutePath().contains(src.getAbsolutePath())){
				return;
			}
		}
		copyDirDex(src,desc);
	}
	public static void copyDir(String srcPath,String descPath){
		//文件夹不能复制给自己，所以原路径与目标路径不能相同。
		if(srcPath.equals(descPath)){
			return;
		}
		copyDir(new File(srcPath),new File(descPath));
	}


}
