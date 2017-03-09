package org.mongodb.morphia.example.coverindex;

import com.github.fakemongo.Fongo;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.junit.*;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.DatastoreImpl;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.MapperOptions;

import java.util.List;

/**
 * Created by liqiulin on 2016/9/19.
 */

public class StudenDAOTest {
    StudentDAO studentDAO;

    @Before
    public void before() {
        final Fongo fongo = new Fongo("javaJunitMongo");
        Morphia morphia = new Morphia();
        MapperOptions options = morphia.mapPackage("org.mongodb.morphia.example.coverindex", true).getMapper().getOptions();
        options.setStoreEmpties(false);
        options.setStoreNulls(false);
        Datastore datastore = new DatastoreImpl(morphia,morphia.getMapper(),new MongoClient(){
            @Override
            public DB getDB(String dbName) {
                return fongo.getDB(dbName);
            }
        }, "morphia-test");

        studentDAO = new StudentDAOImpl(datastore);
    }

    @org.junit.Test
    public void save_Success() {
        Student student = new Student("zhangsan", 18);
        ObjectId id = studentDAO.save(student);
    }

    @org.junit.Test
    public void queryByNameWithoutId_Success() {
        String queryName = "lisi";
        int age = 19;
        studentDAO.save(new Student("zhangsan", 18));
        studentDAO.save(new Student(queryName, age));
        List<Student> students = studentDAO.queryByNameWithoutId(queryName);
        students.forEach(student -> System.out.println(student));
        Assert.assertTrue(students.size() == 1);
        Assert.assertTrue(queryName.equals(students.get(0).getName()));
        Assert.assertTrue(students.get(0).getAge() == age);
    }

}
