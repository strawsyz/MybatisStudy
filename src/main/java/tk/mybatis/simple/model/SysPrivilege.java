package tk.mybatis.simple.model;

public class SysPrivilege {
	private Long id;
	private String PrivilegeName ;
	private String PrivilegeUrl;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPrivilegeName() {
		return PrivilegeName;
	}
	public void setPrivilegeName(String privilegeName) {
		PrivilegeName = privilegeName;
	}
	public String getPrivilegeUrl() {
		return PrivilegeUrl;
	}
	public void setPrivilegeUrl(String privilegeUrl) {
		PrivilegeUrl = privilegeUrl;
	}
	
}
