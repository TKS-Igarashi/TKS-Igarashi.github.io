# Frontend

IGAM's Portfolio（https://igam.dev）のフロントエンドです。
Scala.js + Laminar で SPA として実装し、Vite でビルドしています。

## 環境

| 分類 | 技術 | バージョン |
  |---|---|---|
| 言語 | Scala | 3.10.0 |
| Scala → JS 変換 | Scala.js | 1.22.0 |
| UI ライブラリ | Laminar | 18.0.0-M5 |
| ルーティング | Waypoint | 10.0.0-M7 |
| DOM API | scalajs-dom | 2.8.1 |
| ビルドツール（Scala） | sbt | 2.0.12 |
| ビルドツール（JS） | Vite | 4.5.14 |
| 実行環境 | Node.js | 24 |
| 実行環境 | JDK | 21 以上（CI は 21、ローカルは 25 で確認） |

## セットアップ

  ```bash
  cd frontend
  npm ci
  ```

`npm ci` は `package-lock.json` どおりに依存パッケージをインストールします。

## 開発サーバー

ターミナルを2つ使います。

  ```bash
  # ターミナル1: Scala の変更を監視して JS を作り直す
  sbt ~fastLinkJS

  # ターミナル2: 開発サーバーを起動する（http://localhost:5173）
  npm run dev
  ```

- `npm run dev` も起動時に1回だけ sbt を実行するが、その後の Scala の変更は検知しない
- `sbt ~fastLinkJS` が変更のたびに JS を作り直し、Vite がそのファイル変更を検知してブラウザを更新する

## 本番ビルドと確認

  ```bash
  npm run build     # dist/ に出力（内部で sbt fullLinkJS を実行）
  npm run preview   # dist/ を http://localhost:4173 で配信
  ```

- `fullLinkJS` は最適化を行うため、`fastLinkJS` より時間がかかるがファイルは小さい
- ビルドが失敗しても、`npm run preview` は前回の `dist/` を表示する。ログの最後に `✓ built in ...`
  が出ていることを確認してから preview する

## Vite と sbt の連携（vite.config.js）

`main.js` の `import 'scalajs:main.js'` は実在しない仮の名前。
`vite.config.js` の自作プラグインが、ビルド時に sbt の出力先へ置き換える。

  ```
  scalajs:main.js
     ↓
  target/out/sjs1/scala-3.10.0/frontend/frontend-opt/main.js
  ```

| フック | タイミング | 処理 |
  |---|---|---|
| `configResolved` | 設定確定時 | dev か build かを判定する |
| `buildStart` | ビルド開始時 | `sbt print fastLinkJSOutput`（dev）/
  `fullLinkJSOutput`（build）を実行し、出力先を取得する |
| `resolveId` | import の解決時 | `scalajs:` で始まる import を、実際のパスに置き換える |

### 公式プラグインを使っていない理由

公式の `@scala-js/vite-plugin-scalajs` は、sbt の出力の**最後の1行**を出力先として扱う。
sbt 2 は標準で thin client モードになり、出力先のパスの後にも出力が続くため、GitHub Actions 上で `Could not
  load /main.js` になった。
自作プラグインでは、出力の中から**実在するディレクトリの行**を探すので、出力の順番に左右されない。

## デプロイ

`.github/workflows/deploy-frontend.yml` で、GitHub Pages へ自動デプロイしている。

- **実行条件:** main ブランチへの push で、`frontend/**` かワークフローファイルが変更されたとき（Actions
  タブから手動実行も可）
- **build ジョブ:** JDK・sbt・Node.js をセットアップ → `npm ci` → `npm run build` → `dist/index.html` を
  `dist/404.html` にコピー → アップロード
- **deploy ジョブ:** build の成功後に、GitHub Pages へ公開

### 404.html をコピーする理由

GitHub Pages は SPA のルーティングに対応していないため、`/profile` などを直接開くと 404 になる。
404 ページの中身を `index.html` と同じにしておくことで、JS が起動して URL に応じたページを表示する。
（HTTP ステータスは 404 のままになる）

### 独自ドメイン

- `igam.dev`（Cloudflare で取得・DNS 管理）
- DNS: apex に GitHub Pages の A / AAAA レコード、`www` に `tks-igarashi.github.io` への CNAME（すべて DNS
  only）
- URL を変える場合は、`index.html` の `canonical` / `og:url` / `og:image`
  と、`src/main/scala/data/HomeData.scala` の `publicUrl` を更新する

## ディレクトリ構成

  ```
  frontend/
  ├── index.html            # エントリーの HTML（title / description / OGP）
  ├── main.js               # CSS と Scala.js の出力を読み込む
  ├── style.css
  ├── vite.config.js        # sbt 連携用の自作 Vite プラグイン
  ├── build.sbt
  ├── project/
  │   ├── build.properties  # sbt のバージョン
  │   └── plugins.sbt       # sbt-scalajs
  ├── public/               # そのまま dist/ の直下にコピーされる
  │   └── images/portfolio/ # favicon.ico、home.png（OGP 画像）
  └── src/main/scala/
      ├── Main.scala        # エントリーポイント
      ├── App.scala         # 全体のレイアウトとページの切り替え
      ├── routing/
      │   ├── AppRouter.scala  # URL とページの対応
      │   └── Page.scala       # ページの定義とタイトル
      ├── pages/
      │   ├── Home.scala
      │   ├── Profile.scala
      │   ├── Skill.scala
      │   ├── Works.scala
      │   ├── Contact.scala
      │   └── home/            # ホームを構成するセクション
      │       ├── HeroSection.scala
      │       ├── HeroArtwork.scala
      │       ├── TechStrip.scala
      │       ├── AboutSection.scala
      │       └── WorkSection.scala
      ├── components/        # 複数のページで使う部品
      │   ├── Header.scala
      │   ├── Footer.scala
      │   ├── PageHeading.scala
      │   ├── PageLink.scala
      │   ├── ArrowLink.scala
      │   ├── ProjectCard.scala
      │   └── ContactBand.scala
      ├── models/
      │   └── Project.scala   # 制作実績のデータ型
      ├── data/
      │   └── HomeData.scala  # 技術一覧・制作実績のデータ
      └── styles/
          ├── main.css        # 各 CSS をまとめて読み込む
          ├── base/           # 変数（色）・全体の共通スタイル
          ├── components/     # 部品ごとのスタイル
          ├── pages/          # ページごとのスタイル
          ├── japanese.css
          └── responsive.css  # 画面幅ごとの調整
  ```

### スタイルのルール

- 色は `styles/base/variables.css` の変数（`--ink`、`--text`、`--muted` など）を使う
- 文字色は、背景とのコントラスト比 4.5:1 以上（WCAG AA）を保つ

## 品質

Lighthouse（シークレットウィンドウ、ホームページで計測）

| Performance | Accessibility | Best Practices | SEO |
  |---|---|---|---|
| 100 | 100 | 100 | 100 |

## トラブルシューティング

| 症状 | 原因 | 対処 |
  |---|---|---|
| Git Bash で `Unable to access jarfile ...current.lnk\bin\sbt-launch.jar` | sdkman の `current` は Windows
  から見ると `.lnk` ファイルで、`cmd.exe` がたどれない | PowerShell で実行する。または `~/.bashrc` で Coursier の
  sbt を優先する |
| `DEP0190 DeprecationWarning` | Node 24 で、`shell: true` と引数配列の組み合わせが非推奨になった |
  動作に影響なし。`spawn` にコマンドを1つの文字列で渡せば消える |
| CI で `Could not load /main.js` | 公式プラグインが sbt 2 の出力形式に対応していない |
  自作プラグインで対応済み |
| `npm run preview` で変更が反映されない | ビルドが失敗し、前回の `dist/` が表示されている | `npm run build`
  の成功を確認する |

Git Bash で Coursier の sbt を優先する設定（`~/.bashrc`）:

  ```bash
  export PATH="/c/Users/tks50/AppData/Local/Coursier/data/bin:$PATH"
  ```
