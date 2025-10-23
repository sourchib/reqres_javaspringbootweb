package com.juaracoding.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UIController {


    @GetMapping("/{nama}")
    public String index(Model model, @PathVariable String nama){
        model.addAttribute("Nama ",nama);
        return "index";
    }


}
