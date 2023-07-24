package com.example.service;

import com.example.domain.*;
import com.example.exception.*;
import com.example.carandownerreceiver.CarAndOwnerReceiverFacade;
import com.example.domain.enums.FranchiseOrUnlimited;
import com.example.repository.InsuranceOwnerRepository;
import com.example.repository.InsuranceCarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InsuranceCarService {
    private final InsuranceCarRepository repo;
    private final CarAndOwnerReceiverFacade facade;

    private final InsuranceOwnerRepository insuranceOwnerRepository;

    @Autowired
    public InsuranceCarService(InsuranceCarRepository repo, CarAndOwnerReceiverFacade facade, InsuranceOwnerRepository insuranceOwnerRepository) {
        this.repo = repo;
        this.facade = facade;
        this.insuranceOwnerRepository = insuranceOwnerRepository;
    }

    public Iterable<InsuranceCar> getAll(final int page, final int pageSize) {
        log.info("getting all cars");
        Pageable pageable = PageRequest.of(page, pageSize);
        return repo.findAll(pageable).stream().collect(Collectors.toList());
    }

    public InsuranceCar getById(final long id) {
        log.info("getting car by id");
        return repo.findById(id).orElseThrow(() -> new InsuranceCarNotFoundException(String.format("InsuranceCar with id: %d not found!", id)));
    }



    public InsuranceCar create(final String licensePlate, final String personalNo, final Insurance insurance) throws URISyntaxException {
        log.info("creating car");
        Car curr = facade.getCar(personalNo, licensePlate);
        if (curr == null) {
            throw new CarNotFoundException(String.format("car with license plate %s in ownership of person with %s personalNo doesno't exist.", licensePlate, personalNo));
        }
        InsuranceCar car = mapCar(curr);
        car.setInsurance(insurance);
        if (repo.existsInsuranceCarByLicensePlate(licensePlate)) {
            log.debug("car with license plate:{} already exists",licensePlate);
            throw new LicensePlateAlreadyExistsException(String.format("InsuranceCar with license plate: %s already exists!", car.getLicensePlate()));
        }
        if (getYearsBetween(car.getReleaseDate(), LocalDate.now()) > 25) {
            log.info("car is overaged");
            throw new InsuranceCarOveragedForInsuranceException("InsuranceCar with age overaged!");
        }
        BigDecimal pension = calculatePension(car);
        car.getInsurance().setInsurancePension(pension);
        return repo.save(car);
    }

    private InsuranceCar mapCar(Car car) {
        InsuranceCar resCar = new InsuranceCar();
        resCar.setCarType(car.getCarType());
        resCar.setOdometer(car.getOdometer());
        resCar.setLicensePlate(car.getLicensePlate());
        InsuranceOwner owner;
        if (!insuranceOwnerRepository.existsInsuranceOwnerByPersonalNo(car.getOwner().getPersonalNo())) {
            owner = mapOwner(car.getOwner());
            insuranceOwnerRepository.save(owner);
        }
        owner = insuranceOwnerRepository.getInsuranceOwnerByPersonalNo(car.getOwner().getPersonalNo());
        resCar.setOwner(owner);
        resCar.setOdometerUnitType(car.getOdometerUnitType());
        resCar.setReleaseDate(car.getReleaseDate());
        resCar.setVinCode(car.getVinCode());
        return resCar;
    }

    private InsuranceOwner mapOwner(Owner owner) {
        InsuranceOwner resOwner = new InsuranceOwner();
        resOwner.setBirthDate(owner.getBirthDate());
        resOwner.setFullName(owner.getFullName());
        resOwner.setPersonalNo(owner.getPersonalNo());
        return resOwner;
    }

    private BigDecimal calculatePension(InsuranceCar car) {
        int percentage = 3;
        int carAge = getYearsBetween(car.getReleaseDate(), LocalDate.now());
        if (carAge >= 10 && carAge <= 25) {
            percentage++;
        }
        int ownerAge = getYearsBetween(car.getOwner().getBirthDate(), LocalDate.now());
        if (ownerAge >= 18 && ownerAge <= 24) {
            percentage++;
        }
        if (car.getInsurance().getFranchiseOrUnlimited() == FranchiseOrUnlimited.UNLIMITED) {
            percentage++;
            return car.getInsurance().getInsuranceAmount().multiply(new BigDecimal(percentage)).divide(new BigDecimal(100));
        }
        if (car.getInsurance().getInsuranceAmount().multiply(new BigDecimal(1)).divide(new BigDecimal(100)).compareTo(car.getInsurance().getFranchisedAmount()) < 0) {
            throw new FranchiseExceededException("Franchised amount is exceeded!");
        }
        return car.getInsurance().getInsuranceAmount().multiply(new BigDecimal(percentage)).divide(new BigDecimal(100))
                .subtract(car.getInsurance().getFranchisedAmount().multiply(new BigDecimal(10)).divide(new BigDecimal(100)));
    }

    private int getYearsBetween(LocalDate starDate, LocalDate endDate) {
        return starDate.until(endDate).getYears();
    }


    public InsuranceCar remove(final long id) {
        log.info("removing car");
        if (repo.existsById(id)) {
            InsuranceCar car = repo.findById(id).get();
            repo.deleteById(id);
            return car;
        }
        throw new InsuranceCarNotFoundException(String.format("InsuranceCar with id: %d not found!", id));
    }
}
