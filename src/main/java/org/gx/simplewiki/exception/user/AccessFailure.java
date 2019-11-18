package org.gx.simplewiki.exception.user;

import org.gx.simplewiki.exception.Expt;
import org.gx.simplewiki.exception.WikiException;

public class AccessFailure extends WikiException {
    public AccessFailure() {
        super(Expt.ACCESS_FAILURE, "许可证信息错误");
    }
}
