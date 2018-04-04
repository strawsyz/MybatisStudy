package tk.mybatis.simple;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.cache.decorators.LoggingCache;
import org.apache.ibatis.cache.decorators.LruCache;
import org.apache.ibatis.cache.decorators.SerializedCache;
import org.apache.ibatis.cache.decorators.SynchronizedCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMap;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.mapping.ResultMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.junit.Test;

import tk.mybatis.simple.model.Country;

public class SimpleTest {
	

	@Test
	public void test() throws IOException, SQLException {
		final TypeHandlerRegistry registry = new TypeHandlerRegistry();
		// 使用log4j记录日志
		LogFactory.useLog4JLogging();
		// 创建配置对象
		final Configuration config = new Configuration();
		// 创建setting中的部分属性
		config.setCacheEnabled(true);
		config.setLazyLoadingEnabled(false);
		config.setAggressiveLazyLoading(true);

		// 添加拦截器
		SimpleInterceptor interceptor1 = new SimpleInterceptor("拦截器1");
		SimpleInterceptor interceptor2 = new SimpleInterceptor("拦截器2");
		config.addInterceptor(interceptor1);
		config.addInterceptor(interceptor2);

		// 创建数据源和JDBC事务
		UnpooledDataSource dataSource = new UnpooledDataSource();
		dataSource.setDriver("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/mybatis");
		dataSource.setUsername("root");
		dataSource.setPassword("syz123456");

		Transaction transaction = new JdbcTransaction(dataSource, null, false);

		// 创建Executor
		final Executor executor = config.newExecutor(transaction);

		// 创建SqlSource对象
		StaticSqlSource sqlSource = new StaticSqlSource(config, "SELECT * FROM country WHERE id = ?");

		// 创建参数映射配置
		// 由于前面的sql 语句中有参数id，所以需要ParameterMapping（参数映射）
		List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();
		// 通过ParameterMapping.Builder创建ParameterMapping
		parameterMappings.add(new ParameterMapping.Builder(config, "id", registry.getTypeHandler(Long.class)).build());
		ParameterMap.Builder paramBuilder = new ParameterMap.Builder(config, "defaultParameterMap", Country.class,
				parameterMappings);

		// 创建结果映射
		@SuppressWarnings("serial")
		ResultMap resultMap = new ResultMap.Builder(config, "defaultResultMap", Country.class,
				new ArrayList<ResultMapping>() {
					{
						add(new ResultMapping.Builder(config, "id", "id", Long.class).build());
						add(new ResultMapping.Builder(config, "countryname", "countryname", String.class).build());
						add(new ResultMapping.Builder(config, "countrycode", "countrycode",
								registry.getTypeHandler(String.class)).build());
					}

				}).build();

		// 创建缓存对象
		final Cache countryCache = new SynchronizedCache(//同步缓存
				new SerializedCache(                     //序列化缓存
						new LoggingCache(                //日志缓存
								new LruCache(            //最少使用缓存
										new PerpetualCache( //持久缓存
												"country_cache")))));
		
		//创建MappedStatement对象
		MappedStatement.Builder msBuilder = new MappedStatement.Builder (
				config, 
				"tk.mybatis.simple.mapper.selectCountry", 
				sqlSource, 
				SqlCommandType.SELECT);
		msBuilder.parameterMap(paramBuilder.build());
		List<ResultMap> resultMaps = new ArrayList<ResultMap>();
		resultMaps.add(resultMap);
		msBuilder.resultMaps(resultMaps);
		msBuilder.cache(countryCache);
		MappedStatement ms = msBuilder.build();
		
		//执行查询
		List <Country> countries = executor.query(ms, 1L, RowBounds.DEFAULT	, Executor.NO_RESULT_HANDLER);
		
		
	}
	
	
}
