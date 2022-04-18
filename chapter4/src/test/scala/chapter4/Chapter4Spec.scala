package com.maponics.fpinscala.chapter4

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import org.scalacheck.Gen.{choose, oneOf}
import scala.collection.immutable
import com.maponics.fpinscala.chapter4.Option._
import com.maponics.fpinscala.chapter4.Either._

/** Tests for Functional Programming in Scala chapter 4 exercises
  *
  * These are not meant to be exhaustive, but should be enough to
  * guide you to the answer if you get the tests to pass.
  */
object Chapter4Spec extends Properties("List")
{
  /* property("Exercise 3.5: dropWhile drops the matching prefix") = forAll {
    (l: immutable.List[Int]) => {
      val l2 = List(l: _*)
      checkResults(l.dropWhile(_ > 0), dropWhile(l2)(_ > 0)) &&
      checkResults(l.dropWhile(_ % 2 == 1), dropWhile(l2)(_ % 2 == 1)) &&
      checkResults(l.dropWhile(_ % 10 != 7), dropWhile(l2)(_ % 10 != 7))
    }
   } */

  def months = List("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

  def monthName(month: Int): Option[String] = {
    try {
      Some(months(month - 1))
    } catch {
      case e: Exception => None
    }
  }

  def monthName2(month: Int): Either[String, String] = {
    try {
      Right(months(month - 1))
    } catch {
      case e: Exception => Left(s"Invalid Month Number: $month")
    }
  }

  property("Exercise 4.1: map works for Some") = forAll {
    (l: Int) => {
      val opt = Some(l)
      opt.map(_ * 2) == Some(l * 2)
    }
  }

  property("Exercise 4.1: map works for None") = {
    val opt: Option[Int] = None
    opt.map(_ * 2) == None
  }


  val monthNumber = choose(1, 24)

  property("Exercise 4.1: flatMap works for Some") = forAll(monthNumber) { l =>
    val opt = Some(l)
    (l < 13) == (opt.flatMap(monthName) != None)
  }

  property("Exercise 4.1: flatMap works for None") = {
    val opt: Option[Int] = None
    opt.flatMap(monthName) == None
  }

  property("Exercise 4.1: getOrElse works for Some") = forAll {
    (l: Int) => {
      val opt = Some(l)
      opt.getOrElse("aoeu") == l
    }
  }
  property("Exercise 4.1: getOrElse works for None") = {
    val opt: Option[Int] = None
    opt.getOrElse("aoeu") == "aoeu"
  }

  property("Exercise 4.1: orElse works for Some") = forAll {
    (l: Int) => {
      val opt = Some(l)
      opt.orElse(Some(99)) == Some(l)
    }
  }
  property("Exercise 4.1: orElse works for None") = {
    val opt: Option[Int] = None
    opt.orElse(Some(999)) == Some(999)
  }

  property("Exercise 4.1: filter works for Some") = forAll {
    (l: Int) => {
      val opt = Some(l)
      val expected = if (l % 2 == 1) Some(l) else None
      opt.filter(_ % 2 == 1) == expected
    }
  }
  property("Exercise 4.1: filter works for None") = {
    val opt: Option[Int] = None
    opt.filter(_ % 2 == 1) == None
  }

  property("Exercise 4.2: mean works") = forAll {
    (l: List[Double]) => {
      val expected =
        if (l.isEmpty) None
        else Some(l.sum / l.size)
      mean(l) == expected
    }
  }

  val variData = List(
    (List(24.3, 29.2, 25.8), Some(4.202222222222219)),
    (List(1.0, 2.0, 3.0), Some(0.6666666666666666)),
    (List(), None)
  )

  property("Exercise 4.2: variance works for test data") = {
    val vd = variData.map { case (l, expected) =>
      variance(l) == expected
    }
    vd.reduce(_ && _)
  }

  property("Exercise 4.3: map2 returns Something with valid values") = forAll {
    (n1: Int, n2: Int) => {
      map2(Some(n1), Some(n2))(_ + _) == Some(n1 + n2)
    }
  }

  property("Exercise 4.3: map2 returns None with a None input") = forAll {
    (n1: Int, n2: Int) => {
      (map2(Some(n1), None)(_ + _) == None) && (map2(None, Some(n2))((x: Int, y: Int) => x + y) == None)
    }
  }

  property("Exercise 4.4: sequence works") = forAll {
    (l1: List[Int], l2: List[Int]) => {
      val ol1: List[Option[Int]] = l1.map(Some(_))
      val ol2: List[Option[Int]] = l2.map(Some(_))
      val ol = ol1 ++ ol2
      val noneList: List[Option[Int]] = ol1 ++ (None :: ol2)
      sequence(noneList) == None &&
      sequence(ol) == Some(l1 ++ l2)
    }
  }

  property("Exercise 4.5: traverse works") = forAll {
    (l1: List[Int]) => {
      val result: Option[List[Int]] = traverse(l1)(v => if (v < 0) None else Some(v))
      val expected = if (l1.exists(_ < 0)) None else Some(l1)
      result == expected
    }
  }

  property("Exercise 4.5: sequenceViaTraverse works") = forAll {
    (l1: List[Int], l2: List[Int]) => {
      val ol1: List[Option[Int]] = l1.map(Some(_))
      val ol2: List[Option[Int]] = l2.map(Some(_))
      val ol = ol1 ++ ol2
      val noneList: List[Option[Int]] = ol1 ++ (None :: ol2)
      sequenceViaTraverse(noneList) == None &&
      sequenceViaTraverse(ol) == Some(l1 ++ l2)
    }
  }
  property("Exercise 4.6: flatMap works for Right") = forAll(monthNumber) { l =>
    val opt = Right(l)
    if (l < 13) {
      opt.flatMap(monthName2) == Right(months(l - 1))
    } else {
      opt.flatMap(monthName2) == Left(s"Invalid Month Number: $l")
    }
  }

  property("Exercise 4.6: flatMap works for Left") = {
    val opt = Left("invalid")
      opt.flatMap(monthName2) == Left("invalid")
  }

  property("Exercise 4.6: map works for Right") = forAll {
    (l: Int) => {
      val opt = Right(l)
      val result  = opt.map(_ * 2) == Right(l * 2)
      if (!result) println(s"map: $l ${opt.map(_ * 2)}")
      result

    }
  }
  property("Exercise 4.6: map works for Left") = {
    val opt: Either[String, Int] = Left("bad")
    opt.map(_ * 2) == Left("bad")
  }

  property("Exercise 4.6: orElse works for Right") = forAll {
    (l: Int) => {
      val opt = Right(l)
      opt.orElse(Right(99)) == Right(l)
    }
  }
  property("Exercise 4.6: orElse works for Left") = {
    val opt: Either[String, Int] = Left("bad")
    opt.orElse(Right(999)) == Right(999)
  }

  property("Exercise 4.6: map2 returns Right with valid values") = forAll {
    (n1: Int, n2: Int) => {
      val en1: Either[String, Int] = Right(n1)
      val en2: Either[String, Int] = Right(n2)
      en1.map2(en2)(_ + _) == Right(n1 + n2)
    }
  }

  property("Exercise 4.6: map2 returns Left with a Left input") = forAll {
    (n1: Int) => {
      val en1: Either[String, Int] = Right(n1)
      val en2: Either[String, Int] = Left("bad")
      en1.map2(en2)(_ + _) == Left("bad") &&
      en2.map2(en1)(_ + _) == Left("bad")
    }
  }

  property("Exercise 4.7: sequence works with Either") = forAll {
    (l1: List[Int], l2: List[Int]) => {
      val ol1: List[Either[String, Int]] = l1.map(Right(_))
      val ol2: List[Either[String, Int]] = l2.map(Right(_))
      val ol = ol1 ++ ol2
      val noneList: List[Either[String, Int]] = ol1 ++ (Left("Bad Input") :: ol2)
      sequenceE(noneList) == Left("Bad Input") &&
      sequenceE(ol) == Right(l1 ++ l2)
    }
  }

  property("Exercise 4.7: traverse works with Either") = forAll {
    (l1: List[Int]) => {
      val result: Either[String, List[Int]] = traverseE(l1)(v => if (v < 0) Left("Bad Input") else Right(v))
      val expected = if (l1.exists(_ < 0)) Left("Bad Input") else Right(l1)
      result == expected
    }
  }

}
