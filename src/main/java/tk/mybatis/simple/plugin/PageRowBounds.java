package tk.mybatis.simple.plugin;

import org.apache.ibatis.session.RowBounds;

/***
 * 用来记录total的分页参数
 * 
 * @author SYZPC
 *
 */
public class PageRowBounds extends RowBounds{
	private long total;
	
	public PageRowBounds(){
		super();
	}
	
	public PageRowBounds(int offset ,int limit){
		super(offset, limit);
	}
	
	public long getTotal(){
		return total;
	}
	
	public void setTotal(long total){
		this.total=total;
	}
}