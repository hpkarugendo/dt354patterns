package ie.dit.dt354patters.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ie.dit.dt354patters.model.Book;
import ie.dit.dt354patters.repositories.BookRepository;

@RestController
@RequestMapping("/books")
public class BookRestController {
    @Autowired
    private BookRepository bRepo;
    
    @RequestMapping(value="", method=RequestMethod.GET)
    public Iterable<Book> listBooks(){
	return bRepo.findAll();
    }
    
    @RequestMapping(value="/{isbn}", method=RequestMethod.GET)
    public Book findBookByIsbn(@PathVariable String isbn){
	return bRepo.findBookByIsbn(isbn);
    }
}
