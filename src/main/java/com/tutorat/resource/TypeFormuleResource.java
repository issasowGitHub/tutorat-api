package com.tutorat.resource;

import com.tutorat.dto.request.TypeFormuleRequest;
import com.tutorat.model.TypeFormule;
import com.tutorat.service.TypeFormuleService;
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
@RequestMapping("/type-formules/api")
public class TypeFormuleResource {

    private final TypeFormuleService typeFormSeervice;

    @GetMapping(value = "/v1/all")
    public ResponseEntity<Collection<TypeFormule>> allV1() {
        Collection<TypeFormule> liste = typeFormSeervice.getAll();
        return new ResponseEntity< Collection<TypeFormule> >(liste, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/one/{id}")
    public ResponseEntity<Optional<TypeFormule>> oneV1(@PathVariable(value="id") String id) {
        Optional<TypeFormule> oneObjet = typeFormSeervice.oneById(id);
        return new ResponseEntity< Optional<TypeFormule> >(oneObjet, HttpStatus.OK);
    }

    @PostMapping(value = "/v1/add")
    @Transactional(readOnly = false)
    public ResponseEntity<TypeFormule> addV1(@RequestBody @Valid TypeFormuleRequest addReq) {
        TypeFormule objSaved = typeFormSeervice.add(addReq);
        return new ResponseEntity<TypeFormule>(objSaved, HttpStatus.CREATED);
    }

    @PutMapping(value = "/v1/maj/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<TypeFormule> majV1(@PathVariable(value="id") String id,
                                             @RequestBody @Valid TypeFormuleRequest majReq) {
        TypeFormule objUpdated = typeFormSeervice.maj(majReq, id);
        return new ResponseEntity<TypeFormule>(objUpdated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/v1/del/{id}")
    public ResponseEntity<Void> delV1(@PathVariable(value="id") String id) {
        typeFormSeervice.del(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
