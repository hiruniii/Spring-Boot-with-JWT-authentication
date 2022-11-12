package com.service;

import com.dto.MovieDto;
import com.entity.Movie;
import com.repository.MovieRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MovieService {

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private ModelMapper modelMapper;

    public MovieDto addMovie(MovieDto movieDTO){
        movieRepo.save(modelMapper.map(movieDTO, Movie.class));
        return movieDTO;
    }

    public List<MovieDto> getAllMovies(){
        List<Movie>movieList = movieRepo.findAll();
        return modelMapper.map(movieList, new TypeToken<List<MovieDto>>(){}.getType());
    }

    public MovieDto getMovieByImdb(String Imdb){
        Movie movie = movieRepo.getMovieByImdb(Imdb);
        return modelMapper.map(movie, MovieDto.class);
    }

    public MovieDto updateMovie(MovieDto movieDTO){
        movieRepo.save(modelMapper.map(movieDTO, Movie.class));
        return movieDTO;
    }

    public void deleteMovie(String Imdb){
        movieRepo.deleteMovie(Imdb);
        return;
    }
}
