package com.omertron.omdbapi.emumerations;

public enum PlotType {

    /*
     * The short version of the plot
     */
    SHORT,
    /*
     * The longer version of the plot
     */
    LONG;
    private static final PlotType DEFAULT = PlotType.SHORT;

    /**
     * Is the supplied PlotType the default
     *
     * @param plotType
     * @return
     */
    public static boolean isDefault(PlotType plotType) {
        return plotType.equals(DEFAULT);
    }

    /**
     * Get the default plot type
     *
     * @return
     */
    public static PlotType getDefault() {
        return DEFAULT;
    }
}
