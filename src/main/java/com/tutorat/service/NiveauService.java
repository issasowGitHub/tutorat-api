package com.tutorat.service;

import com.tutorat.dao.NiveauDao;
import com.tutorat.dao.UtilisateurDao;
import com.tutorat.dto.request.NiveauRequest;
import com.tutorat.exception.BusinessResourceException;
import com.tutorat.mapper.AnneeMapper;
import com.tutorat.mapper.NiveauMapper;
import com.tutorat.model.Niveau;
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
public class NiveauService {

    private static final Logger logger = LoggerFactory.getLogger(NiveauService.class);
    private final NiveauMapper mapper;
    private final NiveauDao niveauDao;
    private final UtilisateurDao userDao;

    public Collection<Niveau> getAll() throws BusinessResourceException {
        logger.info("Liste de tous les niveaux. <getAll>");
        return niveauDao.findAll();
    }

    public Optional<Niveau> oneById(String id) throws NumberFormatException, NoSuchElementException, BusinessResourceException {
        try {
            Long myId = Long.valueOf(id.trim()); //getIdFromString(id);
            Optional<Niveau> entityFound = niveauDao.findById(myId);

            if(entityFound.get().getId() == null){
                logger.warn("Aucun niveau avec  " + id + " trouvé. <oneById>");
                throw new BusinessResourceException("NotFound", "Niveau non trouvé.", HttpStatus.NOT_FOUND);
            }
            logger.info("Niveau avec id: "+id+" trouvee. <oneById>");
            return entityFound;
        } catch (NoSuchElementException e){
            logger.warn("Aucun niveau avec " + id + " trouve. <oneById>.");
            throw new BusinessResourceException("NotFound", "Niveau non trouve.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + id + " non autorise. <oneById>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre "+id+" non autorise.", HttpStatus.BAD_REQUEST);
        } catch (BusinessResourceException e) {
            logger.error("Details niveau: Une erreur inatteandue est rencontrée.");
            throw new BusinessResourceException("NotFound", "Aucun niveau ne correspond à cet identifiant: "+id);
        }
    }

    @Transactional(readOnly = false)
    public Niveau add(NiveauRequest req) throws BusinessResourceException{
        try{
            Niveau result =mapper.objReqToEntity(req, new Niveau());
            result.setUtiCree(userDao.findById(1L).get());
            result = niveauDao.save(result);
            logger.info("Niveau: "+result.toString()+" ajoute avec succes. <add>.");

            return  result;
        } catch (DataIntegrityViolationException e) {
            logger.error("Erreur technique de creation du niveau est rencontree: donnée en doublon ou contrainte non respectée");
            throw new BusinessResourceException("SqlError", "Une erreur technique est rencontrée: donnée en doublon ou contrainte non respectée ", HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error("Ajout niveau: Une erreur inatteandue est rencontrée.",ex);
            throw new BusinessResourceException("SaveError", "Erreur technique de création du niveau: "+req.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = false)
    public Niveau maj(NiveauRequest req, String id) throws NumberFormatException, NoSuchElementException, BusinessResourceException {
        try{
            Long myId = Long.valueOf(id.trim()); //getIdFromString(id);
            Optional<Niveau> objFound = niveauDao.findById(myId);

            if(objFound.get().getId() == null){
                logger.warn("Aucun niveau avec  " + id + " trouvé. <oneById>");
                throw new BusinessResourceException("NotFound", "Niveau non trouvé.", HttpStatus.NOT_FOUND);
            }
            //  mise à jour du utilisaeur
            Niveau entityFound = objFound.get();
            entityFound.setUtiModifie(userDao.findById(1L).get());
            Niveau result = niveauDao.saveAndFlush(mapper.objReqToEntity(req, entityFound));
            logger.info("Niveau: "+result.toString()+" mis a jour avec succes. <maj>.");
            return  result;
        } catch (NoSuchElementException e){
            logger.warn("Aucun niveau avec " + id + " trouvé. <maj>.");
            throw new BusinessResourceException("NotFound", "Niveau non trouvé.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id: " + id + " non autorise. <add>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre non autorise.", HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException  e) {
            logger.error("Maj niveau: Une erreur technique est rencontree - donnee en doublon ou contrainte non respectée ");
            throw new BusinessResourceException("SqlError", "Une erreur technique est rencontrée: donnée en doublon ou contrainte non respectée ", HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error("Maj niveau: Une erreur inatteandue est rencontree.");
            throw new BusinessResourceException("UpdateError", "Erreur technique de mise à jour du niveau: "+req.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = false)
    public void del(String id) throws NumberFormatException, BusinessResourceException {
        try{
            Long myId = Long.valueOf(id.trim());//getIdFromString(id);;
            niveauDao.deleteById(myId);
            logger.info("Niveau avec identifiant "+id+" supprimee avec succes. <del>.");
        } catch (NoSuchElementException e){
            logger.warn("Aucun niveau avec " + id + " trouvée. <del>.");
            throw new BusinessResourceException("NotFound", "Niveau non trouve.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + id + " non autorise. <del>.");
            throw new BusinessResourceException("NotValidValueParam", "Niveau non trouvé.", HttpStatus.BAD_REQUEST);
        }  catch(EmptyResultDataAccessException ex){
            logger.error(String.format("Aucun niveau trouve pour cet identifiant: "+id));
            throw new BusinessResourceException("EmptyValue", "Aucun niveau trouvé pour cet identifiant: "+id, HttpStatus.NO_CONTENT);
        } catch(Exception ex){
            logger.error("Erreur de suppression du niveau avec l'identifiant: "+id);
            throw new BusinessResourceException("DeleteError", "Erreur de suppression du niveau avec l'identifiant: "+id, HttpStatus.BAD_REQUEST);
        }
    }
}
