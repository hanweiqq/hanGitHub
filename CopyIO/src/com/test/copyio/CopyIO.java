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
 * �����ļ��� ��Ϥ isFile isDirectiory getName getPath getParent exists
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
	 * �����ļ�
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

	//�����ļ���ϸ��
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

	//�����ļ���
	public static void copyDir(File src,File desc){
		//���Դ·�����ļ��У���ԭ·�����ļ�������Ŀ���ļ�����һ�����ļ����У�
		if(src.isDirectory()){
			desc = new File(desc,src.getName());
			
			if(desc.getAbsolutePath().contains(src.getAbsolutePath())){
				return;
			}
		}
		copyDirDex(src,desc);
	}
	public static void copyDir(String srcPath,String descPath){
		//�ļ��в��ܸ��Ƹ��Լ�������ԭ·����Ŀ��·��������ͬ��
		if(srcPath.equals(descPath)){
			return;
		}
		copyDir(new File(srcPath),new File(descPath));
	}


}
