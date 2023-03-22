import LNB from './LNB';
import ChatGPT from './main/util/ChatGPT';

type MainProps = {
  children: React.ReactNode;
};

const Layout = ({ children }: MainProps) => {
  return (
    <main>
      <LNB />
      <ChatGPT />
      <section>{children}</section>
    </main>
  );
};

export default Layout;
