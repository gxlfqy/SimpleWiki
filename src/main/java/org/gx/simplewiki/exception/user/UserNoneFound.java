package org.gx.simplewiki.exception.user;

import org.gx.simplewiki.exception.Expt;
import org.gx.simplewiki.exception.WikiException;

public class UserNoneFound extends WikiException {
    public UserNoneFound() {
        super(Expt.OPT_USER_NONE_FOUND, "未找到该用户");
    }
}
