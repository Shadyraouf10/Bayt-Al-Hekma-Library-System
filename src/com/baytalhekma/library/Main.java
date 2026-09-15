package com.baytalhekma.library;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner =
            new Scanner(System.in);

    private static final Library library =
            new Library(
                    "Bayt Al Hekma Library"
            );

    public static void main(
            String[] args) {

        initializeLibrary();

        System.out.println(
                "=================================="
        );

        System.out.println(
                " Welcome to "
                        + library.getLibraryName()
        );

        System.out.println(
                "=================================="
        );

        boolean running = true;

        while (running) {

            displayMainMenu();

            int option =
                    readInt(
                            "Choose an option: "
                    );

            System.out.println();

            switch (option) {

                case 1 ->
                        library.displayCatalogue();

                case 2 ->
                        registerNewMember();

                case 3 ->
                        borrowItem();

                case 4 ->
                        returnItem();

                case 5 ->
                        renewLoan();

                case 6 ->
                        searchItemById();

                case 7 ->
                        viewItemsByStatus();

                case 8 ->
                        payOutstandingFines();

                case 9 ->
                        library.displayAllMembers();

                case 10 ->
                        library.displayLibraryReport();

                case 0 -> {

                    running = false;

                    System.out.println(
                            "Thank you for using "
                                    + library
                                    .getLibraryName()
                                    + ". Goodbye!"
                    );
                }

                default ->
                        System.out.println(
                                "Invalid option. "
                                        + "Choose from 0 to 10."
                        );
            }

            if (running) {

                System.out.println(
                        "\nPress Enter to "
                                + "return to the menu..."
                );

                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private static void
    initializeLibrary() {

        library.addItem(
                new Book(
                        "BK001",
                        "Clean Code",
                        "Robert C. Martin",
                        464
                )
        );

        library.addItem(
                new Book(
                        "BK002",
                        "Effective Java",
                        "Joshua Bloch",
                        416
                )
        );

        library.addItem(
                new Magazine(
                        "MG001",
                        "National Geographic",
                        245
                )
        );

        library.addItem(
                new Magazine(
                        "MG002",
                        "Java Magazine",
                        94
                )
        );

        library.addItem(
                new DVD(
                        "DV001",
                        "Interstellar",
                        169
                )
        );

        library.addItem(
                new DVD(
                        "DV002",
                        "The Social Network",
                        120
                )
        );

        library.registerMember(
                new Member(
                        "Shady Abdelraouf",
                        "MEM1001",
                        MembershipType.STUDENT
                )
        );

        library.registerMember(
                new Member(
                        "Ahmed Ali",
                        "MEM1002",
                        MembershipType.STAFF,
                        50
                )
        );

        library.registerMember(
                new Member(
                        "Mona Hassan",
                        "MEM1003",
                        MembershipType.PUBLIC
                )
        );
    }

    private static void
    displayMainMenu() {

        System.out.println(
                "\n========== MAIN MENU =========="
        );

        System.out.println(
                "1. View Catalogue"
        );

        System.out.println(
                "2. Register Member"
        );

        System.out.println(
                "3. Borrow Item"
        );

        System.out.println(
                "4. Return Item"
        );

        System.out.println(
                "5. Renew Loan"
        );

        System.out.println(
                "6. Search Item by ID"
        );

        System.out.println(
                "7. View Items by Status"
        );

        System.out.println(
                "8. Pay Outstanding Fines"
        );

        System.out.println(
                "9. View All Members"
        );

        System.out.println(
                "10. Library Report"
        );

        System.out.println(
                "0. Exit"
        );

        System.out.println(
                "==============================="
        );
    }

    private static void
    registerNewMember() {

        System.out.println(
                "=== Register New Member ==="
        );

        String name =
                readRequiredText(
                        "Enter member name: "
                );

        String membershipId =
                readRequiredText(
                        "Enter membership ID: "
                );

        MembershipType type =
                readMembershipType();

        Member member =
                new Member(
                        name,
                        membershipId,
                        type
                );

        library.registerMember(member);
    }

    private static MembershipType
    readMembershipType() {

        while (true) {

            System.out.println(
                    "\nSelect Membership Type:"
            );

            System.out.println(
                    "1. STUDENT - 25% waiver"
            );

            System.out.println(
                    "2. STAFF - 10% waiver"
            );

            System.out.println(
                    "3. PUBLIC - No waiver"
            );

            int option =
                    readInt(
                            "Choose membership type: "
                    );

            switch (option) {

                case 1:
                    return MembershipType.STUDENT;

                case 2:
                    return MembershipType.STAFF;

                case 3:
                    return MembershipType.PUBLIC;

                default:
                    System.out.println(
                            "Invalid membership type. "
                                    + "Choose 1, 2, or 3."
                    );
            }
        }
    }

    private static void borrowItem() {

        System.out.println(
                "=== Borrow Item ==="
        );

        String catalogueId =
                readRequiredText(
                        "Enter catalogue ID: "
                );

        String membershipId =
                readRequiredText(
                        "Enter membership ID: "
                );

        library.borrowItem(
                catalogueId,
                membershipId
        );
    }

    private static void returnItem() {

        System.out.println(
                "=== Return Item ==="
        );

        String catalogueId =
                readRequiredText(
                        "Enter catalogue ID: "
                );

        int daysOverdue =
                readNonNegativeInt(
                        "Enter days overdue "
                                + "(0 if on time): "
                );

        library.returnItem(
                catalogueId,
                daysOverdue
        );
    }

    private static void renewLoan() {

        System.out.println(
                "=== Renew Loan ==="
        );

        String catalogueId =
                readRequiredText(
                        "Enter catalogue ID: "
                );

        library.renewItem(
                catalogueId
        );
    }

    private static void
    searchItemById() {

        System.out.println(
                "=== Search Item by ID ==="
        );

        String catalogueId =
                readRequiredText(
                        "Enter catalogue ID: "
                );

        library.displayItemById(
                catalogueId
        );
    }

    private static void
    viewItemsByStatus() {

        System.out.println(
                "=== View Items by Status ==="
        );

        ItemStatus status =
                readItemStatus();

        library.displayItemsByStatus(
                status
        );
    }

    private static ItemStatus
    readItemStatus() {

        while (true) {

            System.out.println(
                    "\nSelect Item Status:"
            );

            System.out.println(
                    "1. AVAILABLE"
            );

            System.out.println(
                    "2. ON LOAN"
            );

            System.out.println(
                    "3. RESERVED"
            );

            System.out.println(
                    "4. LOST"
            );

            int option =
                    readInt(
                            "Choose item status: "
                    );

            switch (option) {

                case 1:
                    return ItemStatus.AVAILABLE;

                case 2:
                    return ItemStatus.ON_LOAN;

                case 3:
                    return ItemStatus.RESERVED;

                case 4:
                    return ItemStatus.LOST;

                default:
                    System.out.println(
                            "Invalid item status. "
                                    + "Choose from 1 to 4."
                    );
            }
        }
    }

    private static void
    payOutstandingFines() {

        System.out.println(
                "=== Pay Outstanding Fines ==="
        );

        String membershipId =
                readRequiredText(
                        "Enter membership ID: "
                );

        Member member =
                library.findMemberById(
                        membershipId
                );

        if (member == null) {

            System.out.println(
                    "Payment failed: "
                            + "member was not found."
            );

            return;
        }

        System.out.println(
                "Member: "
                        + member.getName()
        );

        System.out.printf(
                "Outstanding Balance: "
                        + "%.2f EGP%n",
                member.getOutstandingBalance()
        );

        if (member
                .getOutstandingBalance()
                == 0) {

            System.out.println(
                    "This member has "
                            + "no outstanding fines."
            );

            return;
        }

        double amount =
                readPositiveDouble(
                        "Enter payment amount: "
                );

        library.payOutstandingFine(
                membershipId,
                amount
        );
    }

    private static String
    readRequiredText(
            String message) {

        while (true) {

            System.out.print(message);

            String value =
                    scanner.nextLine()
                            .trim();

            if (!value.isBlank()) {

                return value;
            }

            System.out.println(
                    "Input cannot be empty. "
                            + "Please try again."
            );
        }
    }

    private static int readInt(
            String message) {

        while (true) {

            System.out.print(message);

            String input =
                    scanner.nextLine()
                            .trim();

            try {

                return Integer.parseInt(
                        input
                );

            } catch (
                    NumberFormatException
                            exception) {

                System.out.println(
                        "Invalid input. "
                                + "Enter a whole number."
                );
            }
        }
    }

    private static int
    readNonNegativeInt(
            String message) {

        while (true) {

            int number =
                    readInt(message);

            if (number >= 0) {

                return number;
            }

            System.out.println(
                    "Number cannot be negative."
            );
        }
    }

    private static double
    readDouble(
            String message) {

        while (true) {

            System.out.print(message);

            String input =
                    scanner.nextLine()
                            .trim();

            try {

                double number =
                        Double.parseDouble(
                                input
                        );

                if (Double.isFinite(number)) {

                    return number;
                }

                System.out.println(
                        "Enter a valid number."
                );

            } catch (
                    NumberFormatException
                            exception) {

                System.out.println(
                        "Invalid input. "
                                + "Enter a numeric value."
                );
            }
        }
    }

    private static double
    readPositiveDouble(
            String message) {

        while (true) {

            double number =
                    readDouble(message);

            if (number > 0) {

                return number;
            }

            System.out.println(
                    "Amount must be "
                            + "greater than zero."
            );
        }
    }
}