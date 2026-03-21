package pankaj.repo;
import org.springframework.data.mongodb.repository.MongoRepository;

import pankaj.model.Contact;
public interface ContactRepo extends MongoRepository<Contact, String>{

}
