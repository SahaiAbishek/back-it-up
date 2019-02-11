package com.app.abhi.backup.controller;

import com.app.abhi.backup.service.BackupFTPService;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BackupController {

	@Autowired
	BackupFTPService service;

	@CrossOrigin
	@GetMapping(path = "/dir/{dirName}", produces = "application/json")
	public ResponseEntity<String> viewFolder(@PathVariable String dirName) throws Exception {
		return new ResponseEntity<>("Hello World", HttpStatus.OK);
	}

	@CrossOrigin
	@PostMapping(path = "/dir/{fromDir}_{toDir}", produces = "application/json")
	public ResponseEntity<Boolean> addFiles(@PathVariable String fromDir, @PathVariable String toDir) throws Exception {
		System.out.print("Inside addFiles");
		return new ResponseEntity<>(service.addFiles(fromDir, toDir), HttpStatus.OK);
	}

	@CrossOrigin
	@DeleteMapping(path = "/dir/", produces = "application/json")
	public ResponseEntity<String> deleteFiles(@PathVariable String dirName) throws Exception {
		return new ResponseEntity<>("Hello World", HttpStatus.OK);
	}
}
