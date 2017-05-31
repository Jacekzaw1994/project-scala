package controllers

import models.Temperature
import play.api.data.Forms._
import play.api.data._
import play.api.mvc.{Action, Controller}
import play.api.data.format.Formats._

object Temperatures extends Controller {

  val temperatureForm: Form[Temperature] = Form(
    mapping(
      "id" -> number(min=0),
      "date" -> nonEmptyText,
      "temp" ->  of(doubleFormat)
    )(Temperature.apply)(Temperature.unapply)
  )

  def addNew = Action {
    Ok(views.html.temperatures.edit(temperatureForm))
  }

  def view = Action {
    Ok(views.html.temperatures.view(Temperature.findAll))
  }


  def delete(id:Int) = Action {
    Temperature.delete(id)
    Ok(views.html.temperatures.view(Temperature.findAll))
  }

  def addTemperature = Action { implicit request =>
    val temperature = temperatureForm.bindFromRequest.get
    models.Temperature.update(temperature)
    Redirect(routes.Application.index())
  }

  def addTemperatures(id: Int, date: String, temperature: Double) = Action {
    //val lastId = Temperature.findLast
    val newTemp = Temperature(id, date, temperature)
    Temperature.add(newTemp)
    Ok(views.html.temperatures.view(Temperature.findAll))
    Redirect(routes.Temperatures.view())
  }

  def save = Action {implicit request =>

    temperatureForm.bindFromRequest.fold(
      formWithErrors => {
        // binding failure, you retrieve the form containing errors:
        BadRequest(views.html.temperatures.edit(formWithErrors))
      },
      x => {
        /* binding success, you get the actual value. */
        models.Temperature.update(x)
        Redirect(routes.Temperatures.view())
      }
    )
  }
}