package pankaj.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import pankaj.model.Registration;

public interface RegistrationRepo extends MongoRepository<Registration, String> {
	Registration findByEmailAndPassword(String email, String password);


}