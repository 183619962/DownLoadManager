package com.utils.download.bo;

import java.io.Serializable;

/**
 * 文件信息实体类
 * 
 * @author lpf
 * 
 */
public class FileInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 文件ID **/
	private int id;

	/** 文件的url **/
	private String url;

	/** 文件名 **/
	private String fileName;

	/** 文件长度 **/
	private Integer length;

	/** 文件已经完成的进度 **/
	private Integer finished;

	public FileInfo() {
		super();
	}

	public FileInfo(int id, String url, String fileName, Integer length,
			Integer finished) {
		super();
		this.id = id;
		this.url = url;
		this.fileName = fileName;
		this.length = length;
		this.finished = finished;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public int getFinished() {
		return finished;
	}

	public void setFinished(Integer finished) {
		this.finished = finished;
	}

	@Override
	public String toString() {
		return "FileInfo [id=" + id + ", url=" + url + ", fileName=" + fileName
				+ ", length=" + length + ", finished=" + finished + "]";
	}

}
