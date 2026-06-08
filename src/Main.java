import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ReservationService service = new ReservationService(new LoyaltyDiscountPolicy());

        service.addStudent(new Student("S001", "Anna Kowalska", "12c", 120));
        service.addStudent(new Student("S002", "Marek Nowak", "12c", 40));
        service.addStudent(new Student("S003", "Julia Zielinska", "13a", 0));

        service.addEquipment(new LaptopSet("E001", "Lenovo ThinkPad Lab", 80, 32, true));
        service.addEquipment(new LaptopSet("E002", "Dell XPS Demo", 100, 16, false));
        service.addEquipment(new CameraKit("E003", "Sony Content Kit", 90, 3, true));
        service.addEquipment(new CameraKit("E004", "Canon Interview Kit", 70, 1, true));

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- MediaLab Reservation System ---");
            System.out.println("1. Display students");
            System.out.println("2. Display equipment");
            System.out.println("3. Create reservation");
            System.out.println("4. Return equipment");
            System.out.println("5. Show active reservations");
            System.out.println("6. Show report");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("\n--- Students ---");
                    for (Student s : service.getStudents()) {
                        System.out.println(s.getId() + ": " + s.getFullName() + " | Points: " + s.getLoyaltyPoints());
                    }
                    break;
                case "2":
                    System.out.println("\n--- Equipment List ---");
                    for (Equipment e : service.getEquipmentList()) {
                        System.out.println(e.getDisplayText());
                    }
                    break;
                case "3":
                    System.out.print("Enter student id: ");
                    String sId = scanner.nextLine();
                    System.out.print("Enter equipment id: ");
                    String eId = scanner.nextLine();
                    System.out.print("Enter number of days: ");
                    try {
                        int days = Integer.parseInt(scanner.nextLine());
                        service.createReservation(sId, eId, days);
                    } catch (NumberFormatException ex) {
                        System.out.println(" Error: Days must be a valid integer number.");
                    }
                    break;
                case "4":
                    System.out.print("Enter reservation id: ");
                    String rId = scanner.nextLine();
                    service.returnEquipment(rId);
                    break;
                case "5":
                    System.out.println("\n--- Active Reservations ---");
                    service.getReservations().stream()
                            .filter(r -> r.getStatus() == ReservationStatus.ACTIVE)
                            .forEach(r -> System.out.println(r.getDisplayText()));
                    break;
                case "6":
                    service.printReport();
                    break;
                case "0":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println(" Invalid choice. Try again.");
            }
        }
    }
}