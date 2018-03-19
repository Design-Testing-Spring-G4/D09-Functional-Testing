
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Service;

public interface ServiceRepository extends JpaRepository<Service, Integer> {

	@Query("select s from Service s where s.requests.size=(select max(s.requests.size) from Service s)")
	Collection<Service> bestSellingServices();

}
