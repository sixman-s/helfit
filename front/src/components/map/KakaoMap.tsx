/* global kakao */
import { FC, useEffect, useRef, useState } from 'react';
import { IntrinsicAttributes } from '@/pages/map';

import s from '../../styles/map/C_KakaoMap.module.css';

declare global {
  interface Window {
    kakao: any;
  }
}

type Place = {
  place_name: string;
  distance: string;
  place_url: string;
  category_name: string;
  address_name: string;
  road_address_name: string;
  id: string;
  phone: string;
  category_group_code: string;
  category_group_name: string;
  x: string;
  y: string;
};

const KakaoMap = (result: IntrinsicAttributes['result']) => {
  const [selectedPlace, setSelectedPlace] = useState<Place>();
  const [exit, setExit] = useState(false);
  const mapRef = useRef<HTMLDivElement>(null);
  const kakao = (window as any).kakao;

  useEffect(() => {
    window.kakao.maps.load(() => {
      const container = mapRef.current;
      const options = {
        center: new kakao.maps.LatLng(
          result.info ? result.info.lat : 37.555078,
          result.info ? result.info.lng : 126.970702
        ),
        level: 4
      };
      // 지도 객체 생성
      const map = new kakao.maps.Map(container, options);
      // 장소 검색 객체 생성
      const places = new kakao.maps.services.Places(map);

      if (result.search) {
        // 키워드 검색이 끝나고 호출될 콜백 함수
        const placesSearchCB = (data, status, pagination) => {
          if (status === kakao.maps.services.Status.OK) {
            for (let i = 0; i < data.length; i++) {
              displayMarker(data[i]);
            }
          }
        };
        // 키워드로 장소 검색
        places.keywordSearch(result.search, placesSearchCB, {
          useMapBounds: true
        });

        // 지도에 마커를 표시해주는 함수
        const displayMarker = (place) => {
          const marker = new kakao.maps.Marker({
            map: map,
            position: new kakao.maps.LatLng(place.y, place.x)
          });
          kakao.maps.event.addListener(marker, 'click', function () {
            setExit(true);
            setSelectedPlace(place);
          });
        };
      } else if (result.info) {
        new window.kakao.maps.Marker({
          map: map,
          position: options.center
        });
      }
    }, []);
  });

  return (
    <>
      <div id='map' ref={mapRef} className={s.mapContainer} />
      <div className={exit ? s.modalContainer : s.hiddenContainer}>
        {selectedPlace && (
          <div className={s.placeInfo}>
            <p id={s.exit}>
              <img
                onClick={() => setExit(false)}
                src='../../../../assets/mypage/exit.svg'
              />
            </p>
            <span id={s.place}>{selectedPlace.place_name}</span>
            <span id={s.address}>{selectedPlace.road_address_name}</span>
            <span id={s.phone}>{selectedPlace.phone}</span>
            <a href={selectedPlace.place_url}>
              <button id={s.kakaoMapBtn}>카카오 지도로 보기</button>
            </a>
          </div>
        )}
      </div>
    </>
  );
};

export default KakaoMap;
