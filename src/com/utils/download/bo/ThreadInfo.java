package com.utils.download.bo;

/**
 * 线程信息实体类
 * @author lpf
 *
 */
public class ThreadInfo {

	/** 线程ID **/
	private int id;

	/** 下载的url.和文件对应的url一致 **/
	private String url;

	/** 开始位置 **/
	private Integer start;

	/** 结束位置 **/
	private Integer end;

	/** 这段位置中下载了多少 **/
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
