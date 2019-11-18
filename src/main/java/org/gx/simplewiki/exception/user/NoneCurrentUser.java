package org.gx.simplewiki.exception.user;

import org.gx.simplewiki.exception.Expt;
import org.gx.simplewiki.exception.WikiException;

public class NoneCurrentUser extends WikiException {
    public NoneCurrentUser() {
        super(Expt.OPT_NONE_CURRENT_USER, "用户未登录。如果已登录，请尝试注销后登录");
    }
}
