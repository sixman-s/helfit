import KcalChart from '@/components/main/util/UserKcalChart';
import { useEffect, useState } from 'react';

const Chart = () => {
  const [token, setToken] = useState('');
  const userData = JSON.parse(localStorage.getItem('UserInfo'));
  useEffect(() => {
    setToken(localStorage.getItem('accessToken'));
  });

  return (
    <>
      <KcalChart token={token} userId={userData.userId} />
    </>
  );
};
export default Chart;
