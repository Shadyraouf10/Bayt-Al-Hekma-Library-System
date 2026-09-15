package com.baytalhekma.library;

public class DVD
        extends LibraryItem {

    public static final int
            LOAN_PERIOD_DAYS = 3;

    public static final double
            DAILY_FINE_RATE = 15.00;

    private final int runtimeInMinutes;

    public DVD(
            String catalogueId,
            String title,
            int runtimeInMinutes) {

        super(
                catalogueId,
                title
        );

        this.runtimeInMinutes =
                Math.max(
                        runtimeInMinutes,
                        0
                );
    }

    public int getRuntimeInMinutes() {

        return runtimeInMinutes;
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

        return "DVD";
    }

    public void displayDVDDetails() {

        displayDetails();

        System.out.println(
                "Runtime: "
                        + runtimeInMinutes
                        + " minutes"
        );
    }
}