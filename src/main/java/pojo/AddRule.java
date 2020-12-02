package pojo;

import java.util.List;

public class AddRule {

    private List<Add> add;

    /**
     * No args constructor for use in serialization
     *
     */
    public AddRule() {
    }

    public AddRule(List<Add> add) {
        super();
        this.add = add;
    }

    public List<Add> getAdd() {
        return add;
    }

    public void setAdd(List<Add> add) {
        this.add = add;
    }

    public static class Add {

        private String value;
        private String tag;

        /**
         * No args constructor for use in serialization
         *
         */
        public Add() {
        }
        //constructor for adding only value (value is the required field in twitter api)
        public Add (String value){
            this.value = value;
        }
        //constructor for adding both vaue and tag
        public Add(String value, String tag) {
            super();
            this.value = value;
            this.tag = tag;
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

    }
}
