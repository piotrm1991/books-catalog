package com.example.catalog.book.response;

import com.example.catalog.author.response.AuthorResponse;
import com.example.catalog.publisher.response.PublisherResponse;
import com.example.catalog.shelf.response.ShelfResponse;
import com.example.catalog.statustype.response.StatusTypeResponse;

/**
 * A record representing the response containing book information.
 */
public record BookResponse(
        Long id,

        String title,

        AuthorResponse author,

        PublisherResponse publisher,

        ShelfResponse shelf,

        StatusTypeResponse statusType
) {}
