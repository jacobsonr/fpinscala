package com.maponics.fpinscala.chapter1

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import com.maponics.fpinscala.chapter1.Chapter1._
import org.scalacheck.Gen.{choose, oneOf}

/** Tests for introductory functions
  */
object Chapter1Spec extends Properties("Chapter1") {

  property("yes: should return true") = yes() == true

  property("no: should return false") = no() == false

  property("biggerThan100: should generally work") = {
    forAll {(i: Int)  =>
      biggerThan100(i) == (i > 100)
    }
  }

  val edgeCases = oneOf((99, false), (100, false), (101, true))

  property("biggerThan100: should work around 100") = {
    forAll(edgeCases) { case (i: Int, expected: Boolean) =>
      biggerThan100(i) == expected
    }
  }
}
