//package com.secsystem.emr.shared.restclient;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//
//@Component
//public class RuleUtil {
//
//    private final RestTemplate template;
//    HttpHeaders headers = null;
//    ObjectMapper objectMapper = null;
//
//
//    @Value("${jsonPlaceholderUrl.url}")
//    private String jsonPlaceholderUrl;
//
//    public RuleUtil(RestTemplate template) {
//        this.template = template;
//    }
//
//    @PostConstruct
//    public void init(){
//        headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.add("Accept", "*/*");
//        objectMapper = new ObjectMapper();
//    }
//    public ApiResponse createPostHandler(CreateJsonPlaceHolderPostRequest request) {
//        String jsonRequest;
//        try {
//
//            jsonRequest = objectMapper.writeValueAsString(request);
//            HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);
//            String result = template.postForObject(jsonPlaceholderUrl, entity, String.class);
//
//            JsonNode jsonResponse = objectMapper.readTree(result);
//
//            JsonHolder jsonHolder = new JsonHolder();
//            jsonHolder.setBody(request.getBody());
//            jsonHolder.setTitle(request.getTitle());
//            jsonHolder.setUserId(request.getUserId());
//            jsonHolderRepository.save(jsonHolder);
//
//            ApiResponse apiResponse = new ApiResponse();
//            apiResponse.setData(jsonResponse); // Setting the deserialized JSON
//            apiResponse.setStatus(true);
//            apiResponse.setMessage("Success");
//            return apiResponse;
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public ApiResponse getPostHandler() {
//        String result = template.getForObject(jsonPlaceholderUrl, String.class);
//        try {
//            JsonNode jsonResponse = objectMapper.readTree(result);
//
//            ApiResponse apiResponse = new ApiResponse();
//            apiResponse.setData(jsonResponse); // Setting the deserialized JSON
//            apiResponse.setStatus(true);
//            apiResponse.setMessage("Success");
//            return apiResponse;
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    public ApiResponse createPostHandlerWithAuthorization(CreateJsonPlaceHolderPostRequest request, String token) {
//        String jsonRequest;
//        try {
//            jsonRequest = objectMapper.writeValueAsString(request);
//            headers.add("Authorization", "Bearer " + token);
//            HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);
//            String result = template.postForObject(jsonPlaceholderUrl, entity, String.class);
//
//            JsonNode jsonResponse = objectMapper.readTree(result);
//
//            ApiResponse apiResponse = new ApiResponse();
//            apiResponse.setData(jsonResponse);
//            apiResponse.setStatus(true);
//            apiResponse.setMessage("Success");
//            return apiResponse;
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//
//
//}
