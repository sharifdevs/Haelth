package com.example.haelth;

// Close Friends and Family
class People extends Person {

    enum Relation {FATHER, MOTHER, SIBLING, FAMILY, FRIEND}

    //People Info
    private static Relation relation;

    // Setters and Getters
    public static Relation getRelation() {
        return relation;
    }

    public static void setRelation(Relation relation) {
        People.relation = relation;
    }
}

