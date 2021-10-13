package negrosa.motd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import negrosa.motd.entity.Slide;

public interface SlideRepository extends JpaRepository<Slide, Long> {
	
}