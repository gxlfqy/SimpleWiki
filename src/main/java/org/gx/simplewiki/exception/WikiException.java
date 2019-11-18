package org.gx.simplewiki.exception;

public class WikiException extends Throwable {
    public WikiException() {
    }

    public WikiException(WikiExceptionMsg msg) {
        super(msg.getErrmsg());
        setWikiExceptionMsg(msg);
    }

    public WikiException(int errcode, String errmsg) {
        super(errmsg);
        setWikiExceptionMsg(new WikiExceptionMsg(errcode, errmsg));
    }

    private WikiExceptionMsg wikiExceptionMsg;

    /*******getter & setter***********/
    public WikiExceptionMsg getWikiExceptionMsg() {
        return wikiExceptionMsg;
    }

    public void setWikiExceptionMsg(WikiExceptionMsg wikiExceptionMsg) {
        this.wikiExceptionMsg = wikiExceptionMsg;
    }

}
