package kr.co.itcen.mysite.repository;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.exception.UserDaoException;
import kr.co.itcen.mysite.vo.UserVo;

@Repository
public class UserDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public UserVo get(UserVo vo, String userType) {
		UserVo result;
		
		if("sessionUser".equals(userType)) {	//sessionUser 데이터 
			result = sqlSession.selectOne("user.getByEmailAndPassword1", vo);
		}else {	//유저의 모든 데이터
			result = sqlSession.selectOne("user.getByNo",vo);
		}
		 
		return result;
	}
	
	public Boolean insert(UserVo vo) throws UserDaoException {	
		int count = sqlSession.insert("user.insert", vo);
		
		return (count == 1);
	}
	
	public Boolean update(UserVo vo) {	
		int count = sqlSession.update("user.update", vo);
		
		return (count == 1);
		
	}
	
}
