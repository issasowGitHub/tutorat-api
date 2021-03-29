package com.tutorat.service;

import com.tutorat.dao.DiplomeDao;
import com.tutorat.dao.UtilisateurDao;
import com.tutorat.dto.request.DiplomeRequest;
import com.tutorat.exception.BusinessResourceException;
import com.tutorat.mapper.DiplomeMapper;
import com.tutorat.model.Diplome;
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
public class DiplomeService {

    private static final Logger logger = LoggerFactory.getLogger(DiplomeService.class);
    private final DiplomeMapper mapper;
    private final DiplomeDao diplomeDao;
    private final UtilisateurDao userDao;

    public Collection<Diplome> getAll() throws BusinessResourceException {
        logger.info("Liste de tous les diplomes. <getAll>");
        return diplomeDao.findAll();
    }

    public Optional<Diplome> oneById(String id) throws NumberFormatException, NoSuchElementException, BusinessResourceException {
        try {
            Long myId = Long.valueOf(id.trim()); //getIdFromString(id);
            Optional<Diplome> entityFound = diplomeDao.findById(myId);

            if(entityFound.get().getId() == null){
                logger.warn("Aucun diplome avec  " + id + " trouve. <oneById>");
                throw new BusinessResourceException("NotFound", "Diplome non trouvé.", HttpStatus.NOT_FOUND);
            }

            logger.info("Diplome avec id: "+id+" trouve. <oneById>");
            return entityFound;
        } catch (NoSuchElementException e){
            logger.warn("Aucun diplome avec " + id + " trouve. <oneById>.");
            throw new BusinessResourceException("NotFound", "Diplome non trouve.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + id + " non autorise. <oneById>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre "+id+" non autorise.", HttpStatus.BAD_REQUEST);
        } catch (BusinessResourceException e) {
            logger.error("Details diplome: Une erreur inatteandue est rencontrée.");
            throw new BusinessResourceException("NotFound", "Aucun diplome ne correspond à cet identifiant: "+id);
        }
    }

    @Transactional(readOnly = false)
    public Diplome add(DiplomeRequest req) throws BusinessResourceException{
        try{
            Diplome result = mapper.objReqToEntity(req, new Diplome());
            result.setUtiCree(userDao.findById(1L).get());
            result = diplomeDao.save(result);
            logger.info("Diplome: "+result.toString()+" ajoute avec succes. <add>.");
            result.setUtiCree(userDao.findById(1L).get());
            return  result;
        } catch (DataIntegrityViolationException e) {
            logger.error("Erreur technique de creation d'un diplome est rencontree: donnée en doublon ou contrainte non respectée");
            throw new BusinessResourceException("SqlError", "Une erreur technique est rencontrée: donnée en doublon ou contrainte non respectée ", HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error("Ajout diplome: Une erreur inatteandue est rencontrée.");
            throw new BusinessResourceException("SaveError", "Erreur technique de création d'un diplome: "+req.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = false)
    public Diplome maj(DiplomeRequest req, String id) throws NumberFormatException, NoSuchElementException, BusinessResourceException {
        try{
            Long myId = Long.valueOf(id.trim()); //getIdFromString(id);
            Optional<Diplome> objFound = diplomeDao.findById(myId);

            if(objFound.get().getId() == null){
                logger.warn("Aucun diplome avec  " + id + " trouve. <oneById>");
                throw new BusinessResourceException("NotFound", "Diplome non trouvé.", HttpStatus.NOT_FOUND);
            }
            //  mise à jour d'un diplome
            Diplome entityFound = objFound.get();
            entityFound.setUtiModifie(userDao.findById(1L).get());
            Diplome result = diplomeDao.saveAndFlush(mapper.objReqToEntity(req, entityFound));
            logger.info("Diplome: "+result.toString()+" mis a jour avec succes. <maj>.");
            return  result;
        } catch (NoSuchElementException e){
            logger.warn("Aucun diplome avec " + id + " trouvé. <maj>.");
            throw new BusinessResourceException("NotFound", "Apprenant non trouvé.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id: " + id + " non autorise. <add>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre non autorise.", HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException  e) {
            logger.error("Maj diplome: Une erreur technique est rencontree - donnee en doublon ou contrainte non respectée ");
            throw new BusinessResourceException("SqlError", "Une erreur technique est rencontrée: donnée en doublon ou contrainte non respectée ", HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error("Maj diplome: Une erreur inatteandue est rencontree.");
            throw new BusinessResourceException("UpdateError", "Erreur technique de mise à jour d'un diplome: "+req.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = false)
    public void del(String id) throws NumberFormatException, BusinessResourceException {
        try{
            Long myId = Long.valueOf(id.trim());//getIdFromString(id);;
            diplomeDao.deleteById(myId);
            logger.info("Diplome avec identifiant "+id+" supprime avec succes. <del>.");
        } catch (NoSuchElementException e){
            logger.warn("Aucun diplome avec " + id + " trouvée. <del>.");
            throw new BusinessResourceException("NotFound", "Diplome non trouve.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + id + " non autorise. <del>.");
            throw new BusinessResourceException("NotValidValueParam", "Diplome non trouvé.", HttpStatus.BAD_REQUEST);
        }  catch(EmptyResultDataAccessException ex){
            logger.error(String.format("Aucun diplome trouve pour cet identifiant: "+id));
            throw new BusinessResourceException("EmptyValue", "Aucun diplome trouvé pour cet identifiant: "+id, HttpStatus.NO_CONTENT);
        } catch(Exception ex){
            logger.error("Erreur de suppression du diplome avec l'identifiant: "+id);
            throw new BusinessResourceException("DeleteError", "Erreur de suppression du diplome avec l'identifiant: "+id, HttpStatus.BAD_REQUEST);
        }
    }
}
