package ie.dit.dt354patters.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ie.dit.dt354patters.model.Customer;

@RepositoryRestResource
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer>{
    Customer findByEmail(String email);
}
