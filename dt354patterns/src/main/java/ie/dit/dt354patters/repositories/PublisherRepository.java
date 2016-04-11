package ie.dit.dt354patters.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ie.dit.dt354patters.model.Publisher;

@RepositoryRestResource
public interface PublisherRepository extends PagingAndSortingRepository<Publisher, Integer>{
    Iterable<Publisher> findAllByOrderByName();
    Publisher findByName(String name);
}
