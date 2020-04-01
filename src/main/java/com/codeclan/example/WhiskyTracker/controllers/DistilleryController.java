package com.codeclan.example.WhiskyTracker.controllers;

import com.codeclan.example.WhiskyTracker.models.Distillery;
import com.codeclan.example.WhiskyTracker.repositories.DistilleryRepository.DistilleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/distilleries")
public class DistilleryController {

    @Autowired
    DistilleryRepository distilleryRepository;

    @GetMapping(value="/{id}")
    public ResponseEntity<Optional<Distillery>> getDistillery(@PathVariable Long id){
        return new ResponseEntity<>(distilleryRepository.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity postDistillery(@RequestBody Distillery distillery){
        distilleryRepository.save(distillery);
        return new ResponseEntity(distillery, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity findByRegionOrAge(@RequestParam(name = "region", required = false) String region,
                                       @RequestParam(name = "age", required = false) Integer age) {
        if(region != null && age == null){
            return new ResponseEntity(distilleryRepository.findDistilleryByRegion(region), HttpStatus.OK);
        } else if (age != null && region == null){
            return new ResponseEntity(distilleryRepository.findDistilleryByWhiskiesAge(age), HttpStatus.OK);
        }
        return new ResponseEntity<>(distilleryRepository.findAll(), HttpStatus.OK);
    }

}
