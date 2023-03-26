import style from '../../../styles/Community/P_Community.module.css';
import Layout from '@/components/MainLayout';
import Categoty from '@/components/Community/Category';
import Searchbar from '@/components/Community/SearchBar';
import OwwPost from '@/components/Community/EachPost/OwwPost';

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
          <OwwPost />
        </div>
      </div>
    </Layout>
  );
};

export default Community;
