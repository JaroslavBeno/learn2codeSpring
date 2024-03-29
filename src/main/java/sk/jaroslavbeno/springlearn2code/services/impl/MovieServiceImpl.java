package sk.jaroslavbeno.springlearn2code.services.impl;

import org.springframework.stereotype.Service;
import sk.jaroslavbeno.springlearn2code.mappers.MovieMapper;
import sk.jaroslavbeno.springlearn2code.model.Movie;
import sk.jaroslavbeno.springlearn2code.model.dto.MovieDTO;
import sk.jaroslavbeno.springlearn2code.repositories.MovieRepository;
import sk.jaroslavbeno.springlearn2code.services.MovieService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    MovieRepository movieRepository;
    MovieMapper movieMapper;

    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    @Override
    public Movie createAndAddMovie() {

        Movie movie = new Movie();
        movie.setName("Fireproof");

        return movieRepository.save(movie);
    }

    @Override
    public Movie createAndAddMovie2() {

        Movie movie = new Movie();
        movie.setName("Quest");

        return movieRepository.save(movie);
    }

    @Override
    public MovieDTO getMovieById(Long id) {
        return movieMapper.movieToMovieDto(
                movieRepository.findById(id).orElse(null));
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(movieMapper::movieToMovieDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDTO> findMovieByName(String name) {
        return movieRepository.findByName(name).stream()
                        .map(movieMapper::movieToMovieDto)
                        .collect(Collectors.toList());
    }

    @Override
    public MovieDTO addMovie(MovieDTO movieDTO) {
        return movieMapper.movieToMovieDto(
                movieRepository.save(movieMapper.movieDtoToMovie(movieDTO))
        );
    }

    @Override
    public MovieDTO updateMovie(MovieDTO movieDTO, long movieId) {
        return movieMapper.movieToMovieDto(movieRepository.findById(movieId)
                .map(movie ->{
                        movie.setName(movieDTO.getName());
                        movie.setDirectors(movieDTO.getDirectorsOfTheMovie());
                        return movieRepository.save(movie);
                    }
                ).orElseGet( () -> {
//                    movieDTO.setId(movieId);
                    return movieRepository.save(movieMapper.movieDtoToMovie(movieDTO));
                    }
                )
        );
    }

    @Override
    public void deleteMovie(long id) {
        movieRepository.deleteById(id);
    }

}
