import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      // Proxy API requests to your backend gateway
      '/feeType': 'http://localhost:8082',
      '/frequencyDiscount': 'http://localhost:8082',
      '/peopleDiscount': 'http://localhost:8082',
      '/specialDay': 'http://localhost:8082',
      '/rent': 'http://localhost:8082',
      '/receipt': 'http://localhost:8082',
      '/calendar': 'http://localhost:8082',
      '/report': 'http://localhost:8082',
    }
  }
})
