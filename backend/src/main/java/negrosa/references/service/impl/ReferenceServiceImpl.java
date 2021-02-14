package negrosa.references.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import negrosa.references.config.RefsProperties;
import negrosa.references.exception.ConversionFailedException;
import negrosa.references.exception.EmptyFileException;
import negrosa.references.exception.FileExtensionNotAcceptableException;
import negrosa.references.exception.ReferenceFoundException;
import negrosa.references.exception.ReferenceNotFoundException;
import negrosa.references.service.ReferenceService;

@Service
public class ReferenceServiceImpl implements ReferenceService {
	
	@Autowired
	private RefsProperties config;
	
	/* Variables */
	private Path imageStoragePath;
	private Path temporaryStoragePath;
	
	@PostConstruct
	private void initialize() throws IOException {
		imageStoragePath = Paths.get(config.getStorage().getImages().getPath());
		temporaryStoragePath = Paths.get(config.getStorage().getTemporary().getPath());
		
		for (Path path : Arrays.asList(imageStoragePath, temporaryStoragePath)) {
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}
		}
	}
	
	@Override
	public List<String> all() {
		return Arrays.asList(imageStoragePath.toFile().list(config.getStorage().getImages().extensionFilter()))
				.stream().map(FilenameUtils::getBaseName)
				.collect(Collectors.toList());
	}
	
	@Override
	public InputStream find(String name) {
		try {
			return new FileInputStream(ensureExists(name).toFile());
		} catch (FileNotFoundException exception) {
			throw new ReferenceNotFoundException(exception);
		}
	}
	
	@Override
	public String store(MultipartFile file) throws IOException, InterruptedException {
		if (file.isEmpty()) {
			throw new EmptyFileException();
		}
		
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		if (!config.getExtension().equalsIgnoreCase(extension)) {
			throw new FileExtensionNotAcceptableException();
		}
		
		String name = FilenameUtils.getBaseName(file.getOriginalFilename()).toUpperCase();
		Path imagePath = imagePath(name);
		
		if (Files.exists(imagePath)) {
			throw new ReferenceFoundException();
		}
		
		Path temporaryFilePath = temporary();
		
		try (InputStream inputStream = file.getInputStream()) {
			Files.copy(inputStream, temporaryFilePath, StandardCopyOption.REPLACE_EXISTING);
		}
		
		Process process = new ProcessBuilder("python3", "convert.py", temporaryFilePath.toString(), imagePath.toString()).start();
		int exitCode = process.waitFor();
		
		Files.delete(temporaryFilePath);
		
		if (exitCode != 0) {
			throw new ConversionFailedException();
		}
		
		return name;
	}
	
	@Override
	public void delete(String name) throws IOException {
		Files.delete(ensureExists(name));
	}
	
	private Path ensureExists(String name) {
		Path path = imagePath(name);
		
		if (!Files.exists(path)) {
			throw new ReferenceNotFoundException();
		}
		
		return path;
	}
	
	private Path imagePath(String name) {
		String filename = name + "." + config.getStorage().getImages().getExtension();
		
		return imageStoragePath.resolve(filename).toAbsolutePath();
	}
	
	private Path temporary() throws IOException {
		return Files.createTempFile(temporaryStoragePath, config.getStorage().getTemporary().getPrefix(), null).toAbsolutePath();
	}
	
}