package tk.mybatis.simple.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import tk.mybatis.simple.model.SysPrivilege;

public class PrivilegeMapperTest extends BaseMapperTest{
	@Test
	public void testSelectPrivilegeByRoleId(){
		SqlSession sqlSession = getSqlSession();
		try {
			PrivilegeMapper privilegeMapper = sqlSession.getMapper(PrivilegeMapper.class);
			List<SysPrivilege> privilegeList = privilegeMapper.selectPrivilegeByRoleId(1L);
			for (SysPrivilege sysPrivilege : privilegeList) {
				System.out.println("权限名:"+sysPrivilege.getPrivilegeName());
			}
		} finally {
			sqlSession.close();
		}
	}
}
