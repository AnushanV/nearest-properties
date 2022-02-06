public class RentalProperty {

    private Integer mls;
    private Integer rent;

    public RentalProperty(Integer mls, Integer rent) {
        this.mls = mls;
        this.rent = rent;
    }

    public Integer getMls() {
        return mls;
    }

    public Integer getRent() {
        return rent;
    }

    @Override
    public String toString() {
        return "RentalProperty{" +
                "mls=" + mls +
                ", rent=" + rent +
                '}';
    }
}
