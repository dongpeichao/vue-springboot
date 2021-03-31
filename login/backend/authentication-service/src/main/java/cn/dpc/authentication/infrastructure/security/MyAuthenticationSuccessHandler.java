package cn.dpc.authentication.infrastructure.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MyAuthenticationSuccessHandler extends RedirectServerAuthenticationSuccessHandler {

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        ServerHttpRequest request = webFilterExchange.getExchange().getRequest();

        response.setStatusCode(HttpStatus.OK);

        try {
            byte[] bytes = new ObjectMapper().writeValueAsString(authentication).getBytes();
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return webFilterExchange.getExchange().getSession()
                    .flatMap(webSession -> {
//                        response.getHeaders().add("SESSION", webSession.getId());
                        return response.writeWith(Flux.just(buffer));
                    });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return Mono.empty();
    }
}
