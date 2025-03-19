package org.miobook.utils;

import org.miobook.responses.SearchedBookItemRecord;
import org.miobook.responses.SearchedBooksRecord;

import java.util.*;
import java.util.stream.Collectors;
public class BookUtil {
    public static List<SearchedBookItemRecord> applySorting(List<SearchedBookItemRecord> books, String sortBy, String order) {
        Comparator<SearchedBookItemRecord> comparator;
        switch (sortBy != null ? sortBy.toLowerCase() : "") {
            case "average_rating":
                comparator = Comparator.comparing(SearchedBookItemRecord::averageRate);
                break;
            case "review_count":
                comparator = Comparator.comparingInt(SearchedBookItemRecord::reviewCount);
                break;
            default:
                return books;
        }
        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        books = books.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
        return books;
    }

    public static List<SearchedBookItemRecord> applyPagination(List<SearchedBookItemRecord> books, int page, int size) {
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, books.size());

        if (fromIndex >= books.size()) {
            return List.of();
        }

        return books.subList(fromIndex, toIndex);
    }

    public static List<SearchedBookItemRecord> findCommonBooks(List<SearchedBooksRecord> searchResults) {
        if (searchResults.isEmpty()) {
            return new ArrayList<>();
        }
        Set<SearchedBookItemRecord> commonBooks = new HashSet<>(searchResults.get(0).books());
        for (int i = 1; i < searchResults.size(); i++) {
            commonBooks.retainAll(new HashSet<>(searchResults.get(i).books()));
        }
        return new ArrayList<>(commonBooks);
    }

}
