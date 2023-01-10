package com.ftn.clinicCentre.service;

import com.ftn.clinicCentre.entity.ERecipeStatus;
import com.ftn.clinicCentre.entity.Recipe;

import java.util.List;

public interface RecipeService {

    List<Recipe> findAll();
    List<Recipe> findByStatus(ERecipeStatus status);
    Recipe findById(Long id);
    Recipe save(Recipe recipe);
}
