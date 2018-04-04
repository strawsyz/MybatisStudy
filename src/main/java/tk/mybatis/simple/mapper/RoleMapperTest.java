package tk.mybatis.simple.mapper;


import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import tk.mybatis.simple.model.SysPrivilege;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.plugin.PageRowBounds;
import tk.mybatis.simple.type.Enabled;

public class RoleMapperTest extends BaseMapperTest{
	
	@Test
	public void testSelectById(){
		SqlSession sqlSession = getSqlSession();
		try{
		RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
		SysRole role = roleMapper.selectById(1L);
		Assert.assertNotNull(role);
		Assert.assertEquals("管理员", role.getRoleName());
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectById2(){
		SqlSession sqlSession = getSqlSession();
		try{
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole role = roleMapper.selectById2(1L);
			Assert.assertNotNull(role);
			Assert.assertEquals("管理员", role.getRoleName());
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectAll(){
		SqlSession sqlSession = getSqlSession();
		try{
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			List<SysRole> roleList = roleMapper.selectAll();
			Assert.assertNotNull(roleList);
			Assert.assertTrue(roleList.size()>0);
			Assert.assertEquals("管理员", roleList.get(0).getRoleName());
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectAllByRowBounds(){
		SqlSession sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			RowBounds rowBounds = new RowBounds(0,1);
			List<SysRole> list = roleMapper.selectAll(rowBounds);
			for (SysRole sysRole : list) {
				System.out.println("角色名："+sysRole.getRoleName());
			}
			//使用PageRowBounds时会查询总数
			PageRowBounds pageRowBounds = new PageRowBounds();
			list = roleMapper.selectAll(pageRowBounds);
			//获取总数
			System.out.println("查询总数："+ pageRowBounds.getTotal());
			for (SysRole sysRole : list) {
				System.out.println("角色名："+sysRole.getRoleName());
			}
			//再次查询第二个角色
			pageRowBounds = new PageRowBounds(1,1);
			list = roleMapper.selectAll(pageRowBounds);
			//获取总数
			System.out.println("查询总数："+pageRowBounds.getTotal());
			for (SysRole sysRole : list) {
				System.out.println("角色名："+sysRole.getRoleName());
			}
		} finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testInsert(){
		SqlSession sqlSession = getSqlSession();
		try{
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole sysRole = new SysRole();
			sysRole.setId(5l);
			sysRole.setRoleName("haha");
//			sysRole.setCreateBy(1L);
//			sysRole.setCreateTime(new Date());
			sysRole.setEnabled(1);
			int result = roleMapper.insert(sysRole);
			Assert.assertEquals(1, result);
			SysRole role = roleMapper.selectById(5L);
			Assert.assertNotNull(role);
			Assert.assertEquals("haha", role.getRoleName());
		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}
	
	@Test
	public void testInser2(){
		SqlSession sqlSession = getSqlSession();
		try{
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole sysRole = new SysRole();
			sysRole.setRoleName("haha");
//			sysRole.setCreateBy(1L);
//			sysRole.setCreateTime(new Date());
			sysRole.setEnabled(1);
			int result = roleMapper.insert2(sysRole);
			System.out.println(sysRole.getId());
			Assert.assertEquals(1, result);
			Assert.assertEquals("haha", sysRole.getRoleName());
		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}
	
	@Test
	public void testInser3(){
		SqlSession sqlSession = getSqlSession();
		try{
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole sysRole = new SysRole();
			sysRole.setRoleName("haha");
//			sysRole.setCreateBy(1L);
//			sysRole.setCreateTime(new Date());
			sysRole.setEnabled(1);
			int result = roleMapper.insert3(sysRole);
			System.out.println(sysRole.getId());
			Assert.assertEquals(1, result);
			Assert.assertEquals("haha", sysRole.getRoleName());
		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectAllRoleAndPrivileges(){
		SqlSession sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			List<SysRole> roleList = roleMapper.selectAllRoleAndPrivileges();
			System.out.println("角色数："+roleList.size());
			for (SysRole sysRole : roleList) {
				System.out.println("角色名："+ sysRole.getRoleName());
				for (SysPrivilege sysPrivilege : sysRole.getPrivilegeList()) {
					System.out.println("权限名："+sysPrivilege.getPrivilegeName());
				}
			}
		} finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectRoleByUserIdChoose(){
		SqlSession sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper =sqlSession.getMapper(RoleMapper.class);
			SysRole sysRole = roleMapper.selectById(2L);
			sysRole.setEnabled(0);
			roleMapper.updateById(sysRole);
			List<SysRole> roleList = roleMapper.selectRoleByUserIdChoose(1L);
			for (SysRole role : roleList) {
				System.out.println("角色名：" +role.getRoleName());
				if(role.getId().equals(1L)){
					Assert.assertNotNull(role.getPrivilegeList());
				}else if(role.getId().equals(2L)){
					Assert.assertNull(role.getPrivilegeList());
					continue;
				}
				for (SysPrivilege sysPrivilege : role.getPrivilegeList()) {
					System.out.println("权限名："+sysPrivilege.getPrivilegeName());
				}
			}
		} finally { 
			sqlSession.close();
		}
	}
	
	@Test
	public void testUpdateById(){
		SqlSession sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole role = roleMapper.selectById(2L);
			Assert.assertEquals(Enabled.enabled, role.getEnabled());
//			role.setEnabled(Enabled.disabled);
			roleMapper.updateById(role);
			role = roleMapper.selectById(2L);
			Assert.assertEquals(Enabled.disabled, role.getEnabled());
		} finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}
	
	
}
