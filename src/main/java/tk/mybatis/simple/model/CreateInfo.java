package tk.mybatis.simple.model;

import java.util.Date;

public class CreateInfo {
	private String createBy;
	private Date createTime;
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
