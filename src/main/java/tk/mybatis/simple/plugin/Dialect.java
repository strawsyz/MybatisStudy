package tk.mybatis.simple.plugin;

import java.util.List;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.RowBounds;

/***
 * 数据库方言，
 * 针对不同数据库实现
 * @author SYZPC
 *
 */
@SuppressWarnings("rawtypes")
public interface Dialect {
	/***
	 * 跳过count和分页查询
	 * 
	 * @param msId
	 * @param paramenterObject
	 * @param rowBounds
	 * @return
	 */
	boolean skip(String msId,Object paramenterObject,RowBounds rowBounds);
	
	
	/***
	 * 执行分页前，返回true会进行count查询，返回false就会继续下面的beforePage判断
	 * 
	 * @param msId
	 * @param paramenterObject
	 * @param rowBounds
	 * @return
	 */
	boolean beforeCount(String msId ,Object paramenterObject, RowBounds rowBounds );
	
	/***
	 * 生成count查询sql
	 * 
	 * @param boundSql
	 * @param paramentObject
	 * @param rowBounds
	 * @param countKey
	 * @return
	 */
	String getCountSql(BoundSql boundSql ,Object paramentObject,
			RowBounds rowBounds,CacheKey countKey);
	
	/***
	 * 执行完count查询后
	 * 
	 * @param count
	 * @param paramenterObject
	 * @param rowBounds
	 */
	void afterCount(long count,Object paramenterObject,RowBounds rowBounds);
	
	/***
	 * 执行分页前，返回true会进行分页查询，返回false会返回默认查询结果
	 * 
	 * @param msId
	 * @param paramenterObject
	 * @param rowBounds
	 * @return
	 */
	boolean beforePage(String msId ,Object paramenterObject, RowBounds rowBounds );
	
	
	/***
	 * 生成分页查询sql语句
	 * 
	 * @param boundSql
	 * @param paramentObject
	 * @param rowBounds
	 * @param pageKey
	 * @return
	 */
	String getPageSql(BoundSql boundSql ,Object paramentObject,
			RowBounds rowBounds,CacheKey pageKey);
	

	/***
	 * 分页查询后，处理分页结果，拦截器中直接return该方法的返回值
	 * 
	 * @param pageList
	 * @param paramenterObject
	 * @param rowBounds
	 * @return
	 */
	Object afterPage(List pageList,Object paramenterObject ,RowBounds rowBounds);
	
	/***
	 * 设置参数
	 * 
	 * @param properties
	 */
	void setProperties(Properties properties);
	




}
