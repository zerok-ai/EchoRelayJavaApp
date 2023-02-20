package ai.zerok.echorelayapp.parser;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Spec {

    @JsonProperty("username")
    private String userName;

    private String password;

    private String db;

    private String host;

    private int port;

    public Spec() {
    }

    public Spec(String userName, String password, String db, String host, int port) {
        this.userName = userName;
        this.password = password;
        this.db = db;
        this.host = host;
        this.port = port;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getDb() {
        return db;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
