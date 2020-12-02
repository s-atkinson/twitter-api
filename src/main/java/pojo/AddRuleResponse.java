package pojo;

import java.util.List;
//Task: Try to get this working without using static inner classes
public class AddRuleResponse {

    private List<Data> data = null;
    private Meta meta;
    //default constructor
    //without this no-arg constructor i get error: "Cannot construct instance of `pojo.AddRuleResponse` (no Creators, like default constructor, exist): cannot deserialize from Object value (no delegate- or property-based Creator)"
    //A constructor with or without parameters has a purpose of creating an object in order to access the methods in its class. With the no parameter constructor, you are able to create the object in order to access the methods in its class.
    public AddRuleResponse() {
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) { this.data = data; }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
    //if not static i get the error: "non-static inner classes like this can only by instantiated using default, no-argument constructor"
    //is it safe to use static inner classes here?
    public static class Data {

        private String value;
        private String tag;
        private String id;

        public Data() {
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class Meta {

        private String sent;
        private Summary summary;

        public Meta() {
        }

        public String getSent() {
            return sent;
        }

        public void setSent(String sent) {
            this.sent = sent;
        }

        public Summary getSummary() {
            return summary;
        }

        public void setSummary(Summary summary) {
            this.summary = summary;
        }

    }

    public static class Summary {

        private Integer created;
        private Integer not_created;
        private Integer valid;
        private Integer invalid;

        public Summary() {
        }

        public Integer getCreated() {
            return created;
        }

        public void setCreated(Integer created) {
            this.created = created;
        }

        public Integer getNot_created() {
            return not_created;
        }

        public void setNot_created(Integer not_created) {
            this.not_created = not_created;
        }

        public Integer getValid() {
            return valid;
        }

        public void setValid(Integer valid) {
            this.valid = valid;
        }

        public Integer getInvalid() {
            return invalid;
        }

        public void setInvalid(Integer invalid) {
            this.invalid = invalid;
        }

    }

}
