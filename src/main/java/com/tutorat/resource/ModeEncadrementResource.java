package com.tutorat.resource;

import com.tutorat.dto.request.ModeEncadrementRequest;
import com.tutorat.model.ModeEncadrement;
import com.tutorat.service.ModeEncadrementService;
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
@RequestMapping("/mode-encadrements/api")
public class ModeEncadrementResource {

    private final ModeEncadrementService modEncadService;

    @GetMapping(value = "/v1/all")
    public ResponseEntity<Collection<ModeEncadrement>> allV1() {
        Collection<ModeEncadrement> liste = modEncadService.getAll();
        return new ResponseEntity< Collection<ModeEncadrement> >(liste, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/one/{id}")
    public ResponseEntity<Optional<ModeEncadrement>> oneV1(@PathVariable(value="id") String id) {
        Optional<ModeEncadrement> oneObjet = modEncadService.oneById(id);
        return new ResponseEntity< Optional<ModeEncadrement> >(oneObjet, HttpStatus.OK);
    }

    @PostMapping(value = "/v1/add")
    @Transactional(readOnly = false)
    public ResponseEntity<ModeEncadrement> addV1(@RequestBody @Valid ModeEncadrementRequest addReq) {
        ModeEncadrement objSaved = modEncadService.add(addReq);
        return new ResponseEntity<ModeEncadrement>(objSaved, HttpStatus.CREATED);
    }

    @PutMapping(value = "/v1/maj/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<ModeEncadrement> majV1(@PathVariable(value="id") String id,
                                             @RequestBody @Valid ModeEncadrementRequest majReq) {
        ModeEncadrement objUpdated = modEncadService.maj(majReq, id);
        return new ResponseEntity<ModeEncadrement>(objUpdated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/v1/del/{id}")
    public ResponseEntity<Void> delV1(@PathVariable(value="id") String id) {
        modEncadService.del(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
