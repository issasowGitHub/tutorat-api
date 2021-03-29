package com.tutorat.mapper;

import com.tutorat.dao.AnneeDao;
import com.tutorat.dto.request.AnneeRequest;
import com.tutorat.dto.request.ApprenantRequest;
import com.tutorat.exception.BusinessResourceException;
import com.tutorat.model.Apprenant;
import com.tutorat.model.Utilisateur;
import com.tutorat.service.AnneeService;
import com.tutorat.service.NiveauService;
import com.tutorat.service.SerieService;
import com.tutorat.service.UtilisateurService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ApprenantMapper {

    private static final Logger logger = LoggerFactory.getLogger(ApprenantMapper.class);
    private final AnneeService anService;
    private final NiveauService nivServcice;
    private final SerieService serieService;
    private final UtilisateurService userService;

    public Apprenant objReqToEntity(ApprenantRequest req, Apprenant entity) throws BusinessResourceException {
        try{
            if(req.getAnnee() != null) { entity.setAnnee(anService.oneById(req.getAnnee()).get()) ; }
            if(req.getNiveau() != null) { entity.setNiveau(nivServcice.oneById(req.getNiveau()).get()) ; }
            if(req.getSerie() != null) { entity.setSerie(serieService.oneById(req.getSerie()).get()) ; }
            if(req.getTuteur() != null) { entity.setTuteur(userService.oneById(req.getTuteur()).get()) ; }
            if(req.getUtilisateur() != null) { entity.setUtilisateur(userService.oneById(req.getUtilisateur()).get()); }
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
