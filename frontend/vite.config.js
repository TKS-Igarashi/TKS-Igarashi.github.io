import { defineConfig } from "vite";
import { spawn } from "node:child_process";
import { existsSync } from "node:fs";

// sbt 2 の thin client は出力先パスの後にも出力を続けるため、
// 公式プラグインの「最後の行を出力先とみなす」処理が失敗する。
// 出力の中から実在するディレクトリの行を探して使う。
function printSbtTask(task) {
  const args = ["--batch", "-no-colors", "-Dsbt.supershell=false", `print ${task}`];
  const options = { stdio: ["ignore", "pipe", "inherit"] };
  const child = process.platform === "win32"
    ? spawn("sbt.bat", args.map(x => `"${x}"`), { shell: true, ...options })
    : spawn("sbt", args, options);

  let output = "";
  child.stdout.setEncoding("utf-8");
  child.stdout.on("data", data => {
    output += data;
    process.stdout.write(data);
  });

  return new Promise((resolve, reject) => {
    child.on("error", reject);
    child.on("close", code => {
      if (code !== 0) {
        reject(new Error(`sbt failed with exit code ${code}`));
        return;
      }
      const dir = output
        .split(/\r?\n/)
        .map(line => line.replace(/\x1b\[[0-9;?]*[A-Za-z]/g, "").trim())
        .reverse()
        .find(line => line && existsSync(line));
      if (dir) resolve(dir);
      else reject(new Error("Scala.js output directory not found in sbt output"));
    });
  });
}

function scalaJSPlugin() {
  let isDev;
  let outputDir;
  return {
    name: "scalajs-sbt",
    configResolved(config) {
      isDev = config.mode === "development";
    },
    async buildStart() {
      outputDir = await printSbtTask(isDev ? "fastLinkJSOutput" : "fullLinkJSOutput");
    },
    resolveId(source) {
      return source.startsWith("scalajs:")
        ? `${outputDir}/${source.slice("scalajs:".length)}`
        : null;
    },
  };
}

export default defineConfig({
  plugins: [scalaJSPlugin()],
});