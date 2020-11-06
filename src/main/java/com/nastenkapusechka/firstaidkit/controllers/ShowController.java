package com.nastenkapusechka.firstaidkit.controllers;

import com.nastenkapusechka.firstaidkit.models.Pill;
import com.nastenkapusechka.firstaidkit.repo.PillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShowController {

    @Autowired
    private PillRepository repository;

    @GetMapping("/show")
    public String showList(Model model) {
        Iterable<Pill> pills = repository.findAll();
        model.addAttribute("pills", pills);
        return "show";
    }
}
