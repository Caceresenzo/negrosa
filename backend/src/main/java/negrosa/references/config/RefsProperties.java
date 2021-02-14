package negrosa.references.config;

import java.io.FilenameFilter;

import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties("references")
public class RefsProperties {
	
	private String extension;
	private Storage storage = new Storage();
	
	@Data
	public static class Storage {
		
		private ImagesStorage images = new ImagesStorage();
		private TemporaryStorage temporary = new TemporaryStorage();

		@Data
		public static class ImagesStorage {
			
			private String path;
			private String extension;
			
			public FilenameFilter extensionFilter() {
				return (dir, name) -> extension.equalsIgnoreCase(FilenameUtils.getExtension(name));
			}
			
		}
		
		@Data
		public static class TemporaryStorage {
			
			private String path;
			private String prefix;
			
		}
		
	}
	
}