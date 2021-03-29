package com.tutorat.resource;

import com.tutorat.dto.request.AnneeRequest;
import com.tutorat.dto.request.SerieRequest;
import com.tutorat.model.Serie;
import com.tutorat.service.SerieService;
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
@RequestMapping("/series/api")
public class SerieResource {

    private final SerieService serieService;

    @GetMapping(value = "/v1/all")
    public ResponseEntity<Collection<Serie>> allV1() {
        Collection<Serie> liste = serieService.getAll();
        return new ResponseEntity< Collection<Serie> >(liste, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/one/{id}")
    public ResponseEntity<Optional<Serie>> oneV1(@PathVariable(value="id") String id) {
        Optional<Serie> oneObjet = serieService.oneById(id);
        return new ResponseEntity< Optional<Serie> >(oneObjet, HttpStatus.OK);
    }

    @PostMapping(value = "/v1/add")
    @Transactional(readOnly = false)
    public ResponseEntity<Serie> addV1(@RequestBody @Valid SerieRequest addReq) {
        Serie objSaved = serieService.add(addReq);
        return new ResponseEntity<Serie>(objSaved, HttpStatus.CREATED);
    }

    @PutMapping(value = "/v1/maj/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Serie> majV1(@PathVariable(value="id") String id,
                                             @RequestBody @Valid SerieRequest majReq) {
        Serie objUpdated = serieService.maj(majReq, id);
        return new ResponseEntity<Serie>(objUpdated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/v1/del/{id}")
    public ResponseEntity<Void> delV1(@PathVariable(value="id") String id) {
        serieService.del(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
