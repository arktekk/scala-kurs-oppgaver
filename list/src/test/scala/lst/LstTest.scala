package lst

import org.scalatest.FunSuite
import org.scalatest.prop.Checkers
import org.scalacheck.Prop._

class LstTest extends FunSuite with Checkers {

  def toLst[A](list: List[A]) = list.foldRight(Empty: Lst[A])(Cons(_, _))

  test("isEmpty") {
    check { list: List[Int] =>
      toLst(list).isEmpty == list.isEmpty
    }
  }

  test("size") {
    check { list: List[Int] =>
      toLst(list).size == list.size
    }
  }

  test("head") {
    intercept[NoSuchElementException]{
      Empty.head
    }

    check { list:List[Int] =>
      !list.isEmpty ==> (toLst(list).head == list.head)
    }
  }

  test("tail"){
    intercept[NoSuchElementException]{
      Empty.tail
    }

    check{ list:List[Int] =>
      !list.isEmpty ==> (toLst(list).tail == toLst(list.tail))
    }
  }

  test("isDefinedAt"){
    check{ (i:Int, list:List[Int]) =>
      toLst(list).isDefinedAt(i) == list.isDefinedAt(i)
    }
  }

  test("apply"){
    check{ (i:Int, list:List[Int]) =>
      if(list.isDefinedAt(i)){
        list(i) == toLst(list)(i)
      } else throws(classOf[NoSuchElementException]){
        toLst(list)(i)
      }
    }
  }

  test("map"){
    def f(i:Int) = i * 2

    check{ list:List[Int] =>
      toLst(list).map(f) == toLst(list.map(f))
    }
  }

  test("append"){
    check{ (listA:List[Int], listB:List[Int]) =>
      toLst(listA).append(toLst(listB)) == toLst(listA ++ listB)
    }
  }


  test("flatMap"){
    check{ list:List[Int] =>
      val lstResult = toLst(list).flatMap{i => Cons(i, Cons(i, Cons(i, Empty)))}
      val listResult = list.flatMap{ i => List(i, i, i) }
      lstResult == toLst(listResult)
    }
  }

  test("filter"){
    def f(i:Int) = i % 2 == 0

    check{ list:List[Int] =>
      toLst(list).filter(f) == toLst(list.filter(f))
    }
  }

  test("reverse"){
    check{ list:List[Int] =>
      toLst(list).reverse == toLst(list.reverse)
    }
  }

  test("foldLeft"){
    check{ list:List[Int] =>
      toLst(list).foldLeft(List[Int]()){(a, b) => b :: a } == list.reverse
    }
  }

  test("foldRight"){
    check{ list:List[Int] =>
      toLst(list).foldRight(List[Int]()){_ :: _} == list
    }
  }

  test("flatten"){
    check{ list:List[List[Int]] =>
      toLst(list.map(toLst)).flatten == toLst(list.flatten)
    }
  }

  test("sum"){
    check{ list:List[Int] =>
      toLst(list).sum == list.sum
    }
  }

  test("Lst.apply"){
    check{ list:List[Int] =>
      Lst(list :_*) == toLst(list)
    }
  }
}