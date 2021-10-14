package negrosa.motd.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "slides")
@Data
@Accessors(chain = true)
public class Slide {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, optional = false)
	@JsonView(Fields.Presentation.class)
	private Presentation presentation;

	@Column(nullable = false)
	private long position;
	
	@Column
	private Double duration;
	
	@Column(length = 1024, nullable = false)
	private String file;
	
	public void setDuration(Double duration) {
		if (duration == 0.0d) {
			duration = null;
		}
		
		this.duration = duration;
	}
	
	public static interface Fields {
		
		public static interface Presentation {
		}
		
	}
	
}