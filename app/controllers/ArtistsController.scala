package controllers

import java.io.File

import play.api.Logger
import play.api.libs.json.JsValue
import play.api.mvc.{AnyContent, BodyParsers, Controller, Action}
import models.Artist

/**
  * Created by pratimsc on 12/12/15.
  */
class ArtistsController extends Controller {

  def listArtist = Action {
    Logger.debug("I am here at listArtist")
    Ok(views.html.home(Artist.fetch))
  }

  def fetchArtistById(id: Long) = Action {
    Logger.debug("""I am at fetchArtistById(id:Long)""")
    Ok(views.html.home(Artist.fetchById(id)))
  }

  def search(name: Option[String], country: Option[String]) = Action {
    Logger.debug("""I am here at search(name:Option[String],country:Option[String])""")
    val r = (name, country) match {
      case (None, Some(c)) => Artist.fetchByCountry(c)
      case (Some(n), None) => Artist.fetchByName(n)
      case (Some(n), Some(c)) => Artist.fetchByNameOrCountry(n, c)
      case (_, _) => Nil
    }
    Logger.debug(s"The number of artists returned is ${r.length}")
    if (r.isEmpty) NoContent
    else Ok(views.html.home(r))
  }

  def search2(name: String, country: String) = Action {
    Logger.debug("""I am at search2(name:String,country:String)""")
    val r = Artist.fetchByNameAndCountry(name, country)
    if (r.isEmpty) NoContent
    else Ok(views.html.home(r))
  }

  def subscribe = Action(parse.tolerantJson) { request =>
    Logger.debug("Content type is >>>>>>" + request.headers.get("Content-Type").getOrElse("NO CONTENT TYPE"))
    val json:JsValue = request.body
    Ok(s"Added email id [${(json \ "emailId").as[String]}] to subsciber's list and will send email every [${(json \ "interval").as[String]}]")

  }

  def createProfile = Action(parse.multipartFormData) { request =>
    val formData = request.body.asFormUrlEncoded
    val email = formData.get("email") match {
      case Some(e) => e.seq(0)
      case None => "NO EMAIL"
    }
    val name = formData.get("name") match {
      case Some(n) => n.seq(0)
      case None => "NO NAME"
    }
    Logger.debug(s"I am at [createProfile]. The profile being created is [${email}] and [${name}]")

    request.body.file("profilePicture").map { profilePicture =>
      if (!profilePicture.filename.isEmpty) profilePicture.ref.moveTo(new File("/tmp/" + profilePicture.filename), replace = true)
    }
    Created(s"The profile was registered with data email -> [${email}] and name -> [${name}]")
  }

  def fetchAllStudents = TODO
}
