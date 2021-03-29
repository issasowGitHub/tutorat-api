package com.tutorat.resource;

import com.tutorat.dto.request.AnneeRequest;
import com.tutorat.dto.request.ApprenantRequest;
import com.tutorat.model.Apprenant;
import com.tutorat.service.AnneeService;
import com.tutorat.service.ApprenantService;
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
@RequestMapping("/apprenants/api")
public class ApprenantResource {

    private final ApprenantService apprenantService;

    @GetMapping(value = "/v1/all")
    public ResponseEntity<Collection<Apprenant>> allV1() {
        Collection<Apprenant> liste = apprenantService.getAll();
        return new ResponseEntity< Collection<Apprenant> >(liste, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/one/{id}")
    public ResponseEntity<Optional<Apprenant>> oneV1(@PathVariable(value="id") String id) {
        Optional<Apprenant> oneObjet = apprenantService.oneById(id);
        return new ResponseEntity< Optional<Apprenant> >(oneObjet, HttpStatus.OK);
    }

    @PostMapping(value = "/v1/add")
    @Transactional(readOnly = false)
    public ResponseEntity<Apprenant> addV1(@RequestBody @Valid ApprenantRequest addReq) {
        Apprenant objSaved = apprenantService.add(addReq);
        return new ResponseEntity<Apprenant>(objSaved, HttpStatus.CREATED);
    }

    @PutMapping(value = "/v1/maj/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Apprenant> majV1(@PathVariable(value="id") String id,
                                           @RequestBody @Valid ApprenantRequest majReq) {
        Apprenant objUpdated = apprenantService.maj(majReq, id);
        return new ResponseEntity<Apprenant>(objUpdated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/v1/del/{id}")
    public ResponseEntity<Void> delV1(@PathVariable(value="id") String id) {
        apprenantService.del(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
