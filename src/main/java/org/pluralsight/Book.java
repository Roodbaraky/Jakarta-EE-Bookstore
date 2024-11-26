package org.pluralsight;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbNumberFormat;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Entity
@Table(name = "T_BOOK")
@Schema(name = "Book", description = "POJO that represents a book")
//POJO = Plain Old Java Object (lol)
public class Book {
    @Id
    @GeneratedValue
    private Long id;
    @Column(length = 200)
    @NotNull
    @Size(min = 1, max = 200)
    private String title;
    @Column(length = 50)
    private String isbn;
    @Column(length = 10000)
    @Size(min = 10, max = 10000)
    private String description;
    @Min(1)
    @JsonbNumberFormat(value = "$#0.00")
    private BigDecimal price;
    @Column(name = "publication_date")
    @Past
    @JsonbDateFormat(value = "dd-MM-yyyy")
    @JsonbProperty("publication-date")
    private LocalDate publicationDate;
    @Column(name = "nb_of_pages")
    @JsonbProperty("nb-of-pages")

    private Integer nbOfPages;
    @Column(name = "image_url")
    @JsonbTransient
    private String imageUrl;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(final String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(final LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Integer getNbOfPages() {
        return nbOfPages;
    }

    public void setNbOfPages(final Integer nbOfPages) {
        this.nbOfPages = nbOfPages;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
