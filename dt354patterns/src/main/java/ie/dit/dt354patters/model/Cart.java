package ie.dit.dt354patters.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Cart {
    @Id
    private int id;
    @OneToMany
    private List<BookItem> bookItems;
    private double totalPrice;
    
    public Cart() {
	super();
	this.totalPrice = 0.0;
	this.bookItems = new ArrayList<BookItem>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<BookItem> getItems() {
        return bookItems;
    }

    public void setItems(List<BookItem> bookItems) {
        this.bookItems = bookItems;
    }

    public double getTotalPrice() {
	if(this.bookItems == null || this.bookItems.isEmpty()){
	    totalPrice = 0;
	} else {
	    calculateTotal();
	}
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    
    public void calculateTotal(){
	for(BookItem bi : getItems()){
	    this.totalPrice = this.totalPrice + (bi.getBook().getPrice() * bi.getQuantity());
	}
    }
    
    
}
