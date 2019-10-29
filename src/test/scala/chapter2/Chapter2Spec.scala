package com.maponics.fpinscala.chapter2

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import com.maponics.fpinscala.chapter2.Chapter2._
import org.scalacheck.Gen.{choose, oneOf}

/** Tests for Functional Programming in Scala chapter 2 exercises
  * 
  * These are not meant to be exhaustive, but should be enough to
  * guide you to the answer if you get the tests to pass.
  *
  * (ScalaCheck shows some of the value of testing pure functions)
  */
object Chapter2Spec extends Properties("Chapter2") {

  // exercise 2.1
  def tests = oneOf((0, 0), (1, 1), (2, 1), (3, 2), (4, 3), (5, 5), (6, 8), (7, 13))
  property("Exercise 2.1: fib has correct starting sequence") = forAll(tests) { case (x: Int, y: Int) =>
    y == fib(x)
  }

  property("Exercise 2.1: fib") = forAll(choose(2, 1000000)) { a =>
    fib(a) == fib(a - 1) + fib(a - 2)
  }

  // exercise 2.2
  property("Exercise 2.2: isSorted with ints") = forAll{ (l: List[Int]) =>
    val sorted = l.sorted
    isSorted(sorted.toArray, (n1: Int, n2: Int) => n1 <= n2)
  }

  property("Exercise 2.2: isSorted with sorting error") = forAll{ l: List[Int] =>
    forAll(choose(Int.MinValue+1, Int.MaxValue)) {v =>
      // make sure we have at least one value in the list that is not min/max
      val sorted = ((if (v == Int.MinValue || v == Int.MaxValue) v + 2 else v) :: l).sorted
      forAll(choose(0, sorted.size)) { i =>
        val unsorted = sorted.take(i) ::: (if (i < sorted.size) (sorted(i) + 1) else (sorted(i - 1) - 1)) :: sorted.drop(i)
        !isSorted(unsorted.toArray, (n1: Int, n2: Int) => n1 <= n2)
      }
    }

  }

  property("Exercise 2.2: isSorted with strings") = forAll{ (l: List[String]) =>
    val a = l.sorted.toArray
    isSorted(a, (n1: String, n2: String) => n1 <= n2)
  }


  property("Exercise 2.3: curry") = forAll(genFunction2){ f =>
    forAll {(i1: Int, i2: Int) =>
      val c = curry(ff1)
      ff1(i1, i2) == c(i1)(i2)
    }
  }


  // Some test functions for the remaining exercises
  def f1(i: Int) = i * 66
  def f2(i: Int) = 3
  def f3(i: Int) = i * 2

  def ff1(x: Int, y: Int) = x * y
  def ff2(x: Int, y: Int) = x + y
  def ff3(x: Int, y: Int) = s"[$x, $y]"

  // Generators to pick randomly from the above functions
  def genFunction1 = oneOf(f1 _, f2 _, f3 _)
  def genFunction2 = oneOf(ff1 _, ff2 _, ff3 _)

  // exercise 2.3
  property("Exercise 2.3: curry") = forAll(genFunction2){ f =>
    forAll {(i1: Int, i2: Int) =>
      val c = curry(ff1)
      ff1(i1, i2) == c(i1)(i2)
    }
  }

  // exercise 2.4
  property("Exercise 2.4: uncurry") = forAll(genFunction2){ f =>
    forAll {(i1: Int, i2: Int) =>
      val c = curry(f)
      val c2 = uncurry(c)
      c2(i1, i2) == f(i1, i2)
    }
  }

  // exercise 2.5
  property("Exercise 2.5: compose") = forAll(genFunction1, genFunction1) { (f1, f2) =>
    val c = compose(f1, f2)
    forAll { (i: Int) =>
      c(i) == f1(f2(i))
    }
  }
}
