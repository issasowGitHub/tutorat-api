package com.tutorat.service;

import com.tutorat.dao.AnneeDao;
import com.tutorat.dao.UtilisateurDao;
import com.tutorat.dto.request.AnneeRequest;
import com.tutorat.exception.BusinessResourceException;
import com.tutorat.mapper.AnneeMapper;
import com.tutorat.model.Annee;
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
public class AnneeService {

    private static final Logger logger = LoggerFactory.getLogger(AnneeService.class);
    private final AnneeMapper mapper;
    private final AnneeDao anneeDao;
    private final UtilisateurDao userDao;

    public Collection<Annee> getAll() throws BusinessResourceException {
        logger.info("Liste de toutes les annees. <getAll>");
        return anneeDao.findAll();
    }

    public Optional<Annee> oneById(String id) throws NumberFormatException, NoSuchElementException, BusinessResourceException {
        try {
            Long myId = Long.valueOf(id.trim()); //getIdFromString(id);
            Optional<Annee> entityFound = anneeDao.findById(myId);

            if(entityFound.get().getId() == null){
                logger.warn("Aucune année avec  " + id + " trouvé. <oneById>");
                throw new BusinessResourceException("NotFound", "Annee non trouvé.", HttpStatus.NOT_FOUND);
            }

            logger.info("Annee avec id: "+id+" trouvee. <oneById>");
            return entityFound;
        } catch (NoSuchElementException e){
            logger.warn("Aucune annee avec " + id + " trouvee. <oneById>.");
            throw new BusinessResourceException("NotFound", "Annee non trouvee.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + id + " non autorise. <oneById>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre "+id+" non autorise.", HttpStatus.BAD_REQUEST);
        } catch (BusinessResourceException e) {
            logger.error("Annee avec "+id+" non trouvee. <oneById>");
            throw new BusinessResourceException("NotFound", "Aucune annee ne correspond à cet identifiant: "+id);
        }
    }

    @Transactional(readOnly = false)
    public Annee add(AnneeRequest req) throws BusinessResourceException{
        try{
            Annee result = mapper.objReqToEntity(req, new Annee());
            result.setUtiCree(userDao.findById(1L).get());
            result = anneeDao.save(result);
            logger.info("Annee: "+result.toString()+" ajoutee avec succes. <add>.");

            return  result;
        } catch (DataIntegrityViolationException e) {
            logger.error("Erreur technique de creation annee est rencontree: donnée en doublon ou contrainte non respectée");
            throw new BusinessResourceException("SqlError", "Une erreur technique est rencontrée: donnée en doublon ou contrainte non respectée ", HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error("Ajout annee: Une erreur inatteandue est rencontrée.");
            throw new BusinessResourceException("SaveError", "Erreur technique de création d'une année: "+req.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = false)
    public Annee maj(AnneeRequest req, String id) throws NumberFormatException, NoSuchElementException, BusinessResourceException {
        try{
            Long myId = Long.valueOf(id.trim()); //getIdFromString(id);
            Optional<Annee> objFound = anneeDao.findById(myId);

            if(objFound.get().getId() == null){
                logger.warn("Aucune année avec  " + id + " trouvé. <oneById>");
                throw new BusinessResourceException("NotFound", "Annee non trouvé.", HttpStatus.NOT_FOUND);
            }
            //  mise à jour d'un utilisaeur
            Annee entityFound = objFound.get();
            entityFound.setUtiModifie(userDao.findById(1L).get());
            Annee result = anneeDao.saveAndFlush(mapper.objReqToEntity(req, entityFound));
            logger.info("Annee: "+result.toString()+" mis a jour avec succes. <maj>.");
            return  result;
        } catch (NoSuchElementException e){
            logger.warn("Aucun utilisateur avec " + id + " trouve. <maj>.");
            throw new BusinessResourceException("NotFound", "Annee non trouvee.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id: " + id + " non autorise. <add>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre non autorisee.", HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException  e) {
            logger.error("Maj annee: Une erreur technique est rencontree - donnée en doublon ou contrainte non respectée ");
            throw new BusinessResourceException("SqlError", "Une erreur technique est rencontree: donnee en doublon ou contrainte non respectée ", HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error("Maj annee: Une erreur inatteandue est rencontree.");
            throw new BusinessResourceException("UpdateError", "Erreur technique de mise à jour d'une annee: "+req.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = false)
    public void del(String id) throws NumberFormatException, BusinessResourceException {
        try{
            Long myId = Long.valueOf(id.trim());//getIdFromString(id);;
            anneeDao.deleteById(myId);
            logger.info("Annee avec identifiant "+id+" supprimee avec succes. <del>.");
        } catch (NoSuchElementException e){
            logger.warn("Aucune annee avec " + id + " trouvée. <del>.");
            throw new BusinessResourceException("NotFound", "Annee non trouvee.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + id + " non autorise. <del>.");
            throw new BusinessResourceException("NotValidValueParam", "Annee non trouvee.", HttpStatus.BAD_REQUEST);
        }  catch(EmptyResultDataAccessException ex){
            logger.error(String.format("Aucune annee trouvée pour cet identifiant: "+id));
            throw new BusinessResourceException("EmptyValue", "Aucune annee trouvee pour cet identifiant: "+id, HttpStatus.NO_CONTENT);
        } catch(Exception ex){
            logger.error("Erreur de suppression de l'année avec l'identifiant: "+id);
            throw new BusinessResourceException("DeleteError", "Erreur de suppression de l'année avec l'identifiant: "+id, HttpStatus.BAD_REQUEST);
        }
    }
}
