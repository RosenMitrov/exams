package exam.constants;

public enum Messages {
    ;
    public static final String CUSTOMER_INVALID_PRINT_MESSAGE = "Invalid Customer";
    public static final String CUSTOMER_SUCCESSFULLY_SAVED_PRINT_FORMAT = "Successfully imported Customer %s %s - %s";
    public static final String LAPTOP_INVALID_PRINT_MESSAGE = "Invalid Laptop";
    public static final String LAPTOP_SUCCESSFULLY_SAVED_PRINT_FORMAT = "Successfully imported Laptop %s - %.2f - %d - %d";
    public static final String SHOP_INVALID_PRINT_MESSAGE = "Invalid shop";
    public static final String SHOP_SUCCESSFULLY_SAVED_PRINT_FORMAT = "Successfully imported Shop %s - %.0f";
    public static final String TOWN_INVALID_PRINT_MESSAGE = "Invalid town";
    public static final String TOWN_SUCCESSFULLY_SAVED_PRINT_FORMAT = "Successfully imported Town %s";

    public static final String OUTPUT_FORMAT_MESSAGE = "Laptop - %s" + System.lineSeparator() +
            "*Cpu speed - %.2f" + System.lineSeparator() +
            "**Ram - %d" + System.lineSeparator() +
            "***Storage - %d" + System.lineSeparator() +
            "****Price - %.2f" + System.lineSeparator() +
            "#Shop name - %s" + System.lineSeparator() +
            "##Town - %s" + System.lineSeparator();


}
