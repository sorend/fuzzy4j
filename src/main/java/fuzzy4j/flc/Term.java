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

import fuzzy4j.sets.FuzzyFunction;
import fuzzy4j.sets.TrapezoidalFunction;
import fuzzy4j.sets.TriangularFunction;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class Term {

    /**
     * Construct a term with a triangular apply function.
     * @param name
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static Term term(String name, double a, double b, double c) {
        return term(name, new TriangularFunction(a, b, c));
    }

    /**
     * Construct a term with a trapezoidal apply function.
     * @param name
     * @param a
     * @param b
     * @param c
     * @param d
     * @return
     */
    public static Term term(String name, double a, double b, double c, double d) {
        return term(name, new TrapezoidalFunction(a, b, c, d));
    }

    /**
     * Construct a term with a specified apply function.
     * @param name
     * @param set
     * @return
     */
    public static Term term(String name, FuzzyFunction set) {
        if (name == null || set == null)
            throw new IllegalArgumentException("name and set must be non-null");
        return new Term(name, set);
    }

    public final String name;
    public final FuzzyFunction set;

    public Term(String name, FuzzyFunction set) {
        this.name = name;
        this.set = set;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Term term = (Term) o;
        if (!name.equals(term.name)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return name;
    }
}
