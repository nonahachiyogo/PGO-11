public abstract class Equipment implements Displayable {
    private String id;
    private String name;
    private double baseDailyPrice;
    private boolean available;

    public Equipment(String id, String name, double baseDailyPrice) {
        this.id = id;
        this.name = name;
        this.baseDailyPrice = baseDailyPrice;
        this.available = true;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getBaseDailyPrice() { return baseDailyPrice; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public abstract double calculateDailyPrice();
    public abstract String getDetails();

    @Override
    public String getDisplayText() {
        return String.format("[%s] %s (%s) - Daily Price: %.2f PLN | Available: %s | %s",
                id, name, this.getClass().getSimpleName(), calculateDailyPrice(),
                available ? "YES" : "NO", getDetails());
    }
}