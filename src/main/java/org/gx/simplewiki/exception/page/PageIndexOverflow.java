package org.gx.simplewiki.exception.page;

import org.gx.simplewiki.exception.Expt;
import org.gx.simplewiki.exception.WikiException;

public class PageIndexOverflow extends WikiException {
    public PageIndexOverflow() {
        super(Expt.PAGE_INDEX_OVERFLOW, "短文下标越界");
    }

}
