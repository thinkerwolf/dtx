package com.thinkerwolf.dtx.common.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public final class JsonUtil {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String toJsonString(Object obj) throws Exception {
        return OBJECT_MAPPER.writeValueAsString(obj);
    }

    public static byte[] toJsonBytes(Object obj) throws Exception {
        return OBJECT_MAPPER.writeValueAsBytes(obj);
    }

    public static <T> T toObject(String json, Class<T> clazz) throws Exception {
        return OBJECT_MAPPER.readValue(json, clazz);
    }

    public static <T> T toObject(String json, String key, Class<T> clazz) throws Exception {
        JsonNode root = OBJECT_MAPPER.readTree(json);
        JsonNode node = root.get(key);
        if (node == null) {
            throw new NullPointerException("Empty key: " + key);
        }
        return OBJECT_MAPPER.treeToValue(node, clazz);
    }

    public static <T> T toObject(byte[] json, Class<T> clazz) throws Exception {
        return OBJECT_MAPPER.readValue(json, clazz);
    }

    public static <T> T toObject(byte[] json, String key, Class<T> clazz) throws Exception {
        JsonNode root = OBJECT_MAPPER.readTree(json);
        JsonNode node = root.get(key);
        if (node == null) {
            return null;
        }
        return OBJECT_MAPPER.treeToValue(node, clazz);
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> map = new HashMap<>();
        TestBean testBean = new TestBean();
        testBean.setName("wk");
        testBean.setAge(27);
        map.put("cv", testBean);

        String json = toJsonString(map);
        System.out.println(toObject(json, "cv", TestBean.class));

    }

    public static class TestBean {
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

}
