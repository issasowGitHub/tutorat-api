package com.tutorat.service;

import com.tutorat.dao.AnneeDao;
import com.tutorat.dao.JourDao;
import com.tutorat.dao.UtilisateurDao;
import com.tutorat.dto.request.JourRequest;
import com.tutorat.exception.BusinessResourceException;
import com.tutorat.mapper.AnneeMapper;
import com.tutorat.mapper.JourMapper;
import com.tutorat.model.Jour;
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
public class JourService {

    private static final Logger logger = LoggerFactory.getLogger(JourService.class);
    private final JourMapper mapper;
    private final JourDao jourDao;
    private final UtilisateurDao userDao;

    public Collection<Jour> getAll() throws BusinessResourceException {
        logger.info("Liste de tous les jours. <getAll>");
        return jourDao.findAll();
    }

    public Optional<Jour> oneById(String id) throws NumberFormatException, NoSuchElementException, BusinessResourceException {
        try {
            Long myId = Long.valueOf(id.trim()); //getIdFromString(id);
            Optional<Jour> entityFound = jourDao.findById(myId);

            if(entityFound.get().getId() == null){
                logger.warn("Aucun jour avec  " + id + " trouvé. <oneById>");
                throw new BusinessResourceException("NotFound", "Jour non trouvé.", HttpStatus.NOT_FOUND);
            }

            logger.info("Jour avec id: "+id+" trouvee. <oneById>");
            return entityFound;
        } catch (NoSuchElementException e){
            logger.warn("Aucun jour avec " + id + " trouve. <oneById>.");
            throw new BusinessResourceException("NotFound", "Jour non trouve.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + id + " non autorise. <oneById>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre "+id+" non autorise.", HttpStatus.BAD_REQUEST);
        } catch (BusinessResourceException e) {
            logger.error("Details jour: Une erreur inatteandue est rencontrée.");
            throw new BusinessResourceException("NotFound", "Aucun jour ne correspond à cet identifiant: "+id);
        }
    }

    @Transactional(readOnly = false)
    public Jour add(JourRequest req) throws BusinessResourceException{
        try{
            Jour result = mapper.objReqToEntity(req, new Jour());
            result.setUtiCree(userDao.findById(1L).get());
            result = jourDao.save(result);
            logger.info("Jour: "+result.toString()+" ajoutee avec succes. <add>.");
            result.setUtiCree(userDao.findById(1L).get());
            return  result;
        } catch (DataIntegrityViolationException e) {
            logger.error("Erreur technique de creation d'un jour est rencontree: donnée en doublon ou contrainte non respectée");
            throw new BusinessResourceException("SqlError", "Une erreur technique est rencontrée: donnée en doublon ou contrainte non respectée ", HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error("Ajout jour: Une erreur inatteandue est rencontrée.");
            throw new BusinessResourceException("SaveError", "Erreur technique de création d'un jour: "+req.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = false)
    public Jour maj(JourRequest req, String id) throws NumberFormatException, NoSuchElementException, BusinessResourceException {
        try{
            Long myId = Long.valueOf(id.trim()); //getIdFromString(id);
            Optional<Jour> objFound = jourDao.findById(myId);

            if(objFound.get().getId() == null){
                logger.warn("Aucun jour avec  " + id + " trouvé. <oneById>");
                throw new BusinessResourceException("NotFound", "Jour non trouvé.", HttpStatus.NOT_FOUND);
            }
            //  mise à jour d'un jour
            Jour entityFound = objFound.get();
            entityFound.setUtiModifie(userDao.findById(1L).get());
            Jour result = jourDao.saveAndFlush(mapper.objReqToEntity(req, entityFound));
            logger.info("Jour: "+result.toString()+" mis a jour avec succes. <maj>.");
            return  result;
        } catch (NoSuchElementException e){
            logger.warn("Aucun jour avec " + id + " trouvé. <maj>.");
            throw new BusinessResourceException("NotFound", "Jour non trouvé.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id: " + id + " non autorise. <add>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre non autorise.", HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException  e) {
            logger.error("Maj jour: Une erreur technique est rencontree - donnee en doublon ou contrainte non respectée ");
            throw new BusinessResourceException("SqlError", "Une erreur technique est rencontrée: donnée en doublon ou contrainte non respectée ", HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error("Maj jour: Une erreur inatteandue est rencontree.");
            throw new BusinessResourceException("UpdateError", "Erreur technique de mise à jour d'un jour: "+req.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = false)
    public void del(String id) throws NumberFormatException, BusinessResourceException {
        try{
            Long myId = Long.valueOf(id.trim());//getIdFromString(id);;
            jourDao.deleteById(myId);
            logger.info("Jour avec identifiant "+id+" supprimee avec succes. <del>.");
        } catch (NoSuchElementException e){
            logger.warn("Aucun jour avec " + id + " trouvée. <del>.");
            throw new BusinessResourceException("NotFound", "Jour non trouve.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + id + " non autorise. <del>.");
            throw new BusinessResourceException("NotValidValueParam", "Jour non trouvé.", HttpStatus.BAD_REQUEST);
        }  catch(EmptyResultDataAccessException ex){
            logger.error(String.format("Aucun jour trouve pour cet identifiant: "+id));
            throw new BusinessResourceException("EmptyValue", "Aucun jour trouvé pour cet identifiant: "+id, HttpStatus.NO_CONTENT);
        } catch(Exception ex){
            logger.error("Erreur de suppression d'un jour avec l'identifiant: "+id);
            throw new BusinessResourceException("DeleteError", "Erreur de suppression d'un jour avec l'identifiant: "+id, HttpStatus.BAD_REQUEST);
        }
    }
}
