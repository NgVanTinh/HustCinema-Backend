package com.hustcinema.backend.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Movie {

    @Id

    @GeneratedValue(strategy = GenerationType.UUID)

    private String id;
    private String actors;
    private String categories;
    private String directors;
    private String movieName;
    private String description;
    private Long length;
    private LocalDate releaseDate;
    private String imgURL;
    private String posterURL;
    private String trailerURL;

    
    public String getActors() {
        return actors;
    }
    public void setActors(String actors) {
        this.actors = actors;
    }
    public String getCategories() {
        return categories;
    }
    public void setCategories(String categories) {
        this.categories = categories;
    }
    public String getDirectors() {
        return directors;
    }
    public void setDirectors(String directors) {
        this.directors = directors;
    }
    public String getMovieName() {
        return movieName;
    }
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Long getLength() {
        return length;
    }
    public void setLength(Long length) {
        this.length = length;
    }
    public LocalDate getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
    public String getImgURL() {
        return imgURL;
    }
    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
    public String getPosterURL() {
        return posterURL;
    }
    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }
    public String getTrailerURL() {
        return trailerURL;
    }
    public void setTrailerURL(String trailerURL) {
        this.trailerURL = trailerURL;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
   
    

    
}
