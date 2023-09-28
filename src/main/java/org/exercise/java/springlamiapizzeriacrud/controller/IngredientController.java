package org.exercise.java.springlamiapizzeriacrud.controller;

import org.exercise.java.springlamiapizzeriacrud.model.Ingredient;
import org.exercise.java.springlamiapizzeriacrud.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {

    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("ingredientList", ingredientRepository.findAll());
        // passo al model un attributo ingredientObj
        model.addAttribute("ingredientObj", new Ingredient());
        return "ingredients/index";
    }

    //create
    @PostMapping("/create")
    public String doCreate(@ModelAttribute("ingredientObj") Ingredient ingredientForm) {
        // prendo l'oggetto Ingredient dalla request
        // lo salvo sul database
        ingredientRepository.save(ingredientForm);
        // redirect sulla pagina index
        return "redirect:/ingredients";

    }

    // delete
    @PostMapping("/delete/{ingredientId}")
    public String delete(@PathVariable("ingredientId") Integer id) {
        // elimino l'ingrediente preso dal db
        ingredientRepository.deleteById(id);
        // redirect sulla pagina index
        return "redirect:/ingredients";

    }
}
