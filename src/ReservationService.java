import java.util.ArrayList;
import java.util.List;

public class ReservationService {
    private final List<Student> students = new ArrayList<>();
    private final List<Equipment> equipmentList = new ArrayList<>();
    private final List<Reservation> reservations = new ArrayList<>();
    private final DiscountPolicy discountPolicy;
    private int reservationCounter = 1;

    public ReservationService(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public void addStudent(Student s) { students.add(s); }
    public void addEquipment(Equipment e) { equipmentList.add(e); }

    public List<Student> getStudents() { return students; }
    public List<Equipment> getEquipmentList() { return equipmentList; }
    public List<Reservation> getReservations() { return reservations; }

    public void createReservation(String studentId, String equipmentId, int days) {
        Student student = students.stream().filter(s -> s.getId().equalsIgnoreCase(studentId)).findFirst().orElse(null);
        Equipment equipment = equipmentList.stream().filter(e -> e.getId().equalsIgnoreCase(equipmentId)).findFirst().orElse(null);

        if (student == null) {
            System.out.println("Error: Student not found.");
            return;
        }
        if (equipment == null) {
            System.out.println("Error: Equipment not found.");
            return;
        }
        if (!equipment.isAvailable()) {
            System.out.println("Error: Equipment " + equipmentId + " is not available.");
            return;
        }
        if (days < 1 || days > 14) {
            System.out.println("Error: Rental period must be between 1 and 14 days.");
            return;
        }

        String resId = String.format("R%03d", reservationCounter++);
        Reservation reservation = new Reservation(resId, student, equipment, days, discountPolicy);

        equipment.setAvailable(false);
        reservations.add(reservation);

        System.out.println("\n Reservation " + resId + " created successfully!");
        System.out.println("Equipment: " + equipment.getName());
        System.out.println("Cost: " + String.format("%.2f", reservation.getFinalCost()) + " PLN");
        System.out.println("Status: " + reservation.getStatus());
    }

    public void returnEquipment(String reservationId) {
        Reservation res = reservations.stream().filter(r -> r.getId().equalsIgnoreCase(reservationId)).findFirst().orElse(null);

        if (res == null) {
            System.out.println(" Error: Reservation not found.");
            return;
        }
        if (res.getStatus() != ReservationStatus.ACTIVE) {
            System.out.println(" Error: This reservation is already " + res.getStatus() + ".");
            return;
        }

        res.setStatus(ReservationStatus.RETURNED);
        res.getEquipment().setAvailable(true);

        int pointsEarned = (int) (res.getFinalCost() / 10);
        res.getStudent().addLoyaltyPoints(pointsEarned);

        System.out.println("\n Equipment returned. The student received " + pointsEarned + " loyalty points.");
    }

    public void printReport() {
        System.out.println("\n=== COMPLETED RESERVATIONS ===");
        double totalRevenue = 0;
        for (Reservation r : reservations) {
            if (r.getStatus() == ReservationStatus.RETURNED) {
                System.out.println(r.getDisplayText());
                totalRevenue += r.getFinalCost();
            }
        }
        System.out.format("Total Revenue: %.2f PLN\n", totalRevenue);

        System.out.println("\n=== TOP STUDENT ===");
        Student topStudent = students.stream()
                .max((s1, s2) -> Integer.compare(s1.getLoyaltyPoints(), s2.getLoyaltyPoints()))
                .orElse(null);

        if (topStudent != null) {
            System.out.println(topStudent.getFullName() + " with " + topStudent.getLoyaltyPoints() + " points.");
        }
    }
}