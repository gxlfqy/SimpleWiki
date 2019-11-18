package org.gx.simplewiki.exception;

public class WikiExceptionMsg {
    public WikiExceptionMsg() {
    }

    public WikiExceptionMsg(int errcode, String errmsg) {
        setErrcode(errcode);
        setErrmsg(errmsg);
    }

    private int errcode = 0;
    private String errmsg = "";

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public void extendMsg(String errmsg) {
        if (!this.errmsg.isEmpty())
            this.errmsg += " \n";

        this.errmsg += errmsg;
    }
}
