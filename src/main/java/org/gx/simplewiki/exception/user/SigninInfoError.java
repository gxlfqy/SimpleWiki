package org.gx.simplewiki.exception.user;

import org.gx.simplewiki.exception.Expt;
import org.gx.simplewiki.exception.WikiException;

public class SigninInfoError extends WikiException {
    public SigninInfoError() {
        super(Expt.SIGNIN_INFO_ERROR, "登录信息错误");
    }
}
