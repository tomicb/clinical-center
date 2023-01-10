package com.ftn.clinicCentre.service.impl;

import com.ftn.clinicCentre.entity.ERecipeStatus;
import com.ftn.clinicCentre.entity.Recipe;
import com.ftn.clinicCentre.repository.RecipeRepository;
import com.ftn.clinicCentre.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    @Override
    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    @Override
    public List<Recipe> findByStatus(ERecipeStatus status) {
        return recipeRepository.findRecipeByStatus(status);
    }

    @Override
    public Recipe findById(Long id) { return recipeRepository.findById(id).orElse(null); }

    @Override
    public Recipe save(Recipe recipe) { return recipeRepository.save(recipe); }
}
