package com.tutorat.resource;

import com.tutorat.dto.request.MatiereRequest;
import com.tutorat.model.Matiere;
import com.tutorat.service.MatiereService;
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
@RequestMapping("/matieres/api")
public class MatiereResource {

    private final MatiereService matiereService;

    @GetMapping(value = "/v1/all")
    public ResponseEntity<Collection<Matiere>> allV1() {
        Collection<Matiere> liste = matiereService.getAll();
        return new ResponseEntity< Collection<Matiere> >(liste, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/one/{id}")
    public ResponseEntity<Optional<Matiere>> oneV1(@PathVariable(value="id") String id) {
        Optional<Matiere> oneObjet = matiereService.oneById(id);
        return new ResponseEntity< Optional<Matiere> >(oneObjet, HttpStatus.OK);
    }

    @PostMapping(value = "/v1/add")
    @Transactional(readOnly = false)
    public ResponseEntity<Matiere> addV1(@RequestBody @Valid MatiereRequest addReq) {
        Matiere objSaved = matiereService.add(addReq);
        return new ResponseEntity<Matiere>(objSaved, HttpStatus.CREATED);
    }

    @PutMapping(value = "/v1/maj/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Matiere> majV1(@PathVariable(value="id") String id,
                                             @RequestBody @Valid MatiereRequest majReq) {
        Matiere objUpdated = matiereService.maj(majReq, id);
        return new ResponseEntity<Matiere>(objUpdated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/v1/del/{id}")
    public ResponseEntity<Void> delV1(@PathVariable(value="id") String id) {
        matiereService.del(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
