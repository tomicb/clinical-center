package com.ftn.clinicCentre.repository;

import com.ftn.clinicCentre.entity.ERecipeStatus;
import com.ftn.clinicCentre.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findRecipeByStatus(ERecipeStatus status);
}
