import styled from '../styles/lnb.module.css';
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
      return true;
    } else if (path === `/${menu}` || path.includes(`/${menu}`)) {
      return true;
    } else {
      return false;
    }
  };

  return (
    <nav className={styled.container}>
      <Link href='/'>
        <img className={styled.logo} src={'../../assets/logo.svg'} />
      </Link>
      <ul className={styled.manuUl}>
        {category.map((menu: string, key: number) => {
          return (
            <li key={key}>
              <Link className={styled.manu} href={linkPath(menu)}>
                <img
                  className={
                    menuActive(menu.toLowerCase())
                      ? styled.lnbIcon_active
                      : styled.lnbIcon
                  }
                  src={`../../assets/lnb_${
                    menu === 'Index' ? 'home' : menu.toLowerCase()
                  }_icn.svg`}
                />
                <span
                  className={
                    menuActive(menu.toLowerCase())
                      ? styled.lnbFont_active
                      : styled.lnbFont
                  }
                >
                  {menu === 'Index' ? 'Home' : menu}
                </span>
              </Link>
            </li>
          );
        })}
        <li className={styled.menu}>
          <Link className={styled.loginMenu} href='/login'>
            <img
              src='../../assets/lnb_lnout_icn.svg'
              className={styled.lnbIcon}
            />
            <span className={styled.lnbFont}>{'login'}</span>
          </Link>
        </li>
      </ul>
    </nav>
  );
};

export default LNB;
