package lst

sealed trait Lst[+A] extends PartialFunction[Int, A]{
  
  import Lst.todo

  // returnerer om listen er tom
  def isEmpty:Boolean = todo

  // returnerer størrelsen på listen
  def size:Int = todo

  // henter første elementet i listen (kaster NoSuchElementException ved tom liste)
  def head:A = todo

  // henter halen til listen (kaster NoSuchElementException ved tom liste)
  def tail:Lst[A] = todo

  // finnes elementet med gitt index
  def isDefinedAt(x: Int):Boolean = todo

  // hent elementet med gitt index eller kast exception
  def apply(x: Int):A = todo

  // returner en ny liste ved å kalle funksjonen for hvert element i lista
  def map[B](f:A => B):Lst[B] = todo

  // legg "other" til på slutten av denne lista
  def append[AA >: A](other:Lst[AA]):Lst[AA] = todo

  // returnerer en ny liste vel å kalle funksjonen f for alle elementene og appende resultatene etter hverandre
  // f.eks Cons(1, Cons(2, Nil)).flatMap(a => Cons(a, Cons(a + 1, Empty))) == Cons(1, Cons(2, Cons(2, Cons(3, Nil))))
  def flatMap[B](f:A => Lst[B]):Lst[B] = todo

  // returner en liste som inneholder all elementer som er 'true' for predikatet f
  def filter(f:A => Boolean):Lst[A] = todo

  // returnerer listen reversert
  def reverse:Lst[A] = todo

  // Cons(1, Cons(2, Cons(3, Nil)).foldLeft(10)(f)
  // f(f(f(10, 1), 2), 3)
  // http://upload.wikimedia.org/wikipedia/commons/5/5a/Left-fold-transformation.png
  // @annotation.tailrec
  final def foldLeft[B](acc:B)(f:(B, A) => B):B = todo

  // Cons(1, Cons(2, Cons(3, Nil))).foldRight(10)(f)
  // f(3, f(2, f(3, 10)))
  // http://upload.wikimedia.org/wikipedia/commons/3/3e/Right-fold-transformation.png
  final def foldRight[B](acc:B)(f:(A, B) => B):B = todo

  // returnerer en liste flatet ut (om det er mulig, ellers compile error)
  // f.eks. Cons(Cons(1, Nil), Cons(2, Nil)).flatten == Cons(1, Cons(2, Nil))
  def flatten[B](implicit f:A => Lst[B]):Lst[B] = todo

  // returnerer summen av elementene i listen (om den inneholder nummer, ellers compile error)
  def sum[B >: A](implicit num:Numeric[B]):B = todo
}

final case class Cons[A] (x: A, xs:Lst[A]) extends Lst[A]

case object Empty extends Lst[Nothing]

object Lst {
  def apply[A](a:A*):Lst[A] = todo

  private [Lst] def todo = sys.error("todo")
}