package com.tutorat.service;

import com.tutorat.dao.ApprenantDao;
import com.tutorat.dao.UtilisateurDao;
import com.tutorat.dto.request.AnneeRequest;
import com.tutorat.dto.request.ApprenantRequest;
import com.tutorat.exception.BusinessResourceException;
import com.tutorat.mapper.ApprenantMapper;
import com.tutorat.model.Annee;
import com.tutorat.model.Apprenant;
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
public class ApprenantService {

    private static final Logger logger = LoggerFactory.getLogger(ApprenantService.class);
    private final ApprenantMapper mapper;
    private final ApprenantDao apprenantDao;
    private final UtilisateurDao userDao;

    public Collection<Apprenant> getAll() throws BusinessResourceException {
        logger.info("Liste de tous les apprenants. <getAll>");
        return apprenantDao.findAll();
    }

    public Optional<Apprenant> oneById(String id) throws NumberFormatException, NoSuchElementException, BusinessResourceException {
        try {
            Long myId = Long.valueOf(id.trim()); //getIdFromString(id);
            Optional<Apprenant> entityFound = apprenantDao.findById(myId);

            if(entityFound.get().getId() == null){
                logger.warn("Auncu apprenant avec  " + id + " trouvé. <oneById>");
                throw new BusinessResourceException("NotFound", "Apprenant non trouvé.", HttpStatus.NOT_FOUND);
            }

            logger.info("Apprenant avec id: "+id+" trouvee. <oneById>");
            return entityFound;
        } catch (NoSuchElementException e){
            logger.warn("Aucun apprenant avec " + id + " trouvée. <oneById>.");
            throw new BusinessResourceException("NotFound", "Apprenant non trouvée.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + id + " non autorise. <oneById>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre "+id+" non autorise.", HttpStatus.BAD_REQUEST);
        } catch (BusinessResourceException e) {
            logger.error("Details apprenant: Une erreur inatteandue est rencontrée.");
            throw new BusinessResourceException("NotFound", "Aucun apprenant ne correspond à cet identifiant: "+id);
        }
    }

    @Transactional(readOnly = false)
    public Apprenant add(ApprenantRequest req) throws BusinessResourceException{
        try{
            Apprenant result = mapper.objReqToEntity(req, new Apprenant());
            result.setUtiCree(userDao.findById(1L).get());
            result = apprenantDao.save(result);
            logger.info("Apprenant: "+result.toString()+" ajouté avec succes. <add>.");

            return  result;
        } catch (DataIntegrityViolationException e) {
            logger.error("Erreur technique de creation d'un apprenant est rencontree: donnée en doublon ou contrainte non respectée");
            throw new BusinessResourceException("SqlError", "Une erreur technique est rencontrée: donnée en doublon ou contrainte non respectée ", HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error("Ajout apprenant: Une erreur inatteandue est rencontrée.");
            throw new BusinessResourceException("SaveError", "Erreur technique de création d'un apprenant: "+req.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = false)
    public Apprenant maj(ApprenantRequest req, String id) throws NumberFormatException, NoSuchElementException, BusinessResourceException {
        try{
            Long myId = Long.valueOf(id.trim()); //getIdFromString(id);
            Optional<Apprenant> objFound = apprenantDao.findById(myId);

            if(objFound.get().getId() == null){
                logger.warn("Auncu apprenant avec  " + id + " trouvé. <oneById>");
                throw new BusinessResourceException("NotFound", "Apprenant non trouvé.", HttpStatus.NOT_FOUND);
            }
            //  mise à jour d'un apprenant
            Apprenant entityFound = objFound.get();
            entityFound.setUtiModifie(userDao.findById(1L).get());
            Apprenant result = apprenantDao.saveAndFlush(mapper.objReqToEntity(req, entityFound));
            logger.info("Apprenant: "+result.toString()+" mis a jour avec succes. <maj>.");
            result.setUtiModifie(userDao.findById(1L).get());
            return  result;
        } catch (NoSuchElementException e){
            logger.warn("Aucun apprenant avec " + id + " trouvé. <maj>.");
            throw new BusinessResourceException("NotFound", "Apprenant non trouvé.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id: " + id + " non autorise. <add>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre non autorise.", HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException  e) {
            logger.error("Maj apprenant: Une erreur technique est rencontree - donnee en doublon ou contrainte non respectée ");
            throw new BusinessResourceException("SqlError", "Une erreur technique est rencontrée: donnée en doublon ou contrainte non respectée ", HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error("Maj apprenant: Une erreur inatteandue est rencontree.");
            throw new BusinessResourceException("UpdateError", "Erreur technique de mise à jour d'un apprenant: "+req.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = false)
    public void del(String id) throws NumberFormatException, BusinessResourceException {
        try{
            Long myId = Long.valueOf(id.trim());//getIdFromString(id);;
            apprenantDao.deleteById(myId);
            logger.info("Apprenant avec identifiant "+id+" supprime avec succes. <del>.");
        } catch (NoSuchElementException e){
            logger.warn("Aucun apprenant avec " + id + " trouvée. <del>.");
            throw new BusinessResourceException("NotFound", "Apprenant non trouve.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + id + " non autorise. <del>.");
            throw new BusinessResourceException("NotValidValueParam", "Apprenant non trouvé.", HttpStatus.BAD_REQUEST);
        }  catch(EmptyResultDataAccessException ex){
            logger.error(String.format("Aucun apprenant trouve pour cet identifiant: "+id));
            throw new BusinessResourceException("EmptyValue", "Aucun apprenant trouvé pour cet identifiant: "+id, HttpStatus.NO_CONTENT);
        } catch(Exception ex){
            logger.error("Erreur de suppression de l'apprenat avec l'identifiant: "+id);
            throw new BusinessResourceException("DeleteError", "Erreur de suppression de l'apprenat avec l'identifiant: "+id, HttpStatus.BAD_REQUEST);
        }
    }
}
