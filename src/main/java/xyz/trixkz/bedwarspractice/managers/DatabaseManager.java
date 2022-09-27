package xyz.trixkz.bedwarspractice.managers;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;
import xyz.trixkz.bedwarspractice.utils.Utils;

import java.util.Collections;

public class DatabaseManager {

    private MongoClient mongoClient;

    @Getter private MongoDatabase mongoDatabase;

    @Getter private MongoCollection<Document> players;

    private String host, username, password, database, authDatabase;
    private int port;
    private boolean auth;

    public DatabaseManager(String host, int port, String database, boolean auth, String username, String password, String authDatabase) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.auth = auth;
        this.username = username;
        this.password = password;
        this.authDatabase = authDatabase;

        connect();

        Utils.debug("MongoDB database connection successful");
    }

    public void connect() {
        if (auth) {
            MongoCredential mongoCredential = MongoCredential.createCredential(username, authDatabase, password.toCharArray());

            mongoClient = new MongoClient(new ServerAddress(this.host, this.port), Collections.singletonList(mongoCredential));

            Utils.debug("MongoDB database connection successful");
        } else {
            mongoClient = new MongoClient(new ServerAddress(this.host, this.port));

            Utils.debug("MongoDB database connection successful");
        }

        mongoDatabase = mongoClient.getDatabase(database);

        this.players = this.mongoDatabase.getCollection("players");
    }
}
