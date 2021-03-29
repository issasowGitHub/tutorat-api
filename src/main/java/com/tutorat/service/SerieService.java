package com.tutorat.service;

import com.tutorat.dao.SerieDao;
import com.tutorat.dao.UtilisateurDao;
import com.tutorat.dto.request.SerieRequest;
import com.tutorat.exception.BusinessResourceException;
import com.tutorat.mapper.SerieMapper;
import com.tutorat.model.Serie;
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
public class SerieService {

    private static final Logger logger = LoggerFactory.getLogger(SerieService.class);
    private final SerieMapper mapper;
    private final SerieDao niveauDao;
    private final UtilisateurDao userDao;

    public Collection<Serie> getAll() throws BusinessResourceException {
        logger.info("Liste de toutes les series. <getAll>");
        return niveauDao.findAll();
    }

    public Optional<Serie> oneById(String id) throws NumberFormatException, NoSuchElementException, BusinessResourceException {
        try {
            Long myId = Long.valueOf(id.trim()); //getIdFromString(id);
            Optional<Serie> entityFound = niveauDao.findById(myId);

            if(entityFound.get().getId() == null){
                logger.warn("Aucune serie avec  " + id + " trouvé. <oneById>");
                throw new BusinessResourceException("NotFound", "Serie non trouvé.", HttpStatus.NOT_FOUND);
            }

            logger.info("Serie avec id: "+id+" trouvee. <oneById>");
            return entityFound;
        } catch (NoSuchElementException e){
            logger.warn("Aucune serie avec " + id + " trouve. <oneById>.");
            throw new BusinessResourceException("NotFound", "Serie non trouve.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + id + " non autorise. <oneById>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre "+id+" non autorise.", HttpStatus.BAD_REQUEST);
        } catch (BusinessResourceException e) {
            logger.error("Details serie: Une erreur inatteandue est rencontrée.");
            throw new BusinessResourceException("NotFound", "Aucune serie ne correspond à cet identifiant: "+id);
        }
    }

    @Transactional(readOnly = false)
    public Serie add(SerieRequest req) throws BusinessResourceException{
        try{
            Serie result = mapper.objReqToEntity(req, new Serie());
            result.setUtiCree(userDao.findById(1L).get());
            result = niveauDao.save(result);
            logger.info("Serie: "+result.toString()+" ajoutee avec succes. <add>.");
            return  result;
        } catch (DataIntegrityViolationException e) {
            logger.error("Erreur technique de creation d'une serie est rencontree: donnée en doublon ou contrainte non respectée");
            throw new BusinessResourceException("SqlError", "Une erreur technique est rencontrée: donnée en doublon ou contrainte non respectée ", HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error("Ajout serie: Une erreur inatteandue est rencontrée.");
            throw new BusinessResourceException("SaveError", "Erreur technique de création d'une serie: "+req.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = false)
    public Serie maj(SerieRequest req, String id) throws NumberFormatException, NoSuchElementException, BusinessResourceException {
        try{
            Long myId = Long.valueOf(id.trim());
            Optional<Serie> objFound = niveauDao.findById(myId);

            if(objFound.get().getId() == null){
                logger.warn("Aucune serie avec  " + id + " trouvé. <oneById>");
                throw new BusinessResourceException("NotFound", "Serie non trouvé.", HttpStatus.NOT_FOUND);
            }
            //  mise à jour d'un service
            Serie entityFound = objFound.get();
            entityFound.setUtiModifie(userDao.findById(1L).get());
            Serie result = niveauDao.saveAndFlush(mapper.objReqToEntity(req, entityFound));
            logger.info("Serie: "+result.toString()+" mis a jour avec succes. <maj>.");
            result.setUtiModifie(userDao.findById(1L).get());
            return  result;
        } catch (NoSuchElementException e){
            logger.warn("Aucune serie avec " + id + " trouvé. <maj>.");
            throw new BusinessResourceException("NotFound", "Serie non trouvé.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id: " + id + " non autorise. <add>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre non autorise.", HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException  e) {
            logger.error("Maj serie: Une erreur technique est rencontree - donnee en doublon ou contrainte non respectée ");
            throw new BusinessResourceException("SqlError", "Une erreur technique est rencontrée: donnée en doublon ou contrainte non respectée ", HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error("Maj serie: Une erreur inatteandue est rencontree.");
            throw new BusinessResourceException("UpdateError", "Erreur technique de mise à jour d'une serie: "+req.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = false)
    public void del(String id) throws NumberFormatException, BusinessResourceException {
        try{
            Long myId = Long.valueOf(id.trim());//getIdFromString(id);;
            niveauDao.deleteById(myId);
            logger.info("Serie avec identifiant "+id+" supprimee avec succes. <del>.");
        } catch (NoSuchElementException e){
            logger.warn("Aucune serie avec " + id + " trouvée. <del>.");
            throw new BusinessResourceException("NotFound", "Serie non trouve.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + id + " non autorise. <del>.");
            throw new BusinessResourceException("NotValidValueParam", "Serie non trouvé.", HttpStatus.BAD_REQUEST);
        }  catch(EmptyResultDataAccessException ex){
            logger.error(String.format("Aucune serie trouve pour cet identifiant: "+id));
            throw new BusinessResourceException("EmptyValue", "Aucune serie trouvé pour cet identifiant: "+id, HttpStatus.NO_CONTENT);
        } catch(Exception ex){
            logger.error("Erreur de suppression d'une serie avec l'identifiant: "+id);
            throw new BusinessResourceException("DeleteError", "Erreur de suppression d'une serie avec l'identifiant: "+id, HttpStatus.BAD_REQUEST);
        }
    }
}
