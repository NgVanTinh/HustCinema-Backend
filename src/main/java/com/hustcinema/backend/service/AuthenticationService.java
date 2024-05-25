package com.hustcinema.backend.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hustcinema.backend.dto.request.AuthenticationRequest;
import com.hustcinema.backend.dto.request.LogoutRequest;
import com.hustcinema.backend.dto.respond.AuthenticationRespond;
import com.hustcinema.backend.model.InvalidatedToken;
import com.hustcinema.backend.model.User;
import com.hustcinema.backend.repository.InvalidateTokenRepository;
import com.hustcinema.backend.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import jakarta.mail.internet.ParseException;
import lombok.experimental.NonFinal;

@Service
public class AuthenticationService {
    
    protected long REFRESHABLE_DURATION = 36000;

    @Autowired
    private InvalidateTokenRepository invalidatedTokenRepository;
    @Autowired
    private UserRepository userRepository;

    @NonFinal
    protected static final String SIGNER_KEY = "RrLR4Dl8tqVccujm2ez/fvg/z45GzXe3mXuUaA8frJc=";
    
    public AuthenticationRespond authenticated(AuthenticationRequest request){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        var user = userRepository.findByUserName(request.getUserName())
                                .orElseThrow(() -> new RuntimeException("Username or password is incorrect!"));

        
        boolean auth = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!auth) throw new RuntimeException("Username or password is incorrect!");

        String token = generateToken(user);

        AuthenticationRespond respond = new AuthenticationRespond();
        respond.setToken(token);
        respond.setAuthenticated(auth);
        respond.setUser(user.getFirstName() + " " + user.getLastName());
        return respond; 
    }

    private String generateToken(User user){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getUserName())
                    .issuer("HustCinema Administrator")
                    .issueTime(new Date())
                    .expirationTime(new Date(
                            Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                    ))
                    .jwtID(UUID.randomUUID().toString())
                    .claim("scope", user.getRole())
                    .build();
        
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException, java.text.ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime()
                    .toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) throw new RuntimeException("Verification token failed");

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new RuntimeException("Verification token failed");

        return signedJWT;
    }

    public void logout(LogoutRequest request) throws ParseException, JOSEException, java.text.ParseException {
        try {
            var signToken = verifyToken(request.getToken(), true);

            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidatedToken invalidatedToken = new InvalidatedToken();
            invalidatedToken.setId(jit);
            invalidatedToken.setExpiredTime(expiryTime);
            invalidatedTokenRepository.save(invalidatedToken);
        } catch (RuntimeException exception){
           System.out.println("Token already expired");
        }
    }

}
