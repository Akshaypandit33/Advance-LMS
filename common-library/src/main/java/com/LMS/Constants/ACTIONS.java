package com.LMS.Constants;

public enum ACTIONS{
    // Basic CRUD
    CREATE,
    READ,
    UPDATE,
    DELETE,

    // Admin / System
    CONFIGURE,
    MANAGE,
    RESET_PASSWORD,
    ASSIGN_ROLE,
    SET_PERMISSIONS,

    // ABAC-related
    OWN,
    ACCESS_OWN,
    ACCESS_ALL,
}
