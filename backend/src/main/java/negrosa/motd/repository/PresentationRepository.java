package negrosa.motd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import negrosa.motd.entity.Presentation;

public interface PresentationRepository extends JpaRepository<Presentation, Long> {
	
	List<Presentation> findAllByActiveTrue();
	
	Optional<Presentation> findFirstByActiveTrue();
	
}