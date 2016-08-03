package org.lql.study.mongodb;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.mongodb.client.model.Filters.*;
import static java.util.Arrays.asList;

/**
 * Created by liqiulin on 2016/7/29.
 */
public class Client {
    private String collectionName = "restaurants";
    private MongoDatabase db;

    public static void main(String[] args) throws Exception {
        Client client = new Client();

        client.init();

//        client.insert();

        client.queryAll();

//        client.queryByTopLevelField();

//        client.queryByEmbeddedField();

//        client.queryWithGreatThan();

//        client.queryWithLowThan();

//        client.queryWithLogicalOr();

//        client.queryWithLogicalAnd();

//        client.updateTopLevelField();

//        client.deleteMany();

//        client.dropCollection("restaurants");

//        client.group();

        client.createSingleFieldIndex();

        client.createCompoundIndex();

    }

    public void createCompoundIndex() {
        db.getCollection(collectionName).createIndex(
                new Document("cuisine", 1).append("address.zipcode", -1));
    }

    public void createSingleFieldIndex() {
        db.getCollection(collectionName).createIndex(new Document("cuisine", 1));
    }

    public void group() {
        AggregateIterable iterable =
                db.getCollection(collectionName).aggregate(
                        asList(new Document("$group", new Document("_id", "$borough").append("count", new Document("$sum", 1)))));
        iterable.forEach(new Block<Document>() {
            public void apply(final Document document) {
                System.out.println(document.toJson());
            }
        });
    }

    public void dropCollection(String collectionName) {
        db.getCollection(collectionName).drop();
    }

    public void deleteMany() {
        db.getCollection(collectionName).deleteMany(new Document("borough", "Manhattan"));
    }


    public void updateTopLevelField() {
        UpdateResult updateResult = db.getCollection(collectionName).updateOne(new Document("name", "Juni"),
                new Document("$set", new Document("address.street", "East 31st Street")));
        System.out.println(" update count : " + updateResult.getModifiedCount());
    }

    public void queryByOrder() {
        db.getCollection(collectionName).find().sort(new Document("borough", 1).append("address.zipcode", 1));
        db.getCollection(collectionName).find().sort(Sorts.ascending("borough", "address.zipcode"));
    }

    public void queryWithLogicalOr() {
        db.getCollection(collectionName).find(
                new Document("$or",new Document("$or", asList(new Document("cuisine", "Italian"),
                        new Document("address.zipcode", "10075")))));

        db.getCollection(collectionName).find(or(eq("cuisine", "Italian"), eq("address.zipcode", "10075")));
    }

    public void queryWithLogicalAnd() {
        db.getCollection(collectionName).find(
                new Document("grades.score", new Document("$gt", 30)).append("address.zipcode", "10075"));

        db.getCollection(collectionName).find(
                and(eq("grades.score", new Document("$gt", 30)), eq("address.zipcode", "10075")));


    }

    public void queryWithGreatThan() {
        db.getCollection(collectionName).find(
                new Document("grades.score", new Document("$gt", 30)));
    }

    public void queryWithLowThan() {
        db.getCollection(collectionName).find(
                new Document("grades.score", new Document("$lt", 30)));
    }

    public void init() {
        // 默认连接服务器地址： 127.0.0.1：27017
        MongoClient mongoClient = new MongoClient();

        this.db = mongoClient.getDatabase("test");
    }

    public void insert() throws Exception {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        db.getCollection(collectionName).insertOne(
                new Document("address",
                        new Document()
                                .append("street", "2 Avenue")
                                .append("zipcode", "10075")
                                .append("building", "1480")
                                .append("coord", asList(-73.9557413, 40.7720266)))
                        .append("borough", "Manhattan")
                        .append("cuisine", "Italian")
                        .append("grades", asList(
                                new Document()
                                        .append("date", format.parse("2014-10-01T00:00:00Z"))
                                        .append("grade", "A")
                                        .append("score", 11),
                                new Document()
                                        .append("date", format.parse("2014-01-16T00:00:00Z"))
                                        .append("grade", "B")
                                        .append("score", 17)))
                        .append("name", "Vella")
                        .append("restaurant_id", "41704620"));
    }

    public void queryAll() {
        FindIterable<Document> iterable = db.getCollection(collectionName).find();
        iterable.forEach(new Block<Document>() {
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
    }

    public void queryByTopLevelField() {
        // 方式一
        FindIterable<Document> iterable =
                db.getCollection(collectionName).find(new Document("borough", "Manhattan"));

        // 方式二
//        FindIterable<Document> iterable =
//                db.getCollection(collectionName).find(eq("borough", "Manhattan"));

        iterable.forEach(new Block<Document>() {
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
    }

    public void queryByEmbeddedField() {
        FindIterable<Document> iterable =
                db.getCollection(collectionName).find(new Document("address.zipcode", "10075"));
        iterable.forEach(new Block<Document>() {
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
    }
}
