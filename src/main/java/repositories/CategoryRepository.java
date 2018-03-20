
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;
import domain.Service;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	//Returns the category to which a certain trip is associated.
	@Query("select c from Category c where ?1 member of c.service")
	Category categoryOfService(Service s);
}
