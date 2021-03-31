package cn.dpc.authentication.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/")
public class IndexApi {

    @GetMapping
    public Mono<String> hello() {
        return Mono.just("Hello world!");
    }

    @PostMapping("/register")
    public Mono<Void> register() {
        return Mono.empty();
    }
}
