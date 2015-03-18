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

import fuzzy4j.aggregation.Aggregation;
import fuzzy4j.aggregation.Minimum;
import fuzzy4j.flc.defuzzify.Centroid;
import fuzzy4j.flc.defuzzify.Defuzzify;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of a fuzzy logic controller.
 *
 * @author Soren <soren@tanesha.net>
 */
public class ControllerBuilder {

    public static <V> Map<V, Double> map(V[] keys, double[] values) {
        Map<V, Double> m = new LinkedHashMap<V, Double>();
        for (int i = 0; i < keys.length; i++)
            m.put(keys[i], values[i]);
        return m;
    }

    public static ControllerBuilder newBuilder() {
        return new ControllerBuilder();
    }

    private Aggregation tnorm = Minimum.INSTANCE;
    private Aggregation tconorm = fuzzy4j.aggregation.Maximum.INSTANCE;
    private Aggregation activationNorm = Minimum.INSTANCE;
    private Aggregation accumulationTconorm = fuzzy4j.aggregation.Maximum.INSTANCE;
    private Defuzzify defuzzifier = new Centroid();

    private List<IfThenRule> rules = new ArrayList();

    private ControllerBuilder() {
    }

    public List<IfThenRule> getRules() {
        return rules;
    }

    public ControllerBuilder andFunction(Aggregation aggregation) {
        this.tnorm = aggregation;
        return this;
    }

    public ControllerBuilder orFunction(Aggregation aggregation) {
        this.tconorm = aggregation;
        return this;
    }

    public ControllerBuilder activationFunction(Aggregation aggregation) {
        this.activationNorm = aggregation;
        return this;
    }

    public ControllerBuilder accumulationFunction(Aggregation aggregation) {
        this.accumulationTconorm = aggregation;
        return this;
    }

    public ControllerBuilder defuzzifier(Defuzzify defuzzifier) {
        this.defuzzifier = defuzzifier;
        return this;
    }

    public RuleBuilder when() {
        return new RuleBuilder(this);
    }

    public ControllerBuilder addRule(IfThenRule rule) {
        this.rules.add(rule);
        return this;
    }

    public FLC create() {
        return new FLC(tnorm, tconorm,
                activationNorm,
                accumulationTconorm,
                defuzzifier,
                rules);
    }

}
