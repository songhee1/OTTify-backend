package tavebalak.OTTify.program.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OpenApiProgram {
    private boolean adult;
    private String backdrop_path;
    private Long id;
    private String title;
    private String original_language;
    private String original_title;
    private String overview;
    private String poster_path;
    private String media_type;
    private List<Long> genre_ids=new ArrayList<>();
    private double popularity;
    private String release_date;
    private boolean video;
    private double vote_average;
    private int vote_count;

    private String name;
    private String original_name;
    private String first_air_date;
    private List<String> origin_country=new ArrayList<>();



}
