import { useState } from 'react';
import s from '../../styles/mypage/C_MyList.module.css';
import ModalContainer from './modal/ModalContainer';
import ModalQuit from './modal/ModalQuit';
import Pagenation from './Pagination';

const MyList = () => {
  const [showModal, setShowMoadal] = useState(false);

  const handleQuit = () => {
    const yesOrno = confirm('정말 탈퇴하시겠습니까?');
    if (yesOrno) {
      console.log('kkk');
      clickModal();
    } else {
      console.log('ttt');
    }
  };

  const clickModal = () => {
    setShowMoadal(true);
  };

  return (
    <div className={s.myListContainer}>
      <p id={s.quitContainer}>
        <span id={s.quit} onClick={handleQuit}>
          회원탈퇴
        </span>
      </p>
      <div className={s.topLine}>
        <h1>내 게시글</h1>
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

      <ModalContainer
        showModal={showModal}
        exitModal={() => {
          setShowMoadal(false);
        }}
      >
        <ModalQuit />
      </ModalContainer>
    </div>
  );
};

export default MyList;
