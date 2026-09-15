package com.baytalhekma.library;

public class Member {

    public static final int
            MAX_BORROWED_ITEMS = 3;

    public static final double
            MAX_BALANCE_TO_BORROW = 100.00;

    private String name;

    private final String membershipId;

    private final MembershipType
            membershipType;

    private double outstandingBalance;

    private int itemsCurrentlyHeld;

    public Member(
            String name,
            String membershipId,
            MembershipType membershipType) {

        this(
                name,
                membershipId,
                membershipType,
                0
        );
    }

    public Member(
            String name,
            String membershipId,
            MembershipType membershipType,
            double openingBalance) {

        if (name == null
                || name.isBlank()) {

            this.name = "Unknown";

        } else {

            this.name =
                    name.trim();
        }

        if (membershipId == null
                || membershipId.isBlank()) {

            this.membershipId =
                    "UNKNOWN";

        } else {

            this.membershipId =
                    membershipId.trim();
        }

        if (membershipType == null) {

            this.membershipType =
                    MembershipType.PUBLIC;

        } else {

            this.membershipType =
                    membershipType;
        }

        this.outstandingBalance =
                Math.max(
                        openingBalance,
                        0
                );

        this.itemsCurrentlyHeld = 0;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        if (name == null
                || name.isBlank()) {

            System.out.println(
                    "Name update failed: "
                            + "name cannot be empty."
            );

            return;
        }

        this.name =
                name.trim();

        System.out.println(
                "Member name updated successfully."
        );
    }

    public String getMembershipId() {

        return membershipId;
    }

    public MembershipType
    getMembershipType() {

        return membershipType;
    }

    public double getOutstandingBalance() {

        return outstandingBalance;
    }

    public int getItemsCurrentlyHeld() {

        return itemsCurrentlyHeld;
    }

    public boolean canBorrow() {

        return itemsCurrentlyHeld
                < MAX_BORROWED_ITEMS

                && outstandingBalance
                <= MAX_BALANCE_TO_BORROW;
    }

    public boolean chargeFine(
            double amount) {

        if (amount <= 0) {

            System.out.println(
                    "Fine was not charged: "
                            + "amount must be positive."
            );

            return false;
        }

        outstandingBalance +=
                amount;

        System.out.printf(
                "Fine charged: %.2f EGP%n",
                amount
        );

        System.out.printf(
                "New outstanding balance: "
                        + "%.2f EGP%n",
                outstandingBalance
        );

        return true;
    }

    public boolean payFine(
            double amount) {

        if (amount <= 0) {

            System.out.println(
                    "Payment failed: "
                            + "amount must be positive."
            );

            return false;
        }

        if (amount
                > outstandingBalance) {

            System.out.printf(
                    "Payment failed: amount "
                            + "exceeds the balance "
                            + "of %.2f EGP.%n",
                    outstandingBalance
            );

            return false;
        }

        outstandingBalance -=
                amount;

        System.out.printf(
                "Payment successful. "
                        + "Remaining balance: "
                        + "%.2f EGP%n",
                outstandingBalance
        );

        return true;
    }

    public void recordBorrowing() {

        if (itemsCurrentlyHeld
                >= MAX_BORROWED_ITEMS) {

            System.out.println(
                    "Borrowing count cannot "
                            + "exceed "
                            + MAX_BORROWED_ITEMS
                            + "."
            );

            return;
        }

        itemsCurrentlyHeld++;
    }

    public void recordReturn() {

        if (itemsCurrentlyHeld <= 0) {

            System.out.println(
                    "Return count cannot "
                            + "go below zero."
            );

            return;
        }

        itemsCurrentlyHeld--;
    }

    public void displayDetails() {

        System.out.println(
                "Name: " + name
        );

        System.out.println(
                "Membership ID: "
                        + membershipId
        );

        System.out.println(
                "Membership Type: "
                        + membershipType
        );

        System.out.println(
                "Items Currently Held: "
                        + itemsCurrentlyHeld
        );

        System.out.printf(
                "Outstanding Balance: "
                        + "%.2f EGP%n",
                outstandingBalance
        );

        System.out.println(
                "Eligible to Borrow: "
                        + (canBorrow()
                        ? "Yes"
                        : "No")
        );
    }
}