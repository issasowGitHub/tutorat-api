package com.tutorat.resource;

import com.tutorat.dto.request.TypeEncadrementRequest;
import com.tutorat.model.TypeEncadrement;
import com.tutorat.service.TypeEncadrementService;
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
@RequestMapping("/type-encadrements/api")
public class TypeEncadrementResource {

    private final TypeEncadrementService typeEncadrementService;

    @GetMapping(value = "/v1/all")
    public ResponseEntity<Collection<TypeEncadrement>> allV1() {
        Collection<TypeEncadrement> liste = typeEncadrementService.getAll();
        return new ResponseEntity< Collection<TypeEncadrement> >(liste, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/one/{id}")
    public ResponseEntity<Optional<TypeEncadrement>> oneV1(@PathVariable(value="id") String id) {
        Optional<TypeEncadrement> oneObjet = typeEncadrementService.oneById(id);
        return new ResponseEntity< Optional<TypeEncadrement> >(oneObjet, HttpStatus.OK);
    }

    @PostMapping(value = "/v1/add")
    @Transactional(readOnly = false)
    public ResponseEntity<TypeEncadrement> addV1(@RequestBody @Valid TypeEncadrementRequest addReq) {
        TypeEncadrement objSaved = typeEncadrementService.add(addReq);
        return new ResponseEntity<TypeEncadrement>(objSaved, HttpStatus.CREATED);
    }

    @PutMapping(value = "/v1/maj/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<TypeEncadrement> majV1(@PathVariable(value="id") String id,
                                             @RequestBody @Valid TypeEncadrementRequest majReq) {
        TypeEncadrement objUpdated = typeEncadrementService.maj(majReq, id);
        return new ResponseEntity<TypeEncadrement>(objUpdated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/v1/del/{id}")
    public ResponseEntity<Void> delV1(@PathVariable(value="id") String id) {
        typeEncadrementService.del(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
