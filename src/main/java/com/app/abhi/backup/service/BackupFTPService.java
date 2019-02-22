package com.app.abhi.backup.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Future;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

@Service
public class BackupFTPService {

	Logger logger = LoggerFactory.getLogger(BackupFTPService.class);

	@Value("${backup.ftp.hostname}")
	private String host;
	@Value("${backup.ftp.user}")
	private String user;
	@Value("${backup.ftp.password}")
	private String password;
	@Value("${local.folder}")
	private String localFolder;

	@Async("asyncExecutor")
	public Future<Boolean> addFiles(String source, String destination, String fileName) throws Exception {

		logger.debug("Inside addFiles");

		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(host.trim());
			ftpClient.enterLocalPassiveMode();
			ftpClient.login(user, password);
			ftpClient.changeWorkingDirectory(source);
			InputStream inputStream = ftpClient.retrieveFileStream(fileName);
			destination = destination == null ? localFolder:"/"+destination+"/";
			File targetFile = new File(destination + fileName);
			FileUtils.copyInputStreamToFile(inputStream, targetFile);
			logger.debug("files copied successfully");
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
		logger.debug("returning true");
		return new AsyncResult<Boolean>(true);
	}

	public Set<String> viewFiles(String sourceDir) throws Exception {
		logger.debug("Inside viewFiles");
		Set<String> filesInDir = new HashSet<>();

		FTPClient ftpClient = new FTPClient();
		// FTPClientConfig config = new FTPClientConfig();
		// config.setServerTimeZoneId("");
		try {
			ftpClient.connect(host.trim());
			ftpClient.enterLocalPassiveMode();
			ftpClient.login(user, password);
			ftpClient.changeWorkingDirectory(sourceDir);
			FTPFile[] files = ftpClient.listFiles("");
			logger.debug("Total files :" + files.length);
			for (FTPFile file : files) {
				filesInDir.add(file.getName());
			}
			return filesInDir;
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

	}

}
