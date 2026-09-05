public class CineScreen {
    private final int seatsTotal;
    private int seatsAvailable;

    public CineScreen(int seatsTotal) {
        if (seatsTotal <= 0) {
            throw new IllegalArgumentException("construction rejected");
        }
        this.seatsTotal = seatsTotal;
        this.seatsAvailable = seatsTotal;
    }

    public void bookSeat() {
        if (seatsAvailable > 0) {
            seatsAvailable--;
        }
    }

    public void cancelBooking() {
        if (seatsAvailable < seatsTotal) {
            seatsAvailable++;
        }
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    // Main method added so VS Code can run the file
    public static void main(String[] args) {
        System.out.println("--- Testing CineScreen ---");

        // Example 1: Constructor rejection
        try {
            new CineScreen(0);
        } catch (IllegalArgumentException e) {
            System.out.println("Input: new CineScreen(0) -> Output: " + e.getMessage());
        }

        // Example 2: Booking boundary check
        CineScreen c1 = new CineScreen(2);
        c1.bookSeat();
        c1.bookSeat();
        c1.bookSeat(); // Rejection on 3rd booking
        System.out.println("Available after 3 bookings: " + c1.getSeatsAvailable()); // Expected: 0

        // Example 3: Cancellation boundary check
        c1.cancelBooking();
        c1.cancelBooking();
        c1.cancelBooking(); // Rejection on 3rd cancellation
        System.out.println("Available after 3 cancellations: " + c1.getSeatsAvailable()); // Expected: 2
    }
}