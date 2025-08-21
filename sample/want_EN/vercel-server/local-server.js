const express = require('express');
const cors = require('cors');
const fetch = require('node-fetch');

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(cors());
app.use(express.json());

// Test endpoint
app.post('/api/test', (req, res) => {
  console.log('🧪 Test endpoint called');
  res.json({
    message: 'Local server is working!',
    timestamp: new Date().toISOString(),
    body: req.body
  });
});

// Simple Gemini endpoint
app.post('/api/simple-gemini', async (req, res) => {
  try {
    console.log('🚀 Simple Gemini API called');
    
    const { userMessage } = req.body;
    
    if (!userMessage) {
      return res.status(400).json({ error: 'userMessage is required' });
    }
    
    const geminiApiKey = process.env.GEMINI_API_KEY;
    if (!geminiApiKey) {
      return res.status(500).json({ error: 'Gemini API key not configured' });
    }
    
    console.log('📨 Sending request to Gemini API');
    
    const geminiResponse = await fetch(`https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-lite:generateContent?key=${geminiApiKey}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        contents: [
          {
            role: 'user',
            parts: [{ text: userMessage }]
          }
        ],
        generationConfig: {
          temperature: 0.7,
          maxOutputTokens: 1000
        }
      })
    });
    
    if (!geminiResponse.ok) {
      const errorText = await geminiResponse.text();
      console.error('❌ Gemini API error:', errorText);
      return res.status(geminiResponse.status).json({ 
        error: 'Gemini API error',
        details: errorText
      });
    }
    
    const geminiData = await geminiResponse.json();
    const responseText = geminiData.candidates?.[0]?.content?.parts?.[0]?.text || 'No response from Gemini';
    
    console.log('✅ Gemini response received');
    
    res.json({
      response: responseText,
      error: null,
      model: 'gemini-2.5-flash-lite'
    });
    
  } catch (error) {
    console.error('❌ Error:', error);
    res.status(500).json({
      error: 'Internal server error',
      details: error.message
    });
  }
});

app.listen(PORT, () => {
  console.log(`🚀 Local server running on port ${PORT}`);
  console.log(`📝 Test endpoint: http://localhost:${PORT}/api/test`);
  console.log(`🤖 Gemini endpoint: http://localhost:${PORT}/api/simple-gemini`);
});
