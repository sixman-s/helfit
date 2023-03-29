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
      '/map': { page: '/map' },
      '/p/hello-nextjs': { page: '/post', query: { title: 'hello-nextjs' } },
      '/p/learn-nextjs': { page: '/post', query: { title: 'learn-nextjs' } },
      '/p/deploy-nextjs': { page: '/post', query: { title: 'deploy-nextjs' } }
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
