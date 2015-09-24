package computerdatabase

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class Books extends Simulation {
  // your code starts here

  val scn = scenario("books app")
    .exec(http("Home Page")
      .get("http://172.27.59.185:5062"))
    .pause(1)
    
    .exec(http("data")
      .get("http://172.27.59.185:5062/api/Books"))
    .pause(1)
   
   
   
  setUp(scn.inject(rampUsers(100) over (10 seconds)))
  //  setUp(scn.inject(atOnceUsers(100)))
  // your code ends here
}
