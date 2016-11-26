/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.forwardit.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author mauri
 */
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank
    private String title;
    @Lob
    @NotBlank
    private String description;
    @Min(30)
    private int pages;
    @ElementCollection
    private List<Price> prices = new ArrayList<>();
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Calendar releaseDate;
    private String summaryPath;

    public String getSummaryPath() {
        return summaryPath;
    }

    public void setSummaryPath(String summaryPath) {
        this.summaryPath = summaryPath;
    }

    public Calendar getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Calendar releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the pages
     */
    public int getPages() {
        return pages;
    }

    /**
     * @param pages the pages to set
     */
    public void setPages(int pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "Titulo: " + title + " Descição: " + description + " tem " + pages + " Paginas";
    }

    public BigDecimal priceFor(BookType bookType) {
        return prices
                .stream()
                .filter(price -> price.getBookType().equals(bookType))
                .findFirst().get().getValue();
    }
}
