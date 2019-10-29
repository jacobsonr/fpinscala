package com.maponics.fpinscala.chapter3

import annotation._

sealed trait List[+A]
case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List {

  def sum(ints: List[Int]): Int = ints match {
    case Nil => 0
    case Cons(x, xs) => x + sum(xs)
  }

  def product(ds: List[Double]): Double = ds match {
    case Nil => 1.0
    case Cons(x, xs) => x * product(xs)
  }

  def apply[A](as: A*): List[A] =
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))

  // Exercise 3.2
  def tail[A](l: List[A]): List[A] = ???

  // Exercise 3.3
  def setHead[A](l: List[A], a: A): List[A] = ???

  // Exercise 3.4
  def drop[A](l: List[A], n: Int): List[A] = ???

  // Exercise 3.5
  def dropWhile[A](l: List[A])(f: A => Boolean): List[A] = ???

  def foldRight[A, B](as: List[A], z: B)(f: (A, B) => B): B = as match {
    case Nil => z
    case Cons(x, xs) => f(x, foldRight(xs, z)(f))
  }

  def sum2(ns: List[Int]) =
    foldRight(ns, 0)((x, y) => x + y)

  def product2(ns: List[Double]) =
    foldRight(ns, 1.0)(_ * _)

  // Exercise 3.6
  def init[A](l: List[A]): List[A] = ???

  // Exercise 3.9
  def length[A](l: List[A]): Int = ???

  // Exercise 3.10
  def foldLeft[A, B](as: List[A], z: B)(f: (B, A) => B): B = ???

  // Exercise 3.11
  def sumByFoldLeft(ints: List[Int]): Int = ???

  def productByFoldLeft(ds: List[Double]): Double = ???

  def lengthByFoldLeft[A](l: List[A]): Int = ???

  // Exercise 3.12
  def reverse[A](l: List[A]): List[A] = ???

  // Exercise 3.13 (hard)
  // Are these possible?
  def foldLeftByFoldRight[A, B](as: List[A], z: B)(f: (B, A) => B): B = ???

  def foldRightByFoldLeft[A, B](as: List[A], z: B)(f: (A, B) => B): B = ???

  // Exercise 3.14
  // Use foldLeft or foldRight
  def append[A](l: List[A], a: A): List[A] = ???

  // Exercise 3.15 (hard)
  def flatten[A](l: List[List[A]]): List[A] = ???

  // Exercise 3.16
  def addOne(l: List[Int]): List[Int] = ???

  // Exercise 3.17
  def doubleToString(ds: List[Double]): List[String] = ???

  // Exercise 3.18
  def map[A, B](as: List[A], f: A => B): List[B] = ???

  // Exercise 3.19
  def filter[A](l: List[A], f: A => Boolean): List[A] = ???

  // Exercise 3.20
  def flatMap[A, B](as: List[A])(f: A => List[B]): List[B] = ???

  // Exercise 3.21
  def filterByFlatMap[A](l: List[A], f: A => Boolean): List[A] = ???

  // Exercise 3.22
  def add(ints1: List[Int], ints2: List[Int]): List[Int] = ???

  // Exercise 3.23
  // def zipWith ...

  // Exercise 3.24
  def hasSubsequence[A](sup: List[A], sub: List[A]): Boolean = ???
}


sealed trait Tree[+A]
case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]


object Tree {

  // Exercise 3.25
  def size[A](t: Tree[A]): Int = ???

  // Exercise 3.26
  def max(t: Tree[Int]): Int = ???

  // Exercise 3.27
  def depth[A](t: Tree[A]): Int = ???

  // Exercise 3.28
  def map[A, B](t: Tree[A], f: A => B): Tree[B] = ???

}
