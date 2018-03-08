
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

	//The average and the standard deviation of replies per comment.
	@Query("select avg(c.replies.size), stddev(c.replies.size) from Comment c")
	Double[] avgStddevRepliesPerComment();
}
