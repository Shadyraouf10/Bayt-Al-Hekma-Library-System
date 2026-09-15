package com.baytalhekma.library;

public class Book
        extends LibraryItem
        implements Renewable {

    public static final int
            LOAN_PERIOD_DAYS = 14;

    public static final double
            DAILY_FINE_RATE = 5.00;

    public static final int
            RENEWAL_LIMIT = 2;

    private final String author;

    private final int pageCount;

    public Book(
            String catalogueId,
            String title,
            String author,
            int pageCount) {

        super(
                catalogueId,
                title
        );

        if (author == null
                || author.isBlank()) {

            this.author = "Unknown";

        } else {

            this.author =
                    author.trim();
        }

        this.pageCount =
                Math.max(
                        pageCount,
                        0
                );
    }

    public String getAuthor() {

        return author;
    }

    public int getPageCount() {

        return pageCount;
    }

    @Override
    public double calculateFine(
            int daysOverdue) {

        if (daysOverdue <= 0) {

            return 0;
        }

        return daysOverdue
                * DAILY_FINE_RATE;
    }

    @Override
    public int getLoanPeriod() {

        return LOAN_PERIOD_DAYS;
    }

    @Override
    public String getItemType() {

        return "BOOK";
    }

    @Override
    public int getRenewalLimit() {

        return RENEWAL_LIMIT;
    }

    @Override
    public boolean renewLoan() {

        if (getStatus()
                != ItemStatus.ON_LOAN) {

            System.out.println(
                    "Renewal failed: book "
                            + "is not currently on loan."
            );

            return false;
        }

        if (getRenewalCount()
                >= getRenewalLimit()) {

            System.out.println(
                    "Renewal failed: book "
                            + "has reached its limit of "
                            + getRenewalLimit()
                            + " renewals."
            );

            return false;
        }

        if (!recordRenewal()) {

            return false;
        }

        int remainingRenewals =
                getRenewalLimit()
                        - getRenewalCount();

        System.out.println(
                "Book renewed successfully."
        );

        System.out.println(
                "Renewals used: "
                        + getRenewalCount()
                        + " of "
                        + getRenewalLimit()
        );

        System.out.println(
                "Renewals remaining: "
                        + remainingRenewals
        );

        return true;
    }

    public void displayBookDetails() {

        displayDetails();

        System.out.println(
                "Author: " + author
        );

        System.out.println(
                "Page Count: "
                        + pageCount
        );
    }
}
