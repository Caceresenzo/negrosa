package caceresenzo.apps.negrosa.refs;

import static spark.Spark.after;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.jetty.http.HttpStatus;

import caceresenzo.apps.negrosa.refs.model.ConversionResult;
import caceresenzo.apps.negrosa.refs.util.Utils;
import spark.Filter;

public class Bootstrap {
	
	public static final String API_REFS = "/api/refs";
	public static final String API_REFS_FILE = "/api/refs/:ref";
	
	public static List<String> listRefs(Config config) {
		return Arrays
				.asList(new File(config.getStoragePath()).listFiles((name) -> name.getName().endsWith(".png")))
				.stream()
				.map(File::getName)
				.map(FilenameUtils::getBaseName)
				.collect(Collectors.toList());
	}
	
	public static void main(String[] args) throws IOException {
		Config config = Config.load();
		
		{
			File converter = new File("convert.py");
			
			if (!converter.exists()) {
				FileUtils.copyInputStreamToFile(Bootstrap.class.getResourceAsStream("/convert.py"), converter);
			}
			
			File storage = new File(config.getStoragePath());
			
			if (storage.exists() && storage.isFile()) {
				storage.delete();
			}
			
			if (!storage.exists()) {
				storage.mkdirs();
			}
		}
		
		port(80);
		staticFileLocation("web");
		
		after((Filter) (request, response) -> {
			response.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
			response.header("Access-Control-Allow-Origin", "*");
			response.header("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
			response.header("Access-Control-Allow-Credentials", "true");
		});
		
		get("/api/config", (request, response) -> {
			response.status(200);
			response.type("application/json");
			
			return Utils.dataToJson(config);
		});
		
		get(API_REFS, (request, response) -> {
			response.status(200);
			response.type("application/json");
			
			return Utils.dataToJson(listRefs(config));
		});
		
		options(API_REFS, (request, response) -> "{}");
		
		post(API_REFS, (request, response) -> {
			request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
			
			Part filePart = request.raw().getPart("file");
			String originalFileName = filePart.getSubmittedFileName();
			
			ConversionResult result = new ConversionResult().setFile(originalFileName);
			
			if (!FilenameUtils.getExtension(originalFileName).equalsIgnoreCase("dxf")) {
				response.status(HttpStatus.NOT_ACCEPTABLE_406);
				response.type("application/json");
				
				return Utils.dataToJson(result.setError("not a dxf file"));
			}
			
			String fileName = FilenameUtils.getBaseName(originalFileName);
			
			Path tempFile = Files.createTempFile(null, null);
			try (InputStream input = filePart.getInputStream()) {
				Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
			}
			
			Process process = new ProcessBuilder("python3", "convert.py", tempFile.toAbsolutePath().toString(), Paths.get(config.getStoragePath(), fileName).toString()).start();
			int exitCode = process.waitFor();
			
			response.type("application/json");
			
			if (exitCode == 0) {
				response.status(200);
				result.setSuccess(true);
			} else {
				response.status(500);
				result.setError("conversion failed");
			}
			
			return (Utils.dataToJson(result));
		});
		
		get(API_REFS_FILE, (request, response) -> {
			String ref = request.params(":ref");
			
			try {
				byte[] data = Files.readAllBytes(Path.of(config.getStoragePath(), ref + ".png"));
				
				response.status(200);
				response.type("image/png");
				
				return (data);
			} catch (Exception exception) {
				response.status(404);
				
				return exception.getMessage();
			}
		});
		
		delete(API_REFS_FILE, (request, response) -> {
			String ref = request.params(":ref");
			
			response.type("application/json");
			
			File file = new File(config.getStoragePath(), ref + ".png");
			if (file.exists()) {
				file.delete();
				response.status(200);
			} else {
				response.status(404);
			}
			
			return "{}";
		});
	}
	
}