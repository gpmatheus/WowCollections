package com.sonego.WowCollections.api.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthorizationModel {

    private String access_token;

    private String token_type;

    private Long expires_in;

    private String scope;

    private String sub;

}
