package com.epiis.hire.business;

import java.util.Date;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epiis.hire.dto.request.RequestCompanyRegister;
import com.epiis.hire.dto.response.ResponseCompanyRegister;
import com.epiis.hire.entity.EntityCompany;
import com.epiis.hire.entity.EntityUser;
import com.epiis.hire.repository.RepositoryCompany;
import com.epiis.hire.repository.RepositoryUser;
import com.epiis.hire.staticdata.EnumRole;

@Service
public class BusinessCompany {

    private final RepositoryUser repositoryUser;
    private final RepositoryCompany repositoryCompany;
    private final PasswordEncoder passwordEncoder;

    public BusinessCompany(
            RepositoryUser repositoryUser,
            RepositoryCompany repositoryCompany,
            PasswordEncoder passwordEncoder
    ) {
        this.repositoryUser = repositoryUser;
        this.repositoryCompany = repositoryCompany;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public ResponseCompanyRegister register(RequestCompanyRegister request) {
        ResponseCompanyRegister response = new ResponseCompanyRegister();

        if (repositoryUser.existsByEmail(request.getEmail())) {
            response.listMessage.add("Ya existe una cuenta registrada con ese correo empresarial.");
            return response;
        }

        Date now = new Date();

        EntityUser entityUser = new EntityUser();
        entityUser.setIdUser(UUID.randomUUID().toString());
        entityUser.setFirstName(request.getName());
        entityUser.setSurName("");
        entityUser.setEmail(request.getEmail());
        entityUser.setPassword(passwordEncoder.encode(request.getPassword()));
        entityUser.setRole(EnumRole.COMPANY.toString());
        entityUser.setActive(true);
        entityUser.setCreatedAt(now);
        entityUser.setUpdatedAt(now);
        repositoryUser.save(entityUser);

        EntityCompany entityCompany = new EntityCompany();
        entityCompany.setIdCompany(UUID.randomUUID().toString());
        entityCompany.setIdUser(entityUser.getIdUser());
        entityCompany.setName(request.getName());
        entityCompany.setDescription(request.getDescription());
        entityCompany.setSector(request.getSector());
        entityCompany.setLocation(request.getLocation());
        entityCompany.setWebsite(request.getWebsite());
        entityCompany.setApproved(false);
        entityCompany.setCreatedAt(now);
        entityCompany.setUpdatedAt(now);
        repositoryCompany.save(entityCompany);

        response.setIdUser(entityUser.getIdUser());
        response.setIdCompany(entityCompany.getIdCompany());
        response.setEmail(entityUser.getEmail());
        response.success();
        response.listMessage.add("Cuenta de empresa creada. Queda pendiente de aprobación por el equipo de PrimeHire.");

        return response;
    }
}
