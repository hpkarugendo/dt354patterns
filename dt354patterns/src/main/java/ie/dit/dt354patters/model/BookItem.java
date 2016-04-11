package ie.dit.dt354patters.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class BookItem {
    @Id
    @GeneratedValue
    private int id;
    @OneToOne
    private Book book;
    private int quantity;
    @ManyToOne
    private BookOrder order;
    
    public BookItem() {
	super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookOrder getOrder() {
		return order;
	}

	public void setOrder(BookOrder order) {
		this.order = order;
	}

	public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}
