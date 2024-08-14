package app.webbank.dao;

import app.webbank.model.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
}
