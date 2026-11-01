# Frontend

## 環境
* Scala.js(1.22.0) + Vite(4.1.0)
* Scalajs-dom(2.8.1)

## JavaScript生成
`sbt fastLinkJS`

都度JavaScript生成したい場合
`sbt ~fastLinkJS`

## 実行手順
1. `sbt ~fastLinkJS`
2. 別ターミナルでcdでfrontendに移動
3. `npm run dev`でViteが実行される。[Vite実行](https://www.scala-js.org/doc/tutorial/scalajs-vite.html?utm_source=chatgpt.com)


## 構成
```markdown
frontend/
│
├─ Main.scala
│
├─ pages/
│   ├─ Profile.scala
│   ├─ Skill.scala
│   ├─ Works.scala
│   └─ Contact.scala
│
├─ components/
│   ├─ Header.scala
│   ├─ Footer.scala
│   ├─ Button.scala
│   ├─ WorkCard.scala
│   └─ SkillCard.scala
│
├─ models/
│   ├─ Work.scala
│   └─ Skill.scala
│
├─ api/
│   ├─ WorkApi.scala
│   └─ ContactApi.scala
│
├─ utils/
│   └─ Validation.scala
│
└─ styles/
    ├─ style.css
    ├─ header.css
    └─ ...
```