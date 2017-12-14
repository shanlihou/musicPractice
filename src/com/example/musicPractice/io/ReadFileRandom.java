/**
 * Copyright (C) 2009 Android OS Community Inc (http://androidos.cc/bbs).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.musicPractice.io;

import android.util.Log;

import java.io.*;

/**
 * This class for read a file
 * 
 * @author SinFrancis
 * @version 1.0
 * 
 */
public class ReadFileRandom {
	//private RandomAccessFile randomAccessFile = null;
	private InputStream dataInputStream = null;
	private String filePath = null;
	public ReadFileRandom(String path) {
		String tag = "ReadFileRandom";
		this.filePath = path;
		Log.d("shanlihou", "path is:" + filePath);
		try {
		//	randomAccessFile = new RandomAccessFile(filePath,"rw");
			dataInputStream = new DataInputStream(new FileInputStream(filePath));
			Log.d("shanlihou", dataInputStream + "");
		} catch (FileNotFoundException e) {
			Log.d(tag, "Exception :"+e.getMessage());
		}
	}
	
	/**
	 * ���µ������
	 */
	public void openNewStream(){
		close();
		try {
			//randomAccessFile = new RandomAccessFile(filePath,"rw");
			dataInputStream = new DataInputStream(new FileInputStream(filePath));
		} catch (FileNotFoundException e) {
		}
	}
	/**
	 * ��ȡ����
	 * @param length ��ȡ�೤
	 * @return
	 */
	public byte[] readBytes(int length){
		byte[] b = new byte[length];
		try {
			//randomAccessFile.read(b);
			if(dataInputStream == null){
				dataInputStream = new DataInputStream(new FileInputStream(filePath));
			}
			dataInputStream.read(b);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return b;
	}
	
	
	/**
	 * ��ȡ���ݣ��������ݼ��ص��ֽ�������
	 * @param buffer
	 * @return ����ʵ�ʶ�ȡ���ֽ���
	 */
	public int readBytes(byte[] buffer){
		int i = 0;
		try {
			i= dataInputStream.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}	
		return i;
	}
	
	
	/**
	 * �����ֽ�
	 * @param length ��������
	 */
	public void skip(int length){
		try {
			//dataInputStream.skipBytes(length);
			dataInputStream.skip(length);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��������
	 * @param length ��������
	 */
	public void fastSkip(int length ){
		readBytes(length);
	}
	
	
	
	/**
	 * ���ٶ�Ϊ���ļ��е�λ��
	 * @param location ��Ϊ�ĵص�
	 */
	public void locate(int location){
		readBytes(location);
	}

	/**
	 * �õ��ļ��ĵ�ǰλ��
	 * @return
	 */
	/*public long getCurrentLocation(){
		long i = 0;
		 try {
			i = randomAccessFile.getFilePointer();
		} catch (IOException e) {
			 return i;
		}
		 return i;
	}*/
	/**
	 * ȡ���ļ��ĳ���
	 * @return
	 */
	public long getFileLength(){
		long i =0;
		try {
			i= new File(filePath).length();
		} catch (Exception e) {
		}
		return i;
	}
	
	
	/**
	 * �ر���
	 */
	public void close(){
		if(null!=dataInputStream)
			try {
				dataInputStream.close();
			} catch (IOException e) {
			}	
	}

	
	public static void main(String[] args) {
		
		ReadFileRandom r = new ReadFileRandom("src/mayun.txt");
		byte[] b = new byte[10]; 
		r.readBytes(b);
		System.err.println(new String(b));
		r.skip(10);
		b = r.readBytes(21);
		System.out.println("=================");
		System.err.println(new String(b));
		r.close();
	}
	
}
