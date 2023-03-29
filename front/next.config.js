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
  exportPathMap: async function (
    defaultPathMap,
    { dev, dir, outDir, distDir, buildId }
  ) {
    return {
      '/': { page: '/' },
      '/map': { page: '/map' }
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
