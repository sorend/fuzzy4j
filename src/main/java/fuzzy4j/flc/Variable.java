/*
 * Copyright (c) 2013, Søren Atmakuri Davidsen
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package fuzzy4j.flc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Defines variables of a fuzzy controller. The variable has a name, and a set of
 * fuzzy terms attached, as well as a universe for which the input can take.
 *
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class Variable {

    /**
     * Create an input variable with a set of terms.
     *
     * Note that input variable names should be unique within a controller.
     *
     * @param name the name of the variable
     * @param terms the terms to attach
     * @return
     */
    public static Variable input(String name, Term... terms) {
        Variable var = new Variable();
        var.name = name;
        var.input = true;
        if (terms != null)
            var.terms = Arrays.asList(terms);
        return var;
    }

    /**
     * Create an output variable with a set of terms.
     *
     * Note that output variable names should be unique within a controller.
     *
     * @param name the name of the variable
     * @param terms the terms
     * @return
     */
    public static Variable output(String name, Term... terms) {
        Variable var = new Variable();
        var.name = name;
        var.input = false;
        if (terms != null)
            var.terms = Arrays.asList(terms);
        return var;
    }

    private String name = null;
    private boolean input = true;
    private double start = 0.0;
    private double end = 1.0;
    private List<Term> terms = new ArrayList();

    /**
     * Is this an input variable
     * @return true if yes
     */
    public boolean isInput() {
        return input;
    }

    /**
     * Define the starting point of the universe (X) of the variable.
     *
     * Default is 0.0
     * @param start The start
     * @return the variable
     */
    public Variable start(double start) {
        this.start = start;
        return this;
    }

    /**
     * Define the ending point of the universe (X) of the variable.
     *
     * Default is 1.0
     *
     * @param end The end
     * @return the variable
     */
    public Variable end(double end) {
        this.end = end;
        return this;
    }

    public double min() {
        return start;
    }

    public double max() {
        return end;
    }

    /**
     * Set terms of the variable to a new set of terms.
     * @param terms the terms
     * @return the variable
     */
    public Variable terms(Term... terms) {
        this.terms = Arrays.asList(terms);
        return this;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        if (input != variable.input) return false;
        if (!name.equals(variable.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (input ? 1 : 0);
        return result;
    }
}
