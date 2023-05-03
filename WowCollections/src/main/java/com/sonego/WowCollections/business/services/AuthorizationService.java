package com.sonego.WowCollections.business.services;

import com.sonego.WowCollections.api.models.AuthorizationModel;

public interface AuthorizationService {

    public AuthorizationModel getAccessToken(String code, String scope, String redirectURI, String grantType);
}
