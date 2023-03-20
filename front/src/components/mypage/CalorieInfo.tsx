import axios from 'axios';
import s from '../../styles/mypage/C_CalorieInfo.module.css';

// interface cDetailProps {
//   result: number;
//   goal: string;
//   activityLevel: string;
//   createdAt: string;
//   modifiedAt: string;
// }

export interface calorieProps {
  result: number;
}

const CalorieInfo = ({ calorie }: { calorie: calorieProps }) => {
  const url = process.env.NEXT_PUBLIC_URL;
  console.log(`calorie : ${calorie}`);
  // console.log(calorie);
  return (
    <div>
      <h1 className={s.calorieTitle}>칼로리</h1>
      <div className={s.bottomContainer}>
        <div>
          <span>{calorie ? Math.round(calorie) : 0}</span>
        </div>
        <div className={s.kcal}>Kcal</div>
      </div>
    </div>
  );
};

export default CalorieInfo;
