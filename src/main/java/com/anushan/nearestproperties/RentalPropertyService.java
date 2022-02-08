package com.anushan.nearestproperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class RentalPropertyService {

    private RentalPropertyRepository rentalPropertyRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public RentalPropertyService(RentalPropertyRepository rentalPropertyRepository){
        this.rentalPropertyRepository = rentalPropertyRepository;
    }

    //searches database for three nearest properties within 1km
    public List<RentalProperty> getNearestProperties(Float longitude, Float latitude){
        //find 3 nearest houses within 1 km
        String queryStr = "SELECT mls, rent " +
                "FROM dallas_rent " +
                "WHERE ST_DWithin(geometry\\:\\:geography, ST_Point(" + longitude + ", " + latitude + ")\\:\\:geography, " + 1000 + ") " +
                "ORDER BY ST_Distance(geometry\\:\\:geography, ST_Point(" + longitude + ", " + latitude + ")\\:\\:geography) ASC " +
                "LIMIT 3;";

        return em.createNativeQuery(queryStr, RentalProperty.class).getResultList();
    }
}
