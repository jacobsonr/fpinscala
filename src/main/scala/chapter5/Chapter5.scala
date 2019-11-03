package com.maponics.fpinscala.chapter5

import annotation._
import Stream._

sealed trait Stream[+A] {

  def headOption: Option[A] = this match {
    case Empty => None
    case Cons(h, t) => Some(h())
  }

  // 5.1
  def toList: List[A] = ???

  // 5.2
  def take(n: Int): Stream[A] = ???

  // 5.2
  def drop(n: Int): Stream[A] = ???

  // 5.3
  def takeWhile(p: A => Boolean): Stream[A] = ???

  /////
  def exists1(p: A => Boolean): Boolean = ???

  /////
  def foldRight[B](z: => B)(f: (A, => B) => B): B = ???

  /////
  def exists(p: A => Boolean): Boolean = ???

  // 5.4
  def forAll(p: A => Boolean): Boolean = ???

  // 5.5
  def takeWhileb(p: A => Boolean): Stream[A] = ???

  // 5.6
  def headOptionb: Option[A] = ???

  // 5.7
  def map[B](f: A => B): Stream[B] = ???

  // 5.7
  def filter(p: A => Boolean): Stream[A] = ???

  // 5.7
  def append[B >: A](bb: => Stream[B]): Stream[B] = ???

  // 5.7
  def flatMap[B](f: A => Stream[B]): Stream[B] = ???

  // 5.13
  def mapb[B](f: A => B): Stream[B] = ???

  // 5.13
  def takeb(n: Int): Stream[A] = ???

  // 5.13
  def takeWhilec(p: A => Boolean): Stream[A] = ???

  // 5.13
  def zipWith[B, C](that: Stream[B])(f: (A, B) => C): Stream[C] = ???

  // 5.13
  def zipAll[B](that: Stream[B]): Stream[(Option[A], Option[B])] = ???

  // 5.14
  def startsWith[B](prefix: Stream[B]): Boolean = ???

  // 5.15
  def tails: Stream[Stream[A]] = ???

  // 5.16
  def scanRight[B](z: => B)(f: (A, => B) => B): Stream[B] = ???
  
}

case object Empty extends Stream[Nothing]
case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]

object Stream {

  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
    lazy val head = hd
    lazy val tail = tl
    Cons(() => head, () => tail)
  }

  def empty[A]: Stream[A] = Empty

  def apply[A](as: A*): Stream[A] =
    if (as.isEmpty) empty else cons(as.head, apply(as.tail: _*))

  val ones: Stream[Int] = cons(1, ones)

  // 5.8
  def constant[A](a: A): Stream[A] = ???

  // 5.9
  def from(n: Int): Stream[Int] = ???

  // 5.10
  def fibs(n1: Int = 0, n2: Int = 1): Stream[Int] = ???

  // 5.11
  def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream[A] = ???

  // 5.12
  def fibsb(): Stream[Int] = ???


  // 5.12
  def fromb(n: Int): Stream[Int] = ???

  // 5.12
  def constantb[A](a: A): Stream[A] = ???

  // 5.12
  def onesb(): Stream[Int] = ???

}
