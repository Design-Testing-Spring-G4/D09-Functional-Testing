
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {

	//The average and the standard deviation of the number of answers to the questions per rendezvous.
	@Query("select avg(u.answers.size), stddev(u.answers.size) from User u")
	Double[] avgStddevAnswer();

}
