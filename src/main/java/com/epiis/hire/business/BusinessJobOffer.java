package com.epiis.hire.business;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.epiis.hire.dto.request.RequestJobOfferInsert;
import com.epiis.hire.dto.response.ResponseJobOfferGetAll;
import com.epiis.hire.dto.response.ResponseJobOfferGetById;
import com.epiis.hire.dto.response.ResponseJobOfferInsert;
import com.epiis.hire.entity.EntityJobOffer;
import com.epiis.hire.repository.RepositoryJobOffer;
import com.epiis.hire.staticdata.EnumJobOfferStatus;

@Service
public class BusinessJobOffer {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private final RepositoryJobOffer repositoryJobOffer;

    public BusinessJobOffer(RepositoryJobOffer repositoryJobOffer) {
        this.repositoryJobOffer = repositoryJobOffer;
    }

    public ResponseJobOfferInsert insert(RequestJobOfferInsert request) {
        ResponseJobOfferInsert response = new ResponseJobOfferInsert();

        Date now = new Date();

        EntityJobOffer entity = new EntityJobOffer();
        entity.setIdJobOffer(UUID.randomUUID().toString());
        entity.setIdCompany(request.getIdCompany());
        entity.setIdCategory(request.getIdCategory());
        entity.setTitle(request.getTitle());
        entity.setDescription(request.getDescription());
        entity.setSalary(request.getSalary());
        entity.setModality(request.getModality());
        entity.setLocation(request.getLocation());
        entity.setStatus(EnumJobOfferStatus.PUBLISHED.toString());
        entity.setPublishedAt(now);
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);

        repositoryJobOffer.save(entity);

        response.setIdJobOffer(entity.getIdJobOffer());
        response.success();
        response.listMessage.add("Oferta publicada correctamente.");

        return response;
    }

    public ResponseJobOfferGetAll getAllPublished() {
        ResponseJobOfferGetAll response = new ResponseJobOfferGetAll();

        List<EntityJobOffer> listEntity = repositoryJobOffer.findAllPublished(EnumJobOfferStatus.PUBLISHED.toString());

        for (EntityJobOffer entity : listEntity) {
            response.listJobOffer.add(toItem(entity));
        }

        response.success();

        return response;
    }

    public ResponseJobOfferGetAll getByCompany(String idCompany) {
        ResponseJobOfferGetAll response = new ResponseJobOfferGetAll();

        List<EntityJobOffer> listEntity = repositoryJobOffer.findByCompanyWithDetails(idCompany);

        for (EntityJobOffer entity : listEntity) {
            response.listJobOffer.add(toItem(entity));
        }

        response.success();

        return response;
    }

    public ResponseJobOfferGetById getById(String idJobOffer) {
        ResponseJobOfferGetById response = new ResponseJobOfferGetById();

        Optional<EntityJobOffer> optEntity = repositoryJobOffer.findByIdWithDetails(idJobOffer);

        if (optEntity.isEmpty()) {
            response.listMessage.add("No se encontró la oferta de trabajo solicitada.");
            return response;
        }

        EntityJobOffer entity = optEntity.get();

        response.setIdJobOffer(entity.getIdJobOffer());
        response.setTitle(entity.getTitle());
        response.setDescription(entity.getDescription());
        response.setCompanyName(entity.getParentCompany() != null ? entity.getParentCompany().getName() : "");
        response.setCategoryName(entity.getParentCategory() != null ? entity.getParentCategory().getName() : "");
        response.setSalary(entity.getSalary());
        response.setModality(entity.getModality());
        response.setLocation(entity.getLocation());
        response.setStatus(entity.getStatus());
        response.setPublishedAt(entity.getPublishedAt() != null ? DATE_FORMAT.format(entity.getPublishedAt()) : "");

        response.success();

        return response;
    }

    public ResponseJobOfferInsert updateStatus(String idJobOffer, String status) {
        ResponseJobOfferInsert response = new ResponseJobOfferInsert();

        Optional<EntityJobOffer> optEntity = repositoryJobOffer.findById(idJobOffer);

        if (optEntity.isEmpty()) {
            response.listMessage.add("No se encontró la oferta de trabajo.");
            return response;
        }

        EntityJobOffer entity = optEntity.get();
        entity.setStatus(status);
        entity.setUpdatedAt(new Date());
        repositoryJobOffer.save(entity);

        response.setIdJobOffer(entity.getIdJobOffer());
        response.success();
        response.listMessage.add("Estado de la oferta actualizado correctamente.");

        return response;
    }

    private ResponseJobOfferGetAll.ItemJobOffer toItem(EntityJobOffer entity) {
        ResponseJobOfferGetAll.ItemJobOffer item = new ResponseJobOfferGetAll.ItemJobOffer();
        item.idJobOffer = entity.getIdJobOffer();
        item.title = entity.getTitle();
        item.companyName = entity.getParentCompany() != null ? entity.getParentCompany().getName() : "";
        item.categoryName = entity.getParentCategory() != null ? entity.getParentCategory().getName() : "";
        item.salary = entity.getSalary();
        item.modality = entity.getModality();
        item.location = entity.getLocation();
        item.status = entity.getStatus();
        item.publishedAt = entity.getPublishedAt() != null ? DATE_FORMAT.format(entity.getPublishedAt()) : "";
        return item;
    }
}
