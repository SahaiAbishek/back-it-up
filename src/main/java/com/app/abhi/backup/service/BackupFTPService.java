package com.app.abhi.backup.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BackupFTPService {
	
	@Value("${backup.ftp.hostname}")
	private String host;
	@Value("${backup.ftp.user}")
	private String user = "SahaiAbhi";
	@Value("${backup.ftp.password}")
	private String password = "Abhi2207";

	public boolean addFiles(String source, String destination) throws Exception {

		FTPClient ftpClient = new FTPClient();
//		FTPClientConfig config = new FTPClientConfig();
//		config.setServerTimeZoneId("");
		try {
			ftpClient.connect(host);
			ftpClient.enterLocalPassiveMode();
			ftpClient.login(user, password);
			ftpClient.changeWorkingDirectory(source);
			FTPFile[] files = ftpClient.listFiles("");
			System.out.println("Total files :" + files.length);
			for (FTPFile file : files) {
				System.out.println(file.getName());
				// copy file
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				InputStream inputStream = ftpClient.retrieveFileStream(file.getName());
				File targetFile = new File(file.getName());
				FileUtils.copyInputStreamToFile(inputStream, targetFile);
			}
		} catch (SocketException e) {
			e.printStackTrace();
			throw new Exception("Socket Exception");
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("IO exception");
		} finally {
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public static void main(String[] args) {

	}

}
