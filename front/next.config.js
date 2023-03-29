// Process
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
      '/signup': { page: '/signup' },
      '/mypage': { page: '/mypage' },
      '/oauth2': { page: '/oauth2/receive' }
    };
  }
  // async redirects() {
  //   return [
  //     // {
  //     //   source: '/oauth2/:path*',
  //     //   destination: '/oauth2/:path*',
  //     //   permanent: true
  //     // },
  //     {
  //       source: '/oauth2/receive:path*',
  //       destination: '/oauth2/receive:path*',
  //       permanent: true
  //     }
  //   ];
  // }
};

module.exports = nextConfig;
