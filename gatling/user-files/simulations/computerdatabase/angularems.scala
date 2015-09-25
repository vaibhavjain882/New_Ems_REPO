package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class angularems extends Simulation {
  // your code starts here

  val scn = scenario("EMS Scenario")
    .exec(http("Home Page")
      .get("http://172.27.59.185:5005/#/home"))
    .pause(10)
    .exec(http("request_add")
      .post("http://172.27.59.185:5005/#/add")
      .formParam("""code""", """1""") // Note the triple double quotes: used in Scala for protecting a whole chain of characters (no need for backslash)
      .formParam("""name""", """a""")
      .formParam("""city""", """b"""))
    .pause(1)
     .exec(http("list page")
      .get("http://172.27.59.185:5005/#/home"))
    .pause(10)
  setUp(scn.inject(rampUsers(100) over (10 seconds)))
  //  setUp(scn.inject(atOnceUsers(100)))
  // your code ends here
}
