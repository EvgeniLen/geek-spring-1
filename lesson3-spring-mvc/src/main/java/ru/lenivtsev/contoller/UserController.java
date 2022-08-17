package ru.lenivtsev.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lenivtsev.persist.User;
import ru.lenivtsev.persist.UserRepository;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public String listPage(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user";
    }

    @GetMapping("/{id}")
    public String form(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userRepository.findById(id));
        return "user_form";
    }

    @GetMapping("/new_user")
    public String formNewUser() {
        return "user_form";
    }

    @PostMapping()
    public String saveUser(User user) {
        userRepository.update(user);
        return "redirect:/user";
    }

    @PostMapping("/add_user")
    public String addUser(@RequestParam ("username") String name) {
        if (!name.equals("")) {
            userRepository.insert(new User(name));
        }
        return "redirect:/user";
    }

    @GetMapping("/remove_user/{id}")
    public String addUser(@PathVariable("id") long id) {
        userRepository.delete(id);
        return "redirect:/user";
    }


}
