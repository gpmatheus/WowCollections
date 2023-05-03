package com.sonego.WowCollections.api.controllers;

import com.sonego.WowCollections.api.models.AuthorizationModel;
import com.sonego.WowCollections.business.services.AuthorizationService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authorization")
@AllArgsConstructor
public class AuthorizationController {

    private AuthorizationService authService;

    @PostMapping
    public ResponseEntity<AuthorizationModel> getAuthorizationToken(
            @RequestParam("code") @NonNull String code,
            @RequestParam("scope") @NonNull String scope,
            @RequestParam("redirect_uri") @NonNull String redirectURI,
            @RequestParam("grant_type") @NonNull String granType) {

        AuthorizationModel authModel = this.authService.getAccessToken(code, scope, redirectURI, granType);
        return ResponseEntity.ok(authModel);
    }
}
