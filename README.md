# SmartDocExtractor 📄✨

A lightweight, offline-first Kotlin library for Android that intelligently detects document boundaries and isolates the content onto a clean white background—without destructive auto-cropping.

## 🌟 Why SmartDocExtractor?
Most open-source document scanners force rigid auto-cropping, often cutting off critical margins, signatures, or historical manuscript details. **SmartDocExtractor** solves this ecosystem gap by:
- **Preserving Full Context:** Extracts the document cleanly onto a pure white canvas while keeping the original aspect ratio and edges intact.
- **100% Offline & Privacy-First:** Processes all images locally on the device with zero server dependencies.
- **Lightweight Integration:** Built with modern Kotlin and designed for seamless integration into Android apps.

## 🚀 Installation
Add the dependency to your module's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.ahmedalsafti:smart-doc-extractor:1.0.0")
}
```

## 💻 Quick Usage

```kotlin
val extractor = SmartDocExtractor()

// Process a bitmap and isolate the document on a clean white background
val cleanDocumentBitmap = extractor.extractToWhiteCanvas(
    sourceBitmap = originalBitmap,
    enhanceContrast = true
)
```

## 🛠️ Roadmap
- [x] Core document boundary detection
- [x] Smart isolation onto white background
- [ ] Multi-page PDF batch export
- [ ] Jetpack Compose UI components

## 🤝 Contributing
Contributions, issues, and feature requests are welcome! Feel free to check the [issues page](../../issues).

## 📄 License
Distributed under the MIT License. See `LICENSE` for more information.
