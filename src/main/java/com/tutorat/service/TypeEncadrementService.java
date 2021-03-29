package com.tutorat.service;

import com.tutorat.dao.TypeEncadrementDao;
import com.tutorat.dao.UtilisateurDao;
import com.tutorat.dto.request.TypeEncadrementRequest;
import com.tutorat.exception.BusinessResourceException;
import com.tutorat.mapper.TypeEncadrementMapper;
import com.tutorat.model.TypeEncadrement;
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
public class TypeEncadrementService {

    private static final Logger logger = LoggerFactory.getLogger(TypeEncadrementService.class);
    private final TypeEncadrementMapper mapper;
    private final TypeEncadrementDao typEncadrementDao;
    private final UtilisateurDao userDao;

    public Collection<TypeEncadrement> getAll() throws BusinessResourceException {
        logger.info("Liste de tous les type de formules. <getAll>");
        return typEncadrementDao.findAll();
    }

    public Optional<TypeEncadrement> oneById(String id) throws NumberFormatException, NoSuchElementException, BusinessResourceException {
        try {
            Long myId = Long.valueOf(id.trim()); //getIdFromString(id);
            Optional<TypeEncadrement> entityFound = typEncadrementDao.findById(myId);

            if(entityFound.get().getId() == null){
                logger.warn("Aucun type d'encadrement avec  " + id + " trouve. <oneById>");
                throw new BusinessResourceException("NotFound", "TypeEncadrement non trouvé.", HttpStatus.NOT_FOUND);
            }

            logger.info("TypeEncadrement avec id: "+id+" trouve. <oneById>");
            return entityFound;
        } catch (NoSuchElementException e){
            logger.warn("Aucun type encadrement avec " + id + " trouve. <oneById>.");
            throw new BusinessResourceException("NotFound", "Ife non trouve.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + id + " non autorise. <oneById>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre "+id+" non autorise.", HttpStatus.BAD_REQUEST);
        } catch (BusinessResourceException e) {
            logger.error("Details type encadrement: Une erreur inatteandue est rencontrée.");
            throw new BusinessResourceException("NotFound", "Aucun type encadrement ne correspond à cet identifiant: "+id);
        }
    }

    @Transactional(readOnly = false)
    public TypeEncadrement add(TypeEncadrementRequest req) throws BusinessResourceException{
        try{
            TypeEncadrement result = mapper.objReqToEntity(req, new TypeEncadrement());
            result.setUtiCree(userDao.findById(1L).get());
            result = typEncadrementDao.save(result);
            logger.info("TypeEncadrement: "+result.toString()+" ajoute avec succes. <add>.");
            return  result;
        } catch (DataIntegrityViolationException e) {
            logger.error("Erreur technique de creation d'un type encadrement est rencontree: donnée en doublon ou contrainte non respectée");
            throw new BusinessResourceException("SqlError", "Une erreur technique est rencontrée: donnée en doublon ou contrainte non respectée ", HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error("Ajout type encadrement: Une erreur inatteandue est rencontrée.");
            throw new BusinessResourceException("SaveError", "Erreur technique de création d'un type encadrement: "+req.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = false)
    public TypeEncadrement maj(TypeEncadrementRequest req, String id) throws NumberFormatException, NoSuchElementException, BusinessResourceException {
        try{
            Long myId = Long.valueOf(id.trim()); //getIdFromString(id);
            Optional<TypeEncadrement> objFound = typEncadrementDao.findById(myId);

            if(objFound.get().getId() == null){
                logger.warn("Aucun type de formule avec  " + id + " trouve. <oneById>");
                throw new BusinessResourceException("NotFound", "TypeEncadrement non trouvé.", HttpStatus.NOT_FOUND);
            }
            //  mise à jour d'un type de formule
            TypeEncadrement entityFound = objFound.get();
            entityFound.setUtiModifie(userDao.findById(1L).get());
            TypeEncadrement result = typEncadrementDao.saveAndFlush(mapper.objReqToEntity(req, entityFound));
            logger.info("TypeEncadrement: "+result.toString()+" mis a jour avec succes. <maj>.");
            result.setUtiModifie(userDao.findById(1L).get());
            return  result;
        } catch (NoSuchElementException e){
            logger.warn("Aucun type encadrement avec " + id + " trouvé. <maj>.");
            throw new BusinessResourceException("NotFound", "Ife non trouvé.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id: " + id + " non autorise. <add>.");
            throw new BusinessResourceException("NotValidValueParam", "Parametre non autorise.", HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException  e) {
            logger.error("Maj type encadrement: Une erreur technique est rencontree - donnee en doublon ou contrainte non respectée ");
            throw new BusinessResourceException("SqlError", "Une erreur technique est rencontrée: donnée en doublon ou contrainte non respectée ", HttpStatus.CONFLICT);
        } catch(Exception ex){
            logger.error("Maj type encadrement: Une erreur inatteandue est rencontree.");
            throw new BusinessResourceException("UpdateError", "Erreur technique de mise à jour d'un type encadrement: "+req.toString(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = false)
    public void del(String id) throws NumberFormatException, BusinessResourceException {
        try{
            Long myId = Long.valueOf(id.trim());//getIdFromString(id);;
            typEncadrementDao.deleteById(myId);
            logger.info("TypeEncadrement avec identifiant "+id+" supprime avec succes. <del>.");
        } catch (NoSuchElementException e){
            logger.warn("Aucun type encadrement avec " + id + " trouvée. <del>.");
            throw new BusinessResourceException("NotFound", "Ife non trouve.", HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e){
            logger.warn("Parametre id " + id + " non autorise. <del>.");
            throw new BusinessResourceException("NotValidValueParam", "Ife non trouvé.", HttpStatus.BAD_REQUEST);
        }  catch(EmptyResultDataAccessException ex){
            logger.error(String.format("Aucun type encadrement trouve pour cet identifiant: "+id));
            throw new BusinessResourceException("EmptyValue", "Aucun type encadrement trouvé pour cet identifiant: "+id, HttpStatus.NO_CONTENT);
        } catch(Exception ex){
            logger.error("Erreur de suppression d'un type encadrement avec l'identifiant: "+id);
            throw new BusinessResourceException("DeleteError", "Erreur de suppression d'un type encadrement avec l'identifiant: "+id, HttpStatus.BAD_REQUEST);
        }
    }
}
