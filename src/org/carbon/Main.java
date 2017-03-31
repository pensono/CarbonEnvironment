package org.carbon;

import org.carbon.compiler.*;
import org.carbon.compiler.Compiler;
import org.carbon.library.BooleanExpression;
import org.carbon.library.GenericIntegerExpression;
import sun.misc.IOUtils;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        Compiler compiler = new Compiler();
	    RootExpression rootExpression = new RootExpression();
        rootExpression.putMember("Boolean", new BooleanExpression(rootExpression));
        rootExpression.putMember("Integer", new GenericIntegerExpression(rootExpression));

        //Compile all carbon source files
        System.out.println("Searching for source within " + Paths.get("res").toAbsolutePath());
        try(Stream<Path> paths = Files.walk(Paths.get("res").toAbsolutePath())){
            paths.filter(path -> path.toString().endsWith(".cbn")).forEach(file -> {
                try {
                    System.out.println("Compiling " + file);
                    CarbonExpression expression = compiler.compile(rootExpression, new String(Files.readAllBytes(file)));
                    expression = expression.reduce();
                    System.out.println(expression.getPrettyString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ioe){
            ioe.printStackTrace();
        }

        //Start REPL for CoW
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();

            if (input.equals("quit")){
                break;
            } else if (input.isEmpty()){
                continue;
            }

            try {
                CarbonExpression expression = compiler.compile(rootExpression, input);
                System.out.println(expression.getPrettyString());
            } catch (CarbonException pe){
                System.out.flush();
                System.err.println(pe.getMessage());
                pe.printStackTrace(System.err);
                System.err.flush();
            }
        }
    }
}
