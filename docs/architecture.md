# Architecture

## 概要

本プロジェクトは [spring-guides/tut-rest](https://github.com/spring-guides/tut-rest) の links
モジュールをベースに、レイヤードアーキテクチャに沿ったパッケージ分割を行ったサンプルAPIです。

## tut-rest からの変更点

- パッケージ分割: tut-rest では全クラスが単一パッケージ（`payroll`）に配置されていたものを、責務ごとにパッケージを分離
- Service レイヤーの導入: Controller から Repository への直接アクセスを禁止し、Service を経由する構成に変更
- Request / Response DTO の導入: Controller が Entity に直接依存しないよう、入出力用の record を導入
- HATEOAS の除外: tut-rest の主題である Spring HATEOAS は本プロジェクトの目的に含まれないため除外済み

## パッケージ構成

```
payroll/
├── config/           # 設定クラス（データ投入等）
├── controller/       # REST コントローラ
│   ├── request/      # リクエスト DTO
│   └── response/     # レスポンス DTO
├── service/          # ビジネスロジック
├── repository/       # データアクセス
├── entity/           # JPA エンティティ
├── enums/            # 列挙型
└── exception/        # 例外・例外ハンドラ
```

## レイヤー間の依存ルール

```
Controller → Service → Repository → Entity
```

- Controller は Repository, Entity に直接アクセスしない（ArchUnit で強制）
- Controller は Request / Response DTO のみを扱う
- Service が Entity ↔ Response DTO の変換を担当

## 例外ハンドリング

- Controller では正常系のみを扱い、例外ハンドリングは `@RestControllerAdvice` に集約する
- Controller から exception パッケージへの依存は ArchUnit で禁止している (ただし `IllegalStateException` 等の標準例外の
  catch は検出できない)

## Enum の永続化

- `@Enumerated(EnumType.STRING)` は使用しない
  - enum名がそのままDB値になるため、enum名のリファクタリングでDBデータと不整合が起きる
- 代わりに `AttributeConverter` を使い、enum と DB値のマッピングを明示的に定義する
  - enum名を自由に変更しても、Converter 内のマッピングが変わらなければDB側に影響しない

## 技術スタック

- Java 25 (Amazon Corretto)
- Spring Boot 4.0
- Spring Data JPA
- H2 Database (インメモリ)
- Gradle 9 (Groovy DSL)
- ArchUnit (アーキテクチャテスト)
