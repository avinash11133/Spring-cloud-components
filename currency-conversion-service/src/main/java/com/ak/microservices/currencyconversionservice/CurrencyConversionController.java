package com.ak.microservices.currencyconversionservice;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/currency-converter")
@Api(value = "Currency Exchange Resource", description = "Gives us exchange value")
public class CurrencyConversionController {
	
	@Autowired
	public CurrencyExchangeServiceProxy currencyExchangeServiceProxy;

	@GetMapping("/from/{from}/to/{to}/quantity/{quantity}")
	@HystrixCommand(fallbackMethod="fallBackConvertCurrency")
	@ApiOperation(value = "Returns the conversion amount")
	public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity)
	{
		//CurrencyConversionBean response= currencyExchangeServiceProxy.retrieveExchangeValue(from, to);
		CurrencyConversionBean response= currencyExchangeServiceProxy.retrieveExchangeValueThroughSpringCloudGateway(from, to);
		return new CurrencyConversionBean(response.getId(),from,to,response.getConversionMultiple(),quantity,quantity.multiply(response.getConversionMultiple()),response.getPort());
	}
	
	public CurrencyConversionBean fallBackConvertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity)
	{
		return new CurrencyConversionBean(444,from,to,BigDecimal.valueOf(65),quantity,quantity.multiply(BigDecimal.valueOf(65)),8100);
	}
}
