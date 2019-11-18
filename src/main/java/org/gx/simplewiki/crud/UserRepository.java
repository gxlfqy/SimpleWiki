package org.gx.simplewiki.crud;

import org.gx.simplewiki.exception.DataBaseError;
import org.gx.simplewiki.exception.WikiException;
import org.gx.simplewiki.exception.user.AccessFailure;
import org.gx.simplewiki.exception.user.AccessNoneFound;
import org.gx.simplewiki.exception.user.NoneCurrentUser;
import org.gx.simplewiki.exception.user.UserNoneFound;
import org.gx.simplewiki.model.Access;
import org.gx.simplewiki.model.User;

public interface UserRepository {
	// 修改用户数据
	void update(User user) throws WikiException;
	User getUser(String name) throws DataBaseError, UserNoneFound;
	User getUserById(String user_id) throws DataBaseError, UserNoneFound;
	// 获取当前用户
	User getCurUser() throws DataBaseError, NoneCurrentUser;
	// 创建许可证
	Access createAccess(User user) throws DataBaseError;
	Access getAccess(String access_id) throws DataBaseError, AccessNoneFound;
	// 延期许可证
	void extendAccess(String user_id, String access_id) throws DataBaseError, AccessFailure;
}
