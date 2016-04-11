package ie.dit.dt354patters.runners;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import ie.dit.dt354patters.repositories.BookRepository;

public class BookStoreRunner implements CommandLineRunner{
    @Autowired
    private BookRepository bRepo;

    @Override
    public void run(String... arg0) throws Exception {
	Logger l = Logger.getLogger(BookStoreRunner.class);
	l.info("Number of Books in Store: " + bRepo.count());
	
    }

}
