package com.owt.boat.app.controller;


import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.pojo.ApiStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.owt.boat.app.model.Boat;
import com.owt.boat.app.repo.BoatRepository;


@RestController
@RequestMapping(value = "/boats")
@Api(name = "Boats API",description = "Provides a list of methods that manage boats",stage = ApiStage.RC)
@Validated
@CrossOrigin
public class BoatController {
	private BoatRepository boatRepository;

	@Autowired
	public BoatController(BoatRepository boatRepository){
		this.boatRepository = boatRepository;
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(method = GET)
	@ApiMethod(description = "Get all boats from the database")
	public List<Boat> getAll(){
		return boatRepository.findAll();
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "{id}", method = GET)
	@ApiMethod(description = "Get boat from the database")
	public Boat get(@ApiPathParam(name = "id") @PathVariable("id") @Min(1) long id){
		Boat boat = boatRepository.getOne(id);
		return boat;
	}	

	//TODO - Change HTTP VERBE, it should be a UPDATE VERBE but httpClientModule from angular doesn't send Content-Type Authorisation in header for update 
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "update/{id}", method = POST)
	@ApiMethod(description = "Update boat price")
	public void update(@ApiPathParam(name = "id") @PathVariable("id") @Min(1) long id, @RequestBody Boat updatedBoat) {
		Boat boat = boatRepository.getOne(id);
		boat.setName(updatedBoat.getName());
		boat.setDescription(updatedBoat.getDescription());
		boatRepository.save(boat);
	}

	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(method = POST)
	@ApiMethod(description = "Create a boat and save it to the database")
	public void create(@Valid @RequestBody Boat boat){
		boatRepository.save(boat);

	}	

	//TODO - Change HTTP VERBE, it should be a DELETE VERBE but httpClientModule from angular doesn't send Content-Type Authorisation in header for delete 
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@RequestMapping(value = "delete/{id}", method = GET)
	@ApiMethod(description = "Remove the boat from the database")
	public void remove(@ApiPathParam(name = "id") @PathVariable("id") @Min(1) long id){
		boatRepository.deleteById(id);
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler({EmptyResultDataAccessException.class, EntityNotFoundException.class})
	public String databaseError() {
		return "No boat found for this id";
	}
}
