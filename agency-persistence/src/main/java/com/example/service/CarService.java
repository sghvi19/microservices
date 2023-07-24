package com.example.service;

import com.example.domain.Car;
import com.example.domain.Owner;
import com.example.domain.Person;
import com.example.exception.*;
import com.example.repository.CarRepository;
import com.example.repository.OwnerRepository;
import com.example.personreceiver.PersonReceiverFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CarService {
    private final CarRepository repo;
    private final PersonReceiverFacade facade;

    private final OwnerRepository ownerRepository;

    @Autowired
    public CarService(CarRepository repo, PersonReceiverFacade facade, OwnerRepository ownerRepository) {
        this.repo = repo;
        this.facade = facade;
        this.ownerRepository = ownerRepository;
    }

    public Iterable<Car> getAll(final int page, final int pageSize) {
        log.info("getting all cars");
        Pageable pageable = PageRequest.of(page, pageSize);
        return repo.findAll(pageable).stream().collect(Collectors.toList());
    }

    public Car getById(final long id) {
        log.info("getting car by id");
        return repo.findById(id).orElseThrow(() -> new CarNotFoundException(String.format("Car with id: %d not found!", id)));
    }

    public Car getCarByPersonalNoAndLicensePlate(String personalNo, String licensePlate) throws URISyntaxException {
        log.info("getting car by personalNo and license plate");
        Person person = facade.getPerson(personalNo);
        if (person == null) {
            throw new PersonNotFoundException(String.format("person with personalNo:%s doesn't exist.", personalNo));
        }
        if(!ownerRepository.existsOwnerByPersonalNo(personalNo)){
            throw new OwnerNotFoundException(String.format("owner with personalNo:%s doesn't exist.", personalNo));
        }
        return repo.getCarByLicensePlateAndOwnerPersonalNo(licensePlate, personalNo)
                .orElseThrow(() -> new CarNotFoundException(String.format("car with licensePlate %s and personalNo %s doesn't exist", licensePlate, personalNo)));
    }


    public Car update(final long id, final Car car) {
        if (!repo.existsById(id)) {
            log.info("car does not exist");
            throw new CarNotFoundException(String.format("Car with id: %d not found!", id));
        }
        if (repo.existsCarByVinCode(car.getVinCode()) && repo.findCarByVinCode(car.getVinCode()).get().getCarId() != id) {
            throw new VinCodeAlreadyExistsException(String.format("Car with vin code: %s already exists!", car.getVinCode()));
        }
        if (repo.existsCarByLicensePlate(car.getLicensePlate()) && repo.findCarByLicensePlate(car.getLicensePlate()).get().getCarId() != id) {
            throw new LicensePlateAlreadyExistsException(String.format("Car with license plate: %s already exists!", car.getLicensePlate()));
        }
        log.info("updating car");
        repo.save(car);
        return car;
    }

    public Car create(final Car car, final String personalNo) throws URISyntaxException {
        log.info("registering car to a person");
        Person person = facade.getPerson(personalNo);
        if (person == null) {
            throw new PersonNotFoundException(String.format("person with personalNo: %s doesn't exist",personalNo));
        }
        if (ownerRepository.existsOwnerByPersonalNo(personalNo)) {
            log.info("registering new car to our old client.");
            Owner owner = ownerRepository.getOwnerByPersonalNo(personalNo);
            if(repo.existsCarByVinCode(car.getVinCode())){
                log.info("registering old car to our old client.");
                Car curr = repo.findCarByVinCode(car.getVinCode()).get();
                curr.setOwner(owner);
                return repo.save(curr);
            }
            car.setOwner(owner);
            return repo.save(car);
        }
        log.info("registering new car to our new client.");
        Owner owner = new Owner();
        mapPerson(owner,person);
        ownerRepository.save(owner);
        if(repo.existsCarByVinCode(car.getVinCode())){
            Car curr = repo.findCarByVinCode(car.getVinCode()).get();
            curr.setOwner(owner);
            log.info("registering old car to our new client.");
            return repo.save(curr);
        }
        car.setOwner(owner);
        return repo.save(car);
    }

    private void mapPerson(Owner owner, Person person) {
        owner.setBirthDate(person.getBirthDate());
        owner.setFullName(person.getFullName());
        owner.setPersonalNo(person.getPersonalNo());
    }

    public Car remove(final long id) {
        log.info("removing car");
        if (repo.existsById(id)) {
            Car car = repo.findById(id).get();
            repo.deleteById(id);
            return car;
        }
        log.info("car with id:{} doesn't exist",id);
        throw new CarNotFoundException(String.format("Car with id: %d not found!", id));
    }
}
