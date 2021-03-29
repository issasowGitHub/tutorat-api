package com.tutorat.resource;

import com.tutorat.dto.request.AnneeRequest;
import com.tutorat.model.Annee;
import com.tutorat.service.AnneeService;
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
@RequestMapping("/annees/api")
public class AnneeResource {

    private final AnneeService anService;

    @GetMapping(value = "/v1/all")
    public ResponseEntity<Collection<Annee>> allV1() {
        Collection<Annee> liste = anService.getAll();
        return new ResponseEntity< Collection<Annee> >(liste, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/one/{id}")
    public ResponseEntity<Optional<Annee>> oneV1(@PathVariable(value="id") String id) {
        Optional<Annee> oneObjet = anService.oneById(id);
        return new ResponseEntity< Optional<Annee> >(oneObjet, HttpStatus.OK);
    }

    @PostMapping(value = "/v1/add")
    @Transactional(readOnly = false)
    public ResponseEntity<Annee> addV1(@RequestBody @Valid AnneeRequest addReq) {
        Annee objSaved = anService.add(addReq);
        return new ResponseEntity<Annee>(objSaved, HttpStatus.CREATED);
    }

    @PutMapping(value = "/v1/maj/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Annee> majV1(@PathVariable(value="id") String id,
                                             @RequestBody @Valid AnneeRequest majReq) {
        Annee objUpdated = anService.maj(majReq, id);
        return new ResponseEntity<Annee>(objUpdated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/v1/del/{id}")
    public ResponseEntity<Void> delV1(@PathVariable(value="id") String id) {
        anService.del(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
