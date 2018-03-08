
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	//The average and the standard deviation of the number of rendezvous per user.
	@Query("select avg(u.rendezvous.size), stddev(u.rendezvous.size) from User u")
	Double[] avgStddevRendezvousPerUser();

	//The ratio of users who have ever created a rendezvous versus the users who have never created any rendezvouses.
	@Query("select (select count(u) from User u where u.rendezvous.size>0)*1.0/count(u) from User u where u.rendezvous.size=0")
	Double[] ratioRendezvousVsNotRendezvous();

	//The average and the standard deviation of rendezvouses that are RSVPd per user.
	@Query("select avg(u.attendance.size), stddev(u.attendance.size) from User u")
	Double[] avgStddevAttendancePerUser();

}
