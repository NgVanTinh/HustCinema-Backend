package com.hustcinema.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.hustcinema.backend.model.Movie;
import com.hustcinema.backend.model.Schedule;
import com.hustcinema.backend.repository.MovieRepository;
import com.hustcinema.backend.repository.ScheduleRepository;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    // @PreAuthorize("hasRole('ADMIN')")
    public Movie createNewMovie(Movie newMovie) {
        // Kiểm tra trùng lặp tên phim
        if (movieRepository.existsByMovieName(newMovie.getMovieName())) {
            throw new RuntimeException( newMovie.getMovieName() + " already exist!");
        }
        return movieRepository.save(newMovie);
    }

    public List<Movie> findAllMovie() {
        return movieRepository.findAll();
    }

    public Movie findMovieById(String id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie with id = " + id + " not found!"));
    }

    public List<Movie> findMoviesByNameLike(String movieName){
        return movieRepository.findByMovieNameLike(movieName);
    }

    // @PreAuthorize("hasRole('ADMIN')")
    public Movie updateMovie(Movie newMovie, String id) {
        return movieRepository.findById(id)
                .map(movie -> {
                    movie.setActors(newMovie.getActors());
                    movie.setCategories(newMovie.getCategories());
                    movie.setDirectors(newMovie.getDirectors());
                    movie.setMovieName(newMovie.getMovieName()); 
                    movie.setDescription(newMovie.getDescription());
                    movie.setLength(newMovie.getLength());
                    movie.setReleaseDate(newMovie.getReleaseDate());
                    movie.setImgURL(newMovie.getImgURL());
                    movie.setPosterURL(newMovie.getPosterURL());
                    movie.setTrailerURL(newMovie.getTrailerURL());

                    return movieRepository.save(movie);
                }).orElseThrow(() -> new RuntimeException(newMovie.getMovieName() + " already exist!"));
    }

    // @PreAuthorize("hasRole('ADMIN')")
    public String deleteMovieById(String id) {
        if (!movieRepository.existsById(id)) {
            throw new RuntimeException("Movie with id = " + id + " not found!");
        }
        List<Schedule> schedules = scheduleRepository.findByMovieId(id);
        for (Schedule schedule : schedules) {
            scheduleRepository.deleteById(schedule.getId());
        }
        movieRepository.deleteById(id);
        return "Movie with id = " + id + " has been deleted successfully! Schedule which show the movie has been deleted";
    }
}
