package model;

public class GetTweetResponse {

    private Data data;

    public Data getData() {
        return data;
    }

    public class Data {

        private String id;
        private String text;

        public String getId() {
            return id;
        }

        public String getText() {
            return text;
        }
    }
}
