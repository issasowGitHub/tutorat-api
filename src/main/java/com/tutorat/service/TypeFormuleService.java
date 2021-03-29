package com.tutorat.service;

import com.tutorat.dao.TypeFormuleDao;
import com.tutorat.dao.UtilisateurDao;
import com.tutorat.dto.request.TypeFormuleRequest;
import com.tutorat.exception.BusinessResourceException;
import com.tutorat.mapper.TypeFormuleMapper;
import com.tutorat.model.TypeFormule;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TypeFormuleService {

    private static final Logger logger = LoggerFactory.getLogger(TypeFormuleService.class);
    private final TypeFormuleMapper mapper;
    private final TypeFormuleDao typFormuleDao;
    private final UtilisateurDao userDao;

    public Collection<TypeFormule> getAll() throws BusinessResourceException {
        logger.info("Liste de tous les type de formules. <getAll>");
        return typFormuleDao.findAll();
    }

    public Optional<TypeFormule> oneById(String id) throws NumberFormatException, NoSuchElementException, BusinessResourceException {
        try {
            Long myId = Long.valueOf(id.trim()); //getIdFromString(id);
            Optional<TypeFormule> entityFound = typFormuleDao.findById(myId);

            if(entityFound.get().getId() == null){
                logger.warn("Aucun type de formule avec  " + id + " trouve. <oneById>");
                throw new BusinessResourceException("NotFound", "TypeFormule non trouvé.", HttpStatus.NOT_FOUND);
            }

            logger.info("TypeFormule avec id: "+id+" trouve. <oneById>");
            return entityFound;
        } catch (NoSuchElementException e){
            logger.warn("Aucun type de formule avec " + id + " trouve. <oneById>.");
            throw new BusinessResourceException("NotFound", "TypeFormule non trouve.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + id + " non autorise. <oneById>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre "+id+" non autorise.", HttpStatus.BAD_REQUEST);
        } catch (BusinessResourceException e) {
            logger.error("Details type de formule: Une erreur inatteandue est rencontrée.");
            throw new BusinessResourceException("NotFound", "Aucun type de formule ne correspond à cet identifiant: "+id);
        }
    }

    @Transactional(readOnly = false)
    public TypeFormule add(TypeFormuleRequest req) throws BusinessResourceException{
        try{
            TypeFormule result = typFormuleDao.save(mapper.objReqToEntity(req, new TypeFormule()));
            result.setUtiCree(userDao.findById(1L).get());
            result = typFormuleDao.save(result);
            logger.info("TypeFormule: "+result.toString()+" ajoute avec succes. <add>.");
            result.setUtiCree(userDao.findById(1L).get());
            return  result;
        } catch (DataIntegrityViolationException e) {
            logger.error("Erreur technique de creation d'un type de formule est rencontree: donnée en doublon ou contrainte non respectée");
            throw new BusinessResourceException("SqlError", "Une erreur technique est rencontrée: donnée en doublon ou contrainte non respectée ", HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error("Ajout type de formule: Une erreur inatteandue est rencontrée.");
            throw new BusinessResourceException("SaveError", "Erreur technique de création d'un type de formule: "+req.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = false)
    public TypeFormule maj(TypeFormuleRequest req, String id) throws NumberFormatException, NoSuchElementException, BusinessResourceException {
        try{
            Long myId = Long.valueOf(id.trim()); //getIdFromString(id);
            Optional<TypeFormule> objFound = typFormuleDao.findById(myId);

            if(objFound.get().getId() == null){
                logger.warn("Aucun type de formule avec  " + id + " trouve. <oneById>");
                throw new BusinessResourceException("NotFound", "TypeFormule non trouvé.", HttpStatus.NOT_FOUND);
            }
            //  mise à jour d'un type de formule
            TypeFormule entityFound = objFound.get();
            entityFound.setUtiModifie(userDao.findById(1L).get());
            TypeFormule result = typFormuleDao.saveAndFlush(mapper.objReqToEntity(req, entityFound));
            logger.info("TypeFormule: "+result.toString()+" mis a jour avec succes. <maj>.");
            result.setUtiModifie(userDao.findById(1L).get());
            return  result;
        } catch (NoSuchElementException e){
            logger.warn("Aucun type de formule avec " + id + " trouvé. <maj>.");
            throw new BusinessResourceException("NotFound", "TypeFormule non trouvé.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id: " + id + " non autorise. <add>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre non autorise.", HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException  e) {
            logger.error("Maj type de formule: Une erreur technique est rencontree - donnee en doublon ou contrainte non respectée ");
            throw new BusinessResourceException("SqlError", "Une erreur technique est rencontrée: donnée en doublon ou contrainte non respectée ", HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error("Maj type de formule: Une erreur inatteandue est rencontree.");
            throw new BusinessResourceException("UpdateError", "Erreur technique de mise à jour d'un type de formule: "+req.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = false)
    public void del(String id) throws NumberFormatException, BusinessResourceException {
        try{
            Long myId = Long.valueOf(id.trim());//getIdFromString(id);;
            typFormuleDao.deleteById(myId);
            logger.info("TypeFormule avec identifiant "+id+" supprime avec succes. <del>.");
        } catch (NoSuchElementException e){
            logger.warn("Aucun type de formule avec " + id + " trouvée. <del>.");
            throw new BusinessResourceException("NotFound", "TypeFormule non trouve.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + id + " non autorise. <del>.");
            throw new BusinessResourceException("NotValidValueParam", "TypeFormule non trouvé.", HttpStatus.BAD_REQUEST);
        }  catch(EmptyResultDataAccessException ex){
            logger.error(String.format("Aucun type de formule trouve pour cet identifiant: "+id));
            throw new BusinessResourceException("EmptyValue", "Aucun type de formule trouvé pour cet identifiant: "+id, HttpStatus.NO_CONTENT);
        } catch(Exception ex){
            logger.error("Erreur de suppression d'un type de formule avec l'identifiant: "+id);
            throw new BusinessResourceException("DeleteError", "Erreur de suppression d'un type de formule avec l'identifiant: "+id, HttpStatus.BAD_REQUEST);
        }
    }
}
