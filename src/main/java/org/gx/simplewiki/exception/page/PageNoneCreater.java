package org.gx.simplewiki.exception.page;

import org.gx.simplewiki.exception.Expt;
import org.gx.simplewiki.exception.WikiException;

public class PageNoneCreater extends WikiException {
    public PageNoneCreater() {
        super(Expt.PAGE_NONE_CREATER, "短文没有创作者, 词条数据错误");
    }

}
