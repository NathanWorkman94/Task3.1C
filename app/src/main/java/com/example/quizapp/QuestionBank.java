package com.example.quizapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuestionBank {

    public static List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();

        // Add questions to the list
        questions.add(new Question(
                "In what year did streaming-only movies become eligible for the Oscars?",
                Arrays.asList("2012", "2016", "2020"),
                2
        ));

        questions.add(new Question(
                "Who plays Paul Atreides in the original Dune film?",
                Arrays.asList("Timothee Chalamet", "Kyle MachLachlan", "James McAvoy"),
                1
        ));

        questions.add(new Question(
                "Who is the narrator in the classic film Stand By Me?",
                Arrays.asList("Stephen King", "Richard Dreyfuss", "Alec Baldwin"),
                1
        ));

        questions.add(new Question(
                "What movie franchise parodies horror films?",
                Arrays.asList("Halloween", "Nightmare on Elm Street", "Scary Movie"),
                2
        ));

        questions.add(new Question(
                "What film was released in 2021 and directed by Denis Villeneuve?",
                Arrays.asList("Dune Part 1", "Tenet", "Dune Part 2"),
                0
        ));

        questions.add(new Question(
                "Who won the Academy Award for Best Actor for his role in the film There Will Be Blood?",
                Arrays.asList("Daniel Day-Lewis", "Anthony Hopkins", "Paul Dano"),
                0
        ));

        // Shuffle questions so they're random each time
        Collections.shuffle(questions);

        return questions;
    }
}
