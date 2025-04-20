package edu.comillas.icai.gitt.pat.spring.p5.service;

import edu.comillas.icai.gitt.pat.spring.p5.entity.AppUser;
import edu.comillas.icai.gitt.pat.spring.p5.entity.Token;
import edu.comillas.icai.gitt.pat.spring.p5.model.ProfileRequest;
import edu.comillas.icai.gitt.pat.spring.p5.model.ProfileResponse;
import edu.comillas.icai.gitt.pat.spring.p5.model.RegisterRequest;
import edu.comillas.icai.gitt.pat.spring.p5.repository.TokenRepository;
import edu.comillas.icai.gitt.pat.spring.p5.repository.AppUserRepository;
import edu.comillas.icai.gitt.pat.spring.p5.util.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * TODO#6
 * Completa los métodos del servicio para que cumplan con el contrato
 * especificado en el interface UserServiceInterface, utilizando
 * los repositorios y entidades creados anteriormente
 */

@Service
public abstract class UserService implements UserServiceInterface {

    @Autowired private AppUserRepository appUserRepository;
    @Autowired private TokenRepository tokenRepository;
    @Autowired private Hashing hashing;

    @Override
    public Token login(String email, String password) {
        Optional<AppUser> userOptional = appUserRepository.findByEmail(email);
        if (userOptional.isEmpty()) return null;

        AppUser appUser = userOptional.get();
        if (!hashing.compare(appUser.getPassword(), password)) return null;

        tokenRepository.findByAppUser(appUser).ifPresent(tokenRepository::delete);

        Token token = new Token();
        token.setAppUser(appUser);
        return tokenRepository.save(token);
    }

    @Override
    public ProfileResponse profile(RegisterRequest register) {
        AppUser appUser = new AppUser();
        appUser.setEmail(register.email());
        appUser.setPassword(hashing.hash(register.password()));
        appUser.setName(register.name());
        appUser.setRole(register.role());
        AppUser saved = appUserRepository.save(appUser);
        return profile(saved);
    }

    @Override
    public ProfileResponse profile(AppUser appUser, ProfileRequest profile) {
        if (StringUtils.hasText(profile.name())) {
            appUser.setName(profile.name());
        }
        if (StringUtils.hasText(profile.password())) {
            appUser.setPassword(hashing.hash(profile.password()));
        }
        AppUser updated = appUserRepository.save(appUser);
        return profile(updated);
    }

}
