package en.unideb.inf.bootstrap_m10.controller;

import en.unideb.inf.bootstrap_m10.model.Person;
import en.unideb.inf.bootstrap_m10.model.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    PersonRepository personRepository;

    @GetMapping("")
    public String showHomepage(){
        return "index";
    }

    @GetMapping("/persons")
    public String getAllPersons(Model model){
        List<Person> personsList = personRepository.findAll();
        model.addAttribute("personsList", personsList);
        return "persons";
    }

    @GetMapping("/persons/new")
    String newPerson(Model model){
        model.addAttribute("newPerson", new Person());
        model.addAttribute("pageTitle", "Add new person");
        return "newPersonForm";
    }

    @GetMapping("/persons/edit/{id}")
    String editPerson(@PathVariable Integer id, Model model){
        Person personToUpdate = personRepository.findById(id).get();
        model.addAttribute("newPerson", personToUpdate);
        model.addAttribute("pageTitle", "Edit person");
        return "newPersonForm";
    }

    @PostMapping("/persons/save")
    String savePerson(Person person, RedirectAttributes rd){
        personRepository.save(person);
        rd.addFlashAttribute("message", "Test message");
        return "redirect:/persons";
    }

    @GetMapping("/persons/delete/{id}")
    String deletePerson(@PathVariable Integer id){
        Person personToDelete = personRepository.findById(id).get();
        if (personToDelete!=null)
            personRepository.delete(personToDelete);
        return "redirect:/persons";
    }
}
