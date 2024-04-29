package com.example.catalog.book.entity;

import com.example.catalog.author.entity.Author;
import com.example.catalog.publisher.entity.Publisher;
import com.example.catalog.shelf.entity.Shelf;
import com.example.catalog.statustype.entity.StatusType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing book in the catalog.
 */
@Entity
@Table(name = "book")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Book {

  @Id
  @Column
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String title;

  @ManyToOne
  @JoinColumn
  private Author author;

  @ManyToOne
  @JoinColumn
  private Publisher publisher;

  @ManyToOne
  @JoinColumn
  private Shelf shelf;

  @ManyToOne
  @JoinColumn
  private StatusType statusType;
}
