package tk.mybatis.simple.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

public interface UserMapper {
	/***
	 * 通过id查找用户
	 * @param id
	 * @return
	 */
	SysUser selectById(Long id );
	
	/***
	 * 查询全部用户
	 * @return
	 */
	List<SysUser> selectAll();
	
	/***
	 * 根据用户的id获取角色信息
	 * @param userId
	 * @return
	 */
	List<SysRole> selectRolesByUserId(Long userId);
	
	/***
	 * 增加用户
	 * @param sysUser
	 * @return
	 */
	int insert(SysUser sysUser);
	
	/***
	 * 新增用户-使用useGenerateedKeys方式
	 * @param sysUser
	 * @return
	 */
	int insert2(SysUser sysUser);
	
	/**
	 * 增加用户-使用selectKey方式
	 * @param sysUser
	 * @return
	 */
	int insert3 (SysUser sysUser);
	
	/***
	 * 根据主键更新
	 * @param sysUser
	 * @return
	 */
	int updateById(SysUser sysUser);
	
	/***
	 * 通过主键删除
	 * @param id
	 * @return
	 */
	int deleteById(Long id);
	/***
	 * 根据用户的id和角色的enabled状态获取用户的角色
	 * @param userId
	 * @param enabled
	 * @return
	 */
	List<SysRole> selectRolesByUserIdAndRoleEnabled(@Param("userId")Long userId,@Param("enabled")Integer enabled);
	
	/***
	 * 根据动态条件查询用户信息
	 * @param user
	 * @return
	 */
	List<SysUser> selectByUser(SysUser user);
	
	/***
	 * 根据主键更新
	 * @param sysUser
	 * @return
	 */
	int updateByIdSelective(SysUser sysUser);
	
	/***
	 * 根据用户的id或者用户名查找
	 * @param sysUser
	 * @return
	 */
	SysUser selectByIdOrUserName(SysUser sysUser);
	/***
	 * 根据动态条件查询用户信息
	 * @param user
	 * @return
	 */
	List<SysUser> selectByUser2(SysUser user);
	/***
	 * 根据用户的id集合查询
	 * @param idList
	 * @return
	 */
	List<SysUser> selectByIdList(List<Long> idList);
	/***
	 * 批量插入用户信息
	 * @param userList
	 * @return
	 */
	int insertList(List<SysUser> userList);
	
	/***
	 * 根据id求出查询用户和角色
	 * @param id
	 * @return
	 */
	SysUser selectUserAndRoleById(Long id);
	
	/***
	 * 根据id求出查询用户和角色
	 * @param id
	 * @return
	 */
	SysUser selectUserAndRoleById2(Long id);

	SysUser selectUserAndRoleByIdSelect(long id);
	
	/***
	 * 获取所有的用户以及对应的所有角色
	 * @return
	 */
	List<SysUser> selectAllUserAndRoles();
	
	/***
	 * 通过嵌套查询获取指定用户的信息以及用户的角色和权限信息
	 * @param id
	 * @return
	 */
	SysUser selectAllUserAndRolesSelect(Long id);
	/***
	 * 使用存储过程查询用户信息
	 * @param user
	 */
	void selectUserById(SysUser user);
	/***
	 * 使用存储过程分页查询
	 * @param params
	 * @return
	 */
	List<SysUser> selectUserPage(Map<String,Object> params);
	
}






