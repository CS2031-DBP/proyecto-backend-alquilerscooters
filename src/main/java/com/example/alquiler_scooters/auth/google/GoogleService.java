package com.example.alquiler_scooters.auth.google;

import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.json.webtoken.JsonWebToken;
import com.google.auth.oauth2.TokenVerifier;
import org.springframework.stereotype.Service;

@Service
public class GoogleService {
    public GoogleTokenResponse validate(GoogleTokenRequest request) {
        TokenVerifier verifier = TokenVerifier.newBuilder().build();

        try {
            JsonWebSignature jsonWebSignature = verifier.verify(request.getToken());
            JsonWebToken.Payload payload = jsonWebSignature.getPayload();
        } catch (TokenVerifier.VerificationException e) {
            return new GoogleTokenResponse(false);
        }
            return  new GoogleTokenResponse(true);
    }
}
