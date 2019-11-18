package org.gx.simplewiki.exception;

public class DataBaseError extends WikiException {
    public DataBaseError() {
        super(Expt.DATABASE_ERROR, "服务器繁忙!");
    }

}
