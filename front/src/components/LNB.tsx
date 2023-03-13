import styles from '../styles/lnb.module.css';
import Link from 'next/link';
import { useRouter } from 'next/router';

const LNB = () => {
  const router = useRouter();
  const category: string[] = [
    'Index',
    'Community',
    'Map',
    'Calendar',
    'Mypage'
  ];

  const linkPath = (menu: string) => {
    if (menu === 'Index') {
      return '/';
    } else if (menu.includes('Community')) {
      return `/${menu.toLowerCase()}`;
    } else return `/${menu.toLowerCase()}`;
  };

  const menuActive = (menu: string) => {
    const path = router.pathname;
    if (menu === 'index' && path === '/') {
      console.log(menu, path);
      return true;
    } else if (path === `/${menu}` || path.includes(`/${menu}`)) {
      console.log(menu, path);
      return true;
    } else {
      console.log(menu, path);
      return false;
    }
  };

  return (
    <nav className={styles.container}>
      <Link href='/'>
        <img className={styles.logo} src={'../../assets/logo.svg'} />
      </Link>
      <ul className={styles.manuUl}>
        {category.map((menu: string, key: number) => {
          return (
            <li key={key}>
              <Link className={styles.manu} href={linkPath(menu)}>
                <img
                  className={
                    menuActive(menu.toLowerCase())
                      ? styles.lnbIcon_active
                      : styles.lnbIcon
                  }
                  src={`../assets/lnb_${
                    menu === 'Index' ? 'home' : menu.toLowerCase()
                  }_icn.svg`}
                />
                <span
                  className={
                    router.pathname === `/${menu.toLowerCase()}`
                      ? styles.lnbFont_active
                      : styles.lnbFont
                  }
                >
                  {menu === 'Index' ? 'Home' : menu}
                </span>
              </Link>
            </li>
          );
        })}
        <li className={styles.manu}>
          <img
            src='../../assets/lnb_lnout_icn.svg'
            className={styles.lnbIcon}
          />
          <span className={styles.lnbFont}>{'logout'}</span>
        </li>
      </ul>
    </nav>
  );
};

export default LNB;
