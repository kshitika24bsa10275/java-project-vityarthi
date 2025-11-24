package edu.ccrm.domain;

/**
 * Semester enum with optional year field (kept simple here).
 */
public enum Semester {
    SPRING, SUMMER, FALL;

    // Example placeholder if per-instance year were necessary; enums are singletons, so we don't store year here.
    // If you need a year-specific semester, consider a separate value object.
}
