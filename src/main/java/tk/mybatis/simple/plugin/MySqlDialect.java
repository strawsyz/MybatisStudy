package tk.mybatis.simple.plugin;

import java.util.List;
import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.RowBounds;

/***
 * mysql中的实现
 * 
 * @author SYZPC
 *
 */
public class MySqlDialect implements Dialect {

	@Override
	public boolean skip(String msId, Object paramenterObject, RowBounds rowBounds) {
		//使用RowBounds分页
		//没有RowBounds参数时，使用RowBounds.DEFAULT作为默认值
		if(rowBounds != RowBounds.DEFAULT){
			return false;
		}
		return true;
	}

	@Override
	public boolean beforeCount(String msId, Object paramenterObject, RowBounds rowBounds) {
		//只有使用了PageRowBounds才能记录总数，否则查询了总数也没用
		if(rowBounds instanceof PageRowBounds){
			return true;
		}
		return false;
	}

	@Override
	public String getCountSql(BoundSql boundSql, Object paramentObject, RowBounds rowBounds, CacheKey countKey) {
		//简单嵌套实现Mysql count查询
		return "select count(*) from(" + boundSql + ") temp";
	}

	@Override
	public void afterCount(long count, Object paramenterObject, RowBounds rowBounds) {
		//只有PageRowCount才会查询count，所以这里直接强制转换
		((PageRowBounds) rowBounds).setTotal(count);
	}

	@Override
	public boolean beforePage(String msId, Object paramenterObject, RowBounds rowBounds) {
		if(rowBounds != RowBounds.DEFAULT){
			return true;
		}
		return false;
	}

	@Override
	public String getPageSql(BoundSql boundSql, Object paramentObject, RowBounds rowBounds, CacheKey pageKey) {
		pageKey.update("RowBounds");
		return boundSql.getSql()+" limit "+ rowBounds.getOffset()+","+rowBounds.getLimit();
	}

	@Override
	public Object afterPage(List pageList, Object paramenterObject, RowBounds rowBounds) {
		//直接返回查询结果
		return pageList;
	}

	@Override
	public void setProperties(Properties properties) {
		//没有设置其他参数
	}

}
