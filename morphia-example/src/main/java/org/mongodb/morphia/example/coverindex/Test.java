package org.mongodb.morphia.example.coverindex;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.List;

/**
 * 开启mongodb profile即可查询到执行语句
 * db.system.profile.find({op:'query'}).sort({ts:1});
 *
 * Created by qiulin on 2016-9-17.
 */
public class Test {
    public static void main(String[] args) {
//        final Fongo fongo = new Fongo("javaJunitMongo");

        final Morphia morphia = new Morphia();

        // tell morphia where to find your classes
        // can be called multiple times with different packages or classes
        morphia.mapPackage("org.mongodb.morphia.example");

        morphia.getMapper().getOptions().setStoreEmpties(false);
        morphia.getMapper().getOptions().setStoreNulls(false);

        // create the Datastore connecting to the database running on the default port on the local host
        final Datastore datastore = morphia.createDatastore(new MongoClient(), "morphia_example");
        datastore.getDB().dropDatabase();

        StudentDAO studentDAO = new StudentDAOImpl(datastore);
        studentDAO.save(new Student("zhangsan", 18));
        studentDAO.save(new Student("lisi", 19));
        studentDAO.save(new Student("wangwu", 20));

        // 需collection存在才有能建索引
        datastore.ensureIndexes();

        List<Student> students = studentDAO.queryByNameWithoutId("lisi");
        if(students != null) {
            students.forEach(s -> System.out.println(s));
        }
    }
}


