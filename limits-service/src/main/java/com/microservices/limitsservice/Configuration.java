package com.microservices.limitsservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
//@ConfigurationProperties(prefix = "limits-service") //1
//@ConfigurationProperties("limits-service") //2
public class Configuration {
	
	@Value("${limits-service.minimum}") //3
	private int minimum;
	@Value("${limits-service.maximum}")
	private int maximum;
	public int getMinimum() {
		return minimum;
	}
	public void setMinimum(int minimum) {
		this.minimum = minimum;
	}
	public int getMaximum() {
		return maximum;
	}
	public void setMaximum(int maximum) {
		this.maximum = maximum;
	}
	
	

}
