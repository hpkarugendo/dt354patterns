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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.servlet.account.AccountResolver;

import ie.dit.dt354patters.model.Book;
import ie.dit.dt354patters.model.BookItem;
import ie.dit.dt354patters.model.BookOrder;
import ie.dit.dt354patters.model.Cart;
import ie.dit.dt354patters.model.Customer;
import ie.dit.dt354patters.repositories.BookOrderRepository;
import ie.dit.dt354patters.repositories.BookRepository;
import ie.dit.dt354patters.repositories.CustomerRepository;
import patterns.Criteria;
import patterns.CriteriaFantasy;

@Controller
public class ShopController {
    @Autowired
    BookOrderRepository boRepo;
    @Autowired
    CustomerRepository cRepo;
    @Autowired
    BookRepository bRepo;
    
    
    @RequestMapping(value="/", method=RequestMethod.GET)
    public String shopListing(Model model, HttpServletRequest req) {
	if (AccountResolver.INSTANCE.getAccount(req) != null) {
            Account acc = AccountResolver.INSTANCE.getAccount(req);
            
            if(acc.isMemberOfGroup("Customers")){
        	Customer c = cRepo.findByEmail(acc.getEmail());
        	if(c != null){
        	    return "redirect:/catalog";
        	} else {
        	    c = new Customer();
            	c.setEmail(acc.getEmail());
            	c.setFirstName(acc.getGivenName());
            	c.setLastName(acc.getSurname());
            	Customer c2 = cRepo.save(c);
            	model.addAttribute("cart", c2.getCart());
        	model.addAttribute("books", bRepo.findAllByOrderByTitle());
        	model.addAttribute("total", c2.getCart().getTotalPrice());
            	model.addAttribute("customer", c2);
    		return "redirect:/catalog";
        	}
            } else {
        	model.addAttribute("books", bRepo.findAllByOrderByTitle());
        	return "redirect:/shelf";
	    }
	}

        return "shop";
    }
    
    @RequestMapping(value="/books/search", method=RequestMethod.GET)
    public String shopListingSearch(Model model, HttpServletRequest req) {
    	Account acc = AccountResolver.INSTANCE.getAccount(req);
            String title = req.getParameter("searchTitle");
        	model.addAttribute("books", bRepo.findByTitle(title));
            	model.addAttribute("customer", cRepo.findByEmail(acc.getEmail()));
            return "catalog";
    }
    
    @RequestMapping(value="/books/fantasy", method=RequestMethod.GET)
    public String shopListingByGenre(Model model, HttpServletRequest req) {
		Account acc = AccountResolver.INSTANCE.getAccount(req);
           Criteria ctr = new CriteriaFantasy();
            	for(Book b : ctr.meetsCriteria(bRepo.findAll())){
            	    System.out.println(b);
            	}
        	model.addAttribute("books", ctr.meetsCriteria(bRepo.findAll()));
            model.addAttribute("customer", cRepo.findByEmail(acc.getEmail()));
    		return "catalog";
    }
    
    @RequestMapping(value="/catalog", method=RequestMethod.GET)
    public String catalogList(Model model, HttpServletRequest req){
	Account acc = AccountResolver.INSTANCE.getAccount(req);
	Customer c = cRepo.findByEmail(acc.getEmail());
	model.addAttribute("customer", c);
	try {
	    model.addAttribute("cart", c.getCart());
	} catch (Exception e1) {
	    Cart ct = new Cart();
	    c.setCart(ct);
	    model.addAttribute("cart", c.getCart());
	}
	model.addAttribute("books", bRepo.findAll());
	try {
	    model.addAttribute("total", c.getCart().getTotalPrice());
	} catch (Exception e) {
	    model.addAttribute("total", 0.0);
	}
	return "catalog";
    }
    
    @RequestMapping(value="/profile", method=RequestMethod.GET)
    public String profileForm(Model model, HttpServletRequest req){
	model.addAttribute("customer", new Customer());
	return "profiles_form";
    }
    
    @RequestMapping(value="/profile", method=RequestMethod.POST)
    public String profileAdd(Model model, HttpServletRequest req, Customer customer, MultipartFile file,
	    RedirectAttributes rAttributes){
	Account acc = AccountResolver.INSTANCE.getAccount(req);
	Customer thisCustomer = cRepo.save(customer);
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
	    	    "avatar" + thisCustomer.getId() + "pic" + ext);
	    thisCustomer.setAvatar("avatar" + thisCustomer.getId() + "pic" + ext);
	    	            fos.write(b);
	    	            fos.close();
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	thisCustomer.setEmail(acc.getEmail());
	thisCustomer.setFirstName(acc.getGivenName());
	thisCustomer.setLastName(acc.getSurname());
	Customer anotherCus = cRepo.save(thisCustomer);
	rAttributes.addAttribute("customer", anotherCus);
	model.addAttribute("cart", anotherCus.getCart());
	model.addAttribute("books", bRepo.findAll());
	model.addAttribute("total", anotherCus.getCart().getTotalPrice());
	return "redirect:/";
    }
    
    
}
