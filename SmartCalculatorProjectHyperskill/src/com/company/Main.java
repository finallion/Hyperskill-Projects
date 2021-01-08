package com.company;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static boolean exitCommand = false;
    private static boolean validExpression = true;
    private static Map<String, String> variables = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while(!exitCommand) {
            String input = scanner.nextLine();
            String inputWithoutWithspaces = input.replace(" ", "");
            evaluateInput(inputWithoutWithspaces);
        }

    }


    public static void evaluateInput(String input) {
        validExpression = true;
        Deque<String> postfixNotation = new ArrayDeque<>();

        if(input.isEmpty()) {
        } else if(input.startsWith("/")){
            evaluateCommands(input);
        } else if(input.contains("=")) {
            saveVariablesToMap(input);
        } else if(input.matches("[a-zA-Z]+")) {
            System.out.println(returnVariableValue(input));
        } else {
            postfixNotation = convertToPostfixNotation(analyzeOperators(input));
            if(validExpression) {
                System.out.println(calculate(postfixNotation));
            } else {
                System.out.println("Invalid expression");
            }
        }
    }




    public static int calculate(Deque<String> input) {
        Deque<Integer> finalResult = new ArrayDeque<>();
        Iterator<String> it = input.descendingIterator();

        int operationResult = 0;
        while(it.hasNext()){
            String expression = it.next();

            //numbers go directly on finalResult-stack
            if(expression.matches("[-+]?\\d+")) {
                finalResult.offer(Integer.valueOf(expression));
                //for each incoming operator, two numbers are drawn and calculated from finalResult-stack
            } else if(expression.matches("[*/+-]")) {

                int firstNumber = finalResult.pollLast();
                int secondNumber = finalResult.pollLast();

                if(expression.matches("\\+")) {
                    operationResult = secondNumber + firstNumber;
                } else if(expression.matches("-")) {
                    operationResult = secondNumber - firstNumber;
                } else if(expression.matches("\\*")) {
                    operationResult = secondNumber * firstNumber;
                } else if (expression.matches("/")) {
                    operationResult = secondNumber / firstNumber;
                } else {
                    System.out.println("Something went wrong here...");
                }

                finalResult.offer(operationResult);
            }
        }
        return finalResult.getFirst();
    }


    public static Deque<String> convertToPostfixNotation(String input) {
        List<String> expressions = new ArrayList<>();
        Deque<String> operators = new ArrayDeque<>();
        Deque<String> result = new ArrayDeque<>();

        Matcher matcher = Pattern.compile("\\w+|[-+*/()]").matcher(input);

        while(matcher.find()) {
            expressions.add(matcher.group());
        }

        try {
            for (String expression : expressions) {
                //filters variables and numbers
                //assigns values to variables
                if (expression.matches("\\w+")) {
                    if (expression.matches("\\d+")) {
                        result.push(expression);
                    } else {
                        result.push(variables.get(expression));
                    }
                    //operators
                } else {
                    //rule one: if stack is empty or has "(" on top, push operator on stack
                    if (operators.isEmpty() || operators.getFirst().equals("(")) {
                        operators.push(expression);
                    //rule two: if operator is ")", pop operator-stack to result-stack, until "(" is on top
                    //then discard the pair of brackets
                    } else if (expression.equals(")")) {
                        while (!operators.isEmpty()) {
                            if (operators.getFirst().equals("(")) {
                                break;
                            }
                            result.push(operators.pop());
                        }
                        operators.pop();
                    //rule three: if incoming operator has same or lower precedence than upmost operator
                    //pop operator-stack to result-stack until "(" or lower precedence-operator is on top
                    } else if (expression.equals(operators.getFirst()) ||
                            (expression.matches("[*/]") && operators.getFirst().matches("[*/]")) ||
                            (expression.matches("[+-]") && operators.getFirst().matches("[*/()+-]"))) {
                        //for operators "+" and "-" only "(" lower precedence
                        if (expression.matches("[+-]")) {
                            while (!operators.isEmpty()) {
                                if (operators.getFirst().equals("(")) {
                                    break;
                                }
                                result.push(operators.pop());
                            }
                            //for operator "*" and "/" all other operators
                        } else if (expression.matches("[*/]")) {
                            while (!operators.isEmpty()) {
                                if (operators.getFirst().matches("[()+-]")) {
                                    break;
                                }
                                result.push(operators.pop());
                            }
                        }
                        operators.push(expression);
                        //every other case gets pushed on operator-stack
                    } else {
                        operators.push(expression);
                    }
                }

            }
        } catch (Exception e) {
            validExpression = false;
        }

        //adds the operator-stack to result stack
        //if the operator-stack still contains bracket -> invalid input
        while (!operators.isEmpty()) {
            if(operators.peek().matches("[()]")) {
                validExpression = false;
                break;
            }
            result.push(operators.pop());
        }

        return result;
    }



    public static String analyzeOperators(String input) {
        String updatedInput = "";

        Matcher matcher = Pattern.compile("\\(*[+/*-]*\\w+\\)*|[+/*-]*\\(*[+/*-]*\\(*\\w+\\)*").matcher(input);
        while(matcher.find()) {
            updatedInput += removeUnnecessaryOperators(matcher.group());
        }
        return updatedInput;
    }


    public static String removeUnnecessaryOperators(String input) {
        String updatedInput = "";
        int countMinus = 0;

        if(input.contains("+")) {
            updatedInput = "+" + input.replace("+", "");
        } else if(input.contains("-")) {

            for(Character minus : input.toCharArray()) {
                if(minus == '-') {
                    countMinus++;
                }
            }

            if(countMinus % 2 == 0){
                updatedInput = "+" + input.replace("-", "");
            } else {
                updatedInput = "-" + input.replace("-", "");
            }

        } else if(input.matches(".*[/]{2,}.*") || input.matches(".*[*]{2,}.*")) {
            validExpression = false;
        } else {
            updatedInput = input;
        }

        return updatedInput;
    }

    public static String returnVariableValue(String input) {
        if(variables.get(input) != null) {
            return variables.get(input);
        }
        return "Unknown variable";
    }


    public static void saveVariablesToMap(String input) {
        String[] pieces = input.split("=");

        if(pieces.length > 2) {
            System.out.println("Invalid assignment");
            //handles cases like a=3 or a=-3 and new value assignment
        } else if(pieces[0].matches("[a-zA-Z]+") && pieces[1].matches("\\-?\\d+")) {
            variables.putIfAbsent(pieces[0], pieces[1]);
            variables.computeIfPresent(pieces[0], (k, v) -> v = pieces[1]);
            //handles cases like 3=a or -3=a and new value assignment
        } else if(pieces[1].matches("[a-zA-Z]+") && pieces[0].matches("\\-?\\d+")) {
            variables.putIfAbsent(pieces[1], pieces[0]);
            variables.computeIfPresent(pieces[1], (k, v) -> v = pieces[0]);
            //handles cases like a=b; if both have a value the first variable gets the value of the second variable
        } else if(pieces[0].matches("[a-zA-Z]+") && pieces[1].matches("[a-zA-Z]+")) {
            if(variables.containsKey(pieces[0]) && variables.containsKey(pieces[1])) {
                variables.computeIfPresent(pieces[0], (k, v) -> v = variables.get(pieces[1]));
            } else if(variables.containsKey(pieces[0])) {
                variables.put(pieces[1], variables.get(pieces[0]));
            } else if(variables.containsKey(pieces[1])) {
                variables.put(pieces[0], variables.get(pieces[1]));
            } else {
                System.out.println("Unknown variable");
            }
            //handles cases like a1=3 or a = 1a1
        } else {
            if(pieces[0].matches("\\d+")) {
                System.out.println("Invalid identifier");
            } else {
                System.out.println("Invalid assignment");
            }
        }
    }

    public static void evaluateCommands(String input) {
        if(input.equals("/exit")) {
            System.out.println("Bye!");
            exitCommand = true;
        } else if(input.equals("/help")) {
            System.out.println("This program calculates numbers, as you would expect.");
        } else {
            System.out.println("Unknown command");
        }

    }
}