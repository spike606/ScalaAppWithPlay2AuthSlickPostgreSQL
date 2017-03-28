package models

import java.time.OffsetDateTime

import models.db.{AccountRole, Tables}

case class Entity[T](id:Int, data:T)

case class Account(name: String, surname: String, email: String, telephone: String, role: AccountRole.Value) {
  def isAdmin: Boolean = role == AccountRole.admin
}

object Account {
  def apply(row: Tables.AccountRow): Entity[Account] = {
    Entity(
      id = row.id,
      data = Account(
        name = row.name,
        surname = row.surname,
        email = row.email,
        telephone = row.telephone,
        role = row.role
      )
    )
  }
}

case class Message(title:String, content:String) {
  def toRow() = {
    val now = OffsetDateTime.now()
    Tables.MessageRow(
      id = -1,
      title = title,
      content = content,
      createdAt = now,
      updatedAt = now
    )
  }
}

object Message {
  def apply(row: Tables.MessageRow): Entity[Message] = {
    Entity(
      id = row.id,
      data = Message(
        title = row.title,
        content = row.content
      )
    )
  }

  def formApply(title:String, content:String):Message = {
    Message(
      title = title.trim,
      content = content.trim
    )
  }

  def formUnapply(m:Message):Option[(String, String)] = {
    Some((m.title, m.content))
  }
}

