package com.sonego.WowCollections.business.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonego.WowCollections.api.models.AuthorizationModel;
import com.sonego.WowCollections.api.models.ErrorModel;
import com.sonego.WowCollections.business.exceptions.RequestErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private static String ACCESS_TOKEN_ENDPOINT = "https://us.oauth.battle.net/oauth/token";

    private static String CLIENT_ID = "930400512a0b414fab468ac8bf8102cc";

    private static String CLIENT_SECRET = System.getenv("CLIENT_SECRET");

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public AuthorizationModel getAccessToken(String code, String scope, String redirectURI, String grantType) {
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        params.put("scope", scope);
        params.put("redirect_uri", redirectURI);
        params.put("grant_type", grantType);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create(ACCESS_TOKEN_ENDPOINT + getParamsString(params)))
                .header("Authorization", "Basic " + Base64.getEncoder()
                        .encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes()))
                .build();
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == HttpStatus.OK.value())
                return objectMapper.readValue(response.body(), AuthorizationModel.class);
            throw new RequestErrorException(objectMapper.readValue(response.body(), ErrorModel.class),
                    HttpStatus.valueOf(response.statusCode()));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String getParamsString(Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('?');

        final String encodingType = "UTF-8";
        params.forEach((key, value) -> {
            try {
                stringBuilder.append(URLEncoder.encode(key, encodingType));
                stringBuilder.append('=');
                stringBuilder.append(URLEncoder.encode(value, encodingType));
                stringBuilder.append('&');
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });

        return params.size() > 0 ? stringBuilder.toString() : "";
    }
}
