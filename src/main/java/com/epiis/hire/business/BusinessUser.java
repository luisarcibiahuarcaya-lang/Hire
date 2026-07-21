package com.epiis.hire.business;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.epiis.hire.dto.request.RequestUserLogin;
import com.epiis.hire.dto.request.RequestUserRegister;
import com.epiis.hire.dto.response.ResponseUserLogin;
import com.epiis.hire.dto.response.ResponseUserRegister;
import com.epiis.hire.entity.EntityCompany;
import com.epiis.hire.entity.EntityUser;
import com.epiis.hire.repository.RepositoryCompany;
import com.epiis.hire.repository.RepositoryUser;
import com.epiis.hire.staticdata.EnumRole;

@Service
public class BusinessUser {

    private final RepositoryUser repositoryUser;
    private final RepositoryCompany repositoryCompany;
    private final PasswordEncoder passwordEncoder;

    public BusinessUser(
            RepositoryUser repositoryUser,
            RepositoryCompany repositoryCompany,
            PasswordEncoder passwordEncoder
    ) {
        this.repositoryUser = repositoryUser;
        this.repositoryCompany = repositoryCompany;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseUserRegister register(RequestUserRegister request) {
        ResponseUserRegister response = new ResponseUserRegister();

        if (repositoryUser.existsByEmail(request.getEmail())) {
            response.listMessage.add("Ya existe una cuenta registrada con ese correo.");
            return response;
        }

        EntityUser entityUser = new EntityUser();
        entityUser.setIdUser(UUID.randomUUID().toString());
        entityUser.setFirstName(request.getFirstName());
        entityUser.setSurName(request.getSurName());
        entityUser.setEmail(request.getEmail());
        entityUser.setPassword(passwordEncoder.encode(request.getPassword()));
        entityUser.setRole(EnumRole.CANDIDATE.toString());
        entityUser.setActive(true);
        entityUser.setCreatedAt(new Date());
        entityUser.setUpdatedAt(entityUser.getCreatedAt());

        repositoryUser.save(entityUser);

        response.setIdUser(entityUser.getIdUser());
        response.setEmail(entityUser.getEmail());
        response.success();
        response.listMessage.add("Cuenta creada correctamente.");

        return response;
    }

    public ResponseUserLogin login(RequestUserLogin request) {
        ResponseUserLogin response = new ResponseUserLogin();

        Optional<EntityUser> optUser = repositoryUser.findByEmail(request.getEmail());

        if (optUser.isEmpty() || !passwordEncoder.matches(request.getPassword(), optUser.get().getPassword())) {
            response.listMessage.add("Credenciales incorrectas. Verifique su correo y contraseña.");
            return response;
        }

        EntityUser user = optUser.get();

        if (Boolean.FALSE.equals(user.getActive())) {
            response.listMessage.add("Tu cuenta se encuentra suspendida. Contacta a soporte.");
            return response;
        }

        String rawToken = user.getIdUser() + ":" + user.getEmail() + ":" + user.getRole();
        String token = java.util.Base64.getEncoder().encodeToString(rawToken.getBytes());

        response.setIdUser(user.getIdUser());
        response.setFirstName(user.getFirstName());
        response.setSurName(user.getSurName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setToken(token);

        if (EnumRole.COMPANY.toString().equals(user.getRole())) {
            Optional<EntityCompany> optCompany = repositoryCompany.findByIdUser(user.getIdUser());
            optCompany.ifPresent(company -> response.setIdCompany(company.getIdCompany()));
        }

        response.success();
        response.listMessage.add("Inicio de sesión correcto.");

        return response;
    }
}
