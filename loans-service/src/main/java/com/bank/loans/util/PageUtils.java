package com.bank.loans.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.JpaSort;

import java.util.Objects;

/**
 * Util class for pages.
 * @version 1.0
 * @author Dmitry Kalichkin
 */
public final class PageUtils {
    /**
     * Creating page by given params. This method helps to avoid {@link NullPointerException}.
     * @param page Offset.
     * @param size Limit of items on page.
     * @param isAscending Is ascending or descending.
     * @param sortParams Parameters for sorting.
     * @return New {@link Pageable} object.
     */
    public static Pageable create(Integer page, Integer size, Boolean isAscending,
                                  String... sortParams) {
        Pageable pageRequest;

        if (Objects.nonNull(sortParams) && Objects.nonNull(size)) {
            pageRequest = createPageWithSort(page, size, isAscending, sortParams);
        } else {
            pageRequest = PageRequest.of(page, size);
        }
        return pageRequest;
    }


    private static Pageable createPageWithSort(Integer page, Integer size, Boolean isAscending,
                                               String... sortParams) {
        return  (isAscending)
                ? PageRequest.of(page, size, JpaSort.by(sortParams).ascending())
                : PageRequest.of(page, size, JpaSort.by(sortParams).descending());
    }
}
