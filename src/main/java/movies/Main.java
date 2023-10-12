package movies;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.net.SocketOption;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws IOException {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://raw.githubusercontent.com/prust/wikipedia-movie-data/master/movies.json/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        MovieService movieService = retrofit.create(MovieService.class);
        Call<List<MovieDTO>> call = movieService.getMovies();
        List<MovieDTO> movies = call.execute().body();
        Optional<MovieDTO> movieWithMostActors = movies.stream()
                .filter(movie -> movie.getYear() < 2000)
                .max((movie1,movie2) -> Integer.compare(movie1.getCast(), movie2.getCast()));
        if (movieWithMostActors.isPresent()){
            MovieDTO mostActorsMovie = movieWithMostActors.get();
            System.out.println(mostActorsMovie.getTitle());
            System.out.println(mostActorsMovie.getCast());
        }
        else{
            System.out.println("Err");
        }
    }
}
