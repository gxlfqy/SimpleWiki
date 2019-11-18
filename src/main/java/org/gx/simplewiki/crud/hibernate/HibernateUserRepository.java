package org.gx.simplewiki.crud.hibernate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gx.simplewiki.crud.UserRepository;
import org.gx.simplewiki.exception.DataBaseError;
import org.gx.simplewiki.exception.Expt;
import org.gx.simplewiki.exception.WikiException;
import org.gx.simplewiki.exception.user.AccessFailure;
import org.gx.simplewiki.exception.user.AccessNoneFound;
import org.gx.simplewiki.exception.user.NoneCurrentUser;
import org.gx.simplewiki.exception.user.UserNoneFound;
import org.gx.simplewiki.model.Access;
import org.gx.simplewiki.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;

@Repository
@Transactional
public class HibernateUserRepository implements UserRepository {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private static Pattern invalidChars = Pattern.compile("[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\n|\t");

	public static boolean isSpecialChar(String str) {
		Matcher m = invalidChars.matcher(str);
		return m.find();
	}

	@Override
	public void update(User user) throws WikiException {
		String userName = user.getName();
		if (userName == null || userName.isEmpty() || userName.length() > 64) {
			throw new WikiException(Expt.USER_NAME_LENGTH_ERR, "用户名为空或超过64个字符。");
		}

		if (isSpecialChar(userName)) {
			throw new WikiException(Expt.USER_NAME_INVALID_CHAR, "用户名包含非法字符");
		}

		String password = user.getPassword();
		if (password == null || password.isEmpty() || password.length() > 256) {
			throw new WikiException(Expt.PASSWORD_LENGTH_ERR, "密码过长");
		}

		String nickName = user.getNickname();
		if (nickName != null && nickName.length() > 64) {
			throw new WikiException(Expt.USER_NICKNAME_TOO_LONG, "昵称过长");
		}

		try {
			sessionFactory.getCurrentSession().saveOrUpdate(user);
		} catch (HibernateException e) {
			throw new DataBaseError();
		}
	}

	@Override
	public User getUser(String name) throws DataBaseError, UserNoneFound {
		try {
			List users = sessionFactory.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("name", name)).list();
			if (users.isEmpty()) {
				throw new UserNoneFound();
			}

			return (User) users.get(0);
		} catch (HibernateException e) {
			throw new DataBaseError();
		}
	}

	@Override
	public User getUserById(String user_id) throws DataBaseError, UserNoneFound {
		try {
			List users = sessionFactory.getCurrentSession().createCriteria(User.class).add(Restrictions.eq("id", user_id)).list();
			if (users.isEmpty()) {
				throw new UserNoneFound();
			}

			return (User) users.get(0);
		} catch (HibernateException e) {
			throw new DataBaseError();
		}
	}

	@Override
	public User getCurUser() throws DataBaseError, NoneCurrentUser {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String name;

		if (principal instanceof UserDetails) {
			name = ((UserDetails) principal).getUsername();
		} else {
			name = principal.toString();
		}

		try {
			return this.getUserById(name);
		} catch (UserNoneFound e) {
			throw new NoneCurrentUser();
		}
	}

	public static Date addDaysByGetTime(Date date, int n/*加减天数*/) {
		return new Date(date.getTime() + n * 24 * 60 * 60 * 1000L);
	}

	@Override
	public Access createAccess(User user) throws DataBaseError {
		try {
			Access access = new Access(addDaysByGetTime(new Date(), 14), user);
			sessionFactory.getCurrentSession().saveOrUpdate(access);
			return access;
		} catch (HibernateException e) {
			throw new DataBaseError();
		}
	}

	@Override
	public Access getAccess(String access_id) throws DataBaseError, AccessNoneFound {
		try {
			List accesses = sessionFactory.getCurrentSession().createCriteria(Access.class).add(Restrictions.eq("id", access_id)).list();
			if (accesses.isEmpty()) {
				throw new AccessNoneFound();
			}

			return (Access) accesses.get(0);
		} catch (HibernateException e) {
			throw new DataBaseError();
		}
	}

	@Override
	public void extendAccess(String user_id, String access_id) throws DataBaseError, AccessFailure {
		User user;
		try {
			user = getUserById(user_id);
		} catch (UserNoneFound e) {
			throw new AccessFailure();
		}

		if (user.accesses == null || access_id == null || access_id.isEmpty())
			throw new AccessFailure();

		try {
			boolean found = false;
			for (Access term : user.accesses) {
				if (term.getId().equals(access_id)) {
					Date deadline = term.getDeadline();
					if (deadline.getTime() < (new Date()).getTime()) {
						sessionFactory.getCurrentSession().delete(term);
						throw new AccessFailure();
					}
					term.setDeadline(addDaysByGetTime(term.getDeadline(), 14));
					sessionFactory.getCurrentSession().saveOrUpdate(term);
					found = true;
				}
			}
			if (!found)
				throw new AccessFailure();

		} catch (HibernateException e) {
			throw new DataBaseError();
		}
	}

}



