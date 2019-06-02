package cn.scuec.major_adjust_system.test;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.scuec.major_adjust_system.dao.MajorTableDao;
import cn.scuec.major_adjust_system.dao.UserDao;
import cn.scuec.major_adjust_system.model.MajorTable;
import cn.scuec.major_adjust_system.model.User;
import cn.scuec.major_adjust_system.tools.Change;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:spring.xml" })
public class DaoTest {
	
	@Autowired
	private UserDao UserDao;
	
	
	Change change = new Change();

//	for(String key:map.keySet()) {
//        System.out.println("Key: "+key+" Value: "+map.get(key));
//    }
	
	@Test
	public void test() throws Exception {
		// 1.��ȡ�������ݿ����ӵĻỰ�Ĺ����࣬���������ļ�����
		InputStream inputStream = Resources.getResourceAsStream("mybatis-Config.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		// 2.ͨ�������࣬��ȡ�����ݿ����ӵĻỰ
		SqlSession session = sqlSessionFactory.openSession();
		
		// 3.ͨ��session�������ݿ�
		MajorTableDao majorTableDao = session.getMapper(MajorTableDao.class);
		List<MajorTable> majorTables = majorTableDao.selectAll(2019);
		int year = 2019;
		int count = 3;
		Change.ZuiZhongYuJingZhuanYe(majorTables, year, count);
	}
	
	@Test
	public void name() {
		int i = 67;
		int count = (int) Math.round(i*0.05);
		System.out.println(count);
	}
	
	@Test
	public void testGetUser() {
		User adminUser=UserDao.queryuser();
		
		System.out.println(adminUser);
	}
}
