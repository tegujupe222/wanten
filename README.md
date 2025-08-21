# Want EN - Android Version

A conversational AI app that creates personalized chat experiences with AI personas. This is the Android version of the original "want" app, featuring Google Gemini integration for natural conversations.

## Features

- 🤖 **AI-Powered Conversations**: Chat with AI personas using Google Gemini 2.5 Flash Lite
- 👤 **Customizable Personas**: Create and customize AI personalities
- 💬 **Natural Conversations**: More natural and engaging chat experiences
- 🎭 **Emotion Awareness**: AI responds to emotional context
- 📱 **Modern Android UI**: Beautiful Jetpack Compose interface
- 🔒 **Privacy Focused**: Local data storage and secure API communication
- 💾 **Offline Support**: Messages stored locally using Room database

## Technology Stack

### Android App
- **Framework**: Jetpack Compose
- **Language**: Kotlin
- **Target**: Android API 24+
- **AI Provider**: Google Gemini 2.5 Flash Lite
- **Database**: Room
- **Dependency Injection**: Hilt
- **Networking**: Retrofit + OkHttp

### Backend Server
- **Platform**: Vercel
- **Runtime**: Node.js
- **API**: Google Gemini API
- **CORS**: Enabled for Android app

## Setup Instructions

### 1. Android App Setup

1. Clone the repository:
```bash
git clone https://github.com/tegujupe222/wanten.git
cd wanten
```

2. Open the project in Android Studio

3. Update the Vercel server URL in `NetworkModule.kt`:
```kotlin
.baseUrl("https://your-vercel-app.vercel.app/") // Replace with your Vercel URL
```

4. Build and run on Android device or emulator

### 2. Vercel Server Setup

1. Navigate to the server directory:
```bash
cd sample/want_EN/vercel-server
```

2. Deploy to Vercel:
```bash
./deploy.sh
```

3. Set up environment variables:
   - Go to your Vercel dashboard
   - Navigate to your project settings
   - Add `GEMINI_API_KEY` with your Google Gemini API key

4. Update the Android app with your Vercel URL:
   - Open `NetworkModule.kt`
   - Update `baseUrl` with your Vercel deployment URL

## Project Structure

```
app/src/main/java/com/igafactory/want_en/
├── data/
│   ├── local/                    # Room database
│   │   ├── AppDatabase.kt
│   │   ├── PersonaDao.kt
│   │   ├── ChatMessageDao.kt
│   │   └── Converters.kt
│   ├── model/                    # Data models
│   │   ├── UserPersona.kt
│   │   └── ChatMessage.kt
│   ├── remote/                   # API communication
│   │   ├── ApiService.kt
│   │   └── NetworkModule.kt
│   └── repository/               # Repository layer
│       └── ChatRepository.kt
├── di/                          # Dependency injection
│   └── DatabaseModule.kt
├── ui/
│   ├── navigation/              # Navigation
│   │   ├── NavGraph.kt
│   │   └── Screen.kt
│   ├── screen/                  # UI screens
│   │   ├── ChatScreen.kt
│   │   └── PersonaListScreen.kt
│   ├── theme/                   # App theme
│   └── viewmodel/               # ViewModels
│       ├── ChatViewModel.kt
│       └── PersonaViewModel.kt
├── MainActivity.kt
└── WantEnApplication.kt

sample/want_EN/vercel-server/
├── api/
│   └── gemini-proxy.js         # Gemini API endpoint
├── package.json                # Dependencies
├── vercel.json                # Vercel configuration
├── deploy.sh                  # Deployment script
└── README.md                  # Server documentation
```

## API Endpoints

### POST /api/gemini-proxy

Handles AI conversation requests from the Android app.

**Request Body:**
```json
{
  "persona": {
    "id": "persona-id",
    "name": "Assistant Name",
    "relationship": "Friend",
    "personality": ["Friendly", "Helpful"],
    "speechStyle": "Casual and warm",
    "catchphrases": ["Hello!", "How can I help?"],
    "favoriteTopics": ["Technology", "Science"]
  },
  "conversationHistory": [
    {
      "id": "message-id",
      "content": "Hello!",
      "isFromUser": true,
      "timestamp": "2024-01-01T00:00:00Z"
    }
  ],
  "userMessage": "How are you today?",
  "emotionContext": "Happy and excited"
}
```

**Response:**
```json
{
  "response": "Hello! I'm doing great today, thanks for asking! How about you?",
  "error": null,
  "model": "gemini-2.5-flash-lite"
}
```

## Configuration

### Environment Variables

Required for the Vercel server:
- `GEMINI_API_KEY`: Your Google Gemini API key

### Android Permissions

The app requires the following permissions:
- `INTERNET`: For API communication
- `ACCESS_NETWORK_STATE`: For network status checking

## Development

### Building the Android App

```bash
./gradlew assembleDebug
```

### Running the Server Locally

```bash
cd sample/want_EN/vercel-server
npm install
vercel dev
```

## Deployment

### Android App
- Build APK or AAB for distribution
- Upload to Google Play Store
- Configure app signing and release builds

### Vercel Server
- Automatic deployment on git push
- Environment variables configured in Vercel dashboard
- Custom domain setup (optional)

## Security Considerations

- API keys stored securely in Vercel environment variables
- CORS configured for Android app domain
- Input validation and sanitization
- Rate limiting protection
- Error handling without exposing sensitive information

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support and questions:
- Create an issue on GitHub
- Check the documentation in the `sample/want_EN/vercel-server/README.md`
- Review the code comments for implementation details

## Changelog

### Version 1.0.0 (Android Release)
- ✅ Complete Android implementation
- ✅ Google Gemini 2.5 Flash Lite integration
- ✅ Vercel server deployment
- ✅ Enhanced conversation naturalness
- ✅ Improved error handling
- ✅ Modern Material 3 UI
- ✅ Comprehensive documentation
