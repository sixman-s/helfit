/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  webpack: (config) => {
    config.module.rules.push({
      test: /\.svg$/i,
      issuer: /\.[jt]sx?$/,
      use: ['@svgr/webpack']
    });
    return config;
  },
  async redirects() {
    return [
      {
        source: '/community/:path*',
        destination: '/community/:path*',
        permanent: false
      },
      {
        source: '/signup',
        destination: '/signup',
        permanent: false
      }
    ];
  }
};

module.exports = nextConfig;
