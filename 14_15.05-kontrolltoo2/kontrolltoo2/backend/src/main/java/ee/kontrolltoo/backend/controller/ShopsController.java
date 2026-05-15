package ee.kontrolltoo.backend.controller;

import ee.kontrolltoo.backend.dto.TodoItem;
import org.springframework.http.HttpMethod;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/shops")
public class ShopsController {

RestTemplate restTemplate = new RestTemplate();

    @GetMapping
    public TodoItem[] getBooks() {
        String url = "https://jsonplaceholder.typicode.com/todos";
        return restTemplate.exchange(url, HttpMethod.GET, null, TodoItem[].class).getBody();
    }
}
