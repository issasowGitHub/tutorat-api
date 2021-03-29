package com.tutorat.mapper;

import com.tutorat.dto.request.JourRequest;
import com.tutorat.exception.BusinessResourceException;
import com.tutorat.model.Jour;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class JourMapper {

    private static final Logger logger = LoggerFactory.getLogger(JourMapper.class);

    public Jour objReqToEntity(JourRequest req, Jour entity) throws BusinessResourceException {
        try{
            entity.setLibelle(req.getLibelle()); entity.setSigle(req.getSigle());
            entity.setOrdre(Integer.valueOf(req.getOrdre()));
            return entity;
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + 1 + " non autorise. <oneById>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre "+1+" non autorise.", HttpStatus.BAD_REQUEST);
        } catch ( NoSuchElementException ex){
            logger.warn("Une valeure non autorisée est passée en paramétre. <objReqToEntity>.");
            throw new BusinessResourceException("PermetreError", "Une valeure non autorisée est passée en paramétre. <objReqToEntity>.", HttpStatus.BAD_REQUEST);
        } catch ( Exception ex){
            logger.warn("Une valeure non autorisée est passée en paramétre. <objReqToEntity>.");
            throw new BusinessResourceException("PermetreError", "Une valeure non autorisée est passée en paramétre. <objReqToEntity>.", HttpStatus.BAD_REQUEST);
        }

    }

}
