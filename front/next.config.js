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
  trailingSlash: true,
  exportPathMap: async function (
    defaultPathMap,
    { dev, dir, outDir, distDir, buildId }
  ) {
    return {
      '/': { page: '/' },
      '/map': { page: '/map' },
      '/community': { page: '/community' },
      '/calendar': { page: '/calendar' },
      '/login': { page: '/login' },
      '/signup': { page: '/signup' }
    };
  }
  // async redirects() {
  //   return [
  //     {
  //       source: '/login',
  //       destination: '/login',
  //       permanent: false
  //     },
  //     {
  //       source: '/map',
  //       destination: '/community',
  //       permanent: false
  //     }
  //   ];
  // }
};

module.exports = nextConfig;
