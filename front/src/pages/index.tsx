import Layout from '@/components/MainLayout';

export default function Home() {
  const style = {
    height: 1000,
    background: '#ddd'
  };
  return (
    <Layout>
      <h1 style={style}>contents</h1>
    </Layout>
  );
}
