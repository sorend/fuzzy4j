
Fuzzy logic library for Java (fuzzy4j)
======================================

Fuzzy4j is a Java library implementing many commonly used fuzzy logic functions from the areas of fuzzy sets,
fuzzy aggregation, and fuzzy controller. The library was initially developed as part of the fuzzy logic course under
prof. Henrik Legind Larsen at Aalborg University, Esbjerg. Later it has been applied in different real-world
applications under EU-FP7. If you are using fuzzy4j for something interesting, please drop us a line, thanks!

The goals of fuzzy4j is to implement a fuzzy toolbox for Java: 1) to give a set of meaningful and modern interfaces
which can be used to implement your own fuzzy code; and 2) to provide default implementations of most common
fuzzy sets, aggregations and controllers.

We are thankful for the collaborators, users who provide feedback, and researchers who have come up with fuzzy logic.

Usage
-----

Source-code is available in bitbucket, and can as such always be forked or cloned from https://bitbucket.org/sorend/fuzzy4j .
The pre-built artifacts are deployed in the http://conjars.org repository. You can add this repository to your Maven
(or Maven compatible, such as Ivy or SBT) for easy inclusion:

    <repository>
        <id>conjars.org</id>
        <url>http://conjars.org/repo</url>
    </repository>

Once the repository has been added, the artifact can be included in your project as shown below. Unfortunately conjars.org
does not support source and javadoc artifacts (yet).

    <dependency>
        <groupId>fuzzy4j</groupId>
        <artifactId>fuzzy4j</artifactId>
        <version>1.3-SNAPSHOT</version>
    </dependency>

Fuzzy sets
----------

Fuzzy sets are implemented as FuzzyFunctions. The current implementations are found in the fuzzy.sets package.
Creating a fuzzy set based on a specific triangular function and it's negation.

    FuzzyFunction f = new TriangularFunction(0.0, 1.5, 6.0);
    FuzzyFunction not_f = new ZadehNegation(f);

    double mu_x = f.apply(1.0);
    double not_mu_x = not_f.apply(1.0);

Fuzzy aggregation operators
---------------------------

Fuzzy aggregation of values, see fuzzy4j.aggregation for implemented aggregation operators. A small example below:

    Aggregation owa = new OWA(0.5, 0.2, 0.2, 0.1);
    double aggr = owa.apply(0.8, 0.2, 0.7, 0.1);

There is also a class of weighted aggregation operators, see fuzzy4j.aggregation.weighted. A small example below:
("_" is static imported WeightedValue._)

    WeightedAggregation wowa = new WeightedOWA(0.1, 0.5, 0.5, 0.2, 0.2, 0.1);
    double waggr = wowa.apply(_(1.0, 0.8), _(1.0, 0.2), _(1.0, 0.7), _(1.0, 0.1));

Many of the aggregation operators require parameters for instantiation. The unified method
for instantiazing them is to use ParametricFactory<T>. Most of the implemented aggregation
operators with parameters already contain such a factory. Look for a static FACTORY attribute.
As an example below is shown the OWA operator, which contains two factories:

    // create OWA operator with specific OWA weights.
    OWA owa1 = OWA.FACTORY.create(0.5, 0.25, 0.1, 0.1, 0.05);
    // create OWA operator with MEOWA weights from andness.
    OWA owa2 = OWA.MEOWA_FACTORY.create(5, 0.8);

Fuzzy controller (flc)
----------------------

A part of the fuzzy4j library is the fuzzy logic controller (flc). This allows you
to programatically construct fuzzy logic controllers.

The following example is taken from the test class.

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
            .create();

    InputInstance instance = new InputInstance().is(room, 60);

    System.out.println("fuzzy = " + impl.applyFuzzy(instance));

    Map<Variable, Double> crisp = impl.apply(instance);

    System.out.println("crisp = " + crisp);

You can create terms by discretizing using one of the methods from Discretizations.
For example to create a number of uniformly distributed triangular functions, see below:

    Term[] terms = Discretizations.uniformTriangular(0.0, 10.0, "low", "medium", "high");

    assert "low".equals(terms[0].name);
    assert "medium".equals(terms[1].name);
    assert "high".equals(terms[2].name);

Variables have a default domain of [0, 1] (the unit interval). You can set a different
domain by using start(..) and end(..) on the variable. The defuzzifiers use a number of
samples to determine the defuzzified value, the default number of samples is 100. If you
change the domain of a variable, you accordingly set the number of samples to an appropriate
number. See the example of domain and samples below:

    Term low    = term("low", 0, 0, 36.0, 37.25);
    Term normal = term("normal", 36.0, 37.25, 38.5);
    Term high   = term("high", 37.26, 38.5, 100, 100);

    // set domain to [0, 100]
    Variable temp = output("temp", low, normal, high).start(0).end(100);

    // create a centroid defuzzifier, and set number of samples to 1000
    Centroid defuzzifier = new Centroid();
    defuzzifier.setSamples(1000);

You can use hedges to modify matching of a term in rules. For example the "not" hedge
will match not. You can build hedges using the static methods in HedgeBuilder, or, you
can implement your own Hedge. In the example below the "not" and "very" hedges are
used.

    FLC impl = ControllerBuilder.newBuilder()
            .when().var(temp).is(not(high)).then().var(paracetamol).is(none)
            .when().var(temp).is(very(high)).then().var(paracetamol).is(moderate)
            .defuzzifier(new Centroid())
            .create();


License
-------

Fuzzy4j is licensed using the FreeBSD license.

