package com.epiis.hire.business;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.epiis.hire.dto.request.RequestApplicationInsert;
import com.epiis.hire.dto.response.ResponseApplicationGetAll;
import com.epiis.hire.dto.response.ResponseApplicationInsert;
import com.epiis.hire.entity.EntityApplication;
import com.epiis.hire.repository.RepositoryApplication;
import com.epiis.hire.staticdata.EnumApplicationStatus;

@Service
public class BusinessApplication {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private final RepositoryApplication repositoryApplication;

    public BusinessApplication(RepositoryApplication repositoryApplication) {
        this.repositoryApplication = repositoryApplication;
    }

    public ResponseApplicationInsert insert(RequestApplicationInsert request) {
        ResponseApplicationInsert response = new ResponseApplicationInsert();

        boolean alreadyApplied = repositoryApplication
                .existsByIdJobOfferAndIdUser(request.getIdJobOffer(), request.getIdUser());

        if (alreadyApplied) {
            response.warning();
            response.listMessage.add("Ya te has postulado a esta oferta anteriormente.");
            return response;
        }

        Date now = new Date();

        EntityApplication entity = new EntityApplication();
        entity.setIdApplication(UUID.randomUUID().toString());
        entity.setIdJobOffer(request.getIdJobOffer());
        entity.setIdUser(request.getIdUser());
        entity.setStatus(EnumApplicationStatus.PENDING.toString());
        entity.setAppliedAt(now);
        entity.setUpdatedAt(now);

        repositoryApplication.save(entity);

        response.setIdApplication(entity.getIdApplication());
        response.success();
        response.listMessage.add("Postulación enviada correctamente.");

        return response;
    }

    public ResponseApplicationGetAll getByUser(String idUser) {
        ResponseApplicationGetAll response = new ResponseApplicationGetAll();

        List<EntityApplication> listEntity = repositoryApplication.findByUserWithDetails(idUser);

        for (EntityApplication entity : listEntity) {
            ResponseApplicationGetAll.ItemApplication item = new ResponseApplicationGetAll.ItemApplication();
            item.idApplication = entity.getIdApplication();
            item.idJobOffer = entity.getIdJobOffer();
            item.jobTitle = entity.getParentJobOffer() != null ? entity.getParentJobOffer().getTitle() : "";
            item.companyName = entity.getParentJobOffer() != null && entity.getParentJobOffer().getParentCompany() != null
                    ? entity.getParentJobOffer().getParentCompany().getName() : "";
            item.status = entity.getStatus();
            item.appliedAt = entity.getAppliedAt() != null ? DATE_FORMAT.format(entity.getAppliedAt()) : "";
            response.listApplication.add(item);
        }

        response.success();

        return response;
    }

    public ResponseApplicationGetAll getByJobOffer(String idJobOffer) {
        ResponseApplicationGetAll response = new ResponseApplicationGetAll();

        List<EntityApplication> listEntity = repositoryApplication.findByJobOfferWithDetails(idJobOffer);

        for (EntityApplication entity : listEntity) {
            ResponseApplicationGetAll.ItemApplication item = new ResponseApplicationGetAll.ItemApplication();
            item.idApplication = entity.getIdApplication();
            item.idJobOffer = entity.getIdJobOffer();
            item.candidateName = entity.getParentUser() != null
                    ? entity.getParentUser().getFirstName() + " " + entity.getParentUser().getSurName() : "";
            item.candidateEmail = entity.getParentUser() != null ? entity.getParentUser().getEmail() : "";
            item.status = entity.getStatus();
            item.appliedAt = entity.getAppliedAt() != null ? DATE_FORMAT.format(entity.getAppliedAt()) : "";
            response.listApplication.add(item);
        }

        response.success();

        return response;
    }

    public ResponseApplicationInsert updateStatus(String idApplication, String status) {
        ResponseApplicationInsert response = new ResponseApplicationInsert();

        Optional<EntityApplication> optEntity = repositoryApplication.findById(idApplication);

        if (optEntity.isEmpty()) {
            response.listMessage.add("No se encontró la postulación.");
            return response;
        }

        EntityApplication entity = optEntity.get();
        entity.setStatus(status);
        entity.setUpdatedAt(new Date());
        repositoryApplication.save(entity);

        response.setIdApplication(entity.getIdApplication());
        response.success();
        response.listMessage.add("Estado de la postulación actualizado correctamente.");

        return response;
    }
}
