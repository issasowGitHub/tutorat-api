package com.tutorat.service;

import com.tutorat.dao.MatiereDao;
import com.tutorat.dao.UtilisateurDao;
import com.tutorat.dto.request.MatiereRequest;
import com.tutorat.exception.BusinessResourceException;
import com.tutorat.mapper.MatiereMapper;
import com.tutorat.model.Matiere;
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
public class MatiereService {

    private static final Logger logger = LoggerFactory.getLogger(MatiereService.class);
    private final MatiereMapper mapper;
    private final MatiereDao matiereDao;
    private final UtilisateurDao userDao;

    public Collection<Matiere> getAll() throws BusinessResourceException {
        logger.info("Liste de toutes les matieres. <getAll>");
        return matiereDao.findAll();
    }

    public Optional<Matiere> oneById(String id) throws NumberFormatException, NoSuchElementException, BusinessResourceException {
        try {
            Long myId = Long.valueOf(id.trim()); //getIdFromString(id);
            Optional<Matiere> entityFound = matiereDao.findById(myId);

            if(entityFound.get().getId() == null){
                logger.warn("Aucune matiere avec  " + id + " trouvee. <oneById>");
                throw new BusinessResourceException("NotFound", "Matiere non trouvee.", HttpStatus.NOT_FOUND);
            }

            logger.info("Matiere avec id: "+id+" trouvee. <oneById>");
            return entityFound;
        } catch (NoSuchElementException e){
            logger.warn("Aucune matiere avec " + id + " trouve. <oneById>.");
            throw new BusinessResourceException("NotFound", "Matiere non trouve.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + id + " non autorise. <oneById>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre "+id+" non autorise.", HttpStatus.BAD_REQUEST);
        } catch (BusinessResourceException e) {
            logger.error("Details matiere: Une erreur inatteandue est rencontrée.");
            throw new BusinessResourceException("NotFound", "Aucune matiere ne correspond à cet identifiant: "+id);
        }
    }

    @Transactional(readOnly = false)
    public Matiere add(MatiereRequest req) throws BusinessResourceException{
        try{
            Matiere result = mapper.objReqToEntity(req, new Matiere());
            result.setUtiCree(userDao.findById(1L).get());
            result = matiereDao.save(result);
            logger.info("Matiere: "+result.toString()+" ajoutee avec succes. <add>.");
            return  result;
        } catch (DataIntegrityViolationException e) {
            logger.error("Erreur technique de creation d'une matiere est rencontree: donnée en doublon ou contrainte non respectée");
            throw new BusinessResourceException("SqlError", "Une erreur technique est rencontrée: donnée en doublon ou contrainte non respectée ", HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error("Ajout matiere: Une erreur inatteandue est rencontrée.");
            throw new BusinessResourceException("SaveError", "Erreur technique de création d'une matiere: "+req.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = false)
    public Matiere maj(MatiereRequest req, String id) throws NumberFormatException, NoSuchElementException, BusinessResourceException {
        try{
            Long myId = Long.valueOf(id.trim()); //getIdFromString(id);
            Optional<Matiere> objFound = matiereDao.findById(myId);

            if(objFound.get().getId() == null){
                logger.warn("Aucune matiere avec  " + id + " trouvee. <oneById>");
                throw new BusinessResourceException("NotFound", "Matiere non trouvee.", HttpStatus.NOT_FOUND);
            }
            //  mise à matiere d'une matiere
            Matiere entityFound = objFound.get();
            entityFound.setUtiModifie(userDao.findById(1L).get());
            Matiere result = matiereDao.saveAndFlush(mapper.objReqToEntity(req, entityFound));
            logger.info("Matiere: "+result.toString()+" mise a matiere avec succes. <maj>.");
            result.setUtiModifie(userDao.findById(1L).get());
            return  result;
        } catch (NoSuchElementException e){
            logger.warn("Aucune matiere avec " + id + " trouvé. <maj>.");
            throw new BusinessResourceException("NotFound", "Matiere non trouvé.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id: " + id + " non autorise. <add>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre non autorise.", HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException  e) {
            logger.error("Maj matiere: Une erreur technique est rencontree - donnee en doublon ou contrainte non respectée ");
            throw new BusinessResourceException("SqlError", "Une erreur technique est rencontrée: donnée en doublon ou contrainte non respectée ", HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error("Maj matiere: Une erreur inatteandue est rencontree.");
            throw new BusinessResourceException("UpdateError", "Erreur technique de mise à jour d'une matiere: "+req.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = false)
    public void del(String id) throws NumberFormatException, BusinessResourceException {
        try{
            Long myId = Long.valueOf(id.trim());//getIdFromString(id);;
            matiereDao.deleteById(myId);
            logger.info("Matiere avec identifiant "+id+" supprimee avec succes. <del>.");
        } catch (NoSuchElementException e){
            logger.warn("Aucune matiere avec " + id + " trouvée. <del>.");
            throw new BusinessResourceException("NotFound", "Matiere non trouve.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + id + " non autorise. <del>.");
            throw new BusinessResourceException("NotValidValueParam", "Matiere non trouvé.", HttpStatus.BAD_REQUEST);
        }  catch(EmptyResultDataAccessException ex){
            logger.error(String.format("Aucune matiere trouve pour cet identifiant: "+id));
            throw new BusinessResourceException("EmptyValue", "Aucune matiere trouvé pour cet identifiant: "+id, HttpStatus.NO_CONTENT);
        } catch(Exception ex){
            logger.error("Erreur de suppression d'une matiere avec l'identifiant: "+id);
            throw new BusinessResourceException("DeleteError", "Erreur de suppression d'une matiere avec l'identifiant: "+id, HttpStatus.BAD_REQUEST);
        }
    }
}
