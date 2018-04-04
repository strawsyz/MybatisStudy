package tk.mybatis.simple.mapper;

import java.util.List;

import tk.mybatis.simple.model.SysPrivilege;

public interface PrivilegeMapper {
	/***
	 * 根据roleID查找Privilege
	 * @param roleId
	 * @return
	 */
	List<SysPrivilege> selectPrivilegeByRoleId (Long roleId);
}
