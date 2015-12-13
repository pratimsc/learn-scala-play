package models

/**
  * Created by pratimsc on 12/12/15.
  */
case class Artist(id:Long, name: String, country: String)

object Artist {
  val availableArtist = Seq(
    Artist(1,"Wolfgang Amadeus Mozart", "Austria"),
    Artist(2,"Ludwig van Beethoven", "Germany"),
    Artist(3,"Johann Sebastian Bach", "Germany"),
    Artist(4,"Frédéric François Chopin", "Poland"),
    Artist(5,"Joseph Haydn", "Austria"),
    Artist(6,"Antonio Lucio Vivaldi", "Italy"),
    Artist(7,"Franz Peter Schubert", "Austria"),
    Artist(8,"Franz Liszt", "Austria"),
    Artist(9,"Giuseppe Fortunino Francesco Verdi", "Austria")
  ).map(a => Artist(a.id, a.name.toUpperCase, a.country.toUpperCase()))

  def fetch: Seq[Artist] = availableArtist

  def fetchById(id:Long): Seq[Artist] = availableArtist.filter(_.id == id)

  def fetchByName(n:String): Seq[Artist] = availableArtist.filter(_.name.contains(n.toUpperCase))

  def fetchByCountry(c: String) = availableArtist.filter(_.country.contains(c.toUpperCase))

  def fetchByNameOrCountry(n: String, c: String) = availableArtist.filter(a => a.name.contains(n.toUpperCase) || a.country.contains(c.toUpperCase))

  def fetchByNameAndCountry(n: String, c: String) = availableArtist.filter(a => a.name.contains(n.toUpperCase) && a.country.contains(c.toUpperCase))

}