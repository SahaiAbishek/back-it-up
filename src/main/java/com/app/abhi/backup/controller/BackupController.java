package com.app.abhi.backup.controller;

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

   
    @CrossOrigin
    @GetMapping(path="/dir/{dirName}", produces = "application/json")
    public ResponseEntity<String> viewFolder(@PathVariable String dirName) throws Exception {
        return new ResponseEntity<>("Hello World",HttpStatus.OK);
    }
    
    @CrossOrigin
    @PostMapping(path="/dir/", produces = "application/json")
    public ResponseEntity<String> addFiles(@PathVariable String dirName) throws Exception {
        return new ResponseEntity<>("Hello World",HttpStatus.OK);
    }
    
    @CrossOrigin
    @DeleteMapping(path="/dir/", produces = "application/json")
    public ResponseEntity<String> deleteFiles(@PathVariable String dirName) throws Exception {
        return new ResponseEntity<>("Hello World",HttpStatus.OK);
    }
}
