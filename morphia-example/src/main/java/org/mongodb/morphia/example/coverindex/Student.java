package org.mongodb.morphia.example.coverindex;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

/**
 * Created by liqiulin on 2016/9/19.
 */
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