package com.microservices.currencyconversionservice;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservices.currencyconversionservice.bean.CurrencyConversionBean;
/**
 * 
 * Harding URI
 * If there are multiple uri for same service
 * @author ashokkumar.k
 *
 */
//@FeignClient(name = "currency-exchange-service", url = "localhost:8000")
@FeignClient(name = "currency-exchange-service")
/**
 * In the above feign we have removed url because if there are multiple instances it is not possible to declare and
 * Ribbon is used to distribute load among multiple instances we created instnaces for 
 * Currency-Exachange-Service(8000 and 8001)
 * what ever feign calls
 * Below annotation RibbonClient will dynamically distribute load
 * Instances are configured in application properties file as
 * currency-exchange-service.ribbon.listOfServers=http://localhost:8000, http://localhost:8001
 * @author ashokkumar.k
 *
 */
@RibbonClient(name = "currency-exchange-service")
public interface CurrencyExchangeServiceProxy {
	
	@GetMapping("/currency-exchange-jpa/from/{from}/to/{to}")
	public CurrencyConversionBean retriveExchangeValuefromH2(@PathVariable("from") String from, 
			@PathVariable("to") String to);

}
