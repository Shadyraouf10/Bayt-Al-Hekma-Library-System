package com.baytalhekma.library;

public class Library {

    public static final int
            MAX_ITEMS = 100;

    public static final int
            MAX_MEMBERS = 100;

    public static final int
            REPORT_OVERDUE_DAYS = 5;

    private final String libraryName;

    private final LibraryItem[] items;

    private final Member[] members;

    private int itemCount;

    private int memberCount;

    public Library(String libraryName) {

        if (libraryName == null
                || libraryName.isBlank()) {

            this.libraryName =
                    LibraryItem.LIBRARY_NAME;

        } else {

            this.libraryName =
                    libraryName.trim();
        }

        this.items =
                new LibraryItem[MAX_ITEMS];

        this.members =
                new Member[MAX_MEMBERS];

        this.itemCount = 0;

        this.memberCount = 0;
    }

    public String getLibraryName() {

        return libraryName;
    }

    public int getItemCount() {

        return itemCount;
    }

    public int getMemberCount() {

        return memberCount;
    }

    public boolean addItem(
            LibraryItem item) {

        if (item == null) {

            System.out.println(
                    "Item registration failed: "
                            + "item cannot be null."
            );

            return false;
        }

        if (itemCount >= items.length) {

            System.out.println(
                    "Item registration failed: "
                            + "catalogue is full."
            );

            return false;
        }

        if (findItemById(
                item.getCatalogueId()
        ) != null) {

            System.out.println(
                    "Item registration failed: "
                            + "catalogue ID already exists."
            );

            return false;
        }

        items[itemCount] =
                item;

        itemCount++;

        System.out.println(
                "Item added successfully."
        );

        System.out.println(
                "Catalogue ID: "
                        + item.getCatalogueId()
        );

        return true;
    }

    public boolean registerMember(
            Member member) {

        if (member == null) {

            System.out.println(
                    "Member registration failed: "
                            + "member cannot be null."
            );

            return false;
        }

        if (memberCount
                >= members.length) {

            System.out.println(
                    "Member registration failed: "
                            + "member register is full."
            );

            return false;
        }

        if (findMemberById(
                member.getMembershipId()
        ) != null) {

            System.out.println(
                    "Member registration failed: "
                            + "membership ID already exists."
            );

            return false;
        }

        members[memberCount] =
                member;

        memberCount++;

        System.out.println(
                "Member registered successfully."
        );

        System.out.println(
                "Membership ID: "
                        + member.getMembershipId()
        );

        return true;
    }

    public LibraryItem findItemById(
            String catalogueId) {

        if (catalogueId == null
                || catalogueId.isBlank()) {

            return null;
        }

        for (int index = 0;
             index < itemCount;
             index++) {

            if (items[index]
                    .getCatalogueId()
                    .equalsIgnoreCase(
                            catalogueId.trim()
                    )) {

                return items[index];
            }
        }

        return null;
    }

    public Member findMemberById(
            String membershipId) {

        if (membershipId == null
                || membershipId.isBlank()) {

            return null;
        }

        for (int index = 0;
             index < memberCount;
             index++) {

            if (members[index]
                    .getMembershipId()
                    .equalsIgnoreCase(
                            membershipId.trim()
                    )) {

                return members[index];
            }
        }

        return null;
    }

    private Member findMemberByName(
            String memberName) {

        if (memberName == null
                || memberName.isBlank()) {

            return null;
        }

        for (int index = 0;
             index < memberCount;
             index++) {

            if (members[index]
                    .getName()
                    .equalsIgnoreCase(
                            memberName.trim()
                    )) {

                return members[index];
            }
        }

        return null;
    }

    public boolean borrowItem(
            String catalogueId,
            String membershipId) {

        LibraryItem item =
                findItemById(
                        catalogueId
                );

        if (item == null) {

            System.out.println(
                    "Borrowing failed: "
                            + "item was not found."
            );

            return false;
        }

        Member member =
                findMemberById(
                        membershipId
                );

        if (member == null) {

            System.out.println(
                    "Borrowing failed: "
                            + "member was not found."
            );

            return false;
        }

        if (item.getStatus()
                != ItemStatus.AVAILABLE) {

            System.out.println(
                    "Borrowing failed: "
                            + "item is not available."
            );

            System.out.println(
                    "Current status: "
                            + item.getStatus()
            );

            return false;
        }

        if (!member.canBorrow()) {

            System.out.println(
                    "Borrowing failed: "
                            + "member is not eligible."
            );

            System.out.println(
                    "Items currently held: "
                            + member
                            .getItemsCurrentlyHeld()
            );

            System.out.printf(
                    "Outstanding balance: "
                            + "%.2f EGP%n",
                    member
                            .getOutstandingBalance()
            );

            return false;
        }

        if (!item.lendTo(
                member.getName()
        )) {

            return false;
        }

        member.recordBorrowing();

        System.out.println(
                "Borrower: "
                        + member.getName()
        );

        System.out.println(
                "Item: "
                        + item.getTitle()
        );

        System.out.println(
                "Loan period: "
                        + item.getLoanPeriod()
                        + " days"
        );

        return true;
    }

    public boolean returnItem(
            String catalogueId,
            int daysOverdue) {

        LibraryItem item =
                findItemById(
                        catalogueId
                );

        if (item == null) {

            System.out.println(
                    "Return failed: "
                            + "item was not found."
            );

            return false;
        }

        if (item.getStatus()
                != ItemStatus.ON_LOAN) {

            System.out.println(
                    "Return failed: item "
                            + "is not currently on loan."
            );

            return false;
        }

        if (daysOverdue < 0) {

            System.out.println(
                    "Return failed: days overdue "
                            + "cannot be negative."
            );

            return false;
        }

        String borrowerName =
                item.getBorrowerName();

        Member member =
                findMemberByName(
                        borrowerName
                );

        if (member == null) {

            System.out.println(
                    "Return failed: borrower "
                            + "could not be found."
            );

            return false;
        }

        double baseFine =
                item.calculateFine(
                        daysOverdue
                );

        double waiverRate =
                member
                        .getMembershipType()
                        .getWaiverRate();

        double waiverAmount =
                member
                        .getMembershipType()
                        .calculateWaiver(
                                baseFine
                        );

        double fineAfterWaiver =
                baseFine
                        - waiverAmount;

        double administrativeCharge;

        if (daysOverdue == 0) {

            administrativeCharge = 0;

        } else {

            administrativeCharge =
                    LibraryItem
                            .ADMINISTRATIVE_CHARGE;
        }

        double finalCharge =
                fineAfterWaiver
                        + administrativeCharge;

        System.out.println(
                "\n===== RETURN DETAILS ====="
        );

        System.out.println(
                "Item: "
                        + item.getTitle()
        );

        System.out.println(
                "Borrower: "
                        + member.getName()
        );

        System.out.println(
                "Membership Type: "
                        + member
                        .getMembershipType()
        );

        System.out.println(
                "Days Overdue: "
                        + daysOverdue
        );

        System.out.printf(
                "Base Fine: %.2f EGP%n",
                baseFine
        );

        System.out.printf(
                "Waiver Rate: %.0f%%%n",
                waiverRate * 100
        );

        System.out.printf(
                "Waiver Amount: "
                        + "%.2f EGP%n",
                waiverAmount
        );

        System.out.printf(
                "Fine After Waiver: "
                        + "%.2f EGP%n",
                fineAfterWaiver
        );

        System.out.printf(
                "Administrative Charge: "
                        + "%.2f EGP%n",
                administrativeCharge
        );

        System.out.printf(
                "Final Charge: %.2f EGP%n",
                finalCharge
        );

        if (finalCharge > 0) {

            member.chargeFine(
                    finalCharge
            );

        } else {

            System.out.println(
                    "Returned on time. "
                            + "No fine was charged."
            );
        }

        member.recordReturn();

        if (!item.takeBack()) {

            return false;
        }

        return true;
    }

    public boolean renewItem(
            String catalogueId) {

        LibraryItem item =
                findItemById(
                        catalogueId
                );

        if (item == null) {

            System.out.println(
                    "Renewal failed: "
                            + "item was not found."
            );

            return false;
        }

        if (item
                instanceof Renewable
                renewable) {

            return renewable.renewLoan();
        }

        System.out.println(
                "Renewal failed: "
                        + item.getItemType()
                        + " items cannot be renewed."
        );

        return false;
    }

    public boolean payOutstandingFine(
            String membershipId,
            double amount) {

        Member member =
                findMemberById(
                        membershipId
                );

        if (member == null) {

            System.out.println(
                    "Payment failed: "
                            + "member was not found."
            );

            return false;
        }

        return member.payFine(amount);
    }

    public void displayCatalogue() {

        System.out.println(
                "\n===== LIBRARY CATALOGUE ====="
        );

        if (itemCount == 0) {

            System.out.println(
                    "The catalogue is empty."
            );

            return;
        }

        for (int index = 0;
             index < itemCount;
             index++) {

            items[index]
                    .displayDetails();
        }

        System.out.println(
                "Total catalogue items: "
                        + itemCount
        );
    }

    public void displayItemById(
            String catalogueId) {

        LibraryItem item =
                findItemById(
                        catalogueId
                );

        if (item == null) {

            System.out.println(
                    "Item was not found."
            );

            return;
        }

        System.out.println(
                "\n===== ITEM DETAILS ====="
        );

        item.displayDetails();

        if (item instanceof Book book) {

            System.out.println(
                    "Author: "
                            + book.getAuthor()
            );

            System.out.println(
                    "Page Count: "
                            + book.getPageCount()
            );

        } else if (item
                instanceof Magazine
                magazine) {

            System.out.println(
                    "Issue Number: "
                            + magazine
                            .getIssueNumber()
            );

        } else if (item instanceof DVD dvd) {

            System.out.println(
                    "Runtime: "
                            + dvd
                            .getRuntimeInMinutes()
                            + " minutes"
            );
        }
    }

    public void displayItemsByStatus(
            ItemStatus requiredStatus) {

        if (requiredStatus == null) {

            System.out.println(
                    "Invalid item status."
            );

            return;
        }

        int matchingItems = 0;

        System.out.println(
                "\nItems with status: "
                        + requiredStatus
        );

        for (int index = 0;
             index < itemCount;
             index++) {

            LibraryItem item =
                    items[index];

            if (item.getStatus()
                    == requiredStatus) {

                item.displayDetails();

                matchingItems++;
            }
        }

        if (matchingItems == 0) {

            System.out.println(
                    "No items were found "
                            + "with this status."
            );
        }
    }

    public void displayAllMembers() {

        System.out.println(
                "\n===== LIBRARY MEMBERS ====="
        );

        if (memberCount == 0) {

            System.out.println(
                    "No members are registered."
            );

            return;
        }

        for (int index = 0;
             index < memberCount;
             index++) {

            System.out.println(
                    "-------------------------"
            );

            members[index]
                    .displayDetails();
        }

        System.out.println(
                "-------------------------"
        );

        System.out.println(
                "Total members: "
                        + memberCount
        );
    }

    public int getItemsOnLoanCount() {

        int count = 0;

        for (int index = 0;
             index < itemCount;
             index++) {

            if (items[index].getStatus()
                    == ItemStatus.ON_LOAN) {

                count++;
            }
        }

        return count;
    }

    public double getLoanRate() {

        if (itemCount == 0) {

            return 0;
        }

        return getItemsOnLoanCount()
                * 100.0
                / itemCount;
    }

    public double
    calculateTotalOutstandingFines() {

        double total = 0;

        for (int index = 0;
             index < memberCount;
             index++) {

            total +=
                    members[index]
                            .getOutstandingBalance();
        }

        return total;
    }

    public double calculateProjectedFines(
            int daysOverdue) {

        if (daysOverdue <= 0) {

            return 0;
        }

        double total = 0;

        for (int index = 0;
             index < itemCount;
             index++) {

            LibraryItem item =
                    items[index];

            if (item.getStatus()
                    == ItemStatus.ON_LOAN) {

                total +=
                        item.calculateFine(
                                daysOverdue
                        );
            }
        }

        return total;
    }

    public void displayLibraryReport() {

        System.out.println(
                "\n===== LIBRARY REPORT ====="
        );

        System.out.println(
                "Library: " + libraryName
        );

        System.out.println(
                "Catalogue Size: "
                        + itemCount
        );

        System.out.println(
                "Items Ever Catalogued: "
                        + LibraryItem
                        .getTotalItemsCatalogued()
        );

        System.out.println(
                "Items On Loan: "
                        + getItemsOnLoanCount()
        );

        System.out.printf(
                "Loan Rate: %.2f%%%n",
                getLoanRate()
        );

        System.out.printf(
                "Total Outstanding Fines: "
                        + "%.2f EGP%n",
                calculateTotalOutstandingFines()
        );

        System.out.printf(
                "Projected Base Fines "
                        + "for %d Overdue Days: "
                        + "%.2f EGP%n",
                REPORT_OVERDUE_DAYS,
                calculateProjectedFines(
                        REPORT_OVERDUE_DAYS
                )
        );
    }
}
