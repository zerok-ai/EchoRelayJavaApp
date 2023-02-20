package ai.zerok.echorelayapp.parser;

import java.util.Map;

//@JsonIgnoreProperties(value = {"abc"})
public class ResponseSpec {

    private int status;

    private Map<String, String> headers;

    private String data;

    private String statusText;

    private String json;

    private Map<String, String> response;

    public ResponseSpec() {
    }

    public ResponseSpec(int status, Map<String, String> headers, String data, String statusText, String json, Map<String, String> response) {
        this.status = status;
        this.headers = headers;
        this.data = data;
        this.statusText = statusText;
        this.json = json;
        this.response = response;
    }


    public void setStatus(int status) {
        this.status = status;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public void setResponse(Map<String, String> response) {
        this.response = response;
    }

    public int getStatus() {
        return status;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getData() {
        return data;
    }

    public String getStatusText() {
        return statusText;
    }

    public String getJson() {
        return json;
    }

    public Map<String, String> getResponse() {
        return response;
    }
}
