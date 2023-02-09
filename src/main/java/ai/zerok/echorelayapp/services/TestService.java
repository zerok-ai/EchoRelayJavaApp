package ai.zerok.echorelayapp.services;

import ai.zerok.echorelayapp.configs.ApplicationConfiguration;
import ai.zerok.echorelayapp.configs.DatasourceConfig;
import ai.zerok.echorelayapp.utils.QueryResultListener;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private void initializeMySql() {
        //TODO: This is not working - Figure out a better solution
        //https://stackoverflow.com/a/46499472/4666116
        if (jdbcTemplate == null) {
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

    private void initializeMongo() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(datasourceConfig.getMonoguri());
            MongoDatabase database = mongoClient.getDatabase(datasourceConfig.getMongoDatabase());
            MongoCollection<Document> collection = database.getCollection("sampleCollection");

            Document document = new Document("title", "MongoDB")
                    .append("description", "database")
                    .append("likes", 100)
                    .append("url", "http://www.tutorialspoint.com/mongodb/")
                    .append("by", "tutorials point");

            collection.insertOne(document);
        }
    }

    public Object executeRawQueryMySQL(String rawQuery, QueryResultListener queryResultListener) {
        initializeMySql();
        List result = jdbcTemplate.query(rawQuery, new ResultSetExtractor<List>() {
            @Override
            public List extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Object> resultList = new ArrayList<>();
                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                //getting the column type
                int column_count = resultSetMetaData.getColumnCount();
                while (rs.next()) {
                    Map<String, Object> rowData = new HashMap<>();
                    for (int columnIndex = 1; columnIndex <= column_count; columnIndex++) {
                        rowData.put(resultSetMetaData.getColumnName(columnIndex), rs.getString(columnIndex));
                    }
                    resultList.add(rowData);
                }
                return resultList;
            }
        });

        return result;
    }

    public Object executeRawQueryMongo() {
        initializeMongo();

        MongoDatabase database = mongoClient.getDatabase(datasourceConfig.getMongoDatabase());
        MongoCollection<Document> collection = database.getCollection("sampleCollection");
        Document myDoc = collection.find().first();
        if (myDoc != null) {
           System.out.println(myDoc.toJson());
        }
        return myDoc;
    }
}
