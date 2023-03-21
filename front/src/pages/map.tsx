import Layout from '@/components/MainLayout';
import KakaoMap from '@/components/map/KakaoMap';
import axios from 'axios';
import { useState } from 'react';

import useGeoLocation from '../components/map/Geolocation';
import map from '../styles/map/P_Map.module.css';

const Map = () => {
  const [findLocation, setFindLocation] = useState(false);
  const location = useGeoLocation();

  console.log(location);
  const myLocation = () => {
    setFindLocation(true);
  };

  return (
    <Layout>
      <div className={map.mapContainer}>
        <div className={map.topContainer}>
          <span id={map.mylocation}>
            <img src='../../assets/Map/mylocation.svg' onClick={myLocation} />
          </span>
          <span id={map.fitness} className={map.facilities}>
            헬스장
          </span>
          <span id={map.pliates} className={map.facilities}>
            필라테스
          </span>
          <span id={map.yoga} className={map.facilities}>
            요가
          </span>
          <span id={map.crossfit} className={map.facilities}>
            크로스핏
          </span>
        </div>
        <KakaoMap
          address={findLocation ? JSON.stringify(location) : undefined}
        />
      </div>
    </Layout>
  );
};

export default Map;
