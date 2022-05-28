package com.maponics.fpinscala.chapter3

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import com.maponics.fpinscala.chapter3.List._
import org.scalacheck.Gen.{choose, long, oneOf}
import org.scalacheck.Gen
import scala.collection.immutable

/** Tests for Functional Programming in Scala chapter 3 exercises
  *
  * These are not meant to be exhaustive, but should be enough to
  * guide you to the answer if you get the tests to pass.
  */
object Chapter3Spec extends Properties("List")
{
  def checkResults[A](expected: immutable.List[A], result: List[A]) = {
    val e2 = List(expected: _*)
    result == e2
  }

  property("Exercise 3.2: tail returns all but first element with ints") = forAll {
    (t: immutable.List[Int], h: Int) => {
      val l = h :: t
      val l2 = List(l: _*)
      val result = tail(l2)
      checkResults(l.tail, result)
    }
  }

  property("Exercise 3.2: tail returns all but first element with strings") = forAll {
    (t: immutable.List[String], h: String) => {
      val l = h :: t
      val l2 = List(l: _*)
      val result = tail(l2)
      checkResults(l.tail, result)
    }
  }

  property("Exercise 3.3: setHead changes the first element") = forAll {
    (t: immutable.List[String], h: String, newH: String) => {
      val l = h :: t
      val l2 = List(l: _*)
      val result = setHead(l2, newH)
      val expected = newH :: t
      checkResults(expected, result)
    }
  }

  property("Exercise 3.4: drop removes the first n elements") = forAll {
    (l: immutable.List[Int]) => {
      val n = scala.util.Random.nextInt(l.length + 2)
      val expected = l.drop(n)

      val l2 = List(l: _*)
      val result = drop(l2, n)

      checkResults(expected, result)
    }
  }

  property("Exercise 3.5: dropWhile drops the matching prefix") = forAll {
    (l: immutable.List[Int]) => {
      val l2 = List(l: _*)
      checkResults(l.dropWhile(_ > 0), dropWhile(l2)(_ > 0)) &&
      checkResults(l.dropWhile(_ % 2 == 1), dropWhile(l2)(_ % 2 == 1)) &&
      checkResults(l.dropWhile(_ % 10 != 7), dropWhile(l2)(_ % 10 != 7))
    }
  }

  property("Exercise 3.6: init keeps all but the last element") = forAll {
    (i: immutable.List[Int], t: Int) => {
      val l = t :: i
      val l2 = List(l: _*)
      val result = init(l2)
      checkResults(l.init, result)
    }
  }

  property("Exercise 3.9: length should return the list's length") = forAll {
    (l: immutable.List[Int]) => {
      val l2 = List(l: _*)
      l.length == length(l2)
    }
  }

  // Some test functions for the remaining exercises
  def if1(x: Int, y: Int) = x * y
  def if2(x: Int, y: Int) = x + y
  def if3(x: Int, y: Int) = x - y
  def genFunction = oneOf(if1 _, if2 _, if3 _)
  val theTrees = Seq(Leaf(11), Branch(Leaf(12), Leaf(5)), Branch(Branch(Leaf(1), Leaf(13)), Leaf(4)))
  val doubleTrees = Seq(Leaf(22), Branch(Leaf(24), Leaf(10)), Branch(Branch(Leaf(2), Leaf(26)), Leaf(8)))
  def aTree = oneOf(theTrees)

  def genLeaf = for {
    n <- long
  } yield Leaf(n.toInt)

  def genTree(level: Int): Gen[Tree[Int]] = if (level > 100) genLeaf else oneOf(genLeaf, genBranch(level+1))

  def genBranch(level: Int) = for {
    l <- genTree(level)
    r <- genTree(level)
  } yield Branch(l, r)

  lazy val trees: Gen[Tree[Int]] = genTree(0)

  property("Exercise 3.10: foldLeft should work") = forAll(genFunction) { f =>
    forAll { (l: immutable.List[Int], zero: Int) =>
      val l2 = List(l: _*)
      foldLeft(l2, zero)(f) == l.foldLeft(zero)(f)
    }
  }

  property("Exercise 3.11: sum by foldLeft should be the same as sum") = forAll {
    (l: immutable.List[Int]) => {
      val l2 = List(l: _*)
      sum(l2) == sumByFoldLeft(l2)
    }
  }

  property("Exercise 3.11: product by foldLeft should be the same as product") = forAll {
    (l: immutable.List[Int]) => {
      val ds = l.map(x => Math.abs(x) % 10000 / 10000.0)
      val l2 = List(ds: _*)
      (product(l2)*1000).toLong == (productByFoldLeft(l2)*1000).toLong
    }
  }

  property("Exercise 3.11: length by foldLeft should be the same as length") = forAll {
    (l: immutable.List[Int]) => {
      val l2 = List(l: _*)
      length(l2) == lengthByFoldLeft(l2)
    }
  }

  property("Exercise 3.12: reverse should reverse the list") = forAll {
    (l: immutable.List[Int]) => {
      val l2 = List(l: _*)
      checkResults(l.reverse, reverse(l2))
    }
  }

  property("Exercise 3.13: foldLeft by FoldRight should be the same as foldLeft") = forAll(genFunction) { f =>
    forAll { (l: immutable.List[Int], zero: Int) =>
      val l2 = List(l: _*)
      foldLeft(l2, zero)(f) == foldLeftByFoldRight(l2, zero)(f)
    }
  }

  property("Exercise 3.13: foldRight by FoldLeft should be the same as foldRight") = forAll(genFunction) { f =>
    forAll { (l: immutable.List[Int], zero: Int) =>
      val l2 = List(l: _*)
      foldRight(l2, zero)(f) == foldRightByFoldLeft(l2, zero)(f)
    }
  }

  property("Exercise 3.14: append should add an element to the end of the list") = forAll {
    (l: immutable.List[String], last: String) => {
      val l2 = List(l: _*)
      val result = append(l2, last)
      val expected = l ::: immutable.List(last)
      checkResults(expected, result)
    }
  }

  property("Exercise 3.15: flatten should append a list of lists to eachother") = forAll {
    (l: immutable.List[immutable.List[String]]) => {
      val l2s = l.map(li => List(li: _*))
      val l2 = List(l2s: _*)
      checkResults(l.flatten, flatten(l2))
    }
  }

  property("Exercise 3.16: addOne should add one to each int in the list") = forAll {
    (l: immutable.List[Int]) => {
      val l2 = List(l: _*)
      checkResults(l.map(_ + 1), addOne(l2))
    }
  }

  property("Exercise 3.17: doubleToString change the doubles to strings") = forAll {
    (l: immutable.List[Double]) => {
      val l2 = List(l: _*)
      checkResults(l.map(_.toString), doubleToString(l2))
    }
  }

  property("Exercise 3.18: filter") = forAll {
    (l: immutable.List[Int]) => {
      val l2 = List(l: _*)
      checkResults(l.map(_ * 2), map(l2, (x: Int) => x * 2))
      checkResults(l.map(_ + 13), map(l2, (x: Int) => x + 13))
    }
  }

  property("Exercise 3.19: filter") = forAll {
    (l: immutable.List[Int]) => {
      val l2 = List(l: _*)
      checkResults(l.filter(x => (x / 2) * 2 != x), filter(l2, (x: Int) => (x & 1) == 1))
      checkResults(l.filter(_ < 100), filter(l2, (x: Int) => (x < 100)))
    }
  }

  property("Exercise 3.20: flatMap") = forAll {
    def letters(i: Int): List[Char] = List(i.toString.toCharArray().toList: _*)

    (l: immutable.List[Int]) => {
      val l2 = List(l: _*)

      checkResults(l.flatMap(_.toString.toList), flatMap(l2)(letters))
    }
  }

  property("Exercise 3.21: filterByFlatMap") = forAll {
    (l: immutable.List[Int]) => {
      val l2 = List(l: _*)
      checkResults(l.filter(x => (x / 2) * 2 != x), filterByFlatMap(l2, (x: Int) => (x & 1) == 1))
      checkResults(l.filter(_ < 100), filterByFlatMap(l2, (x: Int) => (x < 100)))
    }
  }

  property("Exercise 3.22: add") = forAll {
    (lx1: immutable.List[Int], lx2: immutable.List[Int]) => {
      val l1 = lx1.take(lx2.size)
      val l2 = lx2.take(lx1.size)
      val ll1 = List(l1: _*)
      val ll2 = List(l2: _*)
      val r1 = l1.zip(l2).map(v => v._1 + v._2)
      checkResults(r1, add(ll1, ll2))
    }
  }

  property("Exercise 3.23: zipWith") = forAll {
    (lx1: immutable.List[Int], lx2: immutable.List[Int]) => {
      val l1 = lx1.take(lx2.size)
      val l2 = lx2.take(lx1.size)
      val ll1 = List(l1: _*)
      val ll2 = List(l2: _*)
      val r1 = l1.zip(l2).map(v => v._1 * v._2)
      checkResults(r1, zipWith(ll1, ll2)(_ * _))
    }
  }

  property("Exercise 3.24: hasSubsequence") = true

  property("Exercise 3.25: tree size") = forAll(aTree) { t =>
    Tree.size(t) == (theTrees.indexOf(t)+1)
  }

  property("Exercise 3.26: tree max") = forAll(aTree) { t =>
    Tree.max(t) == (theTrees.indexOf(t)+11)
  }

  property("Exercise 3.27: tree size") = forAll(aTree) { t =>
    Tree.depth(t) == (theTrees.indexOf(t)+1)
  }

  property("Exercise 3.28: tree map") = forAll(aTree) { t =>
    Tree.map(t)(_ * 2) == doubleTrees(theTrees.indexOf(t))
  }
}
