package demo

import io.micronaut.http.annotation.*

@Controller("/")
class IndexController {

    /**
     * OK.
     *
     * curl http://localhost:8080/?name=outersky  -H "X-Session-Id: abc"
     *
     */
    @Get
    fun find1(@Header("X-Session-Id") sessionId: String, @QueryValue name: String): String {
        return """{"sessionId":"$sessionId", "name":"$name"}"""
    }

    /**
     * BUG? : name is ignored in swagger file.
     *
     * curl http://localhost:8080/find1?name=outersky
     *
     * @param name name is a query value by default in micronaut.
     */
    @Get("/find1")
    fun find2(name: String): String {
        return """{"name":"$name"}"""
    }

    /**
     * OK.
     *
     * params are expected in request body.
     *
     *  curl http://localhost:8080/ -H "Content-Type: application/json" -X POST -d '{"name":"outersky","phone":"0086+138"}'
     *
     * @param phone
     * @param name
     */
    @Post("/")
    fun create1(phone: String, name: String): String {
        return """{"phone":"$phone", "name":"$name"}"""
    }


    /**
     * BUG? : sessionId is duplicated in body params.
     *
     * curl http://localhost:8080/create2 -H "Content-Type: application/json" -H "X-Session-Id: abc" -X POST -d '{"name":"outersky","phone":"0086+138"}'
     *
     * @param sessionId auth token in header: X-Session-Id
     * @param phone phone
     * @param name name
     */
    @Post("/create2")
    fun create2(@Header("X-Session-Id") sessionId: String, phone: String, name: String): String {
        return """{"sessionId":"$sessionId", "phone":"$phone", "name":"$name"}"""
    }

}