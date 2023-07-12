package softuni.exam.constants;

public enum StaticMessage {
    ;
    public static final String CAR_SUCCESSFUL_PRINT_MESSAGE = "Successfully imported car - %s - %s";
    public static final String CAR_INVALID_PRINT_MESSAGE = "Invalid car";
    public static final String OFFER_SUCCESSFUL_PRINT_MESSAGE = "Successfully import offer %s - %b";
    public static final String OFFER_INVALID_PRINT_MESSAGE = "Invalid offer";
    public static final String PICTURE_SUCCESSFUL_PRINT_MESSAGE = "Successfully import picture - %s";
    public static final String PICTURE_INVALID_PRINT_MESSAGE = "Invalid picture";
    public static final String SELLER_SUCCESSFUL_PRINT_MESSAGE = "Successfully import seller %s - %s";
    public static final String SELLER_INVALID_PRINT_MESSAGE = "Invalid seller";

    public static final  String OUTPUT_FORMAT = "Car make - %s, model - %s" + System.lineSeparator() +
            "\tKilometers - %d" + System.lineSeparator() +
            "\tRegistered on - %s" + System.lineSeparator() +
            "\tNumber of pictures - %d" + System.lineSeparator();
}
