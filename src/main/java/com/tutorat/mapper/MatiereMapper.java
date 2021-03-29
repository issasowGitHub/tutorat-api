package com.tutorat.mapper;

import com.tutorat.dto.request.MatiereRequest;
import com.tutorat.exception.BusinessResourceException;
import com.tutorat.model.Matiere;
import com.tutorat.service.NiveauService;
import com.tutorat.service.SerieService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class MatiereMapper {

    private static final Logger logger = LoggerFactory.getLogger(MatiereMapper.class);
    private final NiveauService nivServcice;
    private final SerieService serieService;

    public Matiere objReqToEntity(MatiereRequest req, Matiere entity) throws BusinessResourceException {
        try{
            entity.setLibelle(req.getLibelle()); entity.setSigle(req.getSigle());
            if(req.getNiveau() != null) { entity.setNiveau(nivServcice.oneById(req.getNiveau()).get()) ; }
            if(req.getSerie() != null) { entity.setSerie(serieService.oneById(req.getSerie()).get()) ; }
            return entity;
        } catch ( NoSuchElementException ex){
            logger.warn("Une valeure non autorisée est passée en paramétre. <objReqToEntity>.");
            throw new BusinessResourceException("PermetreError", "Une valeure non autorisée est passée en paramétre. <objReqToEntity>.", HttpStatus.BAD_REQUEST);
        } catch ( Exception ex){
            logger.warn("Une valeure non autorisée est passée en paramétre. <objReqToEntity>.");
            throw new BusinessResourceException("PermetreError", "Une valeure non autorisée est passée en paramétre. <objReqToEntity>.", HttpStatus.BAD_REQUEST);
        }

    }

}
