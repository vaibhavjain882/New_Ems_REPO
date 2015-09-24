package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class EMSHome extends Simulation {
  // your code starts here

  val scn = scenario("EMS Scenario")
    .exec(http("Home Page")
      .get("http://172.27.59.6:8080/EmployeeApplication/home.htm"))
    .pause(10)
    .exec(http("request_add")
      .post("http://172.27.59.6:8080/EmployeeApplication/jsp/add.htm")
      .formParam("""code""", """1""") // Note the triple double quotes: used in Scala for protecting a whole chain of characters (no need for backslash)
      .formParam("""name""", """a""")
      .formParam("""city""", """b"""))
    .pause(1)
    .exec(http("request_edit")
      .get("http://172.27.59.6:8080/EmployeeApplication/edit.jsp?code=1&name=a&city=a"))
    .pause(1)
    .exec(http("request_edit submit")
      .post("http://172.27.59.6:8080/EmployeeApplication/edit.htm")
      .formParam("""code""", """1""")
      .formParam("""name""", """b""")
      .formParam("""city""", """c"""))
    .pause(1)
    .exec(http("request_delete")
      .get("http://172.27.59.6:8080/EmployeeApplication/delete.jsp?code=1"))
    .pause(10)
    .exec(http("request_delete submit")
      .post("http://172.27.59.6:8080/EmployeeApplication/delete.htm")
      .formParam("""code""", """1"""))

  setUp(scn.inject(rampUsers(100) over (10 seconds)))
  //  setUp(scn.inject(atOnceUsers(100)))
  // your code ends here
}