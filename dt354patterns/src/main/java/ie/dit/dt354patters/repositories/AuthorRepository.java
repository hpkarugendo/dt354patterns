package ie.dit.dt354patters.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ie.dit.dt354patters.model.Author;

@RepositoryRestResource
public interface AuthorRepository extends PagingAndSortingRepository<Author, Integer>{
    Iterable<Author> findByFirstName(String firstName);
    Iterable<Author> findByLastName(String lastName);
    Iterable<Author> findByFirstNameAndLastName(String firstName, String lastName);
    Iterable<Author> findAllByOrderByFirstNameAsc();
    Iterable<Author> findAllByOrderByLastNameAsc();
}
