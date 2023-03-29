import axios from 'axios';
import s from '../../styles/mypage/C_CalorieInfo.module.css';

const CalorieInfo = (result) => {
  return (
    <div className={s.calroieContainer}>
      <span className={s.calorieTitle}>Result</span>
      <span className={s.subTitle}>=</span>
      <div className={s.bottomContainer}>
        <div className={s.calorieDiv}>
          <span>{result ? Math.round(result.calorie) : 0}</span>
        </div>
        <div className={s.kcal}>Kcal</div>
      </div>
    </div>
  );
};

export default CalorieInfo;
