package controllers

import java.time.OffsetDateTime
import javax.inject.{Inject, Singleton}

import com.github.t3hnar.bcrypt.Password
import models.db.{AccountRole, Tables}
import models.{Account, Entity, FormData, Message}
import play.api.Logger
import jp.t2v.lab.play2.auth._
import org.postgresql.util.PSQLException
import play.api.mvc.Controller
import services.db.DBService
import utils.db.TetraoPostgresDriver.api._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}

@Singleton
class PublicApplication @Inject()(val database: DBService, implicit val webJarAssets: WebJarAssets)
  extends Controller with AuthConfigTrait with OptionalAuthElement with LoginLogout {

  def index() = StackAction { implicit request =>
    Ok(views.html.index(loggedIn))
  }

  def accountList() = AsyncStack() { implicit request =>
    database.runAsync(Tables.Account.sortBy(_.id).result).map { rowSeq =>
      val accountSeq = rowSeq.map(Account(_))
      Ok(views.html.accountList(loggedIn,accountSeq))
    }
  }

  def employeePage(id: Int) = AsyncStack() { implicit request =>

    database.runAsync(Tables.Account.filter(_.id === id).result).map { row =>
      row.map(Account(_)).head
    } flatMap {
      account => database.runAsync(Tables.Message.filter(_.accountId === id).result).map { rowSeq =>
        val messageSeq = rowSeq.map(Message(_))
        Ok(views.html.employeePage(loggedIn, account, messageSeq))
      }
    }
  }

  def signUp() = StackAction() { implicit request =>
    Ok(views.html.signup(loggedIn, FormData.addAccount))
  }

  def signUpAddAccount() = AsyncStack() { implicit request =>
    FormData.addAccount.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(views.html.signup(loggedIn, formWithErrors))),
      accountFormData => {
        if(accountFormData.password.nonEmpty && accountFormData.password == accountFormData.passwordAgain) {
          val now = OffsetDateTime.now()
          val row = Tables.AccountRow(
            id = -1,
            name = accountFormData.name,
            email = accountFormData.email,
            password = accountFormData.password.bcrypt,
            role = AccountRole.normal,
            updatedAt = now,
            createdAt = now
          )
          database.runAsync((Tables.Account returning Tables.Account.map(_.id)) += row).map { id =>
            Logger.info(s"Inserted account#$id")
            Redirect(routes.Authentication.login())
          }
            .recover{
//            case e: PSQLException if(e.getSQLState() == "23505") => Redirect(routes.PublicApplication.signUp())
//                                                                    .flashing("doesUserExists" -> "User identified by this email already exists!")
              case e: PSQLException if(e.getSQLState() == "23505") => {
                val form = FormData.addAccount.fill(accountFormData).withError("userExists", "User identified by this email already exists!")
                BadRequest(views.html.signup(loggedIn, form))
              }
            case e: Exception => InternalServerError("Exception - Something happened..")
          }
        } else {
          val form = FormData.addAccount.fill(accountFormData).withError("passwordAgain", "Passwords don't match")
          Future.successful(BadRequest(views.html.signup(loggedIn, form)))
        }
      }
    )
  }

}
