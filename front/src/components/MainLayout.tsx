import { LNB } from './LNB';

type MainProps = {
  children: React.ReactNode;
};

const Layout = ({ children }: MainProps) => {
  return (
    <main>
      <LNB />
      <section>{children}</section>
    </main>
  );
};

export default Layout;
