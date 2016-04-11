package ie.dit.dt354patters.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import ie.dit.dt354patters.model.BookComment;

@RepositoryRestResource
public interface BookCommentRepository extends PagingAndSortingRepository<BookComment, Integer>{

}
