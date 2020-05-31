package com.aleksandar69.PMSU2020Tim16.models;

import com.aleksandar69.PMSU2020Tim16.enums.Condition;
import com.aleksandar69.PMSU2020Tim16.enums.Operation;

public class Rule {
    private int id;
    private Condition condition;
    private Operation operation;

    public Rule(int id, Condition condition, Operation operation) {
        this.id = id;
        this.condition = condition;
        this.operation = operation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
