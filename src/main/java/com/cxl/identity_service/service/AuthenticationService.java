package com.cxl.identity_service.service;

import com.cxl.identity_service.dto.request.AuthenticationRequest;
import com.cxl.identity_service.dto.request.IntrospectRequest;
import com.cxl.identity_service.dto.response.AuthenticationResponse;
import com.cxl.identity_service.dto.response.IntrospectResponse;
import com.cxl.identity_service.entity.User;
import com.cxl.identity_service.exception.AppException;
import com.cxl.identity_service.exception.ErrorCode;
import com.cxl.identity_service.respository.UserRespository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);
    @Autowired
    UserRespository userRespository;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected   String SINGER_KEY;

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException,ParseException {
        var token = request.getToken();
        JWSVerifier  verifier = new MACVerifier(SINGER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiryTime=signedJWT.getJWTClaimsSet().getExpirationTime();
         var verified=signedJWT.verify(verifier);

         return IntrospectResponse.builder()
                 .valid(verified && expiryTime.after(new Date()))
                 .build();



    }
   public AuthenticationResponse authenticate (AuthenticationRequest authenticationRequest){
    User user =userRespository.findByUserName(authenticationRequest.getUserName()).orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXIT));

        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder(10);
        boolean authentication=  passwordEncoder.matches(authenticationRequest.getPassWord(), user.getPassWord());
        if(!authentication){
            throw new AppException(ErrorCode.UNAUTHENTICATION_EXCEPTION);

        }
        var token=generrateToken(authenticationRequest.getUserName());
        return  AuthenticationResponse.builder()
                .token(token)
                .authentication(true)
                .build();
   }


    private String generrateToken (String username){
        User user=(userRespository.findByUserName(username)).orElseThrow(()->new RuntimeException());
        JWSHeader header=new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet=new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("devteria.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("userid",user.getId())
                .build();
        Payload payload=new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject=new JWSObject(header,payload);

        try {
            jwsObject.sign(new MACSigner(SINGER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("can not create coken",e);
            throw new RuntimeException(e);
        }
    }
}
