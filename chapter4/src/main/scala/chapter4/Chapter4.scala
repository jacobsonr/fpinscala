package com.maponics.fpinscala.chapter4

import annotation._


object Option {
  def mean(xs: Seq[Double]): Option[Double] =
    if (xs.isEmpty) None
    else Some(xs.sum / xs.length)

  // 4.2
  def variance(xs: Seq[Double]): Option[Double] = {
    ???
  }

  // 4.3
  def map2[A, B, C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] = {
    ???
  }

  // 4.4
  def sequence[A](a: List[Option[A]]): Option[List[A]] = {
    ???
  }

  // 4.5
  def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] = {
    ???
  }

  def sequenceViaTraverse[A](a: List[Option[A]]): Option[List[A]] = {
    ???
  }

}

object Either {
  // 4.7
  def sequenceE[E, A](es: List[Either[E, A]]): Either[E, List[A]] = {
    ???
  }

  def traverseE[E, A, B](as: List[A])(f: A => Either[E, B]): Either[E, List[B]] = {
    ???
  }
}

sealed trait Option[+A] {

  // 4.1
  def map[B](f: A => B): Option[B] = {
    ???
  }

  def flatMap[B](f: A => Option[B]): Option[B] = {
    ???
  }

  def getOrElse[B >: A](default: => B): B = {
    ???
  }

  def orElse[B >: A](ob: => Option[B]): Option[B] = {
    ???
  }

  def filter(f: A => Boolean): Option[A] = {
    ???
  }

}

case class Some[+A](get: A) extends Option[A]
case object None extends Option[Nothing]

sealed trait Either[+E, +A] {

  // 4.6
  def map[B](f: A => B): Either[E, B] = {
    ???
  }

  def flatMap[EE >: E, B](f: A => Either[EE, B]): Either[EE, B] = {
    ???
  }

  def orElse[EE >: E, B >: A](b: => Either[EE, B]): Either[EE, B] = {
    ???
  }

  def map2[EE >: E, B, C](b: Either[EE, B])(f: (A, B) => C): Either[EE, C] = {
    ???
  }

  // 4.8
  // Think about this one

}

case class Left[+E](value: E) extends Either[E, Nothing]
case class Right[+A](value: A) extends Either[Nothing, A]
