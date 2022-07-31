package com.bigshare.enums;

public enum Country {
    USA("United States of America", "USA"),
    AFG("Afghanistan", "AFG"),
    ALB("Albania", "ALB"),
    DZA("Algeria", "DZA"),
    AND("Andorra", "AND"),
    AGO("Angola", "AGO"),
    ARG("Argentina", "ARG"),
    ARM("Armenia", "ARM"),
    AUS("Australia", "AUS"),
    AUT("Austria", "AUT"),
    AZE("Azerbaijan", "AZE"),
    BHS("Bahamas", "BHS"),
    BHR("Bahrain", "BHR"),
    BGD("Bangladesh", "BGD"),
    BRB("Barbados", "BRB"),
    BEL("Belgium", "BEL"),
    BLZ("Belize", "BLZ"),
    BRA("Brazil", "BRA"),
    BGR("Bulgaria", "BGR"),
    CAN("Canada", "CAN"),
    COL("Colombia", "COL"),
    COD("Democratic Republic of the Congo", "COD"),
    CRI("Costa Rica", "CRI"),
    HRV("Croatia", "HRV"),
    CZE("Czech Republic", "CZE"),
    DNK("Denmark", "DNK"),
    ECU("Ecuador", "ECU"),
    EGY("Egypt", "EGY"),
    EST("Estonia", "EST"),
    FIN("Finland", "FIN"),
    FRA("France", "FRA"),
    GEO("Georgia", "GEO"),
    DEU("Germany", "DEU"),
    GRC("Greece", "GRC"),
    HUN("Hungary", "HUN"),
    ISL("Iceland", "ISL"),
    IND("India", "IND"),
    IRL("Republic of Ireland", "IRL"),
    ISR("Israel", "ISR"),
    ITA("Italy", "ITA"),
    JPN("Japan", "JPN"),
    MYS("Malaysia", "MYS"),
    MEX("Mexico", "MEX"),
    MDA("Moldova", "MDA"),
    MCO("Monaco", "MCO"),
    MNE("Montenegro", "MNE"),
    NLD("Netherlands", "NLD"),
    NZL("New Zealand", "NZL"),
    NGA("Nigeria", "NGA"),
    NOR("Norway", "NOR"),
    PAN("Panama", "PAN"),
    PRY("Paraguay", "PRY"),
    PER("Peru", "PER"),
    PLN("Poland", "PLN"),
    PRT("Portugal", "PRT"),
    ROU("Romania", "ROU"),
    SAU("Saudi Arabia", "SAU"),
    SGP("Singapore", "SGP"),
    SVK("Slovakia", "SVK"),
    SVN("Slovenia", "SVN"),
    ZAF("South Africa", "ZAF"),
    ESP("Spain", "ESP"),
    SWZ("Swaziland", "SWZ"),
    SWE("Sweden", "SWE"),
    CHE("Switzerland", "CHE"),
    TUR("Turkey", "TUR"),
    TKM("Turkmenistan", "TKM"),
    UKR("Ukraine", "UKR"),
    ARE("United Arab Emirates", "ARE"),
    GBR("United Kingdom", "GBR"),
    URY("Uruguay", "URY"),
    UZB("Uzbekistan", "UZB"),
    VEN("Venezuela","VEN");

    private final String country;
    private final String countryCode;

    Country(String country, String countryCode) {
        this.country = country;
        this.countryCode = countryCode;
    }

    public String getCountry() {
        return country;
    }

    public String getCountryCode() {
        return countryCode;
    }

}