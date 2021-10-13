package negrosa.motd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import negrosa.motd.entity.Presentation;

public interface PresentationRepository extends JpaRepository<Presentation, Long> {
	
}