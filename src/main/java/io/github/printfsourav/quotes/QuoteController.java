package io.github.printfsourav.quotes;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuoteController {
    public record Quote(int id, String text) {}

    private final List<Quote> quotes = List.of(
        new Quote(1, "Start small. Keep improving."),
        new Quote(2, "Build it, test it, then ship it."),
        new Quote(3, "A little progress every day adds up.")
    );

    @GetMapping("/quotes")
    public List<Quote> list() {
        return quotes;
    }

    @GetMapping("/quotes/random")
    public Quote random() {
        return quotes.get(ThreadLocalRandom.current().nextInt(quotes.size()));
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP");
    }
}
