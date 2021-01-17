package caceresenzo.apps.negrosa.refs;

import com.typesafe.config.ConfigFactory;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Config {
	
	/* Variables */
	private final com.typesafe.config.Config config;
	
	/** @return Where all the file should be stored. */
	public String getStoragePath() {
		return config.getString("storage.path");
	}
	
	/** @return An config instance. */
	public static Config load() {
		return new Config(ConfigFactory.load());
	}
	
}