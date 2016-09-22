package org.mongodb.morphia.example.coverindex;

import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by liqiulin on 2016/9/19.
 */
public interface StudentDAO {
    ObjectId save(Student student);

    List<Student> queryByNameWithoutId(String name);

    List<Student> QueryByNameWithCoverIndex(String name);
}
