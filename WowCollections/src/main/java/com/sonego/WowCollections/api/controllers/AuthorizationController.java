package com.sonego.WowCollections.api.controllers;

import com.sonego.WowCollections.api.models.AuthorizationModel;
import com.sonego.WowCollections.business.services.AuthorizationService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authorization")
@AllArgsConstructor
public class AuthorizationController {

    private AuthorizationService authService;

    @PostMapping
    @CrossOrigin(value = "http://192.168.1.107:4200", methods = RequestMethod.POST)
    public ResponseEntity<AuthorizationModel> getAuthorizationToken(
            @RequestParam("code") @NonNull String code,
            @RequestParam("scope") @NonNull String scope,
            @RequestParam("redirect_uri") @NonNull String redirectURI,
            @RequestParam("grant_type") @NonNull String grantType) {

        AuthorizationModel authModel = this.authService.getAccessToken(code, scope, redirectURI, grantType);
        return ResponseEntity.ok(authModel);
    }
}
