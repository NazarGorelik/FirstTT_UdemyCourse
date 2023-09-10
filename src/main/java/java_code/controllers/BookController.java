package java_code.controllers;

import java_code.dao.BookDAO;
import java_code.dao.PersonDAO;
import java_code.models.Book;
import java_code.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private BookDAO bookDAO;
    private PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping
    public String showBooks(Model model){
        model.addAttribute("books", bookDAO.showBooks());
        return "books/showBooks";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        model.addAttribute("book", bookDAO.showBook(id));

        Optional<Person> owner = bookDAO.getOwnerOfBook(id);

        if(owner.isPresent()){
            model.addAttribute("owner", owner.get());
        }else{
            model.addAttribute("people",personDAO.showPeople());
        }
        return "books/showBook";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id){
        bookDAO.releaseBook(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int id, @ModelAttribute("person") Person person){
        bookDAO.assignBook(person.getId(), id);
        return "redirect:/books/" + id;
    }

    @GetMapping("/add")
    public String addBookForm(@ModelAttribute("book") Book book){
        return "books/addBook";
    }

    @PostMapping
    public String addBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "books/addBook";
        }
        bookDAO.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/update")
    public String updateBookForm(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookDAO.showBook(id));
        return "books/updateBook";
    }

    @PostMapping("/{id}")
    public String updateBook(@PathVariable("id") int id, @ModelAttribute("book") @Valid  Book updatedBook, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "books/updateBook";
        }
        bookDAO.updateBook(id, updatedBook);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }
}
