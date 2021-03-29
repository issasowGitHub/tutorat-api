package com.tutorat.resource;

import com.tutorat.dto.request.UtilisateurRequest;
import com.tutorat.model.Utilisateur;
import com.tutorat.service.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@RequestMapping("/utilisateurs/api")
public class UtilisateurResource {

    private final UtilisateurService userService;

    @GetMapping(value = "/v1/all")
    public ResponseEntity<Collection<Utilisateur>> allV1() {
        Collection<Utilisateur> liste = userService.getAll();
        return new ResponseEntity< Collection<Utilisateur> >(liste, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/one/{id}")
    public ResponseEntity<Optional<Utilisateur>> oneV1(@PathVariable(value="id") String id) {
        Optional<Utilisateur> oneObjet = userService.oneById(id);
        return new ResponseEntity< Optional<Utilisateur> >(oneObjet, HttpStatus.OK);
    }

    @PostMapping(value = "/v1/add")
    @Transactional(readOnly = false)
    public ResponseEntity<Utilisateur> addV1(@RequestBody @Valid UtilisateurRequest addReq) {
        Utilisateur objSaved = userService.add(addReq);
        return new ResponseEntity<Utilisateur>(objSaved, HttpStatus.CREATED);
    }

    @PutMapping(value = "/v1/maj/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Utilisateur> majV1(@PathVariable(value="id") String id,
                                             @RequestBody @Valid UtilisateurRequest majReq) {
        Utilisateur objUpdated = userService.maj(majReq, id);
        return new ResponseEntity<Utilisateur>(objUpdated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/v1/del/{id}")
    public ResponseEntity<Void> delV1(@PathVariable(value="id") String id) {
        userService.del(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    /*@GetMapping(value = "/v1/test")
    public ResponseEntity< Utilisateur > add() {
        UtilisateurRequest u = new UtilisateurRequest();
        u.setPrenoms("Lamine"); u.setNom("DIEYE"); u.setDateNaissance("08/05/1992"); u.setLieuNaissance("Guédiawaye");
        u.setSexe("M"); u.setAdresse("Pikine"); u.setTelephone("779783369"); u.setEmail("laminedev@gmail.com");
        u.setUsername("mldieye"); u.setMdpasse("P@sser123"); //u.setProfil("ADMIN");
        u.setMdpasse("1234567"); u.setSituationFamiliale("M");
        Utilisateur utilisateur = userService.add(u);
        //System.out.println(utilisateur.toString());
        return new ResponseEntity< Utilisateur >(utilisateur, HttpStatus.OK);
    }*/
}
