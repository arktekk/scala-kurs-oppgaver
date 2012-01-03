import minitest._

class Oppgave5 extends MiniTest {
  /*
  var i = 0

  before {
    i = i + 5
  }

  after {
    i = i + 10
  }

  test("i == 5"){
    // i -> before
    // 0 + 5
    assertEq(i, 5)
  }

  test("i == 20"){
    // i -> before -> after -> before
    // 0 + 5 + 10 + 5 == 20
    assertEq(i, 20)
  }
  */
}

object RunOppgave5 extends Oppgave5 with App {
  // test:run-main RunOppgave5
}