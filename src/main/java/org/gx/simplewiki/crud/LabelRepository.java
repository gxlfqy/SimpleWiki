package org.gx.simplewiki.crud;

import org.gx.simplewiki.exception.DataBaseError;
import org.gx.simplewiki.model.Label;

import java.util.List;

public interface LabelRepository {
    Label getByTitle(String title);
    void update(Label label) throws DataBaseError;
    void delete(Label label);
    // 获取标签列表
    List<Label> getList() throws DataBaseError;

}
