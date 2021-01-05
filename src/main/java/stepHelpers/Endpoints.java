package stepHelpers;

public enum Endpoints {
    GET_TWEET("tweets"),
    GET_TWEET_ID("tweets/1336443999071195137"),
    POST_TWEET("statuses/update.json"),
    RULES("tweets/search/stream/rules");

    private String endpoint;

    Endpoints(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}
