package com.anushan.nearestproperties;
import javax.persistence.*;

//This class represents rental properties found in database
@Entity
@Table(name = "dallas_rent")
public class RentalProperty {

    @Id
    private Integer mls;
    private Integer rent;

    public Integer getMls() {
        return mls;
    }

    public Integer getRent() {
        return rent;
    }

    public void setMls(Integer mls) {
        this.mls = mls;
    }

    public void setRent(Integer rent) {
        this.rent = rent;
    }

    @Override
    public String toString() {
        return "RentalProperty{" +
                "mls=" + mls +
                ", rent=" + rent +
                '}';
    }
}
