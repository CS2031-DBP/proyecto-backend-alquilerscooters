package com.example.alquiler_scooters.auth.google;

import com.example.alquiler_scooters.auth.CustomUserDetails;
import com.example.alquiler_scooters.config.JwtService;
import com.example.alquiler_scooters.usuario.domain.Role;
import com.example.alquiler_scooters.usuario.domain.Usuario;
import com.example.alquiler_scooters.usuario.domain.UserLogin;
import com.example.alquiler_scooters.usuario.infrastructure.UsuarioRepository;
import com.example.alquiler_scooters.usuario.infrastructure.UserLoginRepository;
import com.google.api.client.json.webtoken.JsonWebSignature;
import com.google.api.client.json.webtoken.JsonWebToken;
import com.google.auth.oauth2.TokenVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class GoogleService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public GoogleTokenResponse validate(GoogleTokenRequest request) {
        TokenVerifier verifier = TokenVerifier.newBuilder().build();

        try {
            JsonWebSignature jsonWebSignature = verifier.verify(request.getToken());
            JsonWebToken.Payload payload = jsonWebSignature.getPayload();

            String email = (String) payload.get("email");
            String name = (String) payload.get("name");
            String givenName = (String) payload.get("given_name");
            String familyName = (String) payload.get("family_name");

            Optional<UserLogin> userLoginOpt = userLoginRepository.findByProviderAndProviderId("google", payload.getSubject());

            Usuario usuario;
            if (userLoginOpt.isPresent()) {
                usuario = userLoginOpt.get().getUsuario();
            } else {
                usuario = usuarioRepository.findByEmail(email)
                        .orElseGet(() -> {
                            Usuario newUser = new Usuario();
                            newUser.setNombre(name != null ? name : givenName + " " + familyName);
                            newUser.setEmail(email);

                            // Generate and encode a random password
                            String randomPassword = PasswordGenerator.generateRandomPassword();
                            newUser.setContrasena(passwordEncoder.encode(randomPassword));

                            newUser.setRole(Role.USER); // Default role USER
                            newUser.setFechaRegistro(LocalDate.now());
                            // Set other fields as necessary
                            return usuarioRepository.save(newUser);
                        });

                UserLogin userLogin = new UserLogin();
                userLogin.setUsuario(usuario);
                userLogin.setProvider("google");
                userLogin.setProviderId(payload.getSubject());
                userLoginRepository.save(userLogin);
            }

            CustomUserDetails userDetails = new CustomUserDetails(usuario);
            String jwtToken = jwtService.generateToken(userDetails);

            // Log the token
            System.out.println("Generated JWT token for Google login: " + jwtToken);

            return new GoogleTokenResponse(true, jwtToken);
        } catch (TokenVerifier.VerificationException e) {
            return new GoogleTokenResponse(false, null);
        }
    }
}
