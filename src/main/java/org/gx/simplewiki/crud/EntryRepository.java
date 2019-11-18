package org.gx.simplewiki.crud;

import org.gx.simplewiki.exception.DataBaseError;
import org.gx.simplewiki.exception.entry.EntryNoneFound;
import org.gx.simplewiki.model.*;
import java.util.List;

public interface EntryRepository {
    // 词条操作
    Entry getByTitle(String entry_title) throws DataBaseError, EntryNoneFound;
    void update(Entry entry) throws DataBaseError;
    void delete(Entry entry) throws DataBaseError;
    List<Entry> getList() throws DataBaseError;

}
