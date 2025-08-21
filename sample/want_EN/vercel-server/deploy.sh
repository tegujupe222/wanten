#!/bin/bash

# Want EN Vercel Server Deployment Script
# This script deploys the Vercel server for the Want EN Android app

echo "🚀 Deploying Want EN Vercel Server..."

# Check if Vercel CLI is installed
if ! command -v vercel &> /dev/null; then
    echo "❌ Vercel CLI is not installed. Installing..."
    npm install -g vercel
fi

# Check if user is logged in to Vercel
if ! vercel whoami &> /dev/null; then
    echo "🔐 Please log in to Vercel..."
    vercel login
fi

# Deploy to Vercel
echo "📦 Deploying to Vercel..."
vercel --prod

echo "✅ Deployment complete!"
echo ""
echo "📝 Next steps:"
echo "1. Go to your Vercel dashboard"
echo "2. Navigate to your project settings"
echo "3. Add GEMINI_API_KEY environment variable"
echo "4. Update the Android app's NetworkModule.kt with your Vercel URL"
echo ""
echo "🔗 Your API endpoint will be: https://your-project.vercel.app/api/gemini-proxy"
