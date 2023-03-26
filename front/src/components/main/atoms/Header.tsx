import Link from 'next/link';
import layout from '../../../styles/main/C_header.module.css';

interface LayoutProps {
  title: string;
  href?: string;
  children: React.ReactNode;
}
const Header = ({ title, href, children }: LayoutProps) => {
  return (
    <article className={layout.container}>
      <header className={layout.header}>
        <h2 className={layout.title}>{title}</h2>
        {href ? (
          <Link className={layout.moreBtn} href={href}>
            More
          </Link>
        ) : null}
      </header>
      {children}
    </article>
  );
};

export default Header;
