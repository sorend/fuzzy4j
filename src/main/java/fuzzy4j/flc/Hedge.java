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

/**
 * Hedge is a modification of membership.
 *
 * Static implementations for different common hedges.
 *
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public interface Hedge {

    public static final Hedge ANY = new Hedge() {
        @Override
        public double apply(double value) {
            return 1.0;
        }
    };

    public static final Hedge EXTREMELY = new Hedge() {
        @Override
        public double apply(double value) {
            return (value < 0.5) ?
                    2 * value * value :
                    1 - 2 * (1 - value) * (1 - value);
        }
        public String toString() {
            return "extremely";
        }
    };

    public static final Hedge SELDOM = new Hedge() {
        @Override
        public double apply(double value) {
            return (value <= 0.5) ?
                    Math.sqrt(value / 2.0) :
                    1 - Math.sqrt((1 - value) / 2.0);
        }
        public String toString() {
            return "seldom";
        }
    };

    public static final Hedge SOMEWHAT = new Hedge() {
        @Override
        public double apply(double value) {
            return Math.sqrt(value);
        }
        public String toString() {
            return "somewhat";
        }
    };

    public static final Hedge NOT = new Hedge() {
        @Override
        public double apply(double value) {
            return 1.0 - value;
        }
        public String toString() {
            return "not";
        }

    };

    public static final Hedge VERY = new Hedge() {
        @Override
        public double apply(double value) {
            return value * value;
        }
        public String toString() {
            return "very";
        }
    };

    double apply(double value);
}
