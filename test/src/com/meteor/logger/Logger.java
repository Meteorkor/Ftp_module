package com.meteor.logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Logger {

	File log_file;
	String file_name;
	FileInputStream fis;
	FileOutputStream fos;
	
	public Logger(){
		StringBuffer sb = new StringBuffer();
	
		sb.append("log");
		sb.append(Calendar.getInstance().get(Calendar.YEAR));
		sb.append(Calendar.getInstance().get(Calendar.MONTH)+1);
		sb.append(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		sb.append(".txt");
	
		file_name = sb.toString();
		
	}
	
	public String get_file_name(){
		return file_name;
	}
	
	public boolean create_file(){
		log_file = new File(file_name);
		
		if(log_file.exists()){
			return false;
			//파일 존재
		}else{
			try {
				return log_file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		
	}
	
	//public boolean write_log(byte[] b){
	public boolean write_log(String b){
		try {
			if(this.create_file()){
				fos = new FileOutputStream(log_file);
				
				fos.write(b.getBytes());
				fos.close();
			
				
			}else{
				fis = new FileInputStream(log_file);
				
				byte[] old_data = new byte[(int)(log_file.length())];
				//byte[] old_data = new byte[1024];
				//System.out.println( (int)(log_file.length()) );
				while( fis.read(old_data)!=-1&&(int)(log_file.length())>0 ){
				}//파일의 크기가 0 초과의 경우만 read
				fis.close();
				
				fos = new FileOutputStream(log_file);
				fos.write(old_data);
				fos.write("\n--\n".getBytes());//이전데이터와 \n 분리
				fos.write(b.getBytes());
				
				fos.close();
				
			}
			
			return true;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
		
		
		
	}
	
	
}
