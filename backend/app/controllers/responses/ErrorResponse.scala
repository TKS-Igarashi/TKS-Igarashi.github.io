package controllers.responses

import play.api.libs.json.*

final case class ErrorResponse(code: String, message: String)

object ErrorResponse:
  given Writes[ErrorResponse] = Writes { error =>
    Json.obj("error" -> Json.obj("code" -> error.code, "message" -> error.message))
  }