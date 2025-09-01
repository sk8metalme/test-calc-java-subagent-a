# 電卓アプリ (Calculator App)

Java 21 + Spring Boot + Thymeleafで開発された電卓アプリケーションです。

## 機能

- 基本的な四則演算（加算、減算、乗算、除算）
- パーセント計算
- 平方根計算
- 二乗計算
- 符号反転
- クリア機能（C、CE）
- キーボード入力対応
- レスポンシブデザイン

## 技術スタック

- **Java 21**
- **Spring Boot 3.2.0**
- **Thymeleaf** (テンプレートエンジン)
- **Gradle** (ビルドツール)
- **Bootstrap** (CSS フレームワーク)

## セットアップ

### 前提条件

- Java 21以上
- Gradle 8.5以上

### インストールと実行

1. リポジトリをクローン
```bash
git clone <repository-url>
cd test-calc-java-subagent-a
```

2. アプリケーションを起動
```bash
./gradlew bootRun
```

3. ブラウザでアクセス
```
http://localhost:8080
```

### ビルドとテスト

```bash
# コンパイル
./gradlew compileJava

# テスト実行
./gradlew test

# 完全ビルド
./gradlew build
```

## プロジェクト構造

```
src/
├── main/
│   ├── java/com/example/calculator/
│   │   ├── CalculatorApplication.java      # メインアプリケーション
│   │   ├── controller/
│   │   │   └── CalculatorController.java   # コントローラー
│   │   ├── service/
│   │   │   └── CalculatorService.java      # ビジネスロジック
│   │   └── model/
│   │       └── CalculationRequest.java     # データモデル
│   └── resources/
│       ├── templates/
│       │   └── calculator.html             # Thymeleafテンプレート
│       ├── static/
│       │   ├── css/
│       │   │   └── calculator.css          # スタイルシート
│       │   └── js/
│       │       └── calculator.js           # JavaScript
│       └── application.properties          # アプリケーション設定
└── test/
    └── java/com/example/calculator/
        └── service/
            └── CalculatorServiceTest.java  # ユニットテスト
```

## 使用方法

1. **数字ボタン**: 0-9の数字を入力
2. **演算子ボタン**: +, -, ×, ÷ で計算
3. **機能ボタン**:
   - `C`: 全クリア
   - `CE`: 現在の入力クリア
   - `±`: 符号反転
   - `%`: パーセント計算
   - `√`: 平方根
   - `x²`: 二乗
   - `=`: 計算実行

## キーボードショートカット

- 数字キー: 0-9
- 演算子: +, -, *, /
- Enter または =: 計算実行
- Escape: 全クリア
- .: 小数点

## ライセンス

このプロジェクトはMITライセンスの下で公開されています。
サブエージェントシンプル
