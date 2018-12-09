package com.akw.springboot.microservice.example.currencyconversion.springbootmicroservicecurrencyconversion;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CurrencyExchangeServiceProxy proxy;

	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);

		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,
				uriVariables);

		CurrencyConversionBean response = responseEntity.getBody();

		System.out.println("the output of the response.getId at line 36 is :" + response.getId());
		System.out.println("the output of the response.from at line 36 is :" + from);
		System.out.println("the output of the response.to at line 36 is :" + to);
		System.out.println(
				"the output of the response.getConversionMultiple at line 36 is :" + response.getConversionMultiple());
		System.out.println("the output of the quantity is :" + quantity);
		System.out.println("the output of the rquantity quantity mutltiply at line 36 is :"
				+ quantity.multiply(response.getConversionMultiple()));
		System.out.println("the output of the response.getPort is :" + response.getPort());

		CurrencyConversionBean currencyConversion = new CurrencyConversionBean(response.getId(), from, to,
				response.getConversionMultiple(), quantity, quantity.multiply(response.getConversionMultiple()),
				response.getPort());

		// System.out.println("the output at line 40 is :"+response);

		return currencyConversion;

	}

	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);

		logger.info("{}", response);

		return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
				quantity.multiply(response.getConversionMultiple()), response.getPort());

	}

}
