package it.epicode.u5w3d1pratica.controller;


import it.epicode.u5w3d1pratica.dto.AutoreDto;
import it.epicode.u5w3d1pratica.exception.NonTrovatoException;
import it.epicode.u5w3d1pratica.exception.ValidationException;
import it.epicode.u5w3d1pratica.model.Autore;
import it.epicode.u5w3d1pratica.service.AutoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class AutoreController {

    @Autowired
    private AutoreService autoreService;

    @PostMapping("/autori")
    @ResponseStatus(HttpStatus.CREATED)
    public Autore saveAutore(@RequestBody @Validated AutoreDto autoreDto, BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().
                    stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (e,c)->e+c));
        }

        return autoreService.saveAutore(autoreDto);
    }

    @GetMapping("/autori")
    public Page<Autore> getAutori(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String sortBy){


        return autoreService.getAutori(page, size, sortBy);
    }

    @GetMapping("/autori/{id}")
    public Autore getAutore(@PathVariable int id) throws NonTrovatoException {
        return autoreService.getAutore(id);
    }
    @PutMapping("/autori/{id}")
    public Autore updateAutore(@PathVariable int id,@RequestBody @Validated AutoreDto autoreDto, BindingResult bindingResult) throws NonTrovatoException, ValidationException {
        if (bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().
                    stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("", (e,c)->e+c));
        }
        return autoreService.updateAutore(id, autoreDto);
    }

    @DeleteMapping("/autori/{id}")
    public void deleteAutore(@PathVariable int id) throws NonTrovatoException {
        autoreService.deleteAutore(id);
    }

    @PatchMapping("/autori/{id}")
    public String patchAutore(@PathVariable int id, @RequestBody MultipartFile file) throws NonTrovatoException, IOException {
        return autoreService.patchAutore(id, file);
    }
}
