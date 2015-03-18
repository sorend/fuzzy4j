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

import fuzzy4j.aggregation.AlgebraicProduct;
import fuzzy4j.aggregation.AlgebraicSum;
import fuzzy4j.flc.defuzzify.Centroid;
import fuzzy4j.sets.FuzzyFunction;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static fuzzy4j.flc.HedgeBuilder.not;
import static fuzzy4j.flc.HedgeBuilder.very;
import static fuzzy4j.flc.Term.term;
import static fuzzy4j.flc.Variable.input;
import static fuzzy4j.flc.Variable.output;
import static junit.framework.Assert.assertEquals;

/**
 * @author Soren A. Davidsen <soren@atmakuridavidsen.com>
 */
public class FLCTest {

    @Test
    public void testBuilding() {

        Term dark   = term("dark", 0, .25, .5);
        Term medium = term("medium", .25, .5, .75);
        Term bright = term("bright", .5, .75, 1);

        Variable ambient = input("ambient", dark, medium, bright);

        Term low = term("low", 0.0, 0.5, 1.0);
        Term med = term("medium", 0.5, 1.0, 1.5);
        Term hig = term("high", 1.0, 1.5, 2.0);

        Variable power = output("power", low, med, hig).start(0).end(2);

        FLC flc = ControllerBuilder.newBuilder().
                when().var(ambient).is(dark).then().var(power).is(hig).
                when().var(ambient).is(medium).then().var(power).is(med).
                when().var(ambient).is(bright).then().var(power).is(low).
                create();

        InputInstance instance = new InputInstance()
                .is(ambient, 0.4);

        Map<Variable, FuzzyFunction> result = flc.applyFuzzy(instance);

        System.out.println("result = " + result);

        Map<Variable, Double> crisp = flc.apply(instance);

        System.out.println("crisp = " + crisp);
    }

    @Test
    public void testExample_01() throws Exception {

        Term cold = term("cold", 10, 40, 70);
        Term ok   = term("ok", 40, 70, 100);
        Term hot  = term("hot", 70, 100, 130);

        Variable room = input("room", cold, ok, hot).start(40).end(100);

        Term low  = term("low", -50, 0, 50);
        Term medi = term("medium", 0, 50, 100);
        Term high = term("high", 50, 100, 150);

        Variable effort = output("effort", low, medi, high).start(-50).end(150);

        FLC impl = ControllerBuilder.newBuilder()
                .when().var(room).is(cold).then().var(effort).is(high)
                .when().var(room).is(ok).then().var(effort).is(medi)
                .when().var(room).is(hot).then().var(effort).is(low)
                .activationFunction(AlgebraicProduct.INSTANCE)
                .accumulationFunction(AlgebraicSum.INSTANCE)
                .defuzzifier(new Centroid())
                .create();

        InputInstance instance = new InputInstance().is(room, 60);

        System.out.println("fuzzy = " + impl.applyFuzzy(instance));

        Map<Variable, Double> crisp = impl.apply(instance);

        System.out.println("crisp = " + crisp);
    }

    @Test
    public void fabioExample() {

        Term poor = Term.term("poor", 0, 0, 4);
        Term good   = Term.term("good", 1, 4, 6, 9);
        Term excellent  = Term.term("excellent", 6, 9, 9);

        Variable service = Variable.input("service", poor, good, excellent).start(0).end(9);

        Term rancid   = Term.term("rancid", 0, 0, 1, 3);
        Term delicious  = Term.term("delicious", 7, 9, 9);

        Variable food = Variable.output("food", rancid, delicious).start(0).end(9);

        Term cheap  = Term.term("cheap", 0, 5, 10);
        Term average = Term.term("average", 10, 15, 20);
        Term generous = Term.term("generous", 20, 25, 30);

        Variable tip = Variable.output("tip", cheap, average, generous).start(0).end(30);

        FLC impl = ControllerBuilder.newBuilder()
                .when().var(service).is(poor).or().var(food).is(rancid).then().var(tip).is(cheap)
                .when().var(service).is(good).then().var(tip).is(average)
                .when().var(service).is(excellent).then().var(tip).is(generous)
                .defuzzifier(new Centroid())
                .create();

        Map<Variable, Double> map=new HashMap<Variable, Double>();
        map.put(service, (double) 9);
        map.put(food, (double) 3);
        InputInstance instance = InputInstance.wrap(map);
        System.out.println("fuzzy = " + impl.applyFuzzy(instance));
        Map<Variable, Double> crisp = impl.apply(instance);
        System.out.println(crisp.get(tip));

        assertEquals(25, crisp.get(tip), 0.01);

        for (int val_food = 0; val_food < 4; val_food++) {
            for (int val_service = 0; val_service < 4; val_service++) {

                System.out.println("crisp(service=" + val_service + ",food="+val_food+") = " +
                    impl.apply(new InputInstance().is(service, val_service).is(food, val_food)));
            }
        }
    }

    @Test
    public void testFabioExample_02() {
        Term poor = Term.term("poor", 0, 0, 4);
        Term good   = Term.term("good", 1, 4, 6, 9);
        Term excellent  = Term.term("excellent", 6, 9, 9);

        Variable service = Variable.input("service", poor, good, excellent).start(0).end(9);

        Term rancid   = Term.term("rancid", 0, 0, 1, 3);
        Term delicious  = Term.term("delicious", 7, 9, 9);

        Variable food = Variable.output("food", rancid, delicious).start(0).end(9);

        Term cheap  = Term.term("cheap", 0, 5, 10);
        Term average = Term.term("average", 10, 15, 20);
        Term generous = Term.term("generous", 20, 25, 30);

        Variable tip = Variable.output("tip", cheap, average, generous).start(0).end(30);

        FLC impl = ControllerBuilder.newBuilder()
                .when().var(service).is(poor).and().var(food).is(rancid).then().var(tip).is(cheap)
                .activationFunction(AlgebraicProduct.INSTANCE)
                .accumulationFunction(AlgebraicSum.INSTANCE)
                .defuzzifier(new Centroid())
                .create();

        Map<Variable, Double> map=new HashMap<Variable, Double>();
        map.put(service, (double) 0);
        map.put(food, (double) 0);
        InputInstance instance = InputInstance.wrap(map);
        System.out.println("fuzzy = " + impl.applyFuzzy(instance));
        Map<Variable, Double> crisp = impl.apply(instance);
        System.out.println(crisp.get(tip));
    }

    @Test
    public void testExample_hedges() throws Exception {

        Term low    = term("low", 0, 36.0, 37.25);
        Term normal = term("normal", 36.0, 37.25, 38.5);
        Term high   = term("high", 37.25, 38.5, 100);

        Variable temp = input("temp", low, normal, high).start(0).end(100);

        Term none     = term("none", -1, 0, 0.1);
        Term moderate = term("moderate",  0, 0.500, 1.000);

        Variable paracetamol = output("paracetamol", none, moderate);

        FLC impl = ControllerBuilder.newBuilder()
                .when().var(temp).is(not(high)).then().var(paracetamol).is(none)
                .when().var(temp).is(high).then().var(paracetamol).is(moderate)
                .defuzzifier(new Centroid())
                .create();

        InputInstance instance = new InputInstance().is(temp, 37.0);

        System.out.println("normal.fuzzy = " + impl.applyFuzzy(instance));

        Map<Variable, Double> crisp = impl.apply(instance);

        System.out.println("normal.crisp = " + crisp);

        InputInstance fever = new InputInstance().is(temp, 38.5);
        System.out.println("fever.fuzzy = " + impl.applyFuzzy(fever));
        System.out.println("fever.crisp = " + impl.apply(fever));
    }


}
