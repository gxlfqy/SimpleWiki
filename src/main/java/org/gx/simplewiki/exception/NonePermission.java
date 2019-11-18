package org.gx.simplewiki.exception;

import org.gx.simplewiki.exception.Expt;
import org.gx.simplewiki.exception.WikiException;

public class NonePermission extends WikiException {
    public NonePermission() {
        super(Expt.OPT_NONE_PERMISSION, "无权操作");
    }
}
