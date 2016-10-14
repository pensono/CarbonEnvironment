package org.carbon;

import org.carbon.compiler.CarbonExpression;
import org.carbon.compiler.Compiler;
import org.carbon.library.BooleanExpression;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    RootExpression rootExpression = new RootExpression();
        rootExpression.putMember("Boolean", new BooleanExpression(rootExpression));
        //Compile all carbon source files

        //Start REPL for CoW
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();
            CarbonExpression expression = Compiler.compile(rootExpression, input);
//            System.out.println(expression.getPrettyString());
        }
    }
}
