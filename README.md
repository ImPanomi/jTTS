# jTTS

**jTTS** (Java Text-to-Speech) is a modern Java wrapper for Google Translate's Text-to-Speech API.

---

## Features

- 🚀 Simple Builder API
- 🌍 Supports 70+ languages and dialects
- 🎵 Save audio directly as MP3
- 📤 Stream audio to any `OutputStream`
- ⚡ Configurable speech speed
- 🌐 Custom Google Translate TLD support

---

## Installation

> **Note**
>
> jTTS is not yet available on Maven Central.
> Clone the repository and build it locally for now.

---

## Usage

### 1. Import the classes

```java
import io.github.jtts.GTTs;
import io.github.jtts.Speed;
```

---

### 2. Create a TTS instance

```java
GTTs tts = GTTs.builder("Your text here")
        .lang("en")          // ISO 639-1 language code
        .speed(Speed.NORMAL) // or Speed.SLOW
        .build();
```

### Parameters

| Parameter | Description |
|-----------|-------------|
| `text` | The text to convert into speech. |
| `lang` | Language code (e.g. `en`, `fr`, `es`). |
| `speed` | `Speed.NORMAL` or `Speed.SLOW`. Default is `NORMAL`. |
| `tld` *(optional)* | Google Translate top-level domain (default: `com`). Example: `co.uk`. |

---

### 3. Save the audio

```java
tts.save("output.mp3");
```

Or write directly to an `OutputStream`:

```java
tts.writeToOutputStream(yourOutputStream);
```

---

## Complete Example

```java
import io.github.jtts.GTTs;
import io.github.jtts.Speed;

public class Main {

    public static void main(String[] args) {
        try {
            GTTs tts = GTTs.builder("Hello, this is a test!")
                    .lang("en")
                    .speed(Speed.NORMAL)
                    .build();

            tts.save("hello.mp3");

            System.out.println("Audio saved!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
```

---

## Supported Languages

jTTS supports over **70 languages and dialects**.

| Code | Language |
|------|----------|
| af | Afrikaans |
| am | Amharic |
| ar | Arabic |
| bg | Bulgarian |
| bn | Bengali |
| bs | Bosnian |
| ca | Catalan |
| cs | Czech |
| cy | Welsh |
| da | Danish |
| de | German |
| el | Greek |
| en | English |
| es | Spanish |
| et | Estonian |
| eu | Basque |
| fi | Finnish |
| fr | French |
| fr-CA | French (Canada) |
| gl | Galician |
| gu | Gujarati |
| ha | Hausa |
| hi | Hindi |
| hr | Croatian |
| hu | Hungarian |
| id | Indonesian |
| is | Icelandic |
| it | Italian |
| iw | Hebrew |
| ja | Japanese |
| jw | Javanese |
| km | Khmer |
| kn | Kannada |
| ko | Korean |
| la | Latin |
| lt | Lithuanian |
| lv | Latvian |
| ml | Malayalam |
| mr | Marathi |
| ms | Malay |
| my | Myanmar (Burmese) |
| ne | Nepali |
| nl | Dutch |
| no | Norwegian |
| pa | Punjabi (Gurmukhi) |
| pl | Polish |
| pt | Portuguese (Brazil) |
| pt-PT | Portuguese (Portugal) |
| ro | Romanian |
| ru | Russian |
| si | Sinhala |
| sk | Slovak |
| sq | Albanian |
| sr | Serbian |
| su | Sundanese |
| sv | Swedish |
| sw | Swahili |
| ta | Tamil |
| te | Telugu |
| th | Thai |
| tl | Filipino |
| tr | Turkish |
| uk | Ukrainian |
| ur | Urdu |
| vi | Vietnamese |
| yue | Cantonese |
| zh | Chinese (Mandarin) |
| zh-CN | Chinese (Simplified) |
| zh-TW | Chinese (Traditional) |

> **Note:** Deprecated language tags such as `en-us` or `fr-fr` automatically fall back to `en` and `fr`.

---

## Contributing

Contributions are welcome!

1. Fork this repository.
2. Clone your fork.
3. Create a new feature branch.
4. Make your changes.
5. Add tests if applicable.
6. Open a Pull Request targeting the `main` branch.

---

Built with ❤️ using Java.