package com.app.abhi.backup.controller;

import java.util.Set;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.abhi.backup.service.BackupFTPService;

import io.swagger.annotations.ApiOperation;

@RestController
public class BackupController {

	Logger logger = LoggerFactory.getLogger(BackupController.class);

	@Autowired
	BackupFTPService service;

	@CrossOrigin
	@GetMapping(path = "/dir/{dirName}", produces = "application/json")
	@ApiOperation(value = "View all files in a directory", nickname = "View all files in a directory")
	public ResponseEntity<Set<String>> viewFiles(@PathVariable String dirName) throws Exception {
		logger.debug("Inside viewFiles");
		return new ResponseEntity<>(service.viewFiles(dirName), HttpStatus.OK);
	}

	@CrossOrigin
	@PostMapping(path = "/dir/{fromDir}_{toDir}", produces = "application/json")
	@ApiOperation(value = "Add files from source to destination", nickname = "Add files from source to destination")
	public ResponseEntity<Boolean> addFiles(@PathVariable String fromDir, @RequestParam(required=false) String toDir) throws Exception {
		logger.debug("Inside addFiles");
		Future<Boolean> isSuccessful = new AsyncResult<Boolean>(false);
		for (String fileName : service.viewFiles(fromDir)) {
			isSuccessful = service.addFiles(fromDir, toDir, fileName);
		}
		if (isSuccessful.get()) {
			logger.debug("files added to :" + toDir);
		} else {
			logger.error("Files not copied");
		}
		return new ResponseEntity<>(isSuccessful.get(), HttpStatus.OK);
	}

	@CrossOrigin
	@DeleteMapping(path = "/dir/", produces = "application/json")
	@ApiOperation(value = "NOT IMPLMENTED : No one should have this much Power ", nickname = "NOT IMPLMENTED : No one should have this much Power ")
	public ResponseEntity<String> deleteFiles(@PathVariable String dirName) throws Exception {
		logger.debug("Inside delete files");
		return new ResponseEntity<>("TO delete or not to delete is the question ?", HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping(path = "/dir/file/{fileName}", produces = "application/json")
	@ApiOperation(value = "Find a file by name", nickname = "Find a file by name")
	public ResponseEntity<String> findFile(@PathVariable String fileName) throws Exception {
		logger.debug("Inside delete files");
		return new ResponseEntity<>("TO delete or not to delete is the question ?", HttpStatus.OK);
	}
}
