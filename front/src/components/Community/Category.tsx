import React, { useState } from 'react';
import { Menu, MenuItemProps } from 'semantic-ui-react';
import styles from '../../styles/Community/C_Community.module.css';

const Category = () => {
  const [activeItem, setActiveItem] = useState<string>('home');

  const handleItemClick = (
    event: React.MouseEvent<HTMLAnchorElement>,
    data: MenuItemProps
  ) => {
    const { name } = data;
    setActiveItem(name || '');
  };

  return (
    <div>
      <Menu pointing secondary className={styles.Menubar}>
        <Menu.Item
          name='home'
          active={activeItem === 'home'}
          onClick={handleItemClick}
          className={
            activeItem === 'home' ? styles.activeItem : styles.menuItem
          }
        />
        <Menu.Item
          name='헬스 갤러리'
          active={activeItem === '헬스 갤러리'}
          onClick={handleItemClick}
          className={
            activeItem === '헬스 갤러리' ? styles.activeItem : styles.menuItem
          }
        />
        <Menu.Item
          name='크로스핏 갤러리'
          active={activeItem === '크로스핏 갤러리'}
          onClick={handleItemClick}
          className={
            activeItem === '크로스핏 갤러리'
              ? styles.activeItem
              : styles.menuItem
          }
        />
        <Menu.Item
          name='필라테스 갤러리'
          active={activeItem === '필라테스 갤러리'}
          onClick={handleItemClick}
          className={
            activeItem === '필라테스 갤러리'
              ? styles.activeItem
              : styles.menuItem
          }
        />
        <Menu.Item
          name='오운완 갤러리'
          active={activeItem === '오운완 갤러리'}
          onClick={handleItemClick}
          className={
            activeItem === '오운완 갤러리' ? styles.activeItem : styles.menuItem
          }
        />
        <Menu.Item
          name='식단 갤러리'
          active={activeItem === '식단 갤러리'}
          onClick={handleItemClick}
          className={
            activeItem === '식단 갤러리' ? styles.activeItem : styles.menuItem
          }
        />
      </Menu>
    </div>
  );
};

export default Category;
