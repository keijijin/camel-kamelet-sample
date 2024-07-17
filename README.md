# Camel Kamelet Sample プロジェクト

このプロジェクトは、Camel 4とSpring Bootを使用してKameletの基本的な使用方法を示すサンプルプロジェクトです。

## プロジェクト構成

```
camel-kamelet-sample/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── sample/
│   │   │           └── CamelKameletSampleApplication.java
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   └── kamelets/
│   │   │       ├── simple-kamelet.kamelet.yaml
│   │   │       └── kamelet.yaml
└── ...
```

## 必要条件

- JDK 17以上
- Maven 3.6.3以上

## セットアップ

### 依存関係のインストール

プロジェクトディレクトリで以下のコマンドを実行して依存関係をインストールします。

```bash
mvn clean install
```

## アプリケーションの実行

以下のコマンドを実行してアプリケーションを起動します。

```bash
mvn spring-boot:run
```

アプリケーションが正常に起動すると、Kameletによって1秒ごとにログにメッセージが出力されます。

## プロジェクトファイルの説明

### `pom.xml`

このファイルは、プロジェクトの依存関係とビルド設定を定義します。以下の依存関係が含まれています。

- `camel-spring-boot-starter`
- `camel-yaml-dsl-starter`
- `camel-kamelet-starter`
- `spring-boot-starter`

### `src/main/resources/kamelets/simple-kamelet.kamelet.yaml`

このファイルは、Kameletのテンプレートを定義します。定義されたKameletは、1秒ごとに指定されたメッセージをログに出力します。

```yaml
apiVersion: camel.apache.org/v1alpha1
kind: Kamelet
metadata:
  name: simple-kamelet
spec:
  definition:
    title: "Simple Kamelet"
    description: "A simple Kamelet example"
    type: object
    properties:
      message:
        title: "Message"
        description: "The message to log"
        type: string
        default: "Hello from Kamelet!"
  template:
    from:
      uri: timer:trigger
      parameters:
        period: 1000
      steps:
        - set-body:
            simple: "{{message}}"
        - to: log:info
```

### `src/main/resources/kamelets/kamelet.yaml`

このファイルは、Kameletを使用するルートを定義します。

```yaml
- from:
    uri: kamelet:simple-kamelet
  steps:
    - to: log:info
```

### `src/main/java/com/example/CamelKameletSampleApplication.java`

このファイルは、Spring Bootアプリケーションのエントリーポイントを定義します。KameletとYAML DSLをロードするための設定が含まれています。

```java
package com.example;

import org.apache.camel.CamelContext;
import org.apache.camel.dsl.yaml.YamlRoutesBuilderLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CamelKameletSampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(CamelKameletSampleApplication.class, args);
    }

    @Bean
    public YamlRoutesBuilderLoader yamlRoutesBuilderLoader(CamelContext camelContext) {
        YamlRoutesBuilderLoader loader = new YamlRoutesBuilderLoader();
        camelContext.getRegistry().bind("yaml", loader);
        return loader;
    }
}
```

### `src/main/resources/application.properties`

このファイルは、Camel Spring Bootの設定を定義します。KameletのYAMLファイルを読み込むための設定が含まれています。

```properties
camel.springboot.routes-include-pattern = classpath:kamelets/*.yaml
```

## トラブルシューティング

### メッセージがログに出力されない場合

1. `simple-kamelet.kamelet.yaml`のファイル名と配置場所を確認してください。
2. `application.properties`の設定が正しいことを確認してください。
3. プロジェクトをクリーンビルドして再実行してください。

```bash
mvn clean install
mvn spring-boot:run
```
