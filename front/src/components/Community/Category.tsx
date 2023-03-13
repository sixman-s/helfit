import React, { useState } from 'react';
import { Menu, MenuItemProps } from 'semantic-ui-react';
import styles from '../../styles/Community/C_Community.module.css';
import { useRouter } from 'next/router';

const menuItems = [
  { name: 'home', path: '/community' },
  { name: '헬스 갤러리', path: '/community/health' },
  { name: '크로스핏 갤러리', path: '/community/crossfit' },
  { name: '필라테스 갤러리', path: '/community/pilates' },
  { name: '오운완 갤러리', path: '/community/oww' },
  { name: '식단 갤러리', path: '/community/diet' }
];

const Category = () => {
  const router = useRouter();
  const [activeItem, setActiveItem] = useState<string>(
    menuItems.find((item) => item.path === router.pathname)?.name || 'home'
  );

  const handleItemClick = (
    event: React.MouseEvent<HTMLAnchorElement>,
    data: MenuItemProps
  ) => {
    const name = data.name as string;
    const path = menuItems.find((item) => item.name === name)?.path || '';
    setActiveItem(name);
    event.preventDefault();
    router.push(path);
  };

  return (
    <div>
      <Menu pointing secondary className={styles.Menubar}>
        {menuItems.map((item) => (
          <Menu.Item
            key={item.name}
            name={item.name}
            active={router.pathname === item.path}
            onClick={handleItemClick}
            className={
              activeItem === item.name ? styles.activeItem : styles.menuItem
            }
          />
        ))}
      </Menu>
    </div>
  );
};

export default Category;
