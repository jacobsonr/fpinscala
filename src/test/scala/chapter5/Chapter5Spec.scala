package com.maponics.fpinscala.chapter5

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll
import org.scalacheck.Gen.{choose, oneOf}
import scala.collection.immutable
import com.maponics.fpinscala.chapter5._
import com.maponics.fpinscala.chapter5.Stream._

class Blah
/** Tests for Functional Programming in Scala chapter 5 exercises
  *
  * These are not meant to be exhaustive, but should be enough to
  * guide you to the answer if you get the tests to pass.
  */
object Chapter5Spec extends Properties("List")
{
  property("Exercise 5.1: toList works") = forAll {
    (l: List[AnyVal]) => {
      val stream = Stream(l: _*)
      val list = stream.toList
      l == list
    }
  }

  property("Exercise 5.2: take works") = forAll {
    (l: List[AnyVal], n: Int) => {
      val nTake = (n % (l.size + 6)) - 3  // Test -3 to size + 2
      val stream = Stream(l: _*)
      val taken = stream.take(nTake).toList
      l.take(nTake) == taken
    }
  }


  property("Exercise 5.2: drop works") = forAll {
    (l: List[AnyVal], n: Int) => {
      val nDrop = (n % (l.size + 6)) - 3  // Test -3 to size + 2
      val stream = Stream(l: _*)
      val dropped = stream.drop(nDrop).toList
      l.drop(nDrop) == dropped
    }
  }

  property("Exercise 5.3: takeWhile works") = forAll {
    (l: List[Int], n: Int) => {
      val stream = Stream(l: _*)
      stream.takeWhile(_ < n).toList == l.takeWhile(_ < n)
    }
  }

  property("Exercise 5.4: forAll works") = forAll {
    (l: List[Int], n: Int) => {
      val stream = Stream(l: _*)
      stream.forAll(_ < n) == l.forall(_ < n)
    }
  }

  property("Exercise 5.5: takeWhile[foldRight] works") = forAll {
    (l: List[Int], n: Int) => {
      val stream = Stream(l: _*)
      stream.takeWhileb(_ < n).toList == l.takeWhile(_ < n)
    }
  }

  property("Exercise 5.6: headOption[foldRight] works") = forAll {
    (l: List[AnyVal]) => {
      val stream = Stream(l: _*)
      stream.headOptionb == l.headOption
    }
  }

  property("Exercise 5.7: map[foldRight] works") = forAll {
    (l: List[AnyVal]) => {
      val stream = Stream(l: _*)
      stream.map(x => x + x.toString).toList == l.map(x => x + x.toString)
    }
  }

  def filterFun(x: AnyVal) = (x.toString.length < 5)

  property("Exercise 5.7: filter[foldRight] works") = forAll {
    (l: List[AnyVal]) => {
      val stream = Stream(l: _*)
      stream.filter(filterFun).toList == l.filter(filterFun)
    }
  }

  property("Exercise 5.7: append[foldRight] works") = forAll {
    (l: List[AnyVal], l2: List[AnyVal]) => {
      val stream = Stream(l: _*)
      val stream2 = Stream(l2: _*)

      stream.append(stream2).toList == l ++ (l2)
    }
  }

  property("Exercise 5.7: flatMap[foldRight] works") = forAll {
    (l: List[AnyVal], l2: List[AnyVal]) => {
      val stream = Stream(l: _*)
      stream.flatMap(x => Stream(l2: _*)).toList == l.flatMap(x => l2)
    }
  }

  property("Exercise 5.8: constant works") = forAll {
    (v: AnyVal, n: Int) => {
      val t = n % 100
      Stream.constant(v).take(t).toList == List.fill(t)(v)
    }
  }

  property("Exercise 5.9: from works") = forAll {
    (start: Int, n: Int) => {
      val t = (n & 1023).min(Int.MaxValue - start.max(0))
      Stream.from(start).take(t).toList == (start until start + t).toList
    }
  }

  property("Exercise 5.10: fibs works") = forAll {
    (nn: Int) => {
      val n = nn & 1023
      val f = fibs().take(1028).toList
      f(0) == 0 &&
      f(1) == 1 // &&
      f(n + 2) == f(n) + f(n + 1)
    }
  }

  property("Exercise 5.11: unfold works") = forAll {
    (start: Int, step: Int) => {
      unfold(start)(i => Some(i, i + step)).take(3).toList == List(start, start + step, start + step * 2)
    }
  }  

  property("Exercise 5.12: fibs[unfold] works") = forAll {
    (nn: Int) => {
      val n = nn & 1023
      val f = fibsb().take(1028).toList
      f(0) == 0 &&
      f(1) == 1 // &&
      f(n + 2) == f(n) + f(n + 1)
    }
  }

  property("Exercise 5.13: from[unfold] works") = forAll {
    (start: Int, n: Int) => {
      val t = (n & 1023).min(Int.MaxValue - start.max(0))
      Stream.fromb(start).take(t).toList == (start until start + t).toList
    }
  }

  property("Exercise 5.13: map[unfold] works") = forAll {
    (l: List[AnyVal]) => {
      val stream = Stream(l: _*)
      stream.mapb(x => x + x.toString).toList == l.map(x => x + x.toString)
    }
  }

  property("Exercise 5.13: take[unfold] works") = forAll {
    (l: List[AnyVal], n: Int) => {
      val nTake = (n % (l.size + 6)) - 3  // Test -3 to size + 2
      val stream = Stream(l: _*)
      val taken = stream.takeb(nTake).toList
      l.take(nTake) == taken
    }
  }

  property("Exercise 5.13: takeWhile[unfold] works") = forAll {
    (l: List[Int], n: Int) => {
      val stream = Stream(l: _*)
      stream.takeWhilec(_ < n).toList == l.takeWhile(_ < n)
    }
  }

  property("Exercise 5.13: zipWith[unfold] works") = forAll {
    (l: List[Int], l2: List[Int]) => {
      val stream = Stream(l: _*)
      val stream2 = Stream(l2: _*)
      stream.zipWith(stream2)(_ + _).toList == (l.zip(l2).map {case (a, b) => a + b})
    }
  }
  

  property("Exercise 5.13: zipAll[unfold] works") = forAll {
    (l: List[Int], l2: List[Int]) => {
      val ol = l.map(Some(_)) ++ List.fill(l2.size - l.size)(None)
      val ol2 = l2.map(Some(_)) ++ List.fill(l.size - l2.size)(None)

      val stream = Stream(l: _*)
      val stream2 = Stream(l2: _*)
      stream.zipAll(stream2).toList == ol.zip(ol2)
    }
  }
  
  property("Exercise 5.14: startsWith works with random streams") = forAll {
    (l: List[Int], l2: List[Int]) => {
      val stream = Stream(l: _*)
      val stream2 = Stream(l2: _*)

      stream.startsWith(stream2) == l.startsWith(l2)
    }
  }

  property("Exercise 5.14: startsWith works with matching prefixes") = forAll {
    (l: List[Int], nn: Int) => {
      val n = if (l.isEmpty) 0 else nn.abs % l.size
      val stream = Stream(l: _*)
      val stream2 = Stream(l.take(n): _*)

      stream.startsWith(stream2)
    }
  }

  property("Exercise 5.14: startsWith works with infitine streams") = {
    Stream.from(10).startsWith(Stream((10 to 1000): _*)) &&
    !Stream.from(10).startsWith(Stream(11))
  }


  property("Exercise 5.15: tails works") = forAll {
    (l: List[AnyVal]) => {
      val stream = Stream(l: _*)
      stream.tails.map(_.toList).toList == (0 to l.size).map(l.drop(_))
    }
  }

  property("Exercise 5.16: scanRight works") = forAll {
    (l: List[Int]) => {
      val stream = Stream(l: _*)
      stream.scanRight(0)(_ + _).toList == l.scanRight(0)(_ + _)
    }
  }
  
}
