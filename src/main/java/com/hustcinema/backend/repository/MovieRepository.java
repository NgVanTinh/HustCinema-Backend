package com.hustcinema.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hustcinema.backend.model.Movie;


public interface MovieRepository extends JpaRepository<Movie,String> {

    boolean existsByMovieName(String movieName);
    
    @Query("SELECT m FROM Movie m WHERE m.movieName LIKE %?1%")
    List<Movie> findByMovieNameLike(String movieName);

}
