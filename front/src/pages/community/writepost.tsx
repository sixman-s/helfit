import Layout from '@/components/MainLayout';
import style from '../../styles/Community/C_WritePost.module.css';
import WritePostBox from '@/components/Community/WritePostBox';
const WritePost = () => {
  return (
    <>
      <Layout>
        <div className={style.container}>
          <div className={style.box}>
            <WritePostBox />
          </div>
        </div>
      </Layout>
    </>
  );
};
export default WritePost;
