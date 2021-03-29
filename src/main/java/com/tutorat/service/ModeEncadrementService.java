package com.tutorat.service;

import com.tutorat.dao.ModeEncadrementDao;
import com.tutorat.dao.UtilisateurDao;
import com.tutorat.dto.request.ModeEncadrementRequest;
import com.tutorat.exception.BusinessResourceException;
import com.tutorat.mapper.ModeEncadrementMapper;
import com.tutorat.model.ModeEncadrement;
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
public class ModeEncadrementService {

    private static final Logger logger = LoggerFactory.getLogger(ModeEncadrementService.class);
    private final ModeEncadrementMapper mapper;
    private final ModeEncadrementDao modeEncadrementDao;
    private final UtilisateurDao userDao;

    public Collection<ModeEncadrement> getAll() throws BusinessResourceException {
        logger.info("Liste de tous les mode encdrements. <getAll>");
        return modeEncadrementDao.findAll();
    }

    public Optional<ModeEncadrement> oneById(String id) throws NumberFormatException, NoSuchElementException, BusinessResourceException {
        try {
            Long myId = Long.valueOf(id.trim()); //getIdFromString(id);
            Optional<ModeEncadrement> entityFound = modeEncadrementDao.findById(myId);

            if(entityFound.get().getId() == null){
                logger.warn("Aucun mode encdrement avec  " + id + " trouve. <oneById>");
                throw new BusinessResourceException("NotFound", "ModeEncadrement non trouvé.", HttpStatus.NOT_FOUND);
            }

            logger.info("ModeEncadrement avec id: "+id+" trouve. <oneById>");
            return entityFound;
        } catch (NoSuchElementException e){
            logger.warn("Aucun mode encadrement avec " + id + " trouve. <oneById>.");
            throw new BusinessResourceException("NotFound", "Mode encadrement non trouve.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + id + " non autorise. <oneById>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre "+id+" non autorise.", HttpStatus.BAD_REQUEST);
        } catch (BusinessResourceException e) {
            logger.error("Details mode encadrement: Une erreur inatteandue est rencontrée.");
            throw new BusinessResourceException("NotFound", "Aucun mode encadrement ne correspond à cet identifiant: "+id);
        }
    }

    @Transactional(readOnly = false)
    public ModeEncadrement add(ModeEncadrementRequest req) throws BusinessResourceException{
        try{
            ModeEncadrement result = mapper.objReqToEntity(req, new ModeEncadrement());
            result.setUtiCree(userDao.findById(1L).get());
            result = modeEncadrementDao.save(result);
            logger.info("ModeEncadrement: "+result.toString()+" ajoute avec succes. <add>.");
            
            return  result;
        } catch (DataIntegrityViolationException e) {
            logger.error("Erreur technique de creation d'un mode encadrement est rencontree: donnée en doublon ou contrainte non respectée");
            throw new BusinessResourceException("SqlError", "Une erreur technique est rencontrée: donnée en doublon ou contrainte non respectée ", HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error("Ajout mode encadrement: Une erreur inatteandue est rencontrée.");
            throw new BusinessResourceException("SaveError", "Erreur technique de création d'un mode encadrement: "+req.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = false)
    public ModeEncadrement maj(ModeEncadrementRequest req, String id) throws NumberFormatException, NoSuchElementException, BusinessResourceException {
        try{
            Long myId = Long.valueOf(id.trim()); //getIdFromString(id);
            Optional<ModeEncadrement> objFound = modeEncadrementDao.findById(myId);

            if(objFound.get().getId() == null){
                logger.warn("Aucun mode encdrement avec  " + id + " trouve. <oneById>");
                throw new BusinessResourceException("NotFound", "ModeEncadrement non trouvé.", HttpStatus.NOT_FOUND);
            }
            //  mise à jour d'un utilisaeur
            ModeEncadrement entityFound = objFound.get();
            entityFound.setUtiModifie(userDao.findById(1L).get());
            ModeEncadrement result = modeEncadrementDao.saveAndFlush(mapper.objReqToEntity(req, entityFound));
            logger.info("ModeEncadrement: "+result.toString()+" mis a jour avec succes. <maj>.");
            result.setUtiModifie(userDao.findById(1L).get());
            return  result;
        } catch (NoSuchElementException e){
            logger.warn("Aucun mode encadrement avec " + id + " trouvé. <maj>.");
            throw new BusinessResourceException("NotFound", "Mode encadrement non trouvé.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id: " + id + " non autorise. <add>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre non autorise.", HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException  e) {
            logger.error("Maj mode encadrement: Une erreur technique est rencontree - donnee en doublon ou contrainte non respectée ");
            throw new BusinessResourceException("SqlError", "Une erreur technique est rencontrée: donnée en doublon ou contrainte non respectée ", HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error("Maj mode encadrement: Une erreur inatteandue est rencontree.");
            throw new BusinessResourceException("UpdateError", "Erreur technique de mise à jour d'un mode encadrement: "+req.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = false)
    public void del(String id) throws NumberFormatException, BusinessResourceException {
        try{
            Long myId = Long.valueOf(id.trim());//getIdFromString(id);;
            modeEncadrementDao.deleteById(myId);
            logger.info("ModeEncadrement avec identifiant "+id+" supprime avec succes. <del>.");
        } catch (NoSuchElementException e){
            logger.warn("Aucun mode encadrement avec " + id + " trouvée. <del>.");
            throw new BusinessResourceException("NotFound", "Mode encadrement non trouve.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + id + " non autorise. <del>.");
            throw new BusinessResourceException("NotValidValueParam", "Mode encadrement non trouvé.", HttpStatus.BAD_REQUEST);
        }  catch(EmptyResultDataAccessException ex){
            logger.error(String.format("Aucun mode encadrement trouve pour cet identifiant: "+id));
            throw new BusinessResourceException("EmptyValue", "Aucun mode encadrement trouvé pour cet identifiant: "+id, HttpStatus.NO_CONTENT);
        } catch(Exception ex){
            logger.error("Erreur de suppression d'un mode encadrement avec l'identifiant: "+id);
            throw new BusinessResourceException("DeleteError", "Erreur de suppression d'un mode encadrement avec l'identifiant: "+id, HttpStatus.BAD_REQUEST);
        }
    }
}
