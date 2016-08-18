import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by liqiulin on 2016/8/3.
 */
public class UserTest extends MongodbBaseTest {
    private DBCollection users;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        users = db.getCollection("user");
    }

    @Test
    public void should_insert_and_get_user() {
        users = db.getCollection("user");
        final DBObject userDocument = new BasicDBObjectBuilder()
                .add("name", "kiwi")
                .get();
        users.insert(userDocument);

        final DBObject userDocumentFromDb = users.findOne(new BasicDBObject("_id", userDocument.get("_id")));

        Assert.assertEquals(userDocumentFromDb.get("name"), "kiwi");
    }
}