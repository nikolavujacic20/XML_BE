package com.example.backend.service;

import com.example.backend.resenje.ResenjeZahteva;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {


    private final Environment env;


}