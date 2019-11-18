package org.gx.simplewiki.exception.entry;

import org.gx.simplewiki.exception.Expt;
import org.gx.simplewiki.exception.WikiException;

public class EntryNoneFound extends WikiException {
    public EntryNoneFound() {
        super(Expt.ENTRY_NONE_FOUND, "该词条不存在");
    }
}
