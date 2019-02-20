package com.app.abhi.backup.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.app.abhi.backup.controller.BackupController;
import com.app.abhi.backup.dao.BackupRepo;
import com.app.abhi.backup.entity.FileEntity;
import com.app.abhi.backup.service.BackupFTPService;
import com.app.abhi.backup.service.BackupGoogleService;
import com.app.abhi.backup.service.MailService;

@Component
public class BackupSchedulingComponent {

	Logger logger = LoggerFactory.getLogger(BackupController.class);

	@Autowired
	private BackupFTPService service;

	@Autowired
	private BackupRepo repo;

	@Value("${backup.ftp.sourceDir}")
	private String sourceDir;

	@Value("${backup.ftp.destDir}")
	private String destDir;

	@Autowired
	private MailService mailService;

	@Autowired
	BackupGoogleService googleService;

	@Scheduled(cron = "0 0/1 * * * ?")
	public void scheduleBackup() {
		logger.info("Inside scheduleBackup");
		try {
			Set<String> files = service.viewFiles(sourceDir);
			Set<FileEntity> targetEntities = repo.findAll();
			if (targetEntities != null && targetEntities.size() > 0) {
				for (FileEntity fileEntity : targetEntities) {
					if (files.contains(fileEntity.getName())) {
						files.remove(fileEntity.getName());
					}
				}
			}
			if (files != null && files.size() > 0) {
				List<FileEntity> filesToSave = new ArrayList<>();
				logger.info("copying " + files.size() + " files.");
				for (String fileName : files) {
					// add file to local drive here
					service.addFiles(sourceDir, destDir, fileName);
					//add files to google drive
					googleService.addFiles(null, "1EMvxLfzgIbN5da73S9_qWFINXVo-kFVO", fileName);
					// save files in DB
					FileEntity entity = new FileEntity();
					entity.setName(fileName);
					entity.setCopied(true);
					filesToSave.add(entity);
				}
				// save copied files to DB
				repo.saveAll(filesToSave);
			} else {
				logger.info("No new files to copy");
			}
		} catch (Exception ex) {
			logger.error("Exception in copying files : " + ex.getStackTrace());
			mailService.sendMail("Exception in copying files : ", ex.getMessage());
		}
		logger.info("completed scheduleBackup");
	}
}
