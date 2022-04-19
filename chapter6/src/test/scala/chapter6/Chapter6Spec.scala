package com.maponics.fpinscala.chapter6

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import org.scalacheck.Gen.{choose, oneOf}
import scala.collection.immutable
import com.maponics.fpinscala.chapter6._
import com.maponics.fpinscala.chapter6.SimpleRNG._

/** Tests for Functional Programming in Scala chapter 6 exercises
  *
  * These are not meant to be exhaustive, but should be enough to
  * guide you to the answer if you get the tests to pass.
  */
object Chapter6Spec extends Properties("State") {

  def inRange(d: Double) = d >= 0.0 && d <= 1.0

  def seemsRandom[A](rng: RNG, checkLimits: A => Boolean, f: RNG => (A, RNG)) = {
    val (i, r2) = f(rng)
    val (i2, r3) = f(r2)
    val (i3, _) = f(r3)
    val (i4, _) = f(r2)
    checkLimits(i) && checkLimits(i2) && checkLimits(i3) &&
    ((i != i2) || (i != i3)) &&
    (i2 == i4)
  }

  property("Exercise 6.1: nonNegativeInt") = forAll {
    (seed: Long) => seemsRandom(SimpleRNG(seed), (i: Int) => i >= 0, nonNegativeInt)
  }

  property("Exercise 6.2: double") = forAll {
    (seed: Long) => seemsRandom(SimpleRNG(seed), (d: Double) => inRange(d), double)
  }

  property("Exercise 6.3(1/3): intDouble") = forAll {
    (seed: Long) => seemsRandom(SimpleRNG(seed), (p: (Int, Double)) => inRange(p._2), intDouble)
  }

  property("Exercise 6.3(2/3): doubleInt") = forAll {
    (seed: Long) => seemsRandom(SimpleRNG(seed), (p: (Double, Int)) => inRange(p._1), doubleInt)
  }

  property("Exercise 6.3(3/3): double3") = forAll {
    (seed: Long) => seemsRandom(SimpleRNG(seed), (p: (Double, Double, Double)) => inRange(p._1) && inRange(p._2) && inRange(p._3) && ((p._1 != p._2) || (p._2 != p._3)), double3)
  }

  property("Exercise 6.4: ints") = forAll {
    (seed: Long, count1: Int) => {
      val count = count1 & 127
      if (count > 0) seemsRandom(SimpleRNG(seed), (l: List[Int]) => l.size == count, ints(count))
      else ints(count)(SimpleRNG(seed))._1 == Nil
    }
  }
  
  property("Exercise 6.5: doubleViaMap") = forAll {
    (seed: Long) => seemsRandom(SimpleRNG(seed), (d: Double) => inRange(d), doubleViaMap)
  }

  property("Exercise 6.6: map2") = forAll {
    (seed: Long) => {
      val rng = SimpleRNG(seed)
      randIntDouble(rng) == intDouble(rng) &&
      randDoubleInt(rng) == doubleInt(rng)
    }
  }

  property("Exercise 6.7: intsViaSequence") = forAll {
    (seed: Long, count1: Int) => {
      val count = count1 & 127
      if (count > 0) seemsRandom(SimpleRNG(seed), (l: List[Int]) => l.size == count, intsViaSequence(count))
      else ints(count)(SimpleRNG(seed))._1 == Nil
    }
  }

  property("Exercise 6.9: map2 via flatMap") = forAll {
    (seed: Long) => {
      val rng = SimpleRNG(seed)
      randIntDoubleViaFlatMap(rng) == intDouble(rng) &&
      randDoubleIntViaFlatMap(rng) == doubleInt(rng)
    }
  }

}
