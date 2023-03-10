import { useState } from 'react';
import styles from '../styles/lnb.module.css';
import Link from 'next/link';
import { useRouter } from 'next/router';

export const LNB = () => {
  const [choiceIdx, setChoiceIdx] = useState(0);
  const [menuClick, setMenuClick] = useState(true);
  const router = useRouter();
  console.log(router.pathname);
  const category: string[] = ['Home', 'Community', 'Map', 'Calender', 'Mypage'];

  const clickMenu = (idx: number) => {
    setChoiceIdx(idx);
    setMenuClick(menuClick!);
  };

  const linkPath = (menu: string) => {
    if (menu === 'Home') {
      return '/';
    } else if (menu.includes('community')) return `/${menu.toLowerCase()}`;
    else return `/${menu.toLowerCase()}`;
  };

  return (
    <nav className={styles.container}>
      <img className={styles.logo} src={'assets/logo.svg'} />
      <ul className={styles.manuUl}>
        {category.map((menu: string, key: number) => {
          return (
            <li key={key} onClick={() => clickMenu(key)}>
              <Link className={styles.manu} href={linkPath(menu)}>
                <img
                  className={
                    menuClick && key === choiceIdx
                      ? styles.lnbIcon_active
                      : styles.lnbIcon
                  }
                  src={`assets/lnb_${menu.toLowerCase()}_icn.svg`}
                />
                <span
                  className={
                    menuClick && key === choiceIdx
                      ? styles.lnbFont_active
                      : styles.lnbFont
                  }
                >
                  {menu}
                </span>
              </Link>
            </li>
          );
        })}
        <li className={styles.manu}>
          <img src='/assets/lnb_lnout_icn.svg' className={styles.lnbIcon} />
          <span className={styles.lnbFont}>{'logout'}</span>
        </li>
      </ul>
    </nav>
  );
};
