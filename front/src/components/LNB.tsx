import styles from '../styles/lnb.module.css';
import Link from 'next/link';
import { useRouter } from 'next/router';

const LNB = () => {
  const router = useRouter();
  const category: string[] = ['', 'Community', 'Map', 'Calender', 'Mypage'];

  const linkPath = (menu: string) => {
    if (menu === '') {
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
            <li key={key}>
              <Link className={styles.manu} href={linkPath(menu)}>
                <img
                  className={
                    router.pathname === `/${menu.toLowerCase()}`
                      ? styles.lnbIcon_active
                      : styles.lnbIcon
                  }
                  src={`assets/lnb_${
                    menu === '' ? 'home' : menu.toLowerCase()
                  }_icn.svg`}
                />
                <span
                  className={
                    router.pathname === `/${menu.toLowerCase()}`
                      ? styles.lnbFont_active
                      : styles.lnbFont
                  }
                >
                  {menu === '' ? 'Home' : menu}
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

export default LNB;
