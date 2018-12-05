package com.ak.microservices.currencyexchangeservice;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/currency-exchange")
@Api(value = "Currency Exchange Resource", description = "Gives us exchange value")
public class CurrencyExchangeController {

	
	@Autowired
	private Environment environment;
	
	@Autowired
	private Configuration configuration;
	
	@ApiOperation(value = "Returns Configuration Property Value")
	@GetMapping("/number")
	public CurrencyExchangeConfiguration retrieveNumberFromConfigurations()
	{
		return new CurrencyExchangeConfiguration(configuration.getNumber());
	}
	
	@ApiOperation(value = "Returns Exchange Value")
	@GetMapping("/from/{from}/to/{to}")
	public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to)
	{
		ExchangeValue exchangeValue = new ExchangeValue(123,"USD","INR",BigDecimal.valueOf(65));
		exchangeValue.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
		return exchangeValue;
	}
	
}
