package com.tutorat.service;

import com.tutorat.dao.IfeDao;
import com.tutorat.dao.UtilisateurDao;
import com.tutorat.dto.request.IfeRequest;
import com.tutorat.exception.BusinessResourceException;
import com.tutorat.mapper.IfeMapper;
import com.tutorat.model.Ife;
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
public class IfeService {

    private static final Logger logger = LoggerFactory.getLogger(IfeService.class);
    private final IfeMapper mapper;
    private final IfeDao ifeDao;
    private final UtilisateurDao userDao;

    public Collection<Ife> getAll() throws BusinessResourceException {
        logger.info("Liste de tous les ifes. <getAll>");
        return ifeDao.findAll();
    }

    public Optional<Ife> oneById(String id) throws NumberFormatException, NoSuchElementException, BusinessResourceException {
        try {
            Long myId = Long.valueOf(id.trim()); //getIdFromString(id);
            Optional<Ife> entityFound = ifeDao.findById(myId);

            if(entityFound.get().getId() == null){
                logger.warn("Aucun ife avec  " + id + " trouve. <oneById>");
                throw new BusinessResourceException("NotFound", "Ife non trouvé.", HttpStatus.NOT_FOUND);
            }

            logger.info("Ife avec id: "+id+" trouve. <oneById>");
            return entityFound;
        } catch (NoSuchElementException e){
            logger.warn("Aucun ife avec " + id + " trouve. <oneById>.");
            throw new BusinessResourceException("NotFound", "Ife non trouve.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + id + " non autorise. <oneById>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre "+id+" non autorise.", HttpStatus.BAD_REQUEST);
        } catch (BusinessResourceException e) {
            logger.error("Details ife: Une erreur inatteandue est rencontrée.");
            throw new BusinessResourceException("NotFound", "Aucun ife ne correspond à cet identifiant: "+id);
        }
    }

    @Transactional(readOnly = false)
    public Ife add(IfeRequest req) throws BusinessResourceException{
        try{
            Ife result = mapper.objReqToEntity(req, new Ife());
            result.setUtiCree(userDao.findById(1L).get());
            result = ifeDao.save(result);
            logger.info("Ife: "+result.toString()+" ajoute avec succes. <add>.");
            result.setUtiCree(userDao.findById(1L).get());
            return  result;
        } catch (DataIntegrityViolationException e) {
            logger.error("Erreur technique de creation d'un ife est rencontree: donnée en doublon ou contrainte non respectée");
            throw new BusinessResourceException("SqlError", "Une erreur technique est rencontrée: donnée en doublon ou contrainte non respectée ", HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error("Ajout ife: Une erreur inatteandue est rencontrée.");
            throw new BusinessResourceException("SaveError", "Erreur technique de création d'un ife: "+req.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = false)
    public Ife maj(IfeRequest req, String id) throws NumberFormatException, NoSuchElementException, BusinessResourceException {
        try{
            Long myId = Long.valueOf(id.trim()); //getIdFromString(id);
            Optional<Ife> objFound = ifeDao.findById(myId);

            if(objFound.get().getId() == null){
                logger.warn("Aucun ife avec  " + id + " trouve. <oneById>");
                throw new BusinessResourceException("NotFound", "Ife non trouvé.", HttpStatus.NOT_FOUND);
            }
            //  mise à jour d'un ife
            Ife entityFound = objFound.get();
            entityFound.setUtiModifie(userDao.findById(1L).get());
            Ife result = ifeDao.saveAndFlush(mapper.objReqToEntity(req, entityFound));
            logger.info("Ife: "+result.toString()+" mis a jour avec succes. <maj>.");
            return  result;
        } catch (NoSuchElementException e){
            logger.warn("Aucun ife avec " + id + " trouvé. <maj>.");
            throw new BusinessResourceException("NotFound", "Ife non trouvé.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id: " + id + " non autorise. <add>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre non autorise.", HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException  e) {
            logger.error("Maj ife: Une erreur technique est rencontree - donnee en doublon ou contrainte non respectée ");
            throw new BusinessResourceException("SqlError", "Une erreur technique est rencontrée: donnée en doublon ou contrainte non respectée ", HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error("Maj ife: Une erreur inatteandue est rencontree.");
            throw new BusinessResourceException("UpdateError", "Erreur technique de mise à jour d'un ife: "+req.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = false)
    public void del(String id) throws NumberFormatException, BusinessResourceException {
        try{
            Long myId = Long.valueOf(id.trim());//getIdFromString(id);;
            ifeDao.deleteById(myId);
            logger.info("Ife avec identifiant "+id+" supprime avec succes. <del>.");
        } catch (NoSuchElementException e){
            logger.warn("Aucun ife avec " + id + " trouvée. <del>.");
            throw new BusinessResourceException("NotFound", "Ife non trouve.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + id + " non autorise. <del>.");
            throw new BusinessResourceException("NotValidValueParam", "Ife non trouvé.", HttpStatus.BAD_REQUEST);
        }  catch(EmptyResultDataAccessException ex){
            logger.error(String.format("Aucun ife trouve pour cet identifiant: "+id));
            throw new BusinessResourceException("EmptyValue", "Aucun ife trouvé pour cet identifiant: "+id, HttpStatus.NO_CONTENT);
        } catch(Exception ex){
            logger.error("Erreur de suppression d'un ife avec l'identifiant: "+id);
            throw new BusinessResourceException("DeleteError", "Erreur de suppression d'un ife avec l'identifiant: "+id, HttpStatus.BAD_REQUEST);
        }
    }
}
