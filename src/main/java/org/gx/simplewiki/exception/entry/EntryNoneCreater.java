package org.gx.simplewiki.exception.entry;


import org.gx.simplewiki.exception.Expt;
import org.gx.simplewiki.exception.WikiException;

public class EntryNoneCreater extends WikiException {
    public EntryNoneCreater() {
        super(Expt.ENTRY_NONE_CREATER, "词条没有创作者, 词条数据错误");
    }

}
