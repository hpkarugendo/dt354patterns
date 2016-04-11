package patterns;

import java.util.List;

import ie.dit.dt354patters.model.Book;

public interface Criteria {
    public List<Book> meetsCriteria(List<Book> books);
}
