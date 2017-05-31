package models

import play.api.Play.current
import play.api.db.DB

case class Temperature(id: Int, date: String, temp: Double)

case object Temperature {
  private var temperatures = getTemperatures

  def getTemperatures = {
    val conn = DB.getConnection()
    var listOfTemp: Set[Temperature] = Set()
    try {
      val stmt = conn.createStatement
      val rs = stmt.executeQuery("SELECT * FROM temp")
      while (rs.next()) {
        listOfTemp += new Temperature(rs.getInt("id"), rs.getString("date"), rs.getDouble("temperature"))
      }
    } finally {
      conn.close()
    }
    listOfTemp
  }

  def findAll = temperatures.toList.sortBy(_.id)

  def findById(id: Int) = temperatures.find(_.id == id)

  def findLast = {
    temperatures.toList.sortBy(_.id)
    val listOfElements = temperatures.toList
    val lastElement = listOfElements.lift(listOfElements.size - 1)
    lastElement
  }

  def add(temperature: Temperature) = {
    temperatures += temperature

    val conn = DB.getConnection()
    try {
      val stmt = conn.prepareStatement("INSERT INTO temp(date, temperature) VALUES(?, ?)")
      stmt.setString(1, temperature.date)
      stmt.setDouble(2, temperature.temp)
      stmt.execute()
    } finally {
      conn.close()
    }
  }

  def delete(id: Int) = {
    val toDel = findById(id)

    toDel.map(x => temperatures = temperatures - x)
    val conn = DB.getConnection()
    try {
      val stmt = conn.prepareStatement("DELETE FROM temp WHERE id = ?")
      stmt.setInt(1, id)
      stmt.execute()
    } finally {
      conn.close()
    }
  }


  def update(temperature: Temperature) = {
    delete(temperature.id)
    add(temperature)
  }
}
