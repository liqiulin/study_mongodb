import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.*;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.extract.UserTempNaming;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.After;
import org.junit.Before;

/**
 * @see <a href="http://blog.csdn.net/kiwi_coder/article/details/37873093" >http://blog.csdn.net/kiwi_coder/article/details/37873093</a>
 * @see <a href="https://github.com/flapdoodle-oss/de.flapdoodle.embed.mongo" >flapdoodle.embed.mongo</a>
 *
 * Created by liqiulin on 2016/8/3.
 */
public class MongodbBaseTest {
    private static final int port = 12345;
    private static final Command command = Command.MongoD;

    private static final IRuntimeConfig runtimeConfig = new RuntimeConfigBuilder()
            .defaults(command)
            .artifactStore(new ExtractedArtifactStoreBuilder()
                    .defaults(command)
                    .download(new DownloadConfigBuilder()
                            .defaultsForCommand(command).build())
                    .executableNaming(new UserTempNaming()))
            .build();

    /**
     * please store Starter or RuntimeConfig in a static final field
     * if you want to use artifact store caching (or else disable caching)
     */
    private static final MongodStarter starter = MongodStarter.getInstance(runtimeConfig);

    private MongodExecutable _mongodExe;
    private MongodProcess _mongod;
    private MongoClient _mongo;
    protected DB db;
    protected MongoDatabase mongoDatabase;

    @Before
    public void setUp() throws Exception {
        IMongodConfig mongodConfig = new MongodConfigBuilder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(port, Network.localhostIsIPv6()))
                .build();

        _mongodExe = starter.prepare(mongodConfig);
        _mongo = new MongoClient("localhost", port);
        mongoDatabase = _mongo.getDatabase("embedded-mongo");
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
