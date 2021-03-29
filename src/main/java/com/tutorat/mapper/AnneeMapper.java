package com.tutorat.mapper;

import com.tutorat.dto.request.AnneeRequest;
import com.tutorat.exception.BusinessResourceException;
import com.tutorat.model.Annee;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class AnneeMapper {

    private static final Logger logger = LoggerFactory.getLogger(AnneeMapper.class);

    public Annee objReqToEntity(AnneeRequest req, Annee entity) throws BusinessResourceException {
        try{
            entity.setLibelle(req.getLibelle()); entity.setSigle(req.getSigle()); entity.setActif(true);
            entity.setClosed(false); entity.setCurrent(false);
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
