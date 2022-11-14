package com.service;

import com.dto.MovieDto;
import com.entity.Movie;
import com.repository.MovieRepository;
import com.util.VarList;
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

    public String addMovie(MovieDto movieDTO){
        if(movieRepo.existsByImdb(movieDTO.getImdb())){
            return VarList.RSP_DUPLICATED;
        }
        else{
            movieRepo.save(modelMapper.map(movieDTO, Movie.class));
            return VarList.RSP_SUCCESS;
        }
    }

    public List<MovieDto> getAllMovies(){
        List<Movie>movieList = movieRepo.findAll();
        return modelMapper.map(movieList, new TypeToken<List<MovieDto>>(){}.getType());
    }

    public MovieDto getMovieByImdb(String Imdb){
        Movie movie = movieRepo.getMovieByImdb(Imdb);
        return modelMapper.map(movie, MovieDto.class);
    }

    public String updateMovie(MovieDto movieDTO){
        if(!movieRepo.existsByImdb(movieDTO.getImdb())){
            return VarList.RSP_NO_DATA_FOUND;
        }
        else{
            movieRepo.save(modelMapper.map(movieDTO, Movie.class));
            return VarList.RSP_SUCCESS;
        }
    }

    public String deleteMovie(String Imdb){
        if(!movieRepo.existsByImdb(Imdb)){
            return VarList.RSP_NO_DATA_FOUND;
        }
        else{
            movieRepo.deleteMovie(Imdb);
            return VarList.RSP_SUCCESS;
        }
    }
}
