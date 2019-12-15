package dzone.aman.restapidemo.dal.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dzone.aman.restapidemo.dal.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
