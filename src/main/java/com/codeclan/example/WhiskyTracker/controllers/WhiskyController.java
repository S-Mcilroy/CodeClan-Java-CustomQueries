package com.codeclan.example.WhiskyTracker.controllers;

import com.codeclan.example.WhiskyTracker.models.Whisky;
import com.codeclan.example.WhiskyTracker.repositories.WhiskyRepository.WhiskyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/whiskies")
public class WhiskyController {

    @Autowired
    WhiskyRepository whiskyRepository;


    @GetMapping(value="/{id}")
    public ResponseEntity<Optional<Whisky>> getWhisky(@PathVariable Long id){
        return new ResponseEntity<>(whiskyRepository.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity postWhisky(@RequestBody Whisky whisky){
        whiskyRepository.save(whisky);
        return new ResponseEntity(whisky, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity findByYearOrDistilleryRegionOrNameAndAge(
            @RequestParam(name = "year", required = false) Integer year,
            @RequestParam(name = "distillery", required = false) String distillery,
            @RequestParam(name = "age", required = false) Integer age,
            @RequestParam(name = "region", required = false) String region){
        if(year != null && distillery == null && age == null && region == null){
            return new ResponseEntity(whiskyRepository.findWhiskyByYear(year), HttpStatus.OK);
        } else if (distillery != null && age != null && year == null && region == null){
            return new ResponseEntity(whiskyRepository.findWhiskyByDistilleryNameAndAge(distillery, age), HttpStatus.OK);
        } else if (region != null && year == null && distillery == null && age == null){
            return new ResponseEntity(whiskyRepository.findWhiskyByDistilleryRegion(region), HttpStatus.OK);
        }
        return new ResponseEntity<>(whiskyRepository.findAll(), HttpStatus.OK);
    }

}
