import Layout from '@/components/MainLayout';
import KakaoMap from '@/components/map/KakaoMap';
import { useEffect, useState } from 'react';
import { Loader } from '@/components/map/Loader';

import useGeoLocation from '../components/map/Geolocation';
import map from '../styles/map/P_Map.module.css';

export interface IntrinsicAttributes {
  result: {
    info: { lat: number; lng: number };
    search: string;
  };
}

const Map = () => {
  const [findLocation, setFindLocation] = useState(false);
  // const [loading, setLoading] = useState(false);
  const [search, setSearch] =
    useState<IntrinsicAttributes['result']['search']>('');
  const [info, setInfo] = useState<IntrinsicAttributes['result']['info']>();

  const location = useGeoLocation();
  useEffect(() => {}, [findLocation]);

  console.log(location);
  const myLocation = () => {
    setFindLocation(true);
    setInfo(location.coordinates);
    console.log(`info : ${info}`);

    if (findLocation) {
      setFindLocation((current) => !current);
      setInfo(undefined);
      setSearch(undefined);
    }
  };

  const findFitness = () => {
    if (info) {
      setSearch('헬스클럽');
      console.log(`헬스info : ${info}`);
    } else {
      alert('현재 위치를 지정해주세요.');
    }
  };

  const findPilates = () => {
    if (info) {
      setSearch('필라테스');
      console.log(`필라테스info : ${info}`);
    } else {
      alert('현재 위치를 지정해주세요.');
    }
  };

  const findYoga = () => {
    if (info) {
      setSearch('요가');
      console.log(`요가info : ${info}`);
    } else {
      alert('현재 위치를 지정해주세요.');
    }
  };

  const findCrossfit = () => {
    if (info) {
      setSearch('크로스핏');
      console.log(`크로스핏info : ${info}`);
    } else {
      alert('현재 위치를 지정해주세요.');
    }
  };

  return (
    <Layout>
      {location.loaded ? (
        <div className={map.mapContainer}>
          <div className={map.topContainer}>
            <span id={map.mylocation}>
              <img src='../../assets/Map/mylocation.svg' onClick={myLocation} />
            </span>
            {/* <button onClick={myLocation}>원위치</button> */}
            <span
              id={map.fitness}
              className={map.facilities}
              onClick={findFitness}
            >
              헬스장
            </span>
            <span
              id={map.pliates}
              className={map.facilities}
              onClick={findPilates}
            >
              필라테스
            </span>
            <span id={map.yoga} className={map.facilities} onClick={findYoga}>
              요가
            </span>
            <span
              id={map.crossfit}
              className={map.facilities}
              onClick={findCrossfit}
            >
              크로스핏
            </span>
          </div>
          <KakaoMap info={info} search={search} />
        </div>
      ) : (
        <Loader />
      )}
    </Layout>
  );
};

export default Map;
