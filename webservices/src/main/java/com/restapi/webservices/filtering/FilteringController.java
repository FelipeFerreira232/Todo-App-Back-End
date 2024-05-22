package com.restapi.webservices.filtering;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {

	@GetMapping("/some-bean")
	public MappingJacksonValue getBean() {

		SomeBean someBean = new SomeBean("value1", "value2", "value3");
		MappingJacksonValue mappingJackson = new MappingJacksonValue(someBean);
		SimpleBeanPropertyFilter someFilter = SimpleBeanPropertyFilter.filterOutAllExcept("field2");
		FilterProvider filter = new SimpleFilterProvider().addFilter("SomeBeanFilter", someFilter);
		mappingJackson.setFilters(filter);
		return mappingJackson;
	}

	@GetMapping("/some-bean-list")
	public MappingJacksonValue getBeansList() {
		List<SomeBean> beans = Arrays.asList(new SomeBean("value4", "value5", "value6"), new SomeBean("value1", "value2", "value3"));

		MappingJacksonValue mappingJackson = new MappingJacksonValue(beans);
		SimpleBeanPropertyFilter someFilter = SimpleBeanPropertyFilter.filterOutAllExcept("field2");
		FilterProvider filter = new SimpleFilterProvider().addFilter("SomeBeanFilter", someFilter);
		mappingJackson.setFilters(filter);
		return mappingJackson;
	}
}
