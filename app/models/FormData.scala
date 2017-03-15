package models

import play.api.data.{Form, Mapping}
import play.api.data.Forms._
import play.api.data.validation.{Constraint, Invalid, Valid, ValidationError}

import scala.util.matching.Regex

case class FormDataLogin(email: String, password: String)

case class FormDataAccount(name:String, email: String, password: String, passwordAgain:String)

object FormData {

  val login = Form(
    mapping(
      "email" -> email,
      "password" -> nonEmptyText
    )(FormDataLogin.apply)(FormDataLogin.unapply)
  )

  val addMessage = Form(
    mapping(
      "content" -> nonEmptyText,
      "tags" -> text
    )(Message.formApply)(Message.formUnapply)
  )
  val atLeastOneUpperLetterAndAtLeasOneSpecialChar = """(?=.*[A-Z])(?=.*[@#$%^&+=]).*"""

  private[this] def accountForm(passwordMapping:Mapping[String]) = Form(
    mapping(
      "name" -> nonEmptyText,
      "email" -> email,
      "password" -> passwordMapping,
      "passwordAgain" -> passwordMapping
    )(FormDataAccount.apply)(FormDataAccount.unapply)
  )

  val updateAccount = accountForm(text)

  val addAccount = accountForm(text
    .verifying("Password must have from 5 to 10 chars", text => text.length() >= 5 && text.length() <= 10 && !text.contains("\\s"))
    .verifying("Password must have at least one special char (@#$%^&+=) and one uppercase letter",
        name => name.matches(atLeastOneUpperLetterAndAtLeasOneSpecialChar)))


}
