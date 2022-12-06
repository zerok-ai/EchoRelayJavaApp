package ai.zerok.echorelayapp.utils;

public interface QueryResultListener {

    void onResult(String result);
    void onError(Throwable error);

}
