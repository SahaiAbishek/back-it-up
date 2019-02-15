package com.app.abhi.backup.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.app.abhi.backup.entity.FileEntity;

@Repository
public interface BackupRepo extends CrudRepository<FileEntity, Long> {
	
	boolean save(List<FileEntity> files);
	Set<FileEntity> findAll();
}
