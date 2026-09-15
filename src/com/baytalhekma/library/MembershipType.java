package com.baytalhekma.library;

public enum MembershipType {

    STUDENT(0.25),
    STAFF(0.10),
    PUBLIC(0.0);

    private final double waiverRate;

    MembershipType(double waiverRate) {
        this.waiverRate = waiverRate;
    }

    public double getWaiverRate() {
        return waiverRate;
    }

    public double calculateWaiver(double fine) {
        return fine * waiverRate;
    }
}
