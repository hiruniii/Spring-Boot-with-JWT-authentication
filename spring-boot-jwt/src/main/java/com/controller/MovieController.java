package com.controller;

import com.dto.MovieDto;
import com.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="api/v1/movie")
@CrossOrigin
public class MovieController {

    @Autowired
    public MovieService movieService;

    @GetMapping("/")
    public List<MovieDto> getMovies(){
        return movieService.getAllMovies();
    }

    @PostMapping("/add")
    public MovieDto addMovie(@RequestBody MovieDto movieDTO){
        return movieService.addMovie(movieDTO);
    }

    @GetMapping("/{Imdb}")
    public MovieDto getMovieByImdb(@PathVariable String Imdb){
        return movieService.getMovieByImdb(Imdb);
    }

    @PutMapping("/update")
    public MovieDto updateMovie(@RequestBody MovieDto movieDTO){
        return movieService.updateMovie(movieDTO);
    }

    @DeleteMapping("/delete/{Imdb}")
    public void deleteMovie(@PathVariable String Imdb){
        movieService.deleteMovie(Imdb);
        return;
    }
}
