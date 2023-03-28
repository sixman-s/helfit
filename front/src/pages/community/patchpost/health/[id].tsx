import Layout from '@/components/MainLayout';
import style from '@/styles/Community/C_WritePost.module.css';
import PatchPostBox from '@/components/Community/Patchpost';
const patchpost = () => {
  return (
    <>
      <Layout>
        <div className={style.container}>
          <div className={style.box}>
            <PatchPostBox />
          </div>
        </div>
      </Layout>
    </>
  );
};
export default patchpost;
