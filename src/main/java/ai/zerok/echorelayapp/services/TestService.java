package ai.zerok.echorelayapp.services;

import ai.zerok.echorelayapp.configs.ApplicationConfiguration;
import ai.zerok.echorelayapp.configs.DatasourceConfig;
import ai.zerok.echorelayapp.utils.QueryResultListener;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class TestService {

//    @Autowired
//    private EntityManager entityManager;
    private JdbcTemplate jdbcTemplate;

    private MongoClient mongoClient;

    @Autowired
    private ApplicationConfiguration applicationConfiguration;

    @Autowired
    private DatasourceConfig datasourceConfig;

    private void initializeMySql(){
        //TODO: This is not working - Figure out a better solution
        //https://stackoverflow.com/a/46499472/4666116
        if(jdbcTemplate == null){
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(datasourceConfig.getDriver());
            dataSource.setUrl(datasourceConfig.getUrl());
            dataSource.setUsername(datasourceConfig.getUser());
            dataSource.setPassword(datasourceConfig.getPassword());
            jdbcTemplate = new JdbcTemplate(dataSource);
//            EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
//            entityManager = emf.createEntityManager();
        }
    }

    private void initializeMongo(){
        if(mongoClient == null){
            mongoClient = MongoClients.create();
        }
    }

    public Object executeRawQUeryMySQL(String rawQuery, QueryResultListener queryResultListener) {
        initializeMySql();
        String result = jdbcTemplate.query(rawQuery, new ResultSetExtractor<String>() {
            @Override
            public String extractData(ResultSet rs) throws SQLException, DataAccessException {
                if(rs.next()){
                    return rs.getString(1);
                }
                return null;
            }
        });

        return result;
//        Query query = entityManager.createNativeQuery(rawQuery);
//        return query.getSingleResult();
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
