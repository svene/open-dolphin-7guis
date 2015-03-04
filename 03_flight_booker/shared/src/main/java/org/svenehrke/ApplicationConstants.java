package org.svenehrke;

/**
 * Place for shared information among client and server. Typically identifiers for models, attributes and actions.
 */
public class ApplicationConstants {

    public static final String PM_APP = unique("APP");
    public static final String ATT_FLIGHT_TYPE = "ATT_FLIGHT_TYPE";
    public static final String VAL_FT_ONE_WAY = "o";
    public static final String VAL_FT_RETURN = "r";

    public static final String ATT_RETURN_DATE_ENABLED = "ATT_RETURN_DATE_ENABLED";

	public static final String ATT_START_DATE = "ATT_START_DATE";
	public static final String ATT_VALID_START_DATE = "ATT_VALID_START_DATE";

	public static final String ATT_RETURN_DATE = "ATT_RETURN_DATE";
	public static final String ATT_INVALID_RETURN_DATE = "ATT_INVALID_RETURN_DATE";

	public static final String COMMAND_INIT = unique("CMD_INIT");
	public static final String COMMAND_BOOK = unique("CMD_BOOK");
	public static final String ATT_BOOK_COMMAND_ENABLED = "ATT_BOOK_COMMAND_ENABLED";


    /**
     * Unify the identifier with the class name prefix.
     */
    private static String unique(String key) {
        return ApplicationConstants.class.getName() + "." + key;
    }

}
