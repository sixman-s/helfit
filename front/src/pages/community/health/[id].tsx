import Layout from '@/components/MainLayout';
import style from '../../../styles/Community/P_Detail.module.css';
import DetailP from '@/components/Community/DetailP';

const DetailPage = () => {
  return (
    <>
      <Layout>
        <div className={style.container}>
          <div className={style.box}>
            <DetailP />
          </div>
        </div>
      </Layout>
    </>
  );
};

export default DetailPage;
