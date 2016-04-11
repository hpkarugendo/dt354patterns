package ie.dit.dt354patters.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ie.dit.dt354patters.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
    Book findBookByIsbn(String isbn);
    List<Book> findAll();
    List<Book> findAllByOrderByTitle();
}
