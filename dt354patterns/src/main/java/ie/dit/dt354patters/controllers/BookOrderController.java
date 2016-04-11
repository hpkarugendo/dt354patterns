package ie.dit.dt354patters.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ie.dit.dt354patters.model.BookOrder;
import ie.dit.dt354patters.repositories.BookOrderRepository;

@RestController
@RequestMapping("/orders")
public class BookOrderController {
    @Autowired
    BookOrderRepository boRepo;
    
    @RequestMapping(value="", method=RequestMethod.GET)
    public Iterable<BookOrder> listOrders(){
	return boRepo.findAll();
    }
}
