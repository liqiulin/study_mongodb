package org.mongodb.morphia.example.coverindex;

import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.junit.internal.builders.IgnoredClassRunner;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.annotations.*;
import org.mongodb.morphia.query.Query;

import java.util.List;

/**
 * 开启mongodb profile即可查询到执行语句
 * db.system.profile.find({op:'query'}).sort({ts:1});
 *
 * Created by qiulin on 2016-9-17.
 */
public class Test {
    public static void main(String[] args) {
        final Morphia morphia = new Morphia();

        // tell morphia where to find your classes
        // can be called multiple times with different packages or classes
        morphia.mapPackage("org.mongodb.morphia.example");

        // create the Datastore connecting to the database running on the default port on the local host
        final Datastore datastore = morphia.createDatastore(new MongoClient(), "morphia_example");

        datastore.getDB().dropDatabase();

        datastore.save(new Student("zhangsan", 18));
        datastore.save(new Student("lisi", 19));
        datastore.save(new Student("wangwu", 20));

        // 需collection存在才有能建索引
        datastore.ensureIndexes();

        Query query = datastore.createQuery(Student.class);
        // 查询剔除掉id字段
        query.retrievedFields(false, "id");


        query.filter("name", "lisi");
        List<Student> students = query.asList();
        if(students != null) {
            students.forEach(s -> System.out.println(s));
        }


//        query = datastore.createQuery(Student.class);
//        query.filter("name", "lisi");
//        query.retrievedFields(true, "name","age");  // 这样id字段还是查询出来了
//        students = query.asList();
//        if(students != null) {
//            students.forEach(s -> System.out.println(s));
//        }


    }
}

@Entity(noClassnameStored=true)
@Indexes({@Index(value="name,age"), @Index(value="name")})
class Student {
    public Student() {

    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Id
    private ObjectId id;

    @Property
    private String name;

    @Property
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
