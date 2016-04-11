package patterns;

import java.util.ArrayList;
import java.util.List;

import ie.dit.dt354patters.model.Book;

public class CriteriaFantasy implements Criteria{

    @Override
    public List<Book> meetsCriteria(List<Book> books) {
	List<Book> tBooks = new ArrayList<Book>();
	for(Book b : books){
	    if(b.getGenre().equalsIgnoreCase("fantasy")){
		tBooks.add(b);
	    }
	}
	
	return tBooks;
    }

}
