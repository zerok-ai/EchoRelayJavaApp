package ai.zerok.echorelayapp.services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private EntityManager entityManager;

    private MongoClient mongoClient;

    public Object executeRawQUeryMySQL(String rawQuery) {
        Query query = entityManager.createNativeQuery(rawQuery);
        return query.getSingleResult();
    }

    public Object executeRawQUeryMongo(String database, String rawQuery) {
//        MongoDatabase database = mongoClient.getDatabase("test");
//        MongoCollection<Document> collection = database.getCollection("test1");
//        Document myDoc = collection.find().first();
//        System.out.println(myDoc.toJson());
        if(mongoClient == null){
            mongoClient = MongoClients.create();
        }
        return "";
    }

}
