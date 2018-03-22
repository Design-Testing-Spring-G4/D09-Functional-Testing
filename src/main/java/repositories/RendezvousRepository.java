
package repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Rendezvous;

@Repository
public interface RendezvousRepository extends JpaRepository<Rendezvous, Integer> {

	//The average and the standard deviation of users per rendezvous.
	@Query("select avg(r.attendants.size), stddev(r.attendants.size) from Rendezvous r")
	Double[] avgStddevUserPerRendezvous();

	//The ratio of users who have ever created a rendezvous versus the users who have never created any rendezvouses.
	@Query("select (select count(u) from User u where u.rendezvous.size>0)*1.0/count(u) from User u where u.rendezvous.size=0")
	Double[] ratioRendezvousVsNotRendezvous();

	//The top-10 rendezvouses in terms of users who have RSVPd them.
	@Query("select r from Rendezvous r order by r.attendants.size")
	List<Rendezvous> topTenRendezvous(Pageable pageable);

	//The average and the standard deviation of announcements per rendezvous.
	@Query("select avg(r.announcements.size), stddev(r.announcements.size) from Rendezvous r")
	Double[] avgStddevAnnouncementsPerRendezvous();

	//The rendezvouses that whose number of announcements is above 75% the average number of announcements per rendezvous.
	@Query("select r from Rendezvous r where r.announcements.size > (select avg(r.announcements.size)*1.75 from Rendezvous r)")
	Collection<Rendezvous> announcementsWithAboveAverageRendezvous();

	// The rendezvouses that are linked to a number of rendezvouses that is greater than the average plus 10%.
	@Query("select r from Rendezvous r where r.links.size >= (select avg(r.links.size)*1.1 from Rendezvous r)")
	Collection<Rendezvous> announcementsWithLinksAboveAverageRendezvous();

	//The average and the standard deviation of the number of questions per rendezvous.
	@Query("select avg(r.questions.size), stddev(r.questions.size) from Rendezvous r")
	Double[] avgStddevQuestionsPerRendezvous();

	//The average, the minimum, the maximum, and the standard deviation of
	//services requested per rendezvous.
	@Query("select avg(r.requests.size), min(r.requests.size), max(r.requests.size), stddev(r.requests.size) from Rendezvous r")
	Double[] avgMinMaxStddevRequestsPerRendezvous();

	//The average number of categories per rendezvous.
	@Query("select (select count(c) from Rendezvous r join r.requests x join x.service s join s.category c)*1.0/ count(r) from Rendezvous r")
	Double avgCategoriesPerRendezvous();
}
