import Link from 'next/link';
import layout from '../../styles/main/C_infoLayout.module.css';

const CommunityInfo = () => {
  const username = '홍길동';
  return (
    <article className={layout.container}>
      <header className={layout.header}>
        <h2 className={layout.title}>Today's Member</h2>
      </header>
    </article>
  );
};

export default CommunityInfo;
