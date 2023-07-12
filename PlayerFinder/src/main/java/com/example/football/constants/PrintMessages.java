package com.example.football.constants;

public enum PrintMessages {
    ;
    public static final String SUCCESSFULLY_ADDED_TOWN_IN_DB_PRINT_FORMAT = "Successfully imported Town %s - %d";
    public static final String INVALID_TOWN_PRINT_MESSAGE = "Invalid Town";
    public static final String SUCCESSFULLY_ADDED_TEAM_IN_DB_PRINT_FORMAT = "Successfully imported Team %s - %d";
    public static final String INVALID_TEAM_PRINT_MESSAGE = "Invalid Team";
    public static final String SUCCESSFULLY_ADDED_PLAYER_IN_DB_PRINT_FORMAT = "Successfully imported Player %s %s - %s";
    public static final String INVALID_PLAYER_PRINT_MESSAGE = "Invalid Player";
    public static final String SUCCESSFULLY_ADDED_STAT_IN_DB_PRINT_FORMAT = "Successfully imported Stat %.2f - %.2f - %.2f";
    public static final String INVALID_STAT_PRINT_MESSAGE = "Invalid Stat";
    public static final String VIEW_FORMAT = "Player - %s %s" + System.lineSeparator() +
            "\tPosition - %s" + System.lineSeparator() +
            "\tTeam - %s" + System.lineSeparator() +
            "\tStadium - %s";
    public static final String BIRTHDATE_AFTER_PATTERN = "1995-01-01";
    public static final String BIRTHDATE_BEFORE_PATTERN = "2003-01-01";
    public static final String LOCAL_DATE_ADAPTER_PATTERN = "dd/MM/yyyy";
}
