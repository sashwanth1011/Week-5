class MovieTicket {
    private String seatNumber;
    private double ticketPrice;
    String screenId;
    public String movieTitle;
}

public class AccessChecker {

    public static String classifyAccess(String fieldModifier, String accessorContext) {
        if (fieldModifier == null || accessorContext == null) {
            return "DENIED";
        }

        switch (fieldModifier) {
            case "public":
                return "ALLOWED";

            case "protected":
                switch (accessorContext) {
                    case "SAME_CLASS":
                    case "SAME_PACKAGE":
                    case "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE":
                        return "ALLOWED";
                    case "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE":
                    case "DIFFERENT_PACKAGE":
                    default:
                        return "DENIED";
                }

            case "default":
                if ("SAME_CLASS".equals(accessorContext) || "SAME_PACKAGE".equals(accessorContext)) {
                    return "ALLOWED";
                }
                return "DENIED";

            case "private":
                if ("SAME_CLASS".equals(accessorContext)) {
                    return "ALLOWED";
                }
                return "DENIED";

            default:
                return "DENIED";
        }
    }

    public static String summarizeBatch(String[][] attempts) {
        int allowed = 0;
        int denied = 0;

        if (attempts != null) {
            for (String[] attempt : attempts) {
                if (attempt != null && attempt.length >= 2) {
                    String result = classifyAccess(attempt[0], attempt[1]);
                    if ("ALLOWED".equals(result)) {
                        allowed++;
                    } else {
                        denied++;
                    }
                }
            }
        }

        return "Allowed: " + allowed + "\nDenied: " + denied;
    }

    public static void main(String[] args) {
        System.out.println("--- Testing AccessChecker ---");
        System.out.println("private, SAME_CLASS -> " + classifyAccess("private", "SAME_CLASS"));
        System.out.println("protected, DIFFERENT_PACKAGE -> " + classifyAccess("protected", "DIFFERENT_PACKAGE"));
        System.out.println("protected, SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE -> " + classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"));
        System.out.println("protected, SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE -> " + classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"));

        String[][] batch = {
            {"default", "SAME_PACKAGE"},
            {"default", "DIFFERENT_PACKAGE"},
            {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println("\nBatch Summary:\n" + summarizeBatch(batch));
    }
}
