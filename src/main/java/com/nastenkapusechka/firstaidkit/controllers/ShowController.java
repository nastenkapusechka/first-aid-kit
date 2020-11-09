package com.nastenkapusechka.firstaidkit.controllers;

import com.google.common.base.Preconditions;
import com.nastenkapusechka.firstaidkit.models.Pill;
import com.nastenkapusechka.firstaidkit.repo.PillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

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

    @PostMapping("/add")
    public String add(@RequestParam String name, @RequestParam String description,
                      @RequestParam String form, @RequestParam String date, Model model) {

        Date goodDate;

        try{
            Preconditions.checkArgument(!date.equals(""));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            goodDate = format.parse(date);
        } catch (IllegalArgumentException | ParseException e) {
            goodDate = new Date();
        }

        Pill pill = new Pill(name, description, form, goodDate);
        repository.save(pill);

        return "redirect:/";
    }

    @GetMapping("/add/form")
    public String addForm(Model model) {
        return "add";
    }

    @GetMapping("/show/{id}")
    public String details(@PathVariable(value = "id") long id, Model model) {
        if (!repository.existsById(id)) {
            return "redirect:/";
        }
        Optional<Pill> pill = repository.findById(id);
        ArrayList<Pill> pills = new ArrayList<>();
        pill.ifPresent(pills :: add);
        model.addAttribute("pills", pills);
        return "details";
    }
}
