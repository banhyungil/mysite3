package kr.co.itcen.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.mysite.exception.UserDaoException;
import kr.co.itcen.mysite.repository.UserDao;
import kr.co.itcen.mysite.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	public void join(UserVo vo) {
		userDao.insert(vo);
	}

	/**
	 *	Session정보에 no, name만 가지게 한다 
	 */
	public UserVo getSessionUser(UserVo vo) {
		return userDao.get(vo,"sessionUser");	
	}
	
	/**
	 * user의 모든 정보를가져온다
	 */
	public UserVo getUser(UserVo vo) {
		return userDao.get(vo,"user");	
	}


	public void update(UserVo vo) {
		// TODO Auto-generated method stub
		userDao.update(vo);
	}
	
}
