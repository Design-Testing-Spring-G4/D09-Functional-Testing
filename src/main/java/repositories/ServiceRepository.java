
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {

	//The best-selling services.
	@Query("select s from Service s where s.requests.size=(select max(s.requests.size) from Service s)")
	Collection<Service> bestSellingServices();

	//The top-selling services.
	@Query("select s from Service s order by s.requests.size desc")
	List<Service> topSellingServices(Pageable pageable);
}
