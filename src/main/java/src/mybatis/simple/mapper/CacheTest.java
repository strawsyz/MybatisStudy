package src.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import tk.mybatis.simple.mapper.BaseMapperTest;
import tk.mybatis.simple.mapper.RoleMapper;
import tk.mybatis.simple.mapper.UserMapper;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

public class CacheTest extends BaseMapperTest{
	
	@Test
	public void testL1Cache(){
		SqlSession sqlSession = getSqlSession();
		SysUser user1 = null;
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			user1 = userMapper.selectById(1L);
			user1.setUserName("New Name");
			SysUser user2 = userMapper.selectById(1L);
			Assert.assertEquals("New Name", user2.getUserName());
			Assert.assertEquals(user1, user2);
			
		} finally {
			sqlSession.close();
		}
		System.out.println("开始新的sqlSession");
		//开始新的session
		sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user2 = userMapper.selectById(1L);
			Assert.assertNotEquals("New Name", user2.getUserName());
			Assert.assertNotEquals(user1, user2);
			userMapper.deleteById(2L);
			SysUser user3 = userMapper.selectById(1L);
			Assert.assertNotEquals(user2, user3);
		} finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void test2Cache(){
		SqlSession sqlSession = getSqlSession();
		SysRole role1 = null;
		try {
			RoleMapper roleMapper= sqlSession.getMapper(RoleMapper.class);
			role1 = roleMapper.selectById(1l);
			role1.setRoleName("New Name");
			SysRole role2 =roleMapper.selectById(1l);
			Assert.assertEquals("New Name", role2.getRoleName());
			Assert.assertEquals(role1, role2);
		} finally {
			sqlSession.close();
		}
		
		System.out.println("开启新的sqlSession");
		sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole role2 = roleMapper.selectById(1l);
			Assert.assertEquals("New Name", role2.getRoleName());
			Assert.assertNotEquals(role1, role2);
			SysRole role3 = roleMapper.selectById(1l);
			Assert.assertNotEquals(role2, role3);
		} finally {
			sqlSession.close();
		}
	}
	
	
	@Test
	public void testDirtyData(){
		SqlSession sqlSession= getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user= userMapper.selectUserAndRoleById(1001L);
			Assert.assertEquals("普通用户", user.getRole().getRoleName());
			System.out.println("角色名："+user.getRole().getRoleName());
		} finally {
			sqlSession.close();
		}
		//开始新的sqlSession
		sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole role = roleMapper.selectById(2L);
			role.setRoleName("脏数据");
			roleMapper.updateById(role);
			//提交修改
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
		//开始新的sqlsession
		sqlSession =getSqlSession();
		try {
			UserMapper userMapper= sqlSession.getMapper(UserMapper.class);
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysUser user = userMapper.selectUserAndRoleByIdSelect(1001L);
			SysRole role = roleMapper.selectById(2L);
			Assert.assertEquals("普通用户", user.getRole().getRoleName());
			Assert.assertEquals("脏数据", role.getRoleName());
			System.out.println("角色名："+user.getRole().getRoleName());
			//还原数据
			role.setRoleName("普通用户");
			roleMapper.updateById(role);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}
}
