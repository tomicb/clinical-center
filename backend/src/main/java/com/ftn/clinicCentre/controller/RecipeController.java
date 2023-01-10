package com.ftn.clinicCentre.controller;

import com.ftn.clinicCentre.dto.RecipeDTO;
import com.ftn.clinicCentre.entity.*;
import com.ftn.clinicCentre.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;
    
    @Autowired
    private NurseService nurseService;


    @PreAuthorize("hasRole('ROLE_NURSE')")
    @GetMapping
    public ResponseEntity<List<RecipeDTO>> getRecipes() {
        List<Recipe> recipes = recipeService.findByStatus(ERecipeStatus.PENDING);

        List<RecipeDTO> recipesDTO = new ArrayList<>();
        for(Recipe recipe: recipes) {
            recipesDTO.add(new RecipeDTO(recipe));
        }

        return new ResponseEntity<>(recipesDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_NURSE')")
    @GetMapping(value = "/approve/{id}")
    public ResponseEntity<RecipeDTO> approveRecipe(@PathVariable("id") Long id, Authentication authentication) {

        Nurse nurse = nurseService.findOneByEmail(authentication.getName());
        if(nurse == null)
            return new ResponseEntity<>(new RecipeDTO(), HttpStatus.BAD_REQUEST);

        Recipe recipe = recipeService.findById(id);
        if(recipe == null || recipe.getStatus() != ERecipeStatus.PENDING)
            return new ResponseEntity<>(new RecipeDTO(), HttpStatus.NOT_FOUND);

        recipe.setStatus(ERecipeStatus.APPROVED);
        recipe.setNurse(nurse);
        recipe = recipeService.save(recipe);

        return new ResponseEntity<>(new RecipeDTO(recipe), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_NURSE')")
    @GetMapping(value = "/reject/{id}")
    public ResponseEntity<RecipeDTO> rejectRecipe(@PathVariable("id") Long id, Authentication authentication) {

        Nurse nurse = nurseService.findOneByEmail(authentication.getName());
        if(nurse == null)
            return new ResponseEntity<>(new RecipeDTO(), HttpStatus.BAD_REQUEST);

        Recipe recipe = recipeService.findById(id);
        if(recipe == null || recipe.getStatus() != ERecipeStatus.PENDING) {
            return new ResponseEntity<>(new RecipeDTO(), HttpStatus.NOT_FOUND);
        }

        recipe.setStatus(ERecipeStatus.REJECTED);
        recipe.setNurse(nurse);
        recipe = recipeService.save(recipe);

        return new ResponseEntity<>(new RecipeDTO(recipe), HttpStatus.OK);
    }
}
