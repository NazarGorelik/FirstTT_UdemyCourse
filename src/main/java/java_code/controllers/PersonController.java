package java_code.controllers;

import java_code.dao.PersonDAO;
import java_code.models.Person;
import java_code.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping
    private String showPeople(Model model){
        model.addAttribute("people", personDAO.showPeople());
        return "people/showPeople";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDAO.showPerson(id));
        model.addAttribute("books", personDAO.getBooks(id));
        return "people/showPerson";
    }

    @GetMapping("/{id}/update")
    public String updatePersonForm(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDAO.showPerson(id));
        return "people/updatePerson";
    }

    @PostMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, @PathVariable("id") int id){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()){
            return "people/updatePerson";
        }
        personDAO.updatePerson(id, person);
        return "redirect:/people";
    }

    @GetMapping("/add")
    public String addPersonForm(@ModelAttribute("person") Person person){
        return "people/addPerson";
    }

    @PostMapping
    public String addPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()){
            return "people/addPerson";
        }
        personDAO.addPerson(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        personDAO.deletePerson(id);
        return "redirect:/people";
    }
}
