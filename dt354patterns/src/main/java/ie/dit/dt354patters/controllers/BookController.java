package ie.dit.dt354patters.controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.stormpath.sdk.servlet.account.AccountResolver;

import ie.dit.dt354patters.model.Author;
import ie.dit.dt354patters.model.Book;
import ie.dit.dt354patters.model.Publisher;
import ie.dit.dt354patters.repositories.AuthorRepository;
import ie.dit.dt354patters.repositories.BookCommentRepository;
import ie.dit.dt354patters.repositories.BookRepository;
import ie.dit.dt354patters.repositories.PublisherRepository;

@Controller
public class BookController {
    @Autowired
    private BookRepository bRepo;
    @Autowired
    private BookCommentRepository bcRepo;
    @Autowired
    private PublisherRepository pRepo;
    @Autowired
    private AuthorRepository aRepo;
    
    @RequestMapping(value="/book", method=RequestMethod.GET)
    public String addBookForm(Model model, HttpServletRequest req) {
	if(AccountResolver.INSTANCE.getAccount(req) != null){
	    	model.addAttribute("book", Book.getInstance());
		model.addAttribute("publishers", pRepo.findAll());
		model.addAttribute("publisher", new Publisher());
		model.addAttribute("authors", aRepo.findAll());
		model.addAttribute("author", Author.getInstance());
		
		return "books_form";
	}
	
	return "redirect:/login";
    }
    
    @RequestMapping(value="/book", method=RequestMethod.POST)
    public String addBook(Model model, HttpServletRequest req, MultipartFile file, Book book){
	Book thisBook = bRepo.save(book);
	try {
	    byte[] b = file.getBytes();
	    String ext = "";
	    if(file.getContentType().contains("png")){
		ext = ".png";
	    } else if(file.getContentType().contains("jpg") ||
		    file.getContentType().contains("jpeg")){
		ext = ".jpg";
	    } else if(file.getContentType().contains("gif")) {
		ext = ".gif";
	    } else if(file.getContentType().contains("bmp")) {
		ext = ".bmp";
	    }
	    FileOutputStream fos = new FileOutputStream("src/main/resources/static/images/" + 
	    	    "book" + thisBook.getId() + "cover" + ext);
	    thisBook.setImageFilename("book" + thisBook.getId() + "cover" + ext);
	    	            fos.write(b);
	    	            fos.close();
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Book anotherBook = bRepo.save(thisBook);
	System.out.println(file.getContentType().toString() + "#" + anotherBook.getImageFilename());
	return "redirect:/shelf";
    }
    
    @RequestMapping("/book/edit/{id}")
    public String editBook(Model model, HttpServletRequest req, @PathVariable Integer id){
	model.addAttribute("book", bRepo.findOne(id));
	model.addAttribute("authors", aRepo.findAll());
	model.addAttribute("publishers", pRepo.findAll());
	return "books_form";
    }
    
    @RequestMapping("/book/delete/{id}")
    public String addBook(Model model, HttpServletRequest req, @PathVariable Integer id){
	bRepo.delete(id);
	return "redirect:/shelf";
    }
    
    @RequestMapping(value="/shelf", method=RequestMethod.GET)
    public String goToShelf(Model model){
	model.addAttribute("books", bRepo.findAll());
	model.addAttribute("bookCount", bRepo.count());
	return "shelf";
    }
    
    @RequestMapping(value="/author", method=RequestMethod.GET)
    public String addAuthorForm(Model model, HttpServletRequest req){
	model.addAttribute("author", Author.getInstance());
	return "authors_form";
    }
    
    @RequestMapping(value="/author", method=RequestMethod.POST)
    public String addAuthor(Author author, Model model, HttpServletRequest req){
	Author a = aRepo.save(author);
	System.out.println(a.getLastName());
	return "redirect:/authors";
    }
    
    @RequestMapping(value="/authors", method=RequestMethod.GET)
    public String listAuthors(Model model, HttpServletRequest req){
	model.addAttribute("authors", aRepo.findAll());
	return "authors_list";
    }
    
    @RequestMapping(value="/authors-by-firstName", method=RequestMethod.GET)
    public String listAuthorsByFname(Model model, HttpServletRequest req){
	model.addAttribute("authors", aRepo.findAllByOrderByFirstNameAsc());
	return "authors_list";
    }
    
    @RequestMapping(value="/authors-by-lastName", method=RequestMethod.GET)
    public String listAuthorsByLname(Model model, HttpServletRequest req){
	model.addAttribute("authors", aRepo.findAllByOrderByLastNameAsc());
	return "authors_list";
    }
    
    @RequestMapping(value="/authors/search", method=RequestMethod.GET)
    public String listAuthorsByLnameSearch(Model model, HttpServletRequest req){
	String lName = req.getParameter("searchName");
	model.addAttribute("authors", aRepo.findByLastName(lName));
	return "authors_list";
    }
    
    @RequestMapping("author/edit/{id}")
    public String updateAuthor(@PathVariable Integer id, Model model){
	model.addAttribute("author", aRepo.findOne(id));
	return "authors_form";
    }
	
    @RequestMapping("author/delete/{id}")
    public String deleteAuthor(@PathVariable Integer id, Model model){
	aRepo.delete(id);;
	return "redirect:/authors";
    }
    
    @RequestMapping(value="/publisher", method=RequestMethod.POST)
    public String addPublisher(Publisher publisher, Model model, HttpServletRequest req){
	Publisher p = pRepo.save(publisher);
	System.out.println(p.getName());
	return "redirect:/publishers";
    }
    
    @RequestMapping(value="/publisher", method=RequestMethod.GET)
    public String addPublisherForm(Model model, HttpServletRequest req){
	model.addAttribute("publisher", new Publisher());
	return "publishers_form";
    }
    
    @RequestMapping(value="/publishers", method=RequestMethod.GET)
    public String listPublishers(Model model, HttpServletRequest req){
	model.addAttribute("publishers", pRepo.findAll());
	return "publishers_list";
    }
    
    @RequestMapping(value="/publishers-by-name", method=RequestMethod.GET)
    public String listPublishersByName(Model model, HttpServletRequest req){
	model.addAttribute("publishers", pRepo.findAllByOrderByName());
	return "publishers_list";
    }
    
    @RequestMapping("publisher/edit/{id}")
    public String updatePublisher(@PathVariable Integer id, Model model){
	model.addAttribute("publisher", pRepo.findOne(id));
	return "publishers_form";
    }
    
    @RequestMapping("publisher/delete/{id}")
    public String deletePublisher(@PathVariable Integer id, Model model){
	pRepo.delete(id);;
	return "redirect:/publishers";
    }
    
}
