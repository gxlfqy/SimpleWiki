package org.gx.simplewiki.exception.page;

import org.gx.simplewiki.exception.Expt;
import org.gx.simplewiki.exception.WikiException;

public class PageNoneEntry extends WikiException {
    public PageNoneEntry() {
        super(Expt.PAGE_NONE_ENTRY, "该短文不属于任何一个词条");
    }

}
