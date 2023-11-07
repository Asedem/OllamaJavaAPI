# OllamaJavaAPI

OllamaJavaAPI is a Java binding for the [Ollama API](https://github.com/jmorganca/ollama/blob/main/docs/api.md), making it easy to interact with Ollama using you favourite Java variation.

This API is by far not finished and many features are missing by now...

## Features

- Intuitive API client: Set up and interact with Ollama in just a few lines of code. `✓`
- Support for various Ollama operations: Including streaming completions (chatting), listing local models, pulling new models, show model information, creating new models, copying models, deleting models, pushing models, and generating embeddings. `✗`
- Real-time streaming: Stream responses directly to your application. `✗`
- Progress reporting: Get real-time progress feedback on tasks like model pulling. `✗`

## Usage

Here's a simple example to get you started:

```java
// Get the List of all installed models
final List<Model> models = Ollama.listModels();

// Execute a simple prompt
final String result = Ollama.generate(new OllamaPrompt("llama2:latest", "How are you today?")).response();
```

## Credits

Structure of the readme is from [Ollama Sharp](https://github.com/awaescher/OllamaSharp).

Icon and name were reused from the amazing [Ollama project](https://github.com/jmorganca/ollama).