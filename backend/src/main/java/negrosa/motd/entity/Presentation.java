package negrosa.motd.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "presentations")
@Data
@Accessors(chain = true)
public class Presentation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private boolean active;
	
	@Column(nullable = false)
	private double slideDuration = 10.0;
	
	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private LocalDateTime updatedAt;
	
	@OneToMany(mappedBy = "presentation", fetch = FetchType.LAZY, orphanRemoval = true)
	@JsonView(Fields.Slides.class)
	private List<Slide> slides;
	
	public static interface Fields {
		
		public static interface Slides {
		}
		
	}
	
}