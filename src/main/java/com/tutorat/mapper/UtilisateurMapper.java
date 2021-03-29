package com.tutorat.mapper;

import com.tutorat.config.MesConstants;
import com.tutorat.config.MyDateFormatter;
import com.tutorat.dto.request.UtilisateurRequest;
import com.tutorat.exception.BusinessResourceException;
import com.tutorat.model.AppProfil;
import com.tutorat.model.Utilisateur;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UtilisateurMapper {

    private static final Logger logger = LoggerFactory.getLogger(UtilisateurMapper.class);
    private final MyDateFormatter dateFormatter;

    public Utilisateur objReqToEntity(UtilisateurRequest req, Utilisateur entity) throws BusinessResourceException {
        try{
            entity.setPrenoms(req.getPrenoms()); entity.setNom(req.getNom()); entity.setLieuNaissance(req.getLieuNaissance());
            entity.setDateNaissance(dateFormatter.dateFormat(req.getDateNaissance(), MesConstants.DATE_FR_FORMAT_SALASH)); entity.setSexe(req.getSexe());
            entity.setSituationFamiliale(req.getSituationFamiliale()); entity.setAdresse(req.getAdresse()); entity.setTelephone(req.getTelephone());
            entity.setEmail(req.getEmail()); entity.setUsername(req.getUsername()); entity.setProfil(AppProfil.APPRENANT);
            entity.setMdpasse(req.getMdpasse());
            return entity;
        } catch (ParseException e) {
            e.printStackTrace();
            logger.warn("Date incorrect. <dateFormat>.");
            throw new BusinessResourceException("IncorrectDate", "Une date non correcte est passée en paramétre. <dateFormat>.", HttpStatus.BAD_REQUEST);
        } catch ( NoSuchElementException ex){
            logger.warn("Une valeure non autorisée est passée en paramétre. <objReqToEntity>.");
            throw new BusinessResourceException("PermetreError", "Une valeure non autorisée est passée en paramétre. <objReqToEntity>.", HttpStatus.BAD_REQUEST);
        } catch ( Exception ex){
            logger.warn("Une valeure non autorisée est passée en paramétre. <objReqToEntity>.");
            throw new BusinessResourceException("PermetreError", "Une valeure non autorisée est passée en paramétre. <objReqToEntity>.", HttpStatus.BAD_REQUEST);
        }

    }

}
