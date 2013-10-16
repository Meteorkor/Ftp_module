import java.io.File;

import com.meteor.module.Ftp_Comander;


public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		File file;
		file = new File("C:/com/ftp");
		
		Ftp_Comander ftp;
		ftp = new Ftp_Comander(3);
		ftp.send_directory_FtpServer("xxxxx", 21
				, "test", "1", "q1", "C:/com/ftp", file.list());

		
	}

}
