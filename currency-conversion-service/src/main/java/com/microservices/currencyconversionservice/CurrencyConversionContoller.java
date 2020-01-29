package com.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.microservices.currencyconversionservice.bean.CurrencyConversionBean;

@RestController
public class CurrencyConversionContoller {
	@Autowired
	private Environment env;
	
	@Autowired
	private CurrencyExchangeServiceProxy feignProxy;
	
	@GetMapping("currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from, 
			@PathVariable String to, @PathVariable BigDecimal quantity) {
		CurrencyConversionBean bean = new CurrencyConversionBean(1L, from, to, BigDecimal.ONE, quantity, quantity);
		bean.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		return bean;
	}
	
	@GetMapping("currency-converter-exchange/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyfromExchangeService(@PathVariable String from, 
			@PathVariable String to, @PathVariable BigDecimal quantity) {
		
		Map<String,String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		 ResponseEntity<CurrencyConversionBean> restponseEntity= new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
				CurrencyConversionBean.class,
				uriVariables);
		
		CurrencyConversionBean response = restponseEntity.getBody();
		System.out.println(response.toString());
		
		CurrencyConversionBean bean1 =  new CurrencyConversionBean(response.getId(), from, to, 
				response.getConversionMultiple(), quantity, quantity.multiply(response.getConversionMultiple()));
		bean1.setPort(response.getPort());
		
		return bean1;
	}
	
	@GetMapping("currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyfromExchangeServiceUsingFeign(@PathVariable String from, 
			@PathVariable String to, @PathVariable BigDecimal quantity) {
		
				
		CurrencyConversionBean response = feignProxy.retriveExchangeValuefromH2(from, to);
		System.out.println(response.toString());
		
		CurrencyConversionBean bean1 =  new CurrencyConversionBean(response.getId(), from, to, 
				response.getConversionMultiple(), quantity, quantity.multiply(response.getConversionMultiple()));
		bean1.setPort(response.getPort());
		
		return bean1;
	}

}
