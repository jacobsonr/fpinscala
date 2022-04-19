package com.maponics.fpinscala.chapter6

import annotation._
import Stream._
import State._

trait RNG {
  def nextInt: (Int, RNG)
}

case class SimpleRNG(seed: Long) extends RNG {
  def nextInt: (Int, RNG) = {
    val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL
    val nextRNG = SimpleRNG(newSeed)
    val n = (newSeed >>> 16).toInt
    (n, nextRNG)
  }
}

object SimpleRNG {
  // 6.1
  def nonNegativeInt(rng: RNG): (Int, RNG) = ???

  // 6.2
  def double(rng: RNG): (Double, RNG) = ???

  // 6.3
  def intDouble(rng: RNG): ((Int, Double), RNG) = ???

  def doubleInt(rng: RNG): ((Double, Int), RNG) = ???

  def double3(rng: RNG): ((Double,Double,Double), RNG) = ???

  // 6.4
  def ints(count: Int)(rng: RNG): (List[Int], RNG) = ???

  type Rand[+A] = RNG => (A, RNG)

  val int: Rand[Int] = _.nextInt

  def unit[A](a: A): Rand[A] = rng => (a, rng)

  def map[A,B](s: Rand[A])(f: A => B): Rand[B] =
    rng => {
      val (a, rng2) = s(rng)
      (f(a), rng2)
    }

  def nonNegativeEven: Rand[Int] =
    map(nonNegativeInt)(i => i - i % 2)

  // 6.5
  def doubleViaMap: Rand[Double] = ???


  // 6.6
  def map2[A, B, C](ra: Rand[A], rb: Rand[B])(f: (A, B) => C): Rand[C] = rng => {
    ???
  }

  def both[A,B](ra: Rand[A], rb: Rand[B]): Rand[(A,B)] =
    map2(ra, rb)((_, _))

  val randIntDouble: Rand[(Int, Double)] =
    both(int, double)

  val randDoubleInt: Rand[(Double, Int)] =
    both(double, int)

  // 6.7
  def sequence[A](fs: List[Rand[A]]): Rand[List[A]] = ???

  def intsViaSequence(count: Int): Rand[List[Int]] = ???

  // 6.8
  def flatMap[A, B](f: Rand[A])(g: A => Rand[B]): Rand[B] = ???

  // 6.9
  def mapViaFlatMap[A, B](s: Rand[A])(f: A => B): Rand[B] = ???

  def map2ViaFlatMap[A, B, C](ra: Rand[A], rb: Rand[B])(f: (A, B) => C): Rand[C] = ???

  def bothViaFlatMap[A,B](ra: Rand[A], rb: Rand[B]): Rand[(A,B)] =
    map2ViaFlatMap(ra, rb)((_, _))

  def randIntDoubleViaFlatMap: Rand[(Int, Double)] =
    bothViaFlatMap(int, double)

  def randDoubleIntViaFlatMap: Rand[(Double, Int)] =
    bothViaFlatMap(double, int)

}

case class State[S, +A](run: S => (A, S)) {

  // 6.10

  def map[B](f: A => B): State[S, B] = ???

  def map2[B, C](sb: State[S,B])(f: (A, B) => C): State[S,C] = ???

  def flatMap[B](f: A => State[S,B]): State[S,B] = ???

}

object State {

  def unit[S, A](a: A): State[S, A] = ???

  def sequence[S,A](sas: List[State[S, A]]): State[S, List[A]] = ???

}


sealed trait Input
case object Coin extends Input
case object Turn extends Input

case class Machine(locked: Boolean, candies: Int, coins: Int)

object Candy {

  def simulateMachine(inputs: List[Input]): State[Machine, (Int, Int)] = ???

}
