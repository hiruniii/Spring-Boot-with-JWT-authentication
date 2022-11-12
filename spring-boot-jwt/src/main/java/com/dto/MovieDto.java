package com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieDto {
    private String imdb;
    private String title;
    private String description;
    private float rating;
    private String category;
    private Year year;
    private String imageUrl;

}
