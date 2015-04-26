package com.utils.download.bo;

/**
 * �߳���Ϣʵ����
 * @author lpf
 *
 */
public class ThreadInfo {

	/** �߳�ID **/
	private int id;

	/** ���ص�url.���ļ���Ӧ��urlһ�� **/
	private String url;

	/** ��ʼλ�� **/
	private Integer start;

	/** ����λ�� **/
	private Integer end;

	/** ���λ���������˶��� **/
	private Integer finished;

	public ThreadInfo() {
		super();
	}

	public ThreadInfo(int id, String url, Integer start, Integer end, Integer finished) {
		super();
		this.id = id;
		this.url = url;
		this.start = start;
		this.end = end;
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

	public int getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public int getFinished() {
		return finished;
	}

	public void setFinished(Integer finished) {
		this.finished = finished;
	}

	@Override
	public String toString() {
		return "ThreadInfo [id=" + id + ", url=" + url + ", start=" + start
				+ ", end=" + end + ", finished=" + finished + "]";
	}

}
