package com.krungsri.backendtest.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResponseHandler {

    private final ObjectMapper mapper;

    @Autowired
    public ResponseHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public <T> ResponseEntity successResponse(List<T> list, HttpStatus statusCode) {
        ArrayNode arrayNode = mapper.valueToTree(list);
        ObjectNode objNode = mapper.createObjectNode();
        objNode.putArray("data").addAll(arrayNode);
        return new ResponseEntity(objNode, statusCode);
    }

    public <T> ResponseEntity successResponse(T body, HttpStatus statusCode) {
        ObjectNode objNode = mapper.createObjectNode();
        objNode.putPOJO("data", body);
        return new ResponseEntity(objNode, statusCode);
    }

    public ResponseEntity failureResponse(String message, HttpStatus statusCode) {
        ObjectNode objNode = mapper.createObjectNode();
        objNode.put("error", message);
        return new ResponseEntity(objNode, statusCode);
    }
}
