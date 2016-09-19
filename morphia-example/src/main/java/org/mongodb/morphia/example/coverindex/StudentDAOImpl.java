package org.mongodb.morphia.example.coverindex;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;

import java.util.List;

import static javafx.scene.input.KeyCode.T;

/**
 * Created by liqiulin on 2016/9/19.
 */
public class StudentDAOImpl implements StudentDAO {
    private Datastore datastore;

    public StudentDAOImpl(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public ObjectId save(Student student) {
        return (ObjectId) datastore.save(student).getId();
    }

    @Override
    public List<Student> queryByNameWithoutId(String name) {
        Query query = datastore.createQuery(Student.class);
        // 查询剔除掉id字段
        query.retrievedFields(false, "id");
        query.filter("name", name);
        return query.asList();
    }
}
