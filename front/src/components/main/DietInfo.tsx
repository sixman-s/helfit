import Link from 'next/link';
import layout from '../../styles/main/C_infoLayout.module.css';

const DietInfo = () => {
  return (
    <article className={layout.container}>
      <header className={layout.header}>
        <h2 className={layout.title}>Today's menu</h2>
        <Link className={layout.moreBtn} href='/'>
          More
        </Link>
      </header>
    </article>
  );
};

export default DietInfo;
