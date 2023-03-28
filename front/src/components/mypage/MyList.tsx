import { useEffect, useState } from 'react';
import s from '../../styles/mypage/C_MyList.module.css';
import MyBoard from './board/MyBoard';
import MyComment from './board/MyComment';

declare namespace JSX {
  interface IntrinsicElements {
    MyBoard: any;
    MyComment: any;
  }
}

const MyList = () => {
  const [showModal, setShowMoadal] = useState(false);

  return (
    <div className={s.myListContainer}>
      <div>
        <MyBoard />
        <MyComment />
      </div>
    </div>
  );
};

export default MyList;
