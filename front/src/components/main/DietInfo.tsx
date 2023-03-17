import Link from 'next/link';
import layout from '../../styles/main/C_infoLayout.module.css';
import NonMembers from './NonMembers';
import axios from 'axios';
import { useEffect } from 'react';

const DietInfo = () => {
  const url = `${process.env.NEXT_PUBLIC_URL}/api/v1/ai/question`;
  const body = {
    qusetion:
      '키 185cm, 몸무게 90kg, 하루 권장 소비 칼로리 4500kcal 벌크업 식단 알려줘'
  };

  // useEffect(() => {
  //   axios.post(url, body).then((res) => console.log(res));
  // }, []);
  return <></>;
};

export default DietInfo;
