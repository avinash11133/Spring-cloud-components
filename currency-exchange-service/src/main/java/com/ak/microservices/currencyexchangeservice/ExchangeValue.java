package com.ak.microservices.currencyexchangeservice;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

public class ExchangeValue {
	
	
	private long id;
	private String from;
	private String to;
	private BigDecimal conversionMultiple;
	private int port;
	
	public ExchangeValue() {
		
	}

	public ExchangeValue(long id, String from, String to, BigDecimal conversionMultiple) {
		super();
		this.id = id;
		this.from = from;
		this.to = to;
		this.conversionMultiple = conversionMultiple;
	}

	public long getId() {
		return id;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public BigDecimal getConversionMultiple() {
		return conversionMultiple;
	}

	public int getPort() {
		return port;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setConversionMultiple(BigDecimal conversionMultiple) {
		this.conversionMultiple = conversionMultiple;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
}
