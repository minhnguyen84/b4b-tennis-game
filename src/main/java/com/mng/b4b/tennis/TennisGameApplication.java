package com.mng.b4b.tennis;

import com.mng.b4b.tennis.service.CommandLinePrintService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;
import java.util.Scanner;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class TennisGameApplication implements CommandLineRunner{

	private final CommandLinePrintService commandLinePrintService;

	public static void main(String[] args) {
		SpringApplication.run(TennisGameApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("----------------------------");
			System.out.println("X - to exit");
			System.out.println("Or enter a new game results");
			System.out.println("--------");

			String input = scanner.nextLine().toUpperCase(Locale.ROOT);

            if (input.equals("X")) {
                System.out.println("Exiting application...");
                scanner.close();
                return;
            } else {
				commandLinePrintService.decodeAndPrint(input).forEach(System.out::println);
            }
		}
	}

}
