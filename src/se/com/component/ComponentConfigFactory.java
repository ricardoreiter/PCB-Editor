package se.com.component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

import com.google.gson.Gson;

import se.com.config.GlobalConfig;

public class ComponentConfigFactory {

	private HashMap<String, ComponentConfig> components = new HashMap<>();
	
	private static ComponentConfigFactory instance = new ComponentConfigFactory();
	
	private ComponentConfigFactory() {
		loadComponents(GlobalConfig.getInstance().getComponentsConfigFolder());
	}
	
	private void loadComponents(String componentsConfigFolder) {
		try(Stream<Path> paths = Files.walk(Paths.get(componentsConfigFolder))) {
		    paths.forEach(filePath -> {
		        if (Files.isRegularFile(filePath) && filePath.toString().endsWith(GlobalConfig.getInstance().getComponentsConfigExtension())) {
		        	ComponentConfig component = loadComponent(filePath);
		            components.put(component.getName(), component);
		        }
		    });
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public static ComponentConfigFactory getInstance() {
		return instance;
	}
	
	public ComponentConfig getComponent(String name) {
		return components.get(name);
	}
	
	public HashMap<String, ComponentConfig> getComponents() {
		return components;
	}
	
	public ComponentConfig loadComponent(Path filePath) {
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(filePath);
			String componentString = new String(encoded, StandardCharsets.UTF_8);
			
			Gson g = new Gson();
			ComponentConfig component = g.fromJson(componentString, ComponentConfig.class);
			
			return component;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
