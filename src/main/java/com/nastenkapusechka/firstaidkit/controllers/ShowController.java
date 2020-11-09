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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public String addForm() {
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

    @GetMapping("/show/{id}/edit")
    public String edit(@PathVariable(value = "id") long id, Model model) {
        if (!repository.existsById(id)) {
            return "redirect:/";
        }
        Optional<Pill> pill = repository.findById(id);
        ArrayList<Pill> pills = new ArrayList<>();
        pill.ifPresent(pills :: add);
        model.addAttribute("pills", pills);
        return "edit";
    }

    @PostMapping("/show/{id}/edit")
    public String update(@PathVariable(value = "id") long id,
                         @RequestParam String name,
                         @RequestParam String description,
                         @RequestParam String form,
                         @RequestParam String date,
                         Model model) {
        Pill pill = repository.findById(id).orElseThrow();
        pill.setDescription(description);
        pill.setName(name);
        pill.setForm(form);

        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            Date newDate = format.parse(date);
            pill.setDate(newDate);
        } catch (ParseException ignore) {}

        repository.save(pill);
        return "redirect:/show";
    }

    @PostMapping("/show/{id}/remove")
    public String remove(@PathVariable(value = "id") long id, Model model) {
        Pill pill = repository.findById(id).orElseThrow();
        repository.delete(pill);
        return "redirect:/show";
    }

    @GetMapping("/search")
    public String search(){
        return "search";
    }

    @PostMapping("/search")
    public String searchResult(@RequestParam String name, Model model){

        List<Pill> list = new ArrayList<>();
        repository.findAll().forEach(list :: add);
        List<Pill> res = list.stream()
                .filter(p -> p.getName().equals(name))
                .collect(Collectors.toList());
        model.addAttribute("pills", res);
        return "show";
    }
}

