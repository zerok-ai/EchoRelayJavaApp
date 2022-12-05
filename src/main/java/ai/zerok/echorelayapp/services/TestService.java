package ai.zerok.echorelayapp.services;

import ai.zerok.echorelayapp.configs.ApplicationConfiguration;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

//    @Autowired
    private EntityManager entityManager;

    private MongoClient mongoClient;

    @Autowired
    private ApplicationConfiguration applicationConfiguration;

    private void initializeMySql(){
        //TODO: This is not working - Figure out a better solution
        //https://stackoverflow.com/a/46499472/4666116
        if(entityManager == null){
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
            entityManager = emf.createEntityManager();
        }
    }

    private void initializeMongo(){
        if(mongoClient == null){
            mongoClient = MongoClients.create();
        }
    }

    public Object executeRawQUeryMySQL(String rawQuery) {
        initializeMySql();
        Query query = entityManager.createNativeQuery(rawQuery);
        return query.getSingleResult();
    }

    public Object executeRawQUeryMongo(String database, String rawQuery) {
//        MongoDatabase database = mongoClient.getDatabase("test");
//        MongoCollection<Document> collection = database.getCollection("test1");
//        Document myDoc = collection.find().first();
//        System.out.println(myDoc.toJson());
        initializeMongo();
        return "";
    }

}
