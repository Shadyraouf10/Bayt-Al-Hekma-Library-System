package com.baytalhekma.library;

public abstract class LibraryItem {

    public static final String
            LIBRARY_NAME =
            "Bayt Al Hekma Library";

    public static final double
            ADMINISTRATIVE_CHARGE =
            10.00;

    private static int
            totalItemsCatalogued = 0;

    private final String catalogueId;

    private final String title;

    private ItemStatus status;

    private String borrowerName;

    private int renewalCount;

    public LibraryItem(
            String catalogueId,
            String title) {

        if (catalogueId == null
                || catalogueId.isBlank()) {

            this.catalogueId =
                    "UNKNOWN";

        } else {

            this.catalogueId =
                    catalogueId.trim();
        }

        if (title == null
                || title.isBlank()) {

            this.title =
                    "Untitled";

        } else {

            this.title =
                    title.trim();
        }

        this.status =
                ItemStatus.AVAILABLE;

        this.borrowerName =
                "None";

        this.renewalCount = 0;

        totalItemsCatalogued++;
    }

    public String getCatalogueId() {

        return catalogueId;
    }

    public String getTitle() {

        return title;
    }

    public ItemStatus getStatus() {

        return status;
    }

    public String getBorrowerName() {

        return borrowerName;
    }

    public int getRenewalCount() {

        return renewalCount;
    }

    public static int
    getTotalItemsCatalogued() {

        return totalItemsCatalogued;
    }

    public boolean lendTo(
            String memberName) {

        if (status
                != ItemStatus.AVAILABLE) {

            System.out.println(
                    "Lending failed: item "
                            + "is not available."
            );

            return false;
        }

        if (memberName == null
                || memberName.isBlank()) {

            System.out.println(
                    "Lending failed: "
                            + "member name is required."
            );

            return false;
        }

        borrowerName =
                memberName.trim();

        status =
                ItemStatus.ON_LOAN;

        renewalCount = 0;

        System.out.println(
                "Item borrowed successfully."
        );

        return true;
    }

    public final boolean takeBack() {

        if (status
                != ItemStatus.ON_LOAN) {

            System.out.println(
                    "Return failed: item "
                            + "is not currently on loan."
            );

            return false;
        }

        status =
                ItemStatus.AVAILABLE;

        borrowerName =
                "None";

        renewalCount = 0;

        System.out.println(
                "Item returned successfully."
        );

        return true;
    }

    public boolean markReserved() {

        if (status
                != ItemStatus.AVAILABLE) {

            System.out.println(
                    "Reservation failed: item "
                            + "must be available."
            );

            return false;
        }

        status =
                ItemStatus.RESERVED;

        System.out.println(
                "Item marked as reserved."
        );

        return true;
    }

    public boolean markLost() {

        if (status
                == ItemStatus.LOST) {

            System.out.println(
                    "Item is already "
                            + "marked as lost."
            );

            return false;
        }

        status =
                ItemStatus.LOST;

        borrowerName =
                "None";

        renewalCount = 0;

        System.out.println(
                "Item marked as lost."
        );

        return true;
    }

    public boolean makeAvailable() {

        if (status
                == ItemStatus.ON_LOAN) {

            System.out.println(
                    "An item on loan must "
                            + "be returned normally."
            );

            return false;
        }

        if (status
                == ItemStatus.AVAILABLE) {

            System.out.println(
                    "Item is already available."
            );

            return false;
        }

        status =
                ItemStatus.AVAILABLE;

        borrowerName =
                "None";

        renewalCount = 0;

        System.out.println(
                "Item is now available."
        );

        return true;
    }

    protected boolean
    recordRenewal() {

        if (status
                != ItemStatus.ON_LOAN) {

            System.out.println(
                    "Renewal failed: item "
                            + "is not currently on loan."
            );

            return false;
        }

        renewalCount++;

        return true;
    }

    public void displayDetails() {

        System.out.printf(
                "ID: %s | Type: %s"
                        + " | Title: %s"
                        + " | Status: %s"
                        + " | Borrower: %s"
                        + " | Loan Period: %d days"
                        + " | One-Day Fine: "
                        + "%.2f EGP%n",

                catalogueId,
                getItemType(),
                title,
                status,
                borrowerName,
                getLoanPeriod(),
                calculateFine(1)
        );
    }

    public abstract double
    calculateFine(
            int daysOverdue);

    public abstract int
    getLoanPeriod();

    public abstract String
    getItemType();
}