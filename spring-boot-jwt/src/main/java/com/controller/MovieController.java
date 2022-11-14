package com.controller;

import com.dto.MovieDto;
import com.dto.ResponseDto;
import com.service.MovieService;
import com.util.VarList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="api/v1/movie")
@CrossOrigin
public class MovieController {

    @Autowired
    public MovieService movieService;

    @Autowired
    private ResponseDto responseDto;

    @GetMapping("/")
    public List<MovieDto> getMovies(){
        return movieService.getAllMovies();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMovie(@RequestBody MovieDto movieDTO){
        String response = movieService.addMovie(movieDTO);
        if(response=="00"){
            responseDto.setCode(VarList.RSP_SUCCESS);
            responseDto.setMessage("Success");
            responseDto.setContent(null);
            return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
        }
        else if(response=="04"){
            responseDto.setCode(VarList.RSP_DUPLICATED);
            responseDto.setMessage("Movie Already Exists");
            responseDto.setContent(null);
            return new ResponseEntity<>(responseDto, HttpStatus.ALREADY_REPORTED);
        }
        else{
            responseDto.setCode(VarList.RSP_BAD_REQUEST);
            responseDto.setMessage("Bad Request");
            responseDto.setContent(null);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{Imdb}")
    public MovieDto getMovieByImdb(@PathVariable String Imdb){
        return movieService.getMovieByImdb(Imdb);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateMovie(@RequestBody MovieDto movieDTO){
        String response = movieService.updateMovie(movieDTO);
        if(response=="00"){
            responseDto.setCode(VarList.RSP_SUCCESS);
            responseDto.setMessage("Success");
            responseDto.setContent(null);
            return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
        } else if (response=="02") {
            responseDto.setCode(VarList.RSP_NO_DATA_FOUND);
            responseDto.setMessage("No such movie exists");
            responseDto.setContent(null);
            return new ResponseEntity<>(responseDto, HttpStatus.NO_CONTENT);
        }else{
            responseDto.setCode(VarList.RSP_BAD_REQUEST);
            responseDto.setMessage("Bad Request");
            responseDto.setContent(null);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{Imdb}")
    public ResponseEntity<?> deleteMovie(@PathVariable String Imdb){
        String response = movieService.deleteMovie(Imdb);
        if(response=="00"){
            responseDto.setCode(VarList.RSP_SUCCESS);
            responseDto.setMessage("Success");
            responseDto.setContent(null);
            return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
        }
        else if(response=="04"){
            responseDto.setCode(VarList.RSP_NO_DATA_FOUND);
            responseDto.setMessage("No Such Movie Exists");
            responseDto.setContent(null);
            return new ResponseEntity<>(responseDto, HttpStatus.ALREADY_REPORTED);
        }
        else{
            responseDto.setCode(VarList.RSP_BAD_REQUEST);
            responseDto.setMessage("Bad Request");
            responseDto.setContent(null);
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }
}
