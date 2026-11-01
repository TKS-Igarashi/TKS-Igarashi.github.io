package pages

import org.scalajs.dom
import com.raquo.laminar.api.L.{*, given}
import components.PageHeading

object Contact:

  private val inquiryKinds: List[String] =
    List(
      "スカウト・採用について",
      "Webサイト制作について",
      "アプリケーション開発について",
      "その他のご相談"
    )

  def render(): HtmlElement =
    div(
      cls := "wrap",
      PageHeading.render(
        title = "お問い合わせ",
        eyebrow = "ご相談窓口",
        description =
          "スカウト・採用のお話や、Webサイト・アプリケーション開発についてのご相談はこちらから。"
      ),
      div(
        cls := "contact-layout",
        introduction,
        contactForm
      )
    )

  private def introduction: HtmlElement =
    div(
      span(
        cls := "overline",
        "お気軽にご相談ください"
      ),
      h2(
        "そのアイデアを、",
        br(),
        "かたちにする一歩に。"
      ),
      p(
        cls := "body-copy",
        "Webサイトやアプリケーションについて、",
        br(),
        "ご相談の内容をお聞かせください。"
      ),
      p(
        cls := "notice",
        "こちらはデザイン確認用フォームです。",
        br(),
        "入力内容の確認まで体験できます。",
        br(),
        "データは送信・保存されません。"
      )
    )

  private def contactForm: HtmlElement =
    val confirmed = Var(List.empty[(String, String)])

    val result = div(
      cls := "form-result",
      aria.live := "polite",
      children <-- confirmed.signal.map { rows =>
        if rows.isEmpty then
          List.empty[HtmlElement]
        else
          List(
            div(
              strong("入力内容を確認しました（未送信）"),
              rows.map { case (label, text) =>
                p(
                  whiteSpace := "pre-wrap",
                  overflowWrap := "anywhere",
                  s"$label：$text"
                )
              },
              p("実際のお問い合わせは送信されていません。"),
              onMountCallback { ctx =>
                ctx.thisNode.ref.scrollIntoView(false)
              }
            )
          )
      }
    )

    form(
      idAttr := "contact-form",
      nameField,
      emailField,
      inquiryKindField,
      messageField,
      demoConfirmation,
      submitButton,
      result,

      // 入力を変更したら古い確認結果を消す
      onInput --> { _ => confirmed.set(Nil) },
      onChange --> { _ => confirmed.set(Nil) },

      onSubmit --> { event =>
        event.preventDefault()

        val element =
          event.currentTarget.asInstanceOf[dom.HTMLFormElement]
        val data = new dom.FormData(element)

        val fields = List(
          "お名前" -> "name",
          "メール" -> "email",
          "種類" -> "kind",
          "内容" -> "message"
        )

        confirmed.set(
          fields.map { case (label, key) =>
            label -> Option(data.get(key)).fold("")(_.toString)
          }
        )
      }
    )

  private def nameField: HtmlElement =
    formField(
      fieldId = "name",
      labelText = "お名前",
      isRequired = true,
      control =
        input(
          typ := "text",
          idAttr := "name",
          nameAttr := "name",
          required := true,
          autoComplete := "name",
          placeholder := "山田 太郎",
          maxLength := 100
        )
    )

  private def emailField: HtmlElement =
    formField(
      fieldId = "email",
      labelText = "メールアドレス",
      isRequired = true,
      control =
        input(
          typ := "email",
          idAttr := "email",
          nameAttr := "email",
          required := true,
          autoComplete := "email",
          placeholder := "hello@example.com"
        )
    )

  private def inquiryKindField: HtmlElement =
    formField(
      fieldId = "kind",
      labelText = "ご相談の種類",
      isRequired = false,
      control =
        select(
          idAttr := "kind",
          nameAttr := "kind",
          inquiryKinds.map { kind =>
            option(
              value := kind,
              kind
            )
          }
        )
    )

  private def messageField: HtmlElement =
    formField(
      fieldId = "message",
      labelText = "お問い合わせ内容",
      isRequired = true,
      control =
        textArea(
          idAttr := "message",
          nameAttr := "message",
          required := true,
          placeholder := "ご相談の内容をご記入ください。",
          maxLength := 5000
        )
    )

  private def demoConfirmation: HtmlElement =
    label(
      cls := "check",
      input(
        typ := "checkbox",
        required := true
      ),
      "デモフォームであり、" +
        "実際には送信されないことを確認しました。"
    )

  private def submitButton: HtmlElement =
    button(
      typ := "submit",
      cls := "button",
      "入力内容を確認する ",
      span("→")
    )

  private def formField(
                         fieldId: String,
                         labelText: String,
                         isRequired: Boolean,
                         control: HtmlElement
                       ): HtmlElement =
    div(
      cls := "field",
      label(
        forId := fieldId,
        labelText,
        Option.when(isRequired)(
          span(
            cls := "required",
            "必須"
          )
        )
      ),
      control
    )
