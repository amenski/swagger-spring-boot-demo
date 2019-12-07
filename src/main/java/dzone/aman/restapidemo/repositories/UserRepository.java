package dzone.aman.restapidemo.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dzone.aman.restapidemo.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
