package unq.pdes._5.g1.segui_tus_compras.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

    @GetMapping("/register")
    public String register() {
        return "Hello Users!";
    }

    @GetMapping("/login")
    public String login() {
        return "Hello Users!";
    }

    @GetMapping("/")
    public String getAllUsers() {
        return "Hello Users!";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable String id) {
        return "TODO: devolver usuario " + id;
    }

}

