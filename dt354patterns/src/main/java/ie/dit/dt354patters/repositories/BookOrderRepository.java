package ie.dit.dt354patters.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ie.dit.dt354patters.model.BookOrder;
import ie.dit.dt354patters.model.Customer;

@Repository
public interface BookOrderRepository extends CrudRepository<BookOrder, Integer>{
    Iterable<BookOrder> findByCustomer(Customer customer);
    BookOrder findByCustomerAndNewOrder(Customer customer, boolean which);
}
