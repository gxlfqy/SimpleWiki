package org.gx.simplewiki.exception.user;

import org.gx.simplewiki.exception.Expt;
import org.gx.simplewiki.exception.WikiException;

public class AccessNoneFound extends WikiException {
    public AccessNoneFound() {
        super(Expt.ACCESS_NONE_FOUND, "许可证未找到");
    }
}
