package org.gx.simplewiki.controller.result;

import org.gx.simplewiki.exception.WikiExceptionMsg;

public class Result {
    public Result() {
    }

    public Result(boolean succeed, Object obj) {
        setSucceed(succeed);
    }

    private boolean succeed = false;
    private Object obj;

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public void except(WikiExceptionMsg wikiExceptionMsg) {
        setSucceed(false);
        setObj(wikiExceptionMsg);
    }

    public void success(Object obj) {
        setSucceed(true);
        setObj(obj);
    }

}
