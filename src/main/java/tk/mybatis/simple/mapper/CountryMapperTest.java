package tk.mybatis.simple.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import tk.mybatis.simple.model.Country;
import tk.mybatis.simple.model.CountryExample;

public class CountryMapperTest extends BaseMapperTest{
	
//	private static SqlSessionFactory sqlSessionFactory;
//	@BeforeClass
//	public static void init(){
//		try{
//			Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
//			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
//			reader.close();
//		}catch(IOException ignore){
//			ignore.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void testSelectAll(){
//		SqlSession sqlSession  =sqlSessionFactory.openSession();
//		try{
//			List<Country> countryList = sqlSession.selectList("selectAll");
//			prin(countryList);
//		}finally{
//			sqlSession.close();//不要忘记关闭sqlsession
//		}
//	}

	private void prin(List<Country> countryList) {
		for (Country country : countryList) {
			System.out.printf("%-4d%4s%4s\n",country.getId(),country.getCountryname(),country.getCountrycode());
		}
	}
	
	//了解Example
	@Test
	public void testExample(){
		SqlSession sqlSession = getSqlSession();
		try {
			CountryMapper  countryMapper = sqlSession.getMapper(CountryMapper.class);
			CountryExample  example= new CountryExample();
			example.setOrderByClause("id desc ,countryname asc");
			example.setDistinct(true);
			//创建条件
			CountryExample.Criteria criteria  = example.createCriteria();
			criteria.andIdGreaterThanOrEqualTo(1);
			criteria.andIdLessThan(4);
			criteria.andCountrycodeLike("%U%");
			CountryExample.Criteria or = example.or();
			or.andCountrynameEqualTo("中国");
			List<Country> countryList = countryMapper.selectByExample(example);
			prin(countryList);
		} finally {
			sqlSession.close();
		}
	}
	//updateByExampleSelective
	@Test
	public void testUpdateByExampleSelective(){
		SqlSession sqlSession = getSqlSession();
		try {
			CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
			CountryExample example = new CountryExample();
			CountryExample.Criteria criteria = example.createCriteria();
			criteria.andIdGreaterThan(2);
			Country country = new Country();
			country.setCountryname("China");
			countryMapper.updateByExampleSelective(country, example);
			prin(countryMapper.selectByExample(example));
		} finally {
			sqlSession.close();
		}
	}
	//deleteByExample
	@Test
	public void testDeleteByExample(){
		SqlSession sqlSession = getSqlSession();
		try {
			CountryMapper countryMapper = sqlSession.getMapper(CountryMapper.class);
			CountryExample example = new CountryExample();
			CountryExample.Criteria criteria = example.createCriteria();
			criteria.andIdGreaterThan(2);
			countryMapper.deleteByExample(example);
			Assert.assertEquals(0, (countryMapper.countByExample(example)));
		} finally {
			sqlSession.close();
		}
	}
}
