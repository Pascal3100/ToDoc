package fr.plopez.todoc.view.main;

/**
 * List of all possible sort methods for task
 */
public enum PossibleSortMethods {
    /**
     * Sort alphabetical by name
     */
    ALPHABETICAL,
    /**
     * Inverted sort alphabetical by name
     */
    ALPHABETICAL_INVERTED,
    /**
     * Lastly created first
     */
    RECENT_FIRST,
    /**
     * First created first
     */
    OLD_FIRST,
    /**
     * No sort
     */
    NONE
}
