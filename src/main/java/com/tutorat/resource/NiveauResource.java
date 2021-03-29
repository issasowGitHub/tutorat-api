package com.tutorat.resource;

import com.tutorat.dto.request.NiveauRequest;
import com.tutorat.model.Niveau;
import com.tutorat.service.NiveauService;
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
@RequestMapping("/niveaux/api")
public class NiveauResource {

    private final NiveauService nivService;

    @GetMapping(value = "/v1/all")
    public ResponseEntity<Collection<Niveau>> allV1() {
        Collection<Niveau> liste = nivService.getAll();
        return new ResponseEntity< Collection<Niveau> >(liste, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/one/{id}")
    public ResponseEntity<Optional<Niveau>> oneV1(@PathVariable(value="id") String id) {
        Optional<Niveau> oneObjet = nivService.oneById(id);
        return new ResponseEntity< Optional<Niveau> >(oneObjet, HttpStatus.OK);
    }

    @PostMapping(value = "/v1/add")
    @Transactional(readOnly = false)
    public ResponseEntity<Niveau> addV1(@RequestBody @Valid NiveauRequest addReq) {
        Niveau objSaved = nivService.add(addReq);
        return new ResponseEntity<Niveau>(objSaved, HttpStatus.CREATED);
    }

    @PutMapping(value = "/v1/maj/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Niveau> majV1(@PathVariable(value="id") String id,
                                             @RequestBody @Valid NiveauRequest majReq) {
        Niveau objUpdated = nivService.maj(majReq, id);
        return new ResponseEntity<Niveau>(objUpdated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/v1/del/{id}")
    public ResponseEntity<Void> delV1(@PathVariable(value="id") String id) {
        nivService.del(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
