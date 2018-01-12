package org.carbon;

import org.carbon.compiler.*;
import org.carbon.compiler.Compiler;
import org.carbon.library.CarbonLibrary;
import org.carbon.runtime.CarbonExpression;
import org.carbon.runtime.RootScope;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        RootScope rootScope = loadCarbonEnvironment();

        //Start REPL for CoW
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine().trim();

            if (input.equals("quit")){
                break;
            } else if (input.equals("reload")) {
                rootScope = loadCarbonEnvironment();
                continue;
            } else if (input.isEmpty()){
                continue;
            }

            try {
                if (input.endsWith(";")) {
                    Compiler.compileStatementsInto(rootScope, input);
                } else {
                    CarbonExpression expression = Compiler.compileExpression(rootScope, input);

                    System.out.println(expression.getFullString());
                }
            } catch (Exception e){
                handleError(e);
            }
        }
    }

    private static void handleError(Exception e){
        System.out.flush();

        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) { }

        e.printStackTrace(System.err);
        System.err.flush();

        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) { }
    }

    private static RootScope loadCarbonEnvironment() {
        RootScope rootScope = new CarbonLibrary();

        loadTestSources(rootScope);
        return rootScope;
    }

    private static void loadTestSources(RootScope rootScope){
        System.out.println("Searching for source within " + Paths.get("res").toAbsolutePath());
        try(Stream<Path> paths = Files.walk(Paths.get("res").toAbsolutePath())){
            paths.filter(path -> path.toString().endsWith(".cbn")).forEach(file -> {
                try {
                    System.out.println("Compiling " + file);
                    Compiler.compileStatementsInto(rootScope, new String(Files.readAllBytes(file)));
                } catch (IOException | CarbonException e) {
                    handleError(e);
                }
            });
        } catch (IOException ioe){
            handleError(ioe);
        }
    }
}
