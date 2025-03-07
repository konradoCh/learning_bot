package org.learning_bot;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.internal.MongoClientImpl;
import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.BsonInt64;
import org.bson.Document;
import org.bson.conversions.Bson;

public class MongoDB {



    public static void connectToDatabase() {
        String uri = "mongodb://localhost:27017";

        MongoClient mongoClient = new Mon
//        try (MongoClient mongoClient = MongoClients.create(uri)){
//            MongoDatabase mongoDatabase = mongoClient.getDatabase("admin");
//
//            try {
//                Bson command = new BsonDocument("ping", new BsonInt32(1));
//                Document commandResult = mongoDatabase.runCommand(command);
//                System.out.println("Connected successfully to server");
//            } catch (MongoException mongoException) {
//                mongoException.printStackTrace();
//            }
//        }
        }

    }
