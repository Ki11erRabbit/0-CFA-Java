package cfa;

import ast.ExpressionNode;
import cfa.fact.*;
import utils.SubscriptPrinter;

import java.util.*;

public class Cfa {
    private int nextPoint = 0;
    List<ProgramPoint> programPoints = new ArrayList<>();
    List<ProgramPoint> valuePoints = new ArrayList<>();
    Map<ExpressionNode, Integer> pointToInt = new HashMap<>();
    Set<Fact> facts = new HashSet<>();
    Set<ExpressionNode>[] sets;

    public Cfa() {

    }

    private int getNextPoint() {
        return nextPoint++;
    }

    private void addPoint(ExpressionNode tree) {
        ProgramPoint point = new ProgramPoint(ProgramPoint.Type.POINT, getNextPoint(), tree);
        programPoints.add(point);
        pointToInt.put(tree, point.getIndex());
    }

    private void addValue(ExpressionNode tree) {
        ProgramPoint point = new ProgramPoint(ProgramPoint.Type.VALUE, getNextPoint(), tree);
        programPoints.add(point);
        valuePoints.add(point);
        pointToInt.put(tree, point.getIndex());
    }

    public void loadPoints(ExpressionNode tree) {
        if (tree instanceof ast.Application app) {
            addPoint(tree);
            loadPoints(app.getFunction());
            loadPoints(app.getArgument());
        } else if (tree instanceof ast.Paren paren) {
            loadPoints(paren.getExpression());
        } else if (tree instanceof ast.Number) {
            addValue(tree);
        } else if (tree instanceof ast.Lambda lambda) {
            addValue(tree);
            loadPoints(lambda.getParameter());
            loadPoints(lambda.getBody());
        } else if (tree instanceof ast.BinaryOp bin) {
            addPoint(tree);
            loadPoints(bin.getLeft());
            loadPoints(bin.getRight());
        } else if (tree instanceof  ast.Variable) {
            addPoint(tree);
        }
    }

    public void initializeFacts(ExpressionNode node) {
        initializeFacts(node, -1);
    }

    private void initializeFacts(ExpressionNode node, int varIndex) {
        int index = pointToInt.get(node);
        if (node instanceof ast.Number) {
            Substitution sub = new SubstitutionSimple(index);
            Value set = new ValueSet(node);
            Fact fact = new Fact(set, sub);
            facts.add(fact);
        } else if (node instanceof ast.Paren paren) {
            Substitution sub = new SubstitutionSimple(index);
            Value set = new ValueSet(paren.getExpression());
            Fact fact = new Fact(set, sub);
            facts.add(fact);
            initializeFacts(paren.getExpression(), varIndex);
        } else if (node instanceof ast.Variable) {
            Substitution sub = new SubstitutionSimple(index);
            Value src = new ValueSource(varIndex);
            Fact fact = new Fact(src, sub);
            facts.add(fact);
        } else if (node instanceof ast.Lambda lambda) {
            Substitution sub = new SubstitutionSimple(index);
            Value set = new ValueSet(node);
            Fact fact = new Fact(set, sub);
            facts.add(fact);

            varIndex = pointToInt.get(lambda.getParameter());
            initializeFacts(lambda.getBody(), varIndex);
        } else if (node instanceof ast.BinaryOp bin) {
            Substitution sub = new SubstitutionSimple(index);
            Value set = new ValueSet(node);
            Fact fact = new Fact(set, sub);
            facts.add(fact);

            initializeFacts(bin.getLeft(), varIndex);
            initializeFacts(bin.getRight(), varIndex);
        } else if (node instanceof ast.Application app) {
            {
                Substitution sub = new SubstitutionSimple(index);
                Value set = new ValueSet(node);
                Fact fact = new Fact(set, sub);
                facts.add(fact);
            }
            ExpressionNode fun = app.getFunction();
            int funIndex = pointToInt.get(fun);
            ExpressionNode arg = app.getArgument();
            int argIndex = pointToInt.get(arg);
            Substitution application = new SubstitutionMapping(new SubstitutionSimple(argIndex), new SubstitutionSimple(index));
            Value src = new ValueSource(funIndex);

            Fact fact = new Fact(src, application);
            facts.add(fact);
            initializeFacts(fun, varIndex);
            initializeFacts(arg, varIndex);
        }
    }

    public void solve() {
        sets = new Set[this.nextPoint];
        for (int i = 0; i < sets.length; i++) {
            sets[i] = new HashSet<>();
        }
        boolean noChanges = true;
        int pass = 0;
        do {
            System.out.println("Pass " + pass + ": ");
            displayState();
            noChanges = true;
            for (Fact fact : new HashSet<>(facts)) {
                noChanges = noChanges && !applyFact(fact);
            }
            pass++;
        } while (!noChanges);
        System.out.println("Final Result:");
        displayState();

        boolean allFilled = true;
        for (Set<ExpressionNode> set : sets) {
            allFilled = allFilled && !set.isEmpty();
            if (!allFilled) {
                System.out.println("There is an empty set");
                return;
            }
        }
        if (sets[0].size() > 1) {
            System.out.println("There is a solution");
        } else {
            System.out.println("There wasn't a solution");
        }
    }

    private boolean applyFact(Fact fact) {
        Substitution sub = fact.getSubstitution();
        Value value = fact.getValues();
        boolean anyUpdates = false;

        if (sub instanceof SubstitutionSimple simple) {
            int index = simple.getIndex();
            if (value instanceof ValueSet set) {
                boolean alreadyContains = true;
                for (ExpressionNode node : set.getSet()) {
                    boolean contains = sets[index].contains(node);
                    alreadyContains = alreadyContains && contains;
                    if (!contains) {
                        sets[index].add(node);
                    }
                }
                if (!alreadyContains) {
                    anyUpdates = true;
                }
            } else if (value instanceof ValueSource src) {
                int srcIndex = src.getIndex();

                if (!sets[index].contains(programPoints.get(srcIndex).getNode())) {
                    anyUpdates = true;
                    sets[index].add(programPoints.get(srcIndex).getNode());
                } else {
                    for (ExpressionNode node : new HashSet<>(sets[srcIndex])) {
                        if (!sets[index].contains(node)) {
                            anyUpdates = true;
                            sets[index].add(node);
                        }
                    }
                }

            }
        } else if (sub instanceof SubstitutionMapping app) {
            if (value instanceof ValueSource src) {
                for (ExpressionNode node : new HashSet<>(sets[src.getIndex()])) {
                    if (node instanceof ast.Lambda lambda) {
                        Value valueSrc = new ValueSource(app.getValue().getIndex());
                        Substitution newSub = new SubstitutionSimple(pointToInt.get(lambda.getParameter()));
                        Fact newFact = new Fact(valueSrc, newSub);

                        if (!facts.contains(newFact)) {
                            facts.add(newFact);
                            anyUpdates = true;
                        }
                        for (ExpressionNode resultingExpr : new HashSet<>(sets[app.getValue().getIndex()])) {
                            if (!(sets[app.getResult().getIndex()].contains(resultingExpr))) {
                                sets[app.getResult().getIndex()].add(resultingExpr);
                                anyUpdates = true;
                            }
                            if (!sets[src.getIndex()].contains(resultingExpr)) {
                                sets[src.getIndex()].add(resultingExpr);
                                anyUpdates = true;
                            }
                        }
                    }
                }
            }
        }

        return anyUpdates;
    }

    private void displayFacts() {
        System.out.println("Facts:");
        for (Fact fact : facts) {
            System.out.println("\t" + fact);
        }
    }

    private void displaySets() {
        System.out.println("\nSets:");
        int index = 0;
        for (Set<ExpressionNode> set : sets) {
            StringBuilder builder = new StringBuilder("α");
            builder.append(SubscriptPrinter.subscript(index));
            builder.append(" = { ");
            int currentSetValue = 0;
            for (ExpressionNode node : set) {
                builder.append(node.toString());
                if (currentSetValue++ < set.size() - 1) {
                    builder.append(", ");
                }
            }
            builder.append(" }");
            System.out.println(builder);
            index++;
        }
    }

    private void displayState() {
        displayFacts();
        displaySets();
    }
}
