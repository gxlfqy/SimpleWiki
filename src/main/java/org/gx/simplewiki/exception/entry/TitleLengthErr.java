package org.gx.simplewiki.exception.entry;

import org.gx.simplewiki.exception.Expt;
import org.gx.simplewiki.exception.WikiException;

public class TitleLengthErr extends WikiException {
    public TitleLengthErr() {
        super(Expt.ENTRY_TITLE_LENGTH_ERR, "词条标题长度错误");
    }

}
