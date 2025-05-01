package com.studyflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.*;


// Just an example test case to try out spring
@SpringBootApplication
public class SpringDemo {
    public static void main(String[] args) {
        SpringApplication.run(SpringDemo.class, args);
        System.out.println("SUPABASE_API_KEY = " + System.getenv("SUPABASE_API_KEY"));
    }
}
