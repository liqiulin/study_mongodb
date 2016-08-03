import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.After;

/**
 * @see <a href="http://blog.csdn.net/kiwi_coder/article/details/37873093" >http://blog.csdn.net/kiwi_coder/article/details/37873093</a>
 * @see <a href="https://github.com/flapdoodle-oss/de.flapdoodle.embed.mongo" >flapdoodle.embed.mongo</a>
 *
 * Created by liqiulin on 2016/8/3.
 */
public class MongodbBaseTest {
    /**
     * please store Starter or RuntimeConfig in a static final field
     * if you want to use artifact store caching (or else disable caching)
     */
    private static final MongodStarter starter = MongodStarter.getDefaultInstance();

    private MongodExecutable _mongodExe;
    private MongodProcess _mongod;
    protected DB db;
    private MongoClient _mongo;

    @org.junit.Before
    public void setUp() throws Exception {

        _mongodExe = starter.prepare(new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(12345, Network.localhostIsIPv6()))
                .build());
        _mongod = _mongodExe.start();

        _mongo = new MongoClient("localhost", 12345);
//        db = _mongo.getDatabase("embedded-mongo");
        db = _mongo.getDB("embedded-mongo");
    }

    @After
    public void tearDown() throws Exception {
        _mongod.stop();
        _mongodExe.stop();
    }

    public Mongo getMongo() {
        return _mongo;
    }
}
