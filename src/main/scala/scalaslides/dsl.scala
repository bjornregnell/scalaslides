package scalaslides

case class Slide(title: String, body: String)

case class Deck(slides: Seq[Slide]) {
  def select(titles: String*): Deck =
    copy(titles.flatMap(t => slides.find(_.title == t)))
}

case class Presentation(slides: Slide*)
object Presentation {
  def apply(deck: Deck): Presentation = new Presentation(deck.slides: _*)
}
