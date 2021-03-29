package com.tutorat.resource;

import com.tutorat.dto.request.DiplomeRequest;
import com.tutorat.model.Diplome;
import com.tutorat.service.DiplomeService;
import com.tutorat.service.IfeService;
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
@RequestMapping("/diplomes/api")
public class DiplomeResource {

    private final DiplomeService diplomeService;

    @GetMapping(value = "/v1/all")
    public ResponseEntity<Collection<Diplome>> allV1() {
        Collection<Diplome> liste = diplomeService.getAll();
        return new ResponseEntity< Collection<Diplome> >(liste, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/one/{id}")
    public ResponseEntity<Optional<Diplome>> oneV1(@PathVariable(value="id") String id) {
        Optional<Diplome> oneObjet = diplomeService.oneById(id);
        return new ResponseEntity< Optional<Diplome> >(oneObjet, HttpStatus.OK);
    }

    @PostMapping(value = "/v1/add")
    @Transactional(readOnly = false)
    public ResponseEntity<Diplome> addV1(@RequestBody @Valid DiplomeRequest addReq) {
        Diplome objSaved = diplomeService.add(addReq);
        return new ResponseEntity<Diplome>(objSaved, HttpStatus.CREATED);
    }

    @PutMapping(value = "/v1/maj/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Diplome> majV1(@PathVariable(value="id") String id,
                                             @RequestBody @Valid DiplomeRequest majReq) {
        Diplome objUpdated = diplomeService.maj(majReq, id);
        return new ResponseEntity<Diplome>(objUpdated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/v1/del/{id}")
    public ResponseEntity<Void> delV1(@PathVariable(value="id") String id) {
        diplomeService.del(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
