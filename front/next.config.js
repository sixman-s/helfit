/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  exportPathMap: async function () {
    return {
      '/': { page: '/' },
      '/community': { page: '/community' },
      '/login': { page: '/login' },
      '/signup': { page: '/signup' },
      '/map': { page: '/map' },
      '/calendar': { page: '/calendar' }
    };
  },

  webpack: (config) => {
    config.module.rules.push({
      test: /\.svg$/i,
      issuer: /\.[jt]sx?$/,
      use: ['@svgr/webpack']
    });
    return config;
  }
};

module.exports = nextConfig;
