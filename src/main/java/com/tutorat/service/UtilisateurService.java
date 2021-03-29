package com.tutorat.service;

import com.tutorat.dao.UtilisateurDao;
import com.tutorat.dto.request.UtilisateurRequest;
import com.tutorat.exception.BusinessResourceException;
import com.tutorat.mapper.UtilisateurMapper;
import com.tutorat.model.Utilisateur;
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
public class UtilisateurService {

    private static final Logger logger = LoggerFactory.getLogger(UtilisateurService.class);
    private final UtilisateurMapper mapper;
    private final UtilisateurDao userDao;

    public Collection<Utilisateur> getAll() throws BusinessResourceException {
        logger.info("Liste de tous les utilisateurs. <getAll>");
        return userDao.findAll();
        //return IteratorUtils.toList(userDao.findAll().iterator());
    }

    public Optional<Utilisateur> oneById(String id) throws NumberFormatException, NoSuchElementException, BusinessResourceException {
        try {
            Long myId = Long.valueOf(id.trim()); //getIdFromString(id);
            Optional<Utilisateur> entityFound = userDao.findById(myId);

            if(entityFound.get().getId() == null){
                logger.warn("Aucun utilisateur avec  " + id + " trouvé. <oneById>");
                throw new BusinessResourceException("NotFound", "Utilisateur non trouvé.", HttpStatus.NOT_FOUND);
            }

            logger.info("Utilisateur avec id: "+id+" trouve. <oneById>");
            return entityFound;
        } catch (NoSuchElementException e){
            logger.warn("Aucun utilisateur avec " + id + " trouve. <oneById>.");
            throw new BusinessResourceException("NotFound", "Utilisateur non trouve.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + id + " non autorise. <oneById>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre "+id+" non autorise.", HttpStatus.BAD_REQUEST);
        } catch (BusinessResourceException e) {
            logger.error("Details utilisateur: Une erreur inatteandue est rencontrée.");
            throw new BusinessResourceException("NotFound", "Aucun utilisateur ne correspond à cet identifiant: "+id);
        }
    }

    @Transactional(readOnly = false)
    public Utilisateur add(UtilisateurRequest req) throws BusinessResourceException{
        try{
            Utilisateur result = mapper.objReqToEntity(req, new Utilisateur());
            result.setUtiCree(1L);
            result = userDao.save(result);
            logger.info("Utilisateur: "+result.toString()+" ajoute avec succes. <add>.");
            return  result;
        } catch (DataIntegrityViolationException e) {
            logger.error("Erreur technique de creation d'un utilisateur est rencontree: donnée en doublon ou contrainte non respectée");
            throw new BusinessResourceException("SqlError", "Une erreur technique est rencontrée: donnée en doublon ou contrainte non respectée ", HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error("Ajout utilisateur: Une erreur inatteandue est rencontrée.");
            throw new BusinessResourceException("SaveError", "Erreur technique de création d'un utilisateur: "+req.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = false)
    public Utilisateur maj(UtilisateurRequest req, String id) throws NumberFormatException, NoSuchElementException, BusinessResourceException {
        try{
            Long myId = Long.valueOf(id.trim()); //getIdFromString(id);
            Optional<Utilisateur> objFound = userDao.findById(myId);

            if(objFound.get().getId() == null){
                logger.warn("Aucun utilisateur avec  " + id + " trouvé. <oneById>");
                throw new BusinessResourceException("NotFound", "Utilisateur non trouvé.", HttpStatus.NOT_FOUND);
            }
            //  mise à jour d'un utilisaeur
            Utilisateur entityFound = objFound.get();
            entityFound.setUtiModifie(1L);
            Utilisateur result = userDao.saveAndFlush(mapper.objReqToEntity(req, entityFound));
            logger.info("Utilisateur: "+result.toString()+" mis a jour avec succes. <maj>.");
            return  result;
        } catch (NoSuchElementException e){
            logger.warn("Aucun utilisateur avec " + id + " trouvé. <maj>.");
            throw new BusinessResourceException("NotFound", "Utilisateur non trouvé.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id: " + id + " non autorise. <add>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre non autorise.", HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException  e) {
            logger.error("Maj utilisateur: Une erreur technique est rencontree - donnee en doublon ou contrainte non respectée ");
            throw new BusinessResourceException("SqlError", "Une erreur technique est rencontrée: donnée en doublon ou contrainte non respectée ", HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error("Maj utilisateur: Une erreur inatteandue est rencontree.");
            throw new BusinessResourceException("UpdateError", "Erreur technique de mise à jour d'un utilisateur: "+req.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = false)
    public void del(String id) throws NumberFormatException, BusinessResourceException {
        try{
            Long myId = Long.valueOf(id.trim());//getIdFromString(id);;
            userDao.deleteById(myId);
            logger.info("Utilisateur avec identifiant "+id+" supprime avec succes. <del>.");
        } catch (NoSuchElementException e){
            logger.warn("Aucun utilisateur avec " + id + " trouve. <del>.");
            throw new BusinessResourceException("NotFound", "Utilisateur non trouve.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + id + " non autorise. <del>.");
            throw new BusinessResourceException("NotValidValueParam", "Utilisateur non trouve.", HttpStatus.BAD_REQUEST);
        }  catch(EmptyResultDataAccessException ex){
            logger.error(String.format("Aucun utilisateur trouve pour cet identifiant: "+id));
            throw new BusinessResourceException("EmptyValue", "Aucun utilisateur trouve pour cet identifiant: "+id, HttpStatus.NO_CONTENT);
        } catch(Exception ex){
            logger.error("Erreur de suppression d'un utilisateur avec l'identifiant: "+id);
            throw new BusinessResourceException("DeleteError", "Erreur de suppression d'un utilisateur avec l'identifiant: "+id, HttpStatus.BAD_REQUEST);
        }
    }
}
