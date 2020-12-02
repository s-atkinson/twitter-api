public class Payload {

    public String addRules(String value, String tag){
        return "{\n" +
                "  \"add\": [\n" +
                "    {\n" +
                "      \"value\": \""+value+"\",\n" +
                "      \"tag\": \""+tag+"\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    public String deleteRules(String id){
        return "{\n" +
                "  \"delete\": {\n" +
                "      \"ids\": [\""+id+"\"]\n" +
                "    }\n" +
                "}";
    }
}
