import style from '../../../styles/Community/P_Community.module.css';
import Layout from '@/components/MainLayout';
import Categoty from '@/components/Community/Category';
import Searchbar from '@/components/Community/SearchBar';
import CrossfitPost from '@/components/Community/EachPost/CrossfitPost';

const Community = () => {
  return (
    <Layout>
      <div className={style.Container}>
        <div className={style.Searchbar}>
          <Searchbar />
        </div>
        <div className={style.Category}>
          <Categoty />
        </div>
        <div className={style.Popular}>
          <CrossfitPost />
        </div>
      </div>
    </Layout>
  );
};

export default Community;
