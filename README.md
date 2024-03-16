# OllamaJavaAPI

OllamaJavaAPI is a Java binding for the [Ollama API](https://github.com/jmorganca/ollama/blob/main/docs/api.md), making
it easy to interact with Ollama using you favourite Java variation.

This API is by far not finished and many features are missing by now...

## 1 Features

- Intuitive API client: Set up and interact with Ollama in just a few lines of code. `✓`
- Support for various Ollama operations: Including streaming completions (chatting), listing local models, pulling new
  models, show model information, creating new models, copying models, deleting models, pushing models, and generating
  embeddings. `✗`
- Real-time streaming: Stream responses directly to your application. `✗`
- Progress reporting: Get real-time progress feedback on tasks like model pulling. `✗`

## 2 Installation

### Maven

#### Repository

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

#### Dependency

```xml
<dependency>
    <groupId>com.github.Asedem</groupId>
    <artifactId>OllamaJavaAPI</artifactId>
    <version>master-SNAPSHOT</version>
</dependency>
```

### Gradle

#### Repository

```groovy
repositories {
    maven { url 'https://jitpack.io' }
}
```

#### Dependency

```groovy
dependencies {
    implementation 'com.github.Asedem:OllamaJavaAPI:master-SNAPSHOT'
}
```

## 3 Usage

*These examples use poor error handling for simplicity, but you should handle errors properly in your code.*

### 2.1 Initialize Ollama

```java
// By default, it will connect to localhost:11434
Ollama ollama = Ollama.initDefault();

// For custom values
Ollama ollama = Ollama.init("http://localhost", 11434);
```

### 2.2 Completion generation

```java
String model = "llama2:latest";
String prompt = "Why is the sky blue?";

GenerationResponse response = ollama.generate(new GenerationRequest(model, prompt));

System.out.

println(response.response());
```

**OUTPUTS:** The sky appears blue because of a phenomenon called Rayleigh scattering...

### 2.3 List local models

```java
List<Model> models = ollama.listModels();
```

*Returns a* `List` *of* `Model` *objects.*

### 2.4 Show model information

```java
ModelInfo modelInfo = ollama.showInfo("llama2:latest");
```

*Returns a* `ModelInfo` *object.*

### 2.5 Copy a model

```java
boolean success = ollama.copy("llama2:latest", "llama2-backup");
```

*Returns* `true` *if the copy process was successfully.*

### 2.6 Copy a model

```java
boolean success = ollama.delete("llama2-backup");
```

*Returns* `true` *if the copy process was successfully.*

## 4 Credits

Structure of the readme is inspired from [Ollama Sharp](https://github.com/awaescher/OllamaSharp)
and [ollama-rs](https://github.com/pepperoni21/ollama-rs).

Icon and name were reused from the amazing [Ollama project](https://github.com/jmorganca/ollama).