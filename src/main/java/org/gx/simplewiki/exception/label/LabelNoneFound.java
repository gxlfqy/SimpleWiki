package org.gx.simplewiki.exception.label;

import org.gx.simplewiki.exception.Expt;
import org.gx.simplewiki.exception.WikiException;

public class LabelNoneFound extends WikiException {
    public LabelNoneFound() {
        super(Expt.LABEL_NONE_FOUND, "标签未找到");
    }

}
