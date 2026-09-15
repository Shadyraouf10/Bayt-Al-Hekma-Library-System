package com.baytalhekma.library;

public class Magazine
        extends LibraryItem
        implements Renewable {

    public static final int
            LOAN_PERIOD_DAYS = 7;

    public static final double
            DAILY_FINE_RATE = 3.00;

    public static final double
            MAXIMUM_FINE = 30.00;

    public static final int
            RENEWAL_LIMIT = 1;

    private final int issueNumber;

    public Magazine(
            String catalogueId,
            String title,
            int issueNumber) {

        super(
                catalogueId,
                title
        );

        this.issueNumber =
                Math.max(
                        issueNumber,
                        0
                );
    }

    public int getIssueNumber() {

        return issueNumber;
    }

    @Override
    public double calculateFine(
            int daysOverdue) {

        if (daysOverdue <= 0) {

            return 0;
        }

        double calculatedFine =
                daysOverdue
                        * DAILY_FINE_RATE;

        return Math.min(
                calculatedFine,
                MAXIMUM_FINE
        );
    }

    @Override
    public int getLoanPeriod() {

        return LOAN_PERIOD_DAYS;
    }

    @Override
    public String getItemType() {

        return "MAGAZINE";
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
                    "Renewal failed: magazine "
                            + "is not currently on loan."
            );

            return false;
        }

        if (getRenewalCount()
                >= getRenewalLimit()) {

            System.out.println(
                    "Renewal failed: magazine "
                            + "has reached its limit of "
                            + getRenewalLimit()
                            + " renewal."
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
                "Magazine renewed successfully."
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

    public void displayMagazineDetails() {

        displayDetails();

        System.out.println(
                "Issue Number: "
                        + issueNumber
        );
    }
}
