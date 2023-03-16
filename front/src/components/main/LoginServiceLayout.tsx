import Link from 'next/link';
import layout from '../../styles/main/C_infoLayout.module.css';

interface LayoutProps {
  title: string;
  href: string;
  children: React.ReactNode;
}
const LoginServiceLayout = ({ title, href, children }: LayoutProps) => {
  return (
    <article className={layout.container}>
      <header className={layout.header}>
        <h2 className={layout.title}>{title}</h2>
        <Link className={layout.moreBtn} href={href}>
          More
        </Link>
      </header>
      {children}
    </article>
  );
};

export default LoginServiceLayout;
