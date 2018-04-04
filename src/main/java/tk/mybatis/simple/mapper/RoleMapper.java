package tk.mybatis.simple.mapper;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.session.RowBounds;

import tk.mybatis.simple.model.SysRole;

@CacheNamespaceRef(RoleMapper.class)
public interface RoleMapper {
	
	@Select({"select id ,role_name roleName,enabled,create_by createBy ,create_time createTime from sys_role where id = #{id}"})
	SysRole selectById(Long id);

	@Results(value={
			@Result(property = "id" ,column = "id",id=true),
			@Result(property = "roleName" , column = "role_name" ),
			@Result(property = "enabled" , column = "enabled" ),
			@Result(property = "createBy" , column = "create_by" ),
			@Result(property = "createTime" , column = "create_time" ),
	})
	@Select({"select id ,role_name,enabled,create_by,create_time from sys_role where id = #{id}"})
	SysRole selectById2(Long id);
	
//	MyBatis3.3.1之后Results注解中才有id属性可以用
	@Results(value={
			@Result(property = "id" ,column = "id",id=true),
			@Result(property = "roleName" , column = "role_name" ),
			@Result(property = "enabled" , column = "enabled" ),
			@Result(property = "createBy" , column = "create_by" ),
			@Result(property = "createTime" , column = "create_time" ),
	})
	@Select({"select * from sys_role"})
	List<SysRole> selectAll();
	
	/***
	 * @param rowBounds
	 * 测试分页插件时，新增加的方法
	 * @return
	 */
	@Results(value={
			@Result(property = "id" ,column = "id",id=true),
			@Result(property = "roleName" , column = "role_name" ),
			@Result(property = "enabled" , column = "enabled" ),
			@Result(property = "createBy" , column = "create_by" ),
			@Result(property = "createTime" , column = "create_time" ),
	})
	@Select({"select * from sys_role"})
	List<SysRole> selectAll(RowBounds rowBounds);
	
	@Insert({"insert into sys_role(id,role_name,enabled,create_by,create_time)values(#{id},#{roleName},#{enabled},#{createBy},#{createTime,jdbcType=DATE})"})
	int insert(SysRole sysRole);
	

	@Insert({"insert into sys_role(role_name,enabled,create_by,create_time)values(#{roleName},#{enabled},#{createBy},#{createTime,jdbcType=DATE})"})
	@Options(useGeneratedKeys = true , keyProperty="id")
	int insert2(SysRole sysRole);

	
	@Insert({"insert into sys_role(role_name,enabled,create_by,create_time)values(#{roleName},#{enabled},#{createBy},#{createTime,jdbcType=DATE})"})
	@SelectKey(statement="SELECT LAST_INSERT_ID()",
			keyProperty="id",before=false,resultType=Long.class)
	int insert3(SysRole sysRole);
	/***
	 * 根据角色根性
	 * @param role
	 * @return
	 */
	int updateById(SysRole sysRole	);
	
	List<SysRole> selectAllRoleAndPrivileges();
	/***
	 * 根据用户ID获取用户的角色信息
	 * @param userId
	 * @return
	 */
	List<SysRole> selectRoleByUserIdChoose(Long userId);
}
