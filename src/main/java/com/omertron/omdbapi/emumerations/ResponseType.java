package com.omertron.omdbapi.emumerations;

public enum ResponseType {
    /*
     * Get the response as JSON format
     */

    JSON,
    /*
     * Get the response as XML format
     */
    XML;
    private static final ResponseType DEFAULT = ResponseType.JSON;

    /**
     * Is the supplied ResponseType the default
     *
     * @param responseType
     * @return
     */
    public static boolean isDefault(ResponseType responseType) {
        return responseType.equals(DEFAULT);
    }

    /**
     * Get the default ResponseType
     *
     * @return
     */
    public static ResponseType getDefault() {
        return DEFAULT;
    }
}
