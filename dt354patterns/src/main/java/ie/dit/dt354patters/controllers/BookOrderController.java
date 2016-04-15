package ie.dit.dt354patters.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.number.money.MonetaryAmountFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.servlet.account.AccountResolver;
import ie.dit.dt354patters.model.Book;
import ie.dit.dt354patters.model.BookItem;
import ie.dit.dt354patters.model.BookOrder;
import ie.dit.dt354patters.model.Customer;
import ie.dit.dt354patters.repositories.BookOrderRepository;
import ie.dit.dt354patters.repositories.BookRepository;
import ie.dit.dt354patters.repositories.CustomerRepository;

@Controller
public class BookOrderController {
    @Autowired
    private BookOrderRepository boRepo;
    @Autowired
    private CustomerRepository cRepo;
    @Autowired
    private BookRepository bRepo;
    private final HashMap<String, BookOrder> orders = new HashMap<>();
    
    @RequestMapping("order/add/{id}")
    public String addtoCart(Model model, HttpServletRequest req, @PathVariable Integer id){
		Account acc = AccountResolver.INSTANCE.getAccount(req);
		if(acc == null) {
			return "redirect:/login";
		} else {
			Customer cus = cRepo.findByEmail(acc.getEmail());
			if(orders.isEmpty()){
				BookOrder b = boRepo.findByCustomerAndNewOrder(cus, true);
				if(b == null){
					b = new BookOrder();
					b.setCustomer(cus);
					b.setNewOrder(true);
					BookOrder bo2 = boRepo.save(b);
					orders.put(cus.getEmail(), bo2);
				}
			}
			
			BookOrder bo = orders.get(cus.getEmail());
			Book bk = bRepo.findOne(id);
			boolean itemFound = false;
			if(bo == null || bo.getBookItems().size() < 1){
				bo = new BookOrder();
				bo.setCustomer(cus);
				bo.setNewOrder(true);
				List<BookItem> items = new ArrayList<BookItem>();
				BookItem bi = new BookItem();
				bi.setBook(bk);
				bi.setQuantity(1);
				items.add(bi);
				bo.setBookItems(items);
				boRepo.save(bo);
			} else {
				for(BookItem i : bo.getBookItems()){
					if(bk.getId() == i.getBook().getId()){
						i.setQuantity(i.getQuantity() + 1);
						boRepo.save(bo);
						itemFound = true;
					}
				}
				
				if(itemFound == false){
					
				} else {
					BookItem bi = new BookItem();
					bi.setBook(bk);
					bi.setQuantity(1);
					bo.getBookItems().add(bi);
					boRepo.save(bo);
				}
			}
			model.addAttribute("order", bo);
			model.addAttribute("total", bo.getOrderTotal());
			
			return "cart";
		}
    }
}
