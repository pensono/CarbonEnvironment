package org.carbon;

import org.carbon.compiler.CarbonExpression;
import org.carbon.compiler.Compiler;
import org.carbon.compiler.ParseException;
import org.carbon.library.BooleanExpression;
import org.carbon.library.GenericIntegerExpression;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    RootExpression rootExpression = new RootExpression();
        rootExpression.putMember("Boolean", new BooleanExpression(rootExpression));
        rootExpression.putMember("Integer", new GenericIntegerExpression(rootExpression));
        //Compile all carbon source files

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
                CarbonExpression expression = Compiler.compile(rootExpression, input);
            } catch (ParseException pe){
                System.out.flush();
                System.err.println(pe.getMessage());
                pe.printStackTrace(System.err);
                System.err.flush();
            }
        }
    }
}
