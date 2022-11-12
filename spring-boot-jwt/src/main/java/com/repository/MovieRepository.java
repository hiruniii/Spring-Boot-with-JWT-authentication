package com.repository;

import com.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MovieRepository extends JpaRepository<Movie, String> {

    @Query(value="SELECT * FROM Movie WHERE imdb = ?1", nativeQuery = true)
    Movie getMovieByImdb(String Imdb);

    @Modifying
    @Query(value="DELETE FROM Movie WHERE imdb = ?1", nativeQuery = true)
    void deleteMovie(String Imdb);
}