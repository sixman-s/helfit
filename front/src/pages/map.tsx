import Layout from '@/components/MainLayout';
import KakaoMap from '@/components/map/KakaoMap';

const Map = () => {
  return (
    <Layout>
      <KakaoMap address={'천호 현대 백화점'} />
    </Layout>
  );
};

export default Map;
