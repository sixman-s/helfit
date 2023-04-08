import styled from '../../styles/Community/C_category.module.css';

const menuItems = [
  { name: 'home', path: '/community' },
  { name: '헬스 갤러리', path: '/community/health' },
  { name: '크로스핏 갤러리', path: '/community/crossfit' },
  { name: '필라테스 갤러리', path: '/community/pilates' },
  { name: '오운완 갤러리', path: '/community/oww' },
  { name: '식단 갤러리', path: '/community/diet' }
];

const newCategory = () => {
  return <div>새로운 카테고리</div>;
};

export default newCategory;
