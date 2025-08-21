// Simple test endpoint for Vercel server
export default async function handler(req, res) {
  // Enable CORS
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS');
  res.setHeader('Access-Control-Allow-Headers', 'Content-Type');
  
  if (req.method === 'OPTIONS') {
    res.status(200).end();
    return;
  }
  
  console.log('🧪 Test endpoint called');
  console.log('📝 Method:', req.method);
  console.log('📝 Body:', req.body);
  
  res.status(200).json({
    message: 'Vercel server is working!',
    timestamp: new Date().toISOString(),
    method: req.method,
    body: req.body
  });
}
