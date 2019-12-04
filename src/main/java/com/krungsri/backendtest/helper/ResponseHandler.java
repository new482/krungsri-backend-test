package com.krungsri.backendtest.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResponseHandler {

    private final ObjectMapper mapper;

    @Autowired
    public ResponseHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> ObjectNode successResponse(List<T> list, int statusCode) {
        ArrayNode arrayNode = mapper.valueToTree(list);
        ObjectNode objNode = mapper.createObjectNode();
        objNode.putArray("data").addAll(arrayNode);
        objNode.put("code", statusCode);
        return objNode;
    }

    public <T> ObjectNode successResponse(T body, int statusCode) {
        ObjectNode objNode = mapper.createObjectNode();
        objNode.putPOJO("data", body);
        objNode.put("code", statusCode);
        return objNode;
    }

    public ObjectNode failureResponse(String message, int statusCode) {
        ObjectNode objNode = mapper.createObjectNode();
        objNode.put("error", message);
        objNode.put("code", statusCode);
        return objNode;
    }
}
