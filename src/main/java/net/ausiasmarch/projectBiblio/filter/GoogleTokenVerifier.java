package net.ausiasmarch.projectBiblio.filter;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class GoogleTokenVerifier {

    @Value("${google.client.id}")
    private String CLIENT_ID;

    public GoogleIdToken.Payload verify(String idToken) {
        try {
            System.out.println("CLIENT_ID usado: " + CLIENT_ID);

            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                new GsonFactory()
            )
            .setAudience(Collections.singletonList(CLIENT_ID))
            .build();

            GoogleIdToken googleIdToken = verifier.verify(idToken);
            return googleIdToken != null ? googleIdToken.getPayload() : null;
            
        } catch (Exception e) {
            throw new RuntimeException("Error verificando token de Google", e);
        }
    }
    
}
