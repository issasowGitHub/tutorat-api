package com.tutorat.resource;

import com.tutorat.dto.request.JourRequest;
import com.tutorat.model.Jour;
import com.tutorat.service.AnneeService;
import com.tutorat.service.JourService;
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
@RequestMapping("/jours/api")
public class JourResource {

    private final JourService jourService;

    @GetMapping(value = "/v1/all")
    public ResponseEntity<Collection<Jour>> allV1() {
        Collection<Jour> liste = jourService.getAll();
        return new ResponseEntity< Collection<Jour> >(liste, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/one/{id}")
    public ResponseEntity<Optional<Jour>> oneV1(@PathVariable(value="id") String id) {
        Optional<Jour> oneObjet = jourService.oneById(id);
        return new ResponseEntity< Optional<Jour> >(oneObjet, HttpStatus.OK);
    }

    @PostMapping(value = "/v1/add")
    @Transactional(readOnly = false)
    public ResponseEntity<Jour> addV1(@RequestBody @Valid JourRequest addReq) {
        Jour objSaved = jourService.add(addReq);
        return new ResponseEntity<Jour>(objSaved, HttpStatus.CREATED);
    }

    @PutMapping(value = "/v1/maj/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Jour> majV1(@PathVariable(value="id") String id,
                                             @RequestBody @Valid JourRequest majReq) {
        Jour objUpdated = jourService.maj(majReq, id);
        return new ResponseEntity<Jour>(objUpdated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/v1/del/{id}")
    public ResponseEntity<Void> delV1(@PathVariable(value="id") String id) {
        jourService.del(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
