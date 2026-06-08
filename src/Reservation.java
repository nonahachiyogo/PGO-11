public class Reservation implements Displayable {
    private String id;
    private Student student;
    private Equipment equipment;
    private int days;
    private ReservationStatus status;
    private double finalCost;

    public Reservation(String id, Student student, Equipment equipment, int days, DiscountPolicy discountPolicy) {
        this.id = id;
        this.student = student;
        this.equipment = equipment;
        this.days = days;
        this.status = ReservationStatus.ACTIVE;
        this.finalCost = calculateTotalCost(discountPolicy);
    }

    public String getId() { return id; }
    public Student getStudent() { return student; }
    public Equipment getEquipment() { return equipment; }
    public ReservationStatus getStatus() { return status; }
    public double getFinalCost() { return finalCost; }

    public void setStatus(ReservationStatus status) { this.status = status; }

    public double calculateTotalCost(DiscountPolicy discountPolicy) {
        double baseCost = equipment.calculateDailyPrice() * days;
        return discountPolicy.applyDiscount(student, baseCost);
    }

    @Override
    public String getDisplayText() {
        return String.format("Res ID: %s | Student: %s | Equipment: %s | Days: %d | Cost: %.2f PLN | Status: %s",
                id, student.getFullName(), equipment.getName(), days, finalCost, status);
    }
}