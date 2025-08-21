// Gemini Proxy API for want_EN Android app
// Handles requests from Android client to Google Gemini API

export default async function handler(req, res) {
  // Enable CORS for all origins
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Methods', 'POST, OPTIONS');
  res.setHeader('Access-Control-Allow-Headers', 'Content-Type, Authorization');
  
  // Handle preflight requests
  if (req.method === 'OPTIONS') {
    res.status(200).end();
    return;
  }
  
  // Only allow POST requests
  if (req.method !== 'POST') {
    console.error('❌ Method not allowed:', req.method);
    return res.status(405).json({ 
      error: 'Method not allowed. Only POST requests are supported.',
      method: req.method 
    });
  }
  
  try {
    console.log('🚀 Gemini Proxy API called');
    console.log('📝 Request body:', JSON.stringify(req.body, null, 2));
    
    // Validate request body
    if (!req.body || !req.body.userMessage) {
      console.error('❌ Invalid request: missing userMessage');
      return res.status(400).json({ 
        error: 'Invalid request: userMessage is required' 
      });
    }
    
    // Get Gemini API key from environment
    const geminiApiKey = process.env.GEMINI_API_KEY;
    if (!geminiApiKey) {
      console.error('❌ GEMINI_API_KEY not set in environment');
      return res.status(500).json({ 
        error: 'Gemini API key not configured on server' 
      });
    }
    
    console.log('🔑 Gemini API key found (length: ' + geminiApiKey.length + ')');
    
    // Extract data from request
    const { persona, conversationHistory, userMessage, emotionContext } = req.body;
    
    // Build conversation history for Gemini
    const messages = [];
    
    // Add conversation history
    if (conversationHistory && conversationHistory.length > 0) {
      for (const message of conversationHistory) {
        messages.push({
          role: message.isFromUser ? 'user' : 'model',
          parts: [{ text: message.content }]
        });
      }
    }
    
    // Add current user message
    messages.push({
      role: 'user',
      parts: [{ text: userMessage }]
    });
    
    // Build system prompt with persona context
    const systemPrompt = buildSystemPrompt(persona, emotionContext);
    
    console.log('📨 Sending request to Gemini API');
    console.log('💬 Messages count:', messages.length);
    
    // Call Gemini API
    const geminiResponse = await fetch(`https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash-exp:generateContent?key=${geminiApiKey}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        contents: [
          {
            role: 'user',
            parts: [{ text: systemPrompt }]
          },
          ...messages
        ],
        generationConfig: {
          temperature: 0.7,
          topK: 40,
          topP: 0.95,
          maxOutputTokens: 1000
        }
      })
    });
    
    console.log('📡 Gemini API response status:', geminiResponse.status);
    
    if (!geminiResponse.ok) {
      const errorText = await geminiResponse.text();
      console.error('❌ Gemini API error:', errorText);
      return res.status(geminiResponse.status).json({
        error: `Gemini API error: ${geminiResponse.status} - ${errorText}`
      });
    }
    
    const geminiData = await geminiResponse.json();
    console.log('✅ Gemini API response received');
    
    // Extract the response text
    const responseText = geminiData.candidates?.[0]?.content?.parts?.[0]?.text;
    if (!responseText) {
      console.error('❌ No response text in Gemini response');
      return res.status(500).json({
        error: 'No response text received from Gemini'
      });
    }
    
    console.log('💬 Response text:', responseText.substring(0, 100) + '...');
    
    // Return success response
    res.status(200).json({
      response: responseText,
      error: null,
      model: 'gemini-2.0-flash-exp'
    });
    
  } catch (error) {
    console.error('❌ Server error:', error);
    res.status(500).json({
      error: `Server error: ${error.message}`,
      response: null
    });
  }
}

// Helper function to build system prompt
function buildSystemPrompt(persona, emotionContext) {
  let prompt = `You are ${persona.name}, a ${persona.relationship}. `;
  
  if (persona.personality && persona.personality.length > 0) {
    prompt += `Your personality traits include: ${persona.personality.join(', ')}. `;
  }
  
  if (persona.speechStyle) {
    prompt += `Your speech style is: ${persona.speechStyle}. `;
  }
  
  if (persona.catchphrases && persona.catchphrases.length > 0) {
    prompt += `You often use these phrases: ${persona.catchphrases.join(', ')}. `;
  }
  
  if (persona.favoriteTopics && persona.favoriteTopics.length > 0) {
    prompt += `You love talking about: ${persona.favoriteTopics.join(', ')}. `;
  }
  
  if (emotionContext) {
    prompt += `Current emotional context: ${emotionContext}. `;
  }
  
  prompt += `Please respond naturally as ${persona.name}, maintaining your unique personality and relationship with the user. Keep responses conversational and engaging.`;
  
  return prompt;
}
