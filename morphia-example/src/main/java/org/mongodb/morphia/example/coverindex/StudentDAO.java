package org.mongodb.morphia.example.coverindex;

import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by liqiulin on 2016/9/19.
 */
public interface StudentDAO {
    ObjectId save(Student student);

    /**
     *  返回查询的映射字段   http://docs.mongoing.com/manual-zh/tutorial/project-fields-from-query-results.html
     * @param name
     * @return
     */
    List<Student> queryByNameWithoutId(String name);

    List<Student> QueryByNameWithCoverIndex(String name);
}
