package org.gx.simplewiki.exception.page;

import org.gx.simplewiki.exception.Expt;
import org.gx.simplewiki.exception.WikiException;

public class PageNoneFound extends WikiException {
    public PageNoneFound() {
        super(Expt.PAGE_ID_NONE_FOUND, "未找到与ID对应的短文。");
    }
}
