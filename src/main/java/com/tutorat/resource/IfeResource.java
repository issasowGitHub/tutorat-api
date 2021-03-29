package com.tutorat.resource;

import com.tutorat.dto.request.IfeRequest;
import com.tutorat.model.Ife;
import com.tutorat.service.AnneeService;
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
@RequestMapping("/ifes/api")
public class IfeResource {

    private final IfeService ifeService;

    @GetMapping(value = "/v1/all")
    public ResponseEntity<Collection<Ife>> allV1() {
        Collection<Ife> liste = ifeService.getAll();
        return new ResponseEntity< Collection<Ife> >(liste, HttpStatus.OK);
    }

    @GetMapping(value = "/v1/one/{id}")
    public ResponseEntity<Optional<Ife>> oneV1(@PathVariable(value="id") String id) {
        Optional<Ife> oneObjet = ifeService.oneById(id);
        return new ResponseEntity< Optional<Ife> >(oneObjet, HttpStatus.OK);
    }

    @PostMapping(value = "/v1/add")
    @Transactional(readOnly = false)
    public ResponseEntity<Ife> addV1(@RequestBody @Valid IfeRequest addReq) {
        Ife objSaved = ifeService.add(addReq);
        return new ResponseEntity<Ife>(objSaved, HttpStatus.CREATED);
    }

    @PutMapping(value = "/v1/maj/{id}")
    @Transactional(readOnly = false)
    public ResponseEntity<Ife> majV1(@PathVariable(value="id") String id,
                                             @RequestBody @Valid IfeRequest majReq) {
        Ife objUpdated = ifeService.maj(majReq, id);
        return new ResponseEntity<Ife>(objUpdated, HttpStatus.OK);
    }

    @DeleteMapping(value = "/v1/del/{id}")
    public ResponseEntity<Void> delV1(@PathVariable(value="id") String id) {
        ifeService.del(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
