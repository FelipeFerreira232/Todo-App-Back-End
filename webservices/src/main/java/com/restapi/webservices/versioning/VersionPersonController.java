package com.restapi.webservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionPersonController {
	
	@GetMapping("v1/person")
	public PersonV1 getV1Person() {
		return new PersonV1("Felipe");
	}
	
	@GetMapping("v2/person")
	public PersonV2 getV2Person() {
		return new PersonV2(new Name("Felipe", "Spring Dev"));
	}
	
	@GetMapping(path = "/person", params = "version=1")
	public PersonV1 getPersonV1RequestParam() {
		return new PersonV1("Person: Request Parameter");
	}
	
	@GetMapping(path = "/person", params = "version=2")
	public PersonV2 getPersonV2RequestParam() {
		return new PersonV2(new Name("Felipe", "React Dev"));
	}
	
	@GetMapping(path="/person/header", headers="X-API-VERSION=1")
	public PersonV1 getPersonV1Header () {
		return new PersonV1("Person: Header parameter");
	}
	
	@GetMapping(path="/person/header", headers="X-API-VERSION=2")
	public PersonV2 getPersonV2Header () {
		return new PersonV2(new Name("PersonV2", "Header parameter"));
	}
	
	@GetMapping(path="/person/accept", produces="application/vnd.company.app-v1+json")
	public PersonV1 getPersonV1Accept() {
		return new PersonV1("Person from accept.");
	}
	
	@GetMapping(path="/person/accept", produces="application/vnd.company.app-v2+json")
	public PersonV2 getPersonV2Accept() {
		return new PersonV2(new Name("PersonV2", "from accept"));
	}
}
