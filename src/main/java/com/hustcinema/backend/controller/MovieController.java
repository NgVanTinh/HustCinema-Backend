package com.hustcinema.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hustcinema.backend.model.Movie;
import com.hustcinema.backend.service.MovieService;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping("/")
    Movie newMovie(@RequestBody Movie newMovie) {
        return movieService.createNewMovie(newMovie);
    }

    @GetMapping("/")
    List<Movie> getAllMovie() {
        return movieService.findAllMovie();
    }

    @GetMapping("/{id}")
    Movie getMovieById(@PathVariable String id) {
        return movieService.findMovieById(id);
    }

    @GetMapping("/search/{name}")
    List<Movie> findMoviesByNameLike(@PathVariable String name) {
        return movieService.findMoviesByNameLike(name);
    }
    
    @PutMapping("/{id}")
    Movie updateMovie(@RequestBody Movie newMovie, @PathVariable String id) {
        return movieService.updateMovie(newMovie, id);
    }

    @DeleteMapping("/{id}")
    String deleteMovieById(@PathVariable String id) {    
        return movieService.deleteMovieById(id);
    }

}
