import { FC, useEffect } from 'react';

import s from '../../styles/map/C_KakaoMap.module.css';

declare global {
  interface Window {
    kakao: any;
  }
}

const KakaoMap: FC<{ address: string }> = ({ address }) => {
  useEffect(() => {
    const onLoadKakaoMap = () => {
      window.kakao.maps.load(() => {
        const geocoder = new window.kakao.maps.services.Geocoder(); // 주소-좌표 반환 객체를 생성
        // 주소로 좌표를 검색
        geocoder.addressSearch(address, (result: any, status: any) => {
          if (status === window.kakao.maps.services.Status.OK) {
            // 정상적으로 검색이 완료됐으면
            var coords = new window.kakao.maps.LatLng(result[0].y, result[0].x);
            // 지도를 생성
            const container = document.getElementById('map');
            const options = {
              center: coords,
              level: 3
            };
            const map = new window.kakao.maps.Map(container, options);
            // 결과값으로 받은 위치를 마커로 표시
            new window.kakao.maps.Marker({
              map: map,
              position: coords
            });
          } else {
            // 정상적으로 좌표가 검색이 안 될 경우 디폴트 좌표로 검색
            const container = document.getElementById('map');
            const options = {
              center: new window.kakao.maps.LatLng(33.450701, 126.570667),
              level: 3
            };
            // 지도를 생성
            const map = new window.kakao.maps.Map(container, options);
            new window.kakao.maps.Marker({
              map: map,
              position: coords
            });
          }
        });
      });
    };
    onLoadKakaoMap();
  }, [address]);

  return <div id='map' className={s.mapContainer} />;
};

export default KakaoMap;
