package org.miobook.services;

import java.util.List;

public class ValidationService {
    public static boolean isBookNameUnic(String bookName) {return true;}
    public static boolean doesAuthorExist(String authorName){return true;}
    public static boolean isPublisherValid(String publisher){return true;}
    public static boolean isYearValid(int year){return true;}
    public static boolean isGenresValid(List<String> genres){return true;}
    public static boolean isUserAdmin(String usertype){return true;}
}