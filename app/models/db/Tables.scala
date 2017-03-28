package models.db

// AUTO-GENERATED Slick data model [2017-03-28T12:59:04.043+02:00[Europe/Berlin]]

/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = utils.db.TetraoPostgresDriver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: utils.db.TetraoPostgresDriver
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Account.schema ++ Message.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Account
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(text)
   *  @param surname Database column surname SqlType(text)
   *  @param email Database column email SqlType(text)
   *  @param telephone Database column telephone SqlType(text)
   *  @param password Database column password SqlType(text)
   *  @param role Database column role SqlType(account_role)
   *  @param createdAt Database column created_at SqlType(timestamptz)
   *  @param updatedAt Database column updated_at SqlType(timestamptz) */
  case class AccountRow(id: Int, name: String, surname: String, email: String, telephone: String, password: String, role: models.db.AccountRole.Value, createdAt: java.time.OffsetDateTime, updatedAt: java.time.OffsetDateTime)
  /** GetResult implicit for fetching AccountRow objects using plain SQL queries */
  implicit def GetResultAccountRow(implicit e0: GR[Int], e1: GR[String], e2: GR[models.db.AccountRole.Value], e3: GR[java.time.OffsetDateTime]): GR[AccountRow] = GR{
    prs => import prs._
    AccountRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String], <<[String], <<[models.db.AccountRole.Value], <<[java.time.OffsetDateTime], <<[java.time.OffsetDateTime]))
  }
  /** Table description of table account. Objects of this class serve as prototypes for rows in queries. */
  class Account(_tableTag: Tag) extends Table[AccountRow](_tableTag, "account") {
    def * = (id, name, surname, email, telephone, password, role, createdAt, updatedAt) <> (AccountRow.tupled, AccountRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(name), Rep.Some(surname), Rep.Some(email), Rep.Some(telephone), Rep.Some(password), Rep.Some(role), Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> AccountRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7.get, _8.get, _9.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(text) */
    val name: Rep[String] = column[String]("name")
    /** Database column surname SqlType(text) */
    val surname: Rep[String] = column[String]("surname")
    /** Database column email SqlType(text) */
    val email: Rep[String] = column[String]("email")
    /** Database column telephone SqlType(text) */
    val telephone: Rep[String] = column[String]("telephone")
    /** Database column password SqlType(text) */
    val password: Rep[String] = column[String]("password")
    /** Database column role SqlType(account_role) */
    val role: Rep[models.db.AccountRole.Value] = column[models.db.AccountRole.Value]("role")
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.time.OffsetDateTime] = column[java.time.OffsetDateTime]("created_at")
    /** Database column updated_at SqlType(timestamptz) */
    val updatedAt: Rep[java.time.OffsetDateTime] = column[java.time.OffsetDateTime]("updated_at")
  }
  /** Collection-like TableQuery object for table Account */
  lazy val Account = new TableQuery(tag => new Account(tag))

  /** Entity class storing rows of table Message
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param title Database column title SqlType(text)
   *  @param content Database column content SqlType(text)
   *  @param accountId Database column account_id SqlType(int4), Default(None)
   *  @param createdAt Database column created_at SqlType(timestamptz)
   *  @param updatedAt Database column updated_at SqlType(timestamptz) */
  case class MessageRow(id: Int, title: String, content: String, accountId: Option[Int] = None, createdAt: java.time.OffsetDateTime, updatedAt: java.time.OffsetDateTime)
  /** GetResult implicit for fetching MessageRow objects using plain SQL queries */
  implicit def GetResultMessageRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[Int]], e3: GR[java.time.OffsetDateTime]): GR[MessageRow] = GR{
    prs => import prs._
    MessageRow.tupled((<<[Int], <<[String], <<[String], <<?[Int], <<[java.time.OffsetDateTime], <<[java.time.OffsetDateTime]))
  }
  /** Table description of table message. Objects of this class serve as prototypes for rows in queries. */
  class Message(_tableTag: Tag) extends Table[MessageRow](_tableTag, "message") {
    def * = (id, title, content, accountId, createdAt, updatedAt) <> (MessageRow.tupled, MessageRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (Rep.Some(id), Rep.Some(title), Rep.Some(content), accountId, Rep.Some(createdAt), Rep.Some(updatedAt)).shaped.<>({r=>import r._; _1.map(_=> MessageRow.tupled((_1.get, _2.get, _3.get, _4, _5.get, _6.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column title SqlType(text) */
    val title: Rep[String] = column[String]("title")
    /** Database column content SqlType(text) */
    val content: Rep[String] = column[String]("content")
    /** Database column account_id SqlType(int4), Default(None) */
    val accountId: Rep[Option[Int]] = column[Option[Int]]("account_id", O.Default(None))
    /** Database column created_at SqlType(timestamptz) */
    val createdAt: Rep[java.time.OffsetDateTime] = column[java.time.OffsetDateTime]("created_at")
    /** Database column updated_at SqlType(timestamptz) */
    val updatedAt: Rep[java.time.OffsetDateTime] = column[java.time.OffsetDateTime]("updated_at")

    /** Foreign key referencing Account (database name message_account_id_fkey) */
    lazy val accountFk = foreignKey("message_account_id_fkey", accountId, Account)(r => Rep.Some(r.id), onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Message */
  lazy val Message = new TableQuery(tag => new Message(tag))
}
