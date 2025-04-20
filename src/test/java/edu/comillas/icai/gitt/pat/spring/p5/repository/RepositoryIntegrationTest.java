package edu.comillas.icai.gitt.pat.spring.p5.repository;

import edu.comillas.icai.gitt.pat.spring.p5.entity.AppUser;
import edu.comillas.icai.gitt.pat.spring.p5.entity.Token;
import edu.comillas.icai.gitt.pat.spring.p5.model.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class RepositoryIntegrationTest {
    @Autowired TokenRepository tokenRepository;
    @Autowired AppUserRepository appUserRepository;

    /**
     * TODO#9
     * Completa este test de integración para que verifique
     * que los repositorios TokenRepository y AppUserRepository guardan
     * los datos correctamente, y las consultas por AppToken y por email
     * definidas respectivamente en ellos retornan el token y usuario guardados.
     */
    @Test
    void saveTest() {
        // Given
        AppUser user = new AppUser();
        user.setEmail("test@email.com");
        user.setPassword("hashed123");
        user.setName("Test User");
        user.setRole(Role.USER);
        AppUser savedUser = appUserRepository.save(user);

        Token token = new Token();
        token.setAppUser(savedUser);
        Token savedToken = tokenRepository.save(token);

        // When
        AppUser foundUser = appUserRepository.findByEmail("test@email.com").orElse(null);
        Token foundToken = tokenRepository.findByAppUser(savedUser).orElse(null);

        // Then
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(savedUser.getEmail(), foundUser.getEmail());

        Assertions.assertNotNull(foundToken);
        Assertions.assertEquals(savedUser.getId(), foundToken.getAppUser().getId());
    }


    /**
     * TODO#10
     * Completa este test de integración para que verifique que
     * cuando se borra un usuario, automáticamente se borran sus tokens asociados.
     */
    @Test
    void deleteCascadeTest() {
        // Given
        AppUser user = new AppUser();
        user.setEmail("test2@email.com");
        user.setPassword("pass");
        user.setName("User 2");
        user.setRole(Role.USER);
        user = appUserRepository.save(user);

        Token token = new Token();
        token.setAppUser(user);
        tokenRepository.save(token);

        // When
        appUserRepository.delete(user);

        // Then
        Assertions.assertEquals(0, appUserRepository.count());
        Assertions.assertEquals(0, tokenRepository.count());
    }

}