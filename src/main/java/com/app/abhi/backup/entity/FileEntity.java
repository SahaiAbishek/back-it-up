package com.app.abhi.backup.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BACKUP_FILES")
public class FileEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "FILE_NAME")
	private String name;
	
	@Column(name = "IS_COPIED")
	private boolean isCopied;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCopied() {
		return isCopied;
	}

	public void setCopied(boolean isCopied) {
		this.isCopied = isCopied;
	}

}
