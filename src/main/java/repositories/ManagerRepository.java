
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer> {

	//The managers who provide more services than the average.
	@Query("select m from Manager m where m.services.size > (select avg(m.services.size) from Manager m)")
	Collection<Manager> managerWithAboveAverageServices();

	//The managers who have got more services cancelled.
	@Query("select s.manager from Service s where s.cancelled=true group by s.manager order by count(s) desc")
	List<Manager> managerWithMoreServiceCancelled();

}
