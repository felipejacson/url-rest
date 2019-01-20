package com.urlrest.controller;

import com.urlrest.model.Url;
import com.urlrest.service.UrlService;
import com.urlrest.util.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UrlRestController {

	@Autowired
	private UrlService urlService;

	@GetMapping(path = "/api/{id}")
	public ResponseEntity<CustomResponse> getUrlById(@PathVariable Long id) {
		Optional<Url> url = urlService.findById(id);
		return new ResponseEntity(new CustomResponse(HttpStatus.OK, url.get()), HttpStatus.OK);
	}

	@GetMapping(path = "/api/n/{name}")
	public ResponseEntity<CustomResponse> findByFriendlyName(@PathVariable String name) {
		try {
			Long.parseLong(name);
			return new ResponseEntity(new CustomResponse(HttpStatus.BAD_REQUEST, "The friendly name must be a string!"), HttpStatus.BAD_REQUEST);
		} catch (NumberFormatException e) {
			return new ResponseEntity(new CustomResponse(HttpStatus.OK, urlService.findByFriendlyName(name)), HttpStatus.OK);
		}
	}

	@PostMapping(path = "/api/find")
	public ResponseEntity<CustomResponse> findByAttribute(@RequestBody Url url) {
		return new ResponseEntity(new CustomResponse(HttpStatus.OK, urlService.findByAttribute(url)), HttpStatus.OK);
	}

	@GetMapping(path = "/api/")
	public ResponseEntity<CustomResponse> getAllUrl() {
		return new ResponseEntity(new CustomResponse(HttpStatus.OK, urlService.findAll()), HttpStatus.OK);
	}

	@PostMapping(path = "/api/")
	public ResponseEntity<CustomResponse> postUrl(@RequestBody Url url) {
		url.setId(null);

		if(url.getFriendlyName() != null && !url.getFriendlyName().equals("")) {
			url.setFriendlyName(url.getFriendlyName().trim());

			try {
				Long.parseLong(url.getFriendlyName());
				return new ResponseEntity(new CustomResponse(HttpStatus.BAD_REQUEST, "The friendly name must be a string!"), HttpStatus.BAD_REQUEST);
			} catch (NumberFormatException e) { ; }
		}

		urlService.save(url);
		return new ResponseEntity(new CustomResponse(HttpStatus.CREATED, url), HttpStatus.CREATED);
	}

	@PutMapping(path = "/api/{id}")
	public ResponseEntity<CustomResponse> putUrl(@RequestBody Url url, @PathVariable Long id) {
		url.setId(id);
		Url urlTmp = null;

		if(url.getFriendlyName() != null && !url.getFriendlyName().equals("")) {
			url.setFriendlyName(url.getFriendlyName().trim());

			try {
				Long.parseLong(url.getFriendlyName());
				return new ResponseEntity(new CustomResponse(HttpStatus.BAD_REQUEST, "The friendly name must be a string!"), HttpStatus.BAD_REQUEST);
			} catch (NumberFormatException e) {
				urlTmp = urlService.findByFriendlyName(url.getFriendlyName());
			}
		}

		if(urlTmp == null || (urlTmp.getId().equals(url.getId()))) {
			urlService.save(url, id);
			return new ResponseEntity(new CustomResponse(HttpStatus.OK, url), HttpStatus.OK);
		} else {
			return new ResponseEntity(new CustomResponse(HttpStatus.BAD_REQUEST, "This friendly name alredy exists!"), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(path = "/api/{id}")
	public ResponseEntity<CustomResponse> deleteUrl(@PathVariable Long id) {
		Optional<Url> url = urlService.findById(id);
		if(url.isPresent()) {
			urlService.delete(id);
			return new ResponseEntity(new CustomResponse(HttpStatus.OK, url.get()), HttpStatus.OK);
		} else {
			return new ResponseEntity(new CustomResponse(HttpStatus.BAD_REQUEST, "This URL cannot be deleted!"), HttpStatus.BAD_REQUEST);
		}
	}

}
