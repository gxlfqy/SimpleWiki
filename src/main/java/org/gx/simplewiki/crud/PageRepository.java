package org.gx.simplewiki.crud;

import org.gx.simplewiki.exception.DataBaseError;
import org.gx.simplewiki.exception.page.PageNoneFound;
import org.gx.simplewiki.model.Page;

public interface PageRepository {
    // 短文操作
    Page getPage(String page_id) throws DataBaseError, PageNoneFound;
    void update(Page page) throws DataBaseError;
    void delete(Page page) throws DataBaseError;

}
