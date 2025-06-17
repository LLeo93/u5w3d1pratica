package it.epicode.u5w3d1pratica.repository;

import it.epicode.u5w3d1pratica.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepository extends JpaRepository<BlogPost, Integer> {
}
