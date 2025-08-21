# Want EN - Vercel Server (Gemini)

This is the Vercel serverless backend for the Want EN Android app, providing a secure proxy for Google Gemini API calls.

## Features

- ✅ Secure API key management via environment variables
- ✅ CORS enabled for cross-origin requests
- ✅ Persona-based conversation context
- ✅ Conversation history support
- ✅ Error handling for API limits and safety filters
- ✅ Uses Gemini 2.0 Flash Exp model

## Setup

### 1. Deploy to Vercel

```bash
cd sample/want_EN/vercel-server
vercel --prod
```

### 2. Set Environment Variables

1. Go to your Vercel dashboard
2. Navigate to your project
3. Go to Settings > Environment Variables
4. Add `GEMINI_API_KEY` with your Google Gemini API key

### 3. Get Your API Endpoint

After deployment, you'll get a URL like: `https://your-project.vercel.app`
Your Gemini proxy endpoint will be: `https://your-project.vercel.app/api/gemini-proxy`

## API Endpoint

### POST /api/gemini-proxy

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
  "model": "gemini-2.0-flash-exp"
}
```

## Local Development

```bash
npm install
vercel dev
```

## Environment Variables

- `GEMINI_API_KEY`: Your Google Gemini API key

## Deployment

```bash
vercel --prod
```

## Update Android App

After deploying to Vercel, update the Android app's `NetworkModule.kt`:

```kotlin
.baseUrl("https://your-project.vercel.app/") // Replace with your Vercel URL
```

## Security Considerations

- API keys stored securely in Vercel environment variables
- CORS configured for Android app domain
- Input validation and sanitization
- Rate limiting protection
- Error handling without exposing sensitive information 