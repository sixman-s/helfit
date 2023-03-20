import s from '../../styles/mypage/C_MyList.module.css';
import Pagenation from './Pagination';

const MyList = () => {
  return (
    <div className={s.myListContainer}>
      <div className={s.topLine}>
        <h1>내 게시글</h1>
        <span>더보기</span>
      </div>
      <table className={s.table}>
        <tbody>
          <tr>
            <td className={s.qNumber}>01</td>
            <td className={s.qContent}>kdkdjsdjidajsai</td>
            <td className={s.qDate}>2020/01/01</td>
          </tr>
          <tr>
            <td className={s.qNumber}>02</td>
            <td className={s.qContent}>kdkdjsdjidajsai</td>
            <td className={s.qDate}>2020/01/01</td>
          </tr>
          <tr>
            <td className={s.qNumber}>03</td>
            <td className={s.qContent}>kdkdjsdjidajsai</td>
            <td className={s.qDate}>2020/01/01</td>
          </tr>
          <tr>
            <td className={s.qNumber}>04</td>
            <td className={s.qContent}>kdkdjsdjidajsai</td>
            <td className={s.qDate}>2020/01/01</td>
          </tr>
          <tr>
            <td className={s.qNumber}>05</td>
            <td className={s.qContent}>kdkdjsdjidajsai</td>
            <td className={s.qDate}>2020/01/01</td>
          </tr>
        </tbody>
      </table>
      <div className={s.pageDiv}>
        <Pagenation />
      </div>
      <div className={s.topLine}>
        <h1>내 답변</h1>
        <span>더보기</span>
      </div>
      <table className={s.table}>
        <tbody>
          <tr>
            <td className={s.aNumber}>01</td>
            <td className={s.aContent}>kdkdjsdjidajsai</td>
            <td className={s.aDate}>2020/01/01</td>
          </tr>
          <tr>
            <td className={s.aNumber}>02</td>
            <td className={s.aContent}>kdkdjsdjidajsai</td>
            <td className={s.aDate}>2020/01/01</td>
          </tr>
          <tr>
            <td className={s.aNumber}>03</td>
            <td className={s.aContent}>kdkdjsdjidajsai</td>
            <td className={s.aDate}>2020/01/01</td>
          </tr>
          <tr>
            <td className={s.aNumber}>04</td>
            <td className={s.aContent}>kdkdjsdjidajsai</td>
            <td className={s.aDate}>2020/01/01</td>
          </tr>
          <tr>
            <td className={s.aNumber}>05</td>
            <td className={s.aContent}>kdkdjsdjidajsai</td>
            <td className={s.aDate}>2020/01/01</td>
          </tr>
          <tr>
            <td className={s.page}></td>
          </tr>
        </tbody>
      </table>
      <div className={s.pageDiv}>
        <Pagenation />
      </div>
    </div>
  );
};

export default MyList;
