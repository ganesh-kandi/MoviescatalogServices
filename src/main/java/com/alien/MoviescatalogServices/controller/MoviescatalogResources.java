package com.alien.MoviescatalogServices.controller;

import com.alien.MoviescatalogServices.models.CatalogItems;
import com.alien.MoviescatalogServices.models.Movie;
import com.alien.MoviescatalogServices.models.UserCatalogs;
import com.alien.MoviescatalogServices.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.retry.Retry;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

@RestController
public class MoviescatalogResources {

    @Autowired
    RestTemplate restTemplate;
    private Integer count=0;

    @Autowired
    WebClient.Builder builder;

    public Retry<?> fixedretry= Retry.anyOf(WebClientException.class)
            .fixedBackoff(Duration.ofSeconds(5))
            .retryMax(3)
            .doOnRetry(e-> System.out.println("error in the external service"));

    @GetMapping("/getString")
    public String getStrings(){
        return "i am fine here";
    }
    @GetMapping("/{userId}")
    public List<CatalogItems> getCatalog(@PathVariable String userId){

        HashMap hashMap= new HashMap();
        UserRating  ratings= restTemplate.getForObject("http://RATINGDATASERVICE/movies/"+ userId, UserRating.class);

        /*UserRating  ratings=builder.build()
                .get()
                .uri("http://RATINGDATASERVICE/ratingdata/"+userId)
                .retrieve()
                .bodyToMono(UserRating.class)
                .block();*/

        return ratings.getRatings().stream().map(rating->{
            //restTemplate.getForObject("http://localhost:8081/movies/"+ rating.getMovieId(), Movie.class);
            Movie movie = restTemplate.getForObject("http://MOVIESINFO/movies/"+ rating.getMovieId(), Movie.class);

                    builder.build()
                    .get()
                    .uri("http://MOVIESINFO/movies/"+ rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();

            UserCatalogs userCatalogs=new UserCatalogs();
            List<CatalogItems> catalogItems=Arrays.asList(
                    new CatalogItems(movie.getName(),"desc",rating.getRating()));
            userCatalogs.setCatalogItems(catalogItems);
            return new CatalogItems(movie.getName(),"desc",rating.getRating());
        }).collect(Collectors.toList());
    }

    @GetMapping("/get")
    public String getString(){
        //AtomicInteger retryCount = new AtomicInteger(0);
        /*System.out.println("retry count of :"+ count++ +" at "+ new Date());
        return restTemplate.getForObject("http://localhost:8080/getString",String.class);*/

        /*        PriorityQueue priorityQueue=new PriorityQueue();
        priorityQueue.add("ganesh");
        priorityQueue.add("nava");*/

        return builder.build()
                .get()
                .uri("http://MOVIESINFO/movies/kalki")
                .retrieve()
                .bodyToMono(String.class)
                .block();

    }
}
//.retryWhen(reactor.util.retry.Retry.fixedDelay(5, Duration.ofSeconds(5))
//                        .filter(Throwable->Throwable instanceof ConnectException)
//                        .doBeforeRetry(retrySignal -> System.out.println("Retry attempt: "+  retryCount.incrementAndGet())))
//                .onErrorResume(e-> Mono.just("Service is temporarily unavailable. Please try again later."));