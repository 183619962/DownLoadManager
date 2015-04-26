package com.utils.download.bo;

import java.io.Serializable;

/**
 * �ļ���Ϣʵ����
 * 
 * @author lpf
 * 
 */
public class FileInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** �ļ�ID **/
	private int id;

	/** �ļ���url **/
	private String url;

	/** �ļ��� **/
	private String fileName;

	/** �ļ����� **/
	private Integer length;

	/** �ļ��Ѿ���ɵĽ��� **/
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
