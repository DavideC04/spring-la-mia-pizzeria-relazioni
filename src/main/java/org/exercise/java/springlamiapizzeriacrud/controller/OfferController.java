package org.exercise.java.springlamiapizzeriacrud.controller;

import org.exercise.java.springlamiapizzeriacrud.model.Pizza;
import org.exercise.java.springlamiapizzeriacrud.model.SpecialOffer;
import org.exercise.java.springlamiapizzeriacrud.repository.OfferRepository;
import org.exercise.java.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private PizzaRepository pizzaRepository;

    // metodo per creare un'offerta speciale
    @GetMapping("/create")
    public String create(@RequestParam("pizzaId") Integer pizzaId, Model model) {
        // recupero la Pizza dalla pizzaId
        Optional<Pizza> pizzaResult = pizzaRepository.findById(pizzaId);
        if (pizzaResult.isPresent()) {
            // se esiste la pizza con quell'id, proseguo
            Pizza pizza = pizzaResult.get();
            // creo l'offerta da passare alla view
            SpecialOffer specialOffer = new SpecialOffer();
            // allo specialOffer associo la pizza presa dal db
            specialOffer.setPizza(pizza);
            // precarico i campi dati dai valori di default
            specialOffer.setStartDate(LocalDate.now());
            specialOffer.setEndDate(LocalDate.now().plusDays(30));
            // passo la specialOffer alla view con il model
            model.addAttribute("specialOffer", specialOffer);
            return "offers/form";
        } else {
            // se non esiste sollevo un'eccezione
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pizza with id " + pizzaId + " not found");
        }

    }


    @PostMapping("/create")
    public String doCreate(@ModelAttribute("specialOffer") SpecialOffer specialOfferForm) {
        //salvo lo specialOffer sul database
        offerRepository.save(specialOfferForm);
        return "redirect:/pizza/show/" + specialOfferForm.getPizza().getId();

    }

    @GetMapping("/edit/{offerId}")
    public String edit(@PathVariable("offerId") Integer id, Model model) {
        Optional<SpecialOffer> specialOfferResult = offerRepository.findById(id);
        if (specialOfferResult.isPresent()) {
            model.addAttribute("specialOffer", specialOfferResult.get());
            return "offers/edit";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/edit/{offerId}")
    public String doEdit(@PathVariable("offerId") Integer offerId, @ModelAttribute("specialOffer") SpecialOffer specialOfferForm) {
        specialOfferForm.setId(offerId);
        offerRepository.save(specialOfferForm);
        return "redirect:/pizza/show/" + specialOfferForm.getPizza().getId();
    }


    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Optional<SpecialOffer> specialOfferResult = offerRepository.findById(id);
        if (specialOfferResult.isPresent()) {
            Integer pizzaId = specialOfferResult.get().getPizza().getId();
            offerRepository.deleteById(id);
            return "redirect:/pizza/show/" + pizzaId;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }
}
