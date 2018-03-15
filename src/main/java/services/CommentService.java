
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CommentRepository;
import domain.Comment;
import domain.User;

@Service
@Transactional
public class CommentService {

	//Managed repository

	@Autowired
	private CommentRepository	commentRepository;

	//Supporting services --------------------------------

	@Autowired
	private ActorService		actorService;


	public Comment create() {
		final Comment c = new Comment();
		final User u = (User) this.actorService.findByPrincipal();
		c.setUser(u);
		c.setMoment(new Date(System.currentTimeMillis() - 1));
		c.setReplies(new ArrayList<Comment>());
		return c;
	}

	public Comment findOne(final int id) {
		Assert.notNull(id);
		return this.commentRepository.findOne(id);
	}

	public Collection<Comment> findAll() {
		return this.commentRepository.findAll();
	}

	public Comment save(final Comment c) {
		Assert.notNull(c);

		Assert.isTrue(this.actorService.findByPrincipal().getId() == c.getUser().getId());

		final Comment saved = this.commentRepository.save(c);
		return saved;
	}

	public void delete(final Comment c) {
		Assert.notNull(c);
		this.commentRepository.delete(c);

	}
	public Double[] avgStddevRepliesPerComment() {
		return this.commentRepository.avgStddevRepliesPerComment();
	}
}
