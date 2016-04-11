package ie.dit.dt354patters.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class BookOrder implements Cloneable{
    @Id
    @GeneratedValue
    private int id;
    private String orderDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<BookItem> bookItems;
    private boolean newOrder;
    private double orderTotal;
    @ManyToOne
    private Customer customer;
    
    public BookOrder() {
	super();
	this.bookItems = new ArrayList<BookItem>();
    }
    
    

    public List<BookItem> getBookItems() {
        return bookItems;
    }



    public void setBookItems(List<BookItem> bookItems) {
        this.bookItems = bookItems;
    }



    public boolean isNewOrder() {
        return newOrder;
    }
    
    public boolean getNewOrder(){
	return newOrder;
    }



    public void setNewOrder(boolean newOrder) {
        this.newOrder = newOrder;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<BookItem> getItems() {
        return bookItems;
    }

    public void setItems(List<BookItem> bookItems) {
        this.bookItems = bookItems;
    }

    public double getOrderTotal() {
    	for(BookItem i: this.bookItems){
    		this.orderTotal = this.orderTotal + (i.getQuantity() * i.getBook().getPrice());
    	}
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }
    
    public Object clone() {
	Object clone = null;
	      
	try {
	       clone = super.clone();
	         
	} catch (CloneNotSupportedException e) {
	         e.printStackTrace();
	}
	      
	    return clone;
	}

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    
}
