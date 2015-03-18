/*
 * Copyright (c) 2012, Søren Atmakuri Davidsen
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
package fuzzy4j.sets;

import fuzzy4j.util.SimpleInterval;

/**
 * Defines simple apply functions for use in every-day fuzzy logic problems :-)
 *
 * @author Soren AD <sdavid08@student.aau.dk>
 */
public class SetsFactory {

    private static class SplitFunction implements FuzzyFunction {
        private double a;
        private FuzzyFunction lte;
        private FuzzyFunction gt;

        public SplitFunction(double a, FuzzyFunction lte, FuzzyFunction gt) {
            this.a = a;
            this.lte = lte;
            this.gt = gt;
        }

        @Override
        public double apply(double x) {
            if (x <= a)
                return lte.apply(x);
            else
                return gt.apply(x);
        }
    }

    private static class DifferenceFunction implements FuzzyFunction {

        private FuzzyFunction f1, f2;

        private DifferenceFunction(FuzzyFunction f1, FuzzyFunction f2) {
            this.f1 = f1;
            this.f2 = f2;
        }

        @Override
        public double apply(double x) {
            return Math.max(0.0, f1.apply(x) - f2.apply(x));
        }
    }

    private static class SumFunction implements FuzzyFunction {

        private FuzzyFunction f1, f2;

        private SumFunction(FuzzyFunction f1, FuzzyFunction f2) {
            this.f1 = f1;
            this.f2 = f2;
        }

        @Override
        public double apply(double x) {
            return Math.min(1.0, f1.apply(x) + f2.apply(x));
        }
    }

    private static class Pow2Function implements FuzzyFunction, SupportAware {
        private double a;
        private double n;

        public Pow2Function(double a) {
            this.a = a;
            this.n = Math.pow(a, 2);
        }

        @Override
        public double apply(double x) {
            if (x <= 0)
                return 0.0;
            if (x >= a)
                return 1.0;
            else
                return Math.pow(x, 2) / n;
        }

        @Override
        public SimpleInterval support() {
            return new SimpleInterval(false, 0.0, Double.POSITIVE_INFINITY, true);
        }
    }

    private static class PowerFunction implements FuzzyFunction, SupportAware {
        private double b;
        private double a;

        public PowerFunction(double b, double a) {
            this.b = b;
            this.a = a;
        }

        @Override
        public double apply(double x) {
            return b * Math.pow(x, a);
        }

        @Override
        public SimpleInterval support() {
            return new SimpleInterval(false, 0.0, Double.POSITIVE_INFINITY, true);
        }

        public String toString() {
            return String.format("%f x^%f", b, a);
        }
    }


    private static class ExpFunction implements FuzzyFunction {
        private double b;
        private double a;

        public ExpFunction(double a, double b) {
            this.b = b;
            this.a = a;
        }

        @Override
        public double apply(double x) {
            return b * Math.exp(x * a);
        }

        public String toString() {
            return String.format("%f^%fx", b, a);
        }
    }

    private static class Log10Function implements FuzzyFunction {
        private double a;
        public Log10Function(double a) {
            this.a = a;
        }

        @Override
        public double apply(double x) {
            return Math.log10(x);
        }

    }

}
