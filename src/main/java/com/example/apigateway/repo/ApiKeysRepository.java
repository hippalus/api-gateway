package com.example.apigateway.repo;

import com.example.apigateway.model.ApiKeys;

public interface ApiKeysRepository {

    void save(ApiKeys apiKeys);

    ApiKeys findById(String id);
}
