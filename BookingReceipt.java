import java.util.Arrays;

public class BookingReceipt {
    private final String bookingId;
    private final String[] seatNumbers;

    public BookingReceipt(String bookingId, String[] seatNumbers) {
        this.bookingId = bookingId;
        this.seatNumbers = (seatNumbers != null) ? Arrays.copyOf(seatNumbers, seatNumbers.length) : new String[0];
    }

    public String getBookingId() {
        return bookingId;
    }

    public String[] getSeatNumbers() {
        return Arrays.copyOf(seatNumbers, seatNumbers.length);
    }

    public BookingReceipt withUpdatedSeat(int index, String newSeat) {
        if (index < 0 || index >= seatNumbers.length) {
            return this;
        }
        String[] updatedSeats = Arrays.copyOf(seatNumbers, seatNumbers.length);
        updatedSeats[index] = newSeat;
        return new BookingReceipt(this.bookingId, updatedSeats);
    }

    public static String processNightlySettlement(BookingReceipt[] receipts) {
        int processed = 0;
        int nullSkipped = 0;
        int groupCount = 0;
        int individualCount = 0;

        if (receipts != null) {
            for (BookingReceipt receipt : receipts) {
                if (receipt == null) {
                    nullSkipped++;
                } else {
                    processed++;
                    if (receipt instanceof GroupBookingReceipt) {
                        groupCount++;
                    } else {
                        individualCount++;
                    }
                }
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped\n" +
               groupCount + " group | " + individualCount + " individual";
    }

    public static void main(String[] args) {
        System.out.println("--- Testing BookingReceipt ---");

        BookingReceipt b = new BookingReceipt("CH-1001", new String[]{"A1", "A2"});
        
        // Test Defensive Copying
        String[] seats = b.getSeatNumbers();
        seats[0] = "X";
        System.out.println("Original Seat[0] after modifying copy: " + b.getSeatNumbers()[0]); // Outputs "A1"

        // Test Immutable Wither
        BookingReceipt updated = b.withUpdatedSeat(1, "A3");
        System.out.println("Original Seats: " + Arrays.toString(b.getSeatNumbers())); // ["A1", "A2"]
        System.out.println("Updated Seats: " + Arrays.toString(updated.getSeatNumbers())); // ["A1", "A3"]

        // Test Nightly Settlement
        BookingReceipt[] batch = {
            new GroupBookingReceipt("CH-2002", new String[]{"B1", "B2"}, 2),
            null,
            new BookingReceipt("CH-3003", new String[]{"C1"})
        };
        System.out.println("\nSettlement Output:\n" + processNightlySettlement(batch));
    }
}

class GroupBookingReceipt extends BookingReceipt {
    private final int groupSize;

    public GroupBookingReceipt(String bookingId, String[] seatNumbers, int groupSize) {
        super(bookingId, seatNumbers);
        this.groupSize = groupSize;
    }

    public int getGroupSize() {
        return groupSize;
    }
}