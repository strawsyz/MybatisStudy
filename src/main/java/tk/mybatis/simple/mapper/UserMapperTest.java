package tk.mybatis.simple.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import tk.mybatis.simple.model.SysPrivilege;
import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

public class UserMapperTest extends BaseMapperTest{

	@Test 
	public void testSelectById(){
		SqlSession sqlSession = getSqlSession();
		try{
			//获取UserMapper接口
			UserMapper userMapper =sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectById(1L);
			Assert.assertNotNull(user);
			Assert.assertEquals("admin", user.getUserName());
		}finally{
			sqlSession.close();
		}
	}
	@Test
	public void testSelectAll(){
		SqlSession sqlSession = getSqlSession();
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysUser> userList = userMapper.selectAll();
			Assert.assertNotNull(userList);
			Assert.assertTrue(userList.size() >0);
		}finally{
			sqlSession.close();
		}
	}
	@Test
	public void testSelectRolesByUserId(){
		SqlSession sqlSession = getSqlSession();
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysRole> roleList = userMapper.selectRolesByUserId(1L);
			Assert.assertNotNull(roleList);
			Assert.assertTrue(roleList.size() >0);
		}finally{
			sqlSession.close();
		}
	}
	
	@Test
	public void testInsert(){
		SqlSession sqlSession = getSqlSession();
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setUserName("test1");
			user.setUserPassword("123456");
			user.setUserEmail("test1@straw.com");
			user.setUserInfo("test info");
			user.setHeadImg(new byte[]{1,2,3});
			user.setCreateTime(new Date());
			int result = userMapper.insert(user);
			Assert.assertEquals(1, result);
			Assert.assertNull(user.getId());
		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}
	
	@Test 
	public void testInsert2(){
		SqlSession sqlSession = getSqlSession();
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setUserName("test2");
			user.setUserPassword("123456");
			user.setUserEmail("test2@straw.com");
			user.setUserInfo("test2 info");
			user.setHeadImg(new byte[]{1,2,3});
			user.setCreateTime(new Date());
			int result = userMapper.insert2(user);
			Assert.assertEquals(1, result);
			System.out.println(user.getId());
			Assert.assertNotNull(user.getId());
		}finally {
			sqlSession.commit();
			sqlSession.close();
		}
	}
	@Test 
	public void testInsert3(){
		SqlSession sqlSession = getSqlSession();
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setUserName("test2");
			user.setUserPassword("123456");
			user.setUserEmail("test2@straw.com");
			user.setUserInfo("test2 info");
			user.setHeadImg(new byte[]{1,2,3});
			user.setCreateTime(new Date());
			int result = userMapper.insert3(user);
			Assert.assertEquals(1, result);
			System.out.println(user.getId());
			Assert.assertNotNull(user.getId());
		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}
	@Test
	public void testUpdateById(){
		SqlSession sqlSession = getSqlSession();
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectById(1L);
			Assert.assertEquals("admin", user.getUserName());
			user.setUserName("admin_test");
			user.setUserEmail("test@straw.com");
			int result = userMapper.updateById(user);
			Assert.assertEquals(1,result);
			user = userMapper.selectById(1L);
			Assert.assertEquals("admin_test",user.getUserName());
		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}
	
	@Test
	public void testDeleteById(){
		SqlSession sqlSession = getSqlSession();
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectById(1L);
			Assert.assertNotNull(user);
			Assert.assertEquals(1, userMapper.deleteById(1L));
			user = userMapper.selectById(1L);
			Assert.assertNull(user);
		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
		
	}

	@Test
	public void testSelectRolesByUserIdAndRoleEnabled(){
		SqlSession sqlSession = getSqlSession();
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysRole> userList = userMapper.selectRolesByUserIdAndRoleEnabled(1L,1);
			Assert.assertNotNull(userList);
			Assert.assertTrue(userList.size()>0);
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectByUser (){
		SqlSession sqlSession = getSqlSession();
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user  =new SysUser();
			user.setUserName("ad");
			List<SysUser> userList = userMapper.selectByUser(user);
			Assert.assertNotNull(userList);
			System.out.println(userList.size());
			Assert.assertTrue(userList.size()>0);
			user  =new SysUser();
			user.setUserEmail("test@haha.com");
			userList = userMapper.selectByUser(user);
			Assert.assertNotNull(userList);
			Assert.assertTrue(userList.size()>0);
			user  =new SysUser();
			user.setUserName("adm");
			user.setUserEmail("test@haha.com");
			userList = userMapper.selectByUser(user);
			//空list表不是Null
			Assert.assertFalse(userList.size()>0);
		}finally {
			sqlSession.close();
		}
	}
	//select * from table1 where 1=1与select * from table1完全没有区别
	@Test
	public void testSelectByUser2 (){
		SqlSession sqlSession = getSqlSession();
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user  =new SysUser();
			List<SysUser> userList = userMapper.selectByUser(user);
			Assert.assertNotNull(userList);
			System.out.println(userList.size());
			Assert.assertTrue(userList.size()>0);
		}finally {
			sqlSession.close();
		}
	}
	
	@Test 
	public void testUpdateByIdSelective(){
		SqlSession sqlSession = getSqlSession();
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user  =new SysUser();
			user.setId(1L);
			user.setUserEmail("test@haha.com");
			int result = userMapper.updateByIdSelective(user);
			Assert.assertEquals(1L, result);
			user = userMapper.selectById(1L);
			Assert.assertEquals("admin", user.getUserName());
			Assert.assertEquals("test@haha.com", user.getUserEmail());
		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectByIdOrUserName(){
		SqlSession sqlSession = getSqlSession();
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setId(1L);
			user.setUserName("admin");
			SysUser sysUser = userMapper.selectByIdOrUserName(user);
			Assert.assertNotNull(sysUser);
			user.setId(null);
			sysUser = userMapper.selectByIdOrUserName(user);
			Assert.assertNotNull(sysUser);
			user.setUserName(null);
			sysUser = userMapper.selectByIdOrUserName(user);
			Assert.assertNull(sysUser);
		}finally {
			sqlSession.rollback();
		}
	}
	
	@Test
	public void testSelectByUserWhere (){
		SqlSession sqlSession = getSqlSession();
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user  =new SysUser();
			user.setUserName("ad");
			List<SysUser> userList = userMapper.selectByUser2(user);
			Assert.assertNotNull(userList);
			System.out.println(userList.size());
			Assert.assertTrue(userList.size()>0);
			user  =new SysUser();
			user.setUserEmail("test@haha.com");
			userList = userMapper.selectByUser2(user);
			Assert.assertNotNull(userList);
			Assert.assertTrue(userList.size()>0);
			user  =new SysUser();
			user.setUserName("adm");
			user.setUserEmail("test@haha.com");
			userList = userMapper.selectByUser2(user);
			//空list表不是Null
			Assert.assertFalse(userList.size()>0);
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectByIdList(){
		SqlSession sqlSession = getSqlSession();
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<Long> idList = new ArrayList<Long>();
			idList.add(1L);
			idList.add(1001L);
			List<SysUser> userList = userMapper.selectByIdList(idList);
			Assert.assertNotNull(userList);
			Assert.assertEquals(2, userList.size());
		}finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testInsertList(){
		SqlSession sqlSession = getSqlSession();
		try{
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysUser> userList = new ArrayList<SysUser>();
			for (int i = 0 ;i<2;i++){
				SysUser sysUser = new SysUser();
				sysUser.setUserName("test"+i);
				sysUser.setUserPassword("123");
				sysUser.setUserEmail("test@hah.com");
				userList.add(sysUser);
			}
			int result = userMapper.insertList(userList);
			Assert.assertEquals(2, result);
		}finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}
	
	@Test 
	public void testSelectUserAndRoleById(){
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper= sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectUserAndRoleById(1001L);
			Assert.assertNotNull(user);
			Assert.assertNotNull(user.getRole());
		} finally {
			sqlSession.close();
		}
	}
	
	//使用了association标签
	@Test 
	public void testSelectUserAndRoleById2(){
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper= sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectUserAndRoleById2(1001L);
			Assert.assertNotNull(user);
			Assert.assertNotNull(user.getRole());
		} finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectUserAndRoleByIdSelect(){
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper =sqlSession.getMapper(UserMapper.class);
			SysUser sysUser = userMapper.selectUserAndRoleByIdSelect(1001L);
			Assert.assertNotNull(sysUser);
			System.out.println("调用user.equals(null)");
			sysUser.equals(null);
			System.out.println("调用user.getRole()");
			Assert.assertNotNull(sysUser.getRole());
		} finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectAllUserAndRoles(){
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			List<SysUser> userList = userMapper.selectAllUserAndRoles();
			System.out.println("用户数："+userList.size());
			for (SysUser sysUser : userList) {
				System.out.println("用户名："+sysUser.getUserName());
				for (SysRole role : sysUser.getRoleList()) {
					System.out.println("角色名："+role.getRoleName());
					for (SysPrivilege privilege : role.getPrivilegeList()) {
						System.out.println("权限名："+privilege.getPrivilegeName());
					}
				}
			}
		} finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectAllUserAndRolesSelect(){
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = userMapper.selectAllUserAndRolesSelect(1L);
			System.out.println("用户名："+user.getUserName());
			for(SysRole role :user.getRoleList()){
				System.out.println("角色名："+role.getRoleName());
				for(SysPrivilege privilege :role.getPrivilegeList()){
					System.out.println("权限名："+privilege.getPrivilegeName());
				}
			}
		} finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectUserId(){
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			SysUser user = new SysUser();
			user.setId(1L);
			userMapper.selectUserById(user);
			Assert.assertNotNull(user.getUserName());
			System.out.println("用户名："+user.getUserName());
		} finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectUserPage(){
		SqlSession sqlSession = getSqlSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("userName", "ad");
			params.put("offset", 0);
			params.put("limit", 10);
			List<SysUser> userList =userMapper.selectUserPage(params);
			Long total = (Long) params.get("total");
			System.out.println("总数："+total);
			for (SysUser sysUser : userList) {
				System.out.println("用户名："+sysUser.getUserName());
			}
		} finally {
			sqlSession.close();
		}
	}
}






















