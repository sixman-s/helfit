import axios from 'axios';
import s from '../../styles/mypage/C_CalorieInfo.module.css';
// import { calorieProps } from './HealthInfo';

// export interface calorieProps {
//   result: number;
// }

const CalorieInfo = (result) => {
  console.log(`calorie : ${result.calorie}`);
  // const k1 = JSON.stringify(result.caloir);
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
