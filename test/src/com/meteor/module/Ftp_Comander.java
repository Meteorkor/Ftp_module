package com.meteor.module;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;
import java.util.Calendar;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.meteor.logger.Logger;

/*
 * Version 0.2
 * Unseok Kim
*/
public class Ftp_Comander {

	
	Logger log;
	public Ftp_Comander(){
		log = new Logger();
	}
	
	int logging_type = 2;
	public Ftp_Comander(int type){
		logging_type = type;
		log = new Logger();
		
		
	}
	public void message_loggin(String tex){
		/**/
		StringBuffer prefix = new StringBuffer();
		
		prefix.append("[");
		prefix.append( Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) ;
		prefix.append(":");
		prefix.append( Calendar.getInstance().get(Calendar.MINUTE)) ;
		prefix.append(":");
		prefix.append( Calendar.getInstance().get(Calendar.SECOND)) ;
		prefix.append("]");
		tex = prefix + tex;
		
		switch(logging_type){
		case 0 : 
			break;
		case 1 : 
			log.write_log(tex);
			break;
		case 2 : 
			System.out.println(tex);//console 
			break;
		case 3 : 
			log.write_log(tex);
			System.out.println(tex);//console
			break;
		}
		
	}
	
	public Boolean send_file_FtpServer(String ip,int port
			,String id,String password,File file){
		
		FTPClient ftp = null;
		
		int reply_code;
		
		ftp = new FTPClient();
		
		
		try{
			
			ftp.connect(ip,port); // connect
			reply_code = ftp.getReplyCode();
			message_loggin("li");
			
			
			if(!FTPReply.isPositiveCompletion(reply_code)){
				ftp.disconnect();
				message_loggin("FTP Server Refused Connection");
				
				System.exit(1);
			}
			if(!ftp.login(id, password)){
				ftp.logout();
				message_loggin("Login Fail");
				System.exit(1);
			}
			
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();
			
			message_loggin(ftp.printWorkingDirectory());
			/*
			ftp.makeDirectory(folder);
			ftp.changeWorkingDirectory(folder);
			
			log.write_log(ftp.printWorkingDirectory());
			*/
		
		
			
			
			
			
		}catch(SocketException e){
			message_loggin(e.toString());
		}catch(IOException e){
			message_loggin(e.toString());
		}
		
		try {
			ftp.logout();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	public Boolean send_directory_FtpServer(String ip,int port
			,String id,String password,String folder,String localpath, String[] files){
		
		FTPClient ftp = null;
		
		int reply_code;
		
		ftp = new FTPClient();
		
		Boolean isSuccess = false;
		FileInputStream fis = null;
		try{
			
			ftp.connect(ip,port); // connect
			reply_code = ftp.getReplyCode();
			message_loggin("li");	
			if(!FTPReply.isPositiveCompletion(reply_code)){
				ftp.disconnect();
				message_loggin("FTP Server Refused Connection");
				System.exit(1);
			}
			
			ftp.setSoTimeout(10000);
			
			if(!ftp.login(id, password)){
				ftp.logout();
				message_loggin("Login Fail");
				System.exit(1);
			}
			
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();
			
			message_loggin(ftp.printWorkingDirectory());
			/*
			ftp.makeDirectory(folder);
			ftp.changeWorkingDirectory(folder);
			
			log.write_log(ftp.printWorkingDirectory());
			*/
		
		
			for(int i=0; i< files.length;i++){
				String tempFileName = new String(files[i].getBytes("utf-8"),"iso_8859_1");
				String sourceFile = localpath +"/" +files[i];
				File uploadFile = new File(sourceFile);
				
				
				fis = new FileInputStream(uploadFile);
				message_loggin(sourceFile + " : 전송 시작");
				
				isSuccess = ftp.storeFile(tempFileName, fis);
				
				message_loggin(sourceFile + " : 전송 결과 " + isSuccess);
				
			}
			
			
			
		}catch(SocketException e){
			message_loggin(e.toString());
		}catch(IOException e){
			message_loggin(e.toString());
			isSuccess = false;
		}finally{
			if(fis !=null){
				try{fis.close();
				}
				catch(IOException e){
					message_loggin(e.toString());	
				}
			}
		}
		
		try {
			ftp.logout();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return isSuccess;
	}
	
	public void send(){
	
	
	}
}
